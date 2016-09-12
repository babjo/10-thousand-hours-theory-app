package com.three.a10_thousand_hours_theory_app.view.component;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.three.a10_thousand_hours_theory_app.R;

import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 11..
 */
@EBean
public class TaskDialog {

    private Context mContext;

    public TaskDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void show(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("할 일")
        .setView(LayoutInflater.from(mContext).inflate(R.layout.task_dialog, null, false))
        .setPositiveButton("추가", (dialog, which) -> dialog.dismiss())
        .setNegativeButton("취소", (dialog, which) -> dialog.dismiss());
        Dialog d = alertDialogBuilder.create();
        d.show();
    }
}
