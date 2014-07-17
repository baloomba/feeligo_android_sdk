package fr.baloomba.feeligo.store;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import fr.baloomba.feeligo.Feeligo;
import fr.baloomba.feeligo.R;
import fr.baloomba.feeligo.helper.ActionBarHelper;
import fr.baloomba.feeligo.helper.FeeligoSettings;
import fr.baloomba.feeligo.helper.ViewHelper;
import fr.baloomba.feeligo.model.StickerPack;
import fr.baloomba.feeligo.widget.FeeligoStickerImageView;
import fr.baloomba.wsvolley.WSStringResponseListener;

public class FeeligoStickerStoreDetailActivity extends ActionBarActivity {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoStickerStoreDetailActivity.class.getSimpleName();

    private Handler mHandler = new Handler();
    private StickerPack mData;

    // </editor-fold>

    // <editor-fold desc="ACTIVITY OVERRIDDEN METHODS">

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_store_details);

        setActionBar();

        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // </editor-fold>

    // <editor-fold desc="METHODS">

    private void setActionBar() {
        getSupportActionBar().setTitle("Sticker Store");
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarHelper.setColor(this, FeeligoSettings.getActiveColor(),
                new Drawable.Callback() {
                    @Override
                    public void invalidateDrawable(Drawable who) {
                        getSupportActionBar().setBackgroundDrawable(who);
                    }

                    @Override
                    public void scheduleDrawable(Drawable who, Runnable what, long when) {
                        mHandler.postAtTime(what, when);
                    }

                    @Override
                    public void unscheduleDrawable(Drawable who, Runnable what) {
                        mHandler.removeCallbacks(what);
                    }
                }
        );
    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mData = extras.getParcelable("sticker_pack");
            setContent();
        }
    }

    private void setContent() {
        ((FeeligoStickerImageView)
                findViewById(R.id.activity_sticker_store_details_logo_image_view))
                .setImageUrl(mData.getLogo(this));
        ((TextView)findViewById(R.id.activity_sticker_store_details_name_text_view))
                .setText(mData.getName());
        ((TextView)findViewById(R.id.activity_sticker_store_details_author_text_view))
                .setText(mData.getAuthor());
        ((TextView)findViewById(R.id.activity_sticker_store_details_description_text_view))
                .setText(mData.getDescription());
        updateButton();
        FeeligoStoreDetailAdapter adapter = new FeeligoStoreDetailAdapter();
        adapter.init(this);
        adapter.setData(mData.getStickers());
        ((GridView) findViewById(R.id.activity_sticker_store_details_stickers_grid_view))
                .setAdapter(adapter);
    }

    private void updateButton() {
        final View addButton = findViewById(R.id.activity_sticker_store_details_add_button);
        final View removeButton = findViewById(R.id.activity_sticker_store_details_remove_button);
        ViewHelper.setEnabledViewWithAlpha(addButton, true);
        ViewHelper.setEnabledViewWithAlpha(removeButton, true);
        addButton.setVisibility(View.GONE);
        removeButton.setVisibility(View.GONE);
        if (Feeligo.getInstance().isStickerPackPresent(mData.getId())) {
            removeButton.setVisibility(View.VISIBLE);
            removeButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ViewHelper.setEnabledViewWithAlpha(removeButton, false);
                            Feeligo.getInstance().removeStickerPack(mData,
                                    new WSStringResponseListener() {
                                        @Override
                                        public void onResponse(String response) {
                                            updateButton();
                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    }
                            );
                        }
                    }
            );
        } else {
            addButton.setVisibility(View.VISIBLE);
            addButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ViewHelper.setEnabledViewWithAlpha(addButton, false);
                            Feeligo.getInstance().addStickerPack(mData,
                                    new WSStringResponseListener() {
                                        @Override
                                        public void onResponse(String response) {
                                            updateButton();
                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    }
                            );
                        }
                    }
            );
        }
    }

    // </editor-fold>

}
