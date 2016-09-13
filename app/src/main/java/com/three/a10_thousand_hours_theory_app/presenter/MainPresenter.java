package com.three.a10_thousand_hours_theory_app.presenter;

import android.content.Context;
import android.content.Intent;

import com.three.a10_thousand_hours_theory_app.model.domain.Goal;
import com.three.a10_thousand_hours_theory_app.view.activity.GoalDetailsActivity_;
import com.three.a10_thousand_hours_theory_app.view.activity.NewGoalActivity_;

import org.androidannotations.annotations.EBean;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by LCH on 2016. 9. 11..
 */
@EBean(scope = EBean.Scope.Singleton)
public class MainPresenter {

    private Context mContext;

    public MainPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void addGoal() {
        Intent intent = new Intent(mContext, NewGoalActivity_.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public void showGoalDetails(Goal goal) {
        Intent intent = new Intent(mContext, GoalDetailsActivity_.class);
        intent.putExtra("GOAL_ID", goal.getTitle());
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
