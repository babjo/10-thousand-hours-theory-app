package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.google.firebase.database.DatabaseReference;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateSharedGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateSharedGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.infrastructure.FirebaseDB;

import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by LCH on 2016. 10. 1..
 */

@EBean
public class UpdateSharedGoalUseCase extends UseCase<UpdateSharedGoalRequestDTO> {

    private final DatabaseReference mDatabaseReference;

    public UpdateSharedGoalUseCase(){
        mDatabaseReference = FirebaseDB.sharedGoals();
    }

    @Override
    protected Observable buildUseCaseObservable(UpdateSharedGoalRequestDTO updateSharedGoalRequestDTO) {
        return Observable.create(subscriber -> {
            SharedGoal sharedGoal = updateSharedGoalRequestDTO.getSharedGoal();
            mDatabaseReference.child(sharedGoal.getKey()).setValue(sharedGoal);
            subscriber.onNext(new UpdateSharedGoalResponseDTO());
            subscriber.onCompleted();
        });
    }
}
