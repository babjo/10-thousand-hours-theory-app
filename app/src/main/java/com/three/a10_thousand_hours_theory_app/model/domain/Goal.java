package com.three.a10_thousand_hours_theory_app.model.domain;

/**
 * Created by LCH on 2016. 9. 11..
 */
public class Goal {
    private String title;
    private String description;

    public Goal(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
}
