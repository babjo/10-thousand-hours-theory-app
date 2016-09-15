package com.three.a10_thousand_hours_theory_app.model.dto;

/**
 * Created by LCH on 2016. 9. 15..
 */

public class DeleteGoalRequestDTO {
    private int goalId;

    public DeleteGoalRequestDTO(int goalId) {
        this.goalId = goalId;
    }

    public int getGoalId() {
        return goalId;
    }
}
