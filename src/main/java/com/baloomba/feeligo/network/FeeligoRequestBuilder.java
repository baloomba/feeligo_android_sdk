package com.baloomba.feeligo.network;

import com.baloomba.feeligo.helper.FeeligoSettings;
import com.baloomba.wsvolley.WSManager;
import com.baloomba.wsvolley.WSMethod;
import com.baloomba.wsvolley.WSRequest;
import com.baloomba.wsvolley.WSResponseListener;

public class FeeligoRequestBuilder {

    // <editor-fold desc="STICKERS">

    public static void searchStickers(String keyword, WSResponseListener callbacks) {
        String url = FeeligoURLBuilder.stickerSearch(keyword);
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_search_stickers")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void getRecommendedStickers(String phrase, WSResponseListener callbacks) {
        String url = FeeligoURLBuilder.recommendedStickers(phrase);
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_recommended_stickers")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void getPopularStickers(WSResponseListener callbacks) {
        String url = FeeligoURLBuilder.popularStickers();
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_popular_stickers")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void getRecentStickers(Long id, WSResponseListener callbacks) {
        String url = FeeligoURLBuilder.recentStickers(id);
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_get_recent_stickers")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void setRecentSticker(Long id, Long stickerId, WSResponseListener callbacks) {
        String url = FeeligoURLBuilder.recentStickers(id);
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
        String url = FeeligoURLBuilder.stickerPack();
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_sticker_packs")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    // </editor-fold>

    // <editor-fold desc="USER STICKER PACKS">

    public static void getUserStickerPack(Long id, WSResponseListener callbacks) {
        String url = FeeligoURLBuilder.userStickerPack(id);
        WSRequest request = new WSRequest.Builder(WSMethod.GET, url, "feeligo_user_sticker_packs")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void addUserStickerPack(Long id, Long packId, WSResponseListener callbacks) {
        String url = FeeligoURLBuilder.userStickerPack(id);
        WSRequest request = new WSRequest.Builder(WSMethod.POST, url, "feeligo_user_sticker_packs")
                .addParam("user_sticker_pack[sticker_pack_id]", packId.toString())
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    public static void removeUserStickerPack(Long id, Long packId, WSResponseListener callbacks) {
        String url = FeeligoURLBuilder.userStickerPack(id, packId);
        WSRequest request = new WSRequest.Builder(WSMethod.DELETE, url, "feeligo_user_sticker_packs")
                .setListener(callbacks)
                .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                .setShouldCache(true)
                .build();
        WSManager.getInstance().send(request);
    }

    // </editor-fold>

}
