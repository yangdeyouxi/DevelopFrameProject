package com.develop.frame.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

import com.develop.frame.cache.GlobalCache;
import com.develop.frame.util.permission.PermissionCallback;
import com.develop.frame.util.permission.PermissionUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by czk on 2017/6/22.
 */

public class SystemUtil {

    public static int getAppVersionCode() {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = GlobalCache.mApplication.getPackageManager().getPackageInfo(GlobalCache.mApplication.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getAppVersionName() {
        String versionName = "";
        try {
            PackageInfo packageInfo = GlobalCache.mApplication.getPackageManager().getPackageInfo(GlobalCache.mApplication.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取当前时区
     * @return
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return strTz;

    }


    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号 6.0 7.0
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    /**
     * 获取当前手机系统版本号 23 24 25
     *
     * @return 系统版本号
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    /**
     * android 获取屏幕分辩率
     *
     * @param context
     * @return
     */
    public static String getMetrics(Activity context) {
        if (0 != screenWidth) {
            return screenHeight + "*" + screenWidth;
        }
        if (null == context) {
            return screenHeight + "*" + screenWidth;
        }
        DisplayMetrics dm;
        dm = context.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
//        int h = dm.heightPixels;
//        int w = dm.widthPixels;
        return screenHeight + "*" + screenWidth;
    }

    /**
     * 获取手机系统语言
     *
     * @param context
     * @return
     */
    public static String getLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
//        if (language.endsWith("zh"))
//            return "0";
//        else
//            return "1";
        return language;
    }

    /**
     * 获取CPU信息
     *
     * @return
     */
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取手机ip
     *
     * @return
     */
    public static String getIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            HandleException.printException(e);
//            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取无线网名称，需要权限：
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     *
     * @param ctx
     * @return
     */
    public static String getConnectWifiSsid(Context ctx) {
        try {
            WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return wifiInfo.getSSID();
        } catch (Exception e) {
            HandleException.printException(e);
            return "";
        }
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */

    static String deviceId = "";
    public static String getDeviceId(final Activity ctx, DeviceCallBack callBack) {

        PermissionUtils.getInstance().requestPermission(ctx, new PermissionCallback() {
            @Override
            public void onSuccess(List<String> permissions) {
                deviceId = DeviceUuidFactory.getDeviceId(ctx);
                callBack.onSuccess(deviceId);
            }

            @Override
            public void onFail(List<String> permissions) {
                deviceId = DeviceUuidFactory.getDeviceId(ctx);
                callBack.onSuccess(deviceId);
            }
        }, Manifest.permission.READ_PHONE_STATE);

        return deviceId;
    }

    /**
     * 手机号码
     */
    static String lineNum = "";
    public static String getLine1Number(Activity activity) {

        PermissionUtils.getInstance().requestPermission(activity, new PermissionCallback() {
            @Override
            public void onSuccess(List<String> permissions) {
                TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    lineNum = tm.getLine1Number();
                }
            }

            @Override
            public void onFail(List<String> permissions) {

            }
        }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_NUMBERS);
        return lineNum;
    }

    /**
     * 运营商名称
     */
    public static String getNetworkOperatorName(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) {
                return "";
            }
            return "" + tm.getNetworkOperatorName();
        } catch (Exception e) {
            HandleException.printException(e);
            return "";
        }
    }

    /**
     * sim卡序列号
     */
    static String simSerialNumber  = "";
    public static String getSimSerialNumber(Activity activity) {

        PermissionUtils.getInstance().requestPermission(activity, new PermissionCallback() {
            @Override
            public void onSuccess(List<String> permissions) {
                TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    simSerialNumber = tm.getSimSerialNumber();
                }
            }

            @Override
            public void onFail(List<String> permissions) {

            }
        }, Manifest.permission.READ_PHONE_STATE);
        return simSerialNumber;
    }

