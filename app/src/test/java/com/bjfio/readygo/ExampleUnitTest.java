package com.bjfio.readygo;

import org.junit.Test;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void helloWorld() throws Exception{
        Observable.just("Hello world")
                .map(new Func1<String,Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer word) {
                        System.out.println("got " + word + " @ "
                                + Thread.currentThread().getName());
                    }
                });
        assertEquals(4, 2 + 2);
    }
}