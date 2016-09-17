package com.three.a10_thousand_hours_theory_app.model.service;

import com.three.a10_thousand_hours_theory_app.model.dto.UpdateTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateTaskResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 15..
 */

@EBean
public class UpdateTaskService implements Service<UpdateTaskRequestDTO, UpdateTaskResponseDTO> {

    @Bean
    Requery mRequery;

    @Override
    public UpdateTaskResponseDTO execute(UpdateTaskRequestDTO arg) {
        mRequery.getData().update(arg.getTaskEntity());
        return new UpdateTaskResponseDTO();
    }
}
