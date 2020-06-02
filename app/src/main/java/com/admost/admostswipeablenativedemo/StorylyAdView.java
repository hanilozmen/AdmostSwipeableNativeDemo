package com.admost.admostswipeablenativedemo;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.appsamurai.storyly.external.StorylyExternalView;
import com.appsamurai.storyly.external.StorylyExternalViewListener;

import admost.sdk.AdMostCustomView;
import admost.sdk.AdMostViewBinder;
import admost.sdk.listener.AdMostCustomViewListener;

public class StorylyAdView extends StorylyExternalView {
    public static final String TAG = "StorylyAdView";

    private final static String NATIVE_SWIPEABLE_ZONE_ID = "9e5d4b82-aa93-43a1-9fd5-3c71691fbcc7";

    public StorylyExternalViewListener storylyExternalViewListener;

    private AdMostCustomView nativeBanner;

    public StorylyAdView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setupView();
    }

    public StorylyAdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupView();
    }

    public StorylyAdView(Context context) {
        super(context);
        setupView();
    }

    private void setupView() {
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

        nativeBanner = new AdMostCustomView((Activity) getContext(), NATIVE_SWIPEABLE_ZONE_ID, new AdMostCustomViewListener() {
            @Override
            public void onReady(AdMostCustomView.Data data, View view) {
                Log.d(TAG, String.format("onReady: %s", data.toString()));
                // StorylyExternalViewListener handles load callback for ads, this handles async load flows
                storylyExternalViewListener.onLoad(StorylyAdView.this);
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

    // NOTES: We will add a getIcon function, waits a icon url. Is it possible you to provide a icon url for ads

    @Override
    public long getDuration() {
        Log.d(TAG, "getDuration");
        return nativeBanner.getCustomNativeAdDuration() * 1000L;
    }

    @Override
    public @NonNull String getTitle() {
        // NOTES: We've added getTitle function, waits the title for ad. Is it possible you to provide it?
        Log.d(TAG, "getTitle");
        return "Admost Ad";
    }

    @Override
    public void load() {
        Log.d(TAG, "load");
        if (nativeBanner != null) {
            this.removeAllViews();
            this.addView(nativeBanner.getView());
        }
    }

    @Override
    public void pause() {
        // NOTES: pause doesn't pause video
        Log.d(TAG, "pause");
        if (nativeBanner != null)
            nativeBanner.pause();
    }

    @Override
    public void redirect() {
        Log.d(TAG, "redirect");
        if (nativeBanner != null)
            nativeBanner.goToAdUrl();
    }

    @Override
    public void reset() {
        // NOTES: check reset usage
        Log.d(TAG, "reset");
        if (nativeBanner != null)
            nativeBanner.pause();
    }

    @Override
    public void resume() {
        Log.d(TAG, "resume");
        if (nativeBanner != null)
            nativeBanner.resume();
    }

    // NOTES: destroy will be required in StorylyExternalView interface as well
    public void destroy() {
        Log.d(TAG, "destroy");
        if (nativeBanner != null)
            nativeBanner.destroy();
    }
}
