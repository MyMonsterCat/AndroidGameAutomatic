package com.github.monster.core.ocr.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class OcrEntry {
    String text;
    int[][] box;
    double score;

    @Override
    public String toString() {
        return "RecognizedText{" +
                "text='" + text + '\'' +
                ", box=" + Arrays.toString(box) +
                ", score=" + score +
                '}';
    }
}
