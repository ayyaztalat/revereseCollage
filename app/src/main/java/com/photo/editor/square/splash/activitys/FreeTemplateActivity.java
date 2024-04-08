package com.photo.editor.square.splash.activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.magic.video.editor.effect.gallery.view.CSGalleryActivity;
import com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity;
import com.photo.editor.square.pic.splash.libfreecollage.view.FreeView;
import com.photo.editor.square.splash.utils.AdjustInfo;
import com.photo.editor.square.splash.utils.AssBitManage;
import com.photo.editor.square.splash.utils.CSSaveToSDNew;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.photo.editor.square.splash.view.CollageFunView;
import com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView;
import com.photo.editor.square.splash.view.glitch.IL1;
import com.photo.editor.square.splash.view.view.FreeCollageFunView;
import com.photo.editor.square.splash.view.view.FilterView;
import com.photo.editor.square.splash.view.view.BackgroundGroupsItemsObserver;
import com.photo.editor.square.splash.view.view.BackgroundDrawableView;
import com.photo.editor.square.splash.view.view.CustomFrameLayoutImags;
import com.photo.editor.square.splash.view.view.ColorView;
import com.photo.editor.square.splash.view.view.FramesRecyclerView;
import com.photo.editor.square.splash.view.view.bean.BackgroundGroupsItems;
import com.photo.editor.square.splash.view.view.bean.FiltereTypes;
import com.picspool.snappic.activity.CSSysConfig;
import com.sky.testproject.R;
import com.winflag.Utils.Data_Change;
import com.winflag.lib.bitmap.AsyncBitmapsCrop;
import com.winflag.libcollage.activity.LibCollageConfig;
import com.winflag.libcollage.view.TemplateBottomBarView;
import com.winflag.libsquare.res.CSListNaitveAdRes;
import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.output.save.DMSaveDoneListener;
import com.picspool.lib.border.res.DMWBBorderRes;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes2.dex */
public class FreeTemplateActivity extends FreeCollageActivity {
    private static final int REQUEST_ADD_IMG = 3;
    static String TAG = "TemplateActivity";
    private FreeCollageFunView collageFunView;
    private AdjustInfo currentAdjustInfo;
    private AssBitManage.bitBean currentBitBean;
    private GPUImageFilter currentGlitchGpuImageFilter;
    private Disposable disposable;
    private CSExpandableFilterView filterBarView;
    private IL1 il1;
    private FilterView filterView;
    private boolean isSave;
    private boolean isShowSecondFun;
    private String mResname;
    private int position;
    private CSExpandableFilterView singleFilterBarView;

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public CSListNaitveAdRes getListNativeAd() {
        return null;
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void lockeResClicked(String str) {
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onLayoutItemClicked() {
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity, com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LibCollageConfig.maxQuality = CSSysConfig.getImageQuality(this);
        LibCollageConfig.isLowMemery = false;
        this.T_L = (ImageView) findViewById(R.id.image_left_top);
        this.T_R = (ImageView) findViewById(R.id.image_reit_top);
        this.B_L = (ImageView) findViewById(R.id.image_left_bottem);
        this.B_R = (ImageView) findViewById(R.id.image_reit_bottem);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.isSave = false;
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onSpaceItemClicked() {
        showCollageFunView(1);
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onBottomMarginClick() {
        showCollageFunView(2);
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void resetBottomBar() {
        super.resetBottomBar();
        if (this.il1 != null) {
            this.il1 = null;
        }
        if (this.filterView != null) {
            this.filterView = null;
        }
        if (!this.isShowSecondFun) {
            this.ly_single_sub_function.removeAllViews();
        } else if (this.ly_single_sub_function.getChildCount() == 2) {
            this.isShowSecondFun = false;
            this.ly_single_sub_function.removeViewAt(1);
            this.isBottomOperationViewShow = true;
            ChangeViewHeight(100);
        }
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onTemplateBgItemClicked() {
        resetBottomBar();
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Background, true);
        this.isBottomOperationViewShow = true;
        showCollageFunView(0);
    }

    public void onSquareBorderGroupItemClicked() {
        final FramesRecyclerView framesRecyclerView = new FramesRecyclerView(this);
        framesRecyclerView.setIl2(this.stickerborderRes);
        framesRecyclerView.setIl5(new CustomFrameLayoutImags.IL4567123<DMWBRes>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.1
            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f3  reason: avoid collision after fix types in other method */
            public void mo70f3(DMWBRes dMWBRes) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f2 */
            public void mo71f2() {
                FreeTemplateActivity.this.ly_single_sub_function.removeView(framesRecyclerView);
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f1 */
            public void mo72f1() {
                FreeTemplateActivity.this.ly_single_sub_function.removeView(framesRecyclerView);
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f4  reason: avoid collision after fix types in other method */
            public void mo69f4(DMWBRes dMWBRes) {
                if (dMWBRes == null) {
                    return;
                }
                FreeTemplateActivity.this.stickerborderRes = (DMWBBorderRes) dMWBRes;
                FreeTemplateActivity.this.tlView.setStickerBorderRes(FreeTemplateActivity.this.stickerborderRes);
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, DMScreenInfoUtil.dip2px(this, 100.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(framesRecyclerView);
        ChangeViewHeight(100);
        framesRecyclerView.startAnimation(translateAnimation);
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    protected void onAddItemClicked() {
        if (this.uris.size() == 9) {
            Toast.makeText(this, "You can add at most 9 stickers", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, CSGalleryActivity.class);
        intent.putExtra(CSGalleryActivity.MAX_SELECT_PIC_NUMBER_KEY, 9 - this.uris.size());
        intent.putExtra(CSGalleryActivity.SHOW_PEOPLE_TIPS_KEY, false);
        startActivityForResult(intent, 3);
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 3 && i2 == -1) {
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("uris");
            ArrayList arrayList = new ArrayList();
            if (stringArrayListExtra == null) {
                Uri data = intent.getData();
                if (data != null) {
                    arrayList.add(data);
                }
            } else {
                for (int i3 = 0; i3 < stringArrayListExtra.size(); i3++) {
                    arrayList.add(Uri.parse(stringArrayListExtra.get(i3)));
                }
            }
            if (arrayList.size() > 0) {
                showProcessDialog();
                AsyncBitmapsCrop.AsyncBitmapCropExecute(this, arrayList, FreeView.getCollageCropSize(arrayList.size()), new C21182(arrayList));
                return;
            }
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.photo.editor.square.splash.activitys.FreeTemplateActivity$2 */
    /* loaded from: classes2.dex */
    public class C21182 implements AsyncBitmapsCrop.OnBitmapCropListener {
        final /* synthetic */ List val$tempUris;

        @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
        public void onBitmapCriopFaile() {
        }

        @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
        public void onBitmapCropStart() {
        }

        C21182(List list) {
            this.val$tempUris = list;
        }

        @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
        public int onBitmapRequireItemSize(int i) {
            return FreeView.getCollageCropSize(FreeTemplateActivity.this.uris.size() + i + 1);
        }

        @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
        public void onBitmapCropSuccess(final List<Bitmap> list) {
            FreeTemplateActivity freeTemplateActivity = FreeTemplateActivity.this;
            final List list2 = this.val$tempUris;
            freeTemplateActivity.runOnUiThread(new Runnable() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$FreeTemplateActivity$2$Ej99K-HOiyGUYkDaSHBTr9siI7U
                @Override // java.lang.Runnable
                public final void run() {
                    C21182.this.lambda$onBitmapCropSuccess$0$FreeTemplateActivity$2(list2, list);
                }
            });
        }

        public /* synthetic */ void lambda$onBitmapCropSuccess$0$FreeTemplateActivity$2(List list, List list2) {
            if (FreeTemplateActivity.this.uris == null || FreeTemplateActivity.this.mSrcBitmaps == null) {
                if (FreeTemplateActivity.this.uris == null) {
                    FreeTemplateActivity.this.uris = new ArrayList();
                }
                if (FreeTemplateActivity.this.mSrcBitmaps == null) {
                    FreeTemplateActivity.this.mSrcBitmaps = new ArrayList();
                }
                FreeTemplateActivity.this.uris.addAll(list);
                FreeTemplateActivity.this.mSrcBitmaps.addAll(list2);
                FreeTemplateActivity freeTemplateActivity = FreeTemplateActivity.this;
                FreeTemplateActivity.super.onBitmapCropSuccess(freeTemplateActivity.mSrcBitmaps);
                FreeTemplateActivity.this.dismissProcessDialog();
                return;
            }
            FreeTemplateActivity.this.uris.addAll(list);
            FreeTemplateActivity.this.mSrcBitmaps.addAll(list2);
            FreeTemplateActivity freeTemplateActivity2 = FreeTemplateActivity.this;
            FreeTemplateActivity.super.onBitmapCropSuccess(freeTemplateActivity2.mSrcBitmaps);
            FreeTemplateActivity.this.dismissProcessDialog();
        }
    }

    public void onSquareBgGroupItemClicked(String str, List<BackgroundGroupsItems.BackgroundItem> list) {
        Drawable drawable;
        final BackgroundDrawableView backgroundDrawableView = new BackgroundDrawableView(this, list);
        backgroundDrawableView.setIl6(str);
        try {
            if (this.tlView.img_bg.getBackground() instanceof BitmapDrawable) {
                drawable = new BitmapDrawable(getResources(), ((BitmapDrawable) this.tlView.img_bg.getBackground()).getBitmap().copy(Bitmap.Config.ARGB_8888, true));
            } else {
                drawable = this.tlView.img_bg.getBackground();
            }
        } catch (Exception unused) {
            drawable = null;
        }
        if (drawable != null) {
            backgroundDrawableView.setIl2(drawable);
        }
        backgroundDrawableView.setIl5(new CustomFrameLayoutImags.IL4567123<Drawable>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.3
            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f3  reason: avoid collision after fix types in other method */
            public void mo70f3(Drawable drawable2) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f2 */
            public void mo71f2() {
                if (FreeTemplateActivity.this.ly_single_sub_function != null) {
                    try {
                        FreeTemplateActivity.this.ly_single_sub_function.removeView(backgroundDrawableView);
                    } catch (Exception unused2) {
                    }
                }
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f1 */
            public void mo72f1() {
                if (FreeTemplateActivity.this.ly_single_sub_function != null) {
                    try {
                        FreeTemplateActivity.this.ly_single_sub_function.removeView(backgroundDrawableView);
                    } catch (Exception unused2) {
                    }
                }
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f4  reason: avoid collision after fix types in other method */
            public void mo69f4(Drawable drawable2) {
                if (drawable2 != null) {
                    if (drawable2 instanceof BitmapDrawable) {
                        FreeTemplateActivity.this.tlView.setBackgroundBitmapDrawable(new BitmapDrawable(FreeTemplateActivity.this.getResources(), ((BitmapDrawable) drawable2).getBitmap().copy(Bitmap.Config.ARGB_8888, true)));
                    } else {
                        FreeTemplateActivity.this.tlView.setBackgroundBitmapDrawable(drawable2);
                    }
                }
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, DMScreenInfoUtil.dip2px(this, 100.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        if (this.ly_single_sub_function != null) {
            this.ly_single_sub_function.addView(backgroundDrawableView);
        }
        ChangeViewHeight(100);
        backgroundDrawableView.startAnimation(translateAnimation);
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    protected void onFilterItemClickImpl() {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        CSExpandableFilterView cSExpandableFilterView = new CSExpandableFilterView(this);
        this.singleFilterBarView = cSExpandableFilterView;
        cSExpandableFilterView.setOnExpandableFilterViewListener(new CSExpandableFilterView.OnExpandableFilterViewListener() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.4
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
            public void onFilterClick(DMWBRes dMWBRes, int i, int i2, String str) {
                if (dMWBRes instanceof GPUFilterRes) {
                    FreeTemplateActivity.this.tlView.setFilter((GPUFilterRes) dMWBRes);
                }
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
            public void doneView() {
                FreeTemplateActivity.this.resetBottomBar();
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.singleFilterBarView.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 150.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.singleFilterBarView.setLayoutParams(layoutParams);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, dip2px, 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(this.singleFilterBarView);
        this.ly_single_sub_function.setOnClickListener(new View.OnClickListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$FreeTemplateActivity$iBdtkTq3ZAg8ug_jOciLWTd6Z_k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FreeTemplateActivity.this.lambda$onFilterItemClickImpl$0$FreeTemplateActivity(view);
            }
        });
        ChangeViewHeight(DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA);
        this.singleFilterBarView.startAnimation(translateAnimation);
        this.singleFilterBarView.findViewById(R.id.filter_close).setVisibility(View.VISIBLE);
    }

    public /* synthetic */ void lambda$onFilterItemClickImpl$0$FreeTemplateActivity(View view) {
        resetBottomBar();
        this.singleFilterBarView.findViewById(R.id.filter_close).setVisibility(View.GONE);
        this.ly_single_sub_function.setOnClickListener(null);
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onBottomFilterClick() {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Filter, true);
        if (this.filterBarView == null) {
            CSExpandableFilterView cSExpandableFilterView = new CSExpandableFilterView(this);
            this.filterBarView = cSExpandableFilterView;
            cSExpandableFilterView.setOnExpandableFilterViewListener(new CSExpandableFilterView.OnExpandableFilterViewListener() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.5
                @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
                public void onFilterClick(DMWBRes dMWBRes, int i, int i2, String str) {
                    FreeTemplateActivity.this.mCurrentFilterPos = i;
                    if (dMWBRes instanceof GPUFilterRes) {
                        FreeTemplateActivity.this.tlView.setFilterAll((GPUFilterRes) dMWBRes);
                    }
                    FreeTemplateActivity freeTemplateActivity = FreeTemplateActivity.this;
                    freeTemplateActivity.func_filter = "filter_" + dMWBRes.getShowText();
                }

                @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
                public void doneView() {
                    FreeTemplateActivity.this.resetBottomBar();
                }
            });
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.filterBarView.getLayoutParams();
            int dip2px = DMScreenInfoUtil.dip2px(this, 150.0f);
            if (layoutParams == null) {
                layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
            }
            this.filterBarView.setLayoutParams(layoutParams);
        }
        showFunView(getString(R.string.bottom_filter), this.filterBarView, false, DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA);
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onSingleGlitchClick() {
        if (this.il1 != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.GLITCH, false);
            this.currentAdjustInfo = null;
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        Bitmap selBitmap = this.tlView.getSelBitmap();
        if (selBitmap == null) {
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.GLITCH, false);
            ChangeViewHeight(0);
            this.currentAdjustInfo = null;
            return;
        }
        this.currentAdjustInfo = new AdjustInfo(this, selBitmap.copy(Bitmap.Config.ARGB_8888, true));
        this.isBottomOperationViewShow = true;
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.GLITCH, true);
        if (this.il1 == null) {
            IL1 il1 = new IL1(this);
            this.il1 = il1;
            il1.setShowBottomTool(true);
            this.il1.setIl3(new IL1.IL234567() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.6
                @Override // com.photo.editor.square.splash.view.glitch.IL1.IL234567
                /* renamed from: f1 */
                public void mo84f1(Bitmap bitmap) {
                    if (FreeTemplateActivity.this.tlView != null) {
                        FreeTemplateActivity.this.tlView.setSelViewBitmap(bitmap);
                    }
                }

                @Override // com.photo.editor.square.splash.view.glitch.IL1.IL234567
                /* renamed from: f2 */
                public void mo83f2(GPUImageFilter gPUImageFilter) {
                    FreeTemplateActivity.this.currentGlitchGpuImageFilter = gPUImageFilter;
                    Bitmap src = FreeTemplateActivity.this.currentAdjustInfo.getSrc();
                    if (src == null || src.isRecycled()) {
                        return;
                    }
                    FreeTemplateActivity.this.il1.f12(src.copy(Bitmap.Config.ARGB_8888, true));
                }
            });
            this.il1.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(this.il1);
        ChangeViewHeight(100);
        this.il1.startAnimation(translateAnimation);
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onSingleAdjustClick() {
        if (this.filterView != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.ADJUST, false);
            ChangeViewHeight(0);
            this.currentAdjustInfo = null;
            return;
        }
        resetBottomBar();
        Bitmap selBitmap = this.tlView.getSelBitmap();
        if (selBitmap == null) {
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.ADJUST, false);
            ChangeViewHeight(0);
            this.currentAdjustInfo = null;
            return;
        }
        this.currentAdjustInfo = new AdjustInfo(this, selBitmap.copy(Bitmap.Config.ARGB_8888, true));
        this.isBottomOperationViewShow = true;
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.ADJUST, true);
        if (this.filterView == null) {
            FilterView filterView = new FilterView(this) { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.7
                @Override // com.photo.editor.square.splash.view.view.IL26, com.photo.editor.square.splash.view.view.IL29
                public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
                    super.f10(context, frameLayout, frameLayout2);
                    showBottomToolView(true);
                }
            };
            this.filterView = filterView;
            filterView.setIl5(new CustomFrameLayoutImags.IL4567123<FiltereTypes>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.8
                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f2 */
                public void mo71f2() {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f3  reason: avoid collision after fix types in other method */
                public void mo70f3(FiltereTypes filtereTypes) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f1 */
                public void mo72f1() {
                    FreeTemplateActivity.this.resetBottomBar();
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f4  reason: avoid collision after fix types in other method */
                public void mo69f4(FiltereTypes filtereTypes) {
                    if (filtereTypes != null) {
                        FreeTemplateActivity.this.currentAdjustInfo.setAdjust(filtereTypes.getColorMatrix(), filtereTypes.getAdjustImageFilter());
                    } else {
                        FreeTemplateActivity.this.currentAdjustInfo.setAdjust(null, null);
                    }
                    if (FreeTemplateActivity.this.tlView != null) {
                        FreeTemplateActivity.this.tlView.setSelViewBitmap(FreeTemplateActivity.this.currentAdjustInfo.getResult());
                    }
                }
            });
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(12);
            this.filterView.setLayoutParams(layoutParams);
            this.filterView.setColorShowListener(new FilterView.OnColorShowListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$FreeTemplateActivity$uCKYvG_pbIF6bYIUSUdJIvVUtW8
                @Override // com.photo.editor.square.splash.view.view.IL26.OnColorShowListener
                public final void onColorViewShow() {
                    FreeTemplateActivity.this.showColorView();
                }
            });
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(this.filterView);
        ChangeViewHeight(100);
        this.filterView.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showColorView() {
        if (this.currentAdjustInfo == null) {
            return;
        }
        final ColorView colorView = new ColorView(this);
        colorView.setIl5(new CustomFrameLayoutImags.IL4567123<FiltereTypes>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.9
            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f3  reason: avoid collision after fix types in other method */
            public void mo70f3(FiltereTypes filtereTypes) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f2 */
            public void mo71f2() {
                FreeTemplateActivity.this.ly_single_sub_function.removeView(colorView);
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f1 */
            public void mo72f1() {
                FreeTemplateActivity.this.ly_single_sub_function.removeView(colorView);
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f4  reason: avoid collision after fix types in other method */
            public void mo69f4(FiltereTypes filtereTypes) {
                if (filtereTypes != null) {
                    FreeTemplateActivity.this.currentAdjustInfo.setAdjust(filtereTypes.getColorMatrix());
                } else {
                    FreeTemplateActivity.this.currentAdjustInfo.setAdjust(null);
                }
                if (FreeTemplateActivity.this.tlView != null) {
                    FreeTemplateActivity.this.tlView.setSelViewBitmap(FreeTemplateActivity.this.currentAdjustInfo.getResult());
                }
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(12);
        this.ly_single_sub_function.addView(colorView, layoutParams);
        ChangeViewHeight(100);
        colorView.startAnimation(translateAnimation);
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onTextItemClicked() {
        super.onTextItemClicked();
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onStickerItemClicked2() {
        super.onStickerItemClicked2();
        showStickerFunView(this.ly_sub_function.getChildAt(0));
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onBottomRatioClick() {
        super.onBottomRatioClick();
        showFunView(getString(R.string.bottom_ratio), this.ly_sub_function.getChildAt(0));
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onBottomBlurClick() {
        super.onBottomBlurClick();
        showFunView(getString(R.string.bottom_blur), this.ly_sub_function.getChildAt(0));
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void gotoShare(Bitmap bitmap) {
        if (this.shareBitmap != null) {
            if (!this.shareBitmap.isRecycled()) {
                this.shareBitmap.recycle();
            }
            this.shareBitmap = null;
        }
        this.shareBitmap = bitmap;
        this.random_bt.setVisibility(View.INVISIBLE);
        showProcessDialog();
        this.random_bt.setVisibility(View.VISIBLE);
        CSSaveToSDNew.saveImage(getApplicationContext(), this.shareBitmap, "Color Splash", Bitmap.CompressFormat.PNG, new DMSaveDoneListener() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.10
            @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
            public void onSavingException(Exception exc) {
                FreeTemplateActivity.this.dismissProcessDialog();
            }

            @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
            public void onSaveDone(String str, Uri uri) {
                FreeTemplateActivity.this.dismissProcessDialog();
                if (FreeTemplateActivity.this.shareBitmap == null || FreeTemplateActivity.this.shareBitmap.isRecycled() || FreeTemplateActivity.this.isSave) {
                    return;
                }
                FreeTemplateActivity.this.isSave = true;
                try {
                    Intent intent = new Intent(FreeTemplateActivity.this, CSShareActivity.class);
                    intent.putExtra("keyShareBmp", uri.toString());
                    intent.putExtra("shareBmpWidth", FreeTemplateActivity.this.shareBitmap.getWidth());
                    intent.putExtra("keyShareBmpPath", str);
                    FreeTemplateActivity.this.startActivity(intent);
                    FreeTemplateActivity.this.overridePendingTransition(0, 0);
                } catch (Exception unused) {
                }
            }
        });
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.ly_sub_function.clearDisappearingChildren();
        this.ly_sub_function.removeAllViews();
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        super.onDestroy();
    }

    private void showStickerFunView(View view) {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (int) ((DMScreenInfoUtil.screenWidth(this) * 11.0f) / 18.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(view);
        ChangeViewHeight(100);
        view.startAnimation(translateAnimation);
    }

    private void showFunView(String str, View view) {
        showFunView(str, view, true, 100);
    }

    private void showFunView(final String str, final View view, final boolean z, int i) {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        ViewParent parent = view.getParent();
        view.clearAnimation();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        final CustomFrameLayoutImags<Void> customFrameLayoutImags = new CustomFrameLayoutImags<Void>(this) { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.11
            @Override // com.photo.editor.square.splash.view.view.IL29
            protected void onSelectedPosition(int i2) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.photo.editor.square.splash.view.view.IL29
            public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
                super.f10(context, frameLayout, frameLayout2);
                showToolView(z);
                setIl6(str);
                frameLayout2.addView(view);
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new LayoutParams(-1, -1);
                }
                layoutParams.gravity = 17;
                view.setLayoutParams(layoutParams);
            }
        };
        customFrameLayoutImags.setIl5(new CustomFrameLayoutImags.IL4567123<>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.12
            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f3  reason: avoid collision after fix types in other method */
            public void mo70f3(Void r1) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f4  reason: avoid collision after fix types in other method */
            public void mo69f4(Void r1) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f2 */
            public void mo71f2() {
                if (FreeTemplateActivity.this.isShowSecondFun) {
                    FreeTemplateActivity.this.isShowSecondFun = false;
                    FreeTemplateActivity.this.ly_single_sub_function.removeView(customFrameLayoutImags);
                    FreeTemplateActivity.this.isBottomOperationViewShow = true;
                    return;
                }
                FreeTemplateActivity.this.resetBottomBar();
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f1 */
            public void mo72f1() {
                if (FreeTemplateActivity.this.isShowSecondFun) {
                    FreeTemplateActivity.this.isShowSecondFun = false;
                    FreeTemplateActivity.this.ly_single_sub_function.removeView(customFrameLayoutImags);
                    FreeTemplateActivity.this.isBottomOperationViewShow = true;
                    return;
                }
                FreeTemplateActivity.this.resetBottomBar();
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, customFrameLayoutImags.f11(), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(customFrameLayoutImags);
        ChangeViewHeight(i);
        customFrameLayoutImags.startAnimation(translateAnimation);
    }

    private void showCollageFunView(int i) {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        if (this.collageFunView == null) {
            FreeCollageFunView freeCollageFunView = new FreeCollageFunView(this);
            this.collageFunView = freeCollageFunView;
            freeCollageFunView.setmListener(new CollageFunView.OnTemplateChangedListener() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.13
                @Override // com.photo.editor.square.splash.view.CollageFunView.OnTemplateChangedListener
                public void onTemplateChanged(List<AssBitManage.bitBean> list, int i2) {
                    FreeTemplateActivity.this.currentBitBean = list.get(i2);
                    FreeTemplateActivity.this.refreshCurrentFrame();
                }
            });
            this.collageFunView.setListener(new CollageFunView.OnAdjustBtnClickListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$FreeTemplateActivity$zxnitos2i8clGXQVDMRe0yOjxak
                @Override // com.photo.editor.square.splash.view.CollageFunView.OnAdjustBtnClickListener
                public final void onAdjustClick() {
                    FreeTemplateActivity.this.lambda$showCollageFunView$1$FreeTemplateActivity();
                }
            });
            this.collageFunView.setIl5(new CustomFrameLayoutImags.IL4567123<Void>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.14
                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f3  reason: avoid collision after fix types in other method */
                public void mo70f3(Void r1) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f4  reason: avoid collision after fix types in other method */
                public void mo69f4(Void r1) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f2 */
                public void mo71f2() {
                    FreeTemplateActivity.this.resetBottomBar();
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f1 */
                public void mo72f1() {
                    FreeTemplateActivity.this.resetBottomBar();
                }
            });
            BackgroundGroupsItemsObserver backgroundGroupsItemsObserver = new BackgroundGroupsItemsObserver(this);
            backgroundGroupsItemsObserver.setIl5(new CustomFrameLayoutImags.IL4567123<Observable<BackgroundGroupsItems.BackgroundGroup>>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.15
                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f3  reason: avoid collision after fix types in other method */
                public void mo70f3(Observable<BackgroundGroupsItems.BackgroundGroup> observable) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f2 */
                public void mo71f2() {
                    FreeTemplateActivity.this.resetBottomBar();
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f1 */
                public void mo72f1() {
                    FreeTemplateActivity.this.resetBottomBar();
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f4  reason: avoid collision after fix types in other method */
                public void mo69f4(Observable<BackgroundGroupsItems.BackgroundGroup> observable) {
                    observable.subscribe(new Observer<BackgroundGroupsItems.BackgroundGroup>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.15.1
                        @Override // io.reactivex.Observer
                        public void onComplete() {
                        }

                        @Override // io.reactivex.Observer
                        public void onSubscribe(Disposable disposable) {
                            FreeTemplateActivity.this.disposable = disposable;
                        }

                        @Override // io.reactivex.Observer
                        public void onNext(BackgroundGroupsItems.BackgroundGroup backgroundGroup) {
                            FreeTemplateActivity.this.onSquareBgGroupItemClicked(backgroundGroup.getName(), backgroundGroup.getItems());
                        }

                        @Override // io.reactivex.Observer
                        public void onError(Throwable th) {
                            Log.e(FreeTemplateActivity.TAG, "onError: ", th);
                        }
                    });
                }
            });
            this.collageFunView.setBackground(backgroundGroupsItemsObserver);
            final FramesRecyclerView framesRecyclerView = new FramesRecyclerView(this);
            framesRecyclerView.setIl5(new CustomFrameLayoutImags.IL4567123<DMWBRes>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.16
                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f3  reason: avoid collision after fix types in other method */
                public void mo70f3(DMWBRes dMWBRes) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f2 */
                public void mo71f2() {
                    FreeTemplateActivity.this.ly_single_sub_function.removeView(framesRecyclerView);
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f1 */
                public void mo72f1() {
                    FreeTemplateActivity.this.ly_single_sub_function.removeView(framesRecyclerView);
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f4  reason: avoid collision after fix types in other method */
                public void mo69f4(DMWBRes dMWBRes) {
                    if (dMWBRes == null) {
                        return;
                    }
                    FreeTemplateActivity.this.stickerborderRes = (DMWBBorderRes) dMWBRes;
                    if ("ori".equals(FreeTemplateActivity.this.stickerborderRes.getName())) {
                        FreeTemplateActivity.this.tlView.setStickerBorderRes(null);
                    } else {
                        FreeTemplateActivity.this.tlView.setStickerBorderRes(FreeTemplateActivity.this.stickerborderRes);
                    }
                }
            });
            this.collageFunView.setBorderFrame(framesRecyclerView);
        }
        this.collageFunView.selectedTab(i);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, this.collageFunView.f11(), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(this.collageFunView);
        ChangeViewHeight(100);
        this.collageFunView.startAnimation(translateAnimation);
    }

    public /* synthetic */ void lambda$showCollageFunView$1$FreeTemplateActivity() {
        this.isShowSecondFun = true;
        onBottomRatioClick();
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onTemplateItemClick(DMWBRes dMWBRes, int i) {
        super.onTemplateItemClick(dMWBRes, i);
        this.position = i;
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void afterdeleteSticker() {
        super.afterdeleteSticker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCurrentFrame() {
        Data_Change.getInstance().setTl(null);
        Data_Change.getInstance().setTr(null);
        Data_Change.getInstance().setBr(null);
        Data_Change.getInstance().setBl(null);
        if (this.currentBitBean != null) {
            this.T_L = (ImageView) findViewById(R.id.image_left_top);
            this.T_R = (ImageView) findViewById(R.id.image_reit_top);
            this.B_L = (ImageView) findViewById(R.id.image_left_bottem);
            this.B_R = (ImageView) findViewById(R.id.image_reit_bottem);
            loadFrameBitmap(this.currentBitBean.getL_t(), this.T_L);
            loadFrameBitmap(this.currentBitBean.getR_t(), this.T_R);
            loadFrameBitmap(this.currentBitBean.getL_b(), this.B_L);
            loadFrameBitmap(this.currentBitBean.getR_b(), this.B_R);
            return;
        }
        this.T_L.setImageBitmap(null);
        this.T_R.setImageBitmap(null);
        this.B_L.setImageBitmap(null);
        this.B_R.setImageBitmap(null);
    }

    private void loadFrameBitmap(final String str, final ImageView imageView) {
        Glide.with(this).asBitmap().load(str).listener(new RequestListener<>() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.18
                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Bitmap> target, boolean z) {
                        imageView.setImageBitmap(null);
                        return false;
                    }

                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onResourceReady(Bitmap bitmap, Object obj, Target<Bitmap> target, DataSource dataSource, boolean z) {
                        if (!str.equals(FreeTemplateActivity.this.currentBitBean.getL_t())) {
                            if (!str.equals(FreeTemplateActivity.this.currentBitBean.getR_t())) {
                                if (!str.equals(FreeTemplateActivity.this.currentBitBean.getR_b())) {
                                    if (str.equals(FreeTemplateActivity.this.currentBitBean.getL_b())) {
                                        Data_Change.getInstance().setBl(bitmap);
                                        return false;
                                    }
                                    return false;
                                }
                                Data_Change.getInstance().setBr(bitmap);
                                return false;
                            }
                            Data_Change.getInstance().setTr(bitmap);
                            return false;
                        }
                        Data_Change.getInstance().setTl(bitmap);
                        return false;
                    }
                })
                .into(imageView);
//                .into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() {
//
////            @Override // com.bumptech.glide.request.target.Target
////            public void onResourceReady(Object obj, Transition transition) {
////                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
////            }
//
//
//            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                int width = bitmap.getWidth();
//                int height = bitmap.getHeight();
//                float f = FreeTemplateActivity.this.containerWidth / 800.0f;
//                if (FreeTemplateActivity.this.containerWidth > FreeTemplateActivity.this.containerHeight) {
//                    f = FreeTemplateActivity.this.containerHeight / 800.0f;
//                }
//                Data_Change.getInstance().setContainerWidth(FreeTemplateActivity.this.containerWidth);
//                Data_Change.getInstance().setContainerHeight(FreeTemplateActivity.this.containerHeight);
//                imageView.setImageBitmap(ImageUtils.scale(bitmap, (int) (width * f), (int) (height * f)));
//            }
//        });
    }

    public static Bitmap viewConversionBitmaptem(View view) {
        if (view instanceof ImageView) {
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
        }
        view.clearFocus();
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        if (createBitmap != null) {
            Canvas canvas = new Canvas(createBitmap);
            view.draw(canvas);
            canvas.setBitmap(null);
        }
        return createBitmap;
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity
    public void onBackImpl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tips);
        builder.setMessage(R.string.quit_string);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.19
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
//                if (AdmobConstants.isPositive(AdmobConstants.BACK_MAIN_INT_AS)) {
//                    AdmobLevelManager.getinstance().showAd(FreeTemplateActivity.this, new AdListener() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.19.1
//                        @Override // com.google.android.gms.ads.AdListener
//                        public void onAdClosed() {
//                        }
//                    }, "FreeCollage");
//                }
                FreeTemplateActivity.this.finish();
                dialogInterface.dismiss();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.photo.editor.square.splash.activitys.FreeTemplateActivity.20
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
