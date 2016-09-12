package com.three.a10_thousand_hours_theory_app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.Goal;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.presenter.MainPresenter;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 11..
 */
public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.ViewHolder>{

    private List<GoalEntity> goalList;
    private MainPresenter mMainPresenter;

    public GoalListAdapter(List<GoalEntity> goalList) {
        this.goalList = goalList;
    }

    public void setMainPresenter(MainPresenter mMainPresenter) {
        this.mMainPresenter = mMainPresenter;
    }

    @Override
    public GoalListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GoalListAdapter.ViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        holder.mGoalTitleTv.setText(goal.getTitle());
        holder.mGoalDesTv.setText(goal.getDescription());
        holder.mGoalDetailsIv.setOnClickListener(v -> mMainPresenter.showGoalDetails(goal));
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mGoalTitleTv;
        public TextView mGoalDesTv;
        public ImageView mGoalDetailsIv;

        public ViewHolder(View itemView) {
            super(itemView);
            mGoalTitleTv = (TextView) itemView.findViewById(R.id.goal_title_tv);
            mGoalDesTv = (TextView) itemView.findViewById(R.id.goal_des_tv);
            mGoalDetailsIv = (ImageView) itemView.findViewById(R.id.goal_details_iv);
        }
    }
}
