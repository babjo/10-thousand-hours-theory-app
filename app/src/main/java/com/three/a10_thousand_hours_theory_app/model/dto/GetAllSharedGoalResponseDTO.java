package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by LCH on 2016. 9. 20..
 */

@AllArgsConstructor
@Getter
public class GetAllSharedGoalResponseDTO {
    private final List<SharedGoal> sharedGoals;
}
