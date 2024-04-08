package com.picspool.snappic.activity;

import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import com.picspool.libfuncview.res.CSBarViewControlListener;
import com.picspool.libsquare.view.CSSquareView;
import com.picspool.snappic.adapter.CSBottomRecyclerViewAdapter;
import com.picspool.snappic.manager.CSBottomBarManager;
import com.picspool.snappic.snap.CSBestDragSnapView;
import com.picspool.snappic.snap.CSBestFrameRes;
import com.picspool.snappic.snap.CSBestKeyboardLayout;
import com.picspool.snappic.snap.CSBestTagBarView;
import com.picspool.snappic.utils.CSBmpIoCaChe;
import com.picspool.snappic.widget.CSBottomBar;
import com.picspool.snappic.widget.CSEJAddBar;
import com.picspool.snappic.widget.CSSceneBar;
import com.picspool.snappic.widget.CSSplashBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.picspool.instatextview.textview.DMConstRelativeLayout;
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.instatextview.textview.DMInstaTextView3;
import com.picspool.lib.activity.DMProcessDialogFragment;
import com.picspool.lib.bitmap.DMAsyncBitmapCropExecute;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.OnBitmapCropListener;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public abstract class CSSnapPicMain2Activity extends AppCompatActivity implements CSBestDragSnapView.OnSnapListener, CSEJAddBar.TopbarItemClickListener, CSBarViewControlListener, CSSquareView.OnGetResultBitmapListener {
    private static final int FUNCBARFILTER = 2;
    private static final int FUNCBARSNAP = 4;
    private static final int FUNCBARSTICKER = 1;
    private static final int FUNCBARTEXT = 3;
    public static final int SQUAREPHOTOSELEC = 49;
    private static final String TAG = "CSSnapPicMain2Activity";
    public static Bitmap oriBitmap;
    private boolean adIsShow;
    private FrameLayout ads;
    private Bitmap compareBitmap;
    private DMConstRelativeLayout constRelativeLayout;
    private int containerWidth;
    protected DMProcessDialogFragment dlg;
    CSBestDragSnapView dragSnapView;
    ImageView editTagImageView;
    private boolean firstReset;
    private CSEJAddBar fzbar;
    private Uri imageUri;
    private ImageView imageView_redo;
    private ImageView imageView_undo;
    protected ImageView imgAdGift;
    private ImageView img_compare;
    InputMethodManager imm;
    private DMInstaTextView instaTextView;
    private DMInstaTextView3 instaTextView3;
    private View ly_back;
    private View ly_compare;
    private View ly_done;
    protected FrameLayout ly_func_container;
    private FrameLayout ly_mianview_container;
    protected View ly_root;
    private CSBottomBar mBottomBar;
    private CSSceneBar mSceneBar;
    private CSSplashBar mSplashBar;
    private int maxSize;
    CSBestKeyboardLayout root_tag_text;
    private Bitmap shareBitmap;
    private CSSnapPicView squareMainView;
    private View square_main_container;
    CSBestFrameRes tagBackgroundRes;
    CSBestTagBarView tagNewBar;
    private View topBar;
    FrameLayout vw_tool;
    private boolean isBottomOperationViewShow = false;
    int animationDuration = 300;
    private int processType = 0;
    private int currentSceneFilterPos = -1;
    private List<String> list_undo = new ArrayList();
    private List<String> list_redo = new ArrayList();
    private int current_funcbarstate = 0;
    private boolean isfzmode = false;
    protected int admobbannerHeigt = 0;
    boolean square = true;
    float imageWHR = -1.0f;
    boolean isBottomShow = false;
    boolean isTagBarShown = false;
    Bitmap snapBitmap = null;

    protected CSBottomBarManager.BottomBarEntity getADData() {
        return null;
    }

    @Override // com.picspool.snappic.snap.CSBestDragSnapView.OnSnapListener
    public void longPressSnap() {
    }

    @Override // com.picspool.snappic.widget.CSEJAddBar.TopbarItemClickListener
    public void onAddClicked() {
    }

    protected void onBottomBarAdClick(Object obj) {
    }

    protected void onGlitchItemClicked() {
    }

    public void onShareImpl(Bitmap bitmap) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_snappic_mainview2);
        CSBmpIoCaChe.removeall(this);
        initViewImpl();
        Intent intent = getIntent();
        Uri data = intent.getData();
        this.imageUri = data;
        if (data == null) {
            this.imageUri = (Uri) intent.getParcelableExtra("uri");
        }
        if (this.imageUri == null) {
            Toast.makeText(this, "data wrong", Toast.LENGTH_LONG).show();
//            finishNOAd();
        }
        initData(this.imageUri);
        this.ads = (FrameLayout) findViewById(R.id.banner_container);
