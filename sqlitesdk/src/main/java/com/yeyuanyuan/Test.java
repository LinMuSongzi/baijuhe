package com.yeyuanyuan;

import com.yeyuanyuan.commander.Reserve;
import com.yeyuanyuan.imp.Factory;

/**
 * Created by linhui on 2017/12/4.
 */
public class Test {

    public static void main(String[] agrs){

        Factory.createPlan(Test.class).execute(new Reserve() {
            @Override
            public void result(Object... objects) {
                Test tests = (Test) objects[0];
                System.err.println(tests);
            }

            @Override
            public int type() {
                return Reserve.QUERY;
            }
        });


    }

}
