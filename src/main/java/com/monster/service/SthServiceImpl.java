package com.monster.service;


import com.github.monster.device.cli.AdbCli;
import com.github.monster.device.cli.DeviceCli;
import com.monster.ocr.CoordinateEnum;
import com.monster.ocr.OcrEntry;
import com.monster.ocr.OcrUtil;
import com.monster.util.ImageUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SthServiceImpl implements ISthService {
    @Resource
    private DeviceCli deviceCli;
    @Resource
    private AdbCli adbCli;

    @SneakyThrows
    @Override
    public void attackCity(int x, int y) {
//        deviceCli.touchDown(x, y);
//        deviceCli.touchUp(x, y);
        jumpAddress(x, y);
//        adbCli.swipe(100,150,1300,700,1000);

        System.out.println("被调用啦" + x + "," + y);
    }

    @SneakyThrows
    public void jumpAddress(int x, int y) {
        // 点击搜索
        deviceCli.touchDown(1418, 98);
        deviceCli.touchUp(1418, 98);
        Thread.sleep(500);
        // 点击左侧输入坐标
        deviceCli.touchDown(1187, 854);
        deviceCli.touchUp(1187, 854);
        Thread.sleep(500);
        // 按四下删除键，每次按下需要停止一会
        for (int i = 0; i < 4; i++) {
            adbCli.sendKeyEvent(4);
            Thread.sleep(500);
        }
        //输入x坐标
        adbCli.sendText(String.valueOf(x));
        Thread.sleep(500);
        // 点击两次，第一次取消输入框，第二次点击右侧输入坐标
        deviceCli.touchDown(1362, 857);
        deviceCli.touchUp(1362, 857);

        deviceCli.touchDown(1362, 857);
        deviceCli.touchUp(1362, 857);
        // 按四下删除键，每次按下需要停止一会
        for (int i = 0; i < 4; i++) {
            adbCli.sendKeyEvent(4);
            Thread.sleep(500);
        }
        //输入y坐标
        adbCli.sendText(String.valueOf(y));
        Thread.sleep(500);
        // 点击两次，第一次取消输入框，第二次点击跳转按钮
        deviceCli.touchDown(1481, 852);
        deviceCli.touchUp(1481, 852);
    }

    /**
     * 截屏、裁剪图片、文字识别、计算坐标、点击坐标
     *
     * @param imgPath        文件路径
     * @param aimWord        文字识别目标词
     * @param FuzzyWords     文字识别备用模糊词
     * @param x              裁剪坐标x轴
     * @param y              裁剪坐标y轴
     * @param width          裁剪宽度
     * @param height         裁剪高度
     * @param coordinateEnum 计算坐标方式
     * @return
     */
    @SneakyThrows
    private boolean shotCropperOcrClick(String imgPath, String aimWord, List<String> FuzzyWords, int x, int y, int width, int height, CoordinateEnum coordinateEnum) {
        // 截屏
        deviceCli.screenShot(imgPath);
        // 裁剪
        String cityName = ImageUtil.imageCropper(imgPath, x, y, width, height);
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
        deviceCli.touchDown(aimX, aimY);
        deviceCli.touchUp(aimX, aimY);

        deviceCli.touchDown(x, y);
        deviceCli.touchUp(x, y);
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
}
