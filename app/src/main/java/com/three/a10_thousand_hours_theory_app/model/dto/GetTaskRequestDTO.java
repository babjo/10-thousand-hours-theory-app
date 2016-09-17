package com.three.a10_thousand_hours_theory_app.model.dto;

/**
 * Created by LCH on 2016. 9. 17..
 */
public class GetTaskRequestDTO {

    private int taskId;

    public GetTaskRequestDTO(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }
}
