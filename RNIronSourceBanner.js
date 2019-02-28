import { NativeModules, NativeEventEmitter, requireNativeComponent, Platform, View } from 'react-native';

const RNIronSourceBanner = Platform.OS === 'ios' ? null : NativeModules.RNIronSourceBanner;

module.exports = {
  checkIsBannerLoaded: Platform.OS === 'ios' ? () => {} : RNIronSourceBanner.checkIsBannerLoaded,
  Banner: Platform.OS === 'ios' ? View : requireNativeComponent('RCTBannerView'),
};
