package com.three.a10_thousand_hours_theory_app.view;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;

import java.util.Date;

/**
 * Created by LCH on 2016. 9. 11..
 */
public interface NewGoalView {
    void goNewGoalFormStep1();
    void goNewGoalFormStep2();
    void goNewGoalFormStep3();
    void showDatePicker(Date deadLineDate);
    void showTaskDialog(TaskEntity task);
    void submitNewGoal();
    void addTask(TaskEntity newTask);
    void modifyTask(TaskEntity newTask);
}
