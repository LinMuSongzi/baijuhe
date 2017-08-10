package com.lin.alllib.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhui on 2017/8/10.
 */
public class LisrTest {

    public static void main(String args[]){

        List<String> list = new ArrayList<>();

        list.add("了");
        list.add("啊");
        list.add("人");

        if(list.contains(new String("了"))){
            System.err.println("匹配");
        }else{
            System.err.println("错误");
        }

    }

}
