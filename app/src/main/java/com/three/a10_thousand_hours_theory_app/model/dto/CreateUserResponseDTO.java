package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by LCH on 2016. 9. 20..
 */
@AllArgsConstructor
@Getter
public class CreateUserResponseDTO {
    private final UserEntity userEntity;
}
