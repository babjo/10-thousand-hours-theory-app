package com.three.a10_thousand_hours_theory_app.model.dto;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;

/**
 * Created by LCH on 2016. 9. 15..
 */

public class SaveTaskResponseDTO {

    private TaskEntity taskEntity;

    public SaveTaskResponseDTO(TaskEntity taskEntity) {
        this.taskEntity = taskEntity;
    }

    public TaskEntity getTaskEntity() {
        return taskEntity;
    }
}
