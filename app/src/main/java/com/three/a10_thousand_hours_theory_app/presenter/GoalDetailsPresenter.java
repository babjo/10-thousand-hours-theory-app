package com.three.a10_thousand_hours_theory_app.presenter;

import android.content.Context;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.DeleteGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.DeleteGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.usecase.DefaultSubscriber;
import com.three.a10_thousand_hours_theory_app.model.usecase.DeleteGoalUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.GetGoalUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.UseCase;
import com.three.a10_thousand_hours_theory_app.view.GoalDetailsView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 13..
 */


@EBean
public class GoalDetailsPresenter implements Presenter{

    private Context mContext;
    private GoalDetailsView mGoalDetailsView;

    @Bean(GetGoalUseCase.class)
    UseCase<GetGoalRequestDTO> mGetGoalUseCase;

    @Bean(DeleteGoalUseCase.class)
    UseCase<DeleteGoalRequestDTO> mDeleteGoalUseCase;

    public GoalDetailsPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void setGoalDetailsView(GoalDetailsView mGoalDetailsView) {
        this.mGoalDetailsView = mGoalDetailsView;
    }

    public void loadGoal(int goalId) {
        mGetGoalUseCase.execute(new GetGoalRequestDTO(goalId), new DefaultSubscriber<GetGoalResponseDTO>(){
            @Override
            public void onNext(GetGoalResponseDTO o) {
                GoalEntity goalEntity = o.getGoalEntity();
                mGoalDetailsView.onLoadGoal(goalEntity, 0);
            }
        });
    }

    public void deleteGoal(int goalId) {
        mDeleteGoalUseCase.execute(new DeleteGoalRequestDTO(goalId), new DefaultSubscriber<DeleteGoalResponseDTO>(){
            @Override
            public void onNext(DeleteGoalResponseDTO o) {
                mGoalDetailsView.finish();
            }
        });
    }

    public void showTimerDialog(TaskEntity taskEntity) {
        mGoalDetailsView.showTimerDialog(taskEntity);
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mGoalDetailsView = null;
        mDeleteGoalUseCase.unsubscribe();
        mGetGoalUseCase.unsubscribe();
    }
}
