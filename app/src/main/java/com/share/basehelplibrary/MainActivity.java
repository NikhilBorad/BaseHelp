package com.share.basehelplibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.share.baselibrary.BaseAppCompactActivity;

public class MainActivity extends MyAppCompactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nbSetUpActionBar("HOME");

        nbToast("HEllo Happy people.");
        nbToast("Have a Good day :) ");
    }
}
