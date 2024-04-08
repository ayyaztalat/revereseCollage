package com.picspool.libfuncview.masicview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.picspool.libfuncview.masicview.CSSmearMaskPath;
import com.picspool.libfuncview.masicview.drawmosaic.CSMosaicViewAlgorithm;
import com.picspool.libfuncview.masicview.drawmosaic.CSMosaicViewDraw;
import com.picspool.libfuncview.masicview.drawmosaic.CSMyPath;
import com.picspool.libfuncview.masicview.drawmosaic.CSOnBackStatusListener;
import com.picspool.libfuncview.masicview.util.CSBmpUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;

/* loaded from: classes.dex */
public class CSDrawMosaic implements CSSmearMaskPath {
    private static final String TAG = "CSDrawMosaic";
    private Bitmap blurMosaic;
    private Bitmap bmBaseLayer;
    private Bitmap bmMosaicLayer;
    private Bitmap bmOribmpLayer;
    private Bitmap bmpCustomLayer;
    private List<CSMyPath> mBackPaths;
    private Context mContext;
    private int mGridWidth;
    private int mImageHeight;
    private Rect mImageRect;
    private int mImageWidth;
    private CSOnBackStatusListener mListener;
    private CSMosaicRes mMosaicRes;
    private Bitmap[] mMultiBmps;
    private int mPathWidth;
    private ResultBmpListener mResultBmpListener;
    private Path mTouchPath;
    private List<CSMyPath> mTouchPaths;
    private Canvas mainCanvas;
    private Mode mode;
    private Canvas mosaicCanvas;
    private CSMyPath myPath;
    private Bitmap normalMosaic;
    private Bitmap ori_bitmap;
    private String resPath;
    private Map<String, Bitmap> bitmapMap = new HashMap();
    private int downX = 0;
    private int downY = 0;
    private int curX = 0;
    private int curY = 0;
    private int bmpsdistance = DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA;
    private boolean isdistanceover = false;
    private int multibmp_index = 0;
    private Bitmap bitmapStyle = null;

    /* loaded from: classes.dex */
    public enum Mode {
        MULTIBITMAP,
        NORMAL,
        BLUR,
        CUSTOM,
        BITMAP,
        ERASE
    }

    @Override // com.picspool.libfuncview.masicview.CSSmearMaskPath
    public void setOnResultBmpListener(ResultBmpListener resultBmpListener) {
        this.mResultBmpListener = resultBmpListener;
    }

    public void setOnBackStatusListener(CSOnBackStatusListener cSOnBackStatusListener) {
        this.mListener = cSOnBackStatusListener;
    }

    public CSDrawMosaic(Context context, Bitmap bitmap) {
        this.mContext = context;
        this.ori_bitmap = bitmap;
        init();
    }

    private void init() {
        this.mTouchPaths = new ArrayList();
        this.mBackPaths = new ArrayList();
        this.mPathWidth = CSBmpUtil.dp2px(20, this.mContext);
        this.mGridWidth = CSBmpUtil.dp2px(10, this.mContext);
        Rect rect = new Rect();
        this.mImageRect = rect;
        rect.set(0, 0, this.ori_bitmap.getWidth(), this.ori_bitmap.getHeight());
        setSrcPath();
    }

    private void setSrcPath() {
        reset();
        this.bmBaseLayer = this.ori_bitmap.copy(Bitmap.Config.ARGB_8888, true);
        this.bmOribmpLayer = this.ori_bitmap.copy(Bitmap.Config.ARGB_8888, true);
        this.mainCanvas = new Canvas(this.bmBaseLayer);
        this.mImageHeight = this.bmBaseLayer.getHeight();
        this.mImageWidth = this.bmBaseLayer.getWidth();
        this.bmMosaicLayer = null;
        setMode(Mode.NORMAL);
        updatePathMosaic();
    }

    public void setRes(CSMosaicRes cSMosaicRes) {
        this.mMosaicRes = cSMosaicRes;
        if (cSMosaicRes.getmMosaicMode() != Mode.BLUR || this.mMosaicRes.getmMosaicMode() != Mode.NORMAL || this.mMosaicRes.getmMosaicMode() != Mode.MULTIBITMAP) {
            setResId(this.mMosaicRes.getImageFileName());
        }
        if (this.mMosaicRes.getmMosaicMode() == Mode.MULTIBITMAP) {
            initMultiBmpMode(this.mMosaicRes);
        }
        setMode(this.mMosaicRes.getmMosaicMode());
    }

