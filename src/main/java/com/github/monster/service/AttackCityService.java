package com.github.monster.service;


import com.github.monster.core.constant.ImgConstant;
import com.github.monster.core.enums.CoordinateEnum;
import com.github.monster.core.ocr.core.OcrResponse;
import com.github.monster.core.util.ImageUtil;
import com.github.monster.device.cli.AdbCli;
import com.github.monster.device.cli.DeviceCli;
import com.github.monster.core.ocr.core.OcrEntry;
import com.github.monster.core.ocr.OcrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class AttackCityService {
//    @Resource
//    private DeviceCli deviceCli;
//    @Resource
//    private AdbCli adbCli;

    @SneakyThrows
    public void attackCityStatistics() {
        // 战报
//        deviceCli.touchDown(58, 641);
//        deviceCli.touchUp(58, 641);
//        Thread.sleep(1000);
//        // 同盟日志
//        deviceCli.touchDown(1443, 811);
//        deviceCli.touchUp(1444, 811);
//        Thread.sleep(1000);
//        // 攻城
//        deviceCli.touchDown(295, 130);
//        deviceCli.touchUp(295, 130);
//        Thread.sleep(1000);
        // 开始OCR识别
//        String imgPath = ImgConstant.IMG_PACKAGE + System.currentTimeMillis() + ".png";
//        log.debug("图片路径为{}", imgPath);
//        OcrClick(imgPath, 10, 160, 1000, 700);

        String imgPath = System.getProperty("user.dir") + "/simg/home.png";
        String rePath = System.getProperty("user.dir") + "/rimg/home.png";
        Ocr(imgPath, rePath, 144, 976, 120, 35);

        // 指定某个区域，如果识别到就点击这个词所在的坐标
//        shotCropperOcrClick(System.currentTimeMillis() + ".png", "建业", null, 770, 370, 300, 200, CoordinateEnum.BottomRight);

    }

    @SneakyThrows
    private void Ocr(String imgPath, String rePath, int x, int y, int width, int height) {
        // 裁剪
        String cityName = ImageUtil.imageCropper(imgPath, rePath, x, y, width, height);
        // ocr识别
        OcrResponse ocrResponse = OcrUtil.startOcrAllWord(cityName);
        OcrEntry[] data = ocrResponse.getData();
        for (OcrEntry entry : data) {
            log.debug("{}", entry.getText());
        }
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
    private void shotCropperOcrClick(String imgPath, String aimWord, List<String> FuzzyWords, int x, int y, int width, int height, CoordinateEnum coordinateEnum) {
//        // 截屏
//        deviceCli.screenShot(imgPath);
//        // 裁剪
//        String cityName = ImageUtil.imageCropper(imgPath, x, y, width, height);
//        // ocr识别
//        OcrEntry findWord = OcrUtil.startOcrFindWord(cityName, aimWord, FuzzyWords);
//        if (findWord == null) {
//            log.error("未能成功识别关键词:{} , 模糊词:{}", aimWord, FuzzyWords);
//            return;
//        }
//        int[][] box = findWord.getBox();
//        int aimX = x;
//        int aimY = y;
//        switch (coordinateEnum) {
//            case Center -> {
//                int[] calculate = calculateCoordinates(findWord, x, y);
//                aimX = calculate[0];
//                aimY = calculate[1];
//            }
//            case BottomRight -> {
//                aimX = x + box[3][0];
//                aimY = y + box[3][1];
//            }
//        }
//        // 点击目标地点
//        deviceCli.touchDown(aimX, aimY);
//        deviceCli.touchUp(aimX, aimY);
//
//        deviceCli.touchDown(x, y);
//        deviceCli.touchUp(x, y);
    }


    @SneakyThrows
    private void OcrClick(String imgPath, int x, int y, int width, int height) {
//        // 截屏
//        deviceCli.screenShot(imgPath);
//        // 裁剪
//        String cityName = ImageUtil.imageCropper(imgPath, x, y, width, height);
//        // ocr识别
//        OcrResponse ocrResponse = OcrUtil.startOcrAllWord(cityName);
//        OcrEntry[] data = ocrResponse.getData();
//        for (OcrEntry entry : data) {
//            log.debug("{}", entry.getText());
//        }

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
