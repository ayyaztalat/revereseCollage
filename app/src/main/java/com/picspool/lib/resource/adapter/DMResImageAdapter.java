package com.picspool.lib.resource.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMResImageAdapter extends BaseAdapter {
    private Context context;
    private int height;
    private int width;
    private int textColor = ViewCompat.MEASURED_STATE_MASK;
    int imageWidth = 32;
    int imageHeight = 32;
    private List<Map<String, Object>> list = new ArrayList();

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DMResImageAdapter(Context context) {
        this.width = 68;
        this.height = 28;
        this.context = context;
        this.width = DMScreenInfoUtil.dip2px(context, this.width);
        this.height = DMScreenInfoUtil.dip2px(context, this.height);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
    public Map<String, Object> getItem(int i) {
        return this.list.get(i);
    }

    public void addObject(Map<String, Object> map) {
        this.list.add(map);
        notifyDataSetChanged();
    }

    public void setImageItemSize(int i, int i2) {
        this.imageWidth = i;
        this.imageHeight = i2;
    }

    public void SetTextColor(int i) {
        this.textColor = i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.dm_res_view_image_item, (ViewGroup) null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.item_image);
        Map<String, Object> item = getItem(i);
        imageView.setImageBitmap((Bitmap) item.get(ImageViewTouchBase.LOG_TAG));
        TextView textView = (TextView) inflate.findViewById(R.id.item_text);
        if (item.get("text") != null) {
            if (item.get("textColor") != null) {
                textView.setTextColor(((Integer) item.get("textColor")).intValue());
            }
            String obj = item.get("text").toString();
            textView.setText(obj.substring(obj.indexOf("_") + 1));
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.INVISIBLE);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.topMargin = 0;
            layoutParams.height = DMScreenInfoUtil.dip2px(this.context, this.imageWidth);
            layoutParams.width = DMScreenInfoUtil.dip2px(this.context, this.imageHeight);
            layoutParams.gravity = 17;
        }
        int i2 = this.textColor;
        if (i2 != -16777216) {
            textView.setTextColor(i2);
        }
        return inflate;
    }

    public void dispose() {
        if (this.list != null) {
            for (int i = 0; i < this.list.size(); i++) {
                Map<String, Object> map = this.list.get(i);
                if (map != null && map.get(ImageViewTouchBase.LOG_TAG) != null) {
                    Bitmap bitmap = (Bitmap) map.get(ImageViewTouchBase.LOG_TAG);
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
            }
            this.list.clear();
            this.list = null;
        }
    }
}
