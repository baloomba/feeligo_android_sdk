package com.baloomba.feeligo;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baloomba.feeligo.helper.DisplayHelper;
import com.baloomba.feeligo.helper.FeeligoSettings;
import com.baloomba.feeligo.keyboard.FeeligoKeyboardListener;
import com.baloomba.feeligo.keyboard.FeeligoKeyboardPageAdapter;
import com.baloomba.feeligo.model.StickerPack;

import com.baloomba.feeligo.model.UserStickerPack;
import com.baloomba.feeligo.store.FeeligoStickerStoreActivity;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;

public class FeeligoKeyboard extends Fragment {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoKeyboard.class.getSimpleName();

    private static final String PREFERENCES_NAME = "Feeligo";
    private static final String LAST_POSITION = "lastPosition";

    private static final int LEFT = -1;
    private static final int UNKNOWN = 0;
    private static final int RIGHT = 1;

    private static LayoutInflater sInflater;

    private View mView;
    private ArrayList<StickerPack> mStickerPacks = new ArrayList<StickerPack>();
    private FeeligoKeyboardPageAdapter mAdapter;
    private ViewPager mPager;

    private int mParentViewWidth = 0;
    private int mCellWidth = 0;
    private int mLastPosition = -1;

    // </editor-fold>

    // <editor-fold desc="FRAGMENT OVERRIDDEN METHODS">

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAdapter = new FeeligoKeyboardPageAdapter(getActivity(),
                getChildFragmentManager());
        mAdapter.setData(mStickerPacks);
        mAdapter.setListener(new OnStickerClickListener() {
            @Override
            public void onStickerClick(String stickerCode) {
                if (getActivity() instanceof FeeligoKeyboardListener)
                    ((FeeligoKeyboardListener)getActivity()).onStickerClick(stickerCode);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sInflater = inflater;
        mView = sInflater.inflate(R.layout.fragment_keyboard, container, false);
        if (mView != null) {
            mParentViewWidth = DisplayHelper.getWidth(getActivity());
            mCellWidth = getActivity().getResources()
                    .getDimensionPixelSize(R.dimen.feeligo_sticker_keyboard_pack_item_size);
            if (FeeligoSettings.getStoreAvailable()) {
                mView.findViewById(R.id.frame_sticker_keyboard_store).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.frame_sticker_keyboard_store)
                        .setBackgroundColor(FeeligoSettings.getActiveColor());
                mView.findViewById(R.id.frame_sticker_keyboard_store).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToStoreActivity();
                            }
                        }
                );
            } else
                mView.findViewById(R.id.frame_sticker_keyboard_store).setVisibility(View.GONE);
            mView.findViewById(R.id.feeligo_keyboard_search_layout).setVisibility(View.GONE);
            if (FeeligoSettings.getSearchAvailable()) {
                mView.findViewById(R.id.feeligo_keyboard_search).setVisibility(View.VISIBLE);
            } else
                mView.findViewById(R.id.feeligo_keyboard_search).setVisibility(View.GONE);
            mPager = ((ViewPager)mView.findViewById(R.id.fragment_keyboard_pager));
            mPager.setAdapter(mAdapter);

            UnderlinePageIndicator indicator = ((UnderlinePageIndicator)mView
                    .findViewById(R.id.keyboard_indicator));
            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {}

                @Override
                public void onPageSelected(int position) {
                    pageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });
            indicator.setViewPager(mPager);
            indicator.setSelectedColor(FeeligoSettings.getActiveColor());
            indicator.setFades(false);
            getStickers();
        }
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getStickers();
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public void show() {
        mView.findViewById(R.id.fragment_keyboard_main_layout).setVisibility(View.VISIBLE);
        pageSelected(mLastPosition);
    }

    public void hide() {
        mView.findViewById(R.id.fragment_keyboard_main_layout).setVisibility(View.GONE);
    }

    public void toggle() {
        if (mView.findViewById(R.id.fragment_keyboard_main_layout).getVisibility() == View.VISIBLE)
            hide();
        else
            show();
    }

    private void getStickers() {
        mStickerPacks.clear();
        if (Feeligo.getInstance().getRecentAvailable())
            mStickerPacks.add(Feeligo.getInstance().getRecentStickers());
        if (Feeligo.getInstance().getPopularAvailable())
            mStickerPacks.add(Feeligo.getInstance().getPopularStickers());
        ArrayList<UserStickerPack> stickerPacks = Feeligo.getInstance().getUserStickerPack();
        for (UserStickerPack stickerPack : stickerPacks) {
            mStickerPacks.add(stickerPack.getStickerPack());
        }
        updateStickerView();
    }

    private void updateStickerView() {
        LinearLayout stickerPacksLayout = ((LinearLayout)mView
                .findViewById(R.id.frame_sticker_keyboard_pack_layout));
        stickerPacksLayout.removeAllViews();
        for (final StickerPack stickerPack : mStickerPacks) {
            View view = sInflater.inflate(R.layout.cell_sticker_pack_icon, stickerPacksLayout,
                    false);
            if (view != null) {
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
        }
        int pos = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getInt(LAST_POSITION, 0);
        if (pos == 0) {
            pos = (Feeligo.getInstance().getRecentAvailable() ? pos + 1 : pos);
            pos = (Feeligo.getInstance().getPopularAvailable() ? pos + 1 : pos);
        } else if (pos >= mStickerPacks.size()) {
            pos = mStickerPacks.size() - 1;
        }
        mAdapter.setData(mStickerPacks);
        mAdapter.notifyDataSetChanged();
        mPager.invalidate();
        mPager.setCurrentItem(pos);
        pageSelected(pos);
    }

    private void goToStoreActivity() {
        Intent intent = new Intent(getActivity(), FeeligoStickerStoreActivity.class);
        getActivity().startActivity(intent);
    }

    private void pageSelected(int position) {
        getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit().putInt(LAST_POSITION, position).commit();
        if (mCellWidth != 0 && mParentViewWidth != 0) {
            int offset = 0;
            offset = FeeligoSettings.getStoreAvailable() ? offset + 1 : offset;
            offset = FeeligoSettings.getSearchAvailable() ? offset + 1 : offset;

            int scrollViewWidth = (mParentViewWidth - offset * mCellWidth);
            int x = (int)Math.ceil((double)scrollViewWidth / mCellWidth) - 2;

            int direction = UNKNOWN;
            if (mLastPosition > position)
                direction = LEFT;
            else if (mLastPosition < position)
                direction = RIGHT;

            int initialScrollX =  mView
                    .findViewById(R.id.frame_sticker_keyboard_horizontal_scroll_view).getScrollX();

            if (direction == LEFT) {
                int scrollX = (position - 1) * mCellWidth;
                if (initialScrollX > scrollX)
                    ((HorizontalScrollView) mView
                            .findViewById(R.id.frame_sticker_keyboard_horizontal_scroll_view))
                            .smoothScrollTo(scrollX, 0);
            } else {
                int scrollX = (position - x) * mCellWidth;
                if (initialScrollX < scrollX) {
                    ((HorizontalScrollView) mView
                            .findViewById(R.id.frame_sticker_keyboard_horizontal_scroll_view))
                            .smoothScrollTo(scrollX, 0);
                }
            }

        }
        mLastPosition = position;
    }

    // </editor-fold>

    // <editor-fold desc="ON STICKER CLICK LISTENER CLASS">

    public interface OnStickerClickListener {
        public abstract void onStickerClick(String stickerCode);
    }

    // </editor-fold>

}
