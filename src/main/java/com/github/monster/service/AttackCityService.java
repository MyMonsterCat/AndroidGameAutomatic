package com.github.monster.service;

import com.github.monster.core.constant.ImgConstant;
import com.github.monster.core.ocr.core.OcrEntry;
import com.github.monster.core.ocr.OcrUtil;
import com.github.monster.entity.TargetCommon;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

        String imgPath = ImgConstant.IMG_PACKAGE + "home.png";

        OcrEntry mainCity = OcrUtil.cropperAndOcr(imgPath, TargetCommon.MainCity);
        OcrEntry task = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Task);
        if (mainCity!=null&& task!=null){
            log.info("这是首页");
        }else {
            log.info("这不是首页");
        }

    }


    /**
     * 主页判断
     */
    public boolean isHomePage( String imgPath ){
        OcrEntry mainCity = OcrUtil.cropperAndOcr(imgPath, TargetCommon.MainCity);
        OcrEntry task = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Task);
        return mainCity != null && task != null;
    }


}
