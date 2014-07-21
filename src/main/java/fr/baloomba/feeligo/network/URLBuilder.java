package fr.baloomba.feeligo.network;

import fr.baloomba.feeligo.BuildConfig;
import fr.baloomba.feeligo.FeeligoLog;
import fr.baloomba.feeligo.helper.FeeligoSettings;

import java.net.URLEncoder;

public class URLBuilder {

    // <editor-fold desc="VARIABLES">

    private static final String STICKER_BASE = "/stickers";
    private static final String STICKER_SEARCH = "/search";
    private static final String STICKER_RECOMMEND = "/recommend";
    private static final String STICKER_POPULAR = "/popular";

    private static final String STICKER_USER_BASE = "/users";
    private static final String STICKER_RECENT = "/recent";
    private static final String STICKER_USER_STICKER_PACK = "/user_sticker_packs";

    private static final String STICKER_PACK_BASE = "/sticker_packs";

    // </editor-fold>

    // <editor-fold desc="METHODS">

    private static String base() {
        return BuildConfig.FEELIGO_API_BASE_URL
                + "/" + FeeligoSettings.getDomain();
    }

    private static String stickerBase() {
        return base() + STICKER_BASE;
    }

    private static String userBase(String user_id) {
        return base() + STICKER_USER_BASE + "/" + user_id;
    }

    private static String stickerPackBase() {
        return base() + STICKER_PACK_BASE;
    }

    public static String stickerSearch(String searchString) {
        try {
            searchString = URLEncoder.encode(searchString, "UTF-8");
        } catch (Exception e) {
            FeeligoLog.e(e.getMessage());
        }
        searchString = searchString.replaceAll("\\+", "%20");
        return stickerBase() + STICKER_SEARCH + "/" + searchString;
    }

    public static String recommendedStickers(String searchString) {
        try {
            searchString = URLEncoder.encode(searchString, "UTF-8");
        } catch (Exception e) {
            FeeligoLog.e(e.getMessage());
        }
        searchString = searchString.replaceAll("\\+", "%20");
        return stickerBase() + STICKER_RECOMMEND + "/" + searchString;
    }

    public static String popularStickers() {
        return stickerBase() + STICKER_POPULAR;
    }

    public static String recentStickers(String user_id) {
        return userBase(user_id) + STICKER_BASE + STICKER_RECENT;
    }

    public static String userStickerPack(String user_id) {
        return userBase(user_id) + STICKER_USER_STICKER_PACK;
    }

    public static String userStickerPack(String user_id, Long pack_id) {
        return userStickerPack(user_id) + "/" + pack_id;
    }

    public static String stickerPack() {
        return stickerPackBase();
    }

    // </editor-fold>

}
