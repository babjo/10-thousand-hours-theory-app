package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.three.a10_thousand_hours_theory_app.model.dto.UpdateTaskRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateTaskResponseDTO;
import com.three.a10_thousand_hours_theory_app.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by LCH on 2016. 10. 1..
 */

@EBean
public class UpdateTaskUseCase extends UseCase<UpdateTaskRequestDTO> {

    @Bean
    Requery mRequery;

    @Override
    protected Observable buildUseCaseObservable(UpdateTaskRequestDTO updateTaskRequestDTO) {
        return Observable.create(subscriber -> {
            mRequery.getData().update(updateTaskRequestDTO.getTaskEntity());
            subscriber.onNext(new UpdateTaskResponseDTO());
            subscriber.onCompleted();
        });
    }
}
