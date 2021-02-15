/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\xiangxue\\TSJ_PMS_1\\project1\\PKMS_Demo1\\app\\src\\main\\aidl\\com\\derry\\pmsproject\\MyIPackageManager.aidl
 */
package com.derry.pmsproject;
public interface MyIPackageManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.derry.pmsproject.MyIPackageManager
{
private static final java.lang.String DESCRIPTOR = "com.derry.pmsproject.MyIPackageManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.derry.pmsproject.MyIPackageManager interface,
 * generating a proxy if needed.
 */
public static com.derry.pmsproject.MyIPackageManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.derry.pmsproject.MyIPackageManager))) {
return ((com.derry.pmsproject.MyIPackageManager)iin);
}
return new com.derry.pmsproject.MyIPackageManager.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_getPackageInfo:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
android.content.pm.PackageInfo _result = this.getPackageInfo(_arg0, _arg1, _arg2);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getPackageInfo2:
{
data.enforceInterface(descriptor);
java.lang.String _result = this.getPackageInfo2();
reply.writeNoException();
reply.writeString(_result);
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.derry.pmsproject.MyIPackageManager
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public android.content.pm.PackageInfo getPackageInfo(java.lang.String packageName, int flags, int userId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.content.pm.PackageInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
_data.writeInt(flags);
_data.writeInt(userId);
mRemote.transact(Stub.TRANSACTION_getPackageInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.content.pm.PackageInfo.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getPackageInfo2() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPackageInfo2, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getPackageInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getPackageInfo2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public android.content.pm.PackageInfo getPackageInfo(java.lang.String packageName, int flags, int userId) throws android.os.RemoteException;
public java.lang.String getPackageInfo2() throws android.os.RemoteException;
}
