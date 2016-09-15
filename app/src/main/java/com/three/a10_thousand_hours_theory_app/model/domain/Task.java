package com.three.a10_thousand_hours_theory_app.model.domain;

import java.util.Date;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.Persistable;

/**
 * Created by LCH on 2016. 9. 15..
 */

@Entity
public interface Task extends Persistable{
    @Key
    @Generated
    int getId();

    boolean getCompleted();
    Date getCompletedDate();
    Date getBeginDate();
    Date getEndDate();
    int getHours();
    int getLabelColor();
    String getTitle();

    @ManyToOne
    GoalEntity getGoal();
}
