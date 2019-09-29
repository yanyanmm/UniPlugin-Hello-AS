package com.gensee.fastsdkwx;

import android.app.Activity;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.gensee.common.ServiceType;
import com.gensee.entity.InitParam;
import com.gensee.fastsdk.GenseeLive;
import com.gensee.fastsdk.core.GSFastConfig;
import com.gensee.fastsdk.entity.OfflinePlayParam;
import com.google.gson.Gson;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

public class FastSdkWXModule extends WXSDKEngine.DestroyableModule {

    private static final String SERVICE_TYPE = "serviceType";

    @JSMethod(uiThread = true)
    public void startLive(JSONObject params, JSONObject configs, JSCallback jsCallback) {
        if (mWXSDKInstance.getContext() instanceof Activity) {
            InitParam initParam = createInitParam(params, jsCallback);
            if (initParam != null) {
                GSFastConfig gsFastConfig = createGSFastConfig(configs);
                if (gsFastConfig != null) {
                    GenseeLive.startLive(mWXSDKInstance.getContext(), gsFastConfig, initParam);
                } else {
                    GenseeLive.startVod(mWXSDKInstance.getContext(), initParam);
                }
                successCallback(jsCallback);
            } else {
                failedCallback("param is invalid", jsCallback);
            }
        } else {
            failedCallback("context is not instance of Activity", jsCallback);
        }
    }

    @Override
    public void destroy() {

    }

    //成功回调
    private void successCallback(JSCallback jsCallback) {
        if (jsCallback != null) {
            JSONObject result = new JSONObject();
            result.put("code", 0);
            result.put("msg", "success");
            jsCallback.invoke(result);
        }
    }

    //失败回调
    private void failedCallback(String msg, JSCallback jsCallback) {
        if (jsCallback != null) {
            JSONObject result = new JSONObject();
            result.put("code", -1);
            result.put("msg", msg);
            jsCallback.invoke(result);
        }
    }

    //创建初始化参数
    private InitParam createInitParam(JSONObject param, JSCallback jsCallback) {
        if (param == null) {
            return null;
        }
        InitParam initParam = new Gson().fromJson(param.toJSONString(), InitParam.class);
        if (initParam.getDomain() == null) {
            failedCallback("domain is not null", jsCallback);
            return null;
        }
        if (initParam.getNumber() == null) {
            failedCallback("number is not null", jsCallback);
            return null;
        }
        if (initParam.getNickName() == null) {
            failedCallback("nickName is not null", jsCallback);
            return null;
        }
        if (initParam.getJoinPwd() == null) {
            failedCallback("joinPwd is not null", jsCallback);
            return null;
        }
        String serviceType = param.getString(SERVICE_TYPE);
        if ("webcast".equalsIgnoreCase(serviceType)) {
            initParam.setServiceType(ServiceType.WEBCAST);
        } else if ("training".equalsIgnoreCase(serviceType)) {
            initParam.setServiceType(ServiceType.TRAINING);
        } else {
            failedCallback("serviceType is not null or invalid", jsCallback);
            return null;
        }

        return initParam;
    }

    //创建配置对象
    private GSFastConfig createGSFastConfig(JSONObject config) {
        if (config != null) {
            return new Gson().fromJson(config.toJSONString(), GSFastConfig.class);
        }
        return null;
    }
}
