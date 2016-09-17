package com.three.a10_thousand_hours_theory_app.model.service;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetTaskResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 17..
 */

@EBean
public class GetTaskService implements Service<GetTaskRequestDTO, GetTaskResponseDTO> {

    @Bean
    Requery mRequery;

    @Override
    public GetTaskResponseDTO execute(GetTaskRequestDTO arg) {
        TaskEntity taskEntity = mRequery.getData().findByKey(TaskEntity.class, arg.getTaskId());
        return new GetTaskResponseDTO(taskEntity);
    }
}
