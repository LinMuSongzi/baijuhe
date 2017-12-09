package com.lin.download.business;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by linhui on 2017/12/9.
 */
public class Plan {

    static Plan plan;

    static {
        plan = new Plan();
    }

    private Plan(){}

    public final Set<Runnable> downloadSet = new HashSet<>();




}
