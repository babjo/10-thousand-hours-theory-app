package com.three.a10_thousand_hours_theory_app.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.Goal;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 11..
 */
public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.ViewHolder>{

    private List<Goal> goalList;

    public GoalListAdapter(List<Goal> goalList) {
        this.goalList = goalList;
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
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mGoalTitleTv;
        public TextView mGoalDesTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mGoalTitleTv = (TextView) itemView.findViewById(R.id.goal_title_tv);
            mGoalDesTv = (TextView) itemView.findViewById(R.id.goal_des_tv);
        }
    }
}
