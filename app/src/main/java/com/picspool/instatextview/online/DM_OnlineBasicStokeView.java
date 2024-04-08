package com.picspool.instatextview.online;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.sky.testproject.R;
import com.picspool.instatextview.edit.DM_TextFixedView;
import com.picspool.instatextview.utils.DMSelectorImageView;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.DMTextDrawer;

/* loaded from: classes3.dex */
public class DM_OnlineBasicStokeView extends FrameLayout {
    private Context context;
    private DM_TextFixedView fixedView;
    private SeekBar horizontalSeekBar;
    private LinearLayout ulineSingleButton;
    private DMSelectorImageView ulineSingleImage;
    private LinearLayout unlineDashedButton;
    private DMSelectorImageView unlineDashedImage;
    private LinearLayout unlineDoubleButton;
    private DMSelectorImageView unlineDoubleImage;
    private SeekBar verticalSeekBar;

    public DM_OnlineBasicStokeView(Context context) {
        super(context);
        iniView(context);
    }

    public DM_OnlineBasicStokeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView(context);
    }

    public DM_OnlineBasicStokeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.dm_text_online_basic_view_stoke, (ViewGroup) null);
        addView(inflate);
        this.horizontalSeekBar = (SeekBar) inflate.findViewById(R.id.seekbar_text_horizontal_offset);
        this.verticalSeekBar = (SeekBar) inflate.findViewById(R.id.seekbar_text_vertical_offset);
        this.horizontalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicStokeView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                DM_OnlineBasicStokeView.this.fixedView.setTextSpaceOffset(DMScreenInfoUtil.dip2px(DM_OnlineBasicStokeView.this.getContext(), i));
                DM_OnlineBasicStokeView.this.fixedView.invalidate();
            }
        });
        this.verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicStokeView.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                DM_OnlineBasicStokeView.this.fixedView.setLineSpaceOffset(DMScreenInfoUtil.dip2px(DM_OnlineBasicStokeView.this.getContext(), i));
                DM_OnlineBasicStokeView.this.fixedView.invalidate();
            }
        });
        this.ulineSingleButton = (LinearLayout) inflate.findViewById(R.id.button_underline_single);
        this.unlineDoubleButton = (LinearLayout) inflate.findViewById(R.id.button_underline_double);
        this.unlineDashedButton = (LinearLayout) inflate.findViewById(R.id.button_underline_dashed);
        this.ulineSingleButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicStokeView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DM_OnlineBasicStokeView.this.fixedView.getTextUnderlinesStyle() == DMTextDrawer.UNDERLINES_STYLE.SINGLE) {
                    DM_OnlineBasicStokeView.this.invalidButtons();
                    DM_OnlineBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.NONE);
                    DM_OnlineBasicStokeView.this.ulineSingleButton.setSelected(false);
                    return;
                }
                DM_OnlineBasicStokeView.this.invalidButtons();
                DM_OnlineBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.SINGLE);
                DM_OnlineBasicStokeView.this.ulineSingleButton.setSelected(true);
            }
        });
        this.unlineDoubleButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicStokeView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DM_OnlineBasicStokeView.this.fixedView.getTextUnderlinesStyle() == DMTextDrawer.UNDERLINES_STYLE.DOUBLE) {
                    DM_OnlineBasicStokeView.this.invalidButtons();
                    DM_OnlineBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.NONE);
                    DM_OnlineBasicStokeView.this.unlineDoubleButton.setSelected(false);
                    return;
                }
                DM_OnlineBasicStokeView.this.invalidButtons();
                DM_OnlineBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.DOUBLE);
                DM_OnlineBasicStokeView.this.unlineDoubleButton.setSelected(true);
            }
        });
        this.unlineDashedButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicStokeView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DM_OnlineBasicStokeView.this.fixedView.getTextUnderlinesStyle() == DMTextDrawer.UNDERLINES_STYLE.DASHED) {
                    DM_OnlineBasicStokeView.this.invalidButtons();
                    DM_OnlineBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.NONE);
                    DM_OnlineBasicStokeView.this.unlineDashedButton.setSelected(false);
                    return;
                }
                DM_OnlineBasicStokeView.this.invalidButtons();
                DM_OnlineBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.DASHED);
                DM_OnlineBasicStokeView.this.unlineDashedButton.setSelected(true);
            }
        });
        DMSelectorImageView dMSelectorImageView = (DMSelectorImageView) inflate.findViewById(R.id.imageView2);
        this.ulineSingleImage = dMSelectorImageView;
        dMSelectorImageView.setImgPath("text/text_ui/basic_stoke_underline_1.png");
        this.ulineSingleImage.setImgPressedPath("text/text_ui/basic_stoke_underline_1_1.png");
        this.ulineSingleImage.loadImage();
        DMSelectorImageView dMSelectorImageView2 = (DMSelectorImageView) inflate.findViewById(R.id.imageView3);
        this.unlineDoubleImage = dMSelectorImageView2;
        dMSelectorImageView2.setImgPath("text/text_ui/basic_stoke_underline_2.png");
        this.unlineDoubleImage.setImgPressedPath("text/text_ui/basic_stoke_underline_2_1.png");
        this.unlineDoubleImage.loadImage();
        DMSelectorImageView dMSelectorImageView3 = (DMSelectorImageView) inflate.findViewById(R.id.imageView4);
        this.unlineDashedImage = dMSelectorImageView3;
        dMSelectorImageView3.setImgPath("text/text_ui/basic_stoke_underline_3.png");
        this.unlineDashedImage.setImgPressedPath("text/text_ui/basic_stoke_underline_3_1.png");
        this.unlineDashedImage.loadImage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidButtons() {
        this.ulineSingleButton.setSelected(false);
        this.unlineDoubleButton.setSelected(false);
        this.unlineDashedButton.setSelected(false);
    }

    public DM_TextFixedView getFixedView() {
        return this.fixedView;
    }

    public void setFixedView(DM_TextFixedView dM_TextFixedView) {
        this.fixedView = dM_TextFixedView;
    }

    public void iniData() {
        int lineSpaceOffset = this.fixedView.getLineSpaceOffset();
        int textSpaceOffset = this.fixedView.getTextSpaceOffset();
        this.verticalSeekBar.setProgress(DMScreenInfoUtil.px2dip(getContext(), lineSpaceOffset));
        this.horizontalSeekBar.setProgress(DMScreenInfoUtil.px2dip(getContext(), textSpaceOffset));
        invalidButtons();
        int i = C29566.$SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE[this.fixedView.getTextUnderlinesStyle().ordinal()];
        if (i == 2) {
            this.ulineSingleButton.setSelected(true);
        } else if (i == 3) {
            this.unlineDashedButton.setSelected(true);
        } else if (i != 4) {
        } else {
            this.unlineDoubleButton.setSelected(true);
        }
    }

    /* renamed from: com.picspool.instatextview.online.DM_OnlineBasicStokeView$6 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C29566 {
        static final /* synthetic */ int[] $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE;

        static {
            int[] iArr = new int[DMTextDrawer.UNDERLINES_STYLE.values().length];
            $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE = iArr;
            try {
                iArr[DMTextDrawer.UNDERLINES_STYLE.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE[DMTextDrawer.UNDERLINES_STYLE.SINGLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE[DMTextDrawer.UNDERLINES_STYLE.DASHED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE[DMTextDrawer.UNDERLINES_STYLE.DOUBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
