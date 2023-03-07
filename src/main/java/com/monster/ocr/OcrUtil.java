package com.monster.ocr;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OcrUtil {
    private static final String exePath = "E:\\IDEA\\sourcecode\\STH\\src\\main\\resources\\ocr\\PaddleOCR_json.exe";

    public static OcrEntry startOcrFindWord(String imgPath, String aimWord, List<String> FuzzyWords) {
        // 可选的配置项
        Map<String, Object> arguments = new HashMap<>();
        // arguments.put("use_angle_cls", true);

        // 初始化 OCR
        try (Ocr ocr = new Ocr(new File(exePath), arguments)) {
            OcrResponse resp = ocr.runOcr(new File(imgPath));
            // 读取结果
            if (resp.code == OcrCode.OK) {
                for (OcrEntry entry : resp.data) {

                    System.out.println(entry.text);

                    if (entry.text.equals(aimWord)) {
                        return entry;
                    }
                    if (FuzzyWords != null) {
                        for (String fuzzyWord : FuzzyWords) {
                            if (entry.text.equals(fuzzyWord)) {
                                return entry;
                            }
                        }
                    }
                }
            } else {
                System.out.println("error: code=" + resp.code + " msg=" + resp.msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}