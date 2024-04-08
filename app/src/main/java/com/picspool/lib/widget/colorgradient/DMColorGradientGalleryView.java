package com.picspool.lib.widget.colorgradient;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.picspool.lib.color.DMSysColors;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.widget.colorgallery.DMColorGalleryView;
import com.picspool.lib.widget.listener.OnDMColorGradientChangedListener;
import com.picspool.lib.widget.listener.OnDMItemColorChangedListener;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMColorGradientGalleryView extends FrameLayout implements OnDMItemColorChangedListener {
    private DMColorGalleryView mColorGalleryBottom;
    private DMColorGalleryView mColorGalleryTop;
    private int[] mColors;
    private Context mContext;
    private OnDMColorGradientChangedListener mOnColorGradientChangedListener;

    public DMColorGradientGalleryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColors = new int[2];
        this.mContext = context;
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_colorgradientgallery, (ViewGroup) this, true);
        initView();
        this.mColors[0] = DMSysColors.getColor(DMSysColors.length / 2);
        this.mColors[1] = DMSysColors.getColor((DMSysColors.length / 2) - 1);
    }

    private void initView() {
        DMColorGalleryView dMColorGalleryView = (DMColorGalleryView) findViewById(R.id.gallerytop);
        this.mColorGalleryTop = dMColorGalleryView;
        dMColorGalleryView.setListener(this);
        this.mColorGalleryTop.setFocusable(true);
        DMColorGalleryView dMColorGalleryView2 = (DMColorGalleryView) findViewById(R.id.gallerybottom);
        this.mColorGalleryBottom = dMColorGalleryView2;
        dMColorGalleryView2.setListener(this);
        this.mColorGalleryBottom.setFocusable(true);
    }

    public void setListener(OnDMColorGradientChangedListener onDMColorGradientChangedListener) {
        this.mOnColorGradientChangedListener = onDMColorGradientChangedListener;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int px2dip = (DMScreenInfoUtil.px2dip(this.mContext, i2) - 2) / 2;
        int dip2px = DMScreenInfoUtil.dip2px(this.mContext, px2dip);
        this.mColorGalleryTop.setLayoutParams(new LayoutParams(i, dip2px, 48));
        this.mColorGalleryBottom.setLayoutParams(new LayoutParams(i, dip2px, 80));
        int i5 = px2dip / 5;
        int i6 = i5 * 4;
        this.mColorGalleryTop.setGalleryItemSize(i5, i6, 0, true);
        this.mColorGalleryBottom.setGalleryItemSize(i5, i6, 0, false);
        if (i3 == 0 && i4 == 0) {
            this.mColorGalleryTop.setPointTo(0);
            this.mColorGalleryBottom.setPointTo(DMSysColors.length - 1);
        }
    }

    public GradientDrawable getGradientDrawable() {
        return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, this.mColors);
    }

    @Override // com.picspool.lib.widget.listener.OnDMItemColorChangedListener
    public void onColorChanged(int i, View view) {
        if (view == this.mColorGalleryTop) {
            this.mColors[0] = i;
            OnDMColorGradientChangedListener onDMColorGradientChangedListener = this.mOnColorGradientChangedListener;
            if (onDMColorGradientChangedListener != null) {
                onDMColorGradientChangedListener.onColorGradientChanged(getGradientDrawable());
            }
        }
        if (view == this.mColorGalleryBottom) {
            this.mColors[1] = i;
            OnDMColorGradientChangedListener onDMColorGradientChangedListener2 = this.mOnColorGradientChangedListener;
            if (onDMColorGradientChangedListener2 != null) {
                onDMColorGradientChangedListener2.onColorGradientChanged(getGradientDrawable());
            }
        }
    }
}
