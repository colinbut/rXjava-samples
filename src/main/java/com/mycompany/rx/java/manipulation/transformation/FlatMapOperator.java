/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.manipulation.transformation;

import com.mycompany.rx.java.util.SubscriptionPrint;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

public class FlatMapOperator {

    public static void taking3FunctionsTransformingEventsIntoObservables() {
        Observable<Integer> flatMapped = Observable
            .just(-1, 0, 1)
            .map(new Func1<Integer, Integer>() {
                @Override
                public Integer call(Integer integer) {
                    return 2 / integer;
                }
            })
            .flatMap(new Func1<Integer, Observable<Integer>>() {
                         @Override
                         public Observable<Integer> call(Integer integer) {
                             return Observable.just(integer);
                         }
                     },
                new Func1<Throwable, Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call(Throwable throwable) {
                        return Observable.just(0);
                    }
                },
                new Func0<Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call() {
                        return Observable.just(42);
                    }
                });

        SubscriptionPrint.displaySubscription(flatMapped, "taking3FunctionsTransformingEventsIntoObservables");
    }

    public static void combineSourceWithTriggeredBySource() {
        Observable<Integer> flatMapped = Observable
            .just(5, 432)
            .flatMap(new Func1<Integer, Observable<Integer>>() {
                         @Override
                         public Observable<Integer> call(Integer integer) {
                             return Observable.range(integer, 2);
                         }
                     },
                    new Func2<Integer, Integer, Integer>() {
                         @Override
                         public Integer call(Integer x, Integer y) {
                             return x + y;
                         }
                     }
            );

        SubscriptionPrint.displaySubscription(flatMapped, "combineSourceWithTriggeredBySource");

    }


    public static void main(String[] args) {
        taking3FunctionsTransformingEventsIntoObservables();
        combineSourceWithTriggeredBySource();
    }

}
