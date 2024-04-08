package com.picspool.lib.text.edit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.draw.DMTextDrawer;
import com.picspool.lib.text.util.DMSelectorImageView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMBasicStokeView extends FrameLayout {
    private Context context;
    private DMTextFixedView fixedView;
    private SeekBar horizontalSeekBar;
    private LinearLayout ulineSingleButton;
    private DMSelectorImageView ulineSingleImage;
    private LinearLayout unlineDashedButton;
    private DMSelectorImageView unlineDashedImage;
    private LinearLayout unlineDoubleButton;
    private DMSelectorImageView unlineDoubleImage;
    private SeekBar verticalSeekBar;

    public DMBasicStokeView(Context context) {
        super(context);
        iniView(context);
    }

    public DMBasicStokeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView(context);
    }

    public DMBasicStokeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_systext_basic_view_stoke, (ViewGroup) null);
        addView(inflate);
        this.horizontalSeekBar = (SeekBar) inflate.findViewById(R.id.seekbar_text_horizontal_offset);
        this.verticalSeekBar = (SeekBar) inflate.findViewById(R.id.seekbar_text_vertical_offset);
        this.horizontalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.lib.text.edit.DMBasicStokeView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                DMBasicStokeView.this.fixedView.setTextSpaceOffset(DMScreenInfoUtil.dip2px(DMBasicStokeView.this.getContext(), i));
                DMBasicStokeView.this.fixedView.invalidate();
            }
        });
        this.verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.lib.text.edit.DMBasicStokeView.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                DMBasicStokeView.this.fixedView.setLineSpaceOffset(DMScreenInfoUtil.dip2px(DMBasicStokeView.this.getContext(), i));
                DMBasicStokeView.this.fixedView.invalidate();
            }
        });
        this.ulineSingleButton = (LinearLayout) inflate.findViewById(R.id.button_underline_single);
        this.unlineDoubleButton = (LinearLayout) inflate.findViewById(R.id.button_underline_double);
        this.unlineDashedButton = (LinearLayout) inflate.findViewById(R.id.button_underline_dashed);
        this.ulineSingleButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicStokeView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMBasicStokeView.this.fixedView.getTextUnderlinesStyle() == DMTextDrawer.UNDERLINES_STYLE.SINGLE) {
                    DMBasicStokeView.this.invalidButtons();
                    DMBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.NONE);
                    DMBasicStokeView.this.ulineSingleButton.setSelected(false);
                    return;
                }
                DMBasicStokeView.this.invalidButtons();
                DMBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.SINGLE);
                DMBasicStokeView.this.ulineSingleButton.setSelected(true);
            }
        });
        this.unlineDoubleButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicStokeView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMBasicStokeView.this.fixedView.getTextUnderlinesStyle() == DMTextDrawer.UNDERLINES_STYLE.DOUBLE) {
                    DMBasicStokeView.this.invalidButtons();
                    DMBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.NONE);
                    DMBasicStokeView.this.unlineDoubleButton.setSelected(false);
                    return;
                }
                DMBasicStokeView.this.invalidButtons();
                DMBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.DOUBLE);
                DMBasicStokeView.this.unlineDoubleButton.setSelected(true);
            }
        });
        this.unlineDashedButton.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.text.edit.DMBasicStokeView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMBasicStokeView.this.fixedView.getTextUnderlinesStyle() == DMTextDrawer.UNDERLINES_STYLE.DASHED) {
                    DMBasicStokeView.this.invalidButtons();
                    DMBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.NONE);
                    DMBasicStokeView.this.unlineDashedButton.setSelected(false);
                    return;
                }
                DMBasicStokeView.this.invalidButtons();
                DMBasicStokeView.this.fixedView.setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE.DASHED);
                DMBasicStokeView.this.unlineDashedButton.setSelected(true);
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
        ((ImageView) findViewById(R.id.imageView6)).setImageBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "text/text_ui/underline_top_offset.png"));
        ((ImageView) findViewById(R.id.imageView7)).setImageBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "text/text_ui/underline_buttom_offset.png"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidButtons() {
        this.ulineSingleButton.setSelected(false);
        this.unlineDoubleButton.setSelected(false);
        this.unlineDashedButton.setSelected(false);
    }

    public DMTextFixedView getFixedView() {
        return this.fixedView;
    }

    public void setFixedView(DMTextFixedView dMTextFixedView) {
        this.fixedView = dMTextFixedView;
    }

    public void iniData() {
        int lineSpaceOffset = this.fixedView.getLineSpaceOffset();
        int textSpaceOffset = this.fixedView.getTextSpaceOffset();
        this.verticalSeekBar.setProgress(DMScreenInfoUtil.px2dip(getContext(), lineSpaceOffset));
        this.horizontalSeekBar.setProgress(DMScreenInfoUtil.px2dip(getContext(), textSpaceOffset));
        invalidButtons();
        int i = C33496.f1997x71e3622f[this.fixedView.getTextUnderlinesStyle().ordinal()];
        if (i == 2) {
            this.ulineSingleButton.setSelected(true);
        } else if (i == 3) {
            this.unlineDashedButton.setSelected(true);
        } else if (i != 4) {
        } else {
            this.unlineDoubleButton.setSelected(true);
        }
    }

    /* renamed from: com.picspool.lib.text.edit.DMBasicStokeView$6 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C33496 {

        /* renamed from: $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$UNDERLINES_STYLE */
        static final /* synthetic */ int[] f1997x71e3622f;

        static {
            int[] iArr = new int[DMTextDrawer.UNDERLINES_STYLE.values().length];
            f1997x71e3622f = iArr;
            try {
                iArr[DMTextDrawer.UNDERLINES_STYLE.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1997x71e3622f[DMTextDrawer.UNDERLINES_STYLE.SINGLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1997x71e3622f[DMTextDrawer.UNDERLINES_STYLE.DASHED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1997x71e3622f[DMTextDrawer.UNDERLINES_STYLE.DOUBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
