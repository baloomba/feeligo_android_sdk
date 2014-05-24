package com.baloomba.feeligo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.baloomba.feeligo.helper.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeeligoSticker implements Parcelable {

    // <editor-fold desc="VARIABLES">

    private final static String TAG = FeeligoSticker.class.getSimpleName();

    private Long mId;
    private String mSendableCode;
    private ArrayList<String> mTags;
    private String mImageURL;
    private FeeligoStickerImage mImage;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    public FeeligoSticker(Init<?> builder) {
        mId = builder.mId;
        mSendableCode = builder.mSendableCode;
        mTags = builder.mTags;
        mImageURL = builder.mImageURL;
        mImage = builder.mImage;
    }

    public FeeligoSticker(Parcel in) {
        mId = in.readLong();
        mSendableCode = in.readString();
        mTags = null;
        int size = in.readInt();
        if (size != 0) {
            mTags = new ArrayList<String>();
            for (int i = 0; i < size; i++) {
                mTags.add(in.readString());
            }
        }
        mImageURL = in.readString();
        mImage = in.readParcelable(FeeligoStickerImage.class.getClassLoader());
    }

    // </editor-fold>

    // <editor-fold desc="SETTERS">

    public void setId(Long id) {
        mId = id;
    }

    public void setSendableCode(String code) {
        mSendableCode = code;
    }

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
    }

    public void setImageURL(String url) {
        mImageURL = url;
    }

    public void setImages(FeeligoStickerImage image) {
        mImage = image;
    }

    // </editor-fold>

    // <editor-fold desc="GETTERS">

    public Long getId() {
        return mId;
    }

    public String getSendableCode() {
        return mSendableCode;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public FeeligoStickerImage getImage() {
        return mImage;
    }

    public String getMessage() {
        return mImage.getMessage();
    }

    // </editor-fold>

    // <editor-fold desc="PARCELABLE METHODS IMPLEMENTATION">

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mSendableCode);
        dest.writeInt(mTags != null ? mTags.size() : 0);
        if (mTags != null) {
            for (String tag : mTags) {
                dest.writeString(tag);
            }
        }
        dest.writeString(mImageURL);
        dest.writeParcelable(mImage, flags);
    }

    public static final Creator<FeeligoSticker> CREATOR
            = new Creator<FeeligoSticker>() {
        public FeeligoSticker createFromParcel(Parcel in) {
            return new FeeligoSticker(in);
        }

        public FeeligoSticker[] newArray(int size) {
            return new FeeligoSticker[size];
        }
    };

    // </editor-fold>

    // <editor-fold desc="FACTORY">

    public static class Factory {

        // <editor-fold desc="VARIABLES">

        private static Factory sInstance = new Factory();

        // </editor-fold>

        // <editor-fold desc="INSTANCE">

        public static Factory getInstance() {
            return sInstance;
        }

        // </editor-fold>

        // <editor-fold desc="FACTORY METHODS">

        public FeeligoSticker stickerFromJSON(JSONObject object) throws JSONException {
            if (object == null)
                return null;
            ArrayList<String> tags = null;
            if (object.has("tags")) {
                tags = new ArrayList<String>();
                JSONArray array = object.getJSONArray("tags");
                for (int i = 0; i < array.length(); i++) {
                    tags.add(array.getString(i));
                }
            }
            return new Builder()
                    .setId(object.getLong("id"))
                    .setSendableCode(object.getString("image_url"))
                    .setTags(tags)
                    .setImageURL(object.getString("image_url"))
                    .setImages(FeeligoStickerImage.Factory.getInstance()
                            .stickerImageFromJSON(JSONHelper.getJSONObject(object, "image")))
                    .build();
        }

        public FeeligoSticker sticker(String text) {
            FeeligoSticker feeligoSticker = null;
            Pattern pattern = Pattern.compile("\\[s:([a-zA-Z0-9\\/\\.\\?\\=]+[a-zA-Z0-9]*)\\]");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String code = matcher.group(1).split("/")[1];
                feeligoSticker = new Builder()
                        .setSendableCode(code)
                        .setImages(FeeligoStickerImage.Factory.getInstance().stickerImage(code))
                        .build();
            }
            return feeligoSticker;
        }

        // </editor-fold>
    }

    // </editor-fold>

    // <editor-fold desc="INIT BUILDER CLASS">

    protected static abstract class Init<T extends Init<T>> {

        // <editor-fold desc="VARIABLES">

        private Long mId;
        private String mSendableCode;
        private ArrayList<String> mTags;
        private String mImageURL;
        private FeeligoStickerImage mImage;

        // </editor-fold>

        // <editor-fold desc="CONSTRUCTORS">

        public Init() {
            mId = -1L;
            mSendableCode = "";
            mTags = null;
            mImageURL = "";
            mImage = null;
        }

        // </editor-fold>

        // <editor-fold desc="SETTERS">

        public T setId(Long id) {
            mId = id;
            return self();
        }

        public T setSendableCode(String code) {
            mSendableCode = "[s:" + code + "]";
            return self();
        }

        public T setTags(ArrayList<String> tags) {
            mTags = tags;
            return self();
        }

        public T setImageURL(String url) {
            mImageURL = url;
            setSendableCode(url.split("http://stkr.es/")[1]);
            return self();
        }

        public T setImages(FeeligoStickerImage image) {
            mImage = image;
            return self();
        }

        // </editor-fold>

        // <editor-fold desc="METHODS">

        protected abstract T self();

        public FeeligoSticker build() {
            return new FeeligoSticker(this);
        }

        // </editor-fold>

    }

    // </editor-fold>

    // <editor-fold desc="BUILDER">

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
