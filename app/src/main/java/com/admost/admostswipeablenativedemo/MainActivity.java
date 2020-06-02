package com.admost.admostswipeablenativedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.appsamurai.storyly.StorylyInit;
import com.appsamurai.storyly.StorylyView;
import com.appsamurai.storyly.external.StorylyExternalViewListener;
import com.appsamurai.storyly.external.StorylyExternalViewProvider;

import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.listener.AdMostInitListener;

public class MainActivity extends Activity {
    private StorylyAdView storylyAdView;

    public static final String TAG = "ADMOST_SAMPLE_APP";

    public static final String ADMOST_APP_ID = "6cc8e89a-b52a-4e9a-bb8c-579f7ec538fe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StorylyView storylyView = findViewById(R.id.storyly_view);
        storylyView.setStorylyInit(new StorylyInit("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjU1NiwiYXBwX2lkIjozMTksImluc19pZCI6MzE5fQ.AMVZQxwyXO0mSWFhuxOsNAv8kOXZnSvniyKHV2-izFk"));
        // StorylyExternalProvider listener will handle upcoming request flow for ads
        storylyView.setStorylyExternalViewProvider(new StorylyExternalViewProvider() {
            @Override
            public void onRequest(@NonNull StorylyExternalViewListener storylyExternalViewListener) {
                createStorylyAdView(storylyExternalViewListener);
            }
        });

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
    }

    private void createStorylyAdView(@NonNull StorylyExternalViewListener storylyExternalViewListener) {
        storylyAdView = new StorylyAdView(this);
        storylyAdView.storylyExternalViewListener = storylyExternalViewListener;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (storylyAdView != null)
            storylyAdView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (storylyAdView != null)
            storylyAdView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (storylyAdView != null)
            storylyAdView.destroy();
    }
}
