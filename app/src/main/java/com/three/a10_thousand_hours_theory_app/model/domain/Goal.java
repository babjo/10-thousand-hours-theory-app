package com.three.a10_thousand_hours_theory_app.model.domain;


import java.util.Date;
import java.util.List;

import io.requery.CascadeAction;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.OneToMany;
import io.requery.Persistable;

/**
 * Created by LCH on 2016. 9. 11..
 */

@Entity
public interface Goal extends Persistable {

    @Key
    @Generated
    int getId();

    String getTitle();
    String getDescription();

    int getType();
    Date getDeadLineDate();
    int getGoalHours();

    @OneToMany(mappedBy = "goal", cascade = {CascadeAction.DELETE, CascadeAction.SAVE})
    List<TaskRuleEntity> getTaskRules();

    @OneToMany(mappedBy = "goal", cascade = {CascadeAction.DELETE, CascadeAction.SAVE})
    List<TaskEntity> getTasks();
}
