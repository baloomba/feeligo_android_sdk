package fr.baloomba.feeligo.helper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class FeeligoHelper {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoHelper.class.getSimpleName();

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public static Object retrieveData(Context context, String key) {
        return retrieveData(context, key, null);
    }

    public static Object retrieveData(Context context, String key, Object defaultValue) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo == null || applicationInfo.metaData == null)
                return null;
            return applicationInfo.metaData.get(key);
        } catch (PackageManager.NameNotFoundException e) {
            if (defaultValue != null)
                return defaultValue;
            else
                throw new IllegalArgumentException(key + " must be set in your manifest");
        }
    }

    // </editor-fold>

}
