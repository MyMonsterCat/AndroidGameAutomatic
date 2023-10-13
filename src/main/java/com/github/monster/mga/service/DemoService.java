package com.github.monster.mga.service;

import com.benjaminwan.ocrlibrary.TextBlock;
import com.github.monster.mga.core.constant.ImgConstant;
import com.github.monster.mga.core.entity.Target;
import com.github.monster.mga.core.util.OcrUtil;
import com.github.monster.touch.device.cli.MiniTouchCli;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jpedal.parser.shape.S;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class DemoService {
    @Resource
    private MiniTouchCli deviceCli;

    @SneakyThrows
    public void demo() {
        String imgPath = ImgConstant.IMG_PACKAGE + "map.png";
        Target map = new Target(247, 12, 347, 64, "地图");
        List<TextBlock> ocrResult = OcrUtil.cropperAndOcr(imgPath, map);
        log.info(ocrResult.toString());

        deviceCli.swipe(0, 0, 400, 400, 500);
    }


}
