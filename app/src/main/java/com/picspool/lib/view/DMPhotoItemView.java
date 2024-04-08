package com.picspool.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import com.picspool.lib.loader.DMImageCallBack;
import com.picspool.lib.loader.DMLocalImageLoaderLruCache;
import com.picspool.lib.service.DMImageMediaItem;
import com.sky.testproject.R;


/* loaded from: classes3.dex */
public class DMPhotoItemView extends FrameLayout {
    private static final String TAG = "PhotoItemView";
    private Bitmap bitmap;
    private CheckBox mCheckBox;
    private GridView mGridView;
    private ImageView mImgView;
    private DMImageMediaItem mItem;

    public void setGridView(GridView gridView) {
        this.mGridView = gridView;
    }

    public DMPhotoItemView(Context context) {
        super(context);
        this.mGridView = null;
        initView();
    }

    private void initView() {
        this.mImgView = (ImageView) findViewById(R.id.imgView);
        CheckBox checkBox = (CheckBox) ((ViewGroup) View.inflate(getContext(), R.layout.dm_view_photo_item, this)).findViewById(R.id.checkBox1);
        this.mCheckBox = checkBox;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.picspool.lib.view.DMPhotoItemView.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            }
        });
    }

    public void setSelectViewVisable(boolean z) {
        if (z) {
            findViewById(R.id.imgSelectView).setVisibility(0);
        } else {
            findViewById(R.id.imgSelectView).setVisibility(4);
        }
    }

    public void clear() {
        this.mImgView.setImageBitmap(null);
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
        this.bitmap = null;
    }

    public void clearImageLoader() {
        DMLocalImageLoaderLruCache.getInstance().clearCache();
    }

    public void shutDwonImageLoader() {
        DMLocalImageLoaderLruCache.getInstance().shutdownThumbLoder();
    }

    public void setDataItem(DMImageMediaItem dMImageMediaItem, int i, int i2) {
        if (dMImageMediaItem == null) {
            return;
        }
        clear();
        this.mItem = dMImageMediaItem;
        Bitmap loadImage = DMLocalImageLoaderLruCache.getInstance().loadImage(getContext(), dMImageMediaItem, i, i2, new DMImageCallBack() { // from class: com.picspool.lib.view.DMPhotoItemView.2
            @Override // com.picspool.lib.loader.DMImageCallBack
            public void onImageLoader(Bitmap bitmap, String str) {
                if (DMPhotoItemView.this.mGridView != null) {
                    GridView gridView = DMPhotoItemView.this.mGridView;
                    ImageView imageView = (ImageView) gridView.findViewWithTag("GridViewImageView" + str);
                    if (bitmap != null && !bitmap.isRecycled() && imageView != null) {
                        imageView.setImageBitmap(bitmap);
                        return;
                    } else if (bitmap != null) {
                        bitmap.isRecycled();
                        return;
                    } else {
                        return;
                    }
                }
                DMPhotoItemView.this.mImgView.setImageBitmap(bitmap);
            }
        });
        if (loadImage == null || loadImage.isRecycled()) {
            return;
        }
        this.mImgView.setImageBitmap(loadImage);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        getMeasuredHeight();
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }
}
