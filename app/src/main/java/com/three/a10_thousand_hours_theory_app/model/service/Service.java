package com.three.a10_thousand_hours_theory_app.model.service;

public interface Service<Argument, Result> {
    Result execute(Argument arg);
}
