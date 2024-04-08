package com.picspool.lib.text.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.sky.testproject.R;
import com.picspool.lib.text.draw.DMImager;

/* loaded from: classes3.dex */
public class DMBackgroundAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private int[] imgs = {R.drawable.dm_none, R.color.text_bg_color_16, R.color.text_bg_color_15, R.color.text_bg_color_14, R.color.text_bg_color_13, R.color.text_bg_color_12, R.color.text_bg_color_11, R.color.text_bg_color_10, R.color.text_bg_color_9, R.color.text_bg_color_8, R.color.text_bg_color_7, R.color.text_bg_color_6, R.color.text_bg_color_5, R.color.text_bg_color_4, R.color.text_bg_color_3, R.color.text_bg_color_2, R.color.text_bg_color_1};
    private DMTextFixedView textFixedView;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DMBackgroundAdapter(Context context, DMTextFixedView dMTextFixedView) {
        this.context = context;
        this.textFixedView = dMTextFixedView;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.imgs.length;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            view = (LinearLayout) ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.dm_systext_text_background_item, (ViewGroup) null);
            imageView = (ImageView) view.findViewById(R.id.text_bg_iamge);
            Holder holder = new Holder();
            holder.imageView = imageView;
            view.setTag(holder);
        } else {
            imageView = ((Holder) view.getTag()).imageView;
        }
        imageView.setImageResource(this.imgs[i]);
        return view;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (i == 0) {
            this.textFixedView.cleanBgImage();
            this.textFixedView.invalidate();
            return;
        }
        this.textFixedView.cleanBgImage();
        Bitmap createBitmap = Bitmap.createBitmap((int) this.context.getResources().getDimension(R.dimen.text_bg_image_w), (int) this.context.getResources().getDimension(R.dimen.text_bg_image_h), Bitmap.Config.RGB_565);
        new Canvas(createBitmap).drawColor(this.context.getResources().getColor(this.imgs[i]));
        this.textFixedView.setBgImage(new DMImager.StretchDrawable(this.textFixedView.getTextDrawer(), new BitmapDrawable(this.context.getResources(), createBitmap), new Rect(-15, -10, 15, 10)), null, null, null, null);
        this.textFixedView.invalidate();
    }

    /* loaded from: classes3.dex */
    private static class Holder {
        public ImageView imageView;

        private Holder() {
        }
    }
}
