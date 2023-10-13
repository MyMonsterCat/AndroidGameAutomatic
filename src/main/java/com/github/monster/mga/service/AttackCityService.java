package com.github.monster.mga.service;

import com.benjaminwan.ocrlibrary.TextBlock;
import com.github.monster.mga.core.constant.ImgConstant;
import com.github.monster.mga.core.entity.Target;
import com.github.monster.mga.core.util.OcrUtil;
import com.github.monster.touch.device.cli.MiniTouchCli;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class AttackCityService {
//    @Resource
//    private MiniTouchCli deviceCli;
//    @Resource
//    private AdbCli adbCli;

    @SneakyThrows
    public void attackCityStatistics() {
        String imgPath = ImgConstant.IMG_PACKAGE + "map.png";
        TypeEnum typeEnum = judgeType(imgPath);
        if (TypeEnum.MAP==typeEnum) {
            // 先截图

            // 划定一个范围进行识别
            // 如果识别到了，找到具体坐标，进行点击
            // 如果没有识别到，进行滑动

        }
    }


    /**
     * 判断当前是什么页面
     *
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
        List<TextBlock> mainCity = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Home_MainCity);
        List<TextBlock> task = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Home_Task);
        return mainCity != null && !mainCity.isEmpty() && task != null && !task.isEmpty();
    }

    /**
     * 地图页判断
     */
    public boolean isMap(String imgPath) {
        List<TextBlock> map = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Map_Map);
        List<TextBlock> all = OcrUtil.cropperAndOcr(imgPath, TargetCommon.Map_All);
        return map != null && !map.isEmpty() && all != null && !all.isEmpty();
    }


}
