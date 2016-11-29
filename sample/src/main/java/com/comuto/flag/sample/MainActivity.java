package com.comuto.flag.sample;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import com.comuto.flag.Flaggr;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Flaggr flaggr = Flaggr.with(this);
        flaggr.loadConfig("MY_URL", "MY_LOCAL_URI");
    }
}
