package com.three.a10_thousand_hours_theory_app.presenter;

import android.content.Context;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.SaveTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.service.GetGoalService;
import com.three.a10_thousand_hours_theory_app.model.service.SaveTaskService;
import com.three.a10_thousand_hours_theory_app.model.service.Service;
import com.three.a10_thousand_hours_theory_app.view.GoalDetailsView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 13..
 */


@EBean
public class GoalDetailsPresenter {

    private Context mContext;
    private GoalDetailsView mGoalDetailsView;

    @Bean(GetGoalService.class)
    Service mGetGoalService;

    @Bean(SaveTaskService.class)
    Service mSaveTaskService;

    public GoalDetailsPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void setGoalDetailsView(GoalDetailsView mGoalDetailsView) {
        this.mGoalDetailsView = mGoalDetailsView;
    }

    public void loadGoal(int goalId) {
        GetGoalResponseDTO g = (GetGoalResponseDTO) mGetGoalService.execute(new GetGoalRequestDTO(goalId));
        GoalEntity goalEntity = g.getGoalEntity();
        this.mGoalDetailsView.loadGoal(goalEntity);
    }

    public void completeTask(TaskEntity taskEntity) {
        mSaveTaskService.execute(new SaveTaskRequestDTO(taskEntity));
        loadGoal(taskEntity.getGoal().getId());
    }

}