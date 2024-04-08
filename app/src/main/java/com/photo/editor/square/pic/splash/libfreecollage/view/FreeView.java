package com.photo.editor.square.pic.splash.libfreecollage.view;

import static com.sky.testproject.Opcodes.GETFIELD;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ImageUtils;

import com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity;
import com.photo.editor.square.pic.splash.libfreecollage.frame.resource.FreeFrameBorderRes;
import com.photo.editor.square.pic.splash.libfreecollage.res.BgScaleType;
import com.photo.editor.square.pic.splash.libfreecollage.res.ComposeInfo_Free;
import com.photo.editor.square.pic.splash.libfreecollage.resource.collage.ComposeLayoutInfo_Free;
import com.photo.editor.square.pic.splash.libfreecollage.resource.collage.ComposeManager_Free;
import com.photo.editor.square.pic.splash.libfreecollage.resource.collage.FrameLayoutInfo_Free;
import com.sky.testproject.R;
import com.winflag.Utils.Data_Change;
import com.winflag.libcollage.resource.TemplateRes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.picspool.instafilter.GPUFilter;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.border.res.DMWBBorderRes;
import com.picspool.lib.collagelib.LibDMBitmapInfo;
import com.picspool.lib.filter.cpu.normal.FastBlurFilter;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.sticker.core.DMStickerBMRenderable;
import com.picspool.lib.sticker.drawonview.DMStickerCanvasView;
import com.picspool.lib.sticker.util.DMStickerStateCallback;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.view.image.DMIgnoreRecycleImageView;

/* loaded from: classes2.dex */
public class FreeView extends RelativeLayout implements DMStickerStateCallback {
    RelativeLayout CollageLayout;
    int KMaxPix;
    private Bitmap backgroundBitmap;
    public int backgroundColor;
    public boolean bgIsBitmap;
    private Map<Integer, LibDMBitmapInfo> bitmapInfosMaps;
    private Map<Integer, Bitmap> bitmaps;
    public List<Bitmap> bmps;
    ImageView borderView;
    int composeIndex;
    private GPUFilterRes currentFilterRes;
    int finalRadius;
    Bitmap foregroundBitmap;
    public int imagecount;
    public Boolean imgExchanger;
    public DMIgnoreRecycleImageView img_bg;
    ImageView img_fg;
    FrameLayout imgvwlayout;
    boolean isShowShadow;
    public OnViewItemClickListener listener;
    private Drawable mBackgroundDrawable;
    public TemplateRes mComposeInfo;
    Context mContext;
    private int mCropSize;
    DMSticker mCurrentFace;
    DMStickerBMRenderable mCurrentRenderable;
    private FramesViewProcess mFrameView;
    int mHeight;
    int mHeight1;
    Bitmap mProcessedBitmap;
    private Bitmap mResourceBmp;
    int mRotaiton;
    int mWidth;
    int mWidth1;
    public Bitmap m_vOriginalBitmap;
    public String m_vOriginalfilename;
    public String[] m_vfilenames;
    public int m_vwCount;
    int minnerWidth;
    int mouterWidth;
    public onViewFreePhotoEditorBarClickListener photoEditorBarClickListener;
    Bitmap picBitmap;
    private float radius;
    DMStickerCanvasView sfcView_faces;
    public List<Bitmap> src_bitmaps_list;
    private List<Uri> src_uris_list;
    private List<Bitmap> stickerBitmapList;
    private DMWBBorderRes stickerBorderRes;
    private List<DMStickerStateCallback> stickerStateCallSpreaders;
    private FreeFrameBorderRes tBorderRes;
    int viewWidth;

    /* loaded from: classes2.dex */
    public interface OnItemClickListener {
        void ItemClick(View view, String str);
    }

    /* loaded from: classes2.dex */
    public interface OnItemLongClickListener {
        void ItemLongClick(View view, int i, String str);
    }

    /* loaded from: classes2.dex */
    public interface OnViewItemClickListener {
        void onCollageViewItemClick(View view, int i, int i2, int i3, int i4, int i5);
    }

    /* loaded from: classes2.dex */
    public interface onOutputImageListener {
        void onOutputImageFinish(Bitmap bitmap);
    }

    /* loaded from: classes2.dex */
    public interface onViewFreePhotoEditorBarClickListener {
        void addViewFreePhotoEditorBar(Bitmap bitmap, Uri uri);

        void removeViewFreePhotoEditorBar();
    }

    public static int getCollageCropSize(int i) {
        switch (i) {
            case 1:
                return 960;
            case 2:
                return 800;
            case 3:
                return 700;
            case 4:
                return 600;
            case 5:
                return 520;
            case 6:
                return 460;
            case 7:
                return 450;
            case 8:
                return 430;
            case 9:
                return 400;
            default:
                return 612;
        }
    }

