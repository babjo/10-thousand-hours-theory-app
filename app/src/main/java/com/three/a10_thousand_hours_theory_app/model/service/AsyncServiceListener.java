package com.three.a10_thousand_hours_theory_app.model.service;

public interface AsyncServiceListener<Before, After, Error>{
    void onBefore(Before beforeArgs);
    void onAfter(After afterArg);
    void onError(Error e);
}
