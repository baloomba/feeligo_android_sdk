package com.baloomba.feeligo.keyboard;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.baloomba.feeligo.FeeligoKeyboard;

import com.baloomba.feeligo.model.StickerPack;

import java.util.ArrayList;

public class FeeligoKeyboardPageAdapter extends FragmentStatePagerAdapter {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoKeyboardPageAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<StickerPack> mData;
    private FeeligoKeyboard.OnStickerClickListener mListener;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    public FeeligoKeyboardPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // </editor-fold>

    // <editor-fold desc="SETTERS">

    public void setData(ArrayList<StickerPack> data) {
        mData = data;
    }

    public void setListener(FeeligoKeyboard.OnStickerClickListener listener) {
        mListener = listener;
    }

    // </editor-fold>

    // <editor-fold desc="PAGER ADAPTER OVERRIDDEN METHODS">

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return FeeligoKeyboardStickerPackPage.newInstance(mContext, position,mData.get(position),
                mListener);
    }

    @Override
    public int getItemPosition(Object item) {
        FeeligoKeyboardStickerPackPage fragment = (FeeligoKeyboardStickerPackPage)item;
        StickerPack data = fragment.getStickerPack();
        int position = mData.indexOf(data);
        int lastPosition = fragment.getPosition();
        if (position >= 0 && position == lastPosition)
            return position;
        else
            return POSITION_NONE;
    }

    // </editor-fold>

}
