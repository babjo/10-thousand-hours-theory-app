package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.google.firebase.database.DatabaseReference;
import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.Utils;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRule;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UploadGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.infrastructure.FirebaseDB;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by LCH on 2016. 9. 30..
 */

@EBean(scope = EBean.Scope.Singleton)
public class UploadGoalUseCase extends UseCase<UploadGoalRequestDTO> {

    private final DatabaseReference mDatabaseReference;

    public UploadGoalUseCase(){
        mDatabaseReference = FirebaseDB.sharedGoals();
    }

    @Override
    protected Observable buildUseCaseObservable(UploadGoalRequestDTO uploadGoalRequestDTO) {
        return Observable.create(subscriber -> {
            GoalEntity goalEntity = uploadGoalRequestDTO.getGoalEntity();
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
                sharedGoal.setGoalDays(goalDays);
            }
            sharedGoal.setLike(0);
            sharedGoal.setLikeUserKeys(new ArrayList());

            List<SharedGoal.TaskRule> taskRules = new ArrayList();
            for (TaskRule r : goalEntity.getTaskRules()){
                SharedGoal.TaskRule t = new SharedGoal.TaskRule();
                t.setHours(r.getHours());
                t.setLabelColor(r.getLabelColor());
                t.setTimes(r.getTimes());
                t.setTitle(r.getTitle());
                taskRules.add(t);
            }
            sharedGoal.setTaskRules(taskRules);
            sharedGoal.setUserKey(uploadGoalRequestDTO.getUserEntity().getKey());
            sharedGoal.setUpdatedAt(Utils.DATE_FORMAT_yyyy_MM_dd_hh_mm_ss.format(new Date()));
            mDatabaseReference.child(sharedGoalKey).setValue(sharedGoal);
            subscriber.onNext(new UploadGoalResponseDTO(sharedGoal));
            subscriber.onCompleted();
        });
    }
}
