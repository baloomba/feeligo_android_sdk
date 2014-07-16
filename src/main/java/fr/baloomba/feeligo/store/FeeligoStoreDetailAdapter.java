package fr.baloomba.feeligo.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baloomba.feeligo.R;
import fr.baloomba.feeligo.model.Sticker;
import com.baloomba.wsvolley.widget.NetworkImageView;

import java.util.ArrayList;

public class FeeligoStoreDetailAdapter extends BaseAdapter {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoStoreDetailAdapter.class.getSimpleName();

    private static LayoutInflater sInflater;

    private Context mContext;
    private ArrayList<Sticker> mData;
    private ViewHolder mHolder;

    // </editor-fold>

    // <editor-fold desc="BASE ADAPTER OVERRIDDEN METHODS">

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData != null ? mData.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = sInflater.inflate(R.layout.cell_keyboard_grid_view, parent, false);
            if (convertView != null) {
                mHolder = new ViewHolder();
                mHolder.image = (NetworkImageView)convertView
                        .findViewById(R.id.cell_keyboard_grid_view_image_view);
                convertView.setTag(mHolder);
            }
        } else
            mHolder = (ViewHolder)convertView.getTag();
        setContent(mData.get(position));
        return convertView;
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public void init(Context context) {
        sInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    public void setData(ArrayList<Sticker> data) {
        mData = data;
    }

    private void setContent(final Sticker sticker) {
        String url = sticker.getImage().getUrl(mContext);
        mHolder.image.setImageUrl(url);
        mHolder.image.setOnClickListener(null);
    }

    // </editor-fold>

    // <editor-fold desc="VIEW HOLDER CLASS">

    private static class ViewHolder {
        NetworkImageView image;
    }

    // </editor-fold>

}
