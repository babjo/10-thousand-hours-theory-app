package com.three.a10_thousand_hours_theory_app;

import android.app.Application;
import android.support.annotation.NonNull;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.TaskAlarmManager;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 15..
 */

@EApplication
public class App extends Application {

    @Bean
    Requery mRequery;

    @Bean
    TaskAlarmManager mTaskAlarmManager;

    private static final String TAG = App.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mTaskAlarmManager.setting();

        if (BuildConfig.DEBUG) {
            int count = mRequery.getData().count(GoalEntity.class).get().value();
            if(count == 0) {
                try {
                    GoalEntity goalEntity = new GoalEntity();
                    goalEntity.setStartDate(new Date());
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
                } catch (Exception e) {
                }
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
        newTask.setSecondsLeft(ruleEntity.getHours());
        newTask.setLabelColor(ruleEntity.getLabelColor());
        newTask.setBeginDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+begin));
        newTask.setEndDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+end));
        return newTask;
    }
}
