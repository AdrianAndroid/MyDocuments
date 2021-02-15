package com.derry.pmsproject.pms;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.derry.pmsproject.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // My
        MyContext myContext = new MyContext();
        myContext.getPackageManager().getPackageInfo("com.derry.xxx", 0);

        String result = myContext.getPackageManager().getPackageInfo2();
    }
}
