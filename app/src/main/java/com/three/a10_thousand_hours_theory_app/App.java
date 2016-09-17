package com.three.a10_thousand_hours_theory_app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 15..
 */

@EApplication
public class App extends Application {

    @Bean
    Requery mRequery;

    private static final String TAG = App.class.getName();

    @SystemService
    AlarmManager mAlarmManager;

    void settingAlarmManager(){
        Intent intent = new Intent(this, AlarmReceiver_.class);
        intent.setAction(Const.INTENT_ACTION_CREATE_NEW_TASKS);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);


        Calendar cal = Calendar.getInstance();
        // 다음주 월요일 00:00 태스크 생성
        cal.add(Calendar.DATE, (Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK))); // 이번주 월요일
        cal.add(Calendar.DATE, 7); // 다음주 월요일
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long interval = AlarmManager.INTERVAL_DAY;


        // for test
        /*
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        long interval = 5000;*/

        Log.d(TAG, "처음 알람 예정 시간 : " + Utils.DATE_FORMAT_yyyy_MM_dd_hh_mm_ss.format(cal.getTime()));
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, pendingIntent);
        Log.d(TAG, "알람 매니저 설정 완료");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        settingAlarmManager();
        if (BuildConfig.DEBUG) {
            try {
                GoalEntity goalEntity = new GoalEntity();
                goalEntity.setDeadLineDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-09-30"));
                goalEntity.setTitle("영어 중급 마스터");
                goalEntity.setDescription("할 수 있다. 화이팅");
                goalEntity.setType(Const.GOAL_TYPE_DEADLINE);
                mRequery.getData().insert(goalEntity);


                TaskRuleEntity ruleEntity1 = new TaskRuleEntity();
                ruleEntity1.setTitle("영어 단어 100개 외우기");
                ruleEntity1.setLabelColor(Const.LABEL_COLORS[0]);
                ruleEntity1.setGoal(goalEntity);
                ruleEntity1.setStartDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-08-01"));
                ruleEntity1.setHours(2);
                ruleEntity1.setTimes(2);

                TaskRuleEntity ruleEntity2 = new TaskRuleEntity();
                ruleEntity2.setTitle("영어 딕테이션");
                ruleEntity2.setLabelColor(Const.LABEL_COLORS[1]);
                ruleEntity2.setGoal(goalEntity);
                ruleEntity2.setStartDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-08-20"));
                ruleEntity2.setHours(2);
                ruleEntity2.setTimes(2);
                mRequery.getData().insert(Arrays.asList(ruleEntity1, ruleEntity2));

                List<TaskEntity> taskEntities = new ArrayList();
                taskEntities.add(createTask(goalEntity, ruleEntity1, "08-01", "08-01", "08-07"));
                taskEntities.add(createTask(goalEntity, ruleEntity1, "08-02", "08-01", "08-07"));
                taskEntities.add(createTask(goalEntity, ruleEntity1, "08-03", "08-01", "08-07"));
                taskEntities.add(createTask(goalEntity, ruleEntity1, "08-04", "08-01", "08-07"));
                taskEntities.add(createTask(goalEntity, ruleEntity1, "08-05", "08-01", "08-07"));
                taskEntities.add(createTask(goalEntity, ruleEntity1, "08-06", "08-01", "08-07"));
                taskEntities.add(createTask(goalEntity, ruleEntity1, "08-07", "08-01", "08-07"));

                taskEntities.add(createTask(goalEntity, ruleEntity2, "09-26", "09-26", "10-02"));
                taskEntities.add(createTask(goalEntity, ruleEntity2, "09-27", "09-26", "10-02"));
                taskEntities.add(createTask(goalEntity, ruleEntity2, "09-28", "09-26", "10-02"));
                taskEntities.add(createTask(goalEntity, ruleEntity2, "09-29", "09-26", "10-02"));
                taskEntities.add(createTask(goalEntity, ruleEntity2, "09-30", "09-26", "10-02"));
                taskEntities.add(createTask(goalEntity, ruleEntity2, "10-01", "09-26", "10-02"));
                taskEntities.add(createTask(goalEntity, ruleEntity2, "10-02", "09-26", "10-02"));
                taskEntities.add(createTask(goalEntity, ruleEntity2, "10-03", "10-03", "10-09"));

                mRequery.getData().insert(taskEntities);
            }catch (Exception e){
            }
        }
    }

    @NonNull
    private TaskEntity createTask(GoalEntity goalEntity, TaskRuleEntity ruleEntity, String completed, String begin, String end) throws ParseException {
        TaskEntity newTask;
        newTask = new TaskEntity();
        newTask.setCompleted(true);
        newTask.setCompletedDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+completed));
        newTask.setGoal(goalEntity);
        newTask.setTitle(ruleEntity.getTitle());
        newTask.setHours(ruleEntity.getHours());
        newTask.setMinutesLeft(ruleEntity.getHours() * 60);
        newTask.setLabelColor(ruleEntity.getLabelColor());
        newTask.setBeginDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+begin));
        newTask.setEndDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+end));
        return newTask;
    }
}
