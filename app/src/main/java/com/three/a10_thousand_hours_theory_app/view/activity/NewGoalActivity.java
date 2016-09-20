package com.three.a10_thousand_hours_theory_app.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.presenter.NewGoalPresenter;
import com.three.a10_thousand_hours_theory_app.view.NewGoalView;
import com.three.a10_thousand_hours_theory_app.view.adapter.NewGoalAdapter;
import com.three.a10_thousand_hours_theory_app.view.component.TaskDialog;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import link.fls.swipestack.SwipeStack;

import static com.three.a10_thousand_hours_theory_app.view.adapter.NewGoalAdapter.STEP_1;
import static com.three.a10_thousand_hours_theory_app.view.adapter.NewGoalAdapter.STEP_2;
import static com.three.a10_thousand_hours_theory_app.view.adapter.NewGoalAdapter.STEP_3;

@EActivity
public class NewGoalActivity extends AppCompatActivity implements NewGoalView {

    private final static String TAG = NewGoalActivity.class.getName();

    @ViewById(R.id.new_goal_stack)
    SwipeStack mNewGoalStack;

    @ViewById(R.id.new_goal_progress)
    ProgressBar mProgressBar;

    @Bean
    NewGoalPresenter mNewGoalPresenter;

    @Bean
    TaskDialog mTaskDialog;

    private NewGoalAdapter mNewGoalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        Intent intent = getIntent();
        int goalId = intent.getIntExtra(Const.INTENT_EXTRA_GOAL_ID, -1);

        // 생성
        if(goalId == -1) {
            mNewGoalAdapter = new NewGoalAdapter(this);
        }else{ // 수정
            mNewGoalAdapter = new NewGoalAdapter(this, new CreateGoalRequestDTO(mNewGoalPresenter.getGoal(goalId)));
            setTitle("목표 수정");
        }

        mNewGoalAdapter.setNewGoalPresenter(mNewGoalPresenter);
        mNewGoalStack.setAdapter(mNewGoalAdapter);
        mNewGoalPresenter.setNewGoalView(this);
        mNewGoalStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
            }

            @Override
            public void onViewSwipedToRight(int position) {
                if(currentStep == STEP_1){
                    mProgressBar.setProgress(3);
                    currentStep = STEP_2;
                }else if(currentStep == STEP_2){
                    mProgressBar.setProgress(6);
                    currentStep = STEP_3;
                }else{
                    mProgressBar.setProgress(0);
                    currentStep = STEP_1;
                }
                mNewGoalAdapter.saveInputs();
            }

            @Override
            public void onStackEmpty() {
                goNewGoalFormStep1();
            }
        });
    }

    private int currentStep = STEP_1;

    @Override
    public void goNewGoalFormStep1() {
        mNewGoalAdapter.clearAndAddAll(Arrays.asList(STEP_1, STEP_2, STEP_3));
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
    public void onSubmitNewGoal() {
        mProgressBar.setProgress(10);
        finish();
    }

    @Override
    public void onAddTask(TaskRuleEntity newTaskEntity) {
        mNewGoalAdapter.addRuleTasks(newTaskEntity);
        runOnUiThread(()-> {
            mNewGoalAdapter.clearAndAdd(STEP_3);
            mNewGoalStack.resetStack();
        });
    }

    @Override
    public void onModifyTask(TaskRuleEntity taskEntity) {
        runOnUiThread(()-> {
            mNewGoalAdapter.clearAndAdd(STEP_3);
            mNewGoalStack.resetStack();
        });
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

    @Override
    public void showTaskDialog(TaskRuleEntity taskEntity) {
        mTaskDialog.show(taskEntity);
    }

}
