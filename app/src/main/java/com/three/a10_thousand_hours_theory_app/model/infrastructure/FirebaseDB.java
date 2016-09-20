package com.three.a10_thousand_hours_theory_app.model.infrastructure;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by LCH on 2016. 9. 20..
 */

public class FirebaseDB {

    public static DatabaseReference sharedGoals(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference("shared_goals");
    }

    public static DatabaseReference users(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference("users");
    }
}
