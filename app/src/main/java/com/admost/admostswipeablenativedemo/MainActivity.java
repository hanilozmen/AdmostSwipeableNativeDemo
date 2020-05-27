package com.admost.admostswipeablenativedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import admost.sdk.AdMostCustomView;
import admost.sdk.AdMostViewBinder;
import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.listener.AdMostCustomViewListener;
import admost.sdk.listener.AdMostInitListener;

public class MainActivity extends Activity {

    AdMostCustomView nativeBanner;


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
                .isRoundedMode(true)
                .preventClicks(true) // TODO Note: if you want to be a clickable ad  change it to false.
                .build();

        ((LinearLayout) findViewById(R.id.adLayout)).removeAllViews();
        if (nativeBanner != null) {
            nativeBanner.destroy();
        }

        nativeBanner = new AdMostCustomView(MainActivity.this, NATIVE_SWIPEABLE_ZONE_ID, new AdMostCustomViewListener() {
            @Override
            public void onReady(AdMostCustomView.Data data, View view) {
                Log.d(TAG, String.format("onReady: %s", data.toString()));
                LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
                adLayout.removeAllViews();
                if (view.getParent() != null && view.getParent() instanceof ViewGroup) {
                    ((ViewGroup) view.getParent()).removeAllViews();
                }
                adLayout.addView(view);

                //TODO by Integrating Partner -> Detection of Swipe and calling the method below
                // nativeBanner.goToAdUrl();
            }

            @Override
            public void onFail(int i, String s) {
                Log.d(TAG, String.format("onFail: Error code-> %d , error message-> %s ", i, s));
            }

            @Override
            public void onClick(AdMostCustomView.Data data) {
                Log.d(TAG, String.format("onClick: %s", data.toString()));
            }
        }, customBinder);
        nativeBanner.load();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nativeBanner != null)
            nativeBanner.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nativeBanner != null)
            nativeBanner.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (nativeBanner != null) {
            nativeBanner.destroy();
        }
    }
}
