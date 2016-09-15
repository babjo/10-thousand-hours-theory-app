package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 13..
 */
public class GetAllGoalResponseDTO {
    private final List<GoalEntity> goals;

    public GetAllGoalResponseDTO(List<GoalEntity> goals) {
        this.goals = goals;
    }

    public List<GoalEntity> getGoals() {
        return goals;
    }
}
