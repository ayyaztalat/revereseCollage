package com.picspool.lib.text.edit;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.picspool.lib.color.DMSysColors;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

import com.picspool.lib.text.draw.DMTextDrawer;
import com.picspool.lib.text.util.DMSelectorImageView;
import com.picspool.lib.widget.colorgallery.DMColorGalleryView;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMBasicShadowView extends FrameLayout {
    private LinearLayout bottomShadowButton;
    private DMSelectorImageView bottomShadowImage;
    private LinearLayout centreAlignButton;
    private DMSelectorImageView centreAlignImage;
    private DMColorGalleryView colorGalleryView;
    private Context context;
    private DMTextFixedView fixedView;
    protected boolean iniFlag;
    private LinearLayout leftAlignButton;
    private DMSelectorImageView leftAlignImage;
    private LinearLayout leftBottomShadowButton;
    private DMSelectorImageView leftBottomShadowImage;
    private LinearLayout leftTopShadowButton;
    private DMSelectorImageView leftTopShadowImage;
    private LinearLayout rightAlignButton;
    private DMSelectorImageView rightAlignImage;
    private DMSelectorImageView rightBottomImage;
    private LinearLayout rightBottomShadowButton;
    private LinearLayout rightTopShadowButton;
    private DMSelectorImageView rightTopShadowImage;
    private SeekBar seekBar;
    private LinearLayout strokeButton;
    private DMTextFixedView textFixedView;
    private LinearLayout topShadowButton;
    private DMSelectorImageView topShadowImage;

    public DMBasicShadowView(Context context) {
        super(context);
        iniView(context);
    }

    public DMBasicShadowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView(context);
    }

    public DMBasicShadowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_systext_basic_view_shadow, (ViewGroup) null);
        addView(inflate);
        this.leftAlignButton = (LinearLayout) inflate.findViewById(R.id.shadow_align_left);
        DMSelectorImageView dMSelectorImageView = (DMSelectorImageView) inflate.findViewById(R.id.shadow_align_left_img);
        this.leftAlignImage = dMSelectorImageView;
        dMSelectorImageView.setImgPath("text/text_ui/zuoduiqi.png");
        this.leftAlignImage.setImgPressedPath("text/text_ui/zuoduiqi1.png");
        this.leftAlignImage.loadImage();
        this.centreAlignButton = (LinearLayout) inflate.findViewById(R.id.shadow_align_centre);
        DMSelectorImageView dMSelectorImageView2 = (DMSelectorImageView) inflate.findViewById(R.id.shadow_align_centre_img);
        this.centreAlignImage = dMSelectorImageView2;
        dMSelectorImageView2.setImgPath("text/text_ui/juzhong.png");
        this.centreAlignImage.setImgPressedPath("text/text_ui/juzhong1.png");
        this.centreAlignImage.loadImage();
        this.rightAlignButton = (LinearLayout) inflate.findViewById(R.id.shadow_align_right);
        DMSelectorImageView dMSelectorImageView3 = (DMSelectorImageView) inflate.findViewById(R.id.shadow_align_right_img);
        this.rightAlignImage = dMSelectorImageView3;
        dMSelectorImageView3.setImgPath("text/text_ui/youduiqi.png");
        this.rightAlignImage.setImgPressedPath("text/text_ui/youduiqi1.png");
        this.rightAlignImage.loadImage();
        this.leftTopShadowButton = (LinearLayout) inflate.findViewById(R.id.button_left_top_shadow);
        DMSelectorImageView dMSelectorImageView4 = (DMSelectorImageView) inflate.findViewById(R.id.button_left_top_shadow_img);
        this.leftTopShadowImage = dMSelectorImageView4;
        dMSelectorImageView4.setImgPath("text/text_ui/left_top_shadow.png");
        this.leftTopShadowImage.setImgPressedPath("text/text_ui/left_top_shadow1.png");
        this.leftTopShadowImage.loadImage();
        this.leftBottomShadowButton = (LinearLayout) inflate.findViewById(R.id.button_left_bottom_shadow);
        DMSelectorImageView dMSelectorImageView5 = (DMSelectorImageView) inflate.findViewById(R.id.button_left_bottom_shadow_img);
        this.leftBottomShadowImage = dMSelectorImageView5;
        dMSelectorImageView5.setImgPath("text/text_ui/left_bottom_shadow.png");
        this.leftBottomShadowImage.setImgPressedPath("text/text_ui/left_bottom_shadow1.png");
        this.leftBottomShadowImage.loadImage();
        this.topShadowButton = (LinearLayout) inflate.findViewById(R.id.button_top_shadow);
        DMSelectorImageView dMSelectorImageView6 = (DMSelectorImageView) inflate.findViewById(R.id.button_top_shadow_img);
        this.topShadowImage = dMSelectorImageView6;
        dMSelectorImageView6.setImgPath("text/text_ui/top_shadow.png");
        this.topShadowImage.setImgPressedPath("text/text_ui/top_shadow1.png");
        this.topShadowImage.loadImage();
        this.rightTopShadowButton = (LinearLayout) inflate.findViewById(R.id.button_right_top_shadow);
        DMSelectorImageView dMSelectorImageView7 = (DMSelectorImageView) inflate.findViewById(R.id.button_right_top_shadow_img);
        this.rightTopShadowImage = dMSelectorImageView7;
        dMSelectorImageView7.setImgPath("text/text_ui/right_top_shadow.png");
        this.rightTopShadowImage.setImgPressedPath("text/text_ui/right_top_shadow1.png");
        this.rightTopShadowImage.loadImage();
        this.rightBottomShadowButton = (LinearLayout) inflate.findViewById(R.id.button_right_bottom_shadow);
        DMSelectorImageView dMSelectorImageView8 = (DMSelectorImageView) inflate.findViewById(R.id.button_right_bottom_shadow_img);
        this.rightBottomImage = dMSelectorImageView8;
        dMSelectorImageView8.setImgPath("text/text_ui/right_bottom_shadow.png");
        this.rightBottomImage.setImgPressedPath("text/text_ui/right_bottom_shadow1.png");
        this.rightBottomImage.loadImage();
        this.bottomShadowButton = (LinearLayout) inflate.findViewById(R.id.button_bottom_shadow);
        DMSelectorImageView dMSelectorImageView9 = (DMSelectorImageView) inflate.findViewById(R.id.button_bottom_shadow_img);
        this.bottomShadowImage = dMSelectorImageView9;
        dMSelectorImageView9.setImgPath("text/text_ui/bottom_shadow.png");
        this.bottomShadowImage.setImgPressedPath("text/text_ui/bottom_shadow1.png");
        this.bottomShadowImage.loadImage();
        this.strokeButton = (LinearLayout) inflate.findViewById(R.id.button_basic_Stroke);
        DMColorGalleryView dMColorGalleryView = (DMColorGalleryView) inflate.findViewById(R.id.stoke_color_gallery_view);
        this.colorGalleryView = dMColorGalleryView;
        dMColorGalleryView.setFocusable(true);
        int px2dip = DMScreenInfoUtil.px2dip(context, context.getResources().getDimension(R.dimen.basic_color_gallery_h)) / 5;
        this.colorGalleryView.setGalleryItemSize(px2dip, px2dip * 4, 0, true);
        this.colorGalleryView.setPointTo(29);
        this.seekBar = (SeekBar) inflate.findViewById(R.id.seekBar1);
        this.leftAlignButton.setSelected(true);
        this.leftAlignButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMBasicShadowView.this.fixedView.setTextAlign(DMTextDrawer.TEXTALIGN.LEFT);
                DMBasicShadowView.this.leftAlignButton.setSelected(true);
                DMBasicShadowView.this.centreAlignButton.setSelected(false);
                DMBasicShadowView.this.rightAlignButton.setSelected(false);
            }
        });
        this.centreAlignButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMBasicShadowView.this.fixedView.setTextAlign(DMTextDrawer.TEXTALIGN.CENTER);
                DMBasicShadowView.this.leftAlignButton.setSelected(false);
                DMBasicShadowView.this.centreAlignButton.setSelected(true);
                DMBasicShadowView.this.rightAlignButton.setSelected(false);
            }
        });
        this.rightAlignButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMBasicShadowView.this.fixedView.setTextAlign(DMTextDrawer.TEXTALIGN.RIGHT);
                DMBasicShadowView.this.leftAlignButton.setSelected(false);
                DMBasicShadowView.this.centreAlignButton.setSelected(false);
                DMBasicShadowView.this.rightAlignButton.setSelected(true);
            }
        });
        this.topShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMBasicShadowView.this.invalidShadows();
                if (DMBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.TOP) {
                    DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DMBasicShadowView.this.topShadowButton.setSelected(false);
                    return;
                }
                DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.TOP);
                DMBasicShadowView.this.topShadowButton.setSelected(true);
            }
        });
        this.rightTopShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMBasicShadowView.this.invalidShadows();
                if (DMBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.RIGHT_TOP) {
                    DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DMBasicShadowView.this.rightTopShadowButton.setSelected(false);
                    return;
                }
                DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.RIGHT_TOP);
                DMBasicShadowView.this.rightTopShadowButton.setSelected(true);
            }
        });
        this.rightBottomShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMBasicShadowView.this.invalidShadows();
                if (DMBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM) {
                    DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DMBasicShadowView.this.rightBottomShadowButton.setSelected(false);
                    return;
                }
                DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM);
                DMBasicShadowView.this.rightBottomShadowButton.setSelected(true);
            }
        });
        this.leftTopShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMBasicShadowView.this.invalidShadows();
                if (DMBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.LEFT_TOP) {
                    DMBasicShadowView.this.leftTopShadowButton.setSelected(false);
                    DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    return;
                }
                DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.LEFT_TOP);
                DMBasicShadowView.this.leftTopShadowButton.setSelected(true);
            }
        });
        this.leftBottomShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMBasicShadowView.this.invalidShadows();
                if (DMBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.LEFT_BOTTOM) {
                    DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DMBasicShadowView.this.leftBottomShadowButton.setSelected(false);
                    return;
                }
                DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.LEFT_BOTTOM);
                DMBasicShadowView.this.leftBottomShadowButton.setSelected(true);
            }
        });
        this.bottomShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMBasicShadowView.this.invalidShadows();
                if (DMBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.BOTTOM) {
                    DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DMBasicShadowView.this.bottomShadowButton.setSelected(false);
                    return;
                }
                DMBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.BOTTOM);
                DMBasicShadowView.this.bottomShadowButton.setSelected(true);
            }
        });
        this.strokeButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMBasicShadowView.this.fixedView.isShowSideTraces()) {
                    DMBasicShadowView.this.fixedView.setShowSideTraces(false);
                    DMBasicShadowView.this.strokeButton.setSelected(false);
                    DMBasicShadowView.this.colorGalleryView.setFocusable(false);
                } else {
                    DMBasicShadowView.this.fixedView.setShowSideTraces(true);
                    DMBasicShadowView.this.strokeButton.setSelected(true);
                    DMBasicShadowView.this.colorGalleryView.setFocusable(true);
                }
                if (DMBasicShadowView.this.fixedView != null) {
                    DMBasicShadowView.this.fixedView.invalidate();
                }
            }
        });
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.11
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                DMBasicShadowView.this.fixedView.setTextAlpha(255 - i);
                DMBasicShadowView.this.fixedView.invalidate();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidShadows() {
        this.leftTopShadowButton.setSelected(false);
        this.leftBottomShadowButton.setSelected(false);
        this.rightTopShadowButton.setSelected(false);
        this.rightBottomShadowButton.setSelected(false);
        this.topShadowButton.setSelected(false);
        this.bottomShadowButton.setSelected(false);
    }

    public DMTextFixedView getFixedView() {
        return this.fixedView;
    }

    public void setFixedView(DMTextFixedView dMTextFixedView) {
        this.fixedView = dMTextFixedView;
        this.colorGalleryView.setListener(new OnDMColorChangedListener() { // from class: com.picspool.lib.text.edit.DMBasicShadowView.12
            @Override // com.picspool.lib.widget.listener.OnDMColorChangedListener
            public void onColorChanged(int i) {
                int i2 = 0;
                while (true) {
                    if (DMBasicShadowView.this.fixedView.getTextDrawer() == null || !DMBasicShadowView.this.iniFlag || i2 >= DMSysColors.length) {
                        break;
                    } else if (i == DMSysColors.getColor(i2)) {
                        DMBasicShadowView.this.fixedView.setSideTracesColor(i);
                        DMBasicShadowView.this.fixedView.getTextDrawer().setSideTracesColorIndex(i2);
                        DMBasicShadowView.this.fixedView.invalidate();
                        break;
                    } else {
                        i2++;
                    }
                }
                if (DMBasicShadowView.this.iniFlag) {
                    return;
                }
                DMBasicShadowView.this.iniFlag = true;
            }
        });
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    public void iniData() {
        int sideTracesColorIndex;
        invalidShadows();
        this.leftAlignButton.setSelected(false);
        this.centreAlignButton.setSelected(false);
        this.rightAlignButton.setSelected(false);
        int i = C333513.$SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN[this.textFixedView.getTextAlign().ordinal()];
        if (i == 1) {
            this.leftAlignButton.setSelected(true);
        } else if (i == 2) {
            this.centreAlignButton.setSelected(true);
        } else if (i == 3) {
            this.rightAlignButton.setSelected(true);
        }
        this.strokeButton.setSelected(this.textFixedView.getTextDrawer().isShowSideTraces());
        switch (this.textFixedView.getTextDrawer().getPaintShadowLayer()) {
            case LEFT_TOP:
                this.leftTopShadowButton.setSelected(true);
                break;
            case LEFT_BOTTOM:
                this.leftBottomShadowButton.setSelected(true);
                break;
            case BOTTOM:
                this.bottomShadowButton.setSelected(true);
                break;
            case RIGHT_TOP:
                this.rightTopShadowButton.setSelected(true);
                break;
            case RIGHT_BOTTOM:
                this.rightBottomShadowButton.setSelected(true);
                break;
            case TOP:
                this.topShadowButton.setSelected(true);
                break;
        }
        this.seekBar.setProgress(255 - this.fixedView.getTextAlpha());
        DMTextFixedView dMTextFixedView = this.textFixedView;
        if (dMTextFixedView == null || dMTextFixedView.getTextDrawer() == null || (sideTracesColorIndex = this.textFixedView.getTextDrawer().getSideTracesColorIndex()) < 0) {
            return;
        }
        this.colorGalleryView.setPointTo(sideTracesColorIndex);
    }

    /* renamed from: com.picspool.lib.text.edit.DMBasicShadowView$13 */
    /* loaded from: classes3.dex */
    static class C333513 {
        static final int[] $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN;

        private static final int[] $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN;

        static {
            int[] iArr = new int[DMTextDrawer.SHADOWALIGN.values().length];
            $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN = iArr;
            try {
                iArr[DMTextDrawer.SHADOWALIGN.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.LEFT_TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.LEFT_BOTTOM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.RIGHT_TOP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.TOP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[DMTextDrawer.TEXTALIGN.values().length];
            $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN = iArr2;
            try {
                iArr2[DMTextDrawer.TEXTALIGN.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN[DMTextDrawer.TEXTALIGN.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN[DMTextDrawer.TEXTALIGN.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public DMTextFixedView getTextFixedView() {
        return this.textFixedView;
    }

    public void setTextFixedView(DMTextFixedView dMTextFixedView) {
        this.textFixedView = dMTextFixedView;
    }
}
