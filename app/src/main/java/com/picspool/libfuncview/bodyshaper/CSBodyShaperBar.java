package com.picspool.libfuncview.bodyshaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.picspool.libfuncview.res.CSBarViewControlListener;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.filter.gpu.GPUImageView;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBodyShaperBar extends FrameLayout implements SeekBar.OnSeekBarChangeListener {
    private CSGPUImagePinchDistortionFilter gpuImagePinchDistortionFilter;
    private GPUImageView gpuImageView;
    private ImageView img_src;
    private boolean isApplyFilter;
    private FrameLayout lyImgContainer;
    private FrameLayout lyRoot;
    private View ly_apply;
    private View ly_compare;
    private View ly_reset;
    private Context mContext;
    private CSBarViewControlListener mListener;
    private SeekBar seekBarRadius;
    private SeekBar seekBarStrength;
    private Bitmap srcbmp;
    private TextView text_radius;
    private TextView text_strength;

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void setBarViewControlListener(CSBarViewControlListener cSBarViewControlListener) {
        this.mListener = cSBarViewControlListener;
    }

    public CSBodyShaperBar(Context context, Bitmap bitmap) {
        super(context);
        this.isApplyFilter = false;
        this.mContext = context;
        this.srcbmp = bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        initView();
    }

    private void initView() {
        int screenHeight;
        int width;
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_bodyshaper, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.bodyshaper.CSBodyShaperBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSBodyShaperBar.this.mListener != null) {
                    CSBodyShaperBar.this.mListener.onCancel();
                }
            }
        });
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.bodyshaper.CSBodyShaperBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSBodyShaperBar.this.mListener != null) {
                    GPUFilter.asyncFilterForFilter(CSBodyShaperBar.this.srcbmp, CSBodyShaperBar.this.gpuImagePinchDistortionFilter, new OnPostFilteredListener() { // from class: com.picspool.libfuncview.bodyshaper.CSBodyShaperBar.2.1
                        @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                        public void postFiltered(Bitmap bitmap) {
                            CSBodyShaperBar.this.mListener.onOk(bitmap);
                            Context context = CSBodyShaperBar.this.mContext;
                            CSLibUiConfig.pointEventSingleSaveMode1(context, "enlarger", "strength(" + CSBodyShaperBar.this.seekBarStrength.getProgress() + ")radius(" + CSBodyShaperBar.this.seekBarRadius.getProgress() + ")");
                        }
                    });
                }
            }
        });
        this.lyRoot = (FrameLayout) findViewById(R.id.ly_bodybar_root);
        this.lyImgContainer = (FrameLayout) findViewById(R.id.ly_img_container);
        GPUImageView gPUImageView = (GPUImageView) findViewById(R.id.gpu_imageview);
        this.gpuImageView = gPUImageView;
        gPUImageView.setImage(this.srcbmp);
        this.gpuImageView.setBackgroundColor(this.mContext.getResources().getColor(R.color.libui_gpubg_grey));
        CSGPUImagePinchDistortionFilter cSGPUImagePinchDistortionFilter = new CSGPUImagePinchDistortionFilter(0.3f, 0.4f, new PointF(0.5f, 0.5f));
        this.gpuImagePinchDistortionFilter = cSGPUImagePinchDistortionFilter;
        this.gpuImageView.setFilter(cSGPUImagePinchDistortionFilter);
        this.text_strength = (TextView) findViewById(R.id.text_strength);
        this.text_radius = (TextView) findViewById(R.id.text_radius);
        this.seekBarStrength = (SeekBar) findViewById(R.id.seekbar_strength);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_radius);
        this.seekBarRadius = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        this.seekBarStrength.setOnSeekBarChangeListener(this);
        this.seekBarRadius.setProgress(20);
        this.seekBarStrength.setProgress(70);
        if (this.srcbmp.getWidth() >= this.srcbmp.getHeight()) {
            width = DMScreenInfoUtil.screenWidth(this.mContext) - DMScreenInfoUtil.dip2px(this.mContext, 20.0f);
            screenHeight = (this.srcbmp.getHeight() * width) / this.srcbmp.getWidth();
        } else {
            screenHeight = DMScreenInfoUtil.screenHeight(this.mContext) - DMScreenInfoUtil.dip2px(this.mContext, 170.0f);
            width = (this.srcbmp.getWidth() * screenHeight) / this.srcbmp.getHeight();
        }
        CSBodyTouchView cSBodyTouchView = new CSBodyTouchView(this.mContext);
        this.lyImgContainer.addView(cSBodyTouchView, new LayoutParams(width, screenHeight, 17));
        cSBodyTouchView.setTouchEventCallBack(new CSBodyTouchView.onTouchEventCallBack() { // from class: com.picspool.libfuncview.bodyshaper.CSBodyShaperBar.3
            @Override // com.picspool.libfuncview.bodyshaper.CSBodyTouchView.onTouchEventCallBack
            public void getPoint(float f, float f2) {
                if (CSBodyShaperBar.this.isApplyFilter) {
                    CSBodyShaperBar cSBodyShaperBar = CSBodyShaperBar.this;
                    cSBodyShaperBar.isApplyFilter = !cSBodyShaperBar.isApplyFilter;
                    CSBodyShaperBar.this.ly_apply.setClickable(true);
                    CSBodyShaperBar.this.gpuImagePinchDistortionFilter.setRadius(0.3f);
                    CSBodyShaperBar.this.seekBarRadius.setProgress(60);
                }
                CSBodyShaperBar.this.gpuImagePinchDistortionFilter.setCenter(new PointF(f, f2));
                CSBodyShaperBar.this.gpuImageView.requestRender();
            }
        });
        View findViewById = findViewById(R.id.ly_apply);
        this.ly_apply = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.bodyshaper.CSBodyShaperBar.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSBodyShaperBar.this.isApplyFilter) {
                    return;
                }
                CSBodyShaperBar.this.gpuImageView.setImage(CSBodyShaperBar.this.gpuImageView.getBitmap());
                CSBodyShaperBar.this.isApplyFilter = true;
                CSBodyShaperBar.this.ly_apply.setClickable(false);
                CSBodyShaperBar.this.ly_reset.setClickable(true);
                CSBodyShaperBar.this.ly_reset.setSelected(true);
                CSBodyShaperBar.this.gpuImagePinchDistortionFilter.setRadius(0.0f);
                CSBodyShaperBar.this.seekBarRadius.setProgress(0);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.img_src);
        this.img_src = imageView;
        imageView.setImageBitmap(this.srcbmp);
        View findViewById2 = findViewById(R.id.ly_compare);
        this.ly_compare = findViewById2;
        findViewById2.setOnTouchListener(new OnTouchListener() { // from class: com.picspool.libfuncview.bodyshaper.CSBodyShaperBar.5
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    CSBodyShaperBar.this.img_src.setVisibility(View.VISIBLE);
                } else if (motionEvent.getAction() == 1) {
                    CSBodyShaperBar.this.img_src.setVisibility(View.GONE);
                }
                return true;
            }
        });
        View findViewById3 = findViewById(R.id.ly_reset);
        this.ly_reset = findViewById3;
        findViewById3.setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.bodyshaper.CSBodyShaperBar.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSBodyShaperBar.this.gpuImageView.setImage(CSBodyShaperBar.this.srcbmp);
                CSBodyShaperBar.this.ly_reset.setClickable(false);
                CSBodyShaperBar.this.ly_reset.setSelected(false);
                CSBodyShaperBar.this.gpuImagePinchDistortionFilter.setCenter(new PointF(0.5f, 0.5f));
                CSBodyShaperBar.this.seekBarRadius.setProgress(60);
                CSBodyShaperBar.this.seekBarStrength.setProgress(100);
            }
        });
        View findViewById4 = findViewById(R.id.ly_funcbar);
        Context context = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById4, DMScreenInfoUtil.dip2px(context, context.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (this.gpuImagePinchDistortionFilter != null) {
            if (seekBar.getId() == R.id.seekbar_strength) {
                TextView textView = this.text_strength;
                textView.setText(i + "%");
                this.gpuImagePinchDistortionFilter.setScale(((((float) i) - 50.0f) / 50.0f) * 0.4f);
            } else if (seekBar.getId() == R.id.seekbar_radius) {
                TextView textView2 = this.text_radius;
                textView2.setText(i + "%");
                this.gpuImagePinchDistortionFilter.setRadius(((float) i) / 200.0f);
                boolean z2 = this.isApplyFilter;
                if (z2 && i != 0) {
                    this.isApplyFilter = !z2;
                    this.ly_apply.setClickable(true);
                }
            }
        }
        this.gpuImageView.requestRender();
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
