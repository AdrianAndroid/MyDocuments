package com.derry.pmsproject.pms;

import android.content.pm.PackageInfo;
import android.os.RemoteException;

import com.derry.pmsproject.MyIPackageManager;

// Binder机制的服务端  PKMS
public class MyPackageManagerService extends MyIPackageManager.Stub {

    @Override
    public PackageInfo getPackageInfo(String packageName, int flags, int userId) throws RemoteException {
        return null;
    }

    @Override
    public String getPackageInfo2() throws RemoteException {
        return "Derry";
    }
}
