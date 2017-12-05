package com.yeyuanyuan.web;

/**
 * Created by linhui on 2017/12/5.
 */
class ANWrap implements IAccessNetwork{

    static IAccessNetwork anWrap;
    static{
        anWrap = new ANWrap(Access.create());
    }

    private IAccessNetwork mBase;
    private ANWrap(){}
    private ANWrap(IAccessNetwork mBase) {
        this.mBase = mBase;
    }


    static IAccessNetwork getInstance(){
        return anWrap;
    }

    @Override
    public <T> void asyncExecute(RequetEntity<T> o) {
        mBase.asyncExecute(o);
    }

    @Override
    public <T> RequetEntity<T> execute(RequetEntity<T> requetEntity) {
        return mBase.execute(requetEntity);
    }
}
