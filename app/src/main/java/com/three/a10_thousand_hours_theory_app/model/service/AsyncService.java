package com.three.a10_thousand_hours_theory_app.model.service;


public interface AsyncService<Argument>{
    void execute(Argument arg, AsyncServiceListener asyncServiceListener);
}
