package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;

/**
 * Created by LCH on 2016. 9. 13..
 */
public class GetGoalResponseDTO {
    private final GoalEntity goalEntity;
    public GetGoalResponseDTO(GoalEntity g) {
        this.goalEntity = g;
    }
    public GoalEntity getGoalEntity() {
        return goalEntity;
    }
}
