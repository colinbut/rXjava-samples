/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.util;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public final class SubscriptionPrint {

    private SubscriptionPrint() {}

    public static <T> void displaySubscription(Observable<T> observable, final String name) {
        observable.subscribe(new Action1<T>() {
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

    public static <T> Subscription getSubscription(Observable<T> observable, final String name) {
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

}
