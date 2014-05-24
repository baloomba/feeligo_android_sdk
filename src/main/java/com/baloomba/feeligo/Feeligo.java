package com.baloomba.feeligo;

import android.content.Context;

import com.android.volley.VolleyError;
import com.baloomba.feeligo.helper.FeeligoSettings;
import com.baloomba.feeligo.network.FeeligoRequestBuilder;
import com.baloomba.wsvolley.WSManager;
import com.baloomba.wsvolley.WSResponseListener;
import com.baloomba.wsvolley.WSStringResponseListener;

import java.util.regex.Pattern;

public class Feeligo {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = Feeligo.class.getSimpleName();
    private final static String REG_EXP = "\\[s:([a-zA-Z0-9\\/\\.\\?\\=]+[a-zA-Z0-9]*)\\]";

    private static Feeligo sInstance;

    private Long mUserId;

    // </editor-fold>

    // <editor-fold desc="INSTANCE">

    public static Feeligo getInstance() {
        return sInstance;
    }

    // </editor-fold>

    // <editor-fold desc="MEMBER METHODS">

    public void init(Context context) {
        if (sInstance == null) {
            sInstance = this;
            FeeligoSettings.getInstance(context).init();
            if (WSManager.getInstance() == null)
                new WSManager().init(context);
        }
    }

    public void connect(Long userId) {
        mUserId = userId;
        updateStickerPack();
        updateFeeligoFont();
    }

    public void updateStickerPack() {
        getRecentStickers(new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {}

            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        getPopularStickers(new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {}

            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        getUserStickerPack(new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {}

            @Override
            public void onErrorResponse(VolleyError error) {}
        });
    }

    private void updateFeeligoFont() {

    }

    public void getUserStickerPack(WSResponseListener callbacks) {
        FeeligoRequestBuilder.getUserStickerPack(mUserId, callbacks);
    }

    public void getRecentStickers(WSResponseListener callbacks) {
        FeeligoRequestBuilder.getRecentStickers(mUserId, callbacks);
    }

    public void getPopularStickers(WSResponseListener callbacks) {
        FeeligoRequestBuilder.getPopularStickers(callbacks);
    }

    public void setRecentSticker(Long stickerId) {
        FeeligoRequestBuilder.setRecentSticker(mUserId, stickerId, new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {}

            @Override
            public void onErrorResponse(VolleyError error) {}
        });
    }

    // </editor-fold>

    // <editor-fold desc="STATIC METHODS">

    public static boolean containsSticker(String text) {
        return Pattern.compile(REG_EXP).matcher(text).find();
    }

    // </editor-fold>

}
