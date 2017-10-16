package com.mr.huang.common;

import android.content.Context;
import android.util.Log;

import com.mr.huang.framwork.commande.Calculate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by linhui on 2017/10/16.
 */
public class CalculateImp implements Calculate {

    private Object object;

    public CalculateImp(Context context) {
        object = DexLoad.loadDex1(context);
    }

    @Override
    public double sum(float one, float two) {
        try {
            Method method = object.getClass().getDeclaredMethod("sum",float.class,float.class);
            Object invoke = method.invoke(object,one,two);
            Log.i(TAG, "sum: "+(double)invoke);
            return (double) invoke;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public double subtraction(float one, float two) {
        return 0;
    }

    @Override
    public double multiplication(float one, float two) {
        return 0;
    }

    @Override
    public double division(float one, float two) {
        return 0;
    }
}
