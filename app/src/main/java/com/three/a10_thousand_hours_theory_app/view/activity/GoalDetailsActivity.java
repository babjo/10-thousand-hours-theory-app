package com.three.a10_thousand_hours_theory_app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.presenter.GoalDetailsPresenter;
import com.three.a10_thousand_hours_theory_app.view.GoalDetailsView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class GoalDetailsActivity extends AppCompatActivity implements GoalDetailsView{


    @ViewById(R.id.step3_task_lv)
    ListView mTaskListView;

    @ViewById(R.id.compactcalendar_view)
    CompactCalendarView mCompactCalendarView;

    @Bean
    GoalDetailsPresenter mGoalDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        mGoalDetailsPresenter.setGoalDetailsView(this);

        Intent intent = getIntent();
        if(intent != null){
            int goalId = intent.getIntExtra("GOAL_ID", -1);
            if (goalId != -1) {
                mGoalDetailsPresenter.loadGoal(goalId);
            }else{ // goldId 가 없으면
            }
        }
    }

    @Override
    public void loadGoal(GoalEntity goalEntity) {
        setTitle(goalEntity.getTitle());
    }
}
