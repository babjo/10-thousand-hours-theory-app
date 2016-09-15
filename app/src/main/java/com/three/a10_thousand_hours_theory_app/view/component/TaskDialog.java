package com.three.a10_thousand_hours_theory_app.view.component;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.presenter.NewGoalPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Arrays;

/**
 * Created by LCH on 2016. 9. 11..
 */
@EBean
public class TaskDialog {

    private final static String TAG = TaskDialog.class.getName();
    private Context mContext;
    private static final String[] TIMES = new String[]{"1", "2", "3", "4", "5", "6", "7"};
    private static final String[] HOURS = new String[]{"1", "2", "3", "4", "5", "6", "7"};

    @Bean
    NewGoalPresenter mNewGoalPresenter;
    private Dialog dialog;


    public TaskDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void show(TaskRuleEntity taskEntity){
        View v = LayoutInflater.from(mContext).inflate(R.layout.new_task_dialog, null, false);
        EditText titleEt = (EditText) v.findViewById(R.id.task_title_et);
        WheelView timeWheelView = (WheelView) v.findViewById(R.id.time_wv);
        timeWheelView.setItems(Arrays.asList(TIMES));
        WheelView hourWheelView = (WheelView) v.findViewById(R.id.hour_wv);
        hourWheelView.setItems(Arrays.asList(HOURS));

        // 수정시
        if(taskEntity != null){
            titleEt.setText(taskEntity.getTitle());
            titleEt.setSelection(titleEt.getText().length());
            timeWheelView.setSeletion(taskEntity.getTimes()-1);
            hourWheelView.setSeletion(taskEntity.getHours()-1);
        }
        else{
            timeWheelView.setSeletion(1);
        }


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("할 일").setView(v);

        // 수정시
        if(taskEntity != null) {
            alertDialogBuilder
                    .setPositiveButton("수정", (dialog, which) -> {
                        setTaskEntity(taskEntity, titleEt, timeWheelView, hourWheelView);
                        mNewGoalPresenter.modifyTask(taskEntity);
                        dialog.dismiss();
                    });

            // 키보드 엔터
            titleEt.setOnEditorActionListener((textView, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setTaskEntity(taskEntity, titleEt, timeWheelView, hourWheelView);
                    mNewGoalPresenter.modifyTask(taskEntity);
                    dialog.dismiss();
                    return true;
                }
                return false;
            });
        }else{
            alertDialogBuilder
                    .setPositiveButton("추가", (dialog, which) -> {
                        TaskRuleEntity newTask = newTaskEntity(titleEt, timeWheelView, hourWheelView);
                        mNewGoalPresenter.addTask(newTask);
                        dialog.dismiss();
                    });

            // 키보드 엔터
            titleEt.setOnEditorActionListener((textView, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    TaskRuleEntity newTask = newTaskEntity(titleEt, timeWheelView, hourWheelView);
                    mNewGoalPresenter.addTask(newTask);
                    dialog.dismiss();
                    return true;
                }
                return false;
            });
        }

        alertDialogBuilder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());
        dialog = alertDialogBuilder.create();
        dialog.show();
    }

    @NonNull
    private TaskRuleEntity newTaskEntity(EditText titleEt, WheelView timeWheelView, WheelView hourWheelView) {
        String title = titleEt.getText().toString();
        int times = Integer.parseInt(timeWheelView.getSeletedItem());
        int hours = Integer.parseInt(hourWheelView.getSeletedItem());

        TaskRuleEntity newTask = new TaskRuleEntity();
        newTask.setTitle(title);
        newTask.setTimes(times);
        newTask.setHours(hours);

        Log.d(TAG, String.format("할일추가 %s, %dialog, %dialog", title, times, hours));
        return newTask;
    }

    @NonNull
    private void setTaskEntity(TaskRuleEntity taskEntity, EditText titleEt, WheelView timeWheelView, WheelView hourWheelView) {
        String title = titleEt.getText().toString();
        int times = Integer.parseInt(timeWheelView.getSeletedItem());
        int hours = Integer.parseInt(hourWheelView.getSeletedItem());

        taskEntity.setTitle(title);
        taskEntity.setHours(hours);
        taskEntity.setTimes(times);

        Log.d(TAG, String.format("할일수정 %s, %dialog, %dialog", title, times, hours));
    }
}
