package fr.baloomba.feeligo.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FeeligoStickerPackTextView extends TextView {

    // <editor-fold desc="CONSTRUCTORS">

    public FeeligoStickerPackTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public FeeligoStickerPackTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FeeligoStickerPackTextView(Context context) {
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
