package com.three.a10_thousand_hours_theory_app;

import android.app.Application;
import android.support.annotation.NonNull;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 15..
 */

@EApplication
public class App extends Application {

    @Bean
    Requery mRequery;

    @Override
    public void onCreate() {
        super.onCreate();

        //mRequery.getData().delete(Goal.class).where();
        if (BuildConfig.DEBUG) {
            try {
                GoalEntity goalEntity = new GoalEntity();
                goalEntity.setDeadLineDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-09-30"));
                goalEntity.setTitle("영어 중급 마스터");
                goalEntity.setDescription("할 수 있다. 화이팅");
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
        TaskEntity newT1;
        newT1 = new TaskEntity();
        newT1.setCompleted(true);
        newT1.setCompletedDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+completed));
        newT1.setGoal(goalEntity);
        newT1.setTitle(ruleEntity.getTitle());
        newT1.setHours(ruleEntity.getHours());
        newT1.setLabelColor(ruleEntity.getLabelColor());
        newT1.setBeginDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+begin));
        newT1.setEndDate(Utils.DATE_FORMAT_yyyy_MM_dd.parse("2016-"+end));
        return newT1;
    }
}