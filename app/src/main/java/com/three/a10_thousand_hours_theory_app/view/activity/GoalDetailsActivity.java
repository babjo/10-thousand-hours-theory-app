package com.three.a10_thousand_hours_theory_app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.Task;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRule;
import com.three.a10_thousand_hours_theory_app.presenter.GoalDetailsPresenter;
import com.three.a10_thousand_hours_theory_app.view.GoalDetailsView;
import com.three.a10_thousand_hours_theory_app.view.adapter.TaskAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class GoalDetailsActivity extends AppCompatActivity implements GoalDetailsView{


    @ViewById(R.id.task_lv)
    ListView mTaskListView;

    @ViewById(R.id.compactcalendar_view)
    CompactCalendarView mCompactCalendarView;

    @Bean
    GoalDetailsPresenter mGoalDetailsPresenter;
    private TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details);

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

        List<Task> tasks = new ArrayList();
        for (TaskRule r : goalEntity.getTaskRules()){
            tasks.addAll(r.getTasks());
        }

        mTaskAdapter = new TaskAdapter(this, tasks);
        mTaskAdapter.setGoalDetailsPresenter(mGoalDetailsPresenter);
        mTaskListView.setAdapter(mTaskAdapter);

        mCompactCalendarView.removeAllEvents();
        for (Task t : tasks) {
            if(t.getCompleted()) {
                Event e = new Event(t.getLabelColor(), t.getCompletedDate().getTime());
                mCompactCalendarView.addEvent(e, true);
            }
        }
    }
}
