package com.three.a10_thousand_hours_theory_app.view.firbase_listener;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by LCH on 2016. 9. 25..
 */

public abstract class AbstractSharedGoalsValueEventListener implements ValueEventListener {

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.e("Count " ,""+dataSnapshot.getChildrenCount());
        List<SharedGoal> sharedGoals = new ArrayList();
        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
            Log.d(TAG, "loadSharedGoal:onDataChange Value ==> "+childSnapshot.getKey());
            SharedGoal sharedGoal = childSnapshot.getValue(SharedGoal.class);
            Log.d(TAG, "loadSharedGoal:onDataChange Value ==> "+sharedGoal.getTitle());
            sharedGoals.add(sharedGoal);
        }
        onDataChange(sharedGoals);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(TAG, "loadSharedGoal:onCancelled", databaseError.toException());
    }

    public abstract void onDataChange(List<SharedGoal> sharedGoals);
}
