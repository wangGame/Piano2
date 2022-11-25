package com.tony.flipracing;


//
//public class AndroidAdsAndShopListener implements AdsAndShopListener {
//    private AndroidLauncher activity;
//    private Runnable videoClosedRunnable;
//
//    public AndroidAdsAndShopListener(AndroidLauncher activity){
//        this.activity = activity;
//    }
//
//    @Override
//    public void rate() {
//        Intent intent;
//        try {
//            intent = new Intent(Intent.ACTION_VIEW);
//            String referrer = "market://details?id=" + activity.getPackageName();
//            intent.setData(Uri.parse(referrer));
//            activity.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } catch (OutOfMemoryError e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    @Override
//    public void showBanner(boolean visible) {
//        if (GameConfig.currentLevel < Constant.SHOWBANNERLEVEL){
//            return;
//        }
//        if (visible) {
//            if (GameConfig.currentLevel >= Constant.SHOWBANNERLEVEL) {
//                DoodleAds.showBanner(visible);
//                BrickLog.DEBUG("banner ad show");
//            } else {
//                DoodleAds.showBanner(false);
//                BrickLog.DEBUG("banner ad close");
//            }
//        }else {
//            DoodleAds.showBanner(false);
//            BrickLog.DEBUG("banner ad close");
//        }
//    }
//
//    @Override
//    public boolean showInterstitialAds() {
//        //12 level
//        if (PreferencesUtils.getInstance().getCurrentPlayLevel()<=Constant.SHOWBANNERLEVEL)return false;
//        return showChaping();
//
//    }
//
//    @Override
//    public boolean showInterstitialAdsForcus() {
//       return showChaping();
//    }
//
//    private boolean showChaping() {
//        if(DoodleAds.hasInterstitialAdsReady()) {
//            DoodleAds.showInterstitial();
//            BrickLog.DEBUG("interstitialAds show");
//            return true;
//        } else{
//            BrickLog.DEBUG("interstitialAds close");
//        }
//        return false;
//    }
//
//    @Override
//    public boolean isNetConnect() {
////        boolean isNet = false;
////        isNet = activity.checkNet();
//        return DoodleAds.isVideoAdsReady();
//    }
//
//    @Override
//    public void showVideo(Runnable runnable) {
//        this.videoClosedRunnable = runnable;
//        DoodleAds.showVideoAds();
//    }
//
//
//    @Override
//    public void onVideoClosed() {
//        if(videoClosedRunnable !=null){
//            Gdx.app.postRunnable(videoClosedRunnable);
//        }
//    }
//
//    @Override
//    public boolean videoReady() {
//        return DoodleAds.isVideoAdsReady();
//    }
//}
