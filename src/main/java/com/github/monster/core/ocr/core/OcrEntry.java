package com.github.monster.core.ocr.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OcrEntry {
    private String text;
    private int[][] box;
    private double score;
}
