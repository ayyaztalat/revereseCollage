package com.picspool.lib.text.useless;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import com.picspool.lib.sticker.util.DM_ImageTransformPanel;
import com.picspool.lib.sticker.view.DMStickersRenderer;
import com.picspool.lib.sticker.view.DMStickersSurfaceView;

/* loaded from: classes3.dex */
public class DMTextSurfaceView extends DMStickersSurfaceView {
    public DMTextSurfaceView(Context context) {
        super(context);
    }

    public DMTextSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.picspool.lib.sticker.view.DMStickersSurfaceView
    public void startRender() {
        setRenderer(new DMTextBMStickersRenderer());
    }

    public void stopRender() {
        Log.d("MyTextView", "stopRender ");
    }

    @Override // com.picspool.lib.sticker.view.DMStickersSurfaceView
    public CanvasThread createCanvasThread(SurfaceHolder surfaceHolder, DMStickersRenderer dMStickersRenderer) {
        return new TextCanvasThread(surfaceHolder, dMStickersRenderer);
    }

    /* loaded from: classes3.dex */
    class TextCanvasThread extends CanvasThread {
        public TextCanvasThread(SurfaceHolder surfaceHolder, DMStickersRenderer dMStickersRenderer) {
            super(surfaceHolder, dMStickersRenderer);
        }

        @Override // com.picspool.lib.sticker.view.DMStickersSurfaceView.CanvasThread
        public DM_ImageTransformPanel createPanel() {
            return new DMTextTransformPanel(DMTextSurfaceView.this.getContext());
        }
    }
}
