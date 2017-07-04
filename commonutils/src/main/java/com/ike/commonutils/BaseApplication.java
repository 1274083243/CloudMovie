package com.ike.commonutils;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;


/**
* author ike
* create time 16:01 2017/7/1
* function:上下文的基类
**/
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG){
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}
