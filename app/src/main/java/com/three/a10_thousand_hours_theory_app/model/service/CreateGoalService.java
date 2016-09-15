package com.three.a10_thousand_hours_theory_app.model.service;

import android.content.Context;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.CreateGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 11..
 */

@EBean
public class CreateGoalService implements Service<CreateGoalRequestDTO, CreateGoalResponseDTO> {

    private final Context mContext;
    private static final String TAG = CreateGoalService.class.getName();

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
        goalEntity = requery.getData().insert(goalEntity);
        List<TaskEntity> taskEntities = createGoalRequestDTO.getTasks();
        for (TaskEntity t: taskEntities) t.setGoal(goalEntity);
        requery.getData().insert(taskEntities);
        return new CreateGoalResponseDTO(goalEntity);
    }
}
