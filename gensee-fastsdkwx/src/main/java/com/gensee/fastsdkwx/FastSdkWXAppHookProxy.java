package com.gensee.fastsdkwx;

import android.app.Application;

import com.gensee.fastsdk.GenseeLive;

import io.dcloud.weex.AppHookProxy;

public class FastSdkWXAppHookProxy implements AppHookProxy {
    @Override
    public void onCreate(Application application) {
        //可写初始化触发逻辑
        //在app切到后台,activity被后台回收的场景下,需要主动初始化下
        GenseeLive.initConfiguration(application);
    }
}
