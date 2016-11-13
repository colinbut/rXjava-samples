/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.creation;

import com.mycompany.rx.java.util.SubscriptionPrint;
import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import java.util.concurrent.TimeUnit;

public class Subjects {


    public static void main(String[] args) {

        Observable<Long> interval = Observable.interval(100L, TimeUnit.MILLISECONDS);

        Subject<Long, Long> publishSubject = PublishSubject.create();
        interval.subscribe(publishSubject);

        Subscription subscription1 = SubscriptionPrint.getSubscription(publishSubject, "First");
        Subscription subscription2 = SubscriptionPrint.getSubscription(publishSubject, "Second");
        Subscription subscription3 = null;

        try {
            Thread.sleep(300L);
            publishSubject.onNext(555L);
            subscription3 = SubscriptionPrint.getSubscription(publishSubject, "Third");
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        subscription1.unsubscribe();
        subscription2.unsubscribe();
        subscription3.unsubscribe();

    }
}
