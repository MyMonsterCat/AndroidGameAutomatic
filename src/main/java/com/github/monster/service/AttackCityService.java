package com.github.monster.service;

import com.github.monster.core.constant.ImgConstant;
import com.github.monster.core.entity.Target;
import com.github.monster.core.ocr.core.OcrEntry;
import com.github.monster.core.ocr.OcrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        // 开始OCR识别
//        String imgPath = ImgConstant.IMG_PACKAGE + System.currentTimeMillis() + ".png";
//        log.debug("图片路径为{}", imgPath);
//        OcrClick(imgPath, 10, 160, 1000, 700);

        String imgPath = ImgConstant.IMG_CACHE + "40.png";
        Target f40 = new Target(0, 0, 115, 95, "40");
        List<OcrEntry> ocrEntries = OcrUtil.cropperAndOcr(imgPath, f40);
        System.out.println(ocrEntries);

//        TypeEnum typeEnum = judgeType(imgPath);
//        System.out.println(typeEnum);
    }


    /**
     * 判断当前是什么页面
     * @param imgPath 图片地址
     */
    public TypeEnum judgeType(String imgPath) {
        if (isHomePage(imgPath)) {
            return TypeEnum.HOME;
        }
        if (isMap(imgPath)) {
            return TypeEnum.MAP;
        }
        return TypeEnum.NONE;
    }


    /**
     * 主页判断
     */
    public boolean isHomePage(String imgPath) {
        List<OcrEntry> mainCity = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Home_MainCity);
        List<OcrEntry> task = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Home_Task);
        return mainCity != null && task != null;
    }
    /**
     * 地图页判断
     */
    public boolean isMap(String imgPath) {
        List<OcrEntry> map = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Map_Map);
        List<OcrEntry> all = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Map_All);
        return map != null && all != null;
    }


}
