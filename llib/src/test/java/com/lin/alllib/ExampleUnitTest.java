package com.lin.alllib;

import org.junit.Test;

import java.util.Stack;

import rx.Observable;
import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {



        String a = "1";
        String b = "2";
        String c = "3";
        String d = "4";
        String e = "5";

        final Stack<String> stack = new Stack<>();
        stack.add(a);
        stack.add(b);
        stack.add(c);
        stack.add(d);
        stack.add(e);
        System.out.println(stack.peek());
        System.out.println();


        Observable.from(new Integer[5]).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(stack.get(0));
            }
        });


    }
}