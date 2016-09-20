package com.three.a10_thousand_hours_theory_app.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.presenter.BoardPresenter;
import com.three.a10_thousand_hours_theory_app.view.BoardView;
import com.three.a10_thousand_hours_theory_app.view.adapter.SharedGoalListAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by LCH on 2016. 9. 19..
 */

@EFragment(R.layout.fragment_board)
public class BoardFragment extends Fragment implements BoardView{

    @ViewById(R.id.shared_goal_list)
    RecyclerView mSharedGoalListView;

    @Bean
    BoardPresenter mBoardPresenter;

    @ViewById(R.id.upload_shared_goal_btn)
    FloatingActionButton mFloatingActionButton;

    private SharedGoalListAdapter mSharedGoalListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSharedGoalListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mSharedGoalListView.setLayoutManager(layoutManager);
        mSharedGoalListAdapter = new SharedGoalListAdapter(getContext());
        mSharedGoalListView.setAdapter(mSharedGoalListAdapter);

        // inject
        mSharedGoalListAdapter.setBoardPresenter(mBoardPresenter);
        mBoardPresenter.setBoardView(this);
        mBoardPresenter.loadSharedGoals();
    }

    @Click(R.id.upload_shared_goal_btn)
    public void uploadSharedGoalButton(View view){
        mBoardPresenter.showGoalsDialog();
    }
}
