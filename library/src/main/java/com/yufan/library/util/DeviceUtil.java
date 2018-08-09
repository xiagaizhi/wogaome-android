package com.yufan.library.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.yufan.library.base.BaseApplication;

import java.lang.reflect.Method;

public class DeviceUtil {


    /**
     * 获取程序的权限
     */
    public static String[] AppPremission(Context context) {
        try {
            String packname = context.getPackageName();
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo = pm.getPackageInfo(packname,
                    PackageManager.GET_PERMISSIONS);
            // 获取到所有的权限
            return packinfo.requestedPermissions;
 
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
 
        }
        return null;
    }
 
    /**
     * 获取程序的签名
     */
    public static String AppSignature(Context context) {
        try {
            String packname = context.getPackageName();
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo = pm.getPackageInfo(packname,
                    PackageManager.GET_SIGNATURES);
            // 获取到所有的权限
            return packinfo.signatures[0].toCharsString();
 
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
 
        }
        return "No Search";
    }
 
    /**
     * 获得程序图标
     */
    public static Drawable AppIcon(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(
                    context.getPackageName(), 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
 
        }
        return null;
    }
 
    /**
     * 获得程序名称
     */
    public static String AppName(Context context) {
        try {
            String packname = context.getPackageName();
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "No Search";
    }
 
    /**
     * 获得软件版本号
     */
    public static int VersionCode(Context context) {
        int versioncode = 0;
        try {
            versioncode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
 
        return versioncode;
    }
 
    /**
     * 获得软件版本名
     */
    public static String VersionName(Context context) {
        String versionname = "";
        try {
            versionname = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
 
        return versionname;
    }
 
    /**
     * 得到软件包名
     */
    public static String PackgeName(Context context) {
        String packgename = "";
        try {
            packgename = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packgename;
    }
 
    /**
     * 获得imei号
     */
    public static String IMEI(Context context) {
        String imei = "";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        return imei;
    }
 
    /**
     * 获得imsi号
     */
    public static String IMSI(Context context) {
        String imsi = "";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        imsi = telephonyManager.getSubscriberId();
        return imsi;
    }
 
    /**
     * 返回本机电话号码
     */
    public static String Num(Context context) {
        String num = "";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        num = telephonyManager.getLine1Number();
        return num;
    }
 
    /**
     * 得到手机产品序列号
     */
    public static String SN() {
        String sn = "";
        String serial = android.os.Build.SERIAL;// 第二种得到序列号的方法
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            sn = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
 
            e.printStackTrace();
        }
        return sn;
    }
 
    /**
     * 获得手机sim号
     */
    public static String SIM(Context context) {
        String sim = "";
 
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        sim = telephonyManager.getSimSerialNumber();
 
        return sim;
    }
 
    /**
     * 返回安卓设备ID
     */
    public static String ID(Context context) {
        String id = "";
        id = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
 
        return id;
    }
 
    /**
     * 得到设备mac地址
     */
    public static String MAC(Context context) {
        String mac = "";
        WifiManager manager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        mac = info.getMacAddress();
        return mac;
    }
 
    /**
     * 得到当前系统国家和地区
     */
    public static String Country(Context context) {
        String country = "";
        country = context.getResources().getConfiguration().locale.getCountry();
        return country;
    }
 
    /**
     * 得到当前系统语言
     */
    public static String Language(Context context) {
        String language = "";
        String country = context.getResources().getConfiguration().locale
                .getCountry();
        language = context.getResources().getConfiguration().locale
                .getLanguage();
        // 区分简体和繁体中文
        if (language.equals("zh")) {
            if (country.equals("CN")) {
                language = "Simplified Chinese";
            } else {
                language = "Traditional Chinese";
            }
        }
        return language;
    }
 
    /**
     * 返回系统屏幕的高度（像素单位）
     */
    public static int Height(Context context) {
        int height = 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        height = dm.heightPixels;
        return height;
    }
 
    /**
     * 返回系统屏幕的宽度（像素单位）
     */
    public static int Width(Context context) {
        int width = 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        width = dm.widthPixels;
        return width;
    }
    /**
     * 返回UserAgent
     * @return
     */
    private  static String getUserAgent(){

        StringBuffer sb = new StringBuffer();
        String   userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)

        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        sb.append("/LekeApp");
        Log.v("User-Agent","User-Agent: "+ sb.toString());
        return sb.toString();
    }
}
