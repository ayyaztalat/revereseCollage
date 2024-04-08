package com.picspool.snappic.snap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sky.testproject.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CSBestFrameAdapter extends BaseAdapter {
    private Context context;
    private List<Holder> holderList = new ArrayList();
    private CSBestFrameManager mFrameManager;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public CSBestFrameAdapter(Context context, CSBestFrameManager cSBestFrameManager) {
        this.mFrameManager = null;
        this.context = context;
        this.mFrameManager = cSBestFrameManager;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        CSBestFrameManager cSBestFrameManager = this.mFrameManager;
        if (cSBestFrameManager != null) {
            return cSBestFrameManager.getCount();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        CSBestFrameManager cSBestFrameManager = this.mFrameManager;
        if (cSBestFrameManager != null) {
            return cSBestFrameManager.getRes(i);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = (LinearLayout) ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.text_frame_item, (ViewGroup) null);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.text_frame_iamge);
            view.setTag(holder);
            this.holderList.add(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
        }
        if (this.mFrameManager != null) {
            holder.imageView.setImageBitmap(this.mFrameManager.getRes(i).getIconBitmap());
        }
        return view;
    }

    public void dispose() {
        for (Holder holder : this.holderList) {
            holder.dispose();
        }
        this.holderList.clear();
    }

    /* loaded from: classes.dex */
    private static class Holder {
        public Bitmap bitmap;
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
