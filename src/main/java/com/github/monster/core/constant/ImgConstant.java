package com.github.monster.core.constant;

import java.io.File;

public class ImgConstant {
    private static final String USR_DIR = System.getProperty("user.dir");

    public static final String IMG_PACKAGE = USR_DIR + File.separator + "img" + File.separator;

    public static final String IMG_CACHE = USR_DIR + File.separator + "cache" + File.separator;

    public static final String IMG_TYPE = ".png";
}