//        AdmobBannerViewManager.getinstance().getAdCloseLiveData().observe(this, new Observer() { // from class: com.picspool.snappic.activity.-$$Lambda$CSSnapPicMain2Activity$kYKT9TvGJWuYit2G_kJEwatMLBY
//            @Override // androidx.lifecycle.Observer
//            public final void onChanged(Object obj) {
//                CSSnapPicMain2Activity.this.lambda$onCreate$0$CSSnapPicMain2Activity((Boolean) obj);
//            }
//        });
    }

    public /* synthetic */ void lambda$onCreate$0$CSSnapPicMain2Activity(Boolean bool) {
        showBannerViewAd();
    }

    private void showBannerViewAd() {
//        if (AdmobConstants.isPositive(AdmobConstants.MAIN_BA_AS) && this.ads.getChildCount() == 0) {
//            boolean showAd = AdmobBannerViewManager.getinstance().showAd(this.ads, "cut_spiral");
//            this.adIsShow = showAd;
//            if (showAd) {
//                return;
//            }
//            AdmobBannerViewManager.getinstance().getAdStateLiveData().observe(this, new Observer<Boolean>() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.1
//                @Override // androidx.lifecycle.Observer
//                public void onChanged(Boolean bool) {
//                    CSSnapPicMain2Activity.this.adIsShow = AdmobBannerViewManager.getinstance().showAd(CSSnapPicMain2Activity.this.ads, "cut_spiral", false);
//                    if (CSSnapPicMain2Activity.this.adIsShow) {
//                        AdmobBannerViewManager.getinstance().getAdStateLiveData().removeObserver(this);
//                    }
//                }
//            });
//        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.mhh.libraryads.libads.adsBaseAdActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        CSBmpIoCaChe.removeall(this);
        super.onDestroy();
    }

    protected void setMainViewbitmap(Bitmap bitmap) {
        Bitmap bitmap2;
        if (bitmap != null && !bitmap.isRecycled() && bitmap != (bitmap2 = oriBitmap)) {
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                oriBitmap.recycle();
                oriBitmap = null;
            }
            oriBitmap = bitmap;
            this.squareMainView.initSetPictureImageBitmap(bitmap);
            float width = bitmap.getWidth() / bitmap.getHeight();
            if (width != this.imageWHR) {
                this.imageWHR = width;
                resetPicWH();
            }
            String str = "PicsCut_" + System.currentTimeMillis() + ".png";
            this.list_undo.add(str);
            this.list_redo.clear();
            CSBmpIoCaChe.putPNG(this, str, oriBitmap);
            this.imageView_undo.setSelected(this.list_undo.size() > 1);
            this.imageView_redo.setSelected(false);
        }
        Bitmap bitmap3 = oriBitmap;
        if (bitmap3 == null || bitmap3.isRecycled()) {
            Toast.makeText(this, "data load error,please try again!", Toast.LENGTH_SHORT).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void undoMainViewbitmap(Bitmap bitmap) {
        Bitmap bitmap2;
        if (bitmap != null && !bitmap.isRecycled() && bitmap != (bitmap2 = oriBitmap)) {
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                oriBitmap.recycle();
                oriBitmap = null;
            }
            oriBitmap = bitmap;
            this.squareMainView.initSetPictureImageBitmap(bitmap);
            float width = bitmap.getWidth() / bitmap.getHeight();
            if (width != this.imageWHR) {
                this.imageWHR = width;
                resetPicWH();
            }
            if (this.list_undo.size() == 1) {
                this.imageView_undo.setSelected(false);
            } else {
                this.imageView_undo.setSelected(true);
            }
            if (this.list_redo.size() == 0) {
                this.imageView_redo.setSelected(false);
            } else {
                this.imageView_redo.setSelected(true);
            }
        }
        Bitmap bitmap3 = oriBitmap;
        if (bitmap3 == null || bitmap3.isRecycled()) {
            Toast.makeText(this, "data load error,please try again!", Toast.LENGTH_SHORT).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUndoClicked() {
        List<String> list = this.list_undo;
        if (list == null || list.size() <= 1) {
            return;
        }
        showProcessDialog();
        List<String> list2 = this.list_undo;
        List<String> list3 = this.list_redo;
        List<String> list4 = this.list_undo;
        list3.add(list4.remove(list4.size() - 1));
        CSBmpIoCaChe.getBitmap(this, list2.get((list2.size() - 1) - 1), new CSBmpIoCaChe.BmpCallback() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.2
            @Override // com.picspool.snappic.utils.CSBmpIoCaChe.BmpCallback
            public void onResult(Bitmap bitmap) {
                CSSnapPicMain2Activity.this.dismissProcessDialog();
                if (bitmap != null) {
                    CSSnapPicMain2Activity.this.undoMainViewbitmap(bitmap);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRedoClicked() {
        List<String> list = this.list_redo;
        if (list == null || list.size() <= 0) {
            return;
        }
        showProcessDialog();
        List<String> list2 = this.list_redo;
        List<String> list3 = this.list_undo;
        List<String> list4 = this.list_redo;
        list3.add(list4.remove(list4.size() - 1));
        CSBmpIoCaChe.getBitmap(this, list2.get(list2.size() - 1), new CSBmpIoCaChe.BmpCallback() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.3
            @Override // com.picspool.snappic.utils.CSBmpIoCaChe.BmpCallback
            public void onResult(Bitmap bitmap) {
                CSSnapPicMain2Activity.this.dismissProcessDialog();
                if (bitmap != null) {
                    CSSnapPicMain2Activity.this.undoMainViewbitmap(bitmap);
                }
            }
        });
    }

    private void initViewImpl() {
        this.ly_root = findViewById(R.id.ly_root_container);
        this.square_main_container = findViewById(R.id.square_main_container);
        this.ly_func_container = (FrameLayout) findViewById(R.id.layout_editor_container);
        this.constRelativeLayout = (DMConstRelativeLayout) findViewById(R.id.ly_const);
        this.ly_mianview_container = (FrameLayout) findViewById(R.id.layout_container);
        this.ly_compare = findViewById(R.id.ly_img_ori);
        this.img_compare = (ImageView) findViewById(R.id.img_ori);
        this.ly_compare.setOnTouchListener(new View.OnTouchListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    CSSnapPicMain2Activity.this.img_compare.setVisibility(View.VISIBLE);
                    return true;
                } else if (motionEvent.getAction() == 1) {
                    CSSnapPicMain2Activity.this.img_compare.setVisibility(View.GONE);
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.topBar = findViewById(R.id.topbar);
        View findViewById = findViewById(R.id.ly_back);
        this.ly_back = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSnapPicMain2Activity.this.onBackImpl();
            }
        });
        View findViewById2 = findViewById(R.id.ly_done);
        this.ly_done = findViewById2;
        findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    if (CSSnapPicMain2Activity.oriBitmap == null || CSSnapPicMain2Activity.oriBitmap.isRecycled() || CSSnapPicMain2Activity.this.mSplashBar == null) {
                        return;
                    }
                    CSSnapPicMain2Activity.this.mSplashBar.shareBitmap();
                } catch (Exception unused) {
                }
            }
        });
        CSEJAddBar cSEJAddBar = new CSEJAddBar(this);
        this.fzbar = cSEJAddBar;
        cSEJAddBar.setTopbarItemClickListener(this);
        turnfzmode(false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(12);
        this.constRelativeLayout.addView(this.fzbar, layoutParams);
        this.constRelativeLayout.bringChildToFront(this.ly_func_container);
        this.imageView_undo = (ImageView) findViewById(R.id.img_undo);
        this.imageView_redo = (ImageView) findViewById(R.id.img_redo);
        findViewById(R.id.view_undo).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSnapPicMain2Activity.this.onUndoClicked();
            }
        });
        findViewById(R.id.view_redo).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSnapPicMain2Activity.this.onRedoClicked();
            }
        });
        CSSnapPicView cSSnapPicView = (CSSnapPicView) findViewById(R.id.squareMainView);
        this.squareMainView = cSSnapPicView;
        cSSnapPicView.setOnGetResultBitmapListener(this);
        this.squareMainView.Stawing(true);
        initSnap();
        CSBottomBar cSBottomBar = (CSBottomBar) findViewById(R.id.bottom_bar);
        this.mBottomBar = cSBottomBar;
        cSBottomBar.setData(getADData());
        this.mBottomBar.getBottomRecyclerViewAdapter().setOnRecyclerViewItemClikListener(new CSBottomRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.9
            @Override // com.picspool.snappic.adapter.CSBottomRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z, boolean z2) {
                if (dMWBRes instanceof CSBottomBarManager.BottomBarEntity) {
                    CSBottomBarManager.BottomBarEntity bottomBarEntity = (CSBottomBarManager.BottomBarEntity) dMWBRes;
                    if (bottomBarEntity.getAd() != null) {
                        CSSnapPicMain2Activity.this.onBottomBarAdClick(bottomBarEntity.getAd());
                        return;
                    }
                }
                CSSnapPicMain2Activity.this.onFunctionClick(dMWBRes);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFunctionClick(DMWBRes dMWBRes) {
        String name = dMWBRes.getName();
        if (((name.hashCode() == -895866265 && name.equals("splash")) ? (char) 0 : (char) 65535) != 0) {
            return;
        }
        onSplashItemClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSplashItemClicked() {
        if (this.mSplashBar == null && oriBitmap != null) {
            resetfunccontainer(false);
            CSSplashBar cSSplashBar = new CSSplashBar(this, oriBitmap);
            this.mSplashBar = cSSplashBar;
            cSSplashBar.setonSplashBarClickListner(new CSSplashBar.onSplashBarClickListner() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.10
                @Override // com.picspool.snappic.widget.CSSplashBar.onSplashBarClickListner
                public void onOk(Bitmap bitmap) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        CSSnapPicMain2Activity.this.setMainViewbitmap(bitmap);
                    }
                    CSSnapPicMain2Activity.this.resetfunccontainer(true);
                }

                @Override // com.picspool.snappic.widget.CSSplashBar.onSplashBarClickListner
                public void onCancel() {
                    CSSnapPicMain2Activity.this.onBackImpl();
                }

                @Override // com.picspool.snappic.widget.CSSplashBar.onSplashBarClickListner
                public void shareBitmap(Bitmap bitmap) {
                    CSSnapPicMain2Activity.oriBitmap = bitmap;
                    CSSnapPicMain2Activity.this.onShareImpl(CSSnapPicMain2Activity.oriBitmap);
                }
            });
            this.ly_func_container.addView(this.mSplashBar);
            return;
        }
        resetfunccontainer(true);
    }

    protected void resetfunccontainer(boolean z) {
        if (this.mSceneBar != null) {
            this.mSceneBar = null;
        }
        if (this.mSplashBar != null) {
            this.mSplashBar = null;
        }
        if (this.tagNewBar != null) {
            this.tagNewBar = null;
        }
        CSBottomBar cSBottomBar = this.mBottomBar;
        if (cSBottomBar != null && cSBottomBar.getBottomRecyclerViewAdapter() != null && z) {
            this.mBottomBar.getBottomRecyclerViewAdapter().setSelectedPos(-1);
        }
        this.mBottomBar.setVisibility(View.VISIBLE);
        this.ly_compare.setVisibility(View.VISIBLE);
        this.ly_func_container.removeAllViews();
        this.vw_tool.removeAllViews();
        this.vw_tool.setVisibility(View.INVISIBLE);
    }

    private void initData(Uri uri) {
        this.maxSize = CSSysConfig.getImageQuality(this);
        cropBitmapImpl(uri);
    }

    private void cropBitmapImpl(Uri uri) {
        DMAsyncBitmapCropExecute.asyncBitmapCrop(this, uri, this.maxSize, new OnBitmapCropListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.11
            @Override // com.picspool.lib.bitmap.OnBitmapCropListener
            public void onBitmapCropFinish(Bitmap bitmap) {
                if (bitmap != null) {
                    Bitmap sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(bitmap, CSSnapPicMain2Activity.this.maxSize);
                    if (sampeZoomFromBitmap != bitmap && sampeZoomFromBitmap != null && !sampeZoomFromBitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    CSSnapPicMain2Activity.this.setMainViewbitmap(sampeZoomFromBitmap);
                    CSSnapPicMain2Activity.this.compareBitmap = sampeZoomFromBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    CSSnapPicMain2Activity.this.img_compare.setImageBitmap(CSSnapPicMain2Activity.this.compareBitmap);
                    HashMap hashMap = new HashMap();
                    hashMap.put("editor_use", "single_show");
//                    FlurryAgent.logEvent("editor_use", hashMap);
                    CSSnapPicMain2Activity.this.onSplashItemClicked();
                }
                CSSnapPicMain2Activity.this.dismissProcessDialog();
            }
        });
    }

    protected void resetPicWH() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.squareMainView.getLayoutParams();
        layoutParams.dimensionRatio = this.imageWHR + ":1";
        this.squareMainView.setLayoutParams(layoutParams);
        if (this.firstReset) {
            return;
        }
        this.firstReset = true;
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.img_compare.getLayoutParams();
        layoutParams2.dimensionRatio = this.imageWHR + ":1";
        this.img_compare.setLayoutParams(layoutParams2);
    }

    public void showProcessDialog() {
        try {
            if (this.dlg != null) {
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                if (beginTransaction != null) {
                    beginTransaction.remove(this.dlg);
                    beginTransaction.addToBackStack(null);
                    beginTransaction.commit();
                }
                this.dlg = null;
            }
            if (this.dlg == null) {
                DMProcessDialogFragment dMProcessDialogFragment = new DMProcessDialogFragment();
                this.dlg = dMProcessDialogFragment;
                dMProcessDialogFragment.setProcessType(this.processType);
                Bundle bundle = new Bundle();
                bundle.putString("text", getResources().getString(R.string.dlg_processing));
                this.dlg.setArguments(bundle);
            }
            this.dlg.show(getSupportFragmentManager(), "process");
        } catch (Exception unused) {
        }
    }

    public void dismissProcessDialog() {
        try {
            if (this.dlg != null) {
                this.dlg.dismissAllowingStateLoss();
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                if (this.dlg != null && beginTransaction != null) {
                    beginTransaction.remove(this.dlg);
                    beginTransaction.addToBackStack(null);
                    beginTransaction.commit();
                }
                this.dlg = null;
            }
        } catch (Exception unused) {
        }
    }

    public void onBackImpl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tips);
        builder.setMessage(R.string.quit_string);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.12
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
//                if (AdmobConstants.isPositive(AdmobConstants.BACK_MAIN_INT_AS)) {
//                    if (!AdmobLevelManager.getinstance().showAd(CSSnapPicMain2Activity.this, new AdListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.12.1
//                        @Override // com.google.android.gms.ads.AdListener
//                        public void onAdClosed() {
//                            CSSnapPicMain2Activity.this.finish();
//                        }
//                    }, "CSSnapPicMain2")) {
//                        CSSnapPicMain2Activity.this.finish();
//                    }
//                } else {
                    CSSnapPicMain2Activity.this.finish();
