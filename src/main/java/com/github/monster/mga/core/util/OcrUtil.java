package com.github.monster.mga.core.util;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.TextBlock;
import com.github.monster.mga.core.entity.Target;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Monster
 */
@Slf4j
public class OcrUtil {


    /**
     * 先裁剪，保存在cache目录，然后识别有没有目标词
     *
     * @param imgPath 图片原始路径
     * @param target  目标词汇对象
     * @return 目标词汇信息
     */
    public static List<TextBlock> cropperAndOcr(String imgPath, Target target) {
        // 裁剪
        String newPath = ImageUtil.imageCropper(imgPath, target.getCachePath(), target.getStartX(), target.getStartY(), target.getEndX(), target.getEndY());
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
    public static List<TextBlock> Ocr(String imgPath, Target target) {
        ArrayList<TextBlock> result = new ArrayList<>();
        OcrResult ocrResult = com.github.monster.OcrUtil.runOcr(imgPath);
        for (TextBlock textBlock : ocrResult.getTextBlocks()) {
            String text = textBlock.getText();
            // 比较精确词
            if (Objects.equals(text, target.getAimWord())) {
                result.add(textBlock);
            }
            // 比较模糊词
            List<String> fuzzyWords = target.getFuzzyWords();
            if (fuzzyWords != null) {
                for (String fuzzyWord : fuzzyWords) {
                    if (text.equals(fuzzyWord)) {
                        result.add(textBlock);
                    }
                }
            }
        }
        return result;
    }
}
