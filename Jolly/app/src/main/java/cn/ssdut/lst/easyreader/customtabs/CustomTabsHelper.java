package cn.ssdut.lst.easyreader.customtabs;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongting on 2017/4/25.
 */

public class CustomTabsHelper {
    private static final String TAG = "CustomTabsHelper";
    static final String STABLE_PACKAGE = "com.android.chrome";
    static final String BETA_PACKAGE = "com.chrome.beta";
    static final String DEV_PACKAGE = "com.chrome.dev";
    static final String LOCAL_PACKAGE = "com.google.android.apps.chrome";
    private static final String EXTRA_CUSTOM_TABS_KEEP_ALIVE =
            "android.support.customtabs.extra.KEEP_ALIVE";
    private static final String ACTION_CUSTOM_TABS_CONNECTION =
            "android.support.customtabs.action.CustomTabsService";

    private static String sPackageNameToUse;

    private CustomTabsHelper(){}

    public static void addKeepAliveExtra(Context context, Intent intent) {
        Intent keepAliveIntent = new Intent().setClassName(context.getPackageName(), KeepAliveService.class.getCanonicalName());
        intent.putExtra(EXTRA_CUSTOM_TABS_KEEP_ALIVE, keepAliveIntent);
    }

    public static String getsPackageNameToUse(Context context) {
        if (sPackageNameToUse != null) {
            return sPackageNameToUse;
        }
        PackageManager pm = context.getPackageManager();

        Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));
        ResolveInfo defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0);
        String defaultViewHandlerPackageName = null;
        if (defaultViewHandlerInfo != null) {
            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName;
        }

        //获取所有能够处理这个Intent的App
        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, 0);
        List<String> packageSupportCustomTabs = new ArrayList<>();
        for (ResolveInfo r : resolvedActivityList) {
            Intent serviceIntent = new Intent(ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(r.activityInfo.packageName);
            if (pm.resolveService(serviceIntent, 0) != null) {
                packageSupportCustomTabs.add(r.activityInfo.packageName);
            }
        }

        if (packageSupportCustomTabs.isEmpty()) {
            sPackageNameToUse = null;
        }  else if (packageSupportCustomTabs.size() == 1) {
            sPackageNameToUse = packageSupportCustomTabs.get(0);
        } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
                && !hasSpecializedHandlerIntents(context, activityIntent)
                && packageSupportCustomTabs.contains(defaultViewHandlerPackageName)) {
            sPackageNameToUse = defaultViewHandlerPackageName;
        } else if (packageSupportCustomTabs.contains(STABLE_PACKAGE)) {
            sPackageNameToUse = STABLE_PACKAGE;
        } else if (packageSupportCustomTabs.contains(BETA_PACKAGE)) {
            sPackageNameToUse = BETA_PACKAGE;
        } else if (packageSupportCustomTabs.contains(DEV_PACKAGE)) {
            sPackageNameToUse = DEV_PACKAGE;
        } else if (packageSupportCustomTabs.contains(LOCAL_PACKAGE)) {
            sPackageNameToUse = LOCAL_PACKAGE;
        }
        return sPackageNameToUse;
    }

    //检查是否有能够处理该Intent的Activity
    private static boolean hasSpecializedHandlerIntents(Context context, Intent intent) {
        try {
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> handlers = pm.queryIntentActivities(
                    intent,
                    PackageManager.GET_RESOLVED_FILTER);
            if (handlers == null || handlers.size() == 0) {
                return false;
            }
            for (ResolveInfo resolveInfo : handlers) {
                IntentFilter filter = resolveInfo.filter;
                //经过下面三重门，如果返回了true，则说明有合适的Activity能够处理该Intent
                if (filter == null) {
                    continue;
                }
                if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) {
                    continue;
                }
                if (resolveInfo.activityInfo == null) {
                    continue;
                }
                return true;
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Runtime exception while getting specialized handlers");
            e.printStackTrace();
        }
        return false;
    }

    public static String[] getPackages() {
        return new String[]{"",STABLE_PACKAGE,BETA_PACKAGE,DEV_PACKAGE,LOCAL_PACKAGE};
    }

}