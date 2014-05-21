package com.baloomba.feeligo.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class StickerTextView extends TextView {

    // <editor-fold desc="CONSTRUCTORS">

    public StickerTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public StickerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickerTextView(Context context) {
        super(context);
        init(context);
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/flgsticker.ttf");
        setTypeface(tf);
    }

    // </editor-fold>

}
