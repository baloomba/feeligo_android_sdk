package com.baloomba.feeligo;

import android.os.Parcel;
import android.os.Parcelable;

import com.baloomba.feeligo.helper.JSONHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class StickerImage implements Parcelable {

    // <editor-fold desc="GLOBAL VARIABLES">

    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;

    // </editor-fold>

    // <editor-fold desc="VARIABLES">

    private static final String BASE_URL = "http://stkr.es/";
    private static final String SMALL_PREFIX = "p/";
    private static final String MEDIUM_PREFIX = "p3w/";
    private static final String LARGE_PREFIX = "p7s/";

    private String mCode;
    private String mSmallURL;
    private Boolean mSmallImageAvailable;
    private String mMediumURL;
    private Boolean mMediumImageAvailable;
    private String mLargeURL;
    private Boolean mLargeImageAvailable;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    protected StickerImage(Init<?> builder) {
        mSmallURL = builder.mSmallURL;
        mSmallImageAvailable = builder.mSmallImageAvailable;
        mMediumURL = builder.mMediumURL;
        mMediumImageAvailable = builder.mMediumImageAvailable;
        mLargeURL = builder.mLargeURL;
        mLargeImageAvailable = builder.mLargeImageAvailable;
    }

    protected StickerImage(Parcel in) {
        mSmallURL = in.readString();
        mSmallImageAvailable = in.readInt() == 1;
        mMediumURL = in.readString();
        mMediumImageAvailable = in.readInt() == 1;
        mLargeURL = in.readString();
        mLargeImageAvailable = in.readInt() == 1;
    }

    // </editor-fold>

    // <editor-fold desc="GETTERS">

    public String getSmallURL() {
        return mSmallURL;
    }

    public String getMediumURL() {
        return mMediumImageAvailable ? mMediumURL : getSmallURL();
    }

    public String getLargeURL() {
        return mLargeImageAvailable ? mLargeURL : getMediumURL();
    }

    public String getURL(int size) {
        switch (size) {
            case MEDIUM:
                return getMediumURL();
            case LARGE:
                return getLargeURL();
            case SMALL:
            default:
                return getSmallURL();
        }
    }

    public String getMessage() {
        return "[s:" + SMALL_PREFIX + mCode + "]";
    }

    // </editor-fold>

    // <editor-fold desc="SETTERS">

    // </editor-fold>

    // <editor-fold desc="METHODS">

    // </editor-fold>

    //<editor-fold desc="PARCELABLE METHODS IMPLEMENTATION">

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mSmallURL);
        parcel.writeInt(mSmallImageAvailable ? 1 : 0);
        parcel.writeString(mMediumURL);
        parcel.writeInt(mMediumImageAvailable ? 1 : 0);
        parcel.writeString(mLargeURL);
        parcel.writeInt(mLargeImageAvailable ? 1 : 0);
    }

    public static final Creator<StickerImage> CREATOR =
            new Creator<StickerImage>() {
                @Override
                public StickerImage createFromParcel(Parcel source) {
                    return new StickerImage(source);
                }

                @Override
                public StickerImage[] newArray(int size) {
                    return new StickerImage[size];
                }
            };

    //</editor-fold>

    // <editor-fold desc="FACTORY CLASS">

    public static class Factory {

        // <editor-fold desc="VARIABLES">

        private static Factory sInstance = new Factory();

        private static final String JSON_KEY_SIZES = "sizes";
        private static final String SMALL_JSON_KEY = "70x70";
        private static final String MEDIUM_JSON_KEY = "140x140";
        private static final String LARGE_JSON_KEY = "280x280";

        // </editor-fold>

        // <editor-fold desc="INSTANCE">

        public static Factory getInstance() {
            return sInstance;
        }

        // </editor-fold>

        // <editor-fold desc="METHODS">

//        {
//          "sizes": {
//              "70x70": "http://stkr.es/p/oar",
//              "140x140": "http://stkr.es/p3w/oar",
//              "280x280": "http://stkr.es/p7s/oar",
//              "70x70ext": "http://stkr.es/p/oar.png"
//          }
//        }

        public StickerImage stickerImageFromJSON(JSONObject object) throws JSONException {
            if (object == null)
                return null;
            object = JSONHelper.getJSONObject(object, JSON_KEY_SIZES);
            if (object == null)
                return null;
            Builder builder = new Builder();
            if (object.has(SMALL_JSON_KEY))
                builder.setSmallURL(JSONHelper.getString(object, SMALL_JSON_KEY));
            if (object.has(MEDIUM_JSON_KEY))
                builder.setMediumURL(JSONHelper.getString(object, MEDIUM_JSON_KEY));
            if (object.has(LARGE_JSON_KEY))
                builder.setLargeURL(JSONHelper.getString(object, LARGE_JSON_KEY));
            return builder.build();
        }

        // TODO ASK IF ALL SIZES ARE ALWAYS AVAILABLE
        public StickerImage stickerImage(String code) {
            return new Builder()
                    .setSmallURL(BASE_URL + SMALL_PREFIX + code)
                    .setMediumURL(BASE_URL + MEDIUM_PREFIX + code)
                    .setLargeURL(BASE_URL + LARGE_PREFIX + code)
                    .build();
        }

        // </editor-fold>

    }

    // </editor-fold>

    // <editor-fold desc="INIT BUILDER CLASS">

    protected static abstract class Init<T extends Init<T>> {

        // <editor-fold desc="VARIABLES">

        private String mCode;
        private String mSmallURL;
        private String mMediumURL;
        private String mLargeURL;
        private Boolean mSmallImageAvailable;
        private Boolean mMediumImageAvailable;
        private Boolean mLargeImageAvailable;

        // </editor-fold>

        // <editor-fold desc="CONSTRUCTORS">

        public Init() {
            mCode = null;
            mSmallURL = null;
            mSmallImageAvailable = false;
            mMediumURL = null;
            mMediumImageAvailable = false;
            mLargeURL = null;
            mLargeImageAvailable = false;
        }

        // </editor-fold>

        // <editor-fold desc="SETTERS">

        public T setSmallURL(String smallURL) {
            if (smallURL == null)
                return self();
            mSmallImageAvailable = true;
            mSmallURL = smallURL;
            return self();
        }

        public T setMediumURL(String mediumURL) {
            if (mediumURL == null)
                return self();
            mMediumImageAvailable = true;
            mMediumURL = mediumURL;
            return self();
        }

        public T setLargeURL(String largeURL) {
            if (largeURL == null)
                return self();
            mLargeImageAvailable = true;
            mLargeURL = largeURL;
            return self();
        }

        // </editor-fold>

        // <editor-fold desc="METHODS">

        protected abstract T self();

        public StickerImage build() {
            return new StickerImage(this);
        }

        // </editor-fold>

    }

    // </editor-fold>

    // <editor-fold desc="BUILDER CLASS">

    public static class Builder extends Init<Builder> {

        // <editor-fold desc="OVERRIDDEN INIT<BUILDER> METHODS">

        @Override
        protected Builder self() {
            return this;
        }

        // </editor-fold>

    }

    // </editor-fold>

}
