package com.admost.admostswipeablenativedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.amr.plugin.storyly.AdMostStorylyViewProvider;
import com.appsamurai.storyly.StorylyInit;
import com.appsamurai.storyly.StorylySegmentation;
import com.appsamurai.storyly.StorylyView;

import java.util.HashSet;

import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.listener.AdMostInitListener;

public class MainActivity extends Activity {

    public static final String TAG = "ADMOST_SAMPLE_APP";

    public static final String ADMOST_APP_ID = "caa3dd5e-1617-ac3a-3d00-56adcaaddd6e";
    public static final String NATIVE_ZONE_ID = "cbe97e50-3366-4e91-aba2-6658b36e639c";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }



    public void test() {
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

        HashSet<String> segment = new HashSet<String>();
        segment.add("main_page");
        StorylyView storylyView = findViewById(R.id.storyly_view);
        storylyView.setStorylyInit(new StorylyInit("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjk1NSwiYXBwX2lkIjo1NzgsImluc19pZCI6NTY2fQ.IDo71M-GZ-EPGvbHwkw44EKaNDuFHxTD7Tsq7Z6JoAs",new StorylySegmentation(segment)));
        // StorylyExternalProvider listener will handle upcoming request flow for ads
        storylyView.setStorylyExternalViewProvider(new AdMostStorylyViewProvider(this,NATIVE_ZONE_ID ));
    }

}
