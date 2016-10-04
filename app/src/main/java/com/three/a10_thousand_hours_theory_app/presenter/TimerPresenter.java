package com.three.a10_thousand_hours_theory_app.presenter;

import android.content.Context;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetTaskResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.usecase.DefaultSubscriber;
import com.three.a10_thousand_hours_theory_app.model.usecase.GetTaskUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.UpdateTaskUseCase;
import com.three.a10_thousand_hours_theory_app.model.usecase.UseCase;
import com.three.a10_thousand_hours_theory_app.view.TimerView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by LCH on 2016. 9. 17..
 */

@EBean
public class TimerPresenter implements Presenter{

    private Context mContext;

    @Bean(GetTaskUseCase.class)
    UseCase<GetTaskRequestDTO> mGetTaskUseCase;

    @Bean(UpdateTaskUseCase.class)
    UseCase<UpdateTaskRequestDTO> mUpdateTaskUseCase;

    private TimerView mTimerView;

    public TimerPresenter(Context context) {
        this.mContext = context;
    }

    public void startTimer(int taskId) {
        mGetTaskUseCase.execute(new GetTaskRequestDTO(taskId), new DefaultSubscriber<GetTaskResponseDTO>(){
            @Override
            public void onNext(GetTaskResponseDTO o) {
                mTimerView.onTimerStart(o.getTaskEntity());
            }
        });
    }

    public void setTimerView(TimerView mTimerView) {
        this.mTimerView = mTimerView;
    }

    public void updateTask(TaskEntity taskEntity) {
        mUpdateTaskUseCase.execute(new UpdateTaskRequestDTO(taskEntity), new DefaultSubscriber());
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mTimerView = null;
        mGetTaskUseCase.unsubscribe();
        mUpdateTaskUseCase.unsubscribe();
    }
}
