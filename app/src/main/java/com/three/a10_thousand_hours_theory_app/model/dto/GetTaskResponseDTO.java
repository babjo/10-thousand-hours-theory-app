package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by LCH on 2016. 9. 17..
 */
@AllArgsConstructor
@Getter
public class GetTaskResponseDTO {
    private final TaskEntity taskEntity;
}
