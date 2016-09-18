package com.three.a10_thousand_hours_theory_app.model.service;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 15..
 */

@EBean
public class UpdateGoalService implements Service<UpdateGoalRequestDTO, UpdateGoalResponseDTO> {

    @Bean
    Requery mRequery;

    @Override
    public UpdateGoalResponseDTO execute(UpdateGoalRequestDTO arg) {
        GoalEntity goalEntity = arg.getGoalEntity();

        if(goalEntity.getType() == Const.GOAL_TYPE_HOURS)
            goalEntity.setDeadLineDate(null);
        else
            goalEntity.setGoalHours(0);

        mRequery.getData().update(goalEntity);
        return new UpdateGoalResponseDTO();
    }
}
