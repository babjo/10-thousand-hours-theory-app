package com.three.a10_thousand_hours_theory_app.view;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;

import java.util.List;

/**
 * Created by LCH on 2016. 9. 13..
 */
public interface MainView {
    void loadGoals(List<GoalEntity> goals);
}
