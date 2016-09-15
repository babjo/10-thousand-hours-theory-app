package com.three.a10_thousand_hours_theory_app.model.service;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.SaveTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.SaveTaskResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 15..
 */

@EBean
public class SaveTaskService implements Service<SaveTaskRequestDTO, SaveTaskResponseDTO> {

    @Bean
    Requery mRequery;

    @Override
    public SaveTaskResponseDTO execute(SaveTaskRequestDTO arg) {
        TaskEntity updated = mRequery.getData().update(arg.getTaskEntity());
        return new SaveTaskResponseDTO(updated);
    }
}
