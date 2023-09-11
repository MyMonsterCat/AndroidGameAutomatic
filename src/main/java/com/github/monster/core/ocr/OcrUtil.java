package com.github.monster.core.ocr;

import com.github.monster.core.ocr.core.Ocr;
import com.github.monster.core.ocr.core.OcrCode;
import com.github.monster.core.ocr.core.OcrEntry;
import com.github.monster.core.ocr.core.OcrResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class OcrUtil {
    private static final String exePath = "./libs/ocr/PaddleOCR-json.exe";

    public static OcrEntry startOcrFindWord(String imgPath, String aimWord, List<String> FuzzyWords) {
        // 可选的配置项
        Map<String, Object> arguments = new HashMap<>();
        // arguments.put("use_angle_cls", true);

        // 初始化 OCR
        try (Ocr ocr = new Ocr(new File(exePath), arguments)) {
            OcrResponse resp = ocr.runOcr(new File(imgPath));
            // 读取结果
            if (resp.getCode() == OcrCode.OK) {
                for (OcrEntry entry : resp.getData()) {

                    log.info("识别到: {}", entry.getText());

                    if (entry.getText().equals(aimWord)) {
                        return entry;
                    }
                    if (FuzzyWords != null) {
                        for (String fuzzyWord : FuzzyWords) {
                            if (entry.getText().equals(fuzzyWord)) {
                                return entry;
                            }
                        }
                    }
                }
            } else {
                log.info("error: code=" + resp.getCode() + " msg=" + resp.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static OcrResponse startOcrAllWord(String imgPath) {
        // 可选的配置项
        Map<String, Object> arguments = new HashMap<>();

        // 初始化 OCR
        try (Ocr ocr = new Ocr(new File(exePath), arguments)) {
            OcrResponse resp = ocr.runOcr(new File(imgPath));
            // 读取结果
            if (resp.getCode() == OcrCode.OK) {
                return resp;
            } else {
                log.info("error: code=" + resp.getCode() + " msg=" + resp.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
