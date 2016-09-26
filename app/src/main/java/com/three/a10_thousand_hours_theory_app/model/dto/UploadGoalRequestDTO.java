package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by LCH on 2016. 9. 18..
 */
@AllArgsConstructor
@Getter
public class UploadGoalRequestDTO {
    private final UserEntity userEntity;
    private final GoalEntity goalEntity;
}
