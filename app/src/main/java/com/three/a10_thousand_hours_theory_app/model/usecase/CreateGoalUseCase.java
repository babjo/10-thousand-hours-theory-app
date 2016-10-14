package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by LCH on 2016. 9. 30..
 */

@EBean
public class CreateGoalUseCase extends UseCase<CreateGoalRequestDTO>{

    @Bean
    Requery requery;

    @Override
    protected Observable buildUseCaseObservable(CreateGoalRequestDTO createGoalRequestDTO) {
        return Observable.create(subscriber -> {
            GoalEntity goalEntity = new GoalEntity();
            goalEntity.setTitle(createGoalRequestDTO.getTitle());
            goalEntity.setDescription(createGoalRequestDTO.getDescription());
            goalEntity.setType(createGoalRequestDTO.getGoalType());
            goalEntity.setStartDate(new Date());
            if(createGoalRequestDTO.getGoalType() == Const.GOAL_TYPE_DEADLINE)
                goalEntity.setDeadLineDate(createGoalRequestDTO.getDeadLineDate());
            else
                goalEntity.setGoalHours(createGoalRequestDTO.getGoalHours());
            List<TaskRuleEntity> taskRuleEntities = createGoalRequestDTO.getTaskRuleEntities();

            // Save Goal
            goalEntity = requery.getData().insert(goalEntity);

            List<Integer> colors = Arrays.asList(Const.LABEL_COLORS);
            Collections.shuffle(colors);

            // Set TaskRule
            for (int i=0; i<taskRuleEntities.size(); i++) {
                TaskRuleEntity t = taskRuleEntities.get(i);
                int color = colors.get(i % Const.LABEL_COLORS.length);
                t.setGoal(goalEntity);
                t.setLabelColor(color);
                t.setStartDate(new Date());
            }
            // Save TaskRule
            requery.getData().insert(taskRuleEntities);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, (Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK))); // 그 주 월요일
            cal.add(Calendar.DATE, 6); // 그 주 일요일
            Date endDate = cal.getTime();

            // Set Task
            for (TaskRuleEntity t: taskRuleEntities){
                List<TaskEntity> taskEntities = new ArrayList();

                for (int i=0; i<t.getTimes(); i++){
                    TaskEntity newTaskEntity = new TaskEntity();
                    newTaskEntity.setBeginDate(t.getStartDate());
                    newTaskEntity.setEndDate(endDate);
                    newTaskEntity.setCompleted(false);
                    newTaskEntity.setHours(t.getHours());
                    newTaskEntity.setGoal(goalEntity);
                    newTaskEntity.setTitle(t.getTitle());
                    newTaskEntity.setLabelColor(t.getLabelColor());
                    newTaskEntity.setSecondsLeft(t.getHours() * 60 * 60);
                    taskEntities.add(newTaskEntity);
                }

                // Save Task
                requery.getData().insert(taskEntities);
            }

            subscriber.onNext(new CreateGoalResponseDTO(goalEntity));
            subscriber.onCompleted();
        });
    }
}
