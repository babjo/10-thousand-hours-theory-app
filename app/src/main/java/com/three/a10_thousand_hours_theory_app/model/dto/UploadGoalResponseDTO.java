package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;

/**
 * Created by LCH on 2016. 9. 20..
 */
public class UploadGoalResponseDTO {

    private SharedGoal sharedGoal;

    public UploadGoalResponseDTO(SharedGoal sharedGoal) {
        this.sharedGoal = sharedGoal;
    }

    public SharedGoal getSharedGoal() {
        return sharedGoal;
    }
}