    /**
     * IMSI
     *
     * @param context
     * @return
     */
    static String imsi = "";
    public static String getSubscriberId(Activity activity) {

        PermissionUtils.getInstance().requestPermission(activity, new PermissionCallback() {
            @Override
            public void onSuccess(List<String> permissions) {
                TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    imsi = tm.getSubscriberId();
                }
            }

            @Override
            public void onFail(List<String> permissions) {

            }
        }, Manifest.permission.READ_PHONE_STATE);
        return imsi;

    }

    /**
     * sim卡所在国家
     *
     * @param context
     * @return
     */
    public static String getNetworkCountryIso(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) {
                return "";
            }
            return "" + tm.getNetworkCountryIso();
        } catch (Exception e) {
            HandleException.printException(e);
            return "";
        }
    }

    /**
     * 运营商编号。
     *
     * @param context
     * @return
     */
    public static String getNetworkOperator(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) {
                return "";
            }
            return tm.getNetworkOperator();
        } catch (Exception e) {
            HandleException.printException(e);
            return "";
        }
    }

    /**
     * 获取mac地址
     *
     * @param context
     * @return
     */
    public static String getAdresseMAC(Context context) {
        String realMac = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iF = interfaces.nextElement();
                byte[] addr = iF.getHardwareAddress();
                if (addr == null || addr.length == 0) {
                    continue;
                }
                StringBuilder buf = new StringBuilder();
                for (byte b : addr) {
                    buf.append(String.format("%02X:", b));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                String mac = buf.toString();
                if (iF.getName().contains("wlan")) {
                    realMac = mac;
                }
                Log.i("mac", "iF.getName()=" + iF.getName());
                Log.i("mac", "mac=" + mac);
            }
        } catch (Exception e) {
            HandleException.printException(e);
        }
        return realMac;
    }


    // 获取手机可用内存和总内存
    private String getSystemMemory(Context context) {
    /*
     * 在android开发中，有时候我们想获取手机的一些硬件信息，比如android手机的总内存和可用内存大小。这个该如何实现呢？
     * 通过读取文件"/proc/meminfo"  的信息能够获取手机Memory的总量，而通过ActivityManager.getMemoryInfo
     * (ActivityManager.MemoryInfo)方法可以获取当前的可用Memory量。
     * "/proc/meminfo"文件记录了android手机的一些内存信息
     * ，在命令行窗口里输入"adb shell"，进入shell环境，输入
     * "cat /proc/meminfo"即可在命令行里显示meminfo文件的内容，具体如下所示。
     *
     * C:\Users\Figo>adb shell # cat /proc/meminfo cat /proc/meminfo
     * MemTotal: 94096 kB
      * MemFree: 1684 kB     Buffers: 16 kB     Cached: 27160 kB
     * SwapCached: 0 kB     Active: 35392 kB    Inactive: 44180 kB
     *  Active(anon): 26540 kB Inactive(anon): 28244 kB   Active(file): 8852 kB
     * Inactive(file): 15936 kB  Unevictable: 280 kB  Mlocked: 0 kB       * SwapTotal:   0 kB SwapFree: 0 kB Dirty: 0 kB Writeback: 0 kB AnonPages: 52688 kB
     * Mapped: 17960 kB Slab: 3816 kB SReclaimable: 936 kB SUnreclaim: 2880
     * kB PageTables: 5260 kB NFS_Unstable: 0 kB Bounce: 0 kB
     * WritebackTmp:   0 kB
     * CommitLimit: 47048 kB
     * Committed_AS: 1483784 kB
     * VmallocTotal:  876544 kB
     * VmallocUsed: 15456 kB
      * VmallocChunk: 829444 kB #
     *
     * 下面先对"/proc/meminfo"文件里列出的字段进行粗略解释： MemTotal: 所有可用RAM大小。 MemFree:
     * LowFree与HighFree的总和，被系统留着未使用的内存。 Buffers: 用来给文件做缓冲大小。 Cached:
     * 被高速缓冲存储器（cache memory）用的内存的大小（等于diskcache minus SwapCache）。
     * SwapCached:被高速缓冲存储器（cache
     * memory）用的交换空间的大小。已经被交换出来的内存，仍然被存放在swapfile中，
     * 用来在需要的时候很快的被替换而不需要再次打开I/O端口。 Active:
     * 在活跃使用中的缓冲或高速缓冲存储器页面文件的大小，除非非常必要，否则不会被移作他用。 Inactive:
     * 在不经常使用中的缓冲或高速缓冲存储器页面文件的大小，可能被用于其他途径。 SwapTotal: 交换空间的总大小。 SwapFree:
     * 未被使用交换空间的大小。 Dirty: 等待被写回到磁盘的内存大小。 Writeback: 正在被写回到磁盘的内存大小。
     * AnonPages：未映射页的内存大小。 Mapped: 设备和文件等映射的大小。 Slab:
     * 内核数据结构缓存的大小，可以减少申请和释放内存带来的消耗。 SReclaimable:可收回Slab的大小。
     * SUnreclaim：不可收回Slab的大小（SUnreclaim+SReclaimable＝Slab）。
     * PageTables：管理内存分页页面的索引表的大小。 NFS_Unstable:不稳定页表的大小。
     * 要获取android手机总内存大小，只需读取"/proc/meminfo"文件的第1行，并进行简单的字符串处理即可。
     */
        String availMemory = getAvailMemory(context);
        String totalMemory = getTotalMemory(context);
        return "可用内存=" + availMemory + "\n" + "总内存=" + totalMemory;

    }// 手机的内存信息主要在/proc/meminfo文件中，其中第一行是总内存，而剩余内存可通过ActivityManager.MemoryInfo得到。

    public static String getAvailMemory(Context context) {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }

    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }


    public static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }


    //没有网络连接
    public static final String NETWORK_NONE = "NETWORN_NONE";
    //wifi连接
    public static final String NETWORK_WIFI = "NETWORK_WIFI";
    //手机网络数据连接类型
    public static final String NETWORK_2G = "NETWORK_2G";
    public static final String NETWORK_3G = "NETWORK_3G";
    public static final String NETWORK_4G = "NETWORK_4G";
    public static final String NETWORK_MOBILE = "NETWORK_MOBILE";

    /**
     * 获取当前网络连接类型
     *
     * @param context
     * @return
     */
    public static String getNetworkState(Context context) {
        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //如果当前没有网络
        if (null == connManager)
            return NETWORK_NONE;

        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORK_NONE;
        }

        // 判断是不是连接的是不是wifi
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI;
                }
        }

        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        //如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORK_2G;
                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORK_3G;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_4G;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return NETWORK_3G;
                            } else {
                                return NETWORK_MOBILE;
                            }
                    }
                }
        }
        return NETWORK_NONE;
    }

    /**
     * 获取当前网络连接类型序号
     *
     * @param context
     * @return
     */
    public static int getNetworkStateNum(Context context) {
        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //如果当前没有网络
        if (null == connManager) return 0;

        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return 0;
        }

        // 判断是不是连接的是不是wifi
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return 2;
                }
        }

        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        //如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return 3;
                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return 4;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return 5;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return 4;
                            } else {
                                return 4;
                            }
                    }
                }
        }
        return 0;
    }


    public static String getPhoneInfoToString(Activity context) {
        return "\n手机imei码*" + getDeviceId(context,null) +
                "\n手机imsi码*" + getSubscriberId(context) +
                "\n手机mac地址*" + getAdresseMAC(context) +
                "\n手机系统版本*" + getSystemVersion() +
                "\n手机品牌*" + getDeviceBrand() +
                "\n手机型号*" + getSystemModel() +
                "\n手机硬件配置*" + getCpuName() +
                "\n手机IP地址*" + getIpAddressString() +
                "\n手机号*" + getLine1Number(context) +
                "\n语言环境*" + getSystemLanguage() +
                "\n无线网名称*" + getConnectWifiSsid(context) +
                "\n运营商*" + getNetworkOperatorName(context) +
                "\n总内存*" + getTotalMemory(context) +
                "\n当前可用内存*" + getAvailMemory(context) +
                "\n手机分辨率*" + getMetrics(context) +
                "\nsim卡*" + getSimSerialNumber(context);
    }


    public interface SystemInfoCallBack{
        void onSuccess(String result);
        void onFail();
    }

    public interface DeviceCallBack {
        void onSuccess(String deviceId);
    }


}
