/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.basics.creation;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

/**
 * This class demonstrates some simple examples of a 'Hot Observable'
 *
 * A hot observable is an observable that starts emitting once it is active i.e. once it has becomes a
 * hot observable.
 *
 * A subscriber subscribing to it midway will only observe the emitted items from the point they subscribed.
 * Anything prior to that will not be recieved however.
 *
 * Having said that, it is also possible for subscribers to observe all emitted items from a hot observable even the
 * previously emitted items when the subscriber was not subscribed. (using replay instead of publish)
 *
 */
public class HotColdObservables {

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


    public static void hotObservableUsingConnect() {
        Observable<Long> interval = Observable.interval(100L, TimeUnit.MILLISECONDS);
        ConnectableObservable<Long> published = interval.publish(); // can use replay

        Subscription subscription1 = subscribePrint(published, "First");
        Subscription subscription2 = subscribePrint(published, "Second");

        published.connect(); // becoming a 'Hot Observable'

        Subscription subscription3 = null;

        try {
            Thread.sleep(500L);
            subscription3 = subscribePrint(published, "Third");
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!subscription1.isUnsubscribed()) {
            subscription1.unsubscribe();
        }

        if (!subscription2.isUnsubscribed()) {
            subscription2.unsubscribe();
        }

        if (subscription3 != null && !subscription3.isUnsubscribed()) {
            subscription3.unsubscribe();
        }
    }

    /**
     * This uses refCount() method instead of the connect() method
     */
    public static void hotObservableOnFirstSubscription() {
        // note that share() method is an alias for publish().refCount()
        Observable<Long> refCount = Observable.interval(100L, TimeUnit.MILLISECONDS).publish().refCount();
        Subscription subscription1 = subscribePrint(refCount, "First");
        Subscription subscription2 = subscribePrint(refCount, "Second");

        pause(300L);

        subscription1.unsubscribe();
        subscription2.unsubscribe(); // the hot observable will be deactivated here

        Subscription subscription3 = subscribePrint(refCount, "Third"); // reactivated but emit from beginning again

        pause(300L);

        subscription3.unsubscribe();
    }

    private static void pause(long timeToPause) {
        try {
            Thread.sleep(timeToPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        hotObservableUsingConnect();
        hotObservableOnFirstSubscription();
    }

}
