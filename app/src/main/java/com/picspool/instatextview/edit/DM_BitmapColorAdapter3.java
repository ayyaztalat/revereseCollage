package com.picspool.instatextview.edit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.sky.testproject.R;
import com.picspool.instatextview.resource.manager.DMTextTextureManager;

/* loaded from: classes3.dex */
public class DM_BitmapColorAdapter3 extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private Paint paint;
    private Resources resources;
    private int selectionItem = -1;
    private DM_TextFixedView3 textFixedView;
    private DMTextTextureManager textTextureManager;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DM_BitmapColorAdapter3(Context context, DM_TextFixedView3 dM_TextFixedView3) {
        this.context = context;
        this.resources = context.getResources();
        this.textFixedView = dM_TextFixedView3;
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(-1);
        this.paint.setAntiAlias(true);
        this.textTextureManager = DMTextTextureManager.getSingletonInstance(context);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.textTextureManager.getCount();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.textTextureManager.getRes(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        int dimension = (int) this.resources.getDimension(R.dimen.basic_color_item_size);
        if (view == null) {
            ImageView imageView = new ImageView(this.context);
            imageView.setLayoutParams(new AbsListView.LayoutParams(dimension, dimension));
            imageView.setAdjustViewBounds(false);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder = new Holder();
            holder.imageView = imageView;
            imageView.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
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
            Bitmap iconBitmap = this.textTextureManager.getRes(i).getIconBitmap();
            if (this.paint == null) {
                Paint paint = new Paint();
                this.paint = paint;
                paint.setColor(-1);
                this.paint.setAntiAlias(true);
            }
            if (iconBitmap != null) {
                int i4 = dimension3 + i3;
                canvas.drawBitmap(iconBitmap, new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight()), new Rect(i3, i3, i4, i4), this.paint);
            }
        } catch (Exception unused) {
        }
        holder.imageView.setImageBitmap(createBitmap);
        return holder.imageView;
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
            this.textFixedView.setShaderBitmap(this.textTextureManager.getRes(i).getLocalImageBitmap());
        } catch (Exception unused) {
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

    /* loaded from: classes3.dex */
    private static class Holder {
        public ImageView imageView;

        private Holder() {
        }

        private void recycleImageView(ImageView imageView) {
            if (imageView != null) {
                Drawable drawable = imageView.getDrawable();
                imageView.setImageBitmap(null);
                if (drawable != null) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bitmapDrawable.setCallback(null);
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    if (bitmap == null || bitmap.isRecycled()) {
                        return;
                    }
                    bitmap.recycle();
                }
            }
        }

        public void dispose() {
            recycleImageView(this.imageView);
        }
    }
}
