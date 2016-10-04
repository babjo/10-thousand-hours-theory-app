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

import rx.Observable;

/**
 * Created by LCH on 2016. 9. 30..
 */
@EBean
public class GetUserUseCase extends UseCase<GetUserRequestDTO> {

    @Bean
    Requery mRequery;

    private final DatabaseReference mDatabaseReference;

    public GetUserUseCase(){
        mDatabaseReference = FirebaseDB.users();
    }

    @Override
    protected Observable buildUseCaseObservable(GetUserRequestDTO getUserRequestDTO) {
        return Observable.create(subscriber -> {
            int count = mRequery.getData().count(User.class).get().value();
            if (count == 0) {
                UserEntity userEntity = new UserEntity();
                String newKey = mDatabaseReference.push().getKey();
                mDatabaseReference.child(newKey).setValue("");
                userEntity.setKey(newKey);
                userEntity = mRequery.getData().insert(userEntity);
                subscriber.onNext(new GetUserResponseDTO(userEntity));
            } else {
                subscriber.onNext(new GetUserResponseDTO(mRequery.getData().select(UserEntity.class).get().first()));
            }
        });
    }
}
