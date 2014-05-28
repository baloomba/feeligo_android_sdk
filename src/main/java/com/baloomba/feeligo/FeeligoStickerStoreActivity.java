package com.baloomba.feeligo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;

import com.baloomba.feeligo.helper.ActionBarHelper;
import com.baloomba.feeligo.helper.FeeligoSettings;
import com.baloomba.feeligo.model.StickerPack;
import com.baloomba.feeligo.network.RequestBuilder;

import com.baloomba.wsvolley.WSStringResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeeligoStickerStoreActivity extends ActionBarActivity {

    // <editor-fold desc="VARIABLES">

    private static final String TAG = FeeligoStickerStoreActivity.class.getSimpleName();

    private Handler mHandler = new Handler();
    private ArrayList<StickerPack> mData;

    // </editor-fold>

    // <editor-fold desc="ACTIVITY OVERRIDDEN METHODS">

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_store);

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
        findViewById(R.id.activity_sticker_store_loading_layout).setVisibility(View.VISIBLE);
        mData = new ArrayList<StickerPack>();
        RequestBuilder.getStickerPack(new WSStringResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("sticker_packs");
                    for (int i = 0; i < array.length(); i++) {
                        mData.add(StickerPack.Factory.getInstance()
                                .stickerPackFromJSON(array.getJSONObject(i)));
                    }
                    setContent();
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    findViewById(R.id.activity_sticker_store_loading_layout).setVisibility(View.GONE);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
                findViewById(R.id.activity_sticker_store_loading_layout).setVisibility(View.GONE);
            }
        });
    }

    private void setContent() {
        findViewById(R.id.activity_sticker_store_loading_layout).setVisibility(View.GONE);

        FeeligoStickerStoreAdapter adapter = new FeeligoStickerStoreAdapter(this);
        adapter.setData(mData);

        ListView listView = ((ListView)findViewById(R.id.activity_sticker_store_list_view));
        listView.setAdapter(adapter);
    }

    // </editor-fold>

}
