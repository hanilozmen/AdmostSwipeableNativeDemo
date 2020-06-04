package com.admost.admostswipeablenativedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.amr.plugin.storyly.AdMostStorylyViewProvider;
import com.appsamurai.storyly.StorylyInit;
import com.appsamurai.storyly.StorylyView;

import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.listener.AdMostInitListener;

public class MainActivity extends Activity {

    public static final String TAG = "ADMOST_SAMPLE_APP";

    public static final String ADMOST_APP_ID = "6cc8e89a-b52a-4e9a-bb8c-579f7ec538fe";
    public static final String NATIVE_ZONE_ID = "9e5d4b82-aa93-43a1-9fd5-3c71691fbcc7";


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

        StorylyView storylyView = findViewById(R.id.storyly_view);
        storylyView.setStorylyInit(new StorylyInit("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjU1NiwiYXBwX2lkIjozMTksImluc19pZCI6MzE5fQ.AMVZQxwyXO0mSWFhuxOsNAv8kOXZnSvniyKHV2-izFk"));
        // StorylyExternalProvider listener will handle upcoming request flow for ads
        storylyView.setStorylyExternalViewProvider(new AdMostStorylyViewProvider(this,NATIVE_ZONE_ID ));
    }

}
