package co.squaretwo.ironsource;

import android.support.annotation.Nullable;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;



public class RNIronSourceBannerModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNIronSourceBanner";
    private final ReactApplicationContext reactContext;

    public RNIronSourceBannerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void checkIsBannerLoaded(Callback callback) {
        final boolean result = BannerManager.bannerLoaded;
        callback.invoke(result);
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
