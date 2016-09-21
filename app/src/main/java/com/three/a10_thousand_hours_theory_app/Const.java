package com.three.a10_thousand_hours_theory_app;

import android.graphics.Color;

/**
 * Created by LCH on 2016. 9. 15..
 */

public class Const {
    public static final Integer[] LABEL_COLORS = new Integer[]{
            Color.parseColor("#F44336"), // Red
            Color.parseColor("#E91E63"), // Pink
            Color.parseColor("#9C27B0"), // Purple
            Color.parseColor("#673AB7"), // Deep Purple
            Color.parseColor("#3F51B5"), // Indigo
            Color.parseColor("#2196F3"), // Blue
            Color.parseColor("#03A9F4"), // Light Blue
            Color.parseColor("#00BCD4"), // Cyan
            Color.parseColor("#009688"), // Teal
            Color.parseColor("#4CAF50"), // Green
            Color.parseColor("#8BC34A"), // Light Green
            Color.parseColor("#CDDC39"), // Lime
            Color.parseColor("#FFEB3B"), // Yellow
            Color.parseColor("#FFC107"), // Amber
            Color.parseColor("#FF9800"), // Orange
            Color.parseColor("#FF5722"), // Deep Orange
            Color.parseColor("#795548"), // Brown
            Color.parseColor("#9E9E9E"), // Grey
            Color.parseColor("#607D8B") // Blue Grey
    };
    public static final String INTENT_EXTRA_GOAL_ID = "GOAL_ID";
    public static final String INTENT_ACTION_CREATE_NEW_TASKS = "android.intent.action.CREATE_NEW_TASKS";

    public static final int GOAL_TYPE_DEADLINE = 1;
    public static final int GOAL_TYPE_HOURS = 2;
    public static final String 시간으로_설정할래요 = "시간으로 설정할래요.";
    public static final String 날짜로_설정할래요 = "날짜로 설정할래요.";
    public static final String INTENT_EXTRA_TASK_ID = "INTENT_EXTRA_TASK_ID";
    public static final String 약_D시간_예상 = "약 %d시간 예상";
    public static final String 약_D일_예상 = "약 %d일 예상";
}
