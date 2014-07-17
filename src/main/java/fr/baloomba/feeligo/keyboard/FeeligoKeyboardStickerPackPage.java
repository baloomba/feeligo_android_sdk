package fr.baloomba.feeligo.keyboard;

import android.content.Context;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import fr.baloomba.feeligo.FeeligoKeyboard;
import fr.baloomba.feeligo.model.StickerPack;
import fr.baloomba.feeligo.R;

public class FeeligoKeyboardStickerPackPage extends Fragment {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoKeyboardStickerPackPage.class.getSimpleName();

    private Context mContext;
    private StickerPack mData;
    private FeeligoKeyboard.OnStickerClickListener mListener;
    private int mPosition;
    private FeeligoKeyboardGridAdapter mAdapter;

    // </editor-fold>

    public static FeeligoKeyboardStickerPackPage newInstance(Context context, int position,
                                                             StickerPack data, FeeligoKeyboard
                                                             .OnStickerClickListener listener) {
        FeeligoKeyboardStickerPackPage fragment = new FeeligoKeyboardStickerPackPage();
        fragment.mContext = context;
        fragment.mPosition = position;
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
            mAdapter = new FeeligoKeyboardGridAdapter();
            mAdapter.init(mContext);
            mAdapter.setData(mData.getStickers());
            mAdapter.setListener(mListener);
            ((GridView)view.findViewById(R.id.framgment_sticker_pack_page_grid_view))
                    .setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }

    // </editor-fold>

    // <editor-fold desc="GETTERS">

    public StickerPack getStickerPack() {
        return mData;
    }

    public int getPosition() {
        return mPosition;
    }

    // </editor-fold>

}
