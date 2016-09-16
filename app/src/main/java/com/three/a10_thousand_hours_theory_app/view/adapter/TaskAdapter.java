package com.three.a10_thousand_hours_theory_app.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.Utils;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.presenter.GoalDetailsPresenter;

import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by LCH on 2016. 9. 15..
 */

public class TaskAdapter extends BaseAdapter{

    private final static String TAG = TaskAdapter.class.getName();
    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<TaskEntity> mTasks;

    private GoalDetailsPresenter mGoalDetailsPresenter;

    public TaskAdapter(Context mContext, List<TaskEntity> taskEntities) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mTasks = taskEntities;
    }

    public void setGoalDetailsPresenter(GoalDetailsPresenter mGoalDetailsPresenter) {
        this.mGoalDetailsPresenter = mGoalDetailsPresenter;
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public TaskEntity getItem(int position) {
        Log.d(TAG, String.format("getItem (%d)", position));
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mTasks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, String.format("getView (%d)", position));
        ViewHolder v;
        TaskEntity task = getItem(position);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.task_list_item, parent, false);
            v = new ViewHolder();
            v.mTaskLabel = convertView.findViewById(R.id.task_label);
            v.mTaskTitleTv = (TextView) convertView.findViewById(R.id.task_title_tv);
            v.mTaskHoursTv = (TextView) convertView.findViewById(R.id.task_hours_tv);
            v.mTaskCompletedCb = (CheckBox) convertView.findViewById(R.id.task_completed_cb);
            v.mTaskCompletedDateTv = (TextView) convertView.findViewById(R.id.task_completed_date_tv);
            convertView.setTag(v);
        }else{
            v = (ViewHolder) convertView.getTag();
        }

        v.mTaskLabel.setBackgroundColor(task.getLabelColor());
        v.mTaskTitleTv.setText(task.getTitle());
        v.mTaskHoursTv.setText(task.getHours()+":00");
        v.mTaskCompletedCb.setChecked(task.getCompleted());
        if(task.getCompleted()){
            v.mTaskCompletedDateTv.setText(Utils.DATE_FORMAT_yyyy_MM_dd.format(task.getCompletedDate()));
            v.mTaskCompletedDateTv.setVisibility(VISIBLE);
        }else{
            v.mTaskCompletedDateTv.setVisibility(GONE);
        }

        v.mTaskCompletedCb.setOnClickListener(v1 -> {
            Log.d(TAG, String.format("mTaskCompletedCb onClick position(%d)", position));
            if(task.getCompleted()){
                task.setCompleted(false);
            }else {
                task.setCompleted(true);
                // for test
                //Calendar c = Calendar.getInstance();
                //c.add(Calendar.DATE, -1);
                //task.setCompletedDate(c.getTime());
                task.setCompletedDate(new Date());
            }
            mGoalDetailsPresenter.completeTask(task);
        });

        return convertView;
    }

    public class ViewHolder {
        public View mTaskLabel;
        public TextView mTaskTitleTv;
        public TextView mTaskHoursTv;
        public CheckBox mTaskCompletedCb;
        public TextView mTaskCompletedDateTv;
    }
}
