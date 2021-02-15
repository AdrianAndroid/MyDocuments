package com.derry.pmsproject.pms;

import android.content.pm.PackageInfo;
import android.os.RemoteException;

import com.derry.pmsproject.MyIPackageManager;

public class MyApplicationPackageManager extends MyPackageManager {

    private MyIPackageManager mPM;

    @Override
    public PackageInfo getPackageInfo(String packageName, int flags) {
        try {
            return mPM.getPackageInfo(packageName, flags, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
