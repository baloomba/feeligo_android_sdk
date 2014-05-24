package com.baloomba.feeligo;

import android.content.Context;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import com.baloomba.feeligo.helper.FeeligoLog;
import com.baloomba.feeligo.helper.FeeligoSettings;
import com.baloomba.feeligo.keyboard.FeeligoKeyboardListener;
import com.baloomba.feeligo.keyboard.FeeligoKeyboardPageAdapter;
import com.baloomba.feeligo.model.FeeligoSticker;
import com.baloomba.feeligo.model.FeeligoStickerPack;
import com.baloomba.wsvolley.WSStringResponseListener;

import com.viewpagerindicator.UnderlinePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeeligoKeyboard extends Fragment {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoKeyboard.class.getSimpleName();

    private static LayoutInflater sInflater;

    private View mView;
    private ArrayList<FeeligoStickerPack> mStickerPacks;

    // </editor-fold>

    // <editor-fold desc="FRAGMENT OVERRIDDEN METHODS">

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStickerPacks = new ArrayList<FeeligoStickerPack>();
        getRecentStickers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sInflater = inflater;
        mView = sInflater.inflate(R.layout.fragment_keyboard, container, false);
        if (mView != null) {
            if (FeeligoSettings.getStoreAvailable()) {
                mView.findViewById(R.id.frame_sticker_keyboard_store).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.frame_sticker_keyboard_store)
                        .setBackgroundColor(FeeligoSettings.getActiveColor());
            } else
                mView.findViewById(R.id.frame_sticker_keyboard_store).setVisibility(View.GONE);
            mView.findViewById(R.id.feeligo_keyboard_search_layout).setVisibility(View.GONE);
            if (FeeligoSettings.getSearchAvailable()) {
                mView.findViewById(R.id.feeligo_keyboard_search).setVisibility(View.VISIBLE);
            } else
                mView.findViewById(R.id.feeligo_keyboard_search).setVisibility(View.GONE);
        }
        return mView;
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public void show() {
        mView.findViewById(R.id.fragment_keyboard_main_layout).setVisibility(View.VISIBLE);
    }

    public void hide() {
        mView.findViewById(R.id.fragment_keyboard_main_layout).setVisibility(View.GONE);
    }

    public void toggle() {
        View view = mView.findViewById(R.id.fragment_keyboard_main_layout);
        view.setVisibility((view.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
    }

    private void getRecentStickers() {
        if (FeeligoSettings.getRecentAvailable()) {
            Feeligo.getInstance().getRecentStickers(new WSStringResponseListener() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray array = object.getJSONArray("stickers");
                        ArrayList<FeeligoSticker> stickers = new ArrayList<FeeligoSticker>();
                        for (int i = 0; i < array.length(); i++) {
                            stickers.add(FeeligoSticker.Factory.getInstance()
                                    .stickerFromJSON(array.getJSONObject(i)));
                        }
                        mStickerPacks.add(FeeligoStickerPack.Factory.getInstance()
                                .stickerPack((int) '#', stickers));
                        getPopularStickers();
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        getPopularStickers();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    FeeligoLog.log(FeeligoLog.ERROR, TAG, "getRecentStickers.onErrorResponse:" +
                            error.getMessage());
                    getPopularStickers();
                }
            });
        } else
            getPopularStickers();
    }

    private void getPopularStickers() {
        if (FeeligoSettings.getPopularAvailable()) {
            Feeligo.getInstance().getPopularStickers(new WSStringResponseListener() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray array = object.getJSONArray("stickers");
                        ArrayList<FeeligoSticker> stickers = new ArrayList<FeeligoSticker>();
                        for (int i = 0; i < array.length(); i++) {
                            stickers.add(FeeligoSticker.Factory.getInstance()
                                    .stickerFromJSON(array.getJSONObject(i)));
                        }
                        mStickerPacks.add(FeeligoStickerPack.Factory.getInstance()
                                .stickerPack((int) '$', stickers));
                        updateStickerView();
                        getUserStickers();
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        getUserStickers();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    FeeligoLog.log(FeeligoLog.ERROR, TAG, "getRecentStickers.onErrorResponse:" +
                            error.getMessage());
                    getUserStickers();
                }
            });
        } else
            getUserStickers();
    }

    private void getUserStickers() {
        Feeligo.getInstance().getUserStickerPack(new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("user_sticker_packs");
                    for (int i = 0; i < array.length(); i++) {
                        mStickerPacks.add(FeeligoStickerPack.Factory.getInstance()
                                .stickerPackFromJSON(array.getJSONObject(i)));

                        // TODO REMOVE AFTER TEST
                        mStickerPacks.add(FeeligoStickerPack.Factory.getInstance()
                                .stickerPackFromJSON(array.getJSONObject(i)));

                    }
                    updateStickerView();
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    updateStickerView();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                FeeligoLog.log(FeeligoLog.ERROR, TAG, "getUserStickerPack.onErrorResponse:" +
                        error.getMessage());
                updateStickerView();
            }
        });
    }

    private void updateStickerView() {
        LinearLayout stickerPacksLayout = ((LinearLayout)mView
                .findViewById(R.id.frame_sticker_keyboard_pack_layout));
            stickerPacksLayout.removeAllViews();
        for (final FeeligoStickerPack stickerPack : mStickerPacks) {
            View view = sInflater.inflate(R.layout.cell_sticker_pack_icon, stickerPacksLayout,
                    false);
            ((TextView)view.findViewById(R.id.cell_sticker_pack_icon_text_view))
                    .setText(String.valueOf((char) stickerPack.getIconId()));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.findViewById(R.id.feeligo_keyboard_search_layout)
                            .setVisibility(View.GONE);
                    ((ViewPager) mView.findViewById(R.id.fragment_keyboard_pager))
                            .setCurrentItem(mStickerPacks.indexOf(stickerPack));
                }
            });
            stickerPacksLayout.addView(view);
        }
        FeeligoKeyboardPageAdapter adapter = new FeeligoKeyboardPageAdapter(getActivity(),
                getChildFragmentManager());
        adapter.setData(mStickerPacks);
        adapter.setListener(new OnStickerClickListener() {
            @Override
            public void onStickerClick(String stickerCode) {
                if (getActivity() instanceof FeeligoKeyboardListener)
                    ((FeeligoKeyboardListener)getActivity()).onStickerClick(stickerCode);
            }
        });

        ViewPager pager = ((ViewPager)mView.findViewById(R.id.fragment_keyboard_pager));
        pager.setAdapter(adapter);

        UnderlinePageIndicator indicator = ((UnderlinePageIndicator)mView
                .findViewById(R.id.keyboard_indicator));
        indicator.setViewPager(pager);
        indicator.setSelectedColor(FeeligoSettings.getActiveColor());
        indicator.setFades(false);
    }

    // </editor-fold>

    // <editor-fold desc="ON STICKER CLICK LISTENER CLASS">

    public interface OnStickerClickListener {
        public abstract void onStickerClick(String stickerCode);
    }

    // </editor-fold>

}
