package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;

/**
 * Created by LCH on 2016. 9. 20..
 */
public class GetUserResponseDTO {
    private UserEntity userEntity;

    public GetUserResponseDTO(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}
