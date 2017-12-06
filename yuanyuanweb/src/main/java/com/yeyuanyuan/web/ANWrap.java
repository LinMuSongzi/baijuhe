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
    public void asyncExecute(RequetEntity o) {
        mBase.asyncExecute(o);
    }

    @Override
    public  void execute(RequetEntity requetEntity) {
        mBase.execute(requetEntity);
    }
}
