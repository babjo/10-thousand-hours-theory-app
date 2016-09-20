package com.three.a10_thousand_hours_theory_app.model.domain;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Persistable;

/**
 * Created by LCH on 2016. 9. 20..
 */
@Entity
public interface User extends Persistable {

    @Key
    @Generated
    int getId();
    String getKey();
}
