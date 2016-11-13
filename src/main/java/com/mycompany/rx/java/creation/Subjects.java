/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.creation;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import java.util.concurrent.TimeUnit;

public class Subjects {

    public static <T> Subscription subscribePrint(Observable<T> observable, final String name) {
        return observable.subscribe(new Action1<T>() {
                                        @Override
                                        public void call(T t) {
                                            System.out.println(name + ":" + t);
                                        }
                                    },
            new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    System.err.println("Error from " + name + ":");
                    System.err.println(throwable.getMessage());
                }
            },
            new Action0() {
                @Override
                public void call() {
                    System.out.println(name + " ended!");
                }
            });

    }

    public static void main(String[] args) {

        Observable<Long> interval = Observable.interval(100L, TimeUnit.MILLISECONDS);

        Subject<Long, Long> publishSubject = PublishSubject.create();
        interval.subscribe(publishSubject);

        Subscription subscription1 = subscribePrint(publishSubject, "First");
        Subscription subscription2 = subscribePrint(publishSubject, "Second");
        Subscription subscription3 = null;

        try {
            Thread.sleep(300L);
            publishSubject.onNext(555L);
            subscription3 = subscribePrint(publishSubject, "Third");
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        subscription1.unsubscribe();
        subscription2.unsubscribe();
        subscription3.unsubscribe();

    }
}
