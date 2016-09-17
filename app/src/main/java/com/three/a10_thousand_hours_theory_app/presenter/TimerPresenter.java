package com.three.a10_thousand_hours_theory_app.presenter;

import android.content.Context;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetTaskResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.service.GetTaskService;
import com.three.a10_thousand_hours_theory_app.model.service.Service;
import com.three.a10_thousand_hours_theory_app.model.service.UpdateTaskService;
import com.three.a10_thousand_hours_theory_app.view.TimerView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 17..
 */

@EBean
public class TimerPresenter {

    private Context mContext;

    @Bean(GetTaskService.class)
    Service mGetTaskService;

    @Bean(UpdateTaskService.class)
    Service mUpdateTaskService;

    private TimerView mTimerView;

    public TimerPresenter(Context context) {
        this.mContext = context;
    }

    public void startTimer(int taskId) {
        GetTaskResponseDTO t = (GetTaskResponseDTO) mGetTaskService.execute(new GetTaskRequestDTO(taskId));
        mTimerView.onTimerStart(t.getTaskEntity());
    }

    public void setTimerView(TimerView mTimerView) {
        this.mTimerView = mTimerView;
    }

    public void updateTask(TaskEntity taskEntity) {
        mUpdateTaskService.execute(new UpdateTaskRequestDTO(taskEntity));
    }
}
