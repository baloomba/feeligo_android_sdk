package com.baloomba.feeligo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StickerKeyboard extends Fragment {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = StickerKeyboard.class.getSimpleName();
    private View mView;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    // </editor-fold>

    // <editor-fold desc="FRAGMENT OVERRIDDEN METHODS">

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_keyboard, container, false);
        if (mView != null) {

        }
        return mView;
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    // </editor-fold>

}
