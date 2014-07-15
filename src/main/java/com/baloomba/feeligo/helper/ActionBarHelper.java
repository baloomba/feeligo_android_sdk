package com.baloomba.feeligo.helper;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;

public class ActionBarHelper {

    public static Drawable setColor(ActionBarActivity activity, int colorRes,
                                    Drawable.Callback drawableCallback) {
        Drawable colorDrawable = new ColorDrawable(colorRes);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            colorDrawable.setCallback(drawableCallback);
        else
            activity.getSupportActionBar().setBackgroundDrawable(colorDrawable);
        return colorDrawable;
    }

    public static Drawable setColor(ActionBarActivity activity, int colorRes,
                                         Drawable oldBackground,
                                         Drawable.Callback drawableCallback) {
        Drawable colorDrawable = new ColorDrawable(activity.getResources().getColor(colorRes));
        if (oldBackground == null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                colorDrawable.setCallback(drawableCallback);
            } else {
                activity.getSupportActionBar().setBackgroundDrawable(colorDrawable);
            }
        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground,
                    colorDrawable});
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                td.setCallback(drawableCallback);
            } else {
                activity.getSupportActionBar().setBackgroundDrawable(td);
            }
            td.startTransition(200);
        }

        return colorDrawable;
    }

}
