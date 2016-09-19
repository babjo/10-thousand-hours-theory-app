package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;

/**
 * Created by LCH on 2016. 9. 18..
 */
public class UploadGoalRequestDTO {

    private GoalEntity goalEntity;

    public UploadGoalRequestDTO(GoalEntity goalEntity) {
        this.goalEntity = goalEntity;
    }

    public GoalEntity getGoalEntity() {
        return goalEntity;
    }
}
