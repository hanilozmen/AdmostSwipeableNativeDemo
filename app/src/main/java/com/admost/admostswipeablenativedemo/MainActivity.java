package com.admost.admostswipeablenativedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import admost.adserver.ads.AdMostNativeAdView;
import admost.sdk.AdMostView;
import admost.sdk.AdMostViewBinder;
import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.listener.AdMostInitListener;
import admost.sdk.listener.AdMostViewListener;

public class MainActivity extends Activity {

    AdMostView nativeBanner;


    public static final String TAG = "ADMOST_SAMPLE_APP";

    public static final String ADMOST_APP_ID = "6cc8e89a-b52a-4e9a-bb8c-579f7ec538fe";
    final static String NATIVE_SWIPEABLE_ZONE_ID = "9e5d4b82-aa93-43a1-9fd5-3c71691fbcc7";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdMostConfiguration.Builder configuration = new AdMostConfiguration.Builder(this, ADMOST_APP_ID);
        AdMost.getInstance().init(configuration.build(), new AdMostInitListener() {
            @Override
            public void onInitCompleted() {
                Log.i(TAG, "AdMost onInitCompleted");
            }

            @Override
            public void onInitFailed(int i) {
                Log.i(TAG, "AdMost onInitFailed: status code " + i);
            }
        });
        getNative();

    }

    private void getNative() {
        // This is just for your own style, left null if you want default layout style
        final AdMostViewBinder customBinder = new AdMostViewBinder.Builder(R.layout.admost_native_fullscreen_swipeable)
                .iconImageId(R.id.ad_app_icon)
                .titleId(R.id.ad_headline)
                .callToActionId(R.id.ad_call_to_action)
                .textId(R.id.ad_body)
                .attributionId(R.id.ad_attribution)
                .mainImageId(R.id.ad_image)
                .backImageId(R.id.ad_back)
                .privacyIconId(R.id.ad_privacy_icon)
                .preventClicks(true) // new feature. only privacy icon is clickable.
                .isRoundedMode(true)
                .build();

        ((LinearLayout) findViewById(R.id.adLayout)).removeAllViews();
        if (nativeBanner != null) {
            nativeBanner.destroy();
        }
        nativeBanner = new AdMostView(MainActivity.this, NATIVE_SWIPEABLE_ZONE_ID, new AdMostViewListener() {

            @Override
            public void onReady(String network, int ecpm, View adView) {
                Log.d(TAG, "MainActivity native onReady network: " + network + " ecpm: " + ecpm);
                LinearLayout viewAd = (LinearLayout) findViewById(R.id.adLayout);
                viewAd.removeAllViews();
                if (adView.getParent() != null && adView.getParent() instanceof ViewGroup) {
                    ((ViewGroup) adView.getParent()).removeAllViews();
                }
                viewAd.addView(adView);

                View admostNativeAd = adView.findViewWithTag(AdMostNativeAdView.TAG);

                 // TODO The publisher need to detect swipe and call the method below

                /*if(admostNativeAd instanceof AdMostNativeAdView) {
                    ((AdMostNativeAdView)admostNativeAd).getNativeAd().goToContentUrl();
                }*/

            }

            @Override
            public void onFail(int errorCode) {
                Log.d(TAG,"native error Code :" + errorCode);

            }

            @Override
            public void onClick(String network) {
                Log.d(TAG, "onClick : native" );

            }
        }, customBinder);
        nativeBanner.load();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(nativeBanner != null)
            nativeBanner.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(nativeBanner != null)
            nativeBanner.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(nativeBanner !=null) {
            nativeBanner.destroy();
        }
    }
}