//                }
                dialogInterface.dismiss();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override // com.picspool.libsquare.view.CSSquareView.OnGetResultBitmapListener
    public void getResultBitmapSuccess(Bitmap bitmap) {
        dismissProcessDialog();
        Bitmap bitmap2 = this.shareBitmap;
        if (bitmap2 != null) {
            if (!bitmap2.isRecycled()) {
                this.shareBitmap.recycle();
            }
            this.shareBitmap = null;
        }
        this.shareBitmap = bitmap;
    }

    private void initSnap() {
        CSBestKeyboardLayout cSBestKeyboardLayout = (CSBestKeyboardLayout) findViewById(R.id.root_tag_text);
        this.root_tag_text = cSBestKeyboardLayout;
        cSBestKeyboardLayout.setOnSizeChangedListener(new CSBestKeyboardLayout.onSizeChangedListener() { // from class: com.picspool.snappic.activity.CSSnapPicMain2Activity.14
            @Override // com.picspool.snappic.snap.CSBestKeyboardLayout.onSizeChangedListener
            public void onBraydenChanged(boolean z, int i, int i2, int i3) {
                if (CSSnapPicMain2Activity.this.tagNewBar != null) {
                    CSSnapPicMain2Activity.this.tagNewBar.resetLayoutParam(i, i2, i3);
                }
            }
        });
        CSBestDragSnapView cSBestDragSnapView = (CSBestDragSnapView) findViewById(R.id.drag_snap_view);
        this.dragSnapView = cSBestDragSnapView;
        cSBestDragSnapView.setOnSnapListener(this);
        this.vw_tool = (FrameLayout) findViewById(R.id.vw_tool);
    }

    public void setEditTagImageViewHeight(int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.editTagImageView.getLayoutParams();
        layoutParams.height = i;
        this.editTagImageView.setLayoutParams(layoutParams);
    }

    @Override // com.picspool.snappic.widget.CSEJAddBar.TopbarItemClickListener
    public void onCancelClicked() {
        removeFunContent();
        resetfunccontainer(true);
    }

    @Override // com.picspool.snappic.widget.CSEJAddBar.TopbarItemClickListener
    public void onOkClicked() {
        CSBestDragSnapView cSBestDragSnapView = this.dragSnapView;
        if (cSBestDragSnapView != null && cSBestDragSnapView.getChildCount() != 0) {
            setMainViewbitmap(this.squareMainView.getSnapMainBitmap(oriBitmap));
        }
        removeFunContent();
        resetfunccontainer(true);
    }

    private void removeFunContent() {
        CSBestDragSnapView cSBestDragSnapView = this.dragSnapView;
        if (cSBestDragSnapView != null) {
            cSBestDragSnapView.removeAllViews();
        }
    }

    private void turnfzmode(boolean z) {
        this.isfzmode = z;
        if (z) {
            this.fzbar.setVisibility(View.VISIBLE);
            this.ly_compare.setVisibility(View.GONE);
            this.fzbar.showAsFunTopBar(true);
            this.topBar.setVisibility(View.GONE);
            return;
        }
        this.fzbar.setVisibility(View.INVISIBLE);
        this.topBar.setVisibility(View.VISIBLE);
    }

    @Override // com.picspool.libfuncview.res.CSBarViewControlListener
    public void onOk(Bitmap bitmap) {
        setMainViewbitmap(bitmap);
        resetfunccontainer(true);
    }

    @Override // com.picspool.libfuncview.res.CSBarViewControlListener
    public void onCancel() {
        resetfunccontainer(true);
    }
}
