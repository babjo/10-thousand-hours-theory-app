package com.three.a10_thousand_hours_theory_app.model.service;

import android.content.Context;
import android.graphics.Color;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 11..
 */

@EBean
public class CreateGoalService implements Service<CreateGoalRequestDTO, CreateGoalResponseDTO> {

    private final Context mContext;
    private static final String TAG = CreateGoalService.class.getName();

    private static final Integer[] LABEL_COLORS = new Integer[]{
            Color.parseColor("#F44336"), // Red
            Color.parseColor("#E91E63"), // Pink
            Color.parseColor("#9C27B0"), // Purple
            Color.parseColor("#673AB7"), // Deep Purple
            Color.parseColor("#3F51B5"), // Indigo
            Color.parseColor("#2196F3"), // Blue
            Color.parseColor("#03A9F4"), // Light Blue
            Color.parseColor("#00BCD4"), // Cyan
            Color.parseColor("#009688"), // Teal
            Color.parseColor("#4CAF50"), // Green
            Color.parseColor("#8BC34A"), // Light Green
            Color.parseColor("#CDDC39"), // Lime
            Color.parseColor("#FFEB3B"), // Yellow
            Color.parseColor("#FFC107"), // Amber
            Color.parseColor("#FF9800"), // Orange
            Color.parseColor("#FF5722"), // Deep Orange
            Color.parseColor("#795548"), // Brown
            Color.parseColor("#9E9E9E"), // Grey
            Color.parseColor("#607D8B") // Blue Grey
    };

    @Bean
    Requery requery;

    public CreateGoalService(Context context){
        this.mContext = context;
    }

    @Override
    public CreateGoalResponseDTO execute(CreateGoalRequestDTO createGoalRequestDTO) {
        GoalEntity goalEntity = new GoalEntity();
        goalEntity.setTitle(createGoalRequestDTO.getTitle());
        goalEntity.setDescription(createGoalRequestDTO.getDescription());
        goalEntity.setDeadLineDate(createGoalRequestDTO.getDeadLineDate());
        List<TaskRuleEntity> taskRuleEntities = createGoalRequestDTO.getTasks();

        // Save Goal
        goalEntity = requery.getData().insert(goalEntity);

        List<Integer> colors = Arrays.asList(LABEL_COLORS);
        Collections.shuffle(colors);

        // Set Goal
        for (int i=0; i<taskRuleEntities.size(); i++) {
            TaskRuleEntity t = taskRuleEntities.get(i);
            int color = colors.get(i % LABEL_COLORS.length);
            t.setGoal(goalEntity);
            t.setLabelColor(color);
        }
        // Save TaskRule
        requery.getData().insert(taskRuleEntities);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, (Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK)));
        Date beginDate = cal.getTime();
        cal.add(Calendar.DATE, 6);
        Date endDate = cal.getTime();



        // Set Task
        for (TaskRuleEntity t: taskRuleEntities){
            List<TaskEntity> taskEntities = new ArrayList();

            for (int i=0; i<t.getTimes(); i++){
                TaskEntity newTaskEntity = new TaskEntity();
                newTaskEntity.setBeginDate(beginDate);
                newTaskEntity.setEndDate(endDate);
                newTaskEntity.setCompleted(false);
                newTaskEntity.setHours(t.getHours());
                newTaskEntity.setTaskRule(t);
                newTaskEntity.setTitle(t.getTitle());
                newTaskEntity.setLabelColor(t.getLabelColor());
                taskEntities.add(newTaskEntity);
            }

            // Save Task
            requery.getData().insert(taskEntities);
        }

        return new CreateGoalResponseDTO(goalEntity);
    }
}
