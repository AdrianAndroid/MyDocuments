// MyIPackageManager.aidl
package com.derry.pmsproject;

// Declare any non-default types here with import statements

import android.content.pm.PackageInfo;

interface MyIPackageManager {

    PackageInfo getPackageInfo(String packageName, int flags, int userId);

    String getPackageInfo2();

}
