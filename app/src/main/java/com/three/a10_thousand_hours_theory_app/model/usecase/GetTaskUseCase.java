package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetTaskResponseDTO;
import com.three.a10_thousand_hours_theory_app.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by LCH on 2016. 10. 1..
 */

@EBean
public class GetTaskUseCase extends UseCase<GetTaskRequestDTO>{

    @Bean
    Requery mRequery;

    @Override
    protected Observable buildUseCaseObservable(GetTaskRequestDTO getTaskRequestDTO) {
        return Observable.create(subscriber -> {
            TaskEntity taskEntity = mRequery.getData().findByKey(TaskEntity.class, getTaskRequestDTO.getTaskId());
            subscriber.onNext(new GetTaskResponseDTO(taskEntity));
            subscriber.onCompleted();
        });
    }
}
