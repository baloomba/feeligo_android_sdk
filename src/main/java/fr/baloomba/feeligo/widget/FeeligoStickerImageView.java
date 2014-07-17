package fr.baloomba.feeligo.widget;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;

import fr.baloomba.feeligo.FeeligoLog;
import fr.baloomba.feeligo.helper.FeeligoSettings;
import fr.baloomba.feeligo.R;

import fr.baloomba.feeligo.model.StickerImage;
import fr.baloomba.wsvolley.WSManager;
import fr.baloomba.wsvolley.WSMethod;
import fr.baloomba.wsvolley.WSRequest;
import fr.baloomba.wsvolley.widget.NetworkImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeeligoStickerImageView extends NetworkImageView {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoStickerImageView.class.getSimpleName();

    private static final String BASE_STICKER_URL = "http://stkr.es/";
    private static final String SMALL_PREFIX = "p";
    private static final String MEDIUM_PREFIX = "p3w";
    private static final String LARGE_PREFIX = "p7s";

    private Context mContext;
    protected Boolean mIsActive = true;
    private Boolean mFirstShowing = false;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    public FeeligoStickerImageView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public FeeligoStickerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public FeeligoStickerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    // </editor-fold>

    // <editor-fold desc="MEMBER METHODS">

    public void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
        mRatio = 1f;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.FeeligoStickerImageView, defStyle, 0);
            mIsActive = (a == null ||
                    a.getBoolean(R.styleable.FeeligoStickerImageView_is_active, true));
        }
    }

    // </editor-fold>

    // <editor-fold desc="NETWORK IMAGE VIEW OVERRIDDEN METHODS">

    @Override
    public void setImageUrl(String url) {
        super.setImageUrl(url + "?f-ed=1");
        if (mIsActive && mFirstShowing) {
            FeeligoLog.d(TAG, "setImageUrl isActive");
            WSRequest request = new WSRequest
                    .Builder(WSMethod.GET, url, "feeligo_sticker_image_view")
                    .addHeader("Referer", "http://android-app." + FeeligoSettings.getDomain())
                    .setShouldCache(false)
                    .build();
            WSManager.getInstance().send(request);
        }
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public void setStickerCode(String code) {
        Pattern pattern = Pattern.compile("\\[s:([a-zA-Z0-9\\/\\.\\?\\=]+[a-zA-Z0-9]*)\\]");
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            String url;
            String elem = matcher.group(1);
            if (elem.split("/")[0].equalsIgnoreCase("p")) {
                int density = mContext.getResources().getInteger(R.integer.density);
                String prefix;
                switch (density) {
                    case StickerImage.SMALL:
                        prefix = SMALL_PREFIX;
                        break;
                    case StickerImage.LARGE:
                        prefix = LARGE_PREFIX;
                        break;
                    case StickerImage.MEDIUM:
                    default:
                        prefix = MEDIUM_PREFIX;
                }
                url = BASE_STICKER_URL + prefix + "/" + matcher.group(1).split("/")[1];
            } else {
                url = BASE_STICKER_URL + elem;
            }
            setImageUrl(url);
        }
    }

    // </editor-fold>

}
