package co.squaretwo.ironsource;

import android.app.Activity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;

class BannerManager {
  static private ReactApplicationContext reactContext = null;
  static public IronSourceBannerLayout banner = null;
  static public boolean bannerLoaded = false;

  static void init(ReactApplicationContext context) {
    reactContext = context;
    Activity activity =  context.getCurrentActivity();
    banner = IronSource.createBanner(activity, ISBannerSize.BANNER);
    banner.setBannerListener(new BannerListener() {
      @Override
      public void onBannerAdLoaded() {
        bannerLoaded = true;
      }

      @Override
      public void onBannerAdLoadFailed(IronSourceError error) {
      }

      @Override
      public void onBannerAdClicked() {
      }

      @Override
      public void onBannerAdScreenPresented() {
      }

      @Override
      public void onBannerAdScreenDismissed() {
      }

      @Override
      public void onBannerAdLeftApplication() {
      }
    });
    IronSource.loadBanner(banner);
  }
}