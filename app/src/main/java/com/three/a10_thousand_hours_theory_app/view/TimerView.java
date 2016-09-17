package com.three.a10_thousand_hours_theory_app.view;

import com.three.a10_thousand_hours_theory_app.model.domain.TaskEntity;

/**
 * Created by LCH on 2016. 9. 17..
 */
public interface TimerView {
    void setTitle(CharSequence title);
    void onTimerStart(TaskEntity taskEntity);
}
