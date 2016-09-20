package com.three.a10_thousand_hours_theory_app.model.service;

import com.google.firebase.database.DatabaseReference;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.FirebaseDB;

import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 13..
 */

@EBean
public class GetAllSharedGoalAsyncService implements AsyncService<Void> {

    private final DatabaseReference mDatabaseReference;

    public GetAllSharedGoalAsyncService() {
        mDatabaseReference = FirebaseDB.sharedGoals();
    }

    @Override
    public void execute(Void arg, AsyncServiceListener asyncServiceListener) {



    }
}
