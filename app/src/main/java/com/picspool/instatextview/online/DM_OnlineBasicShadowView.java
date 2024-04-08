package com.picspool.instatextview.online;

import static com.picspool.lib.text.DMTextDrawer.C33201.$SwitchMap$org$picspool$lib$text$DMTextDrawer$SHADOWALIGN;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.picspool.instatextview.edit.DM_TextFixedView;
import com.picspool.instatextview.utils.DMSelectorImageView;
import com.picspool.lib.color.DMSysColors;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.DMTextDrawer;
import com.picspool.lib.widget.colorgallery.DMColorGalleryView;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DM_OnlineBasicShadowView extends FrameLayout {
    private LinearLayout bottomShadowButton;
    private DMSelectorImageView bottomShadowImage;
    private LinearLayout centreAlignButton;
    private DMSelectorImageView centreAlignImage;
    private DMColorGalleryView colorGalleryView;
    private Context context;
    private DM_TextFixedView fixedView;
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
    private DM_TextFixedView textFixedView;
    private LinearLayout topShadowButton;
    private DMSelectorImageView topShadowImage;

    public DM_OnlineBasicShadowView(Context context) {
        super(context);
        iniView(context);
    }

    public DM_OnlineBasicShadowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView(context);
    }

    public DM_OnlineBasicShadowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_online_basic_view_shadow, (ViewGroup) null);
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
        this.seekBar = (SeekBar) inflate.findViewById(R.id.seekBar1);
        this.leftAlignButton.setSelected(true);
        this.leftAlignButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineBasicShadowView.this.fixedView.setTextAlign(DMTextDrawer.TEXTALIGN.LEFT);
                DM_OnlineBasicShadowView.this.leftAlignButton.setSelected(true);
                DM_OnlineBasicShadowView.this.centreAlignButton.setSelected(false);
                DM_OnlineBasicShadowView.this.rightAlignButton.setSelected(false);
            }
        });
        this.centreAlignButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineBasicShadowView.this.fixedView.setTextAlign(DMTextDrawer.TEXTALIGN.CENTER);
                DM_OnlineBasicShadowView.this.leftAlignButton.setSelected(false);
                DM_OnlineBasicShadowView.this.centreAlignButton.setSelected(true);
                DM_OnlineBasicShadowView.this.rightAlignButton.setSelected(false);
            }
        });
        this.rightAlignButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineBasicShadowView.this.fixedView.setTextAlign(DMTextDrawer.TEXTALIGN.RIGHT);
                DM_OnlineBasicShadowView.this.leftAlignButton.setSelected(false);
                DM_OnlineBasicShadowView.this.centreAlignButton.setSelected(false);
                DM_OnlineBasicShadowView.this.rightAlignButton.setSelected(true);
            }
        });
        this.topShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineBasicShadowView.this.invalidShadows();
                if (DM_OnlineBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.TOP) {
                    DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DM_OnlineBasicShadowView.this.topShadowButton.setSelected(false);
                    return;
                }
                DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.TOP);
                DM_OnlineBasicShadowView.this.topShadowButton.setSelected(true);
            }
        });
        this.rightTopShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineBasicShadowView.this.invalidShadows();
                if (DM_OnlineBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.RIGHT_TOP) {
                    DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DM_OnlineBasicShadowView.this.rightTopShadowButton.setSelected(false);
                    return;
                }
                DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.RIGHT_TOP);
                DM_OnlineBasicShadowView.this.rightTopShadowButton.setSelected(true);
            }
        });
        this.rightBottomShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineBasicShadowView.this.invalidShadows();
                if (DM_OnlineBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM) {
                    DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DM_OnlineBasicShadowView.this.rightBottomShadowButton.setSelected(false);
                    return;
                }
                DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM);
                DM_OnlineBasicShadowView.this.rightBottomShadowButton.setSelected(true);
            }
        });
        this.leftTopShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineBasicShadowView.this.invalidShadows();
                if (DM_OnlineBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.LEFT_TOP) {
                    DM_OnlineBasicShadowView.this.leftTopShadowButton.setSelected(false);
                    DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    return;
                }
                DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.LEFT_TOP);
                DM_OnlineBasicShadowView.this.leftTopShadowButton.setSelected(true);
            }
        });
        this.leftBottomShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineBasicShadowView.this.invalidShadows();
                if (DM_OnlineBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.LEFT_BOTTOM) {
                    DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DM_OnlineBasicShadowView.this.leftBottomShadowButton.setSelected(false);
                    return;
                }
                DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.LEFT_BOTTOM);
                DM_OnlineBasicShadowView.this.leftBottomShadowButton.setSelected(true);
            }
        });
        this.bottomShadowButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineBasicShadowView.this.invalidShadows();
                if (DM_OnlineBasicShadowView.this.fixedView.getPaintShadowLayer() == DMTextDrawer.SHADOWALIGN.BOTTOM) {
                    DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
                    DM_OnlineBasicShadowView.this.bottomShadowButton.setSelected(false);
                    return;
                }
                DM_OnlineBasicShadowView.this.fixedView.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.BOTTOM);
                DM_OnlineBasicShadowView.this.bottomShadowButton.setSelected(true);
            }
        });
        this.strokeButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DM_OnlineBasicShadowView.this.fixedView.isShowSideTraces()) {
                    DM_OnlineBasicShadowView.this.fixedView.setShowSideTraces(false);
                    DM_OnlineBasicShadowView.this.strokeButton.setSelected(false);
                    DM_OnlineBasicShadowView.this.colorGalleryView.setFocusable(false);
                } else {
                    DM_OnlineBasicShadowView.this.fixedView.setShowSideTraces(true);
                    DM_OnlineBasicShadowView.this.strokeButton.setSelected(true);
                    DM_OnlineBasicShadowView.this.colorGalleryView.setFocusable(true);
                }
                if (DM_OnlineBasicShadowView.this.fixedView != null) {
                    DM_OnlineBasicShadowView.this.fixedView.invalidate();
                }
            }
        });
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.11
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                DM_OnlineBasicShadowView.this.fixedView.setTextAlpha(255 - i);
                DM_OnlineBasicShadowView.this.fixedView.invalidate();
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

    public DM_TextFixedView getFixedView() {
        return this.fixedView;
    }

    public void setFixedView(DM_TextFixedView dM_TextFixedView) {
        this.fixedView = dM_TextFixedView;
        this.colorGalleryView.setListener(new OnDMColorChangedListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicShadowView.12
            @Override // com.picspool.lib.widget.listener.OnDMColorChangedListener
            public void onColorChanged(int i) {
                int i2 = 0;
                while (true) {
                    if (DM_OnlineBasicShadowView.this.fixedView.getTextDrawer() == null || !DM_OnlineBasicShadowView.this.iniFlag || i2 >= DMSysColors.length) {
                        break;
                    } else if (i == DMSysColors.getColor(i2)) {
                        DM_OnlineBasicShadowView.this.fixedView.setSideTracesColor(i);
                        DM_OnlineBasicShadowView.this.fixedView.getTextDrawer().setSideTracesColorIndex(i2);
                        DM_OnlineBasicShadowView.this.fixedView.invalidate();
                        break;
                    } else {
                        i2++;
                    }
                }
                if (DM_OnlineBasicShadowView.this.iniFlag) {
                    return;
                }
                DM_OnlineBasicShadowView.this.iniFlag = true;
            }
        });
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Context context = this.context;
        int px2dip = DMScreenInfoUtil.px2dip(context, context.getResources().getDimension(R.dimen.basic_color_gallery_h));
        this.colorGalleryView.setLayoutParams(new LinearLayout.LayoutParams(i, DMScreenInfoUtil.dip2px(this.context, px2dip), 48.0f));
        int i5 = px2dip / 5;
        this.colorGalleryView.setGalleryItemSize(i5, i5 * 4, 0, true);
        if (i3 == 0 && i4 == 0) {
            this.colorGalleryView.setPointTo(29);
        }
    }

    public void iniData() {
        int sideTracesColorIndex;
        invalidShadows();
        this.leftAlignButton.setSelected(false);
        this.centreAlignButton.setSelected(false);
        this.rightAlignButton.setSelected(false);
        int i = C294213.$SwitchMap$org$picspool$lib$text$DMTextDrawer$TEXTALIGN[this.textFixedView.getTextAlign().ordinal()];
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
        DM_TextFixedView dM_TextFixedView = this.textFixedView;
        if (dM_TextFixedView == null || dM_TextFixedView.getTextDrawer() == null || (sideTracesColorIndex = this.textFixedView.getTextDrawer().getSideTracesColorIndex()) < 0) {
            return;
        }
        this.colorGalleryView.setPointTo(sideTracesColorIndex);
    }

    /* renamed from: com.picspool.instatextview.online.DM_OnlineBasicShadowView$13 */
    /* loaded from: classes3.dex */
    static  class C294213 {
        static int[] $SwitchMap$org$picspool$lib$text$DMTextDrawer$TEXTALIGN;

        static {
            int[] iArr = new int[DMTextDrawer.SHADOWALIGN.values().length];
            $SwitchMap$org$picspool$lib$text$DMTextDrawer$SHADOWALIGN = iArr;
            try {
                iArr[DMTextDrawer.SHADOWALIGN.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.LEFT_TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.LEFT_BOTTOM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.RIGHT_TOP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$SHADOWALIGN[DMTextDrawer.SHADOWALIGN.TOP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[DMTextDrawer.TEXTALIGN.values().length];
            $SwitchMap$org$picspool$lib$text$DMTextDrawer$TEXTALIGN = iArr2;
            try {
                iArr2[DMTextDrawer.TEXTALIGN.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$TEXTALIGN[DMTextDrawer.TEXTALIGN.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$TEXTALIGN[DMTextDrawer.TEXTALIGN.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public DM_TextFixedView getTextFixedView() {
        return this.textFixedView;
    }

    public void setTextFixedView(DM_TextFixedView dM_TextFixedView) {
        this.textFixedView = dM_TextFixedView;
    }
}
