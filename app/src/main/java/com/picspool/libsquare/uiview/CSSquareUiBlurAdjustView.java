package com.picspool.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareUiBlurAdjustView extends FrameLayout {
    private View layout_close;
    private OnSquareUiBlurAdjustViewListener onEventListener;
    private SeekBar seekBlur;

    /* loaded from: classes.dex */
    public interface OnSquareUiBlurAdjustViewListener {
        void blurRatioChange(float f);

        void onBlurClose();
    }

    public CSSquareUiBlurAdjustView(Context context) {
        super(context);
        init(context);
    }

    public CSSquareUiBlurAdjustView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public void setOnSquareUiBlurAdjustViewListener(OnSquareUiBlurAdjustViewListener onSquareUiBlurAdjustViewListener) {
        this.onEventListener = onSquareUiBlurAdjustViewListener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_blur_adjust, (ViewGroup) this, true);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBlur);
        this.seekBlur = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiBlurAdjustView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                float progress = CSSquareUiBlurAdjustView.this.seekBlur.getProgress() / 100.0f;
                if (progress != 0.0f || CSSquareUiBlurAdjustView.this.onEventListener == null) {
                    return;
                }
                CSSquareUiBlurAdjustView.this.onEventListener.blurRatioChange(progress);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                CSSquareUiBlurAdjustView.this.onEventListener.blurRatioChange(seekBar2.getProgress() / 100.0f);
            }
        });
        View findViewById = findViewById(R.id.ly_close);
        this.layout_close = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiBlurAdjustView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiBlurAdjustView.this.onEventListener != null) {
                    CSSquareUiBlurAdjustView.this.onEventListener.onBlurClose();
                }
            }
        });
    }

    public void setCurrentRatio(float f) {
        this.seekBlur.setProgress((int) (f * 100.0f));
    }
}
