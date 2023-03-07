package com.monster.adb;


import com.android.ddmlib.*;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AdbHandler {

    private static AndroidDebugBridge adb = null;
    private static final String ADB_PATH = "C:\\Users\\Monster\\Desktop\\platform-tools\\adb.exe";

    private static final String IMG_PATH = "C:\\Users\\Monster\\Desktop\\t\\";
    private static final String IMG_CROPPER_PATH = "C:\\Users\\Monster\\Desktop\\t\\cropper\\";
    private static boolean isInit = false;

    public AdbHandler() {
        init();
    }

    public void init() {
        if (!isInit) {
            AndroidDebugBridge.init(false);
            adb = AndroidDebugBridge.createBridge(ADB_PATH, false);
            isInit = true;
        }
        waitForDevice(adb);
    }

    /**
     * 滑动
     *
     * @param device
     * @param command
     */
    public String executeCommand(IDevice device, String command, String logInfo) {
        StringBuilder builder = new StringBuilder();

        try {
            System.out.println("OpsLog   >>>>>>    " + logInfo);
            device.executeShellCommand(command, new MultiLineReceiver() {
                @Override
                public void processNewLines(String[] strings) {
                    for (String data : strings) {
                        System.out.println("MultiLineReceiver: " + data);
                        builder.append(data + System.getProperty("line.separator"));
                    }
                }

                @Override
                public boolean isCancelled() {
                    return false;
                }
            });

            Thread.sleep(500);

        } catch (TimeoutException | AdbCommandRejectedException | ShellCommandUnresponsiveException | IOException |
                 InterruptedException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * 获取截图，并保存在制定位置
     *
     * @param device  模拟器
     * @param imgName xx.png
     * @return
     */
    @SneakyThrows
    public boolean getScreenShot(IDevice device, String imgName) {

        RawImage rawScreen = device.getScreenshot();
        BufferedImage bufferedImage = rawScreen.asBufferedImage();
        boolean result = ImageIO.write(bufferedImage, "PNG", new File(IMG_PATH + imgName));
        // 压缩图片
//            PngCompressor.compress(new File(filepath),new File("C:\\Users\\Monster\\Desktop\\111.png"));
        return result;
    }

    /**
     * 图像裁剪
     *
     * @param imgName
     * @param x       开始坐标X
     * @param y       开始坐标Y
     * @param width   长度
     * @param height  高度
     * @return
     */
    @SneakyThrows
    public String ImageCropper(String imgName, Integer x, Integer y, Integer width, Integer height) {
        // 读取原始图像
        BufferedImage originalImage = ImageIO.read(new File(IMG_PATH + imgName));

        // 裁剪图像
//        int x = 100;
//        int y = 100;
//        int width = 200;
//        int height = 200;
        BufferedImage croppedImage = originalImage.getSubimage(x, y, width, height);

        // 保存裁剪后的图像
        File output = new File(IMG_CROPPER_PATH + imgName);
        ImageIO.write(croppedImage, "png", output);
        return IMG_CROPPER_PATH + imgName;
    }

    /**
     * 获取指定序列号的设备
     *
     * @param serialNum
     * @return
     */
    public IDevice getDevice(String serialNum) {
        IDevice[] devices = adb.getDevices();
        for (IDevice device : devices) {
            if (serialNum.equals(device.getSerialNumber())) {
                return device;
            }
        }
        return null;
    }

    /**
     * 获取所有设备
     *
     * @return
     */
    public IDevice[] getDevices() {
        return adb.getDevices();
    }

    /**
     * 获取adb版本
     *
     * @return
     */
    public AdbVersion getAdbVersion() {
        File adbVer = new File(ADB_PATH);
        ListenableFuture<AdbVersion> future = AndroidDebugBridge.getAdbVersion(adbVer);
        AdbVersion version = null;
        try {
            version = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | java.util.concurrent.TimeoutException e) {
            e.printStackTrace();
        }
        return version;
    }

    public void exit() {
        // 关闭ADB连接
        AndroidDebugBridge.disconnectBridge();
        AndroidDebugBridge.terminate();
    }

    /**
     * 设置等待时间，直到获取到设备信息。
     * 等待超过0.3秒，抛出异常
     */
    private static void waitForDevice(AndroidDebugBridge bridge) {
        int count = 0;
        while (!bridge.hasInitialDeviceList()) {
            try {
                Thread.sleep(100);
                count++;
            } catch (InterruptedException ignored) {
            }
            if (count > 300) {
                System.out.println("Time out");
                break;
            }
        }
    }


}
