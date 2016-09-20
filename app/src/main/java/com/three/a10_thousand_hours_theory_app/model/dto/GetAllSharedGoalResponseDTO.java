package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 20..
 */
public class GetAllSharedGoalResponseDTO {
    private final List<SharedGoal> sharedGoals;

    public GetAllSharedGoalResponseDTO(List<SharedGoal> sharedGoals) {
        this.sharedGoals = sharedGoals;
    }
    public List<SharedGoal> getSharedGoals() {
        return sharedGoals;
    }
}
