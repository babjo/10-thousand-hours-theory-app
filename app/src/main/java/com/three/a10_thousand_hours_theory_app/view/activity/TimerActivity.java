package com.three.a10_thousand_hours_theory_app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.R;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.presenter.TimerPresenter;
import com.three.a10_thousand_hours_theory_app.view.TimerView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

import static com.three.a10_thousand_hours_theory_app.Utils.gethhmmss;

@EActivity
public class TimerActivity extends AppCompatActivity implements TimerView{

    @Bean
    TimerPresenter mTimerPresenter;

    @ViewById(R.id.task_timer_pg)
    ProgressBar mProgressBar;

    @ViewById(R.id.task_timer_min_left_tv)
    TextView mTimerMinutesLeftTv;

    @ViewById(R.id.timer_start_iv)
    ImageView mTimerStartIv;

    private TimerRunnable mTimerRunnable;
    private TaskEntity mTaskEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


        mTimerPresenter.setTimerView(this);
        Intent intent = getIntent();
        int taskId = intent.getIntExtra(Const.INTENT_EXTRA_TASK_ID, -1);

        if(taskId != -1){
            mTimerPresenter.startTimer(taskId);
        }else{
        }
    }

    @Click(R.id.timer_start_iv)
    public void timerStart(){
        if(mIsRunning){
            pauseTimer();
        }else{
            startTimer();
        }
    }

    private void startTimer() {
        mTimerStartIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black_48dp));
        mTimerRunnable = new TimerRunnable();
        mTimerHandler.postDelayed(mTimerRunnable, 0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void pauseTimer() {
        mTimerStartIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_black_48dp));
        mTimerHandler.removeCallbacks(mTimerRunnable);
        mIsRunning = false;
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Click(R.id.timer_stop_iv)
    public void timerStop(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("정지").setMessage("할 일을 멈추고 이전으로 돌아갑니다.");
        b.setPositiveButton("확인", (dialog, which) -> {
            finish();
            dialog.dismiss();
        }).setNegativeButton("취소", (dialog, which) -> {
            dialog.dismiss();
        }).show();
    }

    boolean mIsRunning = false;

    @Override
    public void onTimerStart(TaskEntity taskEntity) {
        mTaskEntity = taskEntity;
        setTitle(taskEntity.getTitle());
        startTimer();
    }

    private Handler mTimerHandler = new Handler();
    class TimerRunnable implements Runnable {
        @Override
        public void run() {
            if(mIsRunning) {
                mTaskEntity.setSecondsLeft(mTaskEntity.getSecondsLeft() - 1);
                if(mTaskEntity.getSecondsLeft() == 0) {
                    mTaskEntity.setCompleted(true);
                    mTaskEntity.setCompletedDate(new Date());
                    mTimerHandler.removeCallbacks(mTimerRunnable);
                    finish();
                }
                mTimerPresenter.updateTask(mTaskEntity);
            }else{
                mIsRunning = true;
            }
            mTimerMinutesLeftTv.setText(gethhmmss(mTaskEntity.getSecondsLeft()));

            int progress = (int)((mTaskEntity.getHours()*3600.0 - mTaskEntity.getSecondsLeft())/(mTaskEntity.getHours()*3600.0) * 100);
            mProgressBar.setProgress(progress);
            mTimerHandler.postDelayed(this, 1000); // 1000 : 1초
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimerPresenter.destroy();
    }
}
