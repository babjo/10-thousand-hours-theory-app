package com.three.a10_thousand_hours_theory_app.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.presenter.MainPresenter;
import com.three.a10_thousand_hours_theory_app.view.MainView;
import com.three.a10_thousand_hours_theory_app.view.adapter.GoalListAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity
public class MainActivity extends AppCompatActivity implements MainView{


    @ViewById(R.id.goal_list)
    RecyclerView mGoalListView;

    @Bean
    MainPresenter mMainPresenter;
    private GoalListAdapter mGoalListAdapter;

    @ViewById(R.id.create_goal_btn)
    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGoalListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mGoalListView.setLayoutManager(layoutManager);
        mGoalListAdapter = new GoalListAdapter();
        mGoalListView.setAdapter(mGoalListAdapter);

        // inject
        mGoalListAdapter.setMainPresenter(mMainPresenter);
        mMainPresenter.setMainView(this);

        mMainPresenter.loadGoals();
    }

    @Click(R.id.create_goal_btn)
    public void createGoalButton(View view){
        mMainPresenter.addGoal();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.loadGoals();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadGoals(List<GoalEntity> goals) {
        runOnUiThread(()->{
            mGoalListAdapter.clearAndAddAll(goals);
            mGoalListAdapter.notifyDataSetChanged();
        });
    }
}
