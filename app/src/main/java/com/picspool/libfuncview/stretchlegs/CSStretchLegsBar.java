package com.picspool.libfuncview.stretchlegs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.picspool.libfuncview.res.CSBarViewControlListener;
import com.picspool.libfuncview.stretchlegs.CSStretchView;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSStretchLegsBar extends FrameLayout {
    private static final String TAG = "CSStretchView";
    private int contentHeight;
    private int contentWidth;
    private View img_reset;
    private ImageView iv_compare;
    private View lyReset;
    private Context mContext;
    private CSBarViewControlListener mListener;
    private SeekBar seekBar;
    private Bitmap srcbmp;
    private int stretchprogress;
    private CSStretchView sv_content;

    public void setBarViewControlListener(CSBarViewControlListener cSBarViewControlListener) {
        this.mListener = cSBarViewControlListener;
    }

    public CSStretchLegsBar(Context context, Bitmap bitmap) {
        super(context);
        this.stretchprogress = -1;
        this.mContext = context;
        this.srcbmp = bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        initView();
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_stretchlegs, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.stretchlegs.CSStretchLegsBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSStretchLegsBar.this.mListener != null) {
                    CSStretchLegsBar.this.mListener.onCancel();
                }
            }
        });
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.stretchlegs.CSStretchLegsBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSStretchLegsBar.this.mListener != null) {
                    CSStretchLegsBar.this.mListener.onOk(CSStretchLegsBar.this.sv_content.getResultBitmap());
                    Context context = CSStretchLegsBar.this.mContext;
                    CSLibUiConfig.pointEventSingleSaveMode1(context, "stretch", "" + CSStretchLegsBar.this.stretchprogress);
                }
            }
        });
        this.sv_content = (CSStretchView) findViewById(R.id.sv_content);
        this.seekBar = (SeekBar) findViewById(R.id.seekbar);
        this.lyReset = findViewById(R.id.ly_reset);
        this.img_reset = findViewById(R.id.img_reset);
        this.lyReset.setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.stretchlegs.CSStretchLegsBar.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSStretchLegsBar.this.resetStretchView();
                CSStretchLegsBar.this.img_reset.setSelected(false);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.iv_compare);
        this.iv_compare = imageView;
        imageView.setOnTouchListener(new OnTouchListener() { // from class: com.picspool.libfuncview.stretchlegs.CSStretchLegsBar.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    CSStretchLegsBar.this.sv_content.setCompare(true);
                    CSStretchLegsBar.this.iv_compare.setPressed(true);
                    return true;
                } else if (motionEvent.getAction() == 1) {
                    CSStretchLegsBar.this.sv_content.setCompare(false);
                    CSStretchLegsBar.this.iv_compare.setPressed(false);
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.sv_content.setOnStretchViewResetListener(new CSStretchView.OnStretchViewResetListener() { // from class: com.picspool.libfuncview.stretchlegs.CSStretchLegsBar.5
            @Override // com.picspool.libfuncview.stretchlegs.CSStretchView.OnStretchViewResetListener
            public void onStretchViewReset() {
                CSStretchLegsBar cSStretchLegsBar = CSStretchLegsBar.this;
                cSStretchLegsBar.contentHeight = cSStretchLegsBar.sv_content.getLayoutParams().height;
                CSStretchLegsBar cSStretchLegsBar2 = CSStretchLegsBar.this;
                cSStretchLegsBar2.contentWidth = cSStretchLegsBar2.sv_content.getLayoutParams().width;
                CSStretchLegsBar.this.seekBar.setProgress(0);
            }
        });
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.libfuncview.stretchlegs.CSStretchLegsBar.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                CSStretchLegsBar.this.stretchprogress = i;
                CSStretchLegsBar.this.img_reset.setSelected(true);
                float progress = ((seekBar.getProgress() / 100.0f) * 40.0f) / ((DMScreenInfoUtil.screenWidth(CSStretchLegsBar.this.mContext) * CSStretchLegsBar.this.srcbmp.getHeight()) / CSStretchLegsBar.this.srcbmp.getWidth());
                CSStretchLegsBar.this.sv_content.setLayoutParams(new LayoutParams(CSStretchLegsBar.this.contentWidth, (int) (CSStretchLegsBar.this.contentHeight * 1.0f * (1.0f + progress)), 17));
                CSStretchLegsBar.this.sv_content.setStretch(progress, false, 0);
                CSStretchLegsBar.this.sv_content.invalidate();
                CSStretchLegsBar.this.sv_content.requestLayout();
            }
        });
        resetStretchView();
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context, context.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetStretchView() {
        float screenWidth = DMScreenInfoUtil.screenWidth(this.mContext);
        float height = (this.srcbmp.getHeight() * screenWidth) / this.srcbmp.getWidth();
        Log.d(TAG, "svW: " + screenWidth + "///svH:" + height);
        int i = (int) screenWidth;
        int i2 = (int) height;
        LayoutParams layoutParams = new LayoutParams(i, i2, 17);
        this.sv_content.setLayoutParams(layoutParams);
        this.sv_content.setSrcWidthHeight(i, i2);
        this.sv_content.setBitmap(this.srcbmp);
        this.sv_content.setStretch(0.0f, true, 0);
        this.sv_content.resetView();
        this.sv_content.invalidate();
        this.sv_content.requestLayout();
        this.seekBar.setProgress(0);
        this.contentWidth = layoutParams.width;
        this.contentHeight = layoutParams.height;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, float f, float f2, float f3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = (int) (width * f);
        int i2 = (int) (height * f);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawBitmap(bitmap, new Rect(0, 0, width, height), new Rect(0, 0, i, i2), new Paint());
        return createBitmap;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        CSBarViewControlListener cSBarViewControlListener;
        if (i != 4 || (cSBarViewControlListener = this.mListener) == null) {
            return true;
        }
        cSBarViewControlListener.onCancel();
        return true;
    }
}
