package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.GetGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by LCH on 2016. 10. 1..
 */

@EBean
public class GetGoalUseCase extends UseCase<GetGoalRequestDTO> {

    @Bean
    Requery mRequery;

    @Override
    protected Observable buildUseCaseObservable(GetGoalRequestDTO getGoalRequestDTO) {
        return Observable.create(subscriber -> {
            GoalEntity g = mRequery.getData().findByKey(GoalEntity.class, getGoalRequestDTO.getGoalId());
            subscriber.onNext(new GetGoalResponseDTO(g));
            subscriber.onCompleted();
        });
    }
}
