package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by LCH on 2016. 9. 11..
 */
@AllArgsConstructor
@Getter
public class CreateGoalResponseDTO {
    private final GoalEntity newGoalEntity;
}
