package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 13..
 */

@EBean
public class GetGoalSyncUseCase implements SyncUseCase<GetGoalRequestDTO, GetGoalResponseDTO> {

    @Bean
    Requery mRequery;

    @Override
    public GetGoalResponseDTO execute(GetGoalRequestDTO arg) {
        GoalEntity g = mRequery.getData().findByKey(GoalEntity.class, arg.getGoalId());
        return new GetGoalResponseDTO(g);
    }
}
