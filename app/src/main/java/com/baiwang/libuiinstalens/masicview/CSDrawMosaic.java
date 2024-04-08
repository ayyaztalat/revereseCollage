package com.baiwang.libuiinstalens.masicview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.Log;
import android.view.MotionEvent;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;

import com.baiwang.libuiinstalens.masicview.util.CSBmpUtil;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class CSDrawMosaic implements CSSmearMaskPath {
    private static final String TAG = "CSDrawMosaic";
    private List<Float> angleList;
    private List<Bitmap> bitmapList;
    private Bitmap bmBaseLayer;
    private Bitmap bmMosaicLayer;
    private Bitmap bmOribmpLayer;
    private String inPath;
    private List<CSMyPath> mBackPaths;
    private final Context mContext;
    private int mGridWidth;
    private int mImageHeight;
    private Rect mImageRect;
    private int mImageWidth;
    private OnBackStatusListener mListener;
    private CSMosaicRes mMosaicRes;
    private Bitmap[] mMultiBmps;
    private int mPathWidth;
    private ResultBmpListener mResultBmpListener;
    private Path mTouchPath;
    private List<CSMyPath> mTouchPaths;
    private Canvas mainCanvas;
    private Bitmap mbitmap;
    private Mode mode;
    private Canvas mosaicCanvas;
    private CSMyPath myPath;
    private final Bitmap ori_bitmap;
    private String outPath;
    private List<Rect> rectList;
    private String resPath;
    private int downX = 0;
    private int downY = 0;
    private int curX = 0;
    private int curY = 0;
    private final int bmpsdistance = DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA;
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

    /* loaded from: classes.dex */
    public interface OnBackStatusListener {
        void backAble();

        void backDisable();

        void forwardAble();

        void forwardDisable();
    }

    private int clamp(int i, int i2, int i3) {
        return i < i2 ? i2 : Math.min(i, i3);
    }

    @Override // com.baiwang.libuiinstalens.masicview.CSSmearMaskPath
    public void setOnResultBmpListener(ResultBmpListener resultBmpListener) {
        mResultBmpListener = resultBmpListener;
    }

    public void setOnBackStatusListener(OnBackStatusListener onBackStatusListener) {
        mListener = onBackStatusListener;
    }

    public void dispose() {
        CSBmpUtil.recycleBitmaps(bmBaseLayer, bmMosaicLayer, bitmapStyle);
        for (CSMyPath cSMyPath : mTouchPaths) {
            if (cSMyPath != null && cSMyPath.getBitmap() != null) {
                CSBmpUtil.recycleBitmaps(cSMyPath.getBitmap());
            }
        }
        for (CSMyPath cSMyPath2 : mBackPaths) {
            if (cSMyPath2 != null && cSMyPath2.getBitmap() != null) {
                CSBmpUtil.recycleBitmaps(cSMyPath2.getBitmap());
            }
        }
    }

    public CSDrawMosaic(Context context, Bitmap bitmap) {
        mContext = context;
        ori_bitmap = bitmap;
        init();
    }

    private void init() {
        mTouchPaths = new ArrayList<>();
        mBackPaths = new ArrayList<>();
        mPathWidth = CSBmpUtil.dp2px(20, mContext);
        mGridWidth = CSBmpUtil.dp2px(10, mContext);
        Rect rect = new Rect();
        mImageRect = rect;
        rect.set(0, 0, ori_bitmap.getWidth(), ori_bitmap.getHeight());
        setSrcPath();
    }

    private void setSrcPath() {
        reset();
        bmBaseLayer = ori_bitmap.copy(Bitmap.Config.ARGB_8888, true);
        bmOribmpLayer = ori_bitmap.copy(Bitmap.Config.ARGB_8888, true);
        mainCanvas = new Canvas(bmBaseLayer);
        mImageHeight = bmBaseLayer.getHeight();
        mImageWidth = bmBaseLayer.getWidth();
        Log.i(TAG, "mImageWidth : " + mImageWidth + ", mImageHeight: " + mImageHeight);
        bmMosaicLayer = null;
        setMode(Mode.NORMAL);
        updatePathMosaic();
    }

    public void setRes(CSMosaicRes cSMosaicRes) {
        mMosaicRes = cSMosaicRes;
        if (cSMosaicRes.getmMosaicMode() != Mode.BLUR || mMosaicRes.getmMosaicMode() != Mode.NORMAL || mMosaicRes.getmMosaicMode() != Mode.MULTIBITMAP) {
            setResId(mMosaicRes.getImageFileName());
        }
        if (mMosaicRes.getmMosaicMode() == Mode.MULTIBITMAP) {
            initMultiBmpMode(mMosaicRes);
        }
        setMode(mMosaicRes.getmMosaicMode());
    }

    private void initMultiBmpMode(CSMosaicRes cSMosaicRes) {
        String[] bmps_filename = cSMosaicRes.getBmps_filename();
        if (mMultiBmps != null) {
            mMultiBmps = null;
        }
        mMultiBmps = new Bitmap[bmps_filename.length];
        if (bmps_filename.length == 0) {
            return;
        }
        for (int i = 0; i < bmps_filename.length; i++) {
            mMultiBmps[i] = DMBitmapDbUtil.getImageFromAssetsFile(mContext, bmps_filename[i]);
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        if (mode == Mode.BITMAP) {
            setCustomMasoicBitmap();
        } else if (mode == Mode.BLUR) {
            setBlurMosaic();
        } else if (mode == Mode.NORMAL) {
            bitmapStyle = getGridMosaic();
        }
    }

    public Mode getMode() {
        return mode;
    }

    private void setBlurMosaic() {
        int i;
        int i2 = mImageWidth;
        if (i2 <= 0 || (i = mImageHeight) <= 0 || bmBaseLayer == null) {
            return;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
        bitmapStyle = createBitmap;
        int width = createBitmap.getWidth();
        int height = bitmapStyle.getHeight();
        int i3 = width * height;
        int[] iArr = new int[i3];
        int[] iArr2 = new int[i3];
        bmBaseLayer.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i4 = 0; i4 < 1; i4++) {
            blur(iArr, iArr2, width, height);
            blur(iArr2, iArr, height, width);
        }
        bitmapStyle.setPixels(iArr, 0, width, 0, 0, width, height);
    }

    private void blur(int[] iArr, int[] iArr2, int i, int i2) {
        int i5 = i - 1;
        int i6 = (50 * 2) + 1;
        int i7 = i6 * 256;
        int[] iArr3 = new int[i7];
        int i8 = 0;
        for (int i9 = 0; i9 < i7; i9++) {
            iArr3[i9] = i9 / i6;
        }
        int i10 = 0;
        int i11 = 0;
        while (i10 < i2) {
            int i12 = 0;
            int i13 = 0;
            int i14 = 0;
            int i15 = 0;
            for (int i16 = -50; i16 <= 50; i16++) {
                int i17 = iArr[clamp(i16, i8, i5) + i11];
                i12 += (i17 >> 24) & 255;
                i13 += (i17 >> 16) & 255;
                i14 += (i17 >> 8) & 255;
                i15 += i17 & 255;
            }
            int i18 = i10;
            int i19 = 0;
            while (i19 < i) {
                iArr2[i18] = (iArr3[i12] << 24) | (iArr3[i13] << 16) | (iArr3[i14] << 8) | iArr3[i15];
                int i20 = i19 + 50 + 1;
                if (i20 > i5) {
                    i20 = i5;
                }
                int i21 = i19 - 50;
                if (i21 < 0) {
                    i21 = 0;
                }
                int i22 = iArr[i20 + i11];
                int i23 = iArr[i11 + i21];
                i12 += ((i22 >> 24) & 255) - ((i23 >> 24) & 255);
                i13 += ((i22 & 16711680) - (16711680 & i23)) >> 16;
                i14 += ((i22 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) - (65280 & i23)) >> 8;
                i15 += (i22 & 255) - (i23 & 255);
                i18 += i2;
                i19++;
            }
            i11 += i;
            i10++;
        }
    }

    private void setCustomMasoicBitmap(int i, int i2) {
        try {
            if (mode != Mode.CUSTOM || bmBaseLayer == null || bmBaseLayer.isRecycled()) {
                return;
            }
            Bitmap decodeStream = BitmapFactory.decodeStream(mContext.getResources().getAssets().open(resPath));
            int width = decodeStream.getWidth();
            int height = decodeStream.getHeight();
            int i3 = width * height;
            int[] iArr = new int[i3];
            if (i <= 0 || i >= bmBaseLayer.getWidth() || i2 <= 0 || i2 >= bmBaseLayer.getHeight()) {
                return;
            }
            int pixel = bmBaseLayer.getPixel(i, i2);
            decodeStream.getPixels(iArr, 0, width, 0, 0, width, height);
            for (int i4 = 0; i4 < i3; i4++) {
                if (iArr[i4] != 0) {
                    iArr[i4] = Color.argb((iArr[i4] & ViewCompat.MEASURED_STATE_MASK) >> 24, (16711680 & pixel) >> 16, (65280 & pixel) >> 8, pixel & 255);
                }
            }
            Bitmap copy = decodeStream.copy(Bitmap.Config.ARGB_8888, true);
            bitmapStyle = copy;
            copy.setPixels(iArr, 0, width, 0, 0, width, height);
            decodeStream.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBitmap(String str, Bitmap bitmap) {
        File file = new File("/sdcard/", str);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception unused) {
            unused.printStackTrace();
        }
    }

    public void setCustomMasoicBitmap() {
        if (mImageWidth <= 0 || mImageHeight <= 0) {
            return;
        }
        try {
            Bitmap decodeStream = BitmapFactory.decodeStream(mContext.getResources().getAssets().open(resPath));
            BitmapShader bitmapShader = new BitmapShader(decodeStream, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            bitmapStyle = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapStyle);
            Rect rect = new Rect(0, 0, mImageWidth, mImageHeight);
            Paint paint = new Paint();
            paint.setShader(bitmapShader);
            canvas.drawRect(rect, paint);
            canvas.save();
            decodeStream.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setResId(String str) {
        resPath = str;
    }

    public void setOutPath(String str) {
        outPath = str;
    }

    public void setGridWidth(int i) {
        mGridWidth = i;
    }

    public int getGridWidth() {
        return mGridWidth;
    }

    public void setPathWidth(int i) {
        mPathWidth = i;
    }

    public int getPathWidth() {
        return mPathWidth;
    }

    private void reset() {
        CSBmpUtil.recycleBitmaps(bmBaseLayer, bmMosaicLayer);
        for (CSMyPath cSMyPath : mTouchPaths) {
            CSBmpUtil.recycleBitmaps(cSMyPath.getBitmap());
        }
        for (CSMyPath cSMyPath2 : mBackPaths) {
            CSBmpUtil.recycleBitmaps(cSMyPath2.getBitmap());
        }
        mTouchPaths.clear();
        mBackPaths.clear();
        OnBackStatusListener onBackStatusListener = mListener;
        if (onBackStatusListener != null) {
            onBackStatusListener.forwardDisable();
            mListener.backDisable();
        }
    }

    private void dispatchPathTouch(int i, int i2, int i3) {
        int i4 = 0;
        int i5 = 0;
        if (mImageWidth <= 0 || mImageHeight <= 0 || i2 < mImageRect.left || i2 > mImageRect.right || i3 < mImageRect.top || i3 > mImageRect.bottom) {
            return;
        }
        float f = (float) (mImageRect.right - mImageRect.left) / mImageWidth;
        int i6 = (int) ((i2 - mImageRect.left) / f);
        int i7 = (int) ((i3 - mImageRect.top) / f);
        if (mode == Mode.CUSTOM) {
            setCustomMasoicBitmap(i6, i7);
        }
        float f2 = 0.0f;
        if (i == 0) {
            downX = i6;
            downY = i7;
            curX = i6;
            curY = i7;
            isdistanceover = false;
            Path path = new Path();
            mTouchPath = path;
            path.moveTo(i6, i7);
            bitmapList = new ArrayList<>();
            angleList = new ArrayList<>();
            angleList.add(0.0f);
            rectList = new ArrayList<>();
            CSMyPath cSMyPath = new CSMyPath();
            myPath = cSMyPath;
            cSMyPath.setPath(mTouchPath);
            myPath.setPaintWidth(mPathWidth);
            myPath.setMode(mode);
            myPath.setAngleLists(angleList);
            if (mode == Mode.ERASE) {
                bitmapList.add(null);
            } else if (mode == Mode.MULTIBITMAP) {
                Bitmap[] bitmapArr = mMultiBmps;
                if (bitmapArr != null) {
                    Collections.addAll(bitmapList, bitmapArr);
                }
                isdistanceover = true;
                int i8 = mPathWidth;
                rectList.add(new Rect(i6 - (i8), i7 - (i8), (i8) + i6, (i8) + i7));
                Log.d("xlb", "down_rect");
            } else {
                bitmapList.add(bitmapStyle);
                int width = bitmapStyle.getWidth();
                int i9 = width / 2;
                int i10 = i6 - i9;
                int height = bitmapStyle.getHeight() / 2;
                int i11 = i7 - height;
                int i12 = i9 + i6;
                int i13 = height + i7;
                int floor = (int) Math.floor(Math.random() * 4.0d);
                if (floor == 0) {
                    i4 = width / 30;
                } else {
                    if (floor == 1) {
                        i5 = width / 30;
                    } else if (floor == 2) {
                        i4 = width / 50;
                    } else {
                        if (floor == 3) {
                            i5 = width / 50;
                        }
                        rectList.add(new Rect(i10, i11, i12, i13));
                    }
                    i10 += i5;
                    i12 += i5;
                    rectList.add(new Rect(i10, i11, i12, i13));
                }
                i10 -= i4;
                i12 -= i4;
                rectList.add(new Rect(i10, i11, i12, i13));
            }
            myPath.setBitmap(bitmapList);
            myPath.setRectList(rectList);
            mTouchPaths.add(myPath);
            multibmp_index = 0;
        } else if (i == 2) {
            Path path2 = mTouchPath;
            if (path2 != null) {
                path2.lineTo(i6, i7);
                if (mode == Mode.CUSTOM) {
                    myPath.getBitmap().add(bitmapStyle);
                }
                int i14 = i6 - downX;
                int i15 = i7 - downY;
                if (i14 > 0 && i15 > 0) {
                    f2 = ((float) ((Math.atan2(i15, i14) / 3.141592653589793d) * 180.0d)) - 90.0f;
                } else if (i14 > 0 && i15 < 0) {
                    f2 = -(((float) ((Math.atan2(Math.abs(i15), i14) / 3.141592653589793d) * 180.0d)) + 90.0f);
                } else if (i14 < 0 && i15 > 0) {
                    f2 = 90.0f - ((float) ((Math.atan2(i15, Math.abs(i14)) / 3.141592653589793d) * 180.0d));
                } else if (i14 < 0 && i15 < 0) {
                    f2 = ((float) ((Math.atan2(Math.abs(i15), Math.abs(i14)) / 3.141592653589793d) * 180.0d)) + 90.0f;
                } else if (i14 != 0 || i15 <= 0) {
                    if (i14 == 0 && i15 < 0) {
                        f2 = 180.0f;
                    } else if (i14 > 0) {
                        f2 = -90.0f;
                    } else if (i14 < 0) {
                        f2 = 90.0f;
                    }
                }
                if (mode == Mode.MULTIBITMAP) {
                    if (Math.abs(i6 - curX) > bmpsdistance || Math.abs(i7 - curY) > bmpsdistance) {
                        curX = i6;
                        curY = i7;
                        isdistanceover = true;
                        int i16 = mPathWidth;
                        myPath.getRectList().add(new Rect(i6 - (i16), i7 - (i16), (i16) + i6, (i16) + i7));
                        Log.d("xlb", "move_rect");
                    } else {
                        isdistanceover = false;
                    }
                } else {
                    Log.i(TAG, "move angle is : " + f2);
                    myPath.getAngleLists().add(f2);
                    int width2 = bitmapStyle.getWidth();
                    int i17 = width2 / 2;
                    int i18 = i6 - i17;
                    int height2 = bitmapStyle.getHeight() / 2;
                    int i19 = i7 - height2;
                    int i20 = i6 + i17;
                    int i21 = height2 + i7;
                    int floor2 = (int) Math.floor(Math.random() * 4.0d);
                    if (floor2 == 0) {
                        i18 -= i17;
                        i20 -= i17;
                    } else if (floor2 == 1) {
                        i18 += i17;
                        i20 += i17;
                    } else if (floor2 == 2) {
                        int i22 = width2 / 4;
                        i18 -= i22;
                        i20 -= i22;
                    } else if (floor2 == 3) {
                        int i23 = width2 / 4;
                        i18 += i23;
                        i20 += i23;
                    }
                    myPath.getRectList().add(new Rect(i18, i19, i20, i21));
                }
            }
        } else if (i == 1) {
            isdistanceover = false;
            if (mTouchPath != null) {
                mBackPaths.clear();
                OnBackStatusListener onBackStatusListener = mListener;
                if (onBackStatusListener != null) {
                    onBackStatusListener.forwardDisable();
                    mListener.backAble();
                }
            }
        }
        OnBackStatusListener onBackStatusListener2 = mListener;
        if (onBackStatusListener2 != null) {
            onBackStatusListener2.backAble();
        }
        if (mTouchPath != null) {
            if (i != 0 || mode == Mode.MULTIBITMAP) {
                drawPathMosaic();
                onDraw();
            }
        }
    }

    private void drawPathMosaic() {
        if (mosaicCanvas != null) {
            System.currentTimeMillis();
            Paint paint = new Paint(1);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setPathEffect(new CornerPathEffect(20.0f));
            paint.setStrokeWidth(mPathWidth);
            paint.setColor(0);
            CSMyPath cSMyPath = myPath;
            if (cSMyPath != null) {
                paint.setStrokeWidth(cSMyPath.getPaintWidth());
                Mode mode = myPath.getMode();
                if (mode == Mode.ERASE) {
                    paint.setColor(0);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                } else {
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                }
                if (mode != Mode.ERASE) {
                    Bitmap bitmap = bitmapStyle;
                    if (bitmap == null || bitmap.isRecycled()) {
                        return;
                    }
                    if (mode == Mode.CUSTOM) {
                        mosaicCanvas.drawPath(myPath.getPath(), paint);
                        Matrix matrix = new Matrix();
                        List<Float> list = angleList;
                        matrix.postRotate(list.get(list.size() - 1).floatValue());
                        Bitmap bitmap2 = bitmapStyle;
                        Bitmap createBitmap = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmapStyle.getHeight(), matrix, true);
                        Canvas canvas = mosaicCanvas;
                        List<Rect> list2 = rectList;
                        canvas.drawBitmap(createBitmap, (Rect) null, list2.get(list2.size() - 1), (Paint) null);
                        createBitmap.recycle();
                        return;
                    } else if (mode == Mode.MULTIBITMAP) {
                        if (isdistanceover) {
                            Bitmap[] bitmapArr = mMultiBmps;
                            int i3 = multibmp_index;
                            Bitmap bitmap3 = bitmapArr[i3];
                            if (i3 < bitmapArr.length - 1) {
                                multibmp_index = i3 + 1;
                            } else {
                                multibmp_index = 0;
                            }
                            if (bitmap3 != null) {
                                mosaicCanvas.drawBitmap(bitmap3, (Rect) null, myPath.getRectList().get(rectList.size() - 1), (Paint) null);
                            }
                            Log.d("xlb", "drawbitmap");
                            return;
                        }
                        return;
                    } else {
                        Bitmap createBitmap2 = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
                        Canvas canvas2 = new Canvas(createBitmap2);
                        paint.setColor(-16711936);
                        canvas2.drawPath(myPath.getPath(), paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas2.drawBitmap(bitmapStyle, 0.0f, 0.0f, paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                        mosaicCanvas.drawBitmap(createBitmap2, 0.0f, 0.0f, paint);
                        createBitmap2.recycle();
                        return;
                    }
                }
                mosaicCanvas.drawPath(myPath.getPath(), paint);
            }
        }
    }

    private void drawpathtoOri(int i) {
        if (mImageWidth <= 0 || mImageHeight <= 0) {
            return;
        }
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setPathEffect(new CornerPathEffect(20.0f));
        paint.setStrokeWidth(mPathWidth);
        paint.setColor(0);
        Canvas canvas = new Canvas(bmOribmpLayer);
        CSMyPath cSMyPath = mTouchPaths.get(i);
        paint.setStrokeWidth(cSMyPath.getPaintWidth());
        Mode mode = cSMyPath.getMode();
        if (mode == Mode.ERASE) {
            paint.setColor(0);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        }
        if (mode == Mode.CUSTOM) {
            List<Bitmap> bitmap = cSMyPath.getBitmap();
            Log.d("xlb", "custom:" + bitmap.size());
            List<Float> angleLists = cSMyPath.getAngleLists();
            List<Rect> rectList = cSMyPath.getRectList();
            for (int i2 = 0; i2 < bitmap.size(); i2++) {
                Bitmap bitmap2 = bitmap.get(i2);
                if (bitmap2 != null && !bitmap2.isRecycled()) {
                    if (i2 == 0) {
                        paint.setColor(0);
                        canvas.drawPath(cSMyPath.getPath(), paint);
                    }
                    Matrix matrix = new Matrix();
                    matrix.postRotate(angleLists.get(i2).floatValue());
                    Bitmap createBitmap = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
                    canvas.drawBitmap(createBitmap, (Rect) null, rectList.get(i2), (Paint) null);
                    createBitmap.recycle();
                }
            }
        } else if (mode == Mode.BITMAP || mode == Mode.BLUR || mode == Mode.NORMAL) {
            List<Bitmap> bitmap3 = cSMyPath.getBitmap();
            Log.d("xlb", mode + ":" + bitmap3.size());
            if (bitmap3 == null || bitmap3.size() <= 0 || bitmap3.get(bitmap3.size() - 1).isRecycled()) {
                return;
            }
            Bitmap createBitmap2 = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap2);
            paint.setColor(-16711936);
            canvas2.drawPath(cSMyPath.getPath(), paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas2.drawBitmap(bitmap3.get(bitmap3.size() - 1), 0.0f, 0.0f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            canvas.drawBitmap(createBitmap2, 0.0f, 0.0f, paint);
            createBitmap2.recycle();
        } else if (mode == Mode.MULTIBITMAP) {
            List<Bitmap> bitmap4 = cSMyPath.getBitmap();
            List<Rect> rectList2 = cSMyPath.getRectList();
            if (rectList2 == null || rectList2.size() <= 0 || bitmap4 == null || bitmap4.size() <= 0) {
                return;
            }
            int i3 = 0;
            for (int i4 = 0; i4 < rectList2.size(); i4++) {
                canvas.drawBitmap(bitmap4.get(i3), (Rect) null, rectList2.get(i4), (Paint) null);
                i3 = i3 < bitmap4.size() - 1 ? i3 + 1 : 0;
            }
        } else {
            canvas.drawPath(cSMyPath.getPath(), paint);
        }
    }

    private void updatePathMosaic() {
        if (mImageWidth <= 0 || mImageHeight <= 0) {
            return;
        }
        System.currentTimeMillis();
        Bitmap bitmap = bmMosaicLayer;
        if (bitmap != null) {
            bitmap.recycle();
        }
        bmMosaicLayer = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setPathEffect(new CornerPathEffect(20.0f));
        paint.setStrokeWidth(mPathWidth);
        paint.setColor(0);
        mosaicCanvas = new Canvas(bmMosaicLayer);
        for (int i = 0; i < mTouchPaths.size(); i++) {
            CSMyPath cSMyPath = mTouchPaths.get(i);
            paint.setStrokeWidth(cSMyPath.getPaintWidth());
            Mode mode = cSMyPath.getMode();
            if (mode == Mode.ERASE) {
                paint.setColor(0);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            } else {
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            }
            if (mode == Mode.CUSTOM) {
                List<Bitmap> bitmap2 = cSMyPath.getBitmap();
                Log.d("xlb", "custom:" + bitmap2.size());
                List<Float> angleLists = cSMyPath.getAngleLists();
                List<Rect> rectList = cSMyPath.getRectList();
                for (int i2 = 0; i2 < bitmap2.size(); i2++) {
                    Bitmap bitmap3 = bitmap2.get(i2);
                    if (bitmap3 != null && !bitmap3.isRecycled()) {
                        if (i2 == 0) {
                            paint.setColor(0);
                            mosaicCanvas.drawPath(cSMyPath.getPath(), paint);
                        }
                        Matrix matrix = new Matrix();
                        matrix.postRotate(angleLists.get(i2));
                        Bitmap createBitmap = Bitmap.createBitmap(bitmap3, 0, 0, bitmap3.getWidth(), bitmap3.getHeight(), matrix, true);
                        mosaicCanvas.drawBitmap(createBitmap, (Rect) null, rectList.get(i2), (Paint) null);
                        createBitmap.recycle();
                    }
                }
            } else if (mode == Mode.BITMAP || mode == Mode.BLUR || mode == Mode.NORMAL) {
                List<Bitmap> bitmap4 = cSMyPath.getBitmap();
                Log.d("xlb", mode + ":" + bitmap4.size());
                if (!bitmap4.isEmpty() && !bitmap4.get(bitmap4.size() - 1).isRecycled()) {
                    Bitmap createBitmap2 = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(createBitmap2);
                    paint.setColor(-16711936);
                    canvas.drawPath(cSMyPath.getPath(), paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    canvas.drawBitmap(bitmap4.get(bitmap4.size() - 1), 0.0f, 0.0f, paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                    mosaicCanvas.drawBitmap(createBitmap2, 0.0f, 0.0f, paint);
                    createBitmap2.recycle();
                }
            } else if (mode == Mode.MULTIBITMAP) {
                List<Bitmap> bitmap5 = cSMyPath.getBitmap();
                List<Rect> rectList2 = cSMyPath.getRectList();
                if (rectList2 != null && !rectList2.isEmpty() && bitmap5 != null && !bitmap5.isEmpty()) {
                    int i3 = 0;
                    for (int i4 = 0; i4 < rectList2.size(); i4++) {
                        mosaicCanvas.drawBitmap(bitmap5.get(i3), (Rect) null, rectList2.get(i4), (Paint) null);
                        i3 = i3 < bitmap5.size() - 1 ? i3 + 1 : 0;
                    }
                }
            } else {
                mosaicCanvas.drawPath(cSMyPath.getPath(), paint);
            }
        }
    }

    public boolean getForwardStatus() {
        return !mBackPaths.isEmpty();
    }

    public boolean getBackStatus() {
        return !mTouchPaths.isEmpty();
    }

    protected void onDraw() {
        if (ori_bitmap != null) {
            mainCanvas.drawBitmap(bmOribmpLayer, (Rect) null, mImageRect, (Paint) null);
        }
        Bitmap bitmap = bmMosaicLayer;
        if (bitmap != null) {
            mainCanvas.drawBitmap(bitmap, (Rect) null, mImageRect, (Paint) null);
        }
        ResultBmpListener resultBmpListener = mResultBmpListener;
        if (resultBmpListener != null) {
            resultBmpListener.onReturnResultBmp(bmBaseLayer);
        }
    }

    public boolean save() {
        if (bmMosaicLayer == null) {
            return false;
        }
        Bitmap createBitmap = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawBitmap(bmMosaicLayer, 0.0f, 0.0f, (Paint) null);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(outPath);
            createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
            byteArrayOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "failed to write image content");
            return false;
        }
    }

    public Bitmap getEditResult() {
        if (bmMosaicLayer == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bmMosaicLayer, 0.0f, 0.0f, (Paint) null);
        canvas.save();
        return createBitmap;
    }

    private Bitmap getGridMosaic() {
        int i;
        int i2 = mImageWidth;
        if (i2 <= 0 || (i = mImageHeight) <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        int ceil = (int) Math.ceil((double) mImageWidth / mGridWidth);
        int ceil2 = (int) Math.ceil((double) mImageHeight / mGridWidth);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        for (int i3 = 0; i3 < ceil; i3++) {
            for (int i4 = 0; i4 < ceil2; i4++) {
                int i5 = mGridWidth;
                int i6 = i5 * i3;
                int i7 = i5 * i4;
                int i8 = i5 + i6;
                int i9 = mImageWidth;
                if (i8 > i9) {
                    i8 = i9;
                }
                int i10 = mGridWidth + i7;
                int i11 = mImageHeight;
                if (i10 > i11) {
                    i10 = i11;
                }
                int pixel = bmBaseLayer.getPixel(i6, i7);
                Rect rect = new Rect(i6, i7, i8, i10);
                paint.setColor(pixel);
                canvas.drawRect(rect, paint);
            }
        }
        saveBitmap("ahahah1.png", createBitmap);
        return createBitmap;
    }

    @Override // com.baiwang.libuiinstalens.masicview.CSActionGesture
    public void actionTouch(MotionEvent motionEvent, float f, float f2, float f3) {
        dispatchPathTouch(motionEvent.getAction(), (int) (f * ori_bitmap.getWidth()), (int) (f2 * ori_bitmap.getHeight()));
    }

    @Override // com.baiwang.libuiinstalens.masicview.CSActionGesture
    public void actionUndo() {
        if (!mTouchPaths.isEmpty()) {
            List<CSMyPath> list = mBackPaths;
            List<CSMyPath> list2 = mTouchPaths;
            list.add(list2.remove(list2.size() - 1));
            if (mListener != null) {
                if (getBackStatus()) {
                    mListener.backAble();
                } else {
                    mListener.backDisable();
                }
                if (getForwardStatus()) {
                    mListener.forwardAble();
                } else {
                    mListener.forwardDisable();
                }
            }
            updatePathMosaic();
            onDraw();
        }
    }

    @Override // com.baiwang.libuiinstalens.masicview.CSActionGesture
    public void actionRedo() {
        if (!mBackPaths.isEmpty()) {
            List<CSMyPath> list = mTouchPaths;
            List<CSMyPath> list2 = mBackPaths;
            list.add(list2.remove(list2.size() - 1));
            if (mListener != null) {
                if (getBackStatus()) {
                    mListener.backAble();
                } else {
                    mListener.backDisable();
                }
                if (getForwardStatus()) {
                    mListener.forwardAble();
                } else {
                    mListener.forwardDisable();
                }
            }
            updatePathMosaic();
            onDraw();
        }
    }

    @Override // com.baiwang.libuiinstalens.masicview.CSSmearMaskPath
    public void actionEraser() {
        setMode(Mode.ERASE);
    }
}
