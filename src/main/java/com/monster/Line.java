package com.monster;

import com.android.ddmlib.IDevice;
import com.monster.adb.AdbCommand;
import com.monster.adb.AdbHandler;
import com.monster.ocr.CoordinateEnum;
import com.monster.ocr.OcrEntry;
import com.monster.ocr.OcrUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Line {

    //    @Resource
    private AdbHandler adbHandler;

    public static void main(String[] args) {
        AdbHandler adbHandler = new AdbHandler();
        attackCity(adbHandler, 1274, 1133);
        adbHandler.exit();
    }

    /**
     * 攻城
     *
     * @param adbHandler
     * @param x
     * @param y
     */
    @SneakyThrows
    public static void attackCity(AdbHandler adbHandler, int x, int y) {

        for (IDevice iDevice : adbHandler.getDevices()) {
            System.out.println("获取当前设备 " + iDevice);

            // 跳转到建业
//            JumpCity(adbHandler, iDevice, x, y);


            adbHandler.executeCommand(iDevice, "input touchscreen multitouch 0 2 1000 540 960 540 720", "点击中心点");

//            Thread.sleep(1000);


            // 点击城池
//            long nowTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai")).toInstant().getEpochSecond();
//            shotCropperOcrClick(adbHandler, iDevice, nowTime + ".png", "建业", null, 770, 370, 300, 200, CoordinateEnum.BottomRight);

//            adbHandler.executeCommand(iDevice, AdbCommand.getTap(ResolvingPowerConstant.Center_1600_900[0], ResolvingPowerConstant.Center_1600_900[1]), "点击中心点");
//            // 出征
//            String expeditionName = System.currentTimeMillis() + ".png";
//            shotCropperOcrClick(adbHandler, iDevice, expeditionName,"出征", Arrays.asList("出证"),1092, 420, 200, 200,CoordinateEnum.Center);
//
//            // 派出部队
//            String troopsName = System.currentTimeMillis() + ".png";
//            shotCropperOcrClick(adbHandler, iDevice, expeditionName,"出征", Arrays.asList("出证"),1092, 420, 200, 200,CoordinateEnum.Center);
//


//            adbHandler.swipe(iDevice, AdbCommand.getSwipe(44, 52, 721, 500));

            System.out.println("执行完毕，退出程序");

        }

    }

    /**
     * 截屏、裁剪图片、文字识别、计算坐标、点击坐标
     *
     * @param adbHandler
     * @param iDevice        设备
     * @param imgName        文件名称
     * @param aimWord        文字识别目标词
     * @param FuzzyWords     文字识别备用模糊词
     * @param x              裁剪坐标x轴
     * @param y              裁剪坐标y轴
     * @param width          裁剪宽度
     * @param height         裁剪高度
     * @param coordinateEnum 计算坐标方式
     * @return
     */
    private static boolean shotCropperOcrClick(AdbHandler adbHandler, IDevice iDevice, String imgName, String aimWord, List<String> FuzzyWords, int x, int y, int width, int height, CoordinateEnum coordinateEnum) {
        // 截屏
        adbHandler.getScreenShot(iDevice, imgName);

        // 裁剪
        String cityName = adbHandler.ImageCropper(imgName, x, y, width, height);

        // ocr识别
        OcrEntry findWord = OcrUtil.startOcrFindWord(cityName, aimWord, FuzzyWords);

        if (findWord == null) {
            System.out.println("未能成功识别");
            return false;
        }
        int[][] box = findWord.getBox();
        int aimX = x;
        int aimY = y;
        switch (coordinateEnum) {
            case Center -> {
                int[] calculate = calculateCoordinates(findWord, x, y);
                aimX = calculate[0];
                aimY = calculate[1];
            }
            case BottomRight -> {
                aimX = x + box[3][0];
                aimY = y + box[3][1];
            }

        }
        // 点击目标地点
        adbHandler.executeCommand(iDevice, AdbCommand.getTap(aimX, aimY), "点击" + aimWord);
        return true;
    }

    /**
     * 计算中心点
     *
     * @param ocrEntry 目标信息
     * @param startX   截图开始坐标X
     * @param startY   截图开始坐标Y
     * @return
     */
    private static int[] calculateCoordinates(OcrEntry ocrEntry, int startX, int startY) {

        int[][] box = ocrEntry.getBox();
        // 计算目标地点在屏幕上的位置
        int leftTopX = box[0][0];
        int leftTopY = box[0][1];
        int rightTopX = box[1][0];
        int leftBottomY = box[2][1];

        int aimX = startX + (rightTopX - leftTopX);
        int aimY = startY + (leftBottomY - leftTopY);
        return new int[]{aimX, aimY};

    }

    /**
     * 跳转到制定地点
     *
     * @param iDevice
     * @throws InterruptedException
     */
    private static void JumpCity(AdbHandler adbHandler, IDevice iDevice, Integer x, Integer y) throws InterruptedException {
        Thread.sleep(1000);
        // 点击搜索
        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1418, 98), "点击搜索按钮");


        // 点击左边输入 1187 854
        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1187, 854), "点击左侧输入坐标");

        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第1次删除");
        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第2次删除");
        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第3次删除");
        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第4次删除");

        // 去冀州州府 516 1431
        adbHandler.executeCommand(iDevice, AdbCommand.getText(x.toString()), "输入冀州州府x坐标");

        // 需要点击两次，第一次取消输入框
        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1362, 857), "取消输入框");
        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1362, 857), "点击右侧输入坐标");

        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第1次删除");
        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第2次删除");
        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第3次删除");
        adbHandler.executeCommand(iDevice, AdbCommand.getDelete(), "第4次删除");

        // 去冀州州府 516 1431
        adbHandler.executeCommand(iDevice, AdbCommand.getText(y.toString()), "输入冀州州府y坐标");

        // 跳转
        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1481, 852), "取消输入框");
        adbHandler.executeCommand(iDevice, AdbCommand.getTap(1481, 852), "点击跳转按钮");
    }
}
