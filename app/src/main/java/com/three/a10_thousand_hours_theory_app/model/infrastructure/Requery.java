package com.three.a10_thousand_hours_theory_app.model.infrastructure;

import android.content.Context;

import com.three.a10_thousand_hours_theory_app.BuildConfig;
import com.three.a10_thousand_hours_theory_app.model.domain.Models;

import org.androidannotations.annotations.EBean;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

/**
 * Created by LCH on 2016. 9. 12..
 */

@EBean(scope = EBean.Scope.Singleton)
public class Requery {

    private Context mContext;
    private EntityDataStore data;

    public Requery(Context mContext) {
        this.mContext = mContext;
        this.data = createData(mContext);
    }

    private EntityDataStore createData(Context mContext) {
        DatabaseSource dataSource = new DatabaseSource(mContext, Models.DEFAULT, 10);
        dataSource.setLoggingEnabled(true);
        if (BuildConfig.DEBUG) {
            dataSource.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }
        Configuration configuration = dataSource.getConfiguration();
        return new EntityDataStore<Persistable>(configuration);
    }

    public EntityDataStore<Persistable> getData(){
        if(data == null) data = createData(mContext);
        return data;
    }

}
