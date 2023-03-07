package com.monster.adb;

import java.text.MessageFormat;

public class AdbCommand {

    private static final String SWIPE = "input swipe";

    private static final String TAP = "input tap";

    private static final String TEXT = "input text";
    /**
     * 分辨率
     */
    private static final String SIZE = "wm size";
    /**
     * 返回键
     */
    private static final String BACK = "input keyevent 4";

    private static final String FIND_STZB = "pm list packages -3 | grep 'stzb.netease'";


    public static String getSwipe(Integer startX, Integer startY, Integer endX, Integer endY, Integer time) {
        return MessageFormat.format("{0} {1} {2} {3} {4}", SWIPE, startX.toString(), startY.toString(), endX.toString(), endY.toString(), time.toString());
    }

    public static String getSwipe(Integer startX, Integer startY, Integer endX, Integer endY) {
        return MessageFormat.format("{0} {1} {2} {3} {4}", SWIPE, startX.toString(), startY.toString(), endX.toString(), endY.toString());
    }

    public static String getText(String text) {
        return MessageFormat.format("{0} {1}", TEXT, text);
    }

    public static String getTap(Integer x, Integer y) {
        return MessageFormat.format("{0} {1} {2}", TAP, x.toString(), y.toString());
    }

    public static String getSize() {
        return MessageFormat.format("{0}", SIZE);
    }

    public static String getBack() {
        return MessageFormat.format("{0}", BACK);
    }

    public static String getDelete() {
        return MessageFormat.format("{0}", "input keyevent 67");
    }

    public static String getStzb() {
        return MessageFormat.format("{0}", FIND_STZB);
    }


}
