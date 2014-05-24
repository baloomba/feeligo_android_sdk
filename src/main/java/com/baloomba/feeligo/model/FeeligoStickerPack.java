package com.baloomba.feeligo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.baloomba.feeligo.helper.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeeligoStickerPack implements Parcelable {

    // <editor-fold desc="VARIABLES">

    private Long mId;
    private String mName;
    private String mDescription;
    private String mAuthor;
    private String mIconURL;
    private int mIconId;
    private ArrayList<FeeligoSticker> mFeeligoStickers;
    private String mDate;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    protected FeeligoStickerPack(Init<?> builder) {
        mId = builder.mId;
        mName = builder.mName;
        mDescription = builder.mDescription;
        mAuthor = builder.mAuthor;
        mIconURL = builder.mIconURL;
        mIconId = builder.mIconId;
        mFeeligoStickers = builder.mFeeligoStickers;
        mDate = builder.mDate;
    }

    protected FeeligoStickerPack(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mDescription = in.readString();
        mAuthor = in.readString();
        mIconURL = in.readString();
        mIconId = in.readInt();
        mFeeligoStickers = new ArrayList<FeeligoSticker>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            mFeeligoStickers.add((FeeligoSticker)in.readParcelable(FeeligoSticker.class.getClassLoader()));
        }
        mDate = in.readString();
    }

    // </editor-fold>

    // <editor-fold desc="GETTERS">

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getIconURL() {
        return mIconURL;
    }

    public int getIconId() {
        return mIconId;
    }

    public ArrayList<FeeligoSticker> getStickers() {
        return mFeeligoStickers;
    }

    public String getDate() {
        return mDate;
    }

    // </editor-fold>

    // <editor-fold desc="SETTERS">

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public void update() {

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
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mAuthor);
        dest.writeString(mIconURL);
        dest.writeInt(mIconId);
        dest.writeInt(mFeeligoStickers.size());
        for (FeeligoSticker feeligoSticker : mFeeligoStickers) {
            dest.writeParcelable(feeligoSticker, flags);
        }
        dest.writeString(mDate);
    }

    public static final Creator<FeeligoStickerPack> CREATOR
            = new Creator<FeeligoStickerPack>() {
        public FeeligoStickerPack createFromParcel(Parcel in) {
            return new FeeligoStickerPack(in);
        }

        public FeeligoStickerPack[] newArray(int size) {
            return new FeeligoStickerPack[size];
        }
    };

    // </editor-fold>

    // <editor-fold desc="FACTORY CLASS">

    public static class Factory {

        // <editor-fold desc="VARIABLES">

        private static Factory sInstance = new Factory();

        // </editor-fold>

        // <editor-fold desc="INSTANCE">

        public static Factory getInstance() {
            return sInstance;
        }

        // </editor-fold>

        // <editor-fold desc="FACTORY">

        public FeeligoStickerPack stickerPack(int iconId, ArrayList<FeeligoSticker> stickers) {
            return new Builder(-1L)
                    .setIconId(iconId)
                    .setStickers(stickers)
                    .build();
        }

        public FeeligoStickerPack stickerPackFromJSON(JSONObject object) throws JSONException {
            if (object == null)
                return null;
            object = object.getJSONObject("sticker_pack");
            if (object == null)
                return null;
            JSONArray array = JSONHelper.getJSONArray(object, "stickers");
            ArrayList<FeeligoSticker> feeligoStickers = null;
            if (array != null) {
                feeligoStickers = new ArrayList<FeeligoSticker>();
                for (int i = 0; i < array.length(); i++) {
                    feeligoStickers.add(FeeligoSticker.Factory.getInstance()
                            .stickerFromJSON(array.getJSONObject(i)));
                }
            }
            return new Builder(JSONHelper.getLong(object, "id"))
                    .setName(JSONHelper.getString(object, "name"))
                    .setAuthor(JSONHelper.getString(object, "author"))
                    .setDescription(JSONHelper.getString(object, "description"))
                    .setIconURL(JSONHelper.getString(object, "icon_url"))
                    .setIconId(JSONHelper.getInt(object, "icon_id"))
                    .setStickers(feeligoStickers)
                    .setDate(JSONHelper.getString(object, "provided_to_all_users_at"))
                    .build();
        }

        // </editor-fold>

    }

    // </editor-fold>

    // <editor-fold desc="INIT BUILDER CLASS ">

    protected static abstract class Init<T extends Init<T>> {

        // <editor-fold desc="VARIABLES">

        private Long mId;
        private String mName;
        private String mDescription;
        private String mAuthor;
        private String mIconURL;
        private int mIconId;
        private ArrayList<FeeligoSticker> mFeeligoStickers;
        private String mDate;

        // </editor-fold>

        // <editor-fold desc="CONSTRUCTORS">

        public Init(Long id) {
            mId = id;
        }

        // </editor-fold>

        // <editor-fold desc="SETTERS">

        public T setName(String name) {
            mName = name;
            return self();
        }

        public T setDescription(String description) {
            mDescription = description;
            return self();
        }

        public T setAuthor(String author) {
            mAuthor = author;
            return self();
        }

        public T setIconURL(String iconURL) {
            mIconURL = iconURL;
            return self();
        }

        public T setIconId(int iconId) {
            mIconId = iconId;
            return self();
        }

        public T setDate(String date) {
            mDate = date;
            return self();
        }

        public T setStickers(ArrayList<FeeligoSticker> feeligoStickers) {
            mFeeligoStickers = feeligoStickers;
            return self();
        }

        // </editor-fold>

        // <editor-fold desc="METHODS">

        protected abstract T self();

        public FeeligoStickerPack build() {
            return new FeeligoStickerPack(this);
        }

        // </editor-fold>

    }

    // </editor-fold>

    // <editor-fold desc="BUILDER CLASS">

    public static class Builder extends Init<Builder> {

        // <editor-fold desc="CONSTRUCTORS">

        public Builder(Long id) {
            super(id);
        }

        // </editor-fold>

        // <editor-fold desc="METHODS">

        @Override
        protected Builder self() {
            return this;
        }

        // </editor-fold>

    }

    // </editor-fold>

}
