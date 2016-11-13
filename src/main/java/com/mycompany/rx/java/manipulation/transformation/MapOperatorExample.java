/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.rx.java.manipulation.transformation;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class MapOperatorExample {

    public static void map() {
        Observable.just("Hello")
            .map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    return s + " Colin";
                }
            })
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println(s);
                }
            });

    }

    public static void mapToDifferentTypeThanOriginalSource() {

        Observable.just("Hello World")
            .map(new Func1<String, Integer>() {
                @Override
                public Integer call(String s) {
                    return s.hashCode();
                }
            })
        .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });

    }
    
    public static void mapTransformedTypeBackToOriginalType() {
        Observable.just("Hello World")
            .map(new Func1<String, Integer>() {
                @Override
                public Integer call(String s) {
                    return s.hashCode();
                }
            })
            .map(new Func1<Integer, String>() {
                @Override
                public String call(Integer integer) {
                    return integer.toString();
                }
            })
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println(s);
                }
            });
    }



    public static void main(String[] args) {
        map();
        mapToDifferentTypeThanOriginalSource();
        mapTransformedTypeBackToOriginalType();
    }

}
