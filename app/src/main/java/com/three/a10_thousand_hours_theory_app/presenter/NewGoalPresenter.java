package com.three.a10_thousand_hours_theory_app.presenter;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.service.CreateGoalService;
import com.three.a10_thousand_hours_theory_app.model.service.GetGoalService;
import com.three.a10_thousand_hours_theory_app.model.service.Service;
import com.three.a10_thousand_hours_theory_app.model.service.UpdateGoalService;
import com.three.a10_thousand_hours_theory_app.view.NewGoalView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;

/**
 * Created by LCH on 2016. 9. 11..
 */
@EBean(scope = EBean.Scope.Singleton)
public class NewGoalPresenter {

    private NewGoalView mNewGoalView;

    @Bean(CreateGoalService.class)
    Service mCreateGoalService;

    @Bean(UpdateGoalService.class)
    Service mUpdateGoalService;

    @Bean(GetGoalService.class)
    Service mGetGoalService;

    public void goNewGoalFormStep2() {
        mNewGoalView.goNewGoalFormStep2();
    }

    public void goNewGoalFormStep1() {
        mNewGoalView.goNewGoalFormStep1();
    }

    public void submitNewGoal(CreateGoalRequestDTO createGoalRequestDTO){
        mCreateGoalService.execute(createGoalRequestDTO);
        mNewGoalView.onSubmitNewGoal();
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
        GetGoalResponseDTO g = (GetGoalResponseDTO) mGetGoalService.execute(new GetGoalRequestDTO(goalId));
        return g.getGoalEntity();
    }

    public void updateGoal(UpdateGoalRequestDTO updateGoalRequestDTO) {
        mUpdateGoalService.execute(updateGoalRequestDTO);
        mNewGoalView.finish();
    }
}
