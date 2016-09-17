package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.Const;
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

    private int goalType;
    private Date deadLineDate;
    private int goalHours;

    private String title;
    private String description;
    private List<TaskRuleEntity> taskRuleEntities;

    public CreateGoalRequestDTO(){
        goalType = Const.GOAL_TYPE_DEADLINE;
        taskRuleEntities = new ArrayList();
    }
    public CreateGoalRequestDTO(GoalEntity goalEntity) {
        this.goalEntity = goalEntity;
        this.goalType = goalEntity.getType();
        this.deadLineDate = goalEntity.getDeadLineDate();
        this.goalHours = goalEntity.getGoalHours();
        this.goalId = goalEntity.getId();
        this.title = goalEntity.getTitle();
        this.description = goalEntity.getDescription();
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

    public int getGoalType() {
        return goalType;
    }

    public void setGoalType(int goalType) {
        this.goalType = goalType;
    }

    public UpdateGoalRequestDTO convert(){
        goalEntity.setTitle(title);
        goalEntity.setDescription(description);
        goalEntity.setDeadLineDate(deadLineDate);
        goalEntity.setType(goalType);
        goalEntity.setGoalHours(goalHours);
        return new UpdateGoalRequestDTO(goalEntity, taskRuleEntities);
    }

    public int getGoalHours() {
        return goalHours;
    }

    public void setGoalHours(int goalHours) {
        this.goalHours = goalHours;
    }
}
