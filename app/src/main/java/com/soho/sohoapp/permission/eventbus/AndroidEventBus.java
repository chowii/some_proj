package com.soho.sohoapp.permission.eventbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class AndroidEventBus {
    private final PublishSubject<Object> subject = PublishSubject.create();

    public <E> void post(E event) {
        subject.onNext(event);
    }

    public <E> Observable<E> observe(Class<E> eventClass) {
        return subject.ofType(eventClass);
    }
}
