package com.three.a10_thousand_hours_theory_app.model.domain;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 19..
 */

public class SharedGoal {

    private String key;
    private String title;
    private String description;
    private int type;
    private String goalDays;
    private int goalHours;
    private List<TaskRule> taskRules;

    private String userKey;
    private String updatedAt;
    private int like;
    private List<String> likeUserKeys;

    public static class TaskRule{
        private String title;
        private int times;
        private int hours;
        private String startDate;
        private int labelColor;

        public TaskRule() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public int getLabelColor() {
            return labelColor;
        }

        public void setLabelColor(int labelColor) {
            this.labelColor = labelColor;
        }
    }

    public SharedGoal() {
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGoalDays() {
        return goalDays;
    }

    public int getType() {
        return type;
    }

    public int getGoalHours() {
        return goalHours;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getLike() {
        return like;
    }

    public List<String> getLikeUserKeys() {
        return likeUserKeys;
    }

    public List<TaskRule> getTaskRules() {
        return taskRules;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setGoalDays(String goalDays) {
        this.goalDays = goalDays;
    }

    public void setGoalHours(int goalHours) {
        this.goalHours = goalHours;
    }

    public void setTaskRules(List<TaskRule> taskRules) {
        this.taskRules = taskRules;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setLikeUserKeys(List<String> likeUserKeys) {
        this.likeUserKeys = likeUserKeys;
    }
}
