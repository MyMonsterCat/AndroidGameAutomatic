package com.github.monster.core.ocr;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class Ocr implements AutoCloseable {
    Process p;
    BufferedReader reader;
    BufferedWriter writer;
    Gson gson;

    boolean ocrReady = false;

    public Ocr(File exePath, Map<String, Object> arguments) throws IOException {
        gson = new Gson();

        String commands = "";
        if (arguments != null) {
            for (Map.Entry<String, Object> entry : arguments.entrySet()) {
                String command = "--" + entry.getKey() + "=";
                if (entry.getValue() instanceof String) {
                    command += "'" + entry.getValue() + "'";
                } else {
                    command += entry.getValue().toString();
                }
                commands += ' ' + command;
            }
        }

        if (!commands.contains("use_debug")) {
            commands += ' ' + "--use_debug=0";
        }

        if (!StandardCharsets.US_ASCII.newEncoder().canEncode(commands)) {
            throw new IllegalArgumentException("参数不能含有非 ASCII 字符");
        }

        System.out.println("当前参数：" + commands);

        File workingDir = exePath.getParentFile();
        ProcessBuilder pb = new ProcessBuilder(exePath.toString(), commands);
        pb.directory(workingDir);
        pb.redirectErrorStream(true);
        p = pb.start();

        InputStream stdout = p.getInputStream();
        OutputStream stdin = p.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
        writer = new BufferedWriter(new OutputStreamWriter(stdin, StandardCharsets.UTF_8));

        String line = "";
        ocrReady = false;
        while (!ocrReady) {
            line = reader.readLine();
//            System.out.println(line);
            if (line.contains("OCR init completed")) {
                ocrReady = true;
            }
        }

        System.out.println("初始化OCR成功");
    }

    public OcrResponse runOcr(File imgFile) throws IOException {
        return this.runOcrOnPath(imgFile.toString());
    }

    public OcrResponse runOcrOnClipboard() throws IOException {
        return this.runOcrOnPath("clipboard");
    }

    private OcrResponse runOcrOnPath(String path) throws IOException {
        if (!p.isAlive()) {
            throw new RuntimeException("OCR进程已经退出");
        }
        Map<String, String> reqJson = new HashMap<>();
        reqJson.put("image_dir", path);
        StringWriter sw = new StringWriter();
        EscapedWriter ew = new EscapedWriter(sw);
        gson.toJson(reqJson, ew);
        writer.write(sw.getBuffer().toString());
        writer.write("\r\n");
        writer.flush();
        String resp = reader.readLine();
        System.out.println(resp);

        Map rawJsonObj = gson.fromJson(resp, Map.class);
        if (rawJsonObj.get("data") instanceof String) {
            return new OcrResponse((int) Double.parseDouble(rawJsonObj.get("code").toString()), rawJsonObj.get("data").toString());
        }

        return gson.fromJson(resp, OcrResponse.class);
    }


    @Override
    public void close() {
        if (p.isAlive()) {
            p.destroy();
        }
    }

}
