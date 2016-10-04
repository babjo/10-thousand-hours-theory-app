package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.google.firebase.database.DatabaseReference;
import com.three.a10_thousand_hours_theory_app.model.domain.User;
import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetUserRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetUserResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.FirebaseDB;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 20..
 */

@EBean
public class GetUserSyncUseCase implements SyncUseCase<GetUserRequestDTO, GetUserResponseDTO>{

    @Bean
    Requery mRequery;

    private final DatabaseReference mDatabaseReference;

    public GetUserSyncUseCase(){
        mDatabaseReference = FirebaseDB.users();
    }

    @Override
    public GetUserResponseDTO execute(GetUserRequestDTO arg) {
        int count = mRequery.getData().count(User.class).get().value();
        if (count == 0) {
            UserEntity userEntity = new UserEntity();
            String newKey = mDatabaseReference.push().getKey();
            mDatabaseReference.child(newKey).setValue("");
            userEntity.setKey(newKey);
            userEntity = mRequery.getData().insert(userEntity);
            return new GetUserResponseDTO(userEntity);
        }
        else return new GetUserResponseDTO(mRequery.getData().select(UserEntity.class).get().first());
    }
}
