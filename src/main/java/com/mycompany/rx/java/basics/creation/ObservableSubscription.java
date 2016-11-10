/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.basics.creation;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class ObservableSubscription {

    /**
     * An implementation of the Observable.from method
     */
    public static <T> Observable<T> fromIterableWithSubscriptionChecking(final Iterable<T> iterable) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                try {
                    Iterator<T> iterator = iterable.iterator();
                    while (iterator.hasNext()) {
                        if (subscriber.isUnsubscribed()) {
                            return; //exit
                        }
                        // otherwise...
                        subscriber.onNext(iterator.next());
                    }

                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onCompleted();
                    }

                } catch (Exception e) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(e);
                    }
                }

            }
        });
    }

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

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src", "main", "resources", "lorem_big.txt");
        List<String> data = Files.readAllLines(path);
        Observable<String> observable = fromIterableWithSubscriptionChecking(data).subscribeOn(Schedulers.computation());

        Subscription subscription = subscribePrint(observable, "File");
        System.out.println("Before unsubcribe");
        subscription.unsubscribe();
        System.out.println("After unsubcribe");
    }
}
