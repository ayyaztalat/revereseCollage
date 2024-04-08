package com.winflag.libcollage.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class ViewTemplateAdjust extends RelativeLayout {
    static String TAG = "ResizeBarView";
    private FrameLayout bg_mask;
    int corner;
    ImageView imgBack;
    int inner;
    public OnCropSeekBarChangeListener mListener;
    int mode;
    int outer;
    private RelativeLayout resize_function_layout;
    int rotation;
    private SeekBar seekBarCornerResize;
    private SeekBar seekBarInnerResize;
    private SeekBar seekBarOuterResize;
    private SeekBar seekBarRotationResize;
    private boolean seekbarTouched;

    /* loaded from: classes.dex */
    public interface OnCropSeekBarChangeListener {
        void progressChanged(int i, int i2);
    }

    /* loaded from: classes.dex */
    public interface OnRizeOperationListener {
        void onResizeRounderConrner(int i);
    }

    protected void resetSelStatus() {
    }

    public ViewTemplateAdjust(Context context) {
        super(context);
        this.mode = 1;
        this.outer = 0;
        this.inner = 0;
        this.corner = 0;
        this.rotation = 0;
        this.seekbarTouched = false;
        init(context);
    }

    public ViewTemplateAdjust(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mode = 1;
        this.outer = 0;
        this.inner = 0;
        this.corner = 0;
        this.rotation = 0;
        this.seekbarTouched = false;
        init(context);
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_template_adjust, (ViewGroup) this, true);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.bg_mask);
        this.bg_mask = frameLayout;
        frameLayout.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.widget.ViewTemplateAdjust.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ViewTemplateAdjust.this.setVisibility(View.INVISIBLE);
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.resize_function_layout);
        this.resize_function_layout = relativeLayout;
        relativeLayout.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.widget.ViewTemplateAdjust.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Log.v(ViewTemplateAdjust.TAG, "resize_function_layout");
            }
        });
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarouter);
        this.seekBarOuterResize = seekBar;
        seekBar.setOnSeekBarChangeListener(new OnSeekBarCornerChangeListener());
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBarinner);
        this.seekBarInnerResize = seekBar2;
        seekBar2.setOnSeekBarChangeListener(new OnSeekBarCornerChangeListener());
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.seekBarcornor);
        this.seekBarCornerResize = seekBar3;
        seekBar3.setOnSeekBarChangeListener(new OnSeekBarCornerChangeListener());
        SeekBar seekBar4 = (SeekBar) findViewById(R.id.seekBarrotation);
        this.seekBarRotationResize = seekBar4;
        seekBar4.setOnSeekBarChangeListener(new OnSeekBarCornerChangeListener());
    }

    public void setSingleModel(boolean z) {
        if (z) {
            findViewById(R.id.layout_inner).setVisibility(View.GONE);
            findViewById(R.id.layout_cornor_container).setVisibility(View.GONE);
            int dip2px = DMScreenInfoUtil.dip2px(getContext(), 45.0f);
            findViewById(R.id.layout_outer).setPadding(dip2px, 0, dip2px, 0);
            return;
        }
        findViewById(R.id.layout_inner).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_cornor_container).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_outer).setPadding(0, 0, 0, 0);
    }

    public void reset() {
        this.outer = 0;
        this.seekBarOuterResize.setProgress(0);
        this.inner = 0;
        this.seekBarInnerResize.setProgress(0);
        this.corner = 0;
        this.seekBarCornerResize.setProgress(0);
        this.rotation = 0;
        this.seekBarRotationResize.setProgress(0);
    }

    public void setRotationValue(int i) {
        this.rotation = i;
        this.seekBarRotationResize.setProgress(i);
    }

    public void setOuterValue(int i) {
        this.outer = i;
        this.seekBarOuterResize.setProgress(i);
    }

    public void setInnerValue(int i) {
        this.inner = i;
        this.seekBarInnerResize.setProgress(i);
    }

    public void setCornerValue(int i) {
        this.corner = i;
        this.seekBarCornerResize.setProgress(i);
    }

    public void setEnable(Boolean bool) {
        this.seekBarInnerResize.setEnabled(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class OnSeekBarCornerChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        OnSeekBarCornerChangeListener() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (ViewTemplateAdjust.this.mListener == null || !ViewTemplateAdjust.this.seekbarTouched) {
                return;
            }
            if (seekBar != ViewTemplateAdjust.this.seekBarOuterResize) {
                if (seekBar != ViewTemplateAdjust.this.seekBarInnerResize) {
                    if (seekBar == ViewTemplateAdjust.this.seekBarCornerResize) {
                        ViewTemplateAdjust.this.mListener.progressChanged(3, i);
                        ViewTemplateAdjust.this.corner = i;
                        return;
                    }
                    ViewTemplateAdjust.this.mListener.progressChanged(4, i);
                    ViewTemplateAdjust.this.rotation = i;
                    return;
                }
                ViewTemplateAdjust.this.mListener.progressChanged(2, i);
                ViewTemplateAdjust.this.inner = i;
                return;
            }
            ViewTemplateAdjust.this.mListener.progressChanged(1, i);
            ViewTemplateAdjust.this.outer = i;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            ViewTemplateAdjust.this.seekbarTouched = true;
        }
    }
}
