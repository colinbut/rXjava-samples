/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.basics.creation;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class TheBasics {


    /**
     * These are 'custom' observables
     *
     * A verbose example which contain lots of boilerplate code
     */
    public static void creatingObservablesUsingCreate() {
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello World");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Observer completed emitting");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Observer had an error emitting items");
            }

            @Override
            public void onNext(String s) {
                System.out.println("Subscriber received " + s + " from the Observer");
            }
        };

        myObservable.subscribe(mySubscriber);
    }

    /**
     * Simpler code to create observables than the above example
     */
    public static void creatingObservablesUsingJust() {
        Observable<String> observable = Observable.just("Hello World"); // just emit one item!

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String o) {
                System.out.println(o);
            }
        };

        observable.subscribe(onNextAction);

    }

    public static void creatingObservablesUsingJustWithMethodChaining() {
        Observable.just("The Basics")
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println(s);
                }
            });
    }


    public static void main(String[] args) {
        TheBasics.creatingObservablesUsingCreate();
        TheBasics.creatingObservablesUsingJust();
        TheBasics.creatingObservablesUsingJustWithMethodChaining();
    }

}
