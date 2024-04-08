package com.picspool.libfuncview.jtblur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.picspool.lib.filter.gpu.GPUImageView;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSJtblurBar extends FrameLayout {
    private GPUImageTwoInputFilter gpuImageNormalBlendFilter2;
    private GPUImageView gpuImageView;
    private CSJtBlurTouchView jtBlurTouchView;
    private FrameLayout ly_img_container;
    private Context mContext;
    private Bitmap srcbmp;

    public CSJtblurBar(Context context, Bitmap bitmap) {
        super(context);
        this.mContext = context;
        this.srcbmp = bitmap;
        initView();
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_jtblur, (ViewGroup) this, true);
        this.ly_img_container = (FrameLayout) findViewById(R.id.ly_img_container);
        int screenWidth = DMScreenInfoUtil.screenWidth(this.mContext) - DMScreenInfoUtil.dip2px(this.mContext, 20.0f);
        int screenHeight = DMScreenInfoUtil.screenHeight(this.mContext) - DMScreenInfoUtil.dip2px(this.mContext, 170.0f);
        if (this.srcbmp.getWidth() / this.srcbmp.getHeight() > screenWidth / screenHeight) {
            screenHeight = (this.srcbmp.getHeight() * screenWidth) / this.srcbmp.getWidth();
        } else {
            screenWidth = (this.srcbmp.getWidth() * screenHeight) / this.srcbmp.getHeight();
        }
        CSJtBlurTouchView cSJtBlurTouchView = new CSJtBlurTouchView(this.mContext, this.srcbmp, screenWidth, screenHeight);
        this.jtBlurTouchView = cSJtBlurTouchView;
        this.ly_img_container.addView(cSJtBlurTouchView, new LayoutParams(screenWidth, screenHeight, 17));
        ((SeekBar) findViewById(R.id.seekbar_1)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.libfuncview.jtblur.CSJtblurBar.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                CSJtblurBar.this.jtBlurTouchView.setSgRatio(i / 10.0f);
            }
        });
        ((SeekBar) findViewById(R.id.seekbar_2)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.libfuncview.jtblur.CSJtblurBar.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                CSJtblurBar.this.jtBlurTouchView.setSgBwidth(i + 50);
            }
        });
        ((SeekBar) findViewById(R.id.seekbar_3)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.libfuncview.jtblur.CSJtblurBar.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                CSJtblurBar.this.jtBlurTouchView.setSgPointGray((int) ((i * 255) / 100.0f));
            }
        });
        ((SeekBar) findViewById(R.id.seekbar_4)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.libfuncview.jtblur.CSJtblurBar.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                CSJtblurBar.this.jtBlurTouchView.setSgPointGRound((int) ((i * 255) / 100.0f));
            }
        });
    }

    private Bitmap creatmBmp() {
        Bitmap createBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(-12230930);
        canvas.drawRect(0.0f, 0.0f, 1000.0f, 300.0f, paint);
        paint.setColor(-1723506962);
        canvas.drawRect(0.0f, 300.0f, 1000.0f, 600.0f, paint);
        paint.setColor(860184302);
        canvas.drawRect(0.0f, 600.0f, 1000.0f, 1000.0f, paint);
        return createBitmap;
    }
}