    private void initMultiBmpMode(CSMosaicRes cSMosaicRes) {
        String[] bmps_filename = cSMosaicRes.getBmps_filename();
        if (this.mMultiBmps != null) {
            this.mMultiBmps = null;
        }
        this.mMultiBmps = new Bitmap[bmps_filename.length];
        if (bmps_filename == null || bmps_filename.length <= 0) {
            return;
        }
        for (int i = 0; i < bmps_filename.length; i++) {
            this.mMultiBmps[i] = DMBitmapDbUtil.getImageFromAssetsFile(this.mContext, bmps_filename[i]);
        }
    }

    public void setMode(Mode mode) {
        Bitmap bitmap;
        this.mode = mode;
        if (mode == Mode.BITMAP) {
            bitmap = this.bitmapMap.get(this.resPath);
            if (bitmap == null || bitmap.isRecycled()) {
                bitmap = getBitmapMasoic(this.mImageWidth, this.mImageHeight, this.mContext);
                this.bitmapMap.put(this.resPath, bitmap);
            }
        } else if (mode == Mode.BLUR) {
            Bitmap bitmap2 = this.blurMosaic;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                this.blurMosaic = CSMosaicViewAlgorithm.setBlurMosaic(this.bmBaseLayer, this.mImageWidth, this.mImageHeight);
            }
            bitmap = this.blurMosaic;
        } else if (mode == Mode.NORMAL) {
            Bitmap bitmap3 = this.normalMosaic;
            if (bitmap3 == null || bitmap3.isRecycled()) {
                this.normalMosaic = CSMosaicViewAlgorithm.getGridMosaic(this.mImageWidth, this.mImageHeight, this.mGridWidth, this.bmBaseLayer);
            }
            bitmap = this.normalMosaic;
        } else {
            bitmap = null;
        }
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.bitmapStyle = bitmap;
    }

    public Mode getMode() {
        return this.mode;
    }

    private void setCustomMasoicBitmap(int i, int i2) {
        try {
            if (this.mode != Mode.CUSTOM || this.bmBaseLayer == null || this.bmBaseLayer.isRecycled()) {
                return;
            }
            Bitmap bitmap = this.bitmapMap.get(this.resPath);
            if (bitmap == null || bitmap.isRecycled()) {
                bitmap = CSMosaicViewAlgorithm.getMosaicBitmap(i, i2, BitmapFactory.decodeStream(this.mContext.getResources().getAssets().open(this.resPath)), this.bmBaseLayer);
                this.bitmapMap.put(this.resPath, bitmap);
            }
            if (bitmap == null || bitmap.isRecycled()) {
                return;
            }
            this.bitmapStyle = bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmapMasoic(int i, int i2, Context context) {
        if (i > 0 && i2 > 0) {
            try {
                return CSMosaicViewAlgorithm.getCustomBitmap(i, i2, BitmapFactory.decodeStream(context.getResources().getAssets().open(this.resPath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setResId(String str) {
        this.resPath = str;
    }

    public void setGridWidth(int i) {
        this.mGridWidth = i;
    }

    public int getGridWidth() {
        return this.mGridWidth;
    }

    public void setPathWidth(int i) {
        this.mPathWidth = i;
    }

    public int getPathWidth() {
        return this.mPathWidth;
    }

    private void reset() {
        CSBmpUtil.recycleBitmaps(this.bmBaseLayer, this.bmMosaicLayer);
        for (CSMyPath cSMyPath : this.mTouchPaths) {
            CSBmpUtil.recycleBitmaps(cSMyPath.getBitmapList());
        }
        for (CSMyPath cSMyPath2 : this.mBackPaths) {
            CSBmpUtil.recycleBitmaps(cSMyPath2.getBitmapList());
        }
        this.mTouchPaths.clear();
        this.mBackPaths.clear();
        CSOnBackStatusListener cSOnBackStatusListener = this.mListener;
        if (cSOnBackStatusListener != null) {
            cSOnBackStatusListener.forwardDisable();
            this.mListener.backDisable();
        }
    }

    private void dispatchPathTouch(int i, int i2, int i3) {
        if (this.mImageWidth <= 0 || this.mImageHeight <= 0 || i2 < this.mImageRect.left || i2 > this.mImageRect.right || i3 < this.mImageRect.top || i3 > this.mImageRect.bottom) {
            return;
        }
        setCustomMasoicBitmap(i2, i3);
        if (i == 0) {
            onActionDown(i2, i3);
        } else if (i == 2) {
            onActionMove(i2, i3);
        } else if (i == 1) {
            onActionUp();
        }
        CSOnBackStatusListener cSOnBackStatusListener = this.mListener;
        if (cSOnBackStatusListener != null) {
            cSOnBackStatusListener.backAble();
        }
        if (this.mTouchPath != null) {
            if (i != 0 || this.mode == Mode.MULTIBITMAP) {
                drawPathMosaic();
                onDraw();
            }
        }
    }

    private void onActionUp() {
        this.isdistanceover = false;
        if (this.mTouchPath != null) {
            this.mBackPaths.clear();
            CSOnBackStatusListener cSOnBackStatusListener = this.mListener;
            if (cSOnBackStatusListener != null) {
                cSOnBackStatusListener.forwardDisable();
                this.mListener.backAble();
            }
        }
    }

    private void onActionMove(int i, int i2) {
        Path path = this.mTouchPath;
        if (path != null) {
            path.lineTo(i, i2);
            if (this.mode == Mode.MULTIBITMAP) {
                if (Math.abs(i - this.curX) > this.bmpsdistance || Math.abs(i2 - this.curY) > this.bmpsdistance) {
                    this.curX = i;
                    this.curY = i2;
                    this.isdistanceover = true;
                    this.myPath.addRect(CSMosaicViewAlgorithm.getMultiBitmapPathRect(i, i2, this.mPathWidth));
                    return;
                }
                this.isdistanceover = false;
                return;
            }
            if (this.mode == Mode.CUSTOM) {
                this.myPath.getBitmapList().add(this.bitmapStyle);
            }
            this.myPath.addAngle(Float.valueOf(CSMosaicViewAlgorithm.getPathAngle(i - this.downX, i2 - this.downY)));
            this.myPath.addRect(CSMosaicViewAlgorithm.getStyleBlurPathRect(i, i2, this.bitmapStyle, 2, 4));
        }
    }

    private void onActionDown(int i, int i2) {
        this.downX = i;
        this.downY = i2;
        this.curX = i;
        this.curY = i2;
        this.isdistanceover = false;
        this.multibmp_index = 0;
        Bitmap bitmap = this.bmpCustomLayer;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this.bmpCustomLayer = Bitmap.createBitmap(this.mImageWidth, this.mImageHeight, Bitmap.Config.ARGB_8888);
        Path path = new Path();
        this.mTouchPath = path;
        path.moveTo(i, i2);
        CSMyPath cSMyPath = new CSMyPath();
        this.myPath = cSMyPath;
        cSMyPath.setPath(this.mTouchPath);
        this.myPath.setMode(this.mode);
        this.myPath.setPaintWidth(getPathWidth());
        this.mTouchPaths.add(this.myPath);
        if (this.mode == Mode.MULTIBITMAP) {
            this.isdistanceover = true;
            this.myPath.addBitmaps(this.mMultiBmps);
            this.myPath.addRect(CSMosaicViewAlgorithm.getMultiBitmapPathRect(i2, i, this.mPathWidth));
            return;
        }
        this.myPath.addBitmap(this.bitmapStyle);
        this.myPath.addRect(CSMosaicViewAlgorithm.getStyleBlurPathRect(i, i2, this.bitmapStyle, 30, 50));
    }

    private void drawPathMosaic() {
        Bitmap bitmap;
        if (this.mosaicCanvas == null || this.myPath == null || (bitmap = this.bitmapStyle) == null || bitmap.isRecycled()) {
            return;
        }
        Mode mode = this.myPath.getMode();
        Paint creatDrawPaint = CSMosaicViewDraw.creatDrawPaint(mode);
        creatDrawPaint.setStrokeWidth(this.myPath.getPaintWidth());
        if (mode == Mode.CUSTOM) {
            CSMosaicViewDraw.onStyleBlurDraw(this.bitmapStyle, this.myPath.getAngle(), this.myPath.getRect(), this.mosaicCanvas);
        } else if (mode == Mode.MULTIBITMAP) {
            onMultiBitmapDraw(this.myPath.getRect(), this.mosaicCanvas, this.isdistanceover);
        } else if (mode == Mode.ERASE) {
            CSMosaicViewDraw.onEraseDraw(creatDrawPaint, this.myPath.getPath(), this.mosaicCanvas);
        } else {
            CSMosaicViewDraw.onNormalDraw(creatDrawPaint, this.bitmapStyle, this.myPath.getPath(), this.mosaicCanvas, this.bmpCustomLayer);
        }
        Log.d(TAG, "drawPathMosaic: ");
    }

    private void updatePathMosaic() {
        if (this.mImageWidth <= 0 || this.mImageHeight <= 0) {
            return;
        }
        Paint creatDrawPaint = CSMosaicViewDraw.creatDrawPaint(this.mode);
        Bitmap bitmap = this.bmMosaicLayer;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bmMosaicLayer.recycle();
            this.bmMosaicLayer = null;
        }
        this.bmMosaicLayer = Bitmap.createBitmap(this.mImageWidth, this.mImageHeight, Bitmap.Config.ARGB_8888);
        this.mosaicCanvas = new Canvas(this.bmMosaicLayer);
        for (int i = 0; i < this.mTouchPaths.size(); i++) {
            CSMyPath cSMyPath = this.mTouchPaths.get(i);
            creatDrawPaint.setStrokeWidth(cSMyPath.getPaintWidth());
            Mode mode = cSMyPath.getMode();
            if (mode == Mode.ERASE) {
                creatDrawPaint.setColor(0);
                creatDrawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            } else {
                creatDrawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            }
            if (mode == Mode.CUSTOM) {
                CSMosaicViewDraw.onStyleBlurUpdateDraw(cSMyPath, this.mosaicCanvas);
            } else if (mode == Mode.MULTIBITMAP) {
                CSMosaicViewDraw.onMultiBitmapUpdateDraw(cSMyPath, this.mosaicCanvas);
            } else if (mode == Mode.ERASE) {
                CSMosaicViewDraw.onEraseDraw(creatDrawPaint, cSMyPath.getPath(), this.mosaicCanvas);
            } else {
                CSMosaicViewDraw.onNormalUpdateDraw(creatDrawPaint, cSMyPath, this.mosaicCanvas, this.mImageWidth, this.mImageHeight);
            }
        }
    }

    private void onMultiBitmapDraw(Rect rect, Canvas canvas, boolean z) {
        if (z) {
            Bitmap[] bitmapArr = this.mMultiBmps;
            int i = this.multibmp_index;
            Bitmap bitmap = bitmapArr[i];
            if (i < bitmapArr.length - 1) {
                this.multibmp_index = i + 1;
            } else {
                this.multibmp_index = 0;
            }
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, (Rect) null, rect, (Paint) null);
            }
        }
    }

    public boolean getForwardStatus() {
        return this.mBackPaths.size() > 0;
    }

    public boolean getBackStatus() {
        return this.mTouchPaths.size() > 0;
    }

    protected void onDraw() {
        if (this.ori_bitmap != null) {
            this.mainCanvas.drawBitmap(this.bmOribmpLayer, (Rect) null, this.mImageRect, (Paint) null);
        }
        Bitmap bitmap = this.bmMosaicLayer;
        if (bitmap != null) {
            this.mainCanvas.drawBitmap(bitmap, (Rect) null, this.mImageRect, (Paint) null);
        }
        ResultBmpListener resultBmpListener = this.mResultBmpListener;
        if (resultBmpListener != null) {
            resultBmpListener.onReturnResultBmp(this.bmBaseLayer);
        }
    }

    @Override // com.picspool.libfuncview.masicview.CSActionGesture
    public void actionTouch(MotionEvent motionEvent, float f, float f2, float f3) {
        dispatchPathTouch(motionEvent.getAction(), (int) (f * this.ori_bitmap.getWidth()), (int) (f2 * this.ori_bitmap.getHeight()));
    }

    @Override // com.picspool.libfuncview.masicview.CSActionGesture
    public void actionUndo() {
        if (this.mTouchPaths.size() > 0) {
            List<CSMyPath> list = this.mBackPaths;
            List<CSMyPath> list2 = this.mTouchPaths;
            list.add(list2.remove(list2.size() - 1));
            if (this.mListener != null) {
                if (getBackStatus()) {
                    this.mListener.backAble();
                } else {
                    this.mListener.backDisable();
                }
                if (getForwardStatus()) {
                    this.mListener.forwardAble();
                } else {
                    this.mListener.forwardDisable();
                }
            }
            updatePathMosaic();
            onDraw();
        }
    }

    @Override // com.picspool.libfuncview.masicview.CSActionGesture
    public void actionRedo() {
        if (this.mBackPaths.size() > 0) {
            List<CSMyPath> list = this.mTouchPaths;
            List<CSMyPath> list2 = this.mBackPaths;
            list.add(list2.remove(list2.size() - 1));
            if (this.mListener != null) {
                if (getBackStatus()) {
                    this.mListener.backAble();
                } else {
                    this.mListener.backDisable();
                }
                if (getForwardStatus()) {
                    this.mListener.forwardAble();
                } else {
                    this.mListener.forwardDisable();
                }
            }
            updatePathMosaic();
            onDraw();
        }
    }

    @Override // com.picspool.libfuncview.masicview.CSSmearMaskPath
    public void actionEraser() {
        setMode(Mode.ERASE);
    }

    public void release() {
        Bitmap bitmap = this.blurMosaic;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.blurMosaic.recycle();
            this.blurMosaic = null;
        }
        Bitmap bitmap2 = this.normalMosaic;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.normalMosaic.recycle();
            this.normalMosaic = null;
        }
        for (Bitmap bitmap3 : this.bitmapMap.values()) {
            if (bitmap3 != null && !bitmap3.isRecycled()) {
                bitmap3.recycle();
            }
        }
        this.bitmapMap.clear();
    }
}
