package com.three.a10_thousand_hours_theory_app.model.dto;

/**
 * Created by LCH on 2016. 9. 13..
 */
public class GetGoalRequestDTO {

    private int goalId;

    public GetGoalRequestDTO(int goalId) {
        this.goalId = goalId;
    }

    public int getGoalId() {
        return goalId;
    }

}
