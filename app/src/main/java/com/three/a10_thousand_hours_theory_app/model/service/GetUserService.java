package com.three.a10_thousand_hours_theory_app.model.service;

import com.three.a10_thousand_hours_theory_app.model.domain.User;
import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetUserResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 20..
 */

@EBean
public class GetUserService implements Service<Void, GetUserResponseDTO>{

    @Bean
    Requery mRrequery;

    @Override
    public GetUserResponseDTO execute(Void arg) {
        int count = mRrequery.getData().count(User.class).get().value();
        if (count == 0) return new GetUserResponseDTO(null);
        else return new GetUserResponseDTO(mRrequery.getData().select(UserEntity.class).get().first());
    }
}
