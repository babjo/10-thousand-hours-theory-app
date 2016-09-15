package com.three.a10_thousand_hours_theory_app.model.service;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.DeleteGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.DeleteGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 15..
 */

@EBean
public class DeleteGoalService implements Service<DeleteGoalRequestDTO, DeleteGoalResponseDTO>{

    @Bean
    Requery mRequery;

    @Override
    public DeleteGoalResponseDTO execute(DeleteGoalRequestDTO arg) {
        GoalEntity g = mRequery.getData().findByKey(GoalEntity.class, arg.getGoalId());
        mRequery.getData().delete(g);
        return new DeleteGoalResponseDTO();
    }
}
