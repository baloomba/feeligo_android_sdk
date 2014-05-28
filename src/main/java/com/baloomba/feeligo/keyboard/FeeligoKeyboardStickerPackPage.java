package com.baloomba.feeligo.keyboard;

import android.content.Context;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.baloomba.feeligo.FeeligoKeyboard;
import com.baloomba.feeligo.model.StickerPack;
import com.baloomba.feeligo.R;

public class FeeligoKeyboardStickerPackPage extends Fragment {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoKeyboardStickerPackPage.class.getSimpleName();

    private Context mContext;
    private StickerPack mData;
    private FeeligoKeyboard.OnStickerClickListener mListener;

    // </editor-fold>

    public static FeeligoKeyboardStickerPackPage newInstance(Context context, StickerPack data,
                                                     FeeligoKeyboard
                                                             .OnStickerClickListener listener) {
        FeeligoKeyboardStickerPackPage fragment = new FeeligoKeyboardStickerPackPage();
        fragment.mContext = context;
        fragment.mData = data;
        fragment.mListener = listener;
        return fragment;
    }

    // <editor-fold desc="FRAGMENT OVERRIDDEN METHODS">

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sticker_pack_page, container, false);
        if (view != null) {
            FeeligoKeyboardGridAdapter adapter = new FeeligoKeyboardGridAdapter();
            adapter.init(mContext);
            adapter.setData(mData.getStickers());
            adapter.setListener(mListener);
            ((GridView)view.findViewById(R.id.framgment_sticker_pack_page_grid_view))
                    .setAdapter(adapter);
        }
        return view;
    }

    // </editor-fold>

}
