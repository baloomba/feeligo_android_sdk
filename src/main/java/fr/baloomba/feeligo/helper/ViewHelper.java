package fr.baloomba.feeligo.helper;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class ViewHelper {

    public static void setEnabledViewWithAlpha(View view, Boolean isEnabled) {
        float alpha = isEnabled ? 1.0f : 0.5f;
        view.setEnabled(isEnabled);
        setAlpha(view, alpha);
    }

    public static void setAlpha(View view, float alpha) {
        if (alpha != 0) {
            view.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
                animation.setDuration(0);
                animation.setFillAfter(true);
                view.startAnimation(animation);
            } else
                view.setAlpha(alpha);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            view.setBackgroundDrawable(background);
        else
            view.setBackground(background);
    }

}
