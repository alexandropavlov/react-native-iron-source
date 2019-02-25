package co.squaretwo.ironsource;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.support.annotation.Nullable;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.IronSourceBannerLayout;

//class BannerActivity extends Activity {}

public class RNIronSourceBannerModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNIronSourceBanner";
    //private final IronSourceBannerLayout banner;
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
    public void loadBanner() {
        final Activity a = reactContext.getCurrentActivity();
        final IronSourceBannerLayout banner = IronSource.createBanner(reactContext.getCurrentActivity(), ISBannerSize.BANNER);
        banner.setBannerListener(new BannerListener() {
            @Override
            public void onBannerAdLoaded() {
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {                
                        new AlertDialog.Builder(a)
                            .setTitle("onBannerAdLoaded")
                            .setMessage("onBannerAdLoaded")
                            .show();
                    }
                });
                sendEvent("test", null);
                sendEvent("bannerDidLoad", null);
            }

            @Override
            public void onBannerAdLoadFailed(IronSourceError error) {
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {                
                        new AlertDialog.Builder(a)
                            .setTitle("onBannerAdLoadFailed")
                            .setMessage("onBannerAdLoadFailed")
                            .show();
                    }
                });
                sendEvent("test", null);
                final WritableMap params = Arguments.createMap();
                params.putString("error_code", error.getErrorMessage());
                sendEvent("bannerDidFailToLoadWithError", params);
            }

            @Override
            public void onBannerAdClicked() {
                sendEvent("didClickBanner", null);
            }

            @Override
            public void onBannerAdScreenPresented() {
                sendEvent("test", null);
                sendEvent("bannerDidScreenPresented", null);
            }

            @Override
            public void onBannerAdScreenDismissed() {
                sendEvent("test", null);
                sendEvent("bannerDidScreenDismissed", null);
            }

            @Override
            public void onBannerAdLeftApplication() {
                sendEvent("bannerDidLeftApplication", null);
            }
        });
        IronSource.loadBanner(banner);
        sendEvent("test", null);
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {                
                new AlertDialog.Builder(a)
                    .setTitle("lol")
                    .setMessage(banner.getPlacementName())
                    .show();
            }
        });
    }

    @ReactMethod
    public void destroyBanner() {
        //IronSource.destroyBanner(this.banner);
    }


    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
