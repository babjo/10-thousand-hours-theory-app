package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetAllGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetAllGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 13..
 */

@EBean
public class GetAllGoalSyncUseCase implements SyncUseCase<GetAllGoalRequestDTO, GetAllGoalResponseDTO> {

    @Bean
    Requery requery;

    @Override
    public GetAllGoalResponseDTO execute(GetAllGoalRequestDTO arg) {
        List<GoalEntity> goals = requery.getData().select(GoalEntity.class).get().toList();
        return new GetAllGoalResponseDTO(goals);
    }
}
