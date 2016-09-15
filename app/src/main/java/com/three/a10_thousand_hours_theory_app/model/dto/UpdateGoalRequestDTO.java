package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 15..
 */
public class UpdateGoalRequestDTO {
    private final GoalEntity goalEntity;
    private final List<TaskRuleEntity> taskRuleEntities;

    public UpdateGoalRequestDTO(GoalEntity goalEntity, List<TaskRuleEntity> taskRuleEntities) {
        this.goalEntity = goalEntity;
        this.taskRuleEntities = taskRuleEntities;
    }

    public GoalEntity getGoalEntity() {
        return goalEntity;
    }

    public List<TaskRuleEntity> getTaskRuleEntities() {
        return taskRuleEntities;
    }
}
