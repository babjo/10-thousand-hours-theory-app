package com.three.a10_thousand_hours_theory_app.model.domain;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 19..
 */

public class SharedGoal {

    private String key;
    private String title;
    private String description;
    private String startDate;
    private int type;
    private String deadLineDate;
    private int goalHours;

    private List<TaskRule> taskRules;

    public class TaskRule{
        private String key;
        private String title;
        private int times;
        private int hours;
        private String startDate;
        private int labelColor;
    }
}
