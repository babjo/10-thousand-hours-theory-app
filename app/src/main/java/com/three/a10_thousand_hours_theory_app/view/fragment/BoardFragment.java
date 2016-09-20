package com.three.a10_thousand_hours_theory_app.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.FirebaseDB;
import com.three.a10_thousand_hours_theory_app.presenter.BoardPresenter;
import com.three.a10_thousand_hours_theory_app.view.BoardView;
import com.three.a10_thousand_hours_theory_app.view.adapter.SharedGoalListAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 19..
 */

@EFragment(R.layout.fragment_board)
public class BoardFragment extends Fragment implements BoardView{

    private static final String TAG = BoardFragment.class.getName();

    @ViewById(R.id.shared_goal_list)
    RecyclerView mSharedGoalListView;

    @Bean
    BoardPresenter mBoardPresenter;

    @ViewById(R.id.upload_shared_goal_btn)
    FloatingActionButton mFloatingActionButton;

    @ViewById(R.id.loading_pg)
    ProgressBar mProgressBar;

    private SharedGoalListAdapter mSharedGoalListAdapter;
    private ValueEventListener mSharedGoalsListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.e("Count " ,""+dataSnapshot.getChildrenCount());
            List<SharedGoal> sharedGoals = new ArrayList();
            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                Log.d(TAG, "loadSharedGoal:onDataChange Value ==> "+childSnapshot.getKey());
                SharedGoal sharedGoal = childSnapshot.getValue(SharedGoal.class);
                Log.d(TAG, "loadSharedGoal:onDataChange Value ==> "+sharedGoal.getTitle());
                sharedGoals.add(sharedGoal);
            }
            SharedGoalListAdapter sharedGoalListAdapter = new SharedGoalListAdapter(getContext(), sharedGoals);
            sharedGoalListAdapter.setBoardPresenter(mBoardPresenter);
            if(mSharedGoalListView != null){
                mSharedGoalListView.setAdapter(sharedGoalListAdapter);
                mProgressBar.setVisibility(View.GONE);
                mSharedGoalListView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "loadSharedGoal:onCancelled", databaseError.toException());
        }
    }; ;

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


        mProgressBar.setVisibility(View.VISIBLE);
        mSharedGoalListView.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set Event
        FirebaseDB.sharedGoals().addValueEventListener(mSharedGoalsListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        FirebaseDB.sharedGoals().removeEventListener(mSharedGoalsListener);
    }

    @Click(R.id.upload_shared_goal_btn)
    public void uploadSharedGoalButton(View view){
        mBoardPresenter.showGoalsDialog();
    }

    @Override
    public void onUploadGoal(SharedGoal sharedGoal) {
        Toast.makeText(getContext(), String.format("내 목표 '%s' 업로드 성공", sharedGoal.getTitle()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSharedGoalDetails(SharedGoal sharedGoal) {

    }
}
