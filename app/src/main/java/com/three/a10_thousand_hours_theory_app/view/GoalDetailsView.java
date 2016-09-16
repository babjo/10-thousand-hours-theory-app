package com.three.a10_thousand_hours_theory_app.view;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;

/**
 * Created by LCH on 2016. 9. 13..
 */

public interface GoalDetailsView {
    void loadGoal(GoalEntity goalEntity, int updatedTaskId);
    void finish();
}
