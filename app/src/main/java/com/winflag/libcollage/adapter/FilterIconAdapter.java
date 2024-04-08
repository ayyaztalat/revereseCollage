package com.winflag.libcollage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class FilterIconAdapter extends BaseAdapter {
    private Context mContext;
    ImageView mCurSelectedItem;
    private LayoutInflater mInflater;
    private DMWBRes[] resList;
    private List<Holder> holderArray = new ArrayList();
    public int selectedPos = 0;
    HashMap<Integer, View> posViewMap = new HashMap<>();

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    public FilterIconAdapter(Context context, DMWBRes[] dMWBResArr) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resList = dMWBResArr;
    }

    public void setCurrentSelectPos(int i) {
        this.selectedPos = i;
    }

    public void setSelectedPos(int i) {
        View view;
        View view2 = this.posViewMap.get(Integer.valueOf(i));
        if (view2 != null) {
            ImageView imageView = ((Holder) view2.getTag()).selectedImageView;
            if (this.mCurSelectedItem == null && (view = this.posViewMap.get(Integer.valueOf(this.selectedPos))) != null) {
                this.mCurSelectedItem = ((Holder) view.getTag()).selectedImageView;
            }
            ImageView imageView2 = this.mCurSelectedItem;
            if (imageView2 != null && imageView2 != imageView) {
                imageView2.setVisibility(View.INVISIBLE);
            }
            imageView.setVisibility(View.VISIBLE);
            this.mCurSelectedItem = imageView;
            this.selectedPos = i;
        }
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Holder holder;
        if (view == null) {
            view = this.mInflater.inflate(R.layout.view_item, viewGroup, false);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.filter_icon_image);
            holder.textView = (TextView) view.findViewById(R.id.filter_icon_text);
            holder.selectedImageView = (ImageView) view.findViewById(R.id.select_icon_image);
            view.setTag(holder);
            this.holderArray.add(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        GPUFilterRes gPUFilterRes = (GPUFilterRes) this.resList[i];
        if (gPUFilterRes == null || holder == null) {
            return null;
        }
        holder.textView.setText(gPUFilterRes.getShowText());
        if (i == this.selectedPos) {
            holder.selectedImageView.setVisibility(View.VISIBLE);
        } else {
            holder.selectedImageView.setVisibility(View.INVISIBLE);
        }
        holder.dispose();
        gPUFilterRes.getAsyncIconBitmap(new DMWBAsyncPostIconListener() { // from class: com.winflag.libcollage.adapter.FilterIconAdapter.1
            @Override // org.picspool.lib.resource.DMWBAsyncPostIconListener
            public void postIcon(Bitmap bitmap) {
                if (bitmap != null) {
                    holder.imageView.setImageBitmap(bitmap);
                }
            }
        });
        this.posViewMap.put(Integer.valueOf(i), view);
        return view;
    }

    /* loaded from: classes.dex */
    public class Holder {
        public ImageView imageView;
        public ImageView selectedImageView;
        public TextView textView;

        public Holder() {
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

    public void dispose() {
        if (this.holderArray != null) {
            for (int i = 0; i < this.holderArray.size(); i++) {
                this.holderArray.get(i).dispose();
            }
            this.holderArray.clear();
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        DMWBRes[] dMWBResArr = this.resList;
        if (dMWBResArr != null) {
            return dMWBResArr.length;
        }
        return 0;
    }
}
