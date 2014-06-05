package com.baloomba.feeligo.store;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.baloomba.feeligo.Feeligo;
import com.baloomba.feeligo.R;
import com.baloomba.feeligo.helper.ViewHelper;
import com.baloomba.feeligo.model.StickerPack;
import com.baloomba.feeligo.widget.FeeligoStickerImageView;
import com.baloomba.wsvolley.WSStringResponseListener;

import java.util.ArrayList;

public class FeeligoStickerStoreAdapter extends BaseAdapter {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoStickerStoreAdapter.class.getSimpleName();

    private static LayoutInflater sInflater;

    private Context mContext;
    private ArrayList<StickerPack> mData;

    // </editor-fold>

    // <editor-fold desc="CONSTRUCTORS">

    public FeeligoStickerStoreAdapter(Context context) {
        sInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    // </editor-fold>

    // <editor-fold desc="SETTERS">

    public void setData(ArrayList<StickerPack> data) {
        mData = data;
    }

    // </editor-fold>

    // <editor-fold desc="BASE ADAPTER OVERRIDDEN METHODS">

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = sInflater.inflate(R.layout.cell_sticker_store, parent, false);
            holder = new ViewHolder();
            if (convertView != null) {
                holder.imageView = (FeeligoStickerImageView)convertView
                        .findViewById(R.id.cell_sticker_store_sticker_image_view);
                holder.nameTextView = (TextView)convertView
                        .findViewById(R.id.cell_sticker_store_name_text_view);
                holder.authorTextView = (TextView)convertView
                        .findViewById(R.id.cell_sticker_store_author_text_view);
//                holder.priceTextView = (TextView)convertView
//                        .findViewById(R.id.cell_sticker_store_price_text_view);
                holder.layout = (LinearLayout)convertView
                        .findViewById(R.id.cell_sticker_store_layout);
                holder.button = (RelativeLayout)convertView
                        .findViewById(R.id.cell_sticker_store_button_layout);
                holder.buttonImage = (ImageView)convertView
                        .findViewById(R.id.cell_sticker_store_action_button);
                holder.progressBar = (ProgressBar)convertView
                        .findViewById(R.id.cell_sticker_store_action_progress_bar);
                convertView.setTag(holder);
            }
        } else
            holder = (ViewHolder)convertView.getTag();
        setContent(holder, mData.get(position));
        return convertView;
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    private void setContent(final ViewHolder holder, final StickerPack stickerPack) {
        if (stickerPack.getIsLoading()) {
            holder.progressBar.setVisibility(View.VISIBLE);
        } else {
            ViewHelper.setEnabledViewWithAlpha(holder.button, true);
            holder.imageView.setImageUrl(stickerPack.getLogo(mContext));
            holder.nameTextView.setText(stickerPack.getName());
            holder.authorTextView.setText(stickerPack.getAuthor());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!stickerPack.getIsLoading())
                        goToDetailsActivity(stickerPack);
                }
            });
            holder.progressBar.setVisibility(View.GONE);
            holder.buttonImage.setVisibility(View.VISIBLE);
            if (Feeligo.getInstance().isStickerPackPresent(stickerPack.getId())) {
                holder.buttonImage.setImageResource(R.drawable.ic_remove_pack);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stickerPack.setIsLoading(true);
                        holder.buttonImage.setVisibility(View.GONE);
                        holder.progressBar.setVisibility(View.VISIBLE);
                        Feeligo.getInstance().removeStickerPack(stickerPack,
                                new WSStringResponseListener() {
                                    @Override
                                    public void onResponse(String response) {
                                        stickerPack.setIsLoading(false);
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        stickerPack.setIsLoading(false);
                                        notifyDataSetChanged();
                                    }
                                }
                        );
                    }
                });
            } else {
                holder.buttonImage.setImageResource(R.drawable.ic_add_pack);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stickerPack.setIsLoading(true);
                        holder.buttonImage.setVisibility(View.GONE);
                        holder.progressBar.setVisibility(View.VISIBLE);
                        Feeligo.getInstance().addStickerPack(stickerPack,
                                new WSStringResponseListener() {
                                    @Override
                                    public void onResponse(String response) {
                                        stickerPack.setIsLoading(false);
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        stickerPack.setIsLoading(false);
                                        notifyDataSetChanged();
                                    }
                                }
                        );
                    }
                });
            }
        }
    }

    private void goToDetailsActivity(StickerPack stickerPack) {
        Intent intent = new Intent(mContext, FeeligoStickerStoreDetailActivity.class);
        intent.putExtra("sticker_pack", stickerPack);
        mContext.startActivity(intent);
    }

    // </editor-fold>

    // <editor-fold desc="VIEW HOLDER CLASS">

    static class ViewHolder {
        LinearLayout layout;
        FeeligoStickerImageView imageView;
        TextView nameTextView;
        TextView authorTextView;
        ImageView buttonImage;
        RelativeLayout button;
        ProgressBar progressBar;
//        TextView priceTextView;
    }

    // </editor-fold>

}
