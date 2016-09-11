package com.three.a10_thousand_hours_theory_app.presenter;

import com.three.a10_thousand_hours_theory_app.model.domain.Goal;
import com.three.a10_thousand_hours_theory_app.view.NewGoalView;

import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 11..
 */
@EBean
public class NewGoalPresenter {

    private NewGoalView mNewGoalView;

    public void goNewGoalFormStep2() {
        mNewGoalView.goNewGoalFormStep2();
    }

    public void goNewGoalFormStep1() {
        mNewGoalView.goNewGoalFormStep1();
    }

    public void submitNewGoal(Goal goal){

    }

    public void setNewGoalView(NewGoalView mNewGoalView) {
        this.mNewGoalView = mNewGoalView;
    }
}
