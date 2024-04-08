package com.picspool.libfuncview.effect.blend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.picspool.libfuncview.effect.blend.CSBlendImageView;
import com.picspool.libfuncview.effect.blend.CSBlendListBar;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBlendBarView extends FrameLayout {
    private CSBlendListBar blendListBar;
    private int blendview_height;
    private int blendview_width;
    private CSBlendView mBlendView;
    private Context mContext;
    private Bitmap srcbmp;

    public CSBlendBarView(Context context, Bitmap bitmap) {
        super(context);
        this.srcbmp = bitmap;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_blend_bar, (ViewGroup) this, true);
        this.mBlendView = (CSBlendView) findViewById(R.id.blendview);
        initLayout();
        this.mBlendView.getBlendImageView().setBlendBitmaps(DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), "effect/blend/blend_img_5.jpg"));
        this.mBlendView.getBlendImageView().setSrcImageView(this.srcbmp);
        this.mBlendView.setBlendImageViewWAH(this.blendview_width, this.blendview_height);
        this.mBlendView.setFirstMatrix(new Matrix());
        this.mBlendView.getBlendImageView().setCurrentMode(2);
        this.mBlendView.getBlendImageView().setBlendMode(CSBlendImageView.BlendMode.Normal);
        this.mBlendView.getBlendImageView().setVisibility(View.VISIBLE);
        this.mBlendView.getBlendImageView().setPaintAlpha(128);
        this.mBlendView.getLayoutParams().height = this.blendview_height;
        this.mBlendView.getLayoutParams().width = this.blendview_width;
        CSBlendListBar cSBlendListBar = (CSBlendListBar) findViewById(R.id.effectlistview);
        this.blendListBar = cSBlendListBar;
        cSBlendListBar.setOnItemClickListener(new CSBlendListBar.onItemClickListener() { // from class: com.picspool.libfuncview.effect.blend.CSBlendBarView.1
            @Override // com.picspool.libfuncview.effect.blend.CSBlendListBar.onItemClickListener
            public void onItemClicked(DMWBRes dMWBRes) {
                CSBlendBarView.this.mBlendView.getBlendImageView().setBlendBitmaps(DMBitmapDbUtil.getImageFromAssetsFile(CSBlendBarView.this.mContext, ((DMWBImageRes) dMWBRes).getImageFileName()));
            }
        });
    }

    private void initLayout() {
        int screenWidth = DMScreenInfoUtil.screenWidth(this.mContext);
        int screenHeight = DMScreenInfoUtil.screenHeight(this.mContext) - DMScreenInfoUtil.dip2px(this.mContext, 150.0f);
        float f = screenWidth;
        float f2 = screenHeight;
        float width = this.srcbmp.getWidth() / this.srcbmp.getHeight();
        if (width > f / f2) {
            this.blendview_width = screenWidth;
            this.blendview_height = (int) (f / width);
            return;
        }
        this.blendview_height = screenHeight;
        this.blendview_width = (int) (f2 * width);
    }
}
