package com.three.a10_thousand_hours_theory_app.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_board) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loadGoals(List<GoalEntity> goals) {
        runOnUiThread(()->{
            mGoalListAdapter.clearAndAddAll(goals);
            mGoalListAdapter.notifyDataSetChanged();
        });
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

}
