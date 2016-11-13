/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.creation;

import com.mycompany.rx.java.util.SubscriptionPrint;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class ObservableFactoryMethods {


    public static void main(String[] args) throws InterruptedException {
        SubscriptionPrint.displaySubscription(Observable.interval(500L, TimeUnit.MILLISECONDS),"Interval Observable");
        SubscriptionPrint.displaySubscription(Observable.timer(0L, 1L, TimeUnit.SECONDS),"Timer Interval Observable"); //deprecated? what?
        SubscriptionPrint.displaySubscription(Observable.timer(1L, TimeUnit.SECONDS),"Timer Observable");
        SubscriptionPrint.displaySubscription(Observable.error(new Exception("Test Error!")),"Error Observable");
        SubscriptionPrint.displaySubscription(Observable.empty(), "Empty Observable");
        SubscriptionPrint.displaySubscription(Observable.never(), "Never Observable");
        SubscriptionPrint.displaySubscription(Observable.range(1, 3), "Range Observable");
        Thread.sleep(2000L);
    }

}
