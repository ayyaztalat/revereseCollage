package com.picspool.lib.resource.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.picspool.lib.resource.adapter.DMResImageAdapter;
import com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMResImageLayout extends LinearLayout {
    private DMResImageAdapter adapter;
    private Context mContext;
    private int mSelLocation;
    private View mSelView;
    private Drawable mSelViewBackImage;
    public OnItemClickListener mitemListener;

    /* loaded from: classes3.dex */
    public interface OnItemClickListener {
        void ItemClick(View view, int i, String str);
    }

    public DMResImageLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSelLocation = -1;
        this.mSelViewBackImage = null;
        this.mSelView = null;
        this.mContext = context;
    }

    public void setSelectImageLocation(int i) {
        this.mSelLocation = i;
    }

    public void setSelectViewBackImage(Drawable drawable) {
        this.mSelViewBackImage = drawable;
    }

    public void clearSelectViewBackImage() {
        View view = this.mSelView;
        if (view != null) {
            ((ImageView) view.findViewById(R.id.item_image)).setImageBitmap((Bitmap) this.adapter.getItem(this.mSelLocation).get(ImageViewTouchBase.LOG_TAG));
            viewSetBackground(this.mSelView, null);
            this.mSelLocation = -1;
            this.mSelView = null;
        }
    }

    public void setAdapter(DMResImageAdapter dMResImageAdapter) {
        this.adapter = dMResImageAdapter;
        removeAllViews();
        if (dMResImageAdapter == null) {
            return;
        }
        for (int i = 0; i < dMResImageAdapter.getCount(); i++) {
            final Map<String, Object> item = dMResImageAdapter.getItem(i);
            View view = dMResImageAdapter.getView(i, null, null);
            if (i == this.mSelLocation) {
                this.mSelView = view;
                ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
                if (item.get("imageSelAssetFile") != null) {
                    imageView.setImageBitmap(getImageFromAssetsFile(this.mContext.getResources(), String.valueOf(item.get("imageSelAssetFile"))));
                }
            }
            view.setTag(Integer.valueOf(i));
            view.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.resource.view.DMResImageLayout.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (DMResImageLayout.this.mitemListener != null) {
                        int i2 = 0;
                        if (view2.getTag() != null) {
                            i2 = Integer.parseInt(String.valueOf(view2.getTag()));
                            ImageView imageView2 = (ImageView) view2.findViewById(R.id.item_image);
                            if (item.get("imageSelAssetFile") != null) {
                                String valueOf = String.valueOf(item.get("imageSelAssetFile"));
                                DMResImageLayout dMResImageLayout = DMResImageLayout.this;
                                imageView2.setImageBitmap(dMResImageLayout.getImageFromAssetsFile(dMResImageLayout.mContext.getResources(), valueOf));
                            }
                            if (DMResImageLayout.this.mSelView != null && DMResImageLayout.this.mSelLocation != -1 && i2 != DMResImageLayout.this.mSelLocation) {
                                ((ImageView) DMResImageLayout.this.mSelView.findViewById(R.id.item_image)).setImageBitmap((Bitmap) DMResImageLayout.this.adapter.getItem(DMResImageLayout.this.mSelLocation).get(ImageViewTouchBase.LOG_TAG));
                                DMResImageLayout dMResImageLayout2 = DMResImageLayout.this;
                                dMResImageLayout2.viewSetBackground(dMResImageLayout2.mSelView, null);
                            }
                            if (DMResImageLayout.this.mSelViewBackImage != null) {
                                DMResImageLayout dMResImageLayout3 = DMResImageLayout.this;
                                dMResImageLayout3.viewSetBackground(view2, dMResImageLayout3.mSelViewBackImage);
                            }
                            DMResImageLayout.this.mSelView = view2;
                            DMResImageLayout.this.mSelLocation = i2;
                        }
                        if (item.get("id") != null) {
                            i2 = ((Integer) item.get("id")).intValue();
                        }
                        DMResImageLayout.this.mitemListener.ItemClick(view2, i2, item.get("text") != null ? item.get("text").toString() : "");
                    }
                }
            });
            addView(view, new LayoutParams(-2, -2));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mitemListener = onItemClickListener;
    }

    public Bitmap getImageFromAssetsFile(Resources resources, String str) {
        Bitmap bitmap = null;
        try {
            InputStream open = resources.getAssets().open(str);
            bitmap = BitmapFactory.decodeStream(open);
            open.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    protected void viewSetBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(drawable);
        } else {
            viewSetBackgroundJellyBean(view, drawable);
        }
    }

    protected void viewSetBackgroundJellyBean(View view, Drawable drawable) {
        view.setBackground(drawable);
    }
}
