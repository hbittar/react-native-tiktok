package com.reactnativetiktok;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;
import com.bytedance.sdk.open.aweme.TikTokOpenApiFactory;
import com.bytedance.sdk.open.aweme.api.TikTokApiEventHandler;
import com.bytedance.sdk.open.aweme.api.TiktokOpenApi;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.aweme.share.Share;
import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Properties;

public class TikTokEntryActivity extends ReactActivity implements TikTokApiEventHandler {
    TiktokOpenApi ttOpenApi;

    public void onReq(BaseReq baseReq) {}

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TiktokOpenApi create = TikTokOpenApiFactory.create(this);
        this.ttOpenApi = create;
        create.handleIntent(getIntent(), this);
    }

    public void onResp(BaseResp baseResp) {
        if (baseResp instanceof Authorization.Response) {
            Authorization.Response response = (Authorization.Response) baseResp;
            WritableMap params = Arguments.createMap();
            params.putInt("code", response.errorCode);
            params.putString("status", response.errorMsg);
            ReactContext ctx = getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
            ctx.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("onAuthCompleted", params);
            finish();
        } else if (baseResp instanceof Share.Response) {
            Share.Response response = (Share.Response) baseResp;
            ReactContext ctx = getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
            ctx.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("onShareCompleted", response.subErrorCode);
            finish();
        }
    }

    public void onErrorIntent(Intent intent) {
        finish();
    }
}
