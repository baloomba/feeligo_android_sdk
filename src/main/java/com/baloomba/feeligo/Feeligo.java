package com.baloomba.feeligo;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.baloomba.feeligo.helper.FeeligoLog;
import com.baloomba.feeligo.helper.FeeligoSettings;
import com.baloomba.feeligo.model.Sticker;
import com.baloomba.feeligo.model.StickerPack;
import com.baloomba.feeligo.model.UserStickerPack;
import com.baloomba.feeligo.network.RequestBuilder;
import com.baloomba.wsvolley.WSManager;
import com.baloomba.wsvolley.WSResponseListener;
import com.baloomba.wsvolley.WSStringResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Feeligo {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = Feeligo.class.getSimpleName();
    private final static String REG_EXP = "\\[s:([a-zA-Z0-9\\/\\.\\?\\=]+[a-zA-Z0-9]*)\\]";

    private static Feeligo sInstance;

    private String mUserId;

    private StickerPack mRecent;
    private StickerPack mPopular;
    private ArrayList<UserStickerPack> mUserStickerPacks = new ArrayList<UserStickerPack>();

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

    public void connect(String userId) {
        mUserId = userId;
        updateStickerPack();
        updateFeeligoFont();
    }

    public void updateStickerPack() {
        getRecentStickers(new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("stickers");
                    ArrayList<Sticker> stickers = new ArrayList<Sticker>();
                    for (int i = 0; i < array.length(); i++) {
                        stickers.add(Sticker.Factory.getInstance()
                                .stickerFromJSON(array.getJSONObject(i)));
                    }
                    mRecent = StickerPack.Factory.getInstance()
                            .stickerPack((int) '#', stickers);
                } catch (JSONException e) {
                    Log.e(TAG, "getRecentStickers.onResponse:" + e.getMessage());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                FeeligoLog.log(FeeligoLog.ERROR, TAG, "getRecentStickers.onErrorResponse:" +
                        error.getMessage());
            }
        });
        getPopularStickers(new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e(TAG, "response:" + response);
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("stickers");
                    ArrayList<Sticker> stickers = new ArrayList<Sticker>();
                    for (int i = 0; i < array.length(); i++) {
                        stickers.add(Sticker.Factory.getInstance()
                                .stickerFromJSON(array.getJSONObject(i)));
                    }
                    mPopular = StickerPack.Factory.getInstance()
                            .stickerPack((int) '$', stickers);
                } catch (JSONException e) {
                    Log.e(TAG, "getPopularStickers.onResponse:" + e.getMessage());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                FeeligoLog.log(FeeligoLog.ERROR, TAG, "getPopularStickers.onErrorResponse:" +
                        error.getMessage());
            }
        });
        getUserStickerPack(new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e(TAG, "response:" + response);
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("user_sticker_packs");
                    for (int i = 0; i < array.length(); i++) {
                        mUserStickerPacks.add(UserStickerPack.Factory.getInstance()
                                .userStickerPackFromJSON(array.getJSONObject(i)));
//                        mUserStickerPacks.add(UserStickerPack.Factory.getInstance()
//                                .userStickerPackFromJSON(array.getJSONObject(i)));
//                        mUserStickerPacks.add(UserStickerPack.Factory.getInstance()
//                                .userStickerPackFromJSON(array.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "getUserStickerPack.onResponse:" + e.getMessage());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                FeeligoLog.log(FeeligoLog.ERROR, TAG, "getUserStickerPack.onErrorResponse:" +
                        error.getMessage());
            }
        });
    }

    private void updateFeeligoFont() {

    }

    public ArrayList<UserStickerPack> getUserStickerPack() {
        return mUserStickerPacks;
    }

    public boolean getRecentAvailable() {
        return FeeligoSettings.getRecentAvailable() && mRecent != null;
    }

    public StickerPack getRecentStickers() {
        return mRecent;
    }

    public boolean getPopularAvailable() {
        return FeeligoSettings.getPopularAvailable() && mPopular != null;
    }

    public StickerPack getPopularStickers() {
        return mPopular;
    }

    public boolean isStickerPackPresent(Long id) {
        for (UserStickerPack stickerPack : mUserStickerPacks) {
            if (stickerPack.getStickerPack().getId().equals(id))
                return true;
        }
        return false;
    }

    // </editor-fold>

    // <editor-fold desc="REQUEST METHODS">

    public void getRecentStickers(WSResponseListener callbacks) {
        RequestBuilder.getRecentStickers(mUserId, callbacks);
    }

    public void getUserStickerPack(WSResponseListener callbacks) {
        RequestBuilder.getUserStickerPack(mUserId, callbacks);
    }

    public void getPopularStickers(WSResponseListener callbacks) {
        RequestBuilder.getPopularStickers(callbacks);
    }

    public void setRecentSticker(Long stickerId) {
        RequestBuilder.setRecentSticker(mUserId, stickerId, new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {
            }

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    public void addStickerPack(StickerPack stickerPack, final WSResponseListener listener) {
        RequestBuilder.addUserStickerPack(mUserId, stickerPack.getId(),
                new WSStringResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            mUserStickerPacks.add(0, UserStickerPack.Factory.getInstance()
                                    .userStickerPackFromJSON(object
                                            .getJSONObject("user_sticker_pack")));
                            listener.onResponse("");
                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage());
                            listener.onErrorResponse(new VolleyError(e.getMessage()));
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "addUserStickerPack:onErrorResponse" + error.getMessage());
                        listener.onErrorResponse(error);
                    }
                }
        );
    }

    public void removeStickerPack(StickerPack stickerPack, final WSResponseListener listener) {
        UserStickerPack userStickerPack = null;
        for (UserStickerPack userSP : mUserStickerPacks) {
            if (userSP.getStickerPack().getId().equals(stickerPack.getId())) {
                userStickerPack = userSP;
                break;
            }
        }
        if (userStickerPack != null) {
            final UserStickerPack userSP = userStickerPack;
            RequestBuilder.removeUserStickerPack(mUserId, userSP.getId(),
                    new WSStringResponseListener() {
                        @Override
                        public void onResponse(String response) {
                            mUserStickerPacks.remove(userSP);
                            listener.onResponse("");
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "removeStickerPack:onErrorResponse" + error.getMessage());
                            listener.onErrorResponse(error);
                        }
                    }
            );
        }
    }

    // </editor-fold>

    // <editor-fold desc="STATIC METHODS">

    public static boolean containsSticker(String text) {
        return Pattern.compile(REG_EXP).matcher(text).find();
    }

    // </editor-fold>

}
