package com.three.a10_thousand_hours_theory_app.model.domain;

import java.util.List;

import lombok.Data;

/**
 * Created by LCH on 2016. 9. 19..
 */
@Data
public class SharedGoal {

    private String key;
    private String title;
    private String description;
    private int type;
    private int goalDays;
    private int goalHours;
    private List<TaskRule> taskRules;
    private String userKey;
    private String updatedAt;
    private int like;
    private List<String> likeUserKeys;

    @Data
    public static class TaskRule{
        private String title;
        private int times;
        private int hours;
        private int labelColor;
    }
}
