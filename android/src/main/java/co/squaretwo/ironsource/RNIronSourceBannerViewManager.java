package co.squaretwo.ironsource;

import android.widget.FrameLayout;
import android.view.View;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.SimpleViewManager;

public class RNIronSourceBannerViewManager extends SimpleViewManager<View> {
  public static final String REACT_CLASS = "RCTBannerView";
  private FrameLayout bannerContainer = null;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public View createViewInstance(ThemedReactContext context) {
      if (bannerContainer != null) bannerContainer.removeAllViews();
      bannerContainer = new FrameLayout(context);

      if (BannerManager.bannerLoaded == true) {
          final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                  FrameLayout.LayoutParams.WRAP_CONTENT);
          bannerContainer.addView(BannerManager.banner, 0, layoutParams);

      }
      return bannerContainer;
  }
}