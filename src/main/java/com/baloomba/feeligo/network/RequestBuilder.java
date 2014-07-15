package com.baloomba.feeligo.network;

import com.baloomba.feeligo.helper.FeeligoSettings;
import com.baloomba.feeligo.model.UserStickerPack;
import com.baloomba.wsvolley.WSManager;
import com.baloomba.wsvolley.WSMethod;
import com.baloomba.wsvolley.WSRequest;
import com.baloomba.wsvolley.WSResponseListener;

public class RequestBuilder {

    // <editor-fold desc="STICKERS">

    public static void searchStickers(String keyword, WSResponseListener callbacks) {
        String url = URLBuilder.stickerSearch(keyword);
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_search_stickers")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void getRecommendedStickers(String phrase, WSResponseListener callbacks) {
        String url = URLBuilder.recommendedStickers(phrase);
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_recommended_stickers")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void getPopularStickers(WSResponseListener callbacks) {
        String url = URLBuilder.popularStickers();
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_popular_stickers")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void getRecentStickers(String id, WSResponseListener callbacks) {
        String url = URLBuilder.recentStickers(id);
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_get_recent_stickers")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void setRecentSticker(String id, Long stickerId, WSResponseListener callbacks) {
        String url = URLBuilder.recentStickers(id);
        WSRequest request = new WSRequest.Builder(WSMethod.POST, url, "feeligo_set_recent_sticker")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .addParam("sticker[id]", stickerId.toString())
                .build();
        WSManager.getInstance().send(request);
    }

    // </editor-fold>

    // <editor-fold desc="STICKER PACKS">

    public static void getStickerPack(WSResponseListener callbacks) {
        String url = URLBuilder.stickerPack();
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_sticker_packs")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    // </editor-fold>

    // <editor-fold desc="USER STICKER PACKS">

    public static void getUserStickerPack(String id, WSResponseListener callbacks) {
        String url = URLBuilder.userStickerPack(id);
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_user_sticker_packs")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void addUserStickerPack(String id, Long packId, WSResponseListener callbacks) {
        String url = URLBuilder.userStickerPack(id);
        WSRequest request = new WSRequest.Builder(WSMethod.POST, url, "feeligo_user_sticker_packs")
                .addParam("user_sticker_pack[sticker_pack_id]", packId.toString())
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(false)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void removeUserStickerPack(String id, Long packId,  WSResponseListener callbacks) {
        String url = URLBuilder.userStickerPack(id, packId);
        WSRequest request = new WSRequest.Builder(WSMethod.DELETE, url,
                "feeligo_user_sticker_packs")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(false)
                .build();
        WSManager.getInstance().send(request);
    }

    // </editor-fold>

}
