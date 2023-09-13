package com.github.monster.core.ocr;

import com.github.monster.core.enums.CoordinateEnum;
import com.github.monster.core.ocr.core.Ocr;
import com.github.monster.core.ocr.core.OcrCode;
import com.github.monster.core.ocr.core.OcrEntry;
import com.github.monster.core.ocr.core.OcrResponse;
import com.github.monster.core.util.ImageUtil;
import com.github.monster.device.cli.DeviceCli;
import com.github.monster.core.entity.Target;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
public class OcrUtil {
    private static final String exePath = "./libs/ocr/PaddleOCR-json.exe";


    /**
     * 截屏、裁剪图片、文字识别、计算坐标、点击坐标
     *
     * @param imgPath        文件路径
     * @param target         目标词汇信息
     * @param coordinateEnum 计算坐标方式
     * @return true/false
     */
    @SneakyThrows
    private boolean shotCropperOcrClick(DeviceCli deviceCli, String imgPath, Target target, CoordinateEnum coordinateEnum) {
        // 截屏
        deviceCli.screenShot(imgPath);
        // 裁剪
        String cityName = ImageUtil.imageCropper(imgPath, null, target.getX(), target.getY(), target.getWidth(), target.getHeight());
        // ocr识别
        OcrEntry findWord = OcrUtil.Ocr(cityName, target);
        if (findWord == null) {
            log.error("未能成功识别关键词:{} , 模糊词:{}", target.getAimWord(), target.getFuzzyWords());
            return false;
        }
        int[][] box = findWord.getBox();
        int aimX = target.getX();
        int aimY = target.getY();
        switch (coordinateEnum) {
            case Center -> {
                int[] calculate = calculateCoordinates(findWord, target.getX(), target.getY());
                aimX = calculate[0];
                aimY = calculate[1];
            }
            case BottomRight -> {
                aimX = target.getX() + box[3][0];
                aimY = target.getY() + box[3][1];
            }
        }
        // 点击目标地点
        deviceCli.touchDown(aimX, aimY);
        deviceCli.touchUp(aimX, aimY);

        return true;
    }

    /**
     * 先裁剪，保存在cache目录，然后识别有没有目标词
     *
     * @param imgPath 图片原始路径
     * @param target  目标词汇对象
     * @return 目标词汇信息
     */
    public static OcrEntry cropperAndOcr(String imgPath, Target target) {
        // 裁剪
        String newPath = ImageUtil.imageCropper(imgPath, target.getCachePath(), target.getX(), target.getY(), target.getWidth(), target.getHeight());
        // OCR识别
        return Ocr(newPath, target);
    }

    /**
     * OCR判断图片中是否有目标词
     *
     * @param imgPath 图片路径
     * @param target  目标词汇对象
     * @return 目标词汇信息
     */
    public static OcrEntry Ocr(String imgPath, Target target) {
        // 可选的配置项
        Map<String, Object> arguments = new HashMap<>();
        // arguments.put("config_path", "models/config_en.txt");识别多国语言，默认中文
        // arguments.put("use_angle_cls", true);启用方向分类，必须与cls值相同，默认关闭
        // arguments.put("cls", true);启用cls方向分类，识别方向不是正朝上的图片，默认关闭
        // arguments.put("enable_mkldnn", false);启用CPU推理加速，关掉可以减少内存占用，但会降低速度，默认开启
        // 初始化 OCR
        try (Ocr ocr = new Ocr(new File(exePath), arguments)) {
            OcrResponse resp = ocr.runOcr(new File(imgPath));
            // 读取结果
            if (resp.getCode() == OcrCode.OK) {
                OcrEntry[] data = resp.getData();
                log.info("OCR --> data:{},chart:{}", data, target);
                for (OcrEntry entry : data) {
                    // 比较精确词
                    if (Objects.equals(entry.getText(), target.getAimWord())) {
                        return entry;
                    }
                    // 比较模糊词
                    List<String> fuzzyWords = target.getFuzzyWords();
                    if (fuzzyWords != null) {
                        for (String fuzzyWord : fuzzyWords) {
                            if (entry.getText().equals(fuzzyWord)) {
                                return entry;
                            }
                        }
                    }
                }
            } else {
                log.error("OCR识别失败: code={},msg={},chart={}", resp.getCode(), resp.getMsg(), target);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("初始化OCR错误: msg={}", e.getMessage());
        }
        return null;
    }

    /**
     * OCR识别图片中的所有词汇
     *
     * @param imgPath 图片路径
     * @return 识别结果数组
     */
    public static OcrEntry[] Ocr(String imgPath) {
        // 可选的配置项
        Map<String, Object> arguments = new HashMap<>();
        // 初始化 OCR
        try (Ocr ocr = new Ocr(new File(exePath), arguments)) {
            OcrResponse resp = ocr.runOcr(new File(imgPath));
            // 读取结果
            if (resp.getCode() == OcrCode.OK) {
                return resp.getData();
            } else {
                log.error("error: code=" + resp.getCode() + " msg=" + resp.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算中心点
     *
     * @param ocrEntry 目标信息
     * @param startX   截图开始坐标X
     * @param startY   截图开始坐标Y
     * @return
     */
    private int[] calculateCoordinates(OcrEntry ocrEntry, int startX, int startY) {

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
