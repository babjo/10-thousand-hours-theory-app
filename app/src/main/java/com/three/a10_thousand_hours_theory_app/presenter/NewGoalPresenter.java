package com.three.a10_thousand_hours_theory_app.presenter;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.service.CreateGoalService;
import com.three.a10_thousand_hours_theory_app.model.service.Service;
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

    public void goNewGoalFormStep2() {
        mNewGoalView.goNewGoalFormStep2();
    }

    public void goNewGoalFormStep1() {
        mNewGoalView.goNewGoalFormStep1();
    }

    public void submitNewGoal(CreateGoalRequestDTO createGoalRequestDTO){
        mCreateGoalService.execute(createGoalRequestDTO);
        mNewGoalView.submitNewGoal();
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

    public void addTask(TaskRuleEntity newTask) {
        mNewGoalView.addTask(newTask);
    }

    public void modifyTask(TaskRuleEntity taskEntity) {
        mNewGoalView.modifyTask(taskEntity);
    }
}
