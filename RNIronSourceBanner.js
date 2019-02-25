import { NativeModules, NativeEventEmitter } from 'react-native';

const RNIronSourceBanner = NativeModules.RNIronSourceBanner;
const IronSourceBannerEventEmitter = new NativeEventEmitter(RNIronSourceBanner);

const eventNames = [
  'test',
  'bannerDidLoad',
  'bannerDidFailToLoadWithError',
  'didClickBanner',
  'bannerDidScreenPresented',
  'bannerDidScreenDismissed',
  'bannerDidLeftApplication',
];

const eventHandlers = eventNames.reduce((result, eventName) => {
  result[eventName] = new Map();
  return result;
}, {});

const addEventListener = (type, handler) => {
  const handlers = eventHandlers[type];
  if (!handlers) {
    console.warn(`Event with type ${type} does not exist.`);
    return;
  }

  if (handlers.has(handler)) {
    console.warn(`Event with type ${type} and handler has already been added.`);
    return;
  }

  handlers.set(handler, IronSourceBannerEventEmitter.addListener(type, handler));
};

const removeEventListener = (type, handler) => {
  if (!eventHandlers[type] || !eventHandlers[type].has(handler)) {
    return;
  }
  eventHandlers[type].get(handler).remove();
  eventHandlers[type].delete(handler);
};

const removeAllListeners = () => {
  const count = eventNames.length;
  for (let i = 0; i < count; i++) {
    IronSourceBannerEventEmitter.removeAllListeners(eventNames[i]);
  }
};

module.exports = {
  ...RNIronSourceBanner,
  loadBanner: () => RNIronSourceBanner.loadBanner(),
  destroyBanner: () => RNIronSourceBanner.destroyBanner(),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
