package com.picspool.snappic.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import com.picspool.snappic.view.CSSplashSelectView;
import com.picspool.snappic.view.CSSplashShapeView;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSplashBar extends FrameLayout {
    private String cacheKey;
    private FrameLayout ly_root;
    private View ly_splashShape_x;
    private Context mContext;
    private int mCurSplashShape;
    private onSplashBarClickListner mListenrer;
    private CSSplashShapeView mSplashShapeView;
    private CSSplashSelectView shape1;
    private CSSplashSelectView shape2;
    private CSSplashSelectView shape3;
    private CSSplashSelectView shape4;
    private CSSplashSelectView shape5;
    private CSSplashSelectView shape6;
    private Bitmap src;
    private Uri srcUri;
    private CSSplashSelectView.StyleBtnMode styleBtnMode;

    /* loaded from: classes.dex */
    public interface onSplashBarClickListner {
        void onCancel();

        void onOk(Bitmap bitmap);

        void shareBitmap(Bitmap bitmap);
    }

    public CSSplashBar(Context context, Bitmap bitmap) {
        super(context);
        this.mCurSplashShape = 1;
        this.styleBtnMode = CSSplashSelectView.StyleBtnMode.STYLE_1;
        this.cacheKey = null;
        this.src = bitmap;
        initView(context);
    }

    public CSSplashBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurSplashShape = 1;
        this.styleBtnMode = CSSplashSelectView.StyleBtnMode.STYLE_1;
        this.cacheKey = null;
        initView(context);
    }

    public CSSplashBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurSplashShape = 1;
        this.styleBtnMode = CSSplashSelectView.StyleBtnMode.STYLE_1;
        this.cacheKey = null;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_splashbar, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        this.mSplashShapeView = (CSSplashShapeView) findViewById(R.id.splash_shapeview);
        int screenWidth = DMScreenInfoUtil.screenWidth(this.mContext);
        int screenHeight = DMScreenInfoUtil.screenHeight(this.mContext) - DMScreenInfoUtil.dip2px(this.mContext, 210.0f);
        int width = this.src.getWidth();
        float f = screenHeight;
        float f2 = screenWidth;
        float height = this.src.getHeight();
        float f3 = width;
        float f4 = f / f2 > height / f3 ? f3 / f2 : height / f;
        this.mSplashShapeView.getLayoutParams().width = (int) (f3 / f4);
        this.mSplashShapeView.getLayoutParams().height = (int) (height / f4);
        this.mSplashShapeView.setImageBitmap(this.src, 1.0f / f4);
        this.mSplashShapeView.loadSplashShape(1);
        this.ly_root = (FrameLayout) findViewById(R.id.ly_splashbar_container);
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSSplashBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSplashBar.this.mListenrer != null) {
                    CSSplashBar.this.mListenrer.onOk(CSSplashBar.this.getSplashBitmap());
                }
            }
        });
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSSplashBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSplashBar.this.mListenrer != null) {
                    CSSplashBar.this.mListenrer.onCancel();
                }
            }
        });
        this.shape1 = (CSSplashSelectView) findViewById(R.id.shape1);
        this.shape2 = (CSSplashSelectView) findViewById(R.id.shape2);
        this.shape3 = (CSSplashSelectView) findViewById(R.id.shape3);
        this.shape4 = (CSSplashSelectView) findViewById(R.id.shape4);
        this.shape5 = (CSSplashSelectView) findViewById(R.id.shape5);
        this.shape6 = (CSSplashSelectView) findViewById(R.id.shape6);
        this.shape1.setBgResource(R.drawable.splash_s1, R.drawable.splash_s2);
        this.shape1.setOnSplashClickListener(new CSSplashSelectView.OnSplashClickListener() { // from class: com.picspool.snappic.widget.CSSplashBar.3
            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickStyle(CSSplashSelectView.StyleBtnMode styleBtnMode) {
                CSSplashBar.this.styleBtnMode = styleBtnMode;
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.setStyleMode(styleBtnMode.styleMode);
                }
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickChange() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(1);
                }
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClick() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(1);
                }
                if (CSSplashBar.this.mCurSplashShape != 1) {
                    CSSplashBar.this.resetButtonBar();
                }
                CSSplashBar.this.mCurSplashShape = 1;
            }
        });
        this.shape2.setBgResource(R.drawable.splash_c1, R.drawable.splash_c2);
        this.shape2.setOnSplashClickListener(new CSSplashSelectView.OnSplashClickListener() { // from class: com.picspool.snappic.widget.CSSplashBar.4
            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickStyle(CSSplashSelectView.StyleBtnMode styleBtnMode) {
                CSSplashBar.this.styleBtnMode = styleBtnMode;
                CSSplashBar.this.mSplashShapeView.setStyleMode(styleBtnMode.styleMode);
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickChange() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(2);
                }
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClick() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(2);
                }
                if (CSSplashBar.this.mCurSplashShape != 2) {
                    CSSplashBar.this.resetButtonBar();
                }
                CSSplashBar.this.mCurSplashShape = 2;
            }
        });
        this.shape3.setBgResource(R.drawable.splash_i1, R.drawable.splash_i2);
        this.shape3.setOnSplashClickListener(new CSSplashSelectView.OnSplashClickListener() { // from class: com.picspool.snappic.widget.CSSplashBar.5
            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickStyle(CSSplashSelectView.StyleBtnMode styleBtnMode) {
                CSSplashBar.this.styleBtnMode = styleBtnMode;
                CSSplashBar.this.mSplashShapeView.setStyleMode(styleBtnMode.styleMode);
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickChange() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(3);
                }
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClick() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(3);
                }
                if (CSSplashBar.this.mCurSplashShape != 3) {
                    CSSplashBar.this.resetButtonBar();
                }
                CSSplashBar.this.mCurSplashShape = 3;
            }
        });
        this.shape4.setBgResource(R.drawable.splash_4, R.drawable.splash_4_press);
        this.shape4.setOnSplashClickListener(new CSSplashSelectView.OnSplashClickListener() { // from class: com.picspool.snappic.widget.CSSplashBar.6
            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickStyle(CSSplashSelectView.StyleBtnMode styleBtnMode) {
                CSSplashBar.this.styleBtnMode = styleBtnMode;
                CSSplashBar.this.mSplashShapeView.setStyleMode(styleBtnMode.styleMode);
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickChange() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(4);
                }
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClick() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(4);
                }
                if (CSSplashBar.this.mCurSplashShape != 4) {
                    CSSplashBar.this.resetButtonBar();
                }
                CSSplashBar.this.mCurSplashShape = 4;
            }
        });
        this.shape5.setBgResource(R.drawable.splash_5, R.drawable.splash_5_press);
        this.shape5.setOnSplashClickListener(new CSSplashSelectView.OnSplashClickListener() { // from class: com.picspool.snappic.widget.CSSplashBar.7
            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickStyle(CSSplashSelectView.StyleBtnMode styleBtnMode) {
                CSSplashBar.this.styleBtnMode = styleBtnMode;
                CSSplashBar.this.mSplashShapeView.setStyleMode(styleBtnMode.styleMode);
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickChange() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(5);
                }
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClick() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(5);
                }
                if (CSSplashBar.this.mCurSplashShape != 5) {
                    CSSplashBar.this.resetButtonBar();
                }
                CSSplashBar.this.mCurSplashShape = 5;
            }
        });
        this.shape6.setBgResource(R.drawable.splash_6, R.drawable.splash_6_press);
        this.shape6.setOnSplashClickListener(new CSSplashSelectView.OnSplashClickListener() { // from class: com.picspool.snappic.widget.CSSplashBar.8
            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickStyle(CSSplashSelectView.StyleBtnMode styleBtnMode) {
                CSSplashBar.this.styleBtnMode = styleBtnMode;
                CSSplashBar.this.mSplashShapeView.setStyleMode(styleBtnMode.styleMode);
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClickChange() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(6);
                }
            }

            @Override // com.picspool.snappic.view.CSSplashSelectView.OnSplashClickListener
            public void onClick() {
                if (CSSplashBar.this.mSplashShapeView != null) {
                    CSSplashBar.this.mSplashShapeView.loadSplashShape(6);
                }
                if (CSSplashBar.this.mCurSplashShape != 6) {
                    CSSplashBar.this.resetButtonBar();
                }
                CSSplashBar.this.mCurSplashShape = 6;
            }
        });
        this.shape1.thisDefaultBtn();
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context2 = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context2, context2.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getSplashBitmap() {
        Bitmap bitmap;
        if (this.mSplashShapeView != null && (bitmap = this.src) != null && !bitmap.isRecycled()) {
            Bitmap createBitmap = Bitmap.createBitmap(this.src.getWidth(), this.src.getHeight(), Bitmap.Config.ARGB_8888);
            this.mSplashShapeView.drawImage(new Canvas(createBitmap));
            if (createBitmap != this.src && createBitmap != null && !createBitmap.isRecycled()) {
                return createBitmap;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetButtonBar() {
        this.shape1.iniData(this.styleBtnMode);
        this.shape2.iniData(this.styleBtnMode);
        this.shape3.iniData(this.styleBtnMode);
        this.shape4.iniData(this.styleBtnMode);
        this.shape5.iniData(this.styleBtnMode);
        this.shape6.iniData(this.styleBtnMode);
    }

    public void setonSplashBarClickListner(onSplashBarClickListner onsplashbarclicklistner) {
        this.mListenrer = onsplashbarclicklistner;
    }

    public void shareBitmap() {
        onSplashBarClickListner onsplashbarclicklistner = this.mListenrer;
        if (onsplashbarclicklistner != null) {
            onsplashbarclicklistner.shareBitmap(getSplashBitmap());
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        onSplashBarClickListner onsplashbarclicklistner;
        if (i != 4 || (onsplashbarclicklistner = this.mListenrer) == null) {
            return true;
        }
        onsplashbarclicklistner.onCancel();
        return true;
    }
}
