package com.three.a10_thousand_hours_theory_app.model.service;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskRuleEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by LCH on 2016. 9. 15..
 */

@EBean
public class UpdateGoalService implements Service<UpdateGoalRequestDTO, UpdateGoalResponseDTO> {

    @Bean
    Requery mRequery;

    @Override
    public UpdateGoalResponseDTO execute(UpdateGoalRequestDTO arg) {

        List<Integer> colors = Arrays.asList(Const.LABEL_COLORS);
        Collections.shuffle(colors);
        List<TaskRuleEntity> taskRuleEntities = arg.getTaskRuleEntities();

        for (int i=0; i<taskRuleEntities.size(); i++) {
            TaskRuleEntity t = taskRuleEntities.get(i);
            if(t.getId() == 0) {
                t.setGoal(arg.getGoalEntity());
                t.setLabelColor(colors.get(i % Const.LABEL_COLORS.length));
                t.setStartDate(new Date());
            }
        }

        mRequery.getData().update(arg.getGoalEntity());
        return new UpdateGoalResponseDTO();
    }
}
