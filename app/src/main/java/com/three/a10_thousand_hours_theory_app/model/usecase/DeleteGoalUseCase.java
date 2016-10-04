package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.DeleteGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.DeleteGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by LCH on 2016. 10. 1..
 */

@EBean
public class DeleteGoalUseCase extends UseCase<DeleteGoalRequestDTO> {

    @Bean
    Requery mRequery;

    @Override
    protected Observable buildUseCaseObservable(DeleteGoalRequestDTO deleteGoalRequestDTO) {
        return Observable.create(subscriber -> {
            GoalEntity g = mRequery.getData().findByKey(GoalEntity.class, deleteGoalRequestDTO.getGoalId());
            mRequery.getData().delete(g);
            subscriber.onNext(new DeleteGoalResponseDTO());
            subscriber.onCompleted();
        });
    }
}
