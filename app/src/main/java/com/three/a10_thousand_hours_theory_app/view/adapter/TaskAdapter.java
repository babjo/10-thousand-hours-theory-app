package com.three.a10_thousand_hours_theory_app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.presenter.NewGoalPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 11..
 */
public class TaskAdapter extends BaseAdapter{

    private final List<TaskEntity> tasks;
    private final Context mContext;
    private final LayoutInflater mInflater;

    private NewGoalPresenter mNewGoalPresenter;

    public TaskAdapter(Context mContext) {
        this(mContext, new ArrayList());
    }

    public TaskAdapter(Context mContext, List<TaskEntity> tasks) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tasks = tasks;
    }

    public void setNewGoalPresenter(NewGoalPresenter mNewGoalPresenter) {
        this.mNewGoalPresenter = mNewGoalPresenter;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public TaskEntity getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v;
        TaskEntity t = getItem(position);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.task_list_item, parent, false);
            v = new ViewHolder();
            v.mNewTaskTitleTv = (TextView) convertView.findViewById(R.id.new_task_title_tv);
            v.mEditIv = (ImageView) convertView.findViewById(R.id.edit_new_task_iv);
            convertView.setOnClickListener(view->mNewGoalPresenter.showTaskDialog(t));
            convertView.setTag(v);
        }else{
            v = (ViewHolder) convertView.getTag();
        }
        v.mNewTaskTitleTv.setText(t.getTitle());

        return convertView;
    }

    public class ViewHolder {
        public TextView mNewTaskTitleTv;
        public ImageView mEditIv;
    }

}
