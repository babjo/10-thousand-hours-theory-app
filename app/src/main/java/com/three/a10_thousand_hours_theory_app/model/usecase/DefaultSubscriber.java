package com.three.a10_thousand_hours_theory_app.model.usecase;

import rx.Subscriber;

/**
 * Created by LCH on 2016. 9. 22..
 */

public class DefaultSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
    }
    @Override
    public void onError(Throwable e) {
    }
    @Override
    public void onNext(T o) {
    }
}
