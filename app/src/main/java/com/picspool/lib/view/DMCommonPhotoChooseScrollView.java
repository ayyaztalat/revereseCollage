package com.picspool.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.picspool.lib.service.DMImageMediaItem;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMCommonPhotoChooseScrollView extends HorizontalScrollView {
    private static final String TAG = "PhotoSelectScrollView";
    private ItemDeleteCallback mCallback;
    private HashMap<View, Bitmap> mHsBitmaps;
    private HashMap<View, Holder> mHsDeleteToHolder;
    private LinearLayout mLayout;

    /* loaded from: classes3.dex */
    public interface ItemDeleteCallback {
        void ItemDelete(DMImageMediaItem dMImageMediaItem);
    }

    public DMCommonPhotoChooseScrollView(Context context) {
        super(context);
        this.mHsBitmaps = new HashMap<>();
        this.mHsDeleteToHolder = new HashMap<>();
        init();
    }

    public DMCommonPhotoChooseScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHsBitmaps = new HashMap<>();
        this.mHsDeleteToHolder = new HashMap<>();
        init();
    }

    public void setCallback(ItemDeleteCallback itemDeleteCallback) {
        this.mCallback = itemDeleteCallback;
    }

    private final void init() {
        this.mLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        layoutParams.gravity = 16;
        this.mLayout.setLayoutParams(layoutParams);
        this.mLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(this.mLayout);
    }

    public void addItem(DMImageMediaItem dMImageMediaItem) {
        try {
            final View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dm_selector_common_item_view, (ViewGroup) null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.item_icon);
            ImageView imageView2 = (ImageView) inflate.findViewById(R.id.delete_icon);
            inflate.setTag(dMImageMediaItem);
            Holder holder = new Holder();
            holder.holderView = inflate;
            holder.item_icon = imageView;
            holder.delete_icon = imageView2;
            this.mHsDeleteToHolder.put(imageView2, holder);
            imageView2.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.view.DMCommonPhotoChooseScrollView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Holder holder2 = (Holder) DMCommonPhotoChooseScrollView.this.mHsDeleteToHolder.get(view);
                    if (holder2 != null) {
                        Bitmap bitmap = (Bitmap) DMCommonPhotoChooseScrollView.this.mHsBitmaps.get(holder2.item_icon);
                        if (bitmap != null) {
                            if (holder2.item_icon != null) {
                                holder2.item_icon.setImageBitmap(null);
                            }
                            if (!bitmap.isRecycled()) {
                                bitmap.recycle();
                            }
                        }
                        DMCommonPhotoChooseScrollView.this.mHsBitmaps.remove(holder2.item_icon);
                        DMCommonPhotoChooseScrollView.this.mLayout.removeView(inflate);
                        if (DMCommonPhotoChooseScrollView.this.mCallback != null) {
                            DMCommonPhotoChooseScrollView.this.mCallback.ItemDelete((DMImageMediaItem) inflate.getTag());
                        }
                        DMCommonPhotoChooseScrollView.this.mHsDeleteToHolder.remove(view);
                    }
                }
            });
            Bitmap thumbnail = dMImageMediaItem.getThumbnail(getContext(), 120);
            if (thumbnail != null) {
                imageView.setImageBitmap(thumbnail);
                this.mHsBitmaps.put(imageView, thumbnail);
            }
            this.mLayout.addView(inflate);
            new Handler().postDelayed(new Runnable() { // from class: com.picspool.lib.view.DMCommonPhotoChooseScrollView.2
                @Override // java.lang.Runnable
                public void run() {
                    DMCommonPhotoChooseScrollView.this.scroolToLast();
                }
            }, 150L);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "Exception");
        }
    }

    public void deleteAll() {
        HashMap<View, Holder> hashMap = this.mHsDeleteToHolder;
        if (hashMap == null || hashMap.size() <= 0) {
            return;
        }
        for (Map.Entry<View, Holder> entry : this.mHsDeleteToHolder.entrySet()) {
            entry.getKey();
            Holder value = entry.getValue();
            if (value != null) {
                Bitmap bitmap = this.mHsBitmaps.get(value.item_icon);
                if (bitmap != null) {
                    if (value.item_icon != null) {
                        value.item_icon.setImageBitmap(null);
                    }
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
                this.mHsBitmaps.remove(value.item_icon);
            }
        }
        this.mHsDeleteToHolder.clear();
        this.mLayout.removeAllViews();
    }

    public int getCount() {
        return this.mLayout.getChildCount();
    }

    public List<Uri> getChoosedUris() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mLayout.getChildCount(); i++) {
            arrayList.add(((DMImageMediaItem) this.mLayout.getChildAt(i).getTag()).getImgUri());
        }
        return arrayList;
    }

    public List<DMImageMediaItem> getChoosedMeidiaItem() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mLayout.getChildCount(); i++) {
            arrayList.add((DMImageMediaItem) this.mLayout.getChildAt(i).getTag());
        }
        return arrayList;
    }

    public void scroolToLast() {
        if (this.mLayout.getChildCount() >= 2) {
            LinearLayout linearLayout = this.mLayout;
            View childAt = linearLayout.getChildAt(linearLayout.getChildCount() - 1);
            if (childAt.getRight() > getWidth()) {
                smoothScrollTo(childAt.getRight(), 0);
            }
        }
    }

    public void dispose() {
        HashMap<View, Bitmap> hashMap = this.mHsBitmaps;
        if (hashMap != null) {
            for (Map.Entry<View, Bitmap> entry : hashMap.entrySet()) {
                ((ImageView) entry.getKey()).setImageBitmap(null);
                Bitmap value = entry.getValue();
                if (value != null && !value.isRecycled()) {
                    value.recycle();
                }
            }
            this.mHsBitmaps.clear();
        }
        HashMap<View, Holder> hashMap2 = this.mHsDeleteToHolder;
        if (hashMap2 != null) {
            hashMap2.clear();
        }
    }

    /* loaded from: classes3.dex */
    class Holder {
        ImageView delete_icon;
        View holderView;
        ImageView item_icon;

        Holder() {
        }
    }
}
