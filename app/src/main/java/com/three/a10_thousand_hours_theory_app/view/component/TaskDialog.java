package com.three.a10_thousand_hours_theory_app.view.component;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.three.a10_thousand_hours_theory_app.R;

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

    public TaskDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void show(){
        View v = LayoutInflater.from(mContext).inflate(R.layout.task_dialog, null, false);

        WheelView timeWheelView = (WheelView) v.findViewById(R.id.time_wv);
        timeWheelView.setItems(Arrays.asList(TIMES));
        timeWheelView.setSeletion(1);
        timeWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });

        WheelView hourWheelView = (WheelView) v.findViewById(R.id.hour_wv);
        hourWheelView.setItems(Arrays.asList(HOURS));
        hourWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
            }
        });


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("할 일")
        .setView(v)
        .setPositiveButton("추가", (dialog, which) -> dialog.dismiss())
        .setNegativeButton("취소", (dialog, which) -> dialog.dismiss());
        Dialog d = alertDialogBuilder.create();
        d.show();
    }
}
