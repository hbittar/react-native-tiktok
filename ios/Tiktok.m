#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(Tiktok, NSObject)
  RCT_EXTERN_METHOD(auth: (NSString)path callback: (RCTResponseSenderBlock)callback)
@end
