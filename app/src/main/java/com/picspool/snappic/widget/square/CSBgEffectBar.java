package com.picspool.snappic.widget.square;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.constraintlayout.motion.widget.Key;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.snappic.adapter.CSBgEffectRecyclerViewAdapter;
import com.picspool.snappic.manager.CSSquareBgEffectColorManager;
import com.picspool.snappic.widget.CSSquareBar;

import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBgEffectBar extends FrameLayout {
    private int aniDuration;
    private int barHeight;
    private int currentBgEffect;
    private CSBgEffectRecyclerViewAdapter effectRecyclerViewAdapter;
    private ImageView imgFunIcon;
    private View lyRoot;
    private Context mContext;
    private onBgEffectClickListner mListenrer;
    private RecyclerView recyclerView;
    private List<DMWBRes> reslist;
    private SeekBar seekBar;

    /* loaded from: classes.dex */
    public interface onBgEffectClickListner {
        void onCancel();

        void onColorClicked(DMWBColorRes dMWBColorRes, int i);

        void onPhotoSelectorClicked();

        void onProgressChanged(SeekBar seekBar);

        void onProgressStop(SeekBar seekBar);
    }

    public CSBgEffectBar(Context context, int i) {
        super(context);
        this.currentBgEffect = 0;
        this.aniDuration = 300;
        this.currentBgEffect = i;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_square_bgeffect_bar, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        this.lyRoot = findViewById(R.id.ly_bgeffect_root);
        this.barHeight = DMScreenInfoUtil.dip2px(this.mContext, 160.0f);
        List<DMWBRes> resList = new CSSquareBgEffectColorManager(this.mContext).getResList();
        this.reslist = resList;
        this.effectRecyclerViewAdapter = new CSBgEffectRecyclerViewAdapter(this.mContext, resList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.recyclerView = recyclerView;
        recyclerView.setAdapter(this.effectRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.effectRecyclerViewAdapter.setOnRecyclerViewItemClikListener(new CSBgEffectRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.picspool.snappic.widget.square.CSBgEffectBar.1
            @Override // com.picspool.snappic.adapter.CSBgEffectRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                if (CSBgEffectBar.this.mListenrer != null) {
                    CSBgEffectBar.this.mListenrer.onColorClicked((DMWBColorRes) dMWBRes, i);
                }
            }
        });
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.square.CSBgEffectBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSBgEffectBar.this.finish();
            }
        });
        findViewById(R.id.ly_photoselector).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.square.CSBgEffectBar.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSBgEffectBar.this.mListenrer != null) {
                    CSBgEffectBar.this.mListenrer.onPhotoSelectorClicked();
                }
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.img_funcicon);
        this.imgFunIcon = imageView;
        imageView.setColorFilter(this.mContext.getResources().getColor(R.color.libui_barview_icon_grey));
        if (this.currentBgEffect == CSSquareBar.BGEFFECTBLUR) {
            this.imgFunIcon.setImageBitmap(DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), "square/square_editor_blur.png"));
        } else if (this.currentBgEffect == CSSquareBar.BGEFFECTMOSAIC) {
            this.imgFunIcon.setImageBitmap(DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), "square/square_editor_mosaic.png"));
        } else if (this.currentBgEffect == CSSquareBar.BGEFFECTTILE) {
            this.imgFunIcon.setImageBitmap(DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), "square/square_editor_tile.png"));
        }
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_main);
        this.seekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.snappic.widget.square.CSBgEffectBar.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                if (CSBgEffectBar.this.mListenrer != null) {
                    CSBgEffectBar.this.mListenrer.onProgressChanged(seekBar2);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                if (CSBgEffectBar.this.mListenrer != null) {
                    CSBgEffectBar.this.mListenrer.onProgressStop(seekBar2);
                }
            }
        });
        performAnimate(this.lyRoot, this.barHeight, 0.0f, this.aniDuration, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        performAnimate(this.lyRoot, 0.0f, this.barHeight, this.aniDuration, new Animator.AnimatorListener() { // from class: com.picspool.snappic.widget.square.CSBgEffectBar.5
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
                if (CSBgEffectBar.this.mListenrer != null) {
                    CSBgEffectBar.this.mListenrer.onCancel();
                }
            }
        });
    }

    public void setDefalutValue(int i, int i2) {
        SeekBar seekBar = this.seekBar;
        if (seekBar != null) {
            seekBar.setProgress(i);
        }
        if (this.effectRecyclerViewAdapter == null || i2 < 0 || i2 >= this.reslist.size()) {
            return;
        }
        this.effectRecyclerViewAdapter.setSelectedPos(i2);
        this.recyclerView.smoothScrollToPosition(i2);
    }

    public void setBgEffectClickListner(onBgEffectClickListner onbgeffectclicklistner) {
        this.mListenrer = onbgeffectclicklistner;
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
        if (animatorListener != null) {
            ofFloat.addListener(animatorListener);
        }
        ofFloat.start();
    }
}
