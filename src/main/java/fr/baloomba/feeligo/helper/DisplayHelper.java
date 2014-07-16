package fr.baloomba.feeligo.helper;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;

public class DisplayHelper {

    @SuppressWarnings("deprecation")
    public static int getWidth(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return activity.getWindowManager().getDefaultDisplay().getWidth();
        } else {
            Point outSize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(outSize);
            return outSize.x;
        }
    }

    @SuppressWarnings("deprecation")
    public static int getHeight(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return activity.getWindowManager().getDefaultDisplay().getHeight();
        } else {
            Point outSize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(outSize);
            return outSize.y;
        }
    }

}
