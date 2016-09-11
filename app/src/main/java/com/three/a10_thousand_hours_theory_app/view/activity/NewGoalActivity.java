package com.three.a10_thousand_hours_theory_app.view.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.presenter.NewGoalPresenter;
import com.three.a10_thousand_hours_theory_app.view.NewGoalView;
import com.three.a10_thousand_hours_theory_app.view.adapter.NewGoalAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

import link.fls.swipestack.SwipeStack;

@EActivity
public class NewGoalActivity extends AppCompatActivity implements NewGoalView {

    private final static String TAG = NewGoalActivity.class.getName();

    @ViewById(R.id.new_goal_stack)
    SwipeStack mNewGoalStack;

    @ViewById(R.id.new_goal_progress)
    ProgressBar mProgressBar;

    @Bean
    NewGoalPresenter mNewGoalPresenter;
    private NewGoalAdapter mNewGoalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        mNewGoalAdapter = new NewGoalAdapter(this);
        mNewGoalAdapter.setNewGoalPresenter(mNewGoalPresenter);
        mNewGoalStack.setAdapter(mNewGoalAdapter);
        mNewGoalPresenter.setNewGoalView(this);
        mNewGoalStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
            }

            @Override
            public void onViewSwipedToRight(int position) {
                mNewGoalAdapter.saveInputs();
            }

            @Override
            public void onStackEmpty() {
            }
        });
    }

    @Override
    public void goNewGoalFormStep1() {
        mNewGoalStack.resetStack();
        mProgressBar.setProgress(0);
    }

    @Override
    public void goNewGoalFormStep2() {
        mNewGoalStack.swipeTopViewToRight();
        mProgressBar.setProgress(3);
    }

    @Override
    public void goNewGoalFormStep3() {
        mNewGoalStack.swipeTopViewToRight();
        mProgressBar.setProgress(6);
    }

    @Override
    public void showDatePicker(Date deadLineDate) {
        final Calendar c = Calendar.getInstance();

        if(deadLineDate != null)
            c.setTime(deadLineDate);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.clear();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date deadLine = c.getTime();
                mNewGoalAdapter.setNewGoalDeadLineDate(deadLine);
            }
        }, year, month, day);
        datePickerDialog.setOnCancelListener(dialog -> dialog.dismiss());
        datePickerDialog.show();
    }

}
