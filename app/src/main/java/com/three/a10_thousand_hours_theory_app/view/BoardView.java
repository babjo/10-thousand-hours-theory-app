package com.three.a10_thousand_hours_theory_app.view;

import com.three.a10_thousand_hours_theory_app.model.domain.GoalEntity;
import com.three.a10_thousand_hours_theory_app.model.domain.SharedGoal;

/**
 * Created by LCH on 2016. 9. 19..
 */

public interface BoardView {
    void onUploadGoal(SharedGoal sharedGoal);
    void onSharedGoalDetails(SharedGoal sharedGoal);
    void onDownloadGoal(GoalEntity goalEntity);
    void onFailToDownloadGoal();
}
