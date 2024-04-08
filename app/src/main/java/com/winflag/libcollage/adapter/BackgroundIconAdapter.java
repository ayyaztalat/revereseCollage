package com.winflag.libcollage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.testproject.R;
import com.winflag.libcollage.resource.GradientRes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class BackgroundIconAdapter extends BaseAdapter {
    private Context mContext;
    ImageView mCurSelectedItem;
    private LayoutInflater mInflater;
    private DMWBRes[] resList;
    private List<Holder> holderArray = new ArrayList();
    public int selectedPos = 1;
    HashMap<Integer, View> posViewMap = new HashMap<>();

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    public BackgroundIconAdapter(Context context, DMWBRes[] dMWBResArr) {
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
        Holder holder;
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
        DMWBRes dMWBRes = this.resList[i];
        if (dMWBRes == null || holder == null) {
            return null;
        }
        holder.textView.setText(dMWBRes.getShowText());
        if (i == this.selectedPos) {
            holder.selectedImageView.setVisibility(View.VISIBLE);
        } else {
            holder.selectedImageView.setVisibility(View.INVISIBLE);
        }
        holder.dispose();
        if (dMWBRes instanceof DMWBImageRes) {
            holder.imageView.setImageBitmap(dMWBRes.getIconBitmap());
        } else if (dMWBRes instanceof DMWBColorRes) {
            holder.imageView.setBackgroundColor(((DMWBColorRes) dMWBRes).getColorValue());
        } else if (dMWBRes instanceof GradientRes) {
            GradientDrawable gradientDrawable = ((GradientRes) dMWBRes).getGradientDrawable();
            gradientDrawable.setBounds(0, 0, holder.imageView.getWidth(), holder.imageView.getHeight());
            gradientDrawable.setCornerRadius(0.0f);
            if (Build.VERSION.SDK_INT > 16) {
                holder.imageView.setBackground(gradientDrawable);
            } else {
                holder.imageView.setBackgroundDrawable(gradientDrawable);
            }
        }
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
