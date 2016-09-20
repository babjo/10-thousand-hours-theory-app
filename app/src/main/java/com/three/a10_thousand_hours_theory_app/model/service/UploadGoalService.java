package com.three.a10_thousand_hours_theory_app.model.service;

import com.google.firebase.database.DatabaseReference;
import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.Utils;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRule;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.FirebaseDB;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 18..
 */

@EBean(scope = EBean.Scope.Singleton)
public class UploadGoalService implements Service<UploadGoalRequestDTO, UploadGoalResponseDTO> {

    private final DatabaseReference mDatabaseReference;

    public UploadGoalService(){
        mDatabaseReference = FirebaseDB.sharedGoals();
    }

    @Override
    public UploadGoalResponseDTO execute(UploadGoalRequestDTO arg) {
        GoalEntity goalEntity = arg.getGoalEntity();
        SharedGoal sharedGoal = new SharedGoal();

        String sharedGoalKey = mDatabaseReference.push().getKey();
        sharedGoal.setKey(sharedGoalKey);
        sharedGoal.setTitle(goalEntity.getTitle());
        sharedGoal.setDescription(goalEntity.getDescription());
        sharedGoal.setType(goalEntity.getType());
        if(sharedGoal.getType() == Const.GOAL_TYPE_HOURS)
            sharedGoal.setGoalHours(goalEntity.getGoalHours());
        else {
            long diff = goalEntity.getDeadLineDate().getTime() - goalEntity.getStartDate().getTime();
            int goalDays = (int) (diff / (24 * 60 * 60 * 1000));
            sharedGoal.setGoalDays(Integer.toString(goalDays));
        }
        sharedGoal.setLike(0);
        sharedGoal.setLikeUserKeys(new ArrayList());

        List<SharedGoal.TaskRule> taskRules = new ArrayList();
        for (TaskRule r : goalEntity.getTaskRules()){
            SharedGoal.TaskRule t = new SharedGoal.TaskRule();
            t.setHours(r.getHours());
            t.setLabelColor(r.getLabelColor());
            t.setStartDate(Utils.DATE_FORMAT_yyyy_MM_dd.format(r.getStartDate()));
            t.setTimes(r.getTimes());
            t.setTitle(r.getTitle());
            taskRules.add(t);
        }
        sharedGoal.setTaskRules(taskRules);
        sharedGoal.setUserKey(arg.getUserEntity().getKey());
        sharedGoal.setUpdatedAt(Utils.DATE_FORMAT_yyyy_MM_dd_hh_mm_ss.format(new Date()));
        mDatabaseReference.child(sharedGoalKey).setValue(sharedGoal);
        return new UploadGoalResponseDTO(sharedGoal);
    }
}
