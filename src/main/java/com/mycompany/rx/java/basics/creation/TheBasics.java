/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.basics.creation;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.List;

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

    public static void creatingObservablesUsingJustWithActions() {
        Observable<String> observable = Observable.just("Hellooooo");

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("Error");
            }
        };

        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                System.out.println("Completed!");
            }
        };

        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
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

    public static void creatingObservablesUsingFrom() {
        List<String> names = new ArrayList<String>(){
            {
                add("Lewis");
                add("Nico");
                add("Sebastian");
                add("Max");
                add("Daniel");
                add("Kimi");
            }
        };

        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("Observed: " + s);
            }
        });
    }


    public static void main(String[] args) {
        TheBasics.creatingObservablesUsingCreate();
        TheBasics.creatingObservablesUsingJust();
        TheBasics.creatingObservablesUsingJustWithActions();
        TheBasics.creatingObservablesUsingJustWithMethodChaining();
        TheBasics.creatingObservablesUsingFrom();
    }

}
