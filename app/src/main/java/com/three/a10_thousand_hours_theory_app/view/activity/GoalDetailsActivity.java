package com.three.a10_thousand_hours_theory_app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.three.a10_thousand_hours_theory_app.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class GoalDetailsActivity extends AppCompatActivity {


    @ViewById(R.id.step3_task_lv)
    ListView mTaskListView;

    @ViewById(R.id.compactcalendar_view)
    CompactCalendarView mCompactCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Intent intent = getIntent();
        if(intent != null){
            String goalId = intent.getStringExtra("GOAL_ID");
            setTitle(goalId);

        }
    }
}
