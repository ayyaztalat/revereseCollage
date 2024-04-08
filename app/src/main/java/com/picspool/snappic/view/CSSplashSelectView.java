package com.picspool.snappic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.picspool.snappic.view.CSSplashShapeView;
import com.sky.testproject.R;


/* loaded from: classes.dex */
public class CSSplashSelectView extends FrameLayout {
    private ImageView bgImage;
    private View btnsLayout;
    private View changeBtn;
    private int clickedBgID;
    private int defaultBgID;
    private OnSplashClickListener onSplashClickListener;
    private View styleBtn;
    private StyleBtnMode styleBtnMode;
    private ImageView styleImage;

    /* loaded from: classes.dex */
    public interface OnSplashClickListener {
        void onClick();

        void onClickChange();

        void onClickStyle(StyleBtnMode styleBtnMode);
    }

    /* loaded from: classes.dex */
    public enum StyleBtnMode {
        STYLE_1(R.drawable.img_splash_style_1, CSSplashShapeView.StyleMode.B_W),
        STYLE_2(R.drawable.img_splash_style_2, CSSplashShapeView.StyleMode.MOSAIC),
        STYLE_3(R.drawable.img_splash_style_3, CSSplashShapeView.StyleMode.POLKA_DOT);
        
        public int imgID;
        public CSSplashShapeView.StyleMode styleMode;

        StyleBtnMode(int i, CSSplashShapeView.StyleMode styleMode) {
            this.imgID = i;
            this.styleMode = styleMode;
        }
    }

    public CSSplashSelectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.styleBtnMode = StyleBtnMode.STYLE_1;
        iniView();
    }

    private void iniView() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_splash_select, (ViewGroup) this, true);
        this.bgImage = (ImageView) findViewById(R.id.select_bg_img);
        this.styleImage = (ImageView) findViewById(R.id.select_style_img);
        this.styleBtn = findViewById(R.id.select_style_btn);
        this.changeBtn = findViewById(R.id.select_change_btn);
        this.btnsLayout = findViewById(R.id.btns_layout);
        this.bgImage.setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.view.CSSplashSelectView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSplashSelectView.this.onSplashClickListener != null) {
                    CSSplashSelectView.this.onSplashClickListener.onClick();
                    CSSplashSelectView.this.bgImage.setImageResource(CSSplashSelectView.this.clickedBgID);
                    CSSplashSelectView.this.btnsLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        this.styleImage.setImageResource(this.styleBtnMode.imgID);
        this.styleBtn.setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.view.CSSplashSelectView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int i = C25034.f1695x716affbb[CSSplashSelectView.this.styleBtnMode.ordinal()];
                if (i == 1) {
                    CSSplashSelectView.this.styleBtnMode = StyleBtnMode.STYLE_2;
                } else if (i == 2) {
                    CSSplashSelectView.this.styleBtnMode = StyleBtnMode.STYLE_3;
                } else if (i == 3) {
                    CSSplashSelectView.this.styleBtnMode = StyleBtnMode.STYLE_1;
                }
                if (CSSplashSelectView.this.onSplashClickListener != null) {
                    CSSplashSelectView.this.onSplashClickListener.onClickStyle(CSSplashSelectView.this.styleBtnMode);
                }
                CSSplashSelectView.this.styleImage.setImageResource(CSSplashSelectView.this.styleBtnMode.imgID);
            }
        });
        this.changeBtn.setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.view.CSSplashSelectView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSplashSelectView.this.onSplashClickListener != null) {
                    CSSplashSelectView.this.onSplashClickListener.onClickChange();
                }
            }
        });
    }

    /* renamed from: com.picspool.snappic.view.CSSplashSelectView$4 */
    /* loaded from: classes.dex */
    static /* synthetic */ class C25034 {

        /* renamed from: $SwitchMap$com$picspool$snappic$view$CSSplashSelectView$StyleBtnMode */
        static final /* synthetic */ int[] f1695x716affbb;

        static {
            int[] iArr = new int[StyleBtnMode.values().length];
            f1695x716affbb = iArr;
            try {
                iArr[StyleBtnMode.STYLE_1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1695x716affbb[StyleBtnMode.STYLE_2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1695x716affbb[StyleBtnMode.STYLE_3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void thisDefaultBtn() {
        this.bgImage.performClick();
    }

    public void setBgResource(int i, int i2) {
        this.defaultBgID = i;
        this.clickedBgID = i2;
        this.bgImage.setImageResource(i);
    }

    public void iniData(StyleBtnMode styleBtnMode) {
        this.btnsLayout.setVisibility(View.INVISIBLE);
        this.styleBtnMode = styleBtnMode;
        this.bgImage.setImageResource(this.defaultBgID);
        this.styleImage.setImageResource(this.styleBtnMode.imgID);
    }

    public OnSplashClickListener getOnSplashClickListener() {
        return this.onSplashClickListener;
    }

    public void setOnSplashClickListener(OnSplashClickListener onSplashClickListener) {
        this.onSplashClickListener = onSplashClickListener;
    }

    public StyleBtnMode getStyleBtnMode() {
        return this.styleBtnMode;
    }

    public void setStyleBtnMode(StyleBtnMode styleBtnMode) {
        this.styleBtnMode = styleBtnMode;
    }
}
