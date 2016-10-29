package com.mycompany.rx.java;

import rx.Observable;
import rx.functions.Action1;

/**
 * Hello world!
 */
public class HelloWorld {

    public static void main(String[] args) {

        String[] names = {"Colin", "Michael", "Peter", "Jamie", "Ryan"};

        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }
        });

    }
}
