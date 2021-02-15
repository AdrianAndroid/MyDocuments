package com.derry.pmsproject.pms;

import android.content.pm.PackageInfo;

public abstract class MyPackageManager {

    public abstract PackageInfo getPackageInfo(String packageName, int flags);

    public abstract String getPackageInfo2();

}
