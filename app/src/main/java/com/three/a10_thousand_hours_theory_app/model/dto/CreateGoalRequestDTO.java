package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 11..
 */
public class CreateGoalRequestDTO {
    private String title;
    private String description;
    private Date deadLineDate;

    private List<TaskEntity> tasks;

    public CreateGoalRequestDTO() {
        tasks = new ArrayList();
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

    public void setDeadLineDate(Date deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public Date getDeadLineDate() {
        return deadLineDate;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void addTask(TaskEntity newTask) {
        tasks.add(newTask);
    }

}
