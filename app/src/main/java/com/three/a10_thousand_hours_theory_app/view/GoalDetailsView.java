package com.three.a10_thousand_hours_theory_app.view;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;

/**
 * Created by LCH on 2016. 9. 13..
 */

public interface GoalDetailsView {
    void onLoadGoal(GoalEntity goalEntity, int updatedTaskId);
    void finish();
    void showTimerDialog(TaskEntity taskEntity);
}
