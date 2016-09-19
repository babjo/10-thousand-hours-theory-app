package com.three.a10_thousand_hours_theory_app.model.service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.three.a10_thousand_hours_theory_app.Utils;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalRequestDTO;

import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 18..
 */

@EBean(scope = EBean.Scope.Singleton)
public class UploadGoalAsyncService implements AsyncService<UploadGoalRequestDTO> {

    private final DatabaseReference mDatabaseReference;

    public UploadGoalAsyncService(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("goals");
    }

    @Override
    public void execute(UploadGoalRequestDTO arg, AsyncServiceListener asyncServiceListener) {
        GoalEntity goalEntity = arg.getGoalEntity();
        String goalKey = mDatabaseReference.push().getKey();
        mDatabaseReference.child(goalKey).child("title").setValue(goalEntity.getTitle());
        mDatabaseReference.child(goalKey).child("description").setValue(goalEntity.getDescription());
        mDatabaseReference.child(goalKey).child("type").setValue(goalEntity.getType());
        mDatabaseReference.child(goalKey).child("deadLineDate").setValue(Utils.DATE_FORMAT_yyyy_MM_dd.format(goalEntity.getDeadLineDate()));
        mDatabaseReference.child(goalKey).child("goalHours").setValue(goalEntity.getGoalHours());

        for(TaskRuleEntity t : goalEntity.getTaskRules()){
            String taskKey = mDatabaseReference.child(goalKey).child("taskRules").push().getKey();
            mDatabaseReference.child(goalKey).child("taskRules").child(taskKey).child("title").setValue(t.getTitle());
            mDatabaseReference.child(goalKey).child("taskRules").child(taskKey).child("times").setValue(t.getTimes());
            mDatabaseReference.child(goalKey).child("taskRules").child(taskKey).child("hours").setValue(t.getHours());
            mDatabaseReference.child(goalKey).child("taskRules").child(taskKey).child("startDate").setValue(Utils.DATE_FORMAT_yyyy_MM_dd.format(t.getStartDate()));
            mDatabaseReference.child(goalKey).child("taskRules").child(taskKey).child("labelColor").setValue(t.getLabelColor());
        }
    }
}
