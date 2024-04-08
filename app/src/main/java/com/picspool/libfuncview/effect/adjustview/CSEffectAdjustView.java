package com.picspool.libfuncview.effect.adjustview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSEffectAdjustView extends FrameLayout {
    private int aniDuration;
    private int barHeight;
    private CSPorterDuffModeBarAdapter effectBarViewAdapter;
    private CSPorterDuffModeManager effectManager;
    private View lyRoot;
    private Context mContext;
    private onEffectAdjustViewListner mListner;
    private RecyclerView recyclerViewPdm;
    private SeekBar seekBarRotation;
    private SeekBar seekBarStrength;
    private TextView textViewRotation;
    private TextView textViewStrength;
    private View viewCancel;

    /* loaded from: classes.dex */
    public interface onEffectAdjustViewListner {
        void onCancel();

        void onPoterDuffModeClick(DMWBRes dMWBRes);

        void onProgressRotationChanged(SeekBar seekBar);

        void onProgressStrengthChanged(SeekBar seekBar);
    }

    public CSEffectAdjustView(Context context) {
        super(context);
        this.aniDuration = 300;
        this.mContext = context;
        initView();
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_effect_adjust_view, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        this.lyRoot = findViewById(R.id.ly_effect_adjust_root);
        this.barHeight = DMScreenInfoUtil.dip2px(this.mContext, 160.0f);
        this.seekBarRotation = (SeekBar) findViewById(R.id.seekbar_rotation);
        this.seekBarStrength = (SeekBar) findViewById(R.id.seekbar_strength);
        this.textViewRotation = (TextView) findViewById(R.id.text_rotation);
        this.textViewStrength = (TextView) findViewById(R.id.text_strength);
        this.recyclerViewPdm = (RecyclerView) findViewById(R.id.recyclerview);
        this.viewCancel = findViewById(R.id.ly_cancel);
        this.seekBarRotation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextView textView = CSEffectAdjustView.this.textViewRotation;
                textView.setText(i + "°");
                if (CSEffectAdjustView.this.mListner != null) {
                    CSEffectAdjustView.this.mListner.onProgressRotationChanged(seekBar);
                }
            }
        });
        this.seekBarStrength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextView textView = CSEffectAdjustView.this.textViewStrength;
                textView.setText(i + "%");
                if (CSEffectAdjustView.this.mListner != null) {
                    CSEffectAdjustView.this.mListner.onProgressStrengthChanged(seekBar);
                }
            }
        });
        this.viewCancel.setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSEffectAdjustView.this.finish();
            }
        });
        performAnimate(this.lyRoot, this.barHeight, 0.0f, this.aniDuration, new Animator.AnimatorListener() { // from class: com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView.4
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        performAnimate(this.lyRoot, 0.0f, this.barHeight, this.aniDuration, new Animator.AnimatorListener() { // from class: com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (CSEffectAdjustView.this.mListner != null) {
                    CSEffectAdjustView.this.mListner.onCancel();
                }
            }
        });
    }

    public void initDefaultDate(int i, int i2, GPUFilterType gPUFilterType) {
        this.seekBarStrength.setProgress(i);
        this.seekBarRotation.setProgress(i2);
    }

    public void setOnEffectAdjustViewListner(onEffectAdjustViewListner oneffectadjustviewlistner) {
        this.mListner = oneffectadjustviewlistner;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
            return true;
        }
        return true;
    }

    protected void performAnimate(View view, float f, float f2, int i, Animator.AnimatorListener animatorListener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, TRANSLATION_Y, f, f2);
        ofFloat.setDuration(i);
        ofFloat.addListener(animatorListener);
        ofFloat.start();
    }
}
