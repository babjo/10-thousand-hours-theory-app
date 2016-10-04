package com.three.a10_thousand_hours_theory_app.model.usecase;

import com.three.a10_thousand_hours_theory_app.Const;
import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalRequestDTO;
import com.three.a10_thousand_hours_theory_app.model.dto.UpdateGoalResponseDTO;
import com.three.a10_thousand_hours_theory_app.model.infrastructure.Requery;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by LCH on 2016. 10. 1..
 */

@EBean
public class UpdateGoalUseCase extends UseCase<UpdateGoalRequestDTO> {

    @Bean
    Requery mRequery;

    @Override
    protected Observable buildUseCaseObservable(UpdateGoalRequestDTO updateGoalRequestDTO) {
        return Observable.create(subscriber -> {
            GoalEntity goalEntity = updateGoalRequestDTO.getGoalEntity();

            if(goalEntity.getType() == Const.GOAL_TYPE_HOURS)
                goalEntity.setDeadLineDate(null);
            else
                goalEntity.setGoalHours(0);

            mRequery.getData().update(goalEntity);
            subscriber.onNext(new UpdateGoalResponseDTO());
            subscriber.onCompleted();
        });
    }
}
