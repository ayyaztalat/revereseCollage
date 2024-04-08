package com.winflag.libcollage.widget.radioview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.sky.testproject.R;

/* loaded from: classes.dex */
public class ViewTemplateRatio extends FrameLayout {
    private OnTempRationViewListener onEventListener;
    private SeekBar seekRatio;

    /* loaded from: classes.dex */
    public interface OnTempRationViewListener {
        void ratioChange(int i);

        void ratioResize();
    }

    public ViewTemplateRatio(Context context) {
        super(context);
        init(context);
    }

    public ViewTemplateRatio(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public void setOnTempRatioViewListener(OnTempRationViewListener onTempRationViewListener) {
        this.onEventListener = onTempRationViewListener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_template_ratio, (ViewGroup) this, true);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekRatio);
        this.seekRatio = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.winflag.libcollage.widget.radioview.ViewTemplateRatio.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                if (ViewTemplateRatio.this.onEventListener != null) {
                    ViewTemplateRatio.this.onEventListener.ratioChange(i);
                }
            }
        });
        findViewById(R.id.layout_resize).setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.widget.radioview.ViewTemplateRatio.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ViewTemplateRatio.this.onEventListener != null) {
                    ViewTemplateRatio.this.onEventListener.ratioResize();
                }
            }
        });
    }

    public void setCurrentRatio(int i) {
        this.seekRatio.setProgress(i);
    }
}
