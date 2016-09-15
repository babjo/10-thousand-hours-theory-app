package com.three.a10_thousand_hours_theory_app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.Utils;
import com.three.a10_thousand_hours_theory_app.model.domain.Task;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRule;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by LCH on 2016. 9. 15..
 */

public class TaskAdapter extends BaseAdapter{

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<TaskRule> mTaskRules;
    private List<Task> mTasks;

    public TaskAdapter(Context mContext, List<TaskRule> taskRules) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mTaskRules = taskRules;

        this.mTasks = new ArrayList();
        for (TaskRule r : mTaskRules){
            mTasks.addAll(r.getTasks());
        }
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Task getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v;
        Task task = getItem(position);

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
