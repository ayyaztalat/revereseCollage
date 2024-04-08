package com.picspool.lib.text.edit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sky.testproject.R;

import java.io.IOException;

/* loaded from: classes3.dex */
public class DMBitmapColorAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private Paint paint;
    private Resources resources;
    private DMTextFixedView textFixedView;
    private String[] imgs = {"text/text_color/text_color_15.png", "text/text_color/text_color_1.png", "text/text_color/text_color_2.png", "text/text_color/text_color_3.png", "text/text_color/text_color_4.png", "text/text_color/text_color_5.png", "text/text_color/text_color_6.png", "text/text_color/text_color_7.png", "text/text_color/text_color_8.png", "text/text_color/text_color_9.png", "text/text_color/text_color_10.png", "text/text_color/text_color_11.png", "text/text_color/text_color_12.png", "text/text_color/text_color_13.png", "text/text_color/text_color_14.png", "text/text_color/text_color_16.png", "text/text_color/text_color_17.png", "text/text_color/text_color_18.png", "text/text_color/text_color_19.png", "text/text_color/text_color_20.png"};
    private String[] textShaderID = {"text/text_color/text_color_15_1.png", "text/text_color/text_color_1_1.png", "text/text_color/text_color_2_1.png", "text/text_color/text_color_3_1.png", "text/text_color/text_color_4_1.png", "text/text_color/text_color_5_1.png", "text/text_color/text_color_6_1.png", "text/text_color/text_color_7_1.png", "text/text_color/text_color_8_1.png", "text/text_color/text_color_9_1.png", "text/text_color/text_color_10_1.png", "text/text_color/text_color_11_1.png", "text/text_color/text_color_12_1.png", "text/text_color/text_color_13_1.png", "text/text_color/text_color_14_1.png", "text/text_color/text_color_16_1.png", "text/text_color/text_color_17_1.png", "text/text_color/text_color_18_1.png", "text/text_color/text_color_19_1.png", "text/text_color/text_color_20_1.png"};
    private int selectionItem = -1;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DMBitmapColorAdapter(Context context, DMTextFixedView dMTextFixedView) {
        this.context = context;
        this.resources = context.getResources();
        this.textFixedView = dMTextFixedView;
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(-1);
        this.paint.setAntiAlias(true);
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
        int dimension = (int) this.resources.getDimension(R.dimen.basic_color_item_size);
        if (view == null) {
            imageView = new ImageView(this.context);
            imageView.setLayoutParams(new AbsListView.LayoutParams(dimension, dimension));
            imageView.setAdjustViewBounds(false);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) view;
        }
        int dimension2 = (int) this.resources.getDimension(R.dimen.basic_color_item_selection_size);
        int dimension3 = (int) this.resources.getDimension(R.dimen.basic_color_item_image_size);
        Bitmap createBitmap = Bitmap.createBitmap(dimension, dimension, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        if (this.selectionItem == i) {
            this.paint.setColor(this.context.getResources().getColor(R.color.text_color_selection_bg));
            int i2 = (dimension - dimension2) / 2;
            float f = i2;
            float f2 = i2 + dimension2;
            canvas.drawArc(new RectF(f, f, f2, f2), 0.0f, 360.0f, true, this.paint);
        }
        int i3 = (dimension - dimension3) / 2;
        try {
            Bitmap decodeStream = BitmapFactory.decodeStream(this.context.getAssets().open(this.imgs[i]));
            if (this.paint == null) {
                Paint paint = new Paint();
                this.paint = paint;
                paint.setColor(-1);
                this.paint.setAntiAlias(true);
            }
            if (decodeStream != null) {
                int i4 = dimension3 + i3;
                canvas.drawBitmap(decodeStream, new Rect(0, 0, decodeStream.getWidth(), decodeStream.getHeight()), new Rect(i3, i3, i4, i4), this.paint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(createBitmap);
        return imageView;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (i == 0) {
            this.textFixedView.setTextColor(-1);
            this.selectionItem = i;
            notifyDataSetChanged();
            return;
        }
        try {
            this.textFixedView.setShaderBitmap(BitmapFactory.decodeStream(this.context.getAssets().open(this.textShaderID[i])));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.selectionItem = i;
        notifyDataSetChanged();
    }

    public void iniSelectionItem() {
        if (this.selectionItem != -1) {
            this.selectionItem = -1;
            notifyDataSetChanged();
        }
    }
}
