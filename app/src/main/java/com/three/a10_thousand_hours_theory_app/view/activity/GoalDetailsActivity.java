package com.three.a10_thousand_hours_theory_app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.Utils;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.Task;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.presenter.GoalDetailsPresenter;
import com.three.a10_thousand_hours_theory_app.view.GoalDetailsView;
import com.three.a10_thousand_hours_theory_app.view.adapter.TaskAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.List;

@EActivity
public class GoalDetailsActivity extends AppCompatActivity implements GoalDetailsView{

    @ViewById(R.id.month_tv)
    TextView mMonthTv;

    @ViewById(R.id.task_lv)
    ListView mTaskListView;

    @ViewById(R.id.compactcalendar_view)
    CompactCalendarView mCompactCalendarView;

    @Bean
    GoalDetailsPresenter mGoalDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details);
        mGoalDetailsPresenter.setGoalDetailsView(this);

        mMonthTv.setText(Utils.getMonth(new Date()));
        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mMonthTv.setText(Utils.getMonth(firstDayOfNewMonth));
            }
        });

        Intent intent = getIntent();
        if(intent != null){
            int goalId = intent.getIntExtra(Const.INTENT_EXTRA_GOAL_ID, -1);
            if (goalId != -1) {
                mGoalDetailsPresenter.loadGoal(goalId);
            }else{ // goldId 가 없으면
            }
        }
    }

    private void setCurrentDateAtCalendar(Task task) {
        Date d;
        if(task.getCompleted())
            d = task.getCompletedDate();
        else
            d = task.getBeginDate();
        mMonthTv.setText(Utils.getMonth(d));
        mCompactCalendarView.setCurrentDate(d);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null){
            int goalId = intent.getIntExtra(Const.INTENT_EXTRA_GOAL_ID, -1);
            if (goalId != -1) {
                mGoalDetailsPresenter.loadGoal(goalId);
            }else{ // goldId 가 없으면
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.goal_details_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, NewGoalActivity_.class);
            intent.putExtra(Const.INTENT_EXTRA_GOAL_ID, getIntent().getIntExtra(Const.INTENT_EXTRA_GOAL_ID, -1));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadGoal(GoalEntity goalEntity) {
        setTitle(goalEntity.getTitle());
        List<TaskEntity> tasks = goalEntity.getTesks();

        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);
        taskAdapter.setGoalDetailsPresenter(mGoalDetailsPresenter);
        mTaskListView.setAdapter(taskAdapter);

        mTaskListView.setOnItemClickListener((parent, view, position, id) ->{
            Task task = taskAdapter.getItem(position);
            setCurrentDateAtCalendar(task);
        });

        mTaskListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0)
                    Log.i("a", "scrolling stopped...");
                if (view.getId() == mTaskListView.getId()) {
                    int currentFirstVisibleItem = mTaskListView.getFirstVisiblePosition();
                    TaskEntity task = (TaskEntity) mTaskListView.getItemAtPosition(currentFirstVisibleItem);
                    setCurrentDateAtCalendar(task);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        mCompactCalendarView.removeAllEvents();
        for (Task t : tasks) {
            if(t.getCompleted()) {
                Event e = new Event(t.getLabelColor(), t.getCompletedDate().getTime());
                mCompactCalendarView.addEvent(e, true);
            }
        }
    }
}
