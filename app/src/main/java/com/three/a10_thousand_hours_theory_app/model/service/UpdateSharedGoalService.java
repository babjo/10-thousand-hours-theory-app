package com.three.a10_thousand_hours_theory_app.model.service;

import com.google.firebase.database.DatabaseReference;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateSharedGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateSharedGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.FirebaseDB;

import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 20..
 */

@EBean
public class UpdateSharedGoalService implements Service<UpdateSharedGoalRequestDTO, UpdateSharedGoalResponseDTO> {

    private final DatabaseReference mDatabaseReference;

    public UpdateSharedGoalService(){
        mDatabaseReference = FirebaseDB.sharedGoals();
    }

    @Override
    public UpdateSharedGoalResponseDTO execute(UpdateSharedGoalRequestDTO arg) {
        SharedGoal sharedGoal = arg.getSharedGoal();
        mDatabaseReference.child(sharedGoal.getKey()).setValue(sharedGoal);
        return new UpdateSharedGoalResponseDTO();
    }
}
