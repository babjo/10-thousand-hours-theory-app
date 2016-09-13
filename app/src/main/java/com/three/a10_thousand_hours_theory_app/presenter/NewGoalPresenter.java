package com.three.a10_thousand_hours_theory_app.presenter;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalResponseDTO;
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
    Service createGoalService;

    public void goNewGoalFormStep2() {
        mNewGoalView.goNewGoalFormStep2();
    }

    public void goNewGoalFormStep1() {
        mNewGoalView.goNewGoalFormStep1();
    }

    public void submitNewGoal(CreateGoalRequestDTO createGoalRequestDTO){
        CreateGoalResponseDTO createGoalResponseDTO  = (CreateGoalResponseDTO) createGoalService.execute(createGoalRequestDTO);
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

    public void showTaskDialog(TaskEntity taskEntity){
        mNewGoalView.showTaskDialog(taskEntity);
    }

    public void addTask(TaskEntity newTask) {
        mNewGoalView.addTask(newTask);
    }

    public void modifyTask(TaskEntity taskEntity) {
        mNewGoalView.modifyTask(taskEntity);
    }
}
