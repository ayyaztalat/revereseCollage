package com.picspool.snappic.widget.square;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.constraintlayout.motion.widget.Key;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.snappic.adapter.CSBgEffectRecyclerViewAdapter;
import com.picspool.snappic.manager.CSSquareBgEffectColorManager;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareBgBar extends FrameLayout {
    private int aniDuration;
    private int barHeight;
    private View lyRoot;
    private Context mContext;
    private SquareBgBarListener mListenrer;

    /* loaded from: classes.dex */
    public interface SquareBgBarListener {
        void onCancel();

        void onColorClicked(DMWBColorRes dMWBColorRes, int i);
    }

    public CSSquareBgBar(Context context) {
        super(context);
        this.aniDuration = 300;
        this.mContext = context;
        initView(context);
    }

    public void setSquareBgBarListener(SquareBgBarListener squareBgBarListener) {
        this.mListenrer = squareBgBarListener;
    }

    private void initView(Context context) {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_square_bg_bar, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        this.lyRoot = findViewById(R.id.ly_squarebg_root);
        this.barHeight = DMScreenInfoUtil.dip2px(this.mContext, 160.0f);
        CSBgEffectRecyclerViewAdapter cSBgEffectRecyclerViewAdapter = new CSBgEffectRecyclerViewAdapter(this.mContext, new CSSquareBgEffectColorManager(this.mContext).getResList());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(cSBgEffectRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        cSBgEffectRecyclerViewAdapter.setOnRecyclerViewItemClikListener(new CSBgEffectRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.picspool.snappic.widget.square.CSSquareBgBar.1
            @Override // com.picspool.snappic.adapter.CSBgEffectRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                if (CSSquareBgBar.this.mListenrer != null) {
                    CSSquareBgBar.this.mListenrer.onColorClicked((DMWBColorRes) dMWBRes, i);
                }
            }
        });
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.square.CSSquareBgBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSquareBgBar.this.finish();
            }
        });
        performAnimate(this.lyRoot, this.barHeight, 0.0f, this.aniDuration, null);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        performAnimate(this.lyRoot, 0.0f, this.barHeight, this.aniDuration, new Animator.AnimatorListener() { // from class: com.picspool.snappic.widget.square.CSSquareBgBar.3
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
                if (CSSquareBgBar.this.mListenrer != null) {
                    CSSquareBgBar.this.mListenrer.onCancel();
                }
            }
        });
    }

    protected void performAnimate(View view, float f, float f2, int i, Animator.AnimatorListener animatorListener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, TRANSLATION_Y, f, f2);
        ofFloat.setDuration(i);
        if (animatorListener != null) {
            ofFloat.addListener(animatorListener);
        }
        ofFloat.start();
    }
}
