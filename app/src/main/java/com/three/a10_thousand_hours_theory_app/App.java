package com.three.a10_thousand_hours_theory_app;

import android.app.Application;
import android.support.annotation.NonNull;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.infrastructure.Requery;
import com.three.a10_thousand_hours_theory_app.infrastructure.TaskAlarmManager;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        mTaskAlarmManager.setting();

        if (BuildConfig.DEBUG) {
            int count = mRequery.getData().count(GoalEntity.class).get().value();
            if(count == 0) {
                try {
                    //createLearningEnglishGoal();
                    createDietGoal();
                    createGradeGoal();
                } catch (Exception e) {
                }
            }
        }
    }

    private void createLearningEnglishGoal() throws ParseException {
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
    }

    private void createDietGoal() throws ParseException {
        // 주중에 3번 랜덤으로 하자.
        /*
        *   다이어트 5kg 감량
            2달 (저번달 초 ~ 이번달까지)
            매주 3번 런닝머신 1시간
            매주 3번 근력운동 1시간
        */

        GoalEntity goalEntity = new GoalEntity();
        goalEntity.setStartDate(new Date());
        goalEntity.setDeadLineDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-09-30"));
        goalEntity.setTitle("다이어트 5kg 감량");
        goalEntity.setDescription("진짜 이번에는 빼자");
        goalEntity.setType(Const.GOAL_TYPE_DEADLINE);
        mRequery.getData().insert(goalEntity);


        TaskRuleEntity ruleEntity1 = new TaskRuleEntity();
        ruleEntity1.setTitle("런닝머신");
        ruleEntity1.setLabelColor(Const.LABEL_COLORS[2]);
        ruleEntity1.setGoal(goalEntity);
        ruleEntity1.setStartDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-08-01"));
        ruleEntity1.setHours(1);
        ruleEntity1.setTimes(3);

        TaskRuleEntity ruleEntity2 = new TaskRuleEntity();
        ruleEntity2.setTitle("근력운동");
        ruleEntity2.setLabelColor(Const.LABEL_COLORS[3]);
        ruleEntity2.setGoal(goalEntity);
        ruleEntity2.setStartDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-08-01"));
        ruleEntity2.setHours(1);
        ruleEntity2.setTimes(3);
        mRequery.getData().insert(Arrays.asList(ruleEntity1, ruleEntity2));

        List<TaskEntity> taskEntities = new ArrayList();
        Random random = new Random(new Date().getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-08-01"));

        boolean deadline = false;
        while(!deadline) {
            c.add(Calendar.DATE, (Calendar.MONDAY - c.get(Calendar.DAY_OF_WEEK))); // 그 주 월요일
            Date monday = c.getTime();
            c.add(Calendar.DATE, random.nextInt(7));
            Date completed = c.getTime();
            c.add(Calendar.DATE, (Calendar.SUNDAY - c.get(Calendar.DAY_OF_WEEK))); // 그 주 일요일
            Date sunday = c.getTime();
            taskEntities.add(createTask(goalEntity, ruleEntity1, completed, monday, sunday));
            taskEntities.add(createTask(goalEntity, ruleEntity2, completed, monday, sunday));
            c.add(Calendar.DATE, 1);

            if (c.getTime().after(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-09-19"))){
                deadline = true;
                taskEntities.add(createTask(goalEntity, ruleEntity1, null, monday, sunday));
                taskEntities.add(createTask(goalEntity, ruleEntity2, null, monday, sunday));
            }
        }

        mRequery.getData().insert(taskEntities);
    }

    private void createGradeGoal() throws ParseException {
        // 주중에 3번 랜덤으로 하자.
        /*
        *   학점 A+ 달성
            3달 (저저번달 초 ~ 이번달까지)
            매주 3번 수업 예습
            매주 3번 수업 복습

        */

        GoalEntity goalEntity = new GoalEntity();
        goalEntity.setStartDate(new Date());
        goalEntity.setDeadLineDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-09-30"));
        goalEntity.setTitle("프로그래밍 수업 학점 A+ 달성");
        goalEntity.setDescription("장학금 받아야한다.");
        goalEntity.setType(Const.GOAL_TYPE_DEADLINE);
        mRequery.getData().insert(goalEntity);


        TaskRuleEntity ruleEntity1 = new TaskRuleEntity();
        ruleEntity1.setTitle("예습");
        ruleEntity1.setLabelColor(Const.LABEL_COLORS[1]);
        ruleEntity1.setGoal(goalEntity);
        ruleEntity1.setStartDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-08-01"));
        ruleEntity1.setHours(1);
        ruleEntity1.setTimes(3);

        TaskRuleEntity ruleEntity2 = new TaskRuleEntity();
        ruleEntity2.setTitle("복습");
        ruleEntity2.setLabelColor(Const.LABEL_COLORS[5]);
        ruleEntity2.setGoal(goalEntity);
        ruleEntity2.setStartDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-08-01"));
        ruleEntity2.setHours(1);
        ruleEntity2.setTimes(3);
        mRequery.getData().insert(Arrays.asList(ruleEntity1, ruleEntity2));

        List<TaskEntity> taskEntities = new ArrayList();
        Random random = new Random(new Date().getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-08-01"));

        boolean deadline = false;
        while(!deadline) {
            c.add(Calendar.DATE, (Calendar.MONDAY - c.get(Calendar.DAY_OF_WEEK))); // 그 주 월요일
            Date monday = c.getTime();
            c.add(Calendar.DATE, random.nextInt(7));
            Date completed = c.getTime();
            c.add(Calendar.DATE, (Calendar.SUNDAY - c.get(Calendar.DAY_OF_WEEK))); // 그 주 일요일
            Date sunday = c.getTime();
            taskEntities.add(createTask(goalEntity, ruleEntity1, completed, monday, sunday));
            taskEntities.add(createTask(goalEntity, ruleEntity2, completed, monday, sunday));
            c.add(Calendar.DATE, 1);

            if (c.getTime().after(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-09-19"))){
                deadline = true;
                taskEntities.add(createTask(goalEntity, ruleEntity1, null, monday, sunday));
                taskEntities.add(createTask(goalEntity, ruleEntity2, null, monday, sunday));
            }
        }

        mRequery.getData().insert(taskEntities);
    }

    @NonNull
    private TaskEntity createTask(GoalEntity goalEntity, TaskRuleEntity ruleEntity, Date completed, Date begin, Date end) throws ParseException {
        TaskEntity newTask;
        newTask = new TaskEntity();
        if(completed == null)
            newTask.setCompleted(false);
        else
            newTask.setCompleted(true);
        newTask.setCompletedDate(completed);
        newTask.setGoal(goalEntity);
        newTask.setTitle(ruleEntity.getTitle());
        newTask.setHours(ruleEntity.getHours());
        newTask.setSecondsLeft(ruleEntity.getHours() * 3600);
        newTask.setLabelColor(ruleEntity.getLabelColor());
        newTask.setBeginDate(begin);
        newTask.setEndDate(end);
        return newTask;
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
        newTask.setSecondsLeft(ruleEntity.getHours() * 3600);
        newTask.setLabelColor(ruleEntity.getLabelColor());
        newTask.setBeginDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+begin));
        newTask.setEndDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+end));
        return newTask;
    }
}
