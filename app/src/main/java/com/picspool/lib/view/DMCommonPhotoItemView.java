package com.picspool.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.picspool.lib.loader.DMImageCallBack;
import com.picspool.lib.loader.DMLocalImageLoaderLruCache;
import com.picspool.lib.service.DMImageMediaItem;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMCommonPhotoItemView extends FrameLayout {
    private static final String TAG = "CommonPhotoItemView";
    private Bitmap bitmap;
    private FrameLayout ly_corner;
    private FrameLayout ly_selected_cover;
    private GridView mGridView;
    private ImageView mImgView;
    private DMImageMediaItem mItem;
    private TextView txt_select_number;

    public void setGridView(GridView gridView) {
        this.mGridView = gridView;
    }

    public GridView getGridView() {
        return this.mGridView;
    }

    public DMCommonPhotoItemView(Context context) {
        super(context);
        this.mGridView = null;
        initView();
    }

    private void initView() {
        ViewGroup viewGroup = (ViewGroup) View.inflate(getContext(), R.layout.dm_view_common_photo_item, this);
        this.mImgView = (ImageView) viewGroup.findViewById(R.id.imgView);
        this.ly_selected_cover = (FrameLayout) viewGroup.findViewById(R.id.ly_selected_cover);
        this.txt_select_number = (TextView) viewGroup.findViewById(R.id.txt_select_number);
        this.ly_corner = (FrameLayout) viewGroup.findViewById(R.id.ly_corner);
    }

    public void setMultiSelectViewVisable(boolean z, int i) {
        if (z) {
            this.ly_selected_cover.setVisibility(View.VISIBLE);
            this.ly_corner.setVisibility(View.VISIBLE);
            this.txt_select_number.setText(String.valueOf(i));
            return;
        }
        this.ly_selected_cover.setVisibility(View.INVISIBLE);
        this.ly_corner.setVisibility(View.INVISIBLE);
        this.txt_select_number.setText(String.valueOf(i));
    }

    public void setSingleSelectViewVisable(boolean z, int i) {
        if (z) {
            this.ly_selected_cover.setVisibility(View.VISIBLE);
            this.ly_corner.setVisibility(View.INVISIBLE);
            return;
        }
        this.ly_selected_cover.setVisibility(View.INVISIBLE);
        this.ly_corner.setVisibility(View.INVISIBLE);
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
        Bitmap loadImage = DMLocalImageLoaderLruCache.getInstance().loadImage(getContext(), dMImageMediaItem, i, i2, new DMImageCallBack() { // from class: com.picspool.lib.view.DMCommonPhotoItemView.1
            @Override // com.picspool.lib.loader.DMImageCallBack
            public void onImageLoader(Bitmap bitmap, String str) {
                if (DMCommonPhotoItemView.this.mGridView != null) {
                    GridView gridView = DMCommonPhotoItemView.this.mGridView;
                    ImageView imageView = (ImageView) gridView.findViewWithTag("GridViewImageView" + str);
                    if (bitmap != null && !bitmap.isRecycled() && imageView != null) {
                        imageView.setImageBitmap(bitmap);
                        return;
                    } else if (bitmap == null || bitmap.isRecycled()) {
                        return;
                    } else {
                        bitmap.recycle();
                        return;
                    }
                }
                DMCommonPhotoItemView.this.mImgView.setImageBitmap(bitmap);
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
