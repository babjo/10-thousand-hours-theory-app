package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.google.firebase.database.DatabaseReference;
import com.three.a10_thousand_hours_theory_app.model.domain.UserEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateUserRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateUserResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.FirebaseDB;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by LCH on 2016. 9. 30..
 */
@EBean(scope = EBean.Scope.Singleton)
public class CreateUserUseCase extends UseCase<CreateUserRequestDTO> {

    @Bean
    Requery mRequery;

    private final DatabaseReference mDatabaseReference;

    public CreateUserUseCase(){
        mDatabaseReference = FirebaseDB.users();
    }

    @Override
    protected Observable buildUseCaseObservable(CreateUserRequestDTO createUserRequestDTO) {
        return Observable.create(subscriber -> {
            UserEntity userEntity = new UserEntity();
            String newKey = mDatabaseReference.push().getKey();
            mDatabaseReference.child(newKey).setValue("");
            userEntity.setKey(newKey);
            userEntity = mRequery.getData().insert(userEntity);
            subscriber.onNext(new CreateUserResponseDTO(userEntity));
            subscriber.onCompleted();
        });
    }
}