    public static int getCollageCropSize(int i, int i2) {
        switch (i2) {
            case 1:
                return 960;
            case 2:
                return 800;
            case 3:
                return 700;
            case 4:
                return 600;
            case 5:
                return 520;
            case 6:
                return 460;
            case 7:
                return 450;
            case 8:
                return 430;
            case 9:
                return 400;
            default:
                return 612;
        }
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onImageDown(DMSticker dMSticker) {
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onStickerChanged() {
    }

    public FreeView(Context context) {
        super(context);
        this.radius = 0.0f;
        this.mHeight1 = 720;
        this.mWidth1 = 720;
        this.src_bitmaps_list = null;
        this.src_uris_list = null;
        this.bitmaps = new LinkedHashMap();
        this.imagecount = 1;
        this.imgExchanger = false;
        this.m_vwCount = 9;
        this.backgroundColor = -1;
        this.bgIsBitmap = false;
        this.KMaxPix = 612;
        this.minnerWidth = 0;
        this.mouterWidth = 0;
        this.mRotaiton = 0;
        this.viewWidth = 720;
        this.composeIndex = 0;
        this.isShowShadow = false;
        this.bitmapInfosMaps = new HashMap();
        this.stickerBitmapList = new ArrayList();
        this.mCropSize = 720;
        this.mContext = context;
        initView();
    }

    public FreeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.radius = 0.0f;
        this.mHeight1 = 720;
        this.mWidth1 = 720;
        this.src_bitmaps_list = null;
        this.src_uris_list = null;
        this.bitmaps = new LinkedHashMap();
        this.imagecount = 1;
        this.imgExchanger = false;
        this.m_vwCount = 9;
        this.backgroundColor = -1;
        this.bgIsBitmap = false;
        this.KMaxPix = 612;
        this.minnerWidth = 0;
        this.mouterWidth = 0;
        this.mRotaiton = 0;
        this.viewWidth = 720;
        this.composeIndex = 0;
        this.isShowShadow = false;
        this.bitmapInfosMaps = new HashMap();
        this.stickerBitmapList = new ArrayList();
        this.mCropSize = 720;
        this.mContext = context;
        initView();
    }

    public FreeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.radius = 0.0f;
        this.mHeight1 = 720;
        this.mWidth1 = 720;
        this.src_bitmaps_list = null;
        this.src_uris_list = null;
        this.bitmaps = new LinkedHashMap();
        this.imagecount = 1;
        this.imgExchanger = false;
        this.m_vwCount = 9;
        this.backgroundColor = -1;
        this.bgIsBitmap = false;
        this.KMaxPix = 612;
        this.minnerWidth = 0;
        this.mouterWidth = 0;
        this.mRotaiton = 0;
        this.viewWidth = 720;
        this.composeIndex = 0;
        this.isShowShadow = false;
        this.bitmapInfosMaps = new HashMap();
        this.stickerBitmapList = new ArrayList();
        this.mCropSize = 720;
        this.mContext = context;
        initView();
    }

    public static Bitmap loadBitmapFromView(View view, int i, int i2) {
        Log.v("lb", "LayoutParams width = " + view.getLayoutParams().width + " height = " + view.getLayoutParams().height);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        view.layout(0, 0, i, i2);
        view.draw(canvas);
        return createBitmap;
    }

    public void setCropSize() {
        getCollageCropSize(5);
    }

    public void setItemOnClickListener(OnViewItemClickListener onViewItemClickListener) {
        this.listener = onViewItemClickListener;
    }

    public void setViewFreePhotoEditorBarOnClickListener(onViewFreePhotoEditorBarClickListener onviewfreephotoeditorbarclicklistener) {
        this.photoEditorBarClickListener = onviewfreephotoeditorbarclicklistener;
    }

    public void dispose() {
        for (Bitmap bitmap : this.bitmaps.values()) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        this.bitmaps.clear();
    }

