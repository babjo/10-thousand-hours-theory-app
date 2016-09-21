package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;

/**
 * Created by LCH on 2016. 9. 11..
 */
public class CreateGoalResponseDTO {

    private final GoalEntity newGoalEntity;

    public CreateGoalResponseDTO(GoalEntity newGoalEntity) {
        this.newGoalEntity = newGoalEntity;
    }

    public GoalEntity getNewGoalEntity() {
        return newGoalEntity;
    }
}
