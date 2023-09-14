package com.github.monster.core.entity;

import com.github.monster.core.constant.ImgConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 识别目标实体
 */
@Getter
@Setter
@ToString
public class Target {

    /**
     * 左上角起点X坐标
     */
    private int startX;
    /**
     * 左上角起点Y坐标
     */
    private int startY;
    /**
     * 右下角X坐标
     */
    private int endX;
    /**
     * 右下角Y坐标
     */
    private int endY;
    /**
     * 目标单词
     */
    private String aimWord;
    /**
     * 模糊词
     */
    private List<String> FuzzyWords;

    public Target(int startX, int startY, int endX, int endY, String aimWord) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.aimWord = aimWord;
    }

    public Target(int startX, int startY, int endX, int endY, String aimWord, List<String> fuzzyWords) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.aimWord = aimWord;
        FuzzyWords = fuzzyWords;
    }

    public String getCachePath(){
        return ImgConstant.IMG_CACHE + aimWord + ImgConstant.IMG_TYPE;
    }
}
