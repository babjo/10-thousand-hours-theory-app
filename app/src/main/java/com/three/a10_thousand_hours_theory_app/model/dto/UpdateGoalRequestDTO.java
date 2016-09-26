package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;

import lombok.Getter;

/**
 * Created by LCH on 2016. 9. 15..
 */

@Getter
public class UpdateGoalRequestDTO {
    private final GoalEntity goalEntity;

    public UpdateGoalRequestDTO(GoalEntity goalEntity) {
        this.goalEntity = goalEntity;
        if(goalEntity.getType() == Const.GOAL_TYPE_HOURS)
            goalEntity.setDeadLineDate(null);
        else
            goalEntity.setGoalHours(0);
    }
}