    private void initView() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_free, (ViewGroup) this, true);
        DMIgnoreRecycleImageView dMIgnoreRecycleImageView = (DMIgnoreRecycleImageView) findViewById(R.id.img_bg);
        this.img_bg = dMIgnoreRecycleImageView;
        dMIgnoreRecycleImageView.setBackgroundColor(this.backgroundColor);
        this.CollageLayout = (RelativeLayout) findViewById(R.id.CollageLayout);
        this.m_vfilenames = new String[this.m_vwCount];
        this.imgvwlayout = (FrameLayout) findViewById(R.id.imgvwlayout);
        this.imgvwlayout = (FrameLayout) findViewById(R.id.imgvwlayout);
        this.img_fg = (ImageView) findViewById(R.id.img_fg);
        this.mFrameView = (FramesViewProcess) findViewById(R.id.frame_fv);
        DMStickerCanvasView dMStickerCanvasView = (DMStickerCanvasView) findViewById(R.id.img_facial);
        this.sfcView_faces = dMStickerCanvasView;
        dMStickerCanvasView.startRender();
        this.sfcView_faces.onShow();
        this.sfcView_faces.setStickerCallBack(this);
        this.stickerStateCallSpreaders = new ArrayList();
    }

    public void setBlurBackground(List<Bitmap> list, int i, int i2) {
        Bitmap cropCenterScaleBitmap;
        if (list == null || list.size() <= 0 || i2 < 0 || i2 >= list.size() || (cropCenterScaleBitmap = DMBitmapCrop.cropCenterScaleBitmap(list.get(i2), 400, 400)) == null || cropCenterScaleBitmap.isRecycled()) {
            return;
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(FastBlurFilter.blur(cropCenterScaleBitmap, i, false));
        this.mBackgroundDrawable = bitmapDrawable;
        setBackgroud(bitmapDrawable);
    }

    private void setBackgroud(Drawable drawable) {
        if (Build.VERSION.SDK_INT < 16) {
            this.img_bg.setBackgroundDrawable(drawable);
        } else {
            setBackground16(this.img_bg, drawable);
        }
    }

    public void resetBackgroud() {
        ColorDrawable colorDrawable = new ColorDrawable(-1);
        if (Build.VERSION.SDK_INT < 16) {
            this.img_bg.setBackgroundDrawable(colorDrawable);
        } else {
            setBackground16(this.img_bg, colorDrawable);
        }
    }

    public void setFilter(GPUFilterRes gPUFilterRes) {
        Map<Integer, LibDMBitmapInfo> map;
        Bitmap bitmap;
        this.currentFilterRes = gPUFilterRes;
        if (gPUFilterRes == null || this.mCurrentFace == null || (map = this.bitmapInfosMaps) == null || map.size() <= 0) {
            return;
        }
        int keyIndex = this.mCurrentFace.getKeyIndex();
        LibDMBitmapInfo libDMBitmapInfo = this.bitmapInfosMaps.get(Integer.valueOf(keyIndex));
        if (libDMBitmapInfo.getUri() == null) {
            return;
        }
        Bitmap image = getImage(libDMBitmapInfo.getUri(), getCollageCropSize(keyIndex + 1));
        Matrix matrix = new Matrix();
        if (libDMBitmapInfo.getRotationDegeree() > 0 || libDMBitmapInfo.getIsHorizontalMirror()) {
            float width = image.getWidth();
            float height = image.getHeight();
            if (libDMBitmapInfo.getIsHorizontalMirror()) {
                float f = width / 2.0f;
                float f2 = height / 2.0f;
                matrix.postScale(1.0f, -1.0f, f, f2);
                matrix.postRotate(180.0f, f, f2);
            }
            if (libDMBitmapInfo.getRotationDegeree() > 0) {
                matrix.postScale(1.0f, -1.0f, width / 2.0f, height / 2.0f);
            }
            Bitmap createBitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
            if (createBitmap != image && !image.isRecycled()) {
                image.recycle();
            }
            Bitmap filterForType = GPUFilter.filterForType(this.mContext, createBitmap, this.currentFilterRes.getFilterType());
            if (createBitmap != filterForType && !createBitmap.isRecycled()) {
                createBitmap.recycle();
            }
            bitmap = filterForType;
        } else {
            bitmap = GPUFilter.filterForType(this.mContext, image, this.currentFilterRes.getFilterType());
            if (bitmap != image && !image.isRecycled()) {
                image.recycle();
            }
        }
        if (bitmap == null) {
            return;
        }
        this.mCurrentFace.setBitmap(bitmap);
        FramesViewProcess framesViewProcess = this.mFrameView;
        if (framesViewProcess != null) {
            framesViewProcess.invalidate();
        }
        this.bitmaps.put(Integer.valueOf(keyIndex), bitmap);
        this.sfcView_faces.invalidate();
    }

    public void setFilterAll(GPUFilterRes gPUFilterRes) {
        Bitmap filterForType;
        this.currentFilterRes = gPUFilterRes;
        if (gPUFilterRes != null) {
            List<DMStickerBMRenderable> stickers = this.sfcView_faces.getStickers();
            int i = 0;
            for (Map.Entry<Integer, LibDMBitmapInfo> entry : this.bitmapInfosMaps.entrySet()) {
                i++;
                LibDMBitmapInfo value = entry.getValue();
                Bitmap image = getImage(value.getUri(), getCollageCropSize(i));
                Matrix matrix = new Matrix();
                if (value.getRotationDegeree() > 0 || value.getIsHorizontalMirror()) {
                    float width = image.getWidth();
                    float height = image.getHeight();
                    if (value.getIsHorizontalMirror()) {
                        float f = width / 2.0f;
                        float f2 = height / 2.0f;
                        matrix.postScale(1.0f, -1.0f, f, f2);
                        matrix.postRotate(180.0f, f, f2);
                    }
                    if (value.getRotationDegeree() > 0) {
                        matrix.postScale(1.0f, -1.0f, width / 2.0f, height / 2.0f);
                    }
                    Bitmap createBitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
                    if (createBitmap != image && !image.isRecycled()) {
                        image.recycle();
                    }
                    filterForType = GPUFilter.filterForType(this.mContext, createBitmap, this.currentFilterRes.getFilterType());
                    if (createBitmap != filterForType && !createBitmap.isRecycled()) {
                        createBitmap.recycle();
                    }
                } else {
                    filterForType = GPUFilter.filterForType(this.mContext, image, this.currentFilterRes.getFilterType());
                    if (filterForType != image && !image.isRecycled()) {
                        image.recycle();
                    }
                }
                if (filterForType == null) {
                    return;
                }
                for (int i2 = 0; i2 < stickers.size(); i2++) {
                    if (stickers.get(i2).getSticker().getKeyIndex() == entry.getKey().intValue() && stickers.get(i2).getSticker().getIsFreePuzzleBitmap()) {
                        stickers.get(i2).getSticker().setBitmap(filterForType);
                    }
                }
                this.bitmaps.put(Integer.valueOf(entry.getKey().intValue()), filterForType);
            }
            this.sfcView_faces.invalidate();
        }
    }

    public void updateGradientBackground() {
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, getWidth(), getHeight());
        this.backgroundColor = 0;
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        setMyViewBackgroud(this.mBackgroundDrawable);
    }

    public void setBackgroundImageBitmap(Bitmap bitmap, boolean z) {
        this.backgroundColor = -1;
        if (this.mBackgroundDrawable != null) {
            this.img_bg.setImageDrawable(null);
            this.mBackgroundDrawable = null;
        }
        this.mBackgroundDrawable = null;
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bgIsBitmap = true;
            this.backgroundBitmap = bitmap;
            if (z) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(this.backgroundBitmap);
                bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                bitmapDrawable.setDither(true);
                bitmapDrawable.setBounds(0, 0, 1280, 1280);
                this.img_bg.setImageDrawable(bitmapDrawable);
                return;
            }
            this.img_bg.setImageBitmap(bitmap);
            return;
        }
        this.bgIsBitmap = false;
        this.img_bg.setImageBitmap(null);
    }

    public void setViewGradientBackground(Drawable drawable) {
        this.backgroundColor = -1;
        Drawable drawable2 = this.mBackgroundDrawable;
        if (drawable2 != null) {
            recycleDrawable(drawable2);
            this.mBackgroundDrawable = null;
        }
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        this.backgroundColor = 0;
        this.mBackgroundDrawable = drawable;
        drawable.setBounds(0, 0, getWidth(), getHeight());
        setMyViewBackgroud(drawable);
    }

    private void setMyViewBackgroud(Drawable drawable) {
        if (Build.VERSION.SDK_INT < 16) {
            this.img_bg.setBackgroundDrawable(drawable);
        } else {
            setMyViewBackgroud(this.img_bg, drawable);
        }
    }

    private void setMyViewBackgroud(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    private void recycleDrawable(Drawable drawable) {
        Bitmap bitmap;
        if (!(drawable instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) drawable).getBitmap()) == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.backgroundColor = -1;
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            recycleDrawable(drawable);
            this.mBackgroundDrawable = null;
        }
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        this.backgroundColor = i;
        this.img_bg.setBackgroundColor(i);
    }

    public void noBg() {
        setBackgroundColor(-1);
    }

    public void addFrame(FreeFrameBorderRes freeFrameBorderRes) {
        if (freeFrameBorderRes == null || freeFrameBorderRes.getName().compareTo("ori") == 0) {
            this.mFrameView.width = 0;
            this.mFrameView.changeRes(null);
        } else {
            this.imgvwlayout.invalidate();
            this.mFrameView.width = getWidth();
            this.mFrameView.height = getHeight();
            this.mFrameView.changeRes(freeFrameBorderRes);
        }
        this.tBorderRes = this.mFrameView.getCurrentRes();
        this.mFrameView.invalidate();
    }

    public void addFrame(FreeFrameBorderRes freeFrameBorderRes, int i, int i2) {
        if (freeFrameBorderRes == null || freeFrameBorderRes.getName().compareTo("ori") == 0) {
            this.mFrameView.width = 0;
            this.mFrameView.changeRes(null);
        } else {
            this.imgvwlayout.invalidate();
            this.mFrameView.width = getWidth();
            this.mFrameView.height = getHeight();
            this.mFrameView.changeRes(freeFrameBorderRes);
        }
        this.tBorderRes = this.mFrameView.getCurrentRes();
        this.mFrameView.invalidate();
    }

    private Bitmap createBgImage(int i) {
        int height = (int) ((i * (getHeight() / getWidth())) + 0.5f);
        if (height == 0) {
            height = i;
        }
        if (this.mBackgroundDrawable != null) {
            Bitmap createBitmap = Bitmap.createBitmap(i, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Rect rect = new Rect();
            rect.left = 0;
            rect.right = i;
            rect.top = 0;
            rect.bottom = height;
            this.mBackgroundDrawable.setBounds(rect);
            this.mBackgroundDrawable.draw(canvas);
            return createBitmap;
        }
        Bitmap bitmap = this.backgroundBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            return createTileBgImage(i);
        }
        Bitmap createBitmap2 = Bitmap.createBitmap(i, height, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        Paint paint = new Paint();
        paint.setColor(this.backgroundColor);
        paint.setDither(false);
        canvas2.drawRect(new Rect(0, 0, i, height), paint);
        return createBitmap2;
    }

    private Bitmap createTileBgImage(int i) {
        int height = (int) ((i * (getHeight() / getWidth())) + 0.5f);
        int width = this.backgroundBitmap.getWidth();
        int height2 = this.backgroundBitmap.getHeight();
        int i2 = ((i + width) - 1) / width;
        int i3 = ((height + height2) - 1) / height2;
        Bitmap createBitmap = Bitmap.createBitmap(i, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        for (int i4 = 0; i4 < i2; i4++) {
            float f = i4 * width;
            canvas.drawBitmap(this.backgroundBitmap, f, 0.0f, (Paint) null);
            for (int i5 = 1; i5 < i3; i5++) {
                canvas.drawBitmap(this.backgroundBitmap, f, i5 * height2, (Paint) null);
            }
        }
        return createBitmap;
    }

    public void getOutputImage(int i, onOutputImageListener onoutputimagelistener) {
        Bitmap bitmap;
        Paint paint = new Paint();
        float f = i;
        int height = (int) (((getHeight() / getWidth()) * f) + 0.5f);
        Bitmap createBgImage = createBgImage(i);
        Bitmap createBitmap = Bitmap.createBitmap(i, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (createBgImage != null) {
            canvas.drawBitmap(createBgImage, 0.0f, 0.0f, paint);
            if (!createBgImage.isRecycled()) {
                createBgImage.recycle();
            }
        }
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        Bitmap resultBitmap = this.sfcView_faces.getResultBitmap();
        canvas.drawBitmap(resultBitmap, new Rect(0, 0, resultBitmap.getWidth(), resultBitmap.getHeight()), new Rect(0, 0, i, height), (Paint) null);
        if (resultBitmap != null && !resultBitmap.isRecycled()) {
            resultBitmap.recycle();
        }
        FramesViewProcess framesViewProcess = this.mFrameView;
        if (framesViewProcess != null && (bitmap = framesViewProcess.getBitmap()) != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, i, height), new Paint());
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        float f2 = height;
        float f3 = f / 800.0f;
        if (f > f2) {
            f3 = f2 / 800.0f;
        }
        try {
            if (Data_Change.getInstance().getBl() != null && Data_Change.getInstance().getBl().getWidth() > 5 && Data_Change.getInstance().getBl().getHeight() > 5) {
                int width = (int) (Data_Change.getInstance().getBl().getWidth() * f3);
                int height2 = (int) (Data_Change.getInstance().getBl().getHeight() * f3);
                Bitmap bl = Data_Change.getInstance().getBl();
                if (bl != null && !bl.isRecycled()) {
                    Bitmap scale = ImageUtils.scale(Data_Change.getInstance().getBl(), width, height2);
                    canvas.drawBitmap(scale, 0.0f, canvas.getHeight() - scale.getHeight(), paint);
                    scale.recycle();
                }
            }
            if (Data_Change.getInstance().getBr() != null && Data_Change.getInstance().getBr().getWidth() > 5 && Data_Change.getInstance().getBr().getHeight() > 5) {
                int width2 = (int) (Data_Change.getInstance().getBr().getWidth() * f3);
                int height3 = (int) (Data_Change.getInstance().getBr().getHeight() * f3);
                Bitmap br = Data_Change.getInstance().getBr();
                if (br != null && !br.isRecycled()) {
                    Bitmap scale2 = ImageUtils.scale(Data_Change.getInstance().getBr(), width2, height3);
                    canvas.drawBitmap(scale2, canvas.getWidth() - scale2.getWidth(), canvas.getHeight() - scale2.getHeight(), paint);
                    scale2.recycle();
                }
            }
            if (Data_Change.getInstance().getTl() != null && Data_Change.getInstance().getTl().getWidth() > 5 && Data_Change.getInstance().getTl().getHeight() > 5) {
                int width3 = (int) (Data_Change.getInstance().getTl().getWidth() * f3);
                int height4 = (int) (Data_Change.getInstance().getTl().getHeight() * f3);
                Bitmap tl = Data_Change.getInstance().getTl();
                if (tl != null && !tl.isRecycled()) {
                    Bitmap scale3 = ImageUtils.scale(Data_Change.getInstance().getTl(), width3, height4);
                    canvas.drawBitmap(scale3, 0.0f, 0.0f, paint);
                    scale3.recycle();
                }
            }
            if (Data_Change.getInstance().getTr() != null && Data_Change.getInstance().getTr().getWidth() > 5 && Data_Change.getInstance().getTr().getHeight() > 5) {
                int width4 = (int) (Data_Change.getInstance().getTr().getWidth() * f3);
                int height5 = (int) (Data_Change.getInstance().getTr().getHeight() * f3);
                Bitmap tr = Data_Change.getInstance().getTr();
                if (tr != null && !tr.isRecycled()) {
                    Bitmap scale4 = ImageUtils.scale(Data_Change.getInstance().getTr(), width4, height5);
                    canvas.drawBitmap(scale4, canvas.getWidth() - scale4.getWidth(), 0.0f, paint);
                    scale4.recycle();
                }
            }
        } catch (Exception unused) {
        }
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getHeight());
            setMyViewBackgroud(this.mBackgroundDrawable);
        }
        if (onoutputimagelistener != null) {
            onoutputimagelistener.onOutputImageFinish(createBitmap);
        }
    }

    public void restCollageViewAndClearBitmap() {
        Map<Integer, Bitmap> map = this.bitmaps;
        if (map != null) {
            for (Bitmap bitmap : map.values()) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
            this.bitmaps.clear();
        }
        DMIgnoreRecycleImageView dMIgnoreRecycleImageView = this.img_bg;
        if (dMIgnoreRecycleImageView != null) {
            dMIgnoreRecycleImageView.setImageBitmap(null);
        }
        Bitmap bitmap2 = this.backgroundBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.backgroundBitmap.recycle();
        }
        this.backgroundBitmap = null;
        Bitmap bitmap3 = this.mProcessedBitmap;
        if (bitmap3 != null) {
            if (!bitmap3.isRecycled()) {
                this.mProcessedBitmap.recycle();
            }
            this.mProcessedBitmap = null;
        }
        if (this.mBackgroundDrawable instanceof BitmapDrawable) {
            if (Build.VERSION.SDK_INT < 16) {
                this.img_bg.setBackgroundDrawable(null);
            } else {
                setBackground16(this.img_bg, null);
            }
            recycleDrawable(this.mBackgroundDrawable);
        }
        Bitmap bitmap4 = this.picBitmap;
        if (bitmap4 != null) {
            if (!bitmap4.isRecycled()) {
                this.picBitmap.recycle();
            }
            this.picBitmap = null;
        }
        Bitmap bitmap5 = this.m_vOriginalBitmap;
        if (bitmap5 != null) {
            if (!bitmap5.isRecycled()) {
                this.m_vOriginalBitmap.recycle();
            }
            this.m_vOriginalBitmap = null;
        }
        if (this.borderView != null) {
            this.borderView = null;
        }
        DMStickerCanvasView dMStickerCanvasView = this.sfcView_faces;
        if (dMStickerCanvasView != null) {
            dMStickerCanvasView.clearStickers();
        }
        this.mFrameView.dispose();
        noBg();
    }

    public void setBackgroundImageBitmap(Bitmap bitmap) {
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable instanceof BitmapDrawable) {
            recycleDrawable(drawable);
        }
        if (bitmap != null) {
            this.bgIsBitmap = true;
            this.backgroundBitmap = bitmap;
            if (bitmap != null) {
                this.img_bg.setImageBitmap(createXORImage(bitmap, createTileBgImage(1024)));
                return;
            }
            return;
        }
        this.bgIsBitmap = false;
        setBackgroundColor(-1);
    }

    public Bitmap createXORImage(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.argb(240, 255, 255, 255));
        paint.setDither(false);
        Canvas canvas = new Canvas(createBitmap);
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public void setBitmapList(List<Bitmap> list, List<Uri> list2) {
        this.src_bitmaps_list = list;
        this.src_uris_list = list2;
        for (int i = 0; i < list.size(); i++) {
            this.bitmaps.put(Integer.valueOf(i), list.get(i));
        }
        int i2 = 0;
        for (Uri uri : list2) {
            LibDMBitmapInfo libDMBitmapInfo = new LibDMBitmapInfo();
            libDMBitmapInfo.setIsHorizontalMirror(false);
            libDMBitmapInfo.setRotationDegree(0);
            libDMBitmapInfo.setUri(uri);
            this.bitmapInfosMaps.put(Integer.valueOf(i2), libDMBitmapInfo);
            i2++;
        }
    }

    public List<Bitmap> getSrc_bitmaps_list() {
        return this.src_bitmaps_list;
    }

    /* renamed from: com.photo.editor.square.pic.splash.libfreecollage.view.FreeView$1 */
    /* loaded from: classes2.dex */
    static /* synthetic */ class C20771 {

        /* renamed from: $SwitchMap$com$photo$editor$square$pic$splash$libfreecollage$res$BgScaleType */
        static final /* synthetic */ int[] f1597x7140d3b7;

        static {
            int[] iArr = new int[BgScaleType.values().length];
            f1597x7140d3b7 = iArr;
            try {
                iArr[BgScaleType.BG_11.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1597x7140d3b7[BgScaleType.BG_54.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1597x7140d3b7[BgScaleType.BG_NOFG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void setBackground(ComposeInfo_Free composeInfo_Free, BgScaleType bgScaleType) {
        Bitmap imageFromAssetsFile;
        if (composeInfo_Free != null) {
            int i = C20771.f1597x7140d3b7[bgScaleType.ordinal()];
            if (i == 1) {
                imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(getResources(), composeInfo_Free.getBackgroundName_1_1());
            } else if (i == 2) {
                imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(getResources(), composeInfo_Free.getBackgroundName_5_4());
            } else {
                imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(getResources(), composeInfo_Free.getBackgroundName_1_1());
            }
            setBackgroundImageBitmap(imageFromAssetsFile, false);
        }
    }

    public void doRotation(float f) {
        DMSticker dMSticker = this.mCurrentFace;
        if (dMSticker != null) {
            int keyIndex = dMSticker.getKeyIndex();
            Map<Integer, Bitmap> map = this.bitmaps;
            if (map == null || map.size() < keyIndex) {
                return;
            }
            try {
                Bitmap bitmap = this.bitmaps.get(Integer.valueOf(keyIndex));
                if (bitmap == null) {
                    return;
                }
                if (this.bitmapInfosMaps.size() >= keyIndex) {
                    LibDMBitmapInfo libDMBitmapInfo = this.bitmapInfosMaps.get(Integer.valueOf(keyIndex));
                    if (libDMBitmapInfo.getRotationDegeree() == 0) {
                        libDMBitmapInfo.setRotationDegree(GETFIELD);
                    } else {
                        libDMBitmapInfo.setRotationDegree(0);
                    }
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(f, bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f);
                Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if (this.src_bitmaps_list.get(keyIndex) != bitmap && createBitmap != bitmap && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                if (createBitmap == null) {
                    return;
                }
                this.mCurrentFace.setBitmap(createBitmap);
                this.mCurrentFace.width = createBitmap.getWidth();
                this.mCurrentFace.height = createBitmap.getHeight();
                if (this.mCurrentRenderable != null) {
                    this.mCurrentRenderable.width = createBitmap.getWidth();
                    this.mCurrentRenderable.height = createBitmap.getHeight();
                }
                this.bitmaps.put(Integer.valueOf(keyIndex), createBitmap);
                this.sfcView_faces.invalidate();
            } catch (Exception unused) {
            }
        }
    }

    public void doReversal(float f) {
        try {
            if (this.mCurrentFace != null) {
                int keyIndex = this.mCurrentFace.getKeyIndex();
                Bitmap bitmap = this.bitmaps.get(Integer.valueOf(keyIndex));
                if (bitmap != null && !bitmap.isRecycled()) {
                    LibDMBitmapInfo libDMBitmapInfo = this.bitmapInfosMaps.get(Integer.valueOf(keyIndex));
                    if (libDMBitmapInfo.getIsHorizontalMirror()) {
                        libDMBitmapInfo.setIsHorizontalMirror(false);
                    } else {
                        libDMBitmapInfo.setIsHorizontalMirror(true);
                    }
                    Matrix matrix = new Matrix();
                    float width = bitmap.getWidth() / 2.0f;
                    float height = bitmap.getHeight() / 2.0f;
                    matrix.postScale(1.0f, -1.0f, width, height);
                    matrix.postRotate(f, width, height);
                    Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    if (this.src_bitmaps_list == null) {
                        return;
                    }
                    if (this.src_bitmaps_list.get(keyIndex) != bitmap && createBitmap != bitmap && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    if (createBitmap == null) {
                        return;
                    }
                    this.mCurrentFace.setBitmap(createBitmap);
                    this.bitmaps.put(Integer.valueOf(keyIndex), createBitmap);
                    this.sfcView_faces.invalidate();
                }
            }
        } catch (Exception unused) {
        }
    }

    public Bitmap getSelBitmap() {
        DMSticker dMSticker = this.mCurrentFace;
        if (dMSticker != null) {
            return dMSticker.getBitmap();
        }
        return null;
    }

    public void setSelViewBitmap(Bitmap bitmap) {
        if (bitmap == null || this.mCurrentFace == null || bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.mCurrentFace.setBitmap(bitmap);
        FramesViewProcess framesViewProcess = this.mFrameView;
        if (framesViewProcess != null) {
            framesViewProcess.invalidate();
        }
        this.bitmaps.put(Integer.valueOf(this.mCurrentFace.getKeyIndex()), bitmap);
        this.sfcView_faces.invalidate();
    }

    public void addSticker(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        DMSticker dMSticker = new DMSticker(this.viewWidth);
        dMSticker.setBitmap(bitmap);
        float width = (getWidth() / 3.0f) / dMSticker.getWidth();
        Matrix matrix = new Matrix();
        Matrix matrix2 = new Matrix();
        Matrix matrix3 = new Matrix();
        matrix3.postScale(width, width);
        matrix2.postTranslate(getWidth() / 3.0f, getHeight() / 3.0f);
        this.stickerBitmapList.add(bitmap);
        this.sfcView_faces.addSticker(dMSticker, matrix, matrix2, matrix3);
        this.sfcView_faces.cancelSelected();
        this.sfcView_faces.invalidate();
    }

    public void addForeBackGround(ComposeInfo_Free composeInfo_Free, int i, int i2, BgScaleType bgScaleType) {
        Bitmap bitmap = null;
        if (composeInfo_Free != null) {
            int i3 = C20771.f1597x7140d3b7[bgScaleType.ordinal()];
            if (i3 == 1) {
                bitmap = DMBitmapUtil.getImageFromAssetsFile(getResources(), composeInfo_Free.getForegroundName_1_1());
            } else if (i3 == 2) {
                bitmap = DMBitmapUtil.getImageFromAssetsFile(getResources(), composeInfo_Free.getForegroundName_5_4());
            } else if (i3 != 3) {
                bitmap = DMBitmapUtil.getImageFromAssetsFile(getResources(), composeInfo_Free.getForegroundName_1_1());
            }
            this.sfcView_faces.setForeGroundBitmap(bitmap, i, i2);
            this.sfcView_faces.invalidate();
        } else if (bgScaleType == BgScaleType.BG_NOFG) {
            this.sfcView_faces.setForeGroundBitmap(null, i, i2);
            this.sfcView_faces.invalidate();
        }
    }

    public void changeToFreeCollage(int i, int i2) {
        float f = 0;
        float f2 = 0;
        this.mHeight = i2;
        this.mWidth = i;
        float f3 = i / 306.0f;
        float f4 = i2 / 306.0f;
        ComposeManager_Free composeManager_Free = new ComposeManager_Free();
        int size = this.bitmapInfosMaps.size();
        String str = "luca";
        Log.i("luca", "changeToFreeCollage bmNum:" + size);
        List<FrameLayoutInfo_Free> itemsOfPhotoNumber = composeManager_Free.itemsOfPhotoNumber(ComposeManager_Free.FreeComposeType.COMPOSE_11, size);
        Iterator<Map.Entry<Integer, Bitmap>> it2 = this.bitmaps.entrySet().iterator();
        int i3 = 0;
        while (it2.hasNext()) {
            Map.Entry<Integer, Bitmap> next = it2.next();
            if (this.composeIndex > itemsOfPhotoNumber.size()) {
                return;
            }
            ComposeLayoutInfo_Free composeLayoutInfo_Free = itemsOfPhotoNumber.get(this.composeIndex).layoutInfoes.get(i3);
            DMSticker dMSticker = new DMSticker(true, next.getKey().intValue());
            dMSticker.setIsShowShadow(this.isShowShadow);
            if (next.getValue() == null) {
                return;
            }
            dMSticker.setBitmap(next.getValue());
            dMSticker.borderColor = -1;
            float dip2px = DMScreenInfoUtil.dip2px(getContext(), 6.0f);
            dMSticker.marginBottom = dip2px;
            dMSticker.marginLeft = dip2px;
            dMSticker.marginRight = dip2px;
            dMSticker.marginTop = dip2px;
            if (dMSticker.getBitmap() != null) {
                float width = dMSticker.getBitmap().getWidth();
                float height = dMSticker.getBitmap().getHeight();
                dMSticker.width = width;
                dMSticker.height = height;
                Matrix matrix = new Matrix();
                Matrix matrix2 = new Matrix();
                Matrix matrix3 = new Matrix();
                float degrees = (float) Math.toDegrees(composeLayoutInfo_Free.rotateRadius);
                matrix.setRotate(degrees);
                List<FrameLayoutInfo_Free> list = itemsOfPhotoNumber;
                int i4 = (int) (composeLayoutInfo_Free.position.x * f3);
                int i5 = (int) (composeLayoutInfo_Free.position.y * f4);
                float f5 = f3;
                float f6 = f4;
                Iterator<Map.Entry<Integer, Bitmap>> it3 = it2;
                int i6 = i3;
                String str2 = str;
                matrix2.setTranslate((i4 - ((int) f)) + DMScreenInfoUtil.dip2px(getContext(), 6.0f), (i5 - ((int) f2)) + DMScreenInfoUtil.dip2px(getContext(), 6.0f));
                RectF rectF = new RectF(0.0f, 0.0f, width, height);
                Matrix matrix4 = new Matrix();
                matrix4.postRotate(degrees, width / 2.0f, height / 2.0f);
                matrix4.mapRect(rectF);
                int i7 = (int) (rectF.right - rectF.left);
                int i8 = (int) (rectF.bottom - rectF.top);
                int dip2px2 = this.mWidth - DMScreenInfoUtil.dip2px(getContext(), 12.0f);
                int dip2px3 = this.mHeight - DMScreenInfoUtil.dip2px(getContext(), 12.0f);
                float f7 = (((this.mWidth / 2.0f) + 0.5f) * composeLayoutInfo_Free.scale) / width;
                do {
                    int i9 = (int) ((i7 / 2) * f7);
                    int i10 = (int) ((i8 / 2) * f7);
                    boolean z = i4 - i9 > 0 && i9 + i4 < dip2px2;
                    boolean z2 = i5 - i10 > 0 && i10 + i5 < dip2px3;
                    if (z && z2) {
                        break;
                    }
                    f7 = (float) (f7 - 0.05d);
                } while (f7 >= 0.1d);
                matrix3.setScale(f7, f7);
                this.sfcView_faces.addSticker(dMSticker, matrix, matrix2, matrix3);
                Log.i(str2, "freeview   before");
                if (this.stickerBorderRes != null) {
                    Log.i(str2, "freeview   stickerBorderRes != null");
                    this.sfcView_faces.addStickerBorder(this.stickerBorderRes);
                }
                this.sfcView_faces.cancelSelected();
                this.sfcView_faces.invalidate();
                i3 = i6 + 1;
                str = str2;
                itemsOfPhotoNumber = list;
                f3 = f5;
                f4 = f6;
                it2 = it3;
            }
        }
    }

    public void ClearOnlyFreePuzzzle() {
        this.sfcView_faces.clearStickersOnlyFreePuzzzle();
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void stickerSelected(DMSticker dMSticker) {
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.stickerSelected(dMSticker);
            }
        }
        DMSticker dMSticker2 = this.mCurrentFace;
        if (dMSticker2 != null && dMSticker2.getIsFreePuzzleBitmap() && dMSticker.getIsFreePuzzleBitmap() && this.mCurrentFace.getKeyIndex() == dMSticker.getKeyIndex()) {
            return;
        }
        this.mCurrentFace = dMSticker;
        DMStickerCanvasView dMStickerCanvasView = this.sfcView_faces;
        if (dMStickerCanvasView != null) {
            Iterator<DMStickerBMRenderable> it2 = dMStickerCanvasView.getStickers().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                DMStickerBMRenderable next = it2.next();
                if (next.getSticker() == this.mCurrentFace) {
                    this.mCurrentRenderable = next;
                    break;
                }
            }
        }
        onViewFreePhotoEditorBarClickListener onviewfreephotoeditorbarclicklistener = this.photoEditorBarClickListener;
        if (onviewfreephotoeditorbarclicklistener != null) {
            onviewfreephotoeditorbarclicklistener.removeViewFreePhotoEditorBar();
        }
        if (this.mCurrentFace.getIsFreePuzzleBitmap()) {
            int keyIndex = this.mCurrentFace.getKeyIndex();
            Log.i("luca", "mCurrentFace.getKeyIndex()   index:" + keyIndex);
            LibDMBitmapInfo libDMBitmapInfo = this.bitmapInfosMaps.get(Integer.valueOf(keyIndex));
            if (libDMBitmapInfo == null || libDMBitmapInfo.getUri() == null || this.photoEditorBarClickListener == null || !this.mCurrentFace.getIsFreePuzzleBitmap()) {
                return;
            }
            Bitmap bitmap = this.mResourceBmp;
            if (bitmap != null) {
                if (!bitmap.isRecycled()) {
                    this.mResourceBmp.recycle();
                }
                this.mResourceBmp = null;
            }
            Bitmap CropItemImage = DMBitmapCrop.CropItemImage(this.mContext, libDMBitmapInfo.getUri(), DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA);
            this.mResourceBmp = CropItemImage;
            this.photoEditorBarClickListener.addViewFreePhotoEditorBar(CropItemImage, libDMBitmapInfo.getUri());
        }
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void editButtonClicked() {
        int indexOf;
        if (this.mCurrentFace != null) {
            this.sfcView_faces.removeCurSelectedSticker();
            Bitmap bitmap = this.mCurrentFace.getBitmap();
            for (int i = 0; i < this.stickerBitmapList.size(); i++) {
                if (bitmap == this.stickerBitmapList.get(i)) {
                    this.stickerBitmapList.remove(bitmap);
                    bitmap.recycle();
                    bitmap = null;
                }
            }
            if (this.mCurrentFace.getIsFreePuzzleBitmap()) {
                LibDMBitmapInfo remove = this.bitmapInfosMaps.remove(Integer.valueOf(this.mCurrentFace.getKeyIndex()));
                if (remove != null && (indexOf = this.src_uris_list.indexOf(remove.getUri())) >= 0) {
                    this.src_uris_list.remove(indexOf);
                    this.src_bitmaps_list.remove(indexOf);
                }
                this.bitmaps.remove(Integer.valueOf(this.mCurrentFace.getKeyIndex()));
            }
            this.mCurrentFace = null;
            this.mCurrentRenderable = null;
        }
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.editButtonClicked();
            }
        }
        ((FreeCollageActivity) this.mContext).afterdeleteSticker();
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void noStickerSelected() {
        this.mCurrentFace = null;
        this.mCurrentRenderable = null;
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.noStickerSelected();
            }
        }
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onDoubleClicked() {
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.onDoubleClicked();
            }
        }
    }

    public int getStickerCount() {
        DMStickerCanvasView dMStickerCanvasView = this.sfcView_faces;
        if (dMStickerCanvasView != null) {
            return dMStickerCanvasView.getStickersCount();
        }
        return 0;
    }

    public int getStickerFreeCount() {
        DMStickerCanvasView dMStickerCanvasView = this.sfcView_faces;
        if (dMStickerCanvasView != null) {
            return dMStickerCanvasView.getStickersCount() - this.sfcView_faces.getStickersNoFreePuzzleCount();
        }
        return 0;
    }

    public DMStickerCanvasView getSfcView_faces() {
        return this.sfcView_faces;
    }

    public void setSfcView_faces(DMStickerCanvasView dMStickerCanvasView) {
        this.sfcView_faces = dMStickerCanvasView;
    }

    public void addStickerStateCallSpreader(DMStickerStateCallback dMStickerStateCallback) {
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            list.add(dMStickerStateCallback);
        }
    }

    public void clearStickerStateCallSpreader() {
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            list.clear();
        }
    }

    public void setBackgroundBitmapDrawable(Drawable drawable) {
        Drawable drawable2 = this.mBackgroundDrawable;
        if ((drawable2 instanceof BitmapDrawable) && drawable2 != drawable) {
            recycleDrawable(drawable2);
        }
        this.img_bg.setImageBitmap(null);
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        this.mBackgroundDrawable = drawable;
        if (Build.VERSION.SDK_INT < 16) {
            this.img_bg.setBackgroundDrawable(drawable);
        } else {
            setBackground16(this.img_bg, drawable);
        }
    }

    private void setBackground16(ImageView imageView, Drawable drawable) {
        imageView.setBackground(drawable);
    }

    public void setShowShadow(boolean z) {
        this.isShowShadow = z;
        this.sfcView_faces.setIsShowShadow(z);
        this.sfcView_faces.invalidate();
    }

    private Bitmap getImage(Uri uri, int i) {
        Cursor query = getContext().getContentResolver().query(uri, null, null, null, null);
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        @SuppressLint("Range") String string = query.getString(query.getColumnIndex("_data"));
        @SuppressLint("Range") String string2 = query.getString(query.getColumnIndex("orientation"));
        query.close();
        if (string != null) {
            Bitmap sampeZoomFromFile = DMBitmapUtil.sampeZoomFromFile(string, i);
            int i2 = 0;
            if (string2 != null && !"".equals(string2)) {
                i2 = Integer.parseInt(string2);
            }
            if (i2 != 0) {
                Matrix matrix = new Matrix();
                int width = sampeZoomFromFile.getWidth();
                int height = sampeZoomFromFile.getHeight();
                matrix.setRotate(i2);
                Bitmap createBitmap = Bitmap.createBitmap(sampeZoomFromFile, 0, 0, width, height, matrix, true);
                if (sampeZoomFromFile != createBitmap && sampeZoomFromFile != null && !sampeZoomFromFile.isRecycled()) {
                    sampeZoomFromFile.recycle();
                }
                return createBitmap;
            }
            return sampeZoomFromFile;
        }
        return null;
    }

    public void setStickerBorderRes(DMWBBorderRes dMWBBorderRes) {
        this.stickerBorderRes = dMWBBorderRes;
        this.sfcView_faces.addStickerBorder(dMWBBorderRes);
        this.sfcView_faces.invalidate();
    }

    public void setComposeIndex(int i) {
        this.composeIndex = i;
    }

    public int getComposeIndex() {
        return this.composeIndex;
    }

    public void setEmptyTextShow(boolean z, int i, int i2) {
        TextView textView = (TextView) findViewById(R.id.tv_empty_show);
        if (z) {
            ColorStateList colorStateList = getResources().getColorStateList(i);
            if (colorStateList != null) {
                textView.setTextColor(colorStateList);
            }
            if (getResources().getColorStateList(i2) != null) {
                textView.setBackgroundResource(i2);
            }
            textView.setVisibility(0);
            return;
        }
        textView.setVisibility(8);
    }
}
