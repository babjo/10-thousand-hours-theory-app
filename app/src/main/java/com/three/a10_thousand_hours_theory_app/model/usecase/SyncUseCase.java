package com.three.a10_thousand_hours_theory_app.model.usecase;

public interface SyncUseCase<T, R>{
    R execute(T t);
}
