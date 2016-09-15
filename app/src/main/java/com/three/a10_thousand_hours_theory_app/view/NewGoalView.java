package com.three.a10_thousand_hours_theory_app.view;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;

import java.util.Date;

/**
 * Created by LCH on 2016. 9. 11..
 */
public interface NewGoalView {
    void goNewGoalFormStep1();
    void goNewGoalFormStep2();
    void goNewGoalFormStep3();
    void showDatePicker(Date deadLineDate);
    void showTaskDialog(TaskRuleEntity task);
    void submitNewGoal();
    void addTask(TaskRuleEntity newTask);
    void modifyTask(TaskRuleEntity newTask);
}
