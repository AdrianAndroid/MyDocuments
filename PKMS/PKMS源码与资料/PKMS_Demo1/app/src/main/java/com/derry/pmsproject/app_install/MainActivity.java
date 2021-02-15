//package com.derry.pmsproject.app_install;
//
//import android.content.Context;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//
//import com.derry.pmsproject.R;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // 说说 /frameworks/base/core/java/android/content/pm/IPackageManager.aidl
//
//        // MyIPackageManager.Stub // 暂停
//
//        // OS 使用系统的PKMS
//        Context context = this;
//
//        try {
//            context.getPackageManager().getPackageInfo("com.derry.xx", 0)
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
