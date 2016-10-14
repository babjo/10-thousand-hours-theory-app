package com.three.a10_thousand_hours_theory_app.infrastructure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.usecase.DefaultSubscriber;
import com.three.a10_thousand_hours_theory_app.model.usecase.UpdateGoalUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.UseCase;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EReceiver;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 16..
 */

@EReceiver
public class AlarmReceiver extends BroadcastReceiver {

    @Bean
    Requery mRequery;

    @Bean(UpdateGoalUseCase.class)
    UseCase<UpdateGoalRequestDTO> mUpdateGoalUseCase;

    @Bean
    TaskAlarmManager mTaskAlarmManager;

    private final static String TAG = AlarmReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, String.format("Received Intent Action : %s", intent.getAction()));
        if (intent.getAction().equals(Const.INTENT_ACTION_CREATE_NEW_TASKS)) {

            // 월요일 00:00 보다 데드라인 날짜가 큰 경우, 목표 시간이 남은 경우
            List<GoalEntity> goalEntities = mRequery.getData().select(GoalEntity.class).where(GoalEntity.DEAD_LINE_DATE.gt(new Date()).or(GoalEntity.GOAL_HOURS.gt(0))).get().toList();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, (Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK))); // 그 주 월요일
            cal.add(Calendar.DATE, 6); // 그 주 일요일

            for (GoalEntity goalEntity : goalEntities){
                List<TaskRuleEntity> taskRuleEntities = goalEntity.getTaskRules();
                for (TaskRuleEntity taskRuleEntity : taskRuleEntities){
                    for (int i=0; i<taskRuleEntity.getTimes(); i++){
                        TaskEntity newTaskEntity = new TaskEntity();
                        newTaskEntity.setBeginDate(new Date());
                        newTaskEntity.setEndDate(cal.getTime());
                        newTaskEntity.setLabelColor(taskRuleEntity.getLabelColor());
                        newTaskEntity.setTitle(taskRuleEntity.getTitle());
                        newTaskEntity.setHours(taskRuleEntity.getHours());
                        if(goalEntity.getType() == Const.GOAL_TYPE_HOURS)
                            goalEntity.setGoalHours(goalEntity.getGoalHours() - taskRuleEntity.getHours());
                        newTaskEntity.setCompleted(false);
                        newTaskEntity.setGoal(goalEntity);
                        goalEntity.getTasks().add(newTaskEntity);
                        Log.d(TAG, String.format("새로운 테스트 추가 (목표 : %s, 테스크 : %s)", goalEntity.getTitle(), newTaskEntity.getTitle()));
                    }
                }
                mUpdateGoalUseCase.execute(new UpdateGoalRequestDTO(goalEntity), new DefaultSubscriber());
            }
        } else if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            mTaskAlarmManager.setting();
        }
    }

}
