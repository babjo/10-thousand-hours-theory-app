package com.three.a10_thousand_hours_theory_app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.presenter.MainPresenter;
import com.three.a10_thousand_hours_theory_app.view.MainView;
import com.three.a10_thousand_hours_theory_app.view.adapter.GoalListAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 19..
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment implements MainView{

    @ViewById(R.id.goal_list)
    RecyclerView mGoalListView;

    @Bean
    MainPresenter mMainPresenter;
    private GoalListAdapter mGoalListAdapter;

    @ViewById(R.id.create_goal_btn)
    FloatingActionButton mFloatingActionButton;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoalListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mGoalListView.setLayoutManager(layoutManager);
        mGoalListAdapter = new GoalListAdapter();
        mGoalListView.setAdapter(mGoalListAdapter);

        // inject
        mGoalListAdapter.setMainPresenter(mMainPresenter);
        mMainPresenter.setMainView(this);

        mMainPresenter.loadGoals();
    }
    @Override
    public void loadGoals(List<GoalEntity> goals) {
        getActivity().runOnUiThread(()->{
            mGoalListAdapter.clearAndAddAll(goals);
            mGoalListAdapter.notifyDataSetChanged();
        });
    }

    @Click(R.id.create_goal_btn)
    public void createGoalButton(View view){
        mMainPresenter.addGoal();
    }
}
