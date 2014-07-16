package fr.baloomba.feeligo.model;

import android.os.Parcel;
import android.os.Parcelable;

import fr.baloomba.feeligo.helper.JSONHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class UserStickerPack implements Parcelable {

    // <editor-fold desc="VARIABLES">

    private Long mId;
    private StickerPack mStickerPack;
    private Long mPosition;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    protected UserStickerPack(Init<?> builder) {
        mId = builder.mId;
        mStickerPack = builder.mStickerPack;
        mPosition = builder.mPosition;
    }

    protected UserStickerPack(Parcel in) {
        mId = in.readLong();
        mStickerPack = in.readParcelable(StickerPack.class.getClassLoader());
        mPosition = in.readLong();
    }

    // </editor-fold>

    // <editor-fold desc="GETTERS">

    public Long getId() {
        return mId;
    }

    public StickerPack getStickerPack() {
        return mStickerPack;
    }

    public Long getPosition() {
        return mPosition;
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
        dest.writeParcelable(mStickerPack, flags);
        dest.writeLong(mPosition);
    }

    public static final Creator<UserStickerPack> CREATOR = new Creator<UserStickerPack>() {
        public UserStickerPack createFromParcel(Parcel in) {
            return new UserStickerPack(in);
        }

        public UserStickerPack[] newArray(int size) {
            return new UserStickerPack[size];
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

        public UserStickerPack userStickerPackFromJSON(JSONObject object) throws JSONException {
            if (object == null)
                return null;
            return new Builder(JSONHelper.getLong(object, "id"))
                    .setStickerPack(StickerPack.Factory.getInstance()
                            .stickerPackFromJSON(JSONHelper.getJSONObject(object, "sticker_pack")))
                    .setPosition(JSONHelper.getLong(object, "position"))
                    .build();
        }

        // </editor-fold>

    }

    // </editor-fold>

    // <editor-fold desc="INIT BUILDER CLASS ">

    protected static abstract class Init<T extends Init<T>> {

        // <editor-fold desc="VARIABLES">

        private Long mId;
        private StickerPack mStickerPack;
        private Long mPosition;

        // </editor-fold>

        // <editor-fold desc="CONSTRUCTORS">

        public Init(Long id) {
            mId = id;
        }

        // </editor-fold>

        // <editor-fold desc="SETTERS">

        public T setStickerPack(StickerPack stickerPack) {
            mStickerPack = stickerPack;
            return self();
        }

        public T setPosition(Long position) {
            mPosition = position;
            return self();
        }

        // </editor-fold>

        // <editor-fold desc="METHODS">

        protected abstract T self();

        public UserStickerPack build() {
            return new UserStickerPack(this);
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
