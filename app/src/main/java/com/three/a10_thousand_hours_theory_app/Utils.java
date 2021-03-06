package com.three.a10_thousand_hours_theory_app;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LCH on 2016. 9. 11..
 */
public class Utils {
    public static final SimpleDateFormat DATE_FORMAT_yyyy_MM_dd_hh_mm_ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_yyyy_MM = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat DATE_FORMAT_M = new SimpleDateFormat("M");
    public static final String getMonth(Date date){
        return DATE_FORMAT_M.format(date) + " 월";
    }
    public static final String gethhmmss(int seconds){
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds / 60) % 60, seconds % 60);
    }

    public static final String gethhmmss2(int seconds){
        return String.format("%02d시간 %02d분 %02d초", seconds / 3600, (seconds / 60) % 60, seconds % 60);
    }
}
