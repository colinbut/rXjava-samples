/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.basics.creation;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

import java.util.concurrent.TimeUnit;

public class ObservableFactoryMethods {



    public static <T> void subscribePrint(Observable<T> observable, final String name) {
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

    public static void main(String[] args) throws InterruptedException {
        subscribePrint(Observable.interval(500L, TimeUnit.MILLISECONDS),"Interval Observable");
        subscribePrint(Observable.timer(0L, 1L, TimeUnit.SECONDS),"Timer Interval Observable"); //deprecated? what?
        subscribePrint(Observable.timer(1L, TimeUnit.SECONDS),"Timer Observable");
        subscribePrint(Observable.error(new Exception("Test Error!")),"Error Observable");
        subscribePrint(Observable.empty(), "Empty Observable");
        subscribePrint(Observable.never(), "Never Observable");
        subscribePrint(Observable.range(1, 3), "Range Observable");
        Thread.sleep(2000L);
    }

}
