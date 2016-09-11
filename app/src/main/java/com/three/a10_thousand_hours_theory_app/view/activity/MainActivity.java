package com.three.a10_thousand_hours_theory_app.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.Goal;
import com.three.a10_thousand_hours_theory_app.presenter.MainPresenter;
import com.three.a10_thousand_hours_theory_app.view.adapter.GoalListAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class MainActivity extends AppCompatActivity {


    @ViewById(R.id.goal_list)
    RecyclerView mGoalListView;

    @Bean
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGoalListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mGoalListView.setLayoutManager(layoutManager);
        List<Goal> goalList = new ArrayList();
        goalList.add(new Goal("모든 영화 자막 없이 보기", "영어 농담도 알아들을 수준"));
        goalList.add(new Goal("드로잉", "'그림 잘 그리는 사람'이 될 때까지 !"));
        GoalListAdapter adapter = new GoalListAdapter(goalList);
        mGoalListView.setAdapter(adapter);
    }



    @Click(R.id.fab)
    public void fabButton(View view){
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        mMainPresenter.addGoal();
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
}
