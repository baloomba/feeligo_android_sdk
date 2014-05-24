package com.baloomba.feeligo.keyboard;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.baloomba.feeligo.FeeligoKeyboard;
import com.baloomba.feeligo.model.FeeligoStickerPack;

import java.util.ArrayList;

public class FeeligoKeyboardPageAdapter extends FragmentPagerAdapter {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoKeyboardPageAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<FeeligoStickerPack> mData;
    private FeeligoKeyboard.OnStickerClickListener mListener;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    public FeeligoKeyboardPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // </editor-fold>

    // <editor-fold desc="SETTERS">

    public void setData(ArrayList<FeeligoStickerPack> data) {
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
        return FeeligoKeyboardStickerPackPage.newInstance(mContext, mData.get(position), mListener);
    }

    // </editor-fold>

}
