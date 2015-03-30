package com.jacob.waveview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;


public class MainActivity extends FragmentActivity {
    private WaterWaveView mWaterWave;
    int progress = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWaterWave = (WaterWaveView) findViewById(R.id.waterWaveView);
    }

    public void start(View view) {
        mWaterWave.startWave();
    }

    public void stop(View view) {
        mWaterWave.stopWave();
    }

    public void progressUp(View view) {
        mWaterWave.setProgress((progress++));
    }

    public void progressDown(View view) {
        mWaterWave.setProgress((progress--));
    }

}
