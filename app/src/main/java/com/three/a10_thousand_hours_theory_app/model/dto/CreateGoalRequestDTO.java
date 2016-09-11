package com.three.a10_thousand_hours_theory_app.model.dto;

/**
 * Created by LCH on 2016. 9. 11..
 */
public class CreateGoalRequestDTO {
    private String title;
    private String description;

    public CreateGoalRequestDTO() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
