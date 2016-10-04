package com.three.a10_thousand_hours_theory_app.presenter;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.usecase.CreateGoalUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.DefaultSubscriber;
import com.three.a10_thousand_hours_theory_app.model.usecase.GetGoalSyncUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.SyncUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.UpdateGoalUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.UseCase;
import com.three.a10_thousand_hours_theory_app.view.NewGoalView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;

/**
 * Created by LCH on 2016. 9. 11..
 */
@EBean(scope = EBean.Scope.Singleton)
public class NewGoalPresenter implements Presenter{

    private NewGoalView mNewGoalView;

    @Bean(CreateGoalUseCase.class)
    UseCase<CreateGoalRequestDTO> mCreateGoalUseCase;

    @Bean(UpdateGoalUseCase.class)
    UseCase<UpdateGoalRequestDTO> mUpdateGoalUseCase;

    @Bean(GetGoalSyncUseCase.class)
    SyncUseCase<GetGoalRequestDTO, GetGoalResponseDTO> mGetGoalSyncUseCase;

    public void goNewGoalFormStep2() {
        mNewGoalView.goNewGoalFormStep2();
    }

    public void goNewGoalFormStep1() {
        mNewGoalView.goNewGoalFormStep1();
    }

    public void submitNewGoal(CreateGoalRequestDTO createGoalRequestDTO){
        mCreateGoalUseCase.execute(createGoalRequestDTO, new DefaultSubscriber<CreateGoalResponseDTO>(){
            @Override
            public void onNext(CreateGoalResponseDTO o) {
                mNewGoalView.onSubmitNewGoal();
            }
        });
    }

    public void setNewGoalView(NewGoalView mNewGoalView) {
        this.mNewGoalView = mNewGoalView;
    }

    public void goNewGoalFormStep3() {
        mNewGoalView.goNewGoalFormStep3();
    }

    public void showDatePicker(Date deadLineDate) {
        mNewGoalView.showDatePicker(deadLineDate);
    }

    public void showTaskDialog(TaskRuleEntity taskEntity){
        mNewGoalView.showTaskDialog(taskEntity);
    }

    public void addTaskRule(TaskRuleEntity newTask) {
        mNewGoalView.onAddTask(newTask);
    }

    public void modifyTask(TaskRuleEntity taskEntity) {
        mNewGoalView.onModifyTask(taskEntity);
    }

    public GoalEntity getGoal(int goalId) {
        GetGoalResponseDTO g = mGetGoalSyncUseCase.execute(new GetGoalRequestDTO(goalId));
        return g.getGoalEntity();
    }

    public void updateGoal(UpdateGoalRequestDTO updateGoalRequestDTO) {
        mUpdateGoalUseCase.execute(updateGoalRequestDTO, new DefaultSubscriber<UpdateGoalResponseDTO>(){
            @Override
            public void onNext(UpdateGoalResponseDTO o) {
                mNewGoalView.finish();
            }
        });
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mNewGoalView = null;
        mCreateGoalUseCase.unsubscribe();
        mUpdateGoalUseCase.unsubscribe();
    }
}
