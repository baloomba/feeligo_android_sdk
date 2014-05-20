package com.baloomba.feeligo.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.baloomba.wsvolley.widget.NetworkImageView;

public class StickerImageView extends NetworkImageView {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = StickerImageView.class.getSimpleName();

    private static final String NAME_SPACE = "http://schemas.android.com/apk/res-auto";

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    public StickerImageView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public StickerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public StickerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    // </editor-fold>

    // <editor-fold desc="MEMBER METHODS">

    public void init(Context context, AttributeSet attrs, int defStyle) {
        mRatio = 1f;
    }

    public void setStickerCode() {

    }

    // </editor-fold>

}
