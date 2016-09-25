package com.three.a10_thousand_hours_theory_app.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;
import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.FirebaseDB;
import com.three.a10_thousand_hours_theory_app.presenter.BoardPresenter;
import com.three.a10_thousand_hours_theory_app.view.BoardView;
import com.three.a10_thousand_hours_theory_app.view.adapter.SharedGoalListAdapter;
import com.three.a10_thousand_hours_theory_app.view.firbase_listener.AbstractSharedGoalsValueEventListener;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
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

    private ValueEventListener mSharedGoalsListener = new AbstractSharedGoalsValueEventListener(){
        @Override
        public void onDataChange(List<SharedGoal> sharedGoals) {
            onPostGetSharedGoals(sharedGoals);
        }
    };

    private ValueEventListener mSharedGoalsReverseListener = new AbstractSharedGoalsValueEventListener(){
        @Override
        public void onDataChange(List<SharedGoal> sharedGoals) {
            Collections.reverse(sharedGoals);
            onPostGetSharedGoals(sharedGoals);
        }
    };

    private void onPostGetSharedGoals(List<SharedGoal> sharedGoals) {
        SharedGoalListAdapter sharedGoalListAdapter = new SharedGoalListAdapter(getContext(), sharedGoals);
        sharedGoalListAdapter.setBoardPresenter(mBoardPresenter);
        if(mSharedGoalListView != null){
            mSharedGoalListView.setAdapter(sharedGoalListAdapter);
            mProgressBar.setVisibility(View.GONE);
            mSharedGoalListView.setVisibility(View.VISIBLE);
        }
    }

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
        setHasOptionsMenu(true);

        FirebaseDB.sharedGoals().addValueEventListener(mSharedGoalsListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_board_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_by_create_at:
                mProgressBar.setVisibility(View.VISIBLE);
                mSharedGoalListView.setVisibility(View.GONE);
                FirebaseDB.sharedGoals().orderByChild("like").removeEventListener(mSharedGoalsReverseListener);
                FirebaseDB.sharedGoals().addValueEventListener(mSharedGoalsListener);
                return true;
            case R.id.action_order_by_like:
                mProgressBar.setVisibility(View.VISIBLE);
                mSharedGoalListView.setVisibility(View.GONE);
                FirebaseDB.sharedGoals().removeEventListener(mSharedGoalsListener);
                FirebaseDB.sharedGoals().orderByChild("like").addValueEventListener(mSharedGoalsReverseListener);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        LayoutInflater inflater = getLayoutInflater(null);
        View dialogView= inflater.inflate(R.layout.dialog_shared_goal_details, null);
        TextView sharedGoalDescriptionTv = (TextView) dialogView.findViewById(R.id.shared_goal_description_tv);
        TextView sharedGoalNeedTv = (TextView) dialogView.findViewById(R.id.shared_goal_need_tv);
        TextView sharedGoalLikeTv = (TextView) dialogView.findViewById(R.id.shared_goal_like_tv);
        ListView sharedGoalTaskRuleLv = (ListView) dialogView.findViewById(R.id.shared_goal_task_rule_lv);

        sharedGoalDescriptionTv.setText(sharedGoal.getDescription());
        if(sharedGoal.getType() == Const.GOAL_TYPE_HOURS)
            sharedGoalNeedTv.setText(String.format(Const.약_D시간_예상, sharedGoal.getGoalHours()));
        else
            sharedGoalNeedTv.setText(String.format(Const.약_D일_예상, sharedGoal.getGoalDays()));

        sharedGoalLikeTv.setText(Integer.toString(sharedGoal.getLike()));

        List<String> sharedGoalTaskRuleList = new ArrayList();
        for (SharedGoal.TaskRule t : sharedGoal.getTaskRules())
            sharedGoalTaskRuleList.add(String.format("매주 %d회 %d시간씩 '%s' 하기", t.getTimes(), t.getHours(), t.getTitle()));
        ListAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, sharedGoalTaskRuleList);
        sharedGoalTaskRuleLv.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(sharedGoal.getTitle());
        builder.setPositiveButton("다운로드", (dialog, which) -> {
            mBoardPresenter.download(sharedGoal);
            dialog.dismiss();
        });
        builder.setNegativeButton("닫기", (dialog, which) -> dialog.dismiss());
        builder.setView(dialogView);
        builder.show();
    }

    @Override
    public void onDownloadGoal(GoalEntity goalEntity) {
        Toast.makeText(getContext(), String.format("목표 '%s'를 다운로드 했습니다. 내 목표에서 확인하세요.", goalEntity.getTitle()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailToDownloadGoal() {
        Toast.makeText(getContext(), "목표 '%s'를 다운로드 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
    }
}
