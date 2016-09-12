package com.three.a10_thousand_hours_theory_app.model.domain;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.Persistable;

/**
 * Created by LCH on 2016. 9. 11..
 */

@Entity
public interface Task extends Persistable {

    @Key
    @Generated
    int getId();

    @ManyToOne
    Goal getGoal();

    String getTitle();
}
