package com.baloomba.feeligo.helper;

import android.content.Context;

import com.baloomba.feeligo.R;

public class FeeligoSettings {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoSettings.class.getSimpleName();

    private static final String FEELIGO_DOMAIN_KEY = "com.feeligo.Domain";
    private static final String FEELIGO_ACTIVE_COLOR = "com.feeligo.Color";
    private static final String FEELIGO_STORE_AVAILABLE =
            "com.feeligo.StoreAvailable";
    private static final String FEELIGO_RECENT_STICKER_AVAILABLE =
            "com.feeligo.RecentStickerAvailable";
    private static final String FEELIGO_POPULAR_STICKER_AVAILABLE =
            "com.feeligo.PopularStickerAvailable";
    private static final String FEELIGO_SEARCH_STICKER_AVAILABLE =
            "com.feeligo.SearchStickerAvailable";

    private static FeeligoSettings sInstance;

    private Context mContext;

    private static String sDomain;
    private static int sActiveColor;
    private static Boolean sStoreAvailable;
    private static Boolean sRecentAvailable;
    private static Boolean sPopularAvailable;
    private static Boolean sSearchAvailable;

    // </editor-fold>

    // <editor-fold desc="INSTANCE">

    public static FeeligoSettings getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FeeligoSettings(context);
        }
        return sInstance;
    }

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    public FeeligoSettings(Context context) {
        mContext = context;
    }

    // </editor-fold>

    // <editor-fold desc="GETTERS">

    public static String getDomain() {
        return sDomain;
    }

    public static int getActiveColor() {
        return sActiveColor;
    }

    public static Boolean getStoreAvailable() {
        return sStoreAvailable;
    }

    public static Boolean getRecentAvailable() {
        return sRecentAvailable;
    }

    public static Boolean getPopularAvailable() {
        return sPopularAvailable;
    }

    public static Boolean getSearchAvailable() {
        return sSearchAvailable;
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public void init() {
        sDomain = (String)FeeligoHelper.retrieveData(mContext, FEELIGO_DOMAIN_KEY);
        try {
            sActiveColor = (Integer)FeeligoHelper.retrieveData(mContext, FEELIGO_ACTIVE_COLOR);
        } catch (Exception e) {
            sActiveColor = mContext.getResources().getColor(R.color.default_active_color);
        }
        sStoreAvailable = (Boolean)FeeligoHelper.retrieveData(mContext,
                FEELIGO_STORE_AVAILABLE, true);
        sRecentAvailable = (Boolean)FeeligoHelper.retrieveData(mContext,
                FEELIGO_RECENT_STICKER_AVAILABLE, true);
        sPopularAvailable = (Boolean)FeeligoHelper.retrieveData(mContext,
                FEELIGO_POPULAR_STICKER_AVAILABLE, false);
        sSearchAvailable = (Boolean)FeeligoHelper.retrieveData(mContext,
                FEELIGO_SEARCH_STICKER_AVAILABLE, false);
    }

    // </editor-fold>

}
