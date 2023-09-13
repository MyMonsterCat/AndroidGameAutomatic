package com.github.monster.core.ocr.core;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

@Slf4j
public class Ocr implements AutoCloseable {
    // 公共
    Gson gson;
    boolean ocrReady = false;
    Map<String, Object> arguments;
    BufferedReader reader;
    BufferedWriter writer;
    OcrMode mode;

    // 本地进程模式
    Process process;
    File exePath;


    // 套接字服务器模式
    String serverAddr;
    int serverPort;
    Socket clientSocket;
    boolean isLoopback = false;

    /**
     * 使用套接字模式初始化
     *
     * @param serverAddr 服务地址
     * @param serverPort 服务端口
     * @param arguments 参数
     * @throws IOException 异常
     */
    public Ocr(String serverAddr, int serverPort, Map<String, Object> arguments) throws IOException {
        this.mode = OcrMode.SOCKET_SERVER;
        this.arguments = arguments;
        this.serverAddr = serverAddr;
        this.serverPort = serverPort;
        checkIfLoopback();
        initOcr();
    }

    /**
     * 使用本地进程模式初始化
     *
     * @param exePath 可执行文件路径
     * @param arguments 参数
     * @throws IOException 异常
     */
    public Ocr(File exePath, Map<String, Object> arguments) throws IOException {
        this.mode = OcrMode.LOCAL_PROCESS;
        this.arguments = arguments;
        this.exePath = exePath;
        initOcr();
    }

    private void initOcr() throws IOException {
        gson = new Gson();

        StringBuilder commands = new StringBuilder();
        if (arguments != null) {
            for (Map.Entry<String, Object> entry : arguments.entrySet()) {
                String command = "--" + entry.getKey() + "=";
                if (entry.getValue() instanceof String) {
                    command += "'" + entry.getValue() + "'";
                } else {
                    command += entry.getValue().toString();
                }
                commands.append(' ').append(command);
            }
        }

        if (!StandardCharsets.US_ASCII.newEncoder().canEncode(commands.toString())) {
            throw new IllegalArgumentException("参数不能含有非 ASCII 字符");
        }

        log.debug("OCR --> 当前参数：" + ((commands.length() == 0) ? "空" : commands.toString()));


        switch (this.mode) {
            case LOCAL_PROCESS -> {
                File workingDir = exePath.getParentFile();
                ProcessBuilder pb = new ProcessBuilder(exePath.toString(), commands.toString());
                pb.directory(workingDir);
                pb.redirectErrorStream(true);
                process = pb.start();

                InputStream stdout = process.getInputStream();
                OutputStream stdin = process.getOutputStream();
                reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
                writer = new BufferedWriter(new OutputStreamWriter(stdin, StandardCharsets.UTF_8));
                String line;
                ocrReady = false;
                while (!ocrReady) {
                    line = reader.readLine();
                    if (line.contains("OCR init completed")) {
                        ocrReady = true;
                    }
                }
                log.debug("OCR --> 初始化OCR成功");
            }
            case SOCKET_SERVER -> {
                clientSocket = new Socket(serverAddr, serverPort);
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
                ocrReady = true;
                log.debug("OCR --> 已连接到OCR套接字服务器，假设服务器已初始化成功");
            }
        }


    }

    /**
     * 使用图片路径进行 OCR
     *
     * @param imgFile 图片路径
     */
    public OcrResponse runOcr(File imgFile) throws IOException {
        if (mode == OcrMode.SOCKET_SERVER && !isLoopback) {
            log.debug("OCR --> 套接字模式下服务器不在本地，发送路径可能失败");
        }
        Map<String, String> reqJson = new HashMap<>();
        reqJson.put("image_path", imgFile.toString());
        return this.sendJsonToOcr(reqJson);
    }

    /**
     * 使用剪贴板中图片进行 OCR
     */
    public OcrResponse runOcrOnClipboard() throws IOException {
        if (mode == OcrMode.SOCKET_SERVER && !isLoopback) {
            log.debug("OCR --> 套接字模式下服务器不在本地，发送剪贴板可能失败");
        }
        Map<String, String> reqJson = new HashMap<>();
        reqJson.put("image_path", "clipboard");
        return this.sendJsonToOcr(reqJson);
    }

    /**
     * 使用 Base64 编码的图片进行 OCR
     *
     * @param base64str Base64 编码的图片
     */
    public OcrResponse runOcrOnImgBase64(String base64str) throws IOException {
        Map<String, String> reqJson = new HashMap<>();
        reqJson.put("image_base64", base64str);
        return this.sendJsonToOcr(reqJson);
    }

    /**
     * 使用图片 Byte 数组进行 OCR
     *
     * @param fileBytes 图片 Byte 数组
     */
    public OcrResponse runOcrOnImgBytes(byte[] fileBytes) throws IOException {
        return this.runOcrOnImgBase64(Base64.getEncoder().encodeToString(fileBytes));
    }

    private OcrResponse sendJsonToOcr(Map<String, String> reqJson) throws IOException {
        if (!isAlive()) {
            throw new RuntimeException("OCR进程已经退出或连接已断开");
        }
        StringWriter sw = new StringWriter();
        EscapedWriter ew = new EscapedWriter(sw);
        gson.toJson(reqJson, ew);
        writer.write(sw.getBuffer().toString());
        writer.write("\r\n");
        writer.flush();
        String resp = reader.readLine();
        log.debug("OCR --> 原始数据resp:{}", resp);

        Map rawJsonObj = gson.fromJson(resp, Map.class);
        if (rawJsonObj.get("data") instanceof String) {
            return new OcrResponse((int) Double.parseDouble(rawJsonObj.get("code").toString()), rawJsonObj.get("data").toString());
        }

        return gson.fromJson(resp, OcrResponse.class);
    }


    private void checkIfLoopback() {
        if (this.mode != OcrMode.SOCKET_SERVER) return;
        try {
            InetAddress address = InetAddress.getByName(serverAddr);
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(address);
            this.isLoopback = networkInterface != null && networkInterface.isLoopback();
        } catch (Exception e) {
            // 非关键路径
            log.debug("OCR --> 套接字模式，未能确认服务端是否在本地");
        }
        log.debug("OCR --> 套接字模式下，服务端在本地：" + isLoopback);
    }

    private boolean isAlive() {
        return switch (this.mode) {
            case LOCAL_PROCESS -> process.isAlive();
            case SOCKET_SERVER -> clientSocket.isConnected();
        };
    }


    @Override
    public void close() {
        if (isAlive()) {
            switch (this.mode) {
                case LOCAL_PROCESS -> process.destroy();
                case SOCKET_SERVER -> {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

