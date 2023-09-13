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
    private int x;
    /**
     * 左上角起点Y坐标
     */
    private int y;
    /**
     * 长度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 目标单词
     */
    private String aimWord;
    /**
     * 模糊词
     */
    private List<String> FuzzyWords;

    public Target(int x, int y, int width, int height, String aimWord, List<String> fuzzyWords) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.aimWord = aimWord;
        FuzzyWords = fuzzyWords;
    }

    public Target(int x, int y, int width, int height, String aimWord) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.aimWord = aimWord;
    }

    public String getCachePath(){
        return ImgConstant.IMG_CACHE + aimWord + ImgConstant.IMG_TYPE;
    }
}
