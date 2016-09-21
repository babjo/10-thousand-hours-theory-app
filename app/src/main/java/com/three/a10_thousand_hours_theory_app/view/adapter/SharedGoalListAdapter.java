package com.three.a10_thousand_hours_theory_app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;
import com.three.a10_thousand_hours_theory_app.model.domain.User;
import com.three.a10_thousand_hours_theory_app.presenter.BoardPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 20..
 */
public class SharedGoalListAdapter extends RecyclerView.Adapter<SharedGoalListAdapter.ViewHolder>{

    private final Context mContext;
    private List<SharedGoal> mSharedGoals;
    private BoardPresenter mBoardPresenter;

    public SharedGoalListAdapter(Context context) {
        this(context, new ArrayList());
    }

    public SharedGoalListAdapter(Context context, List<SharedGoal> mSharedGoals) {
        this.mContext = context;
        this.mSharedGoals = mSharedGoals;
    }

    public void setBoardPresenter(BoardPresenter mBoardPresenter) {
        this.mBoardPresenter = mBoardPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_shared_goal, parent, false);
        SharedGoalListAdapter.ViewHolder viewHolder = new SharedGoalListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SharedGoal sharedGoal = mSharedGoals.get(position);
        holder.mSharedGoalTitleTv.setText(sharedGoal.getTitle());
        holder.mSharedGoalTitleAndNeedLy.setOnClickListener(v -> mBoardPresenter.sharedGoalDetails(sharedGoal));

        if(sharedGoal.getType() == Const.GOAL_TYPE_HOURS)
            holder.mSharedGoalNeedTv.setText(String.format(Const.약_D시간_예상, sharedGoal.getGoalHours()));
        else{
            holder.mSharedGoalNeedTv.setText(String.format(Const.약_D일_예상, sharedGoal.getGoalDays()));
        }

        User user = mBoardPresenter.getUser();
        if(sharedGoal.getLikeUserKeys() != null && sharedGoal.getLikeUserKeys().contains(user.getKey())) {
            holder.mSharedGoalLikeIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_black_24dp));
            holder.mSharedGoalLikeIv.setOnClickListener(v -> mBoardPresenter.unlikeSharedGoal(sharedGoal, user));
        } else{
            holder.mSharedGoalLikeIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
            holder.mSharedGoalLikeIv.setOnClickListener(v -> mBoardPresenter.likeSharedGoal(sharedGoal, user));
        }

        holder.mSharedGoalLikeTv.setText(Integer.toString(sharedGoal.getLike()));
    }

    @Override
    public int getItemCount() {
        return mSharedGoals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mSharedGoalTitleAndNeedLy;
        public TextView mSharedGoalTitleTv;
        public TextView mSharedGoalNeedTv;
        public ImageView mSharedGoalLikeIv;
        public TextView mSharedGoalLikeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mSharedGoalTitleAndNeedLy = (LinearLayout) itemView.findViewById(R.id.shared_goal_item_title_need_ly);
            mSharedGoalTitleTv = (TextView) itemView.findViewById(R.id.shared_goal_item_title_tv);
            mSharedGoalNeedTv = (TextView) itemView.findViewById(R.id.shared_goal_item_need_tv);
            mSharedGoalLikeIv = (ImageView) itemView.findViewById(R.id.shared_goal_item_like_iv);
            mSharedGoalLikeTv = (TextView) itemView.findViewById(R.id.shared_goal_item_like_tv);
        }
    }
}
