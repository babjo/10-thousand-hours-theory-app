package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;

/**
 * Created by LCH on 2016. 9. 18..
 */
public class UploadGoalRequestDTO {

    private final UserEntity userEntity;
    private GoalEntity goalEntity;

    public UploadGoalRequestDTO(UserEntity userEntity, GoalEntity goalEntity) {
        this.userEntity = userEntity;
        this.goalEntity = goalEntity;
    }

    public GoalEntity getGoalEntity() {
        return goalEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}
