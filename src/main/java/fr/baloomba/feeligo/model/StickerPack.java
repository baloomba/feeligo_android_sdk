package fr.baloomba.feeligo.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import fr.baloomba.feeligo.helper.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StickerPack implements Parcelable {

    // <editor-fold desc="VARIABLES">

    private Long mId;
    private String mName;
    private String mDescription;
    private String mAuthor;
    private String mIconURL;
    private int mIconId;
    private StickerImage mLogo;
    private ArrayList<Sticker> mStickers;
    private String mDate;

    private Boolean mIsLoading = false;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    protected StickerPack(Init<?> builder) {
        mId = builder.mId;
        mName = builder.mName;
        mDescription = builder.mDescription;
        mAuthor = builder.mAuthor;
        mIconURL = builder.mIconURL;
        mIconId = builder.mIconId;
        mLogo = builder.mLogo;
        mStickers = builder.mStickers;
        mDate = builder.mDate;
    }

    protected StickerPack(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mDescription = in.readString();
        mAuthor = in.readString();
        mIconURL = in.readString();
        mIconId = in.readInt();
        mLogo = in.readParcelable(StickerImage.class.getClassLoader());
        mStickers = new ArrayList<Sticker>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            mStickers.add((Sticker)in.readParcelable(Sticker.class.getClassLoader()));
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
        return mStickers.get(0).getImageURL();
    }

    public int getIconId() {
        return mIconId;
    }

    public ArrayList<Sticker> getStickers() {
        return mStickers;
    }

    public String getLogo(Context context) {
        return mLogo.getUrl(context);
    }

    public String getDate() {
        return mDate;
    }

    public Boolean getIsLoading() {
        return mIsLoading;
    }

    // </editor-fold>

    // <editor-fold desc="SETTERS">

    public void setIsLoading(Boolean isLoading) {
        mIsLoading = isLoading;
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public void update(StickerPack otherPack) {
        mStickers = otherPack.mStickers;
        mDate = otherPack.mDate;
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
        dest.writeParcelable(mLogo, flags);
        dest.writeInt(mStickers.size());
        for (Sticker sticker : mStickers) {
            dest.writeParcelable(sticker, flags);
        }
        dest.writeString(mDate);
    }

    public static final Creator<StickerPack> CREATOR
            = new Creator<StickerPack>() {
        public StickerPack createFromParcel(Parcel in) {
            return new StickerPack(in);
        }

        public StickerPack[] newArray(int size) {
            return new StickerPack[size];
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

        public StickerPack stickerPack(int iconId, ArrayList<Sticker> stickers) {
            return new Builder(-1L)
                    .setIconId(iconId)
                    .setStickers(stickers)
                    .build();
        }

        public StickerPack stickerPackFromJSON(JSONObject object) throws JSONException {
            if (object == null)
                return null;
            if (object.has("sticker_pack"))
                object = object.getJSONObject("sticker_pack");
            JSONArray array = JSONHelper.getJSONArray(object, "stickers");
            ArrayList<Sticker> stickers = null;
            if (array != null) {
                stickers = new ArrayList<Sticker>();
                for (int i = 0; i < array.length(); i++) {
                    stickers.add(Sticker.Factory.getInstance()
                            .stickerFromJSON(array.getJSONObject(i)));
                }
            }
            return new Builder(JSONHelper.getLong(object, "id"))
                    .setName(JSONHelper.getString(object, "name"))
                    .setAuthor(JSONHelper.getString(object, "author"))
                    .setDescription(JSONHelper.getString(object, "description"))
                    .setIconURL(JSONHelper.getString(object, "icon_url"))
                    .setIconId(JSONHelper.getInt(object, "icon_id"))
                    .setStickers(stickers)
                    .setLogo(StickerImage.Factory.getInstance()
                            .stickerImageFromJSON(JSONHelper.getJSONObject(object, "logo")))
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
        private StickerImage mLogo;
        private ArrayList<Sticker> mStickers;
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

        public T setLogo(StickerImage logo) {
            mLogo = logo;
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

        public T setStickers(ArrayList<Sticker> stickers) {
            mStickers = stickers;
            return self();
        }

        // </editor-fold>

        // <editor-fold desc="METHODS">

        protected abstract T self();

        public StickerPack build() {
            return new StickerPack(this);
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
