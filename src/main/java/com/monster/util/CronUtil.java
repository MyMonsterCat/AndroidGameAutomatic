package com.monster.util;

import cn.hutool.core.date.DateTime;

import java.util.Calendar;

/**
 * @author Monster
 * @date 2023/3/16 14:33
 */
public class CronUtil {

    /**
     * 将日期转换为Cron表达式
     *
     * @param dateTime
     * @return
     */
    public static String ToCron(DateTime dateTime) {
        Calendar calendar = dateTime.toCalendar();
        // 将日期转换为Cron表达式
        String cronExpression = String.format("0 %d %d %d %d ? %d",
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
        return cronExpression;
    }
}
