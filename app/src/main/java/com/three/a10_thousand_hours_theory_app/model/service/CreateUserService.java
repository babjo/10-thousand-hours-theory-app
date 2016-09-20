package com.three.a10_thousand_hours_theory_app.model.service;

import com.google.firebase.database.DatabaseReference;
import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateUserRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateUserResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.FirebaseDB;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 20..
 */

@EBean(scope = EBean.Scope.Singleton)
public class CreateUserService implements Service<CreateUserRequestDTO, CreateUserResponseDTO> {

    @Bean
    Requery mRequery;

    private final DatabaseReference mDatabaseReference;


    public CreateUserService(){
        mDatabaseReference = FirebaseDB.users();
    }

    @Override
    public CreateUserResponseDTO execute(CreateUserRequestDTO arg) {
        UserEntity userEntity = new UserEntity();
        String newKey = mDatabaseReference.push().getKey();
        mDatabaseReference.child(newKey).setValue("");
        userEntity.setKey(newKey);
        userEntity = mRequery.getData().insert(userEntity);
        return new CreateUserResponseDTO(userEntity);
    }
}
