package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 11..
 */
public class CreateGoalRequestDTO {

    private GoalEntity goalEntity;
    private int goalId = 0;
    private String title;
    private String description;
    private Date deadLineDate;
    private List<TaskRuleEntity> taskRuleEntities;

    public CreateGoalRequestDTO(){
        taskRuleEntities = new ArrayList();
    }
    public CreateGoalRequestDTO(GoalEntity goalEntity) {
        this.goalEntity = goalEntity;
        this.goalId = goalEntity.getId();
        this.title = goalEntity.getTitle();
        this.description = goalEntity.getDescription();
        this.deadLineDate = goalEntity.getDeadLineDate();
        this.taskRuleEntities = goalEntity.getTaskRules();
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

    public List<TaskRuleEntity> getTaskRuleEntities() {
        return taskRuleEntities;
    }

    public void addTaskRuleEntity(TaskRuleEntity newTaskRule) {
        taskRuleEntities.add(newTaskRule);
    }

    public int getGoalId() {
        return goalId;
    }

    public UpdateGoalRequestDTO convert(){
        goalEntity.setTitle(title);
        goalEntity.setDescription(description);
        goalEntity.setDeadLineDate(deadLineDate);
        return new UpdateGoalRequestDTO(goalEntity, taskRuleEntities);
    }
}
