package com.magic.video.editor.effect.gallery.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.C0879Utils;
import com.blankj.utilcode.util.UriUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.magic.video.editor.effect.gallery.model.CSMediaOptions;
import com.magic.video.editor.effect.gallery.present.CSGalleryPresent;
import com.magic.video.editor.effect.gallery.present.CSGalleryPresentImpl;
import com.magic.video.editor.effect.gallery.utils.CSScreenInfoUtil;
import com.sky.testproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class CSGalleryActivity extends AppCompatActivity implements CSGalleryView {
    public static final String IMAGE_DATA = "image_data";
    public static final int MAX_IMAGE_SUM = 20;
    public static final String MAX_SELECT_PIC_NUMBER_KEY = "max_select_pic_number_key";
    public static final String MEDIA_TYPE = "media_type";
    public static final String NEXT_ACTIVITY_INTENT = "next_activity_intent";
    private static final int REQUEST_CHOOSE_VIDEO_CODE = 21;
    private static final int REQUEST_RECORD_VIDEO_CODE = 20;
    public static final String SHOW_PEOPLE_TIPS_KEY = "show_people_tip_key";
    public static final String VIDEO_IMAGE_DATA = "video_and_image";
    private FrameLayout adContainer;
    private boolean adIsShow;
    private ImageView cover_img;
    private LinearLayout cover_ll;
    private CSGalleryPresent galleryPresent;
    private View gallery_bottom_bar;
    private boolean isShowPeopleTip;
    private int mediaType;
    private Intent nextActivity;
    private View okButton;
    private ViewPager pager;
    private TabPagerAdapter pagerAdapter;
    private RecyclerView recyclerView;
    private TextView selectImageNum;
    private CSGallerySelectedAdapter selectedListAdapter;
    private TabLayout tab;
    private File tempPhotoFile;
    private File tempRecordFile;
    private Uri tempUri;
    private TextView tv_selected;
    private final String TAG = "CSGalleryActivity";
    private int maxSelectNum = 20;
    private boolean canClick = true;
    private boolean isSingle = false;
    private Handler handler = new Handler();
    private boolean isOpen = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$iniActivity$8(View view) {
    }

//    @Override // com.mhh.libraryads.libads.adsBaseAdActivity
//    protected String getAppOpenTag() {
//        return "gallery_act";
//    }
//
//    @Override // com.mhh.libraryads.libads.adsBaseAdActivity
//    protected String getBackAdTag() {
//        return "gallery_act";
//    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public Context getContext() {
        return this;
    }

    public void onNextBtnClick(View view) {
        if (isNotCanClick()) {
            return;
        }
        this.galleryPresent.next();
    }

    public void onCameraClick(View view) {
        if (isNotCanClick()) {
            return;
        }
        this.galleryPresent.openCamera();
    }

    public void onGalleryClick(View view) {
        if (isNotCanClick()) {
            return;
        }
        this.galleryPresent.openGallery();
    }

    public void onDeleteClick(View view) {
        if (isNotCanClick()) {
            return;
        }
        this.galleryPresent.deleteAll();
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public void onShowMaxSelectedTips() {
        Toast.makeText(this, "You can add at most " + this.maxSelectNum + " stickers", Toast.LENGTH_SHORT).show();
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public void onAddItem(int i) {
        this.selectedListAdapter.addData(i);
        this.recyclerView.scrollToPosition(i);
        this.okButton.setVisibility(View.VISIBLE);
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public void updateUi() {
        this.selectImageNum.setText("(" + this.galleryPresent.getSelectedImageCount() + "/" + this.maxSelectNum + ")");
        this.pagerAdapter.getCurrentFragment().notifyMediaAdapterData();
        this.tab.invalidate();
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public void onNextBtnClick(final List<Uri> list) {
        if (this.isOpen) {
            return;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$waMCBZc456HkDP7B7ONcwM-Z2ZI
            @Override // java.lang.Runnable
            public final void run() {
                CSGalleryActivity.this.lambda$onNextBtnClick$0$CSGalleryActivity(list);
            }
        }, 200L);
        this.isOpen = true;
    }

    private void handleVideo(Uri uri, boolean z) {
        try {
            this.nextActivity.putExtra("videoUri", uri);
            startActivity(this.nextActivity);
            finish();
        } catch (Exception unused) {
            Toast.makeText(this, R.string.video_choose_wrong, Toast.LENGTH_SHORT).show();
        }
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public void onDeleteItem(int i) {
        this.selectedListAdapter.removeData(i);
        this.recyclerView.scrollToPosition(i - 1);
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public void onDeleteAll() {
        this.okButton.setVisibility(View.GONE);
        this.tab.invalidate();
        this.selectedListAdapter.notifyDataSetChanged();
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public int getSelectMaxNumber() {
        return this.maxSelectNum;
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public void onOpenCamera() {
        CSGalleryActivityPermissionsDispatcher.takePictureWithPermissionCheck(this);
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSGalleryView
    public void onOpenGallery() {
        try {
            Intent intent = new Intent("android.intent.action.PICK");
            if (this.mediaType == 1) {
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 9);
            } else {
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
                startActivityForResult(intent, 21);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class TabPagerAdapter extends PagerAdapter {
        CSGalleryPager fragment;

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        TabPagerAdapter() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            CSGalleryPager newInstance;
            if (CSGalleryActivity.this.mediaType == 1) {
                newInstance = CSGalleryPager.newInstance(CSGalleryActivity.this, new CSMediaOptions.Builder().canSelectMultiPhoto(true).canSelectMultiVideo(false).canSelectBothPhotoVideo().setMediaListSelected(new ArrayList()).build(), CSGalleryActivity.this.galleryPresent.getGroupItem(i), CSGalleryActivity.this.maxSelectNum);
            } else {
                newInstance = CSGalleryPager.newInstance(CSGalleryActivity.this, new CSMediaOptions.Builder().canSelectMultiPhoto(false).canSelectMultiVideo(true).setMediaListSelected(new ArrayList()).build(), CSGalleryActivity.this.galleryPresent.getGroupItem(i), CSGalleryActivity.this.maxSelectNum);
            }
            viewGroup.addView(newInstance);
            return newInstance;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public CharSequence getPageTitle(int i) {
            String groupItem = CSGalleryActivity.this.galleryPresent.getGroupItem(i);
            String substring = groupItem.substring(groupItem.lastIndexOf("/") + 1);
            if (substring.length() > 1) {
                return substring.substring(0, 1).toUpperCase() + substring.substring(1);
            }
            return substring.toUpperCase();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return CSGalleryActivity.this.galleryPresent.getGroupCount();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
            this.fragment = (CSGalleryPager) obj;
            super.setPrimaryItem(viewGroup, i, obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            CSGalleryPager cSGalleryPager = (CSGalleryPager) obj;
            cSGalleryPager.onDestroy();
            viewGroup.removeView(cSGalleryPager);
        }

        CSGalleryPager getCurrentFragment() {
            return this.fragment;
        }
    }

    private void showBannerViewAd() {
//        if (AdmobConstants.isPositive(AdmobConstants.GALLERY_BA_AS) && this.adContainer.getChildCount() == 0) {
//            boolean showAd = AdmobBannerViewManager.getinstance().showAd(this.adContainer, "cut_spiral");
//            this.adIsShow = showAd;
//            if (showAd) {
//                return;
//            }
//            AdmobBannerViewManager.getinstance().getAdStateLiveData().observe(this, new Observer<Boolean>() { // from class: com.magic.video.editor.effect.gallery.view.CSGalleryActivity.1
//                @Override // androidx.lifecycle.Observer
//                public void onChanged(Boolean bool) {
//                    CSGalleryActivity.this.adIsShow = AdmobBannerViewManager.getinstance().showAd(CSGalleryActivity.this.adContainer, "cut_spiral", false);
//                    if (CSGalleryActivity.this.adIsShow) {
//                        AdmobBannerViewManager.getinstance().getAdStateLiveData().removeObserver(this);
//                    }
//                }
//            });
//        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_gallery_cs);
        Intent intent = getIntent();
        if (intent != null) {
            int intExtra = intent.getIntExtra(MAX_SELECT_PIC_NUMBER_KEY, 20);
            this.maxSelectNum = intExtra;
            this.isSingle = intExtra == 1;
            this.isShowPeopleTip = intent.getBooleanExtra(SHOW_PEOPLE_TIPS_KEY, false);
            this.mediaType = intent.getIntExtra(MEDIA_TYPE, 1);
            Intent intent2 = (Intent) intent.getParcelableExtra(NEXT_ACTIVITY_INTENT);
            if (intent2 != null) {
                this.nextActivity = new Intent(intent2);
            }
        }
        this.galleryPresent = new CSGalleryPresentImpl(this, this.mediaType);
        this.cover_img = (ImageView) findViewById(R.id.cover_img);
        this.cover_ll = (LinearLayout) findViewById(R.id.cover_ll);
        this.okButton = findViewById(R.id.gallery_next);
        this.adContainer = (FrameLayout) findViewById(R.id.banner_container);
        this.recyclerView = (RecyclerView) findViewById(R.id.gallery_recycler_view);
        this.selectImageNum = (TextView) findViewById(R.id.gallery_num);
        this.tv_selected = (TextView) findViewById(R.id.tv_selected);
        this.tab = (TabLayout) findViewById(R.id.indicator);
        this.pager = (ViewPager) findViewById(R.id.pager);
        this.gallery_bottom_bar = findViewById(R.id.gallery_bottom_bar);
        findViewById(R.id.gallery_next).setOnClickListener(new View.OnClickListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$Vknnkif79Bx9C0upiDX3W_jQHdM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CSGalleryActivity.this.onNextBtnClick(view);
            }
        });
        findViewById(R.id.btn_gallery_camera).setOnClickListener(new View.OnClickListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$eGL1TiZEjb0nwFQshvKKQeMTIZM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CSGalleryActivity.this.onCameraClick(view);
            }
        });
        findViewById(R.id.btn_gallery).setOnClickListener(new View.OnClickListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$0tLaz0JPJfAOZSabxXsG1MUYgLg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CSGalleryActivity.this.onGalleryClick(view);
            }
        });
        findViewById(R.id.gallery_delete).setOnClickListener(new View.OnClickListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$6tJKPIV5U8dQ1JkYAM5KzBj2jdc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CSGalleryActivity.this.onDeleteClick(view);
            }
        });
//        AdmobBannerViewManager.getinstance().getAdCloseLiveData().observe(this, new Observer() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$75i34oL93k3H43Cee-I7sqFDAms
//            @Override // androidx.lifecycle.Observer
//            public final void onChanged(Object obj) {
//                CSGalleryActivity.this.lambda$onCreate$1$CSGalleryActivity((Boolean) obj);
//            }
//        });
        iniActivity();
    }

    public /* synthetic */ void lambda$onCreate$1$CSGalleryActivity(Boolean bool) {
        showBannerViewAd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.mhh.libraryads.libads.adsBaseAdActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        upDataBottomUi();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upDataBottomUi() {
        String valueOf = String.valueOf(this.galleryPresent.getSelectedImageCount());
        this.galleryPresent.resume();
        if (this.isSingle) {
            return;
        }
        this.selectImageNum.setText("(" + valueOf + "/" + this.maxSelectNum + ")");
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        if (this.galleryPresent.getSelectedImageCount() > 0) {
            this.selectedListAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.mhh.libraryads.libads.adsBaseAdActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.galleryPresent.pause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.mhh.libraryads.libads.adsBaseAdActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.handler = null;
        super.onDestroy();
        this.galleryPresent.destroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.mhh.libraryads.libads.adsBaseAdActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Uri uri;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i != 6) {
                if (i == 9) {
                    try {
                        Uri data = intent.getData();
                        if (uriCanuse(data) && data != null) {
                            addimg(data, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (i == 20) {
                    File file = this.tempRecordFile;
                    if (file != null && file != null && file.exists()) {
                        MediaScannerConnection.scanFile(this, new String[]{this.tempRecordFile.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$4gIc5o0-GgTu6eLzkCOiS5cWrLo
                            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                            public final void onScanCompleted(String str, Uri uri2) {
                                CSGalleryActivity.this.lambda$onActivityResult$2$CSGalleryActivity(str, uri2);
                            }
                        });
                    }
                } else if (i == 21) {
                    try {
                        Uri data2 = intent.getData();
                        if (uriCanuse(data2) && data2 != null) {
                            handleVideo(data2, false);
//                            finishNOAd();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } else if (this.selectedListAdapter != null && (uri = this.tempUri) != null) {
                uri.getPath();
                UriUtils.uri2File(this.tempUri).getPath();
                MediaScannerConnection.scanFile(this, new String[]{this.tempPhotoFile.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$qa7gdPdetxh25EgR3JMQp7opGSY
                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                    public final void onScanCompleted(String str, Uri uri2) {
                        CSGalleryActivity.this.lambda$onActivityResult$3$CSGalleryActivity(str, uri2);
                    }
                });
            }
        }
        this.tempUri = null;
        this.tempRecordFile = null;
        this.tempPhotoFile = null;
    }

    public /* synthetic */ void lambda$onActivityResult$2$CSGalleryActivity(String str, Uri uri) {
        handleVideo(uri, true);
    }

    public /* synthetic */ void lambda$onActivityResult$3$CSGalleryActivity(String str, final Uri uri) {
        runOnUiThread(new Runnable() { // from class: com.magic.video.editor.effect.gallery.view.CSGalleryActivity.2
            @Override // java.lang.Runnable
            public void run() {
                CSGalleryActivity.this.addimg(uri, true);
                CSGalleryActivity.this.upDataBottomUi();
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        CSGalleryActivityPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
        if (i == 10) {
            if (iArr.length == 0 || iArr[0] != 0) {
//                finishNOAd();
            } else {
                this.handler.post(new Runnable() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$_qfrvOHpICOL32wrIV8SZBb-X1A
                    @Override // java.lang.Runnable
                    public final void run() {
                        CSGalleryActivity.this.iniActivity();
                    }
                });
            }
        }
    }

    public boolean isNotCanClick() {
        if (this.canClick) {
            this.canClick = false;
            this.handler.postDelayed(new Runnable() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$ejbCtCYPxRtbmIW_WIlz0VjybDM
                @Override // java.lang.Runnable
                public final void run() {
                    CSGalleryActivity.this.lambda$isNotCanClick$4$CSGalleryActivity();
                }
            }, 500L);
            return false;
        }
        return true;
    }

    public /* synthetic */ void lambda$isNotCanClick$4$CSGalleryActivity() {
        this.canClick = true;
    }

    public void iniActivity() {
        findViewById(R.id.gallery_back).setOnClickListener(new View.OnClickListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$IWMSieYbrzu22-RD3E9dcXdOOkk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CSGalleryActivity.this.lambda$iniActivity$5$CSGalleryActivity(view);
            }
        });
        this.cover_img.setOnTouchListener(new View.OnTouchListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$zJAbzButoImECYRvDJljdwuQf_g
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return CSGalleryActivity.this.lambda$iniActivity$6$CSGalleryActivity(view, motionEvent);
            }
        });
        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.tips_dialog);
        if (this.isShowPeopleTip && isFirstOpen()) {
            viewGroup.setVisibility(View.VISIBLE);
            viewGroup.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$KlZlA_eAwH4DIZDDBQ_ivYTKkXI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    viewGroup.setVisibility(View.GONE);
                }
            });
            viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$NJEjO0_lOIx0XH6NicZmWHVRu9M
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CSGalleryActivity.lambda$iniActivity$8(view);
                }
            });
        }
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter();
        this.pagerAdapter = tabPagerAdapter;
        this.pager.setAdapter(tabPagerAdapter);
        this.tab.setupWithViewPager(this.pager);
        resetTabs();
        this.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.magic.video.editor.effect.gallery.view.CSGalleryActivity.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
            }
        });
        this.recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() { // from class: com.magic.video.editor.effect.gallery.view.CSGalleryActivity.4
            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public void onRequestDisallowInterceptTouchEvent(boolean z) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                return (motionEvent.getAction() & 255) == 5;
            }
        });
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) { // from class: com.magic.video.editor.effect.gallery.view.CSGalleryActivity.5
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (Exception ignored) {
                }
            }
        });
        CSGallerySelectedAdapter cSGallerySelectedAdapter = new CSGallerySelectedAdapter(this, this.galleryPresent.getSelectedImages());
        this.selectedListAdapter = cSGallerySelectedAdapter;
        cSGallerySelectedAdapter.setItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGalleryActivity$8zt0jW-ix3IRpfQXBKYzeUOYVXo
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                CSGalleryActivity.this.lambda$iniActivity$9$CSGalleryActivity(adapterView, view, i, j);
            }
        });
        this.recyclerView.setAdapter(this.selectedListAdapter);
        final int dip2px = CSScreenInfoUtil.dip2px(this, 10.0f);
        this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.magic.video.editor.effect.gallery.view.CSGalleryActivity.6
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                super.getItemOffsets(rect, view, recyclerView, state);
                if (recyclerView.getChildAdapterPosition(view) == 0) {
                    rect.left = dip2px;
                }
            }
        });
        TextView textView = this.tv_selected;
        textView.setText("Select 1–" + this.maxSelectNum + " photos");
        if (this.isSingle) {
            this.gallery_bottom_bar.setVisibility(View.GONE);
            ((ConstraintLayout.LayoutParams) this.pager.getLayoutParams()).bottomMargin = 0;
        }
    }

    public /* synthetic */ void lambda$iniActivity$5$CSGalleryActivity(View view) {
        finish();
    }

    public /* synthetic */ boolean lambda$iniActivity$6$CSGalleryActivity(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            this.cover_ll.setVisibility(View.GONE);
        }
        return true;
    }

    public /* synthetic */ void lambda$iniActivity$9$CSGalleryActivity(AdapterView adapterView, View view, int i, long j) {
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null || recyclerView.getScrollState() != 0) {
            return;
        }
        this.galleryPresent.deleteItem(i);
    }

    private void resetTabs() {
        this.tab.removeAllTabs();
        for (int i = 0; i < this.pagerAdapter.getCount(); i++) {
            TabLayout.Tab newTab = this.tab.newTab();
            View inflate = LayoutInflater.from(this.tab.getContext()).inflate(R.layout.layout_gallery_tab_cs, (ViewGroup) null);
            TextView textView = (TextView) inflate.findViewById(R.id.tab_name);
            inflate.findViewById(R.id.tab_indicator).setBackgroundResource(R.drawable.gallery_tab_selector);
            if (isDestroyed()) {
                return;
            }
            textView.setText(this.pagerAdapter.getPageTitle(i));
            newTab.setCustomView(inflate);
            this.tab.addTab(newTab);
        }
    }

    private boolean isFirstOpen() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("GalleryTips_firstOpen", true)) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("GalleryTips_firstOpen", false).apply();
            return true;
        }
        return false;
    }

    public void addimg(Uri uri) {
        addimg(uri, false);
    }

    public void addimg(Uri uri, boolean z) {
        if (this.isSingle) {
            if (this.nextActivity == null) {
                if (isNotCanClick()) {
                    return;
                }
                Intent intent = new Intent();
                intent.setData(uri);
                intent.putExtra("cameraUri", z);
                setResult(-1, intent);
            } else if (isNotCanClick()) {
                return;
            } else {
                if (this.mediaType == 1) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(uri);
                    lambda$onNextBtnClick$0$CSGalleryActivity(arrayList);
                } else {
                    handleVideo(uri, z);
                }
            }
//            finishNOAd();
            return;
        }
        this.galleryPresent.selectedItem(uri);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handlePhotos */
    public void lambda$onNextBtnClick$0$CSGalleryActivity(List<Uri> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            for (Uri uri : list) {
                arrayList.add(uri.toString());
            }
        } catch (Exception ignored) {
        }
        if (arrayList.size() > 0) {
            Intent intent = this.nextActivity;
            if (intent != null) {
                intent.putStringArrayListExtra("uris", arrayList);
                this.nextActivity.setData(list.get(0));
                startActivity(this.nextActivity);
            } else {
                Intent intent2 = new Intent();
                intent2.putStringArrayListExtra("uris", arrayList);
                intent2.setData(list.get(0));
                setResult(-1, intent2);
                finish();
            }
            this.isOpen = false;
        }
    }

    public boolean isCanSelected() {
        return !this.galleryPresent.isSelectedMax();
    }

    public int getUriNum(Uri uri) {
        int i = 0;
        if (uri != null && this.galleryPresent.getSelectedImageCount() != 0) {
            for (Uri uri2 : this.galleryPresent.getSelectedImages()) {
                if (uri2.toString().equals(uri.toString())) {
                    i++;
                }
            }
        }
        return i;
    }

    public boolean uriCanuse(Uri uri) {
        try {
            try {
                InputStream openInputStream = getContentResolver().openInputStream(uri);
                if (openInputStream != null) {
                    try {
                        openInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                return false;
            }
        } catch (Throwable unused) {
        }
        return true;
    }

    public void cover(Boolean bool, Uri uri) {
        if (bool.booleanValue()) {
            this.cover_ll.setVisibility(View.VISIBLE);
            if (uri != null) {
                Glide.with(getApplicationContext()).load(uri).into(this.cover_img);
                return;
            }
            return;
        }
        this.cover_ll.setVisibility(View.GONE);
    }

    public void takePicture() {
        if (this.mediaType == 1) {
            openCamera();
        } else {
            startRecord();
        }
    }

    private void startRecord() {
        Uri fromFile;
        try {
            Intent intent = new Intent();
            intent.setAction("android.media.action.VIDEO_CAPTURE");
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            this.tempRecordFile = new File(externalStoragePublicDirectory, "VideoEditor_" + System.currentTimeMillis() + ".mp4");
            if (Build.VERSION.SDK_INT >= 24) {
                fromFile = FileProvider.getUriForFile(C0879Utils.getApp(), C0879Utils.getApp().getPackageName() + ".utilcode.provider", this.tempRecordFile);
            } else {
                fromFile = Uri.fromFile(this.tempRecordFile);
            }
            intent.putExtra("output", fromFile);
            intent.addCategory("android.intent.category.DEFAULT");
            startActivityForResult(intent, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        Uri fromFile;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        this.tempPhotoFile = new File(externalStoragePublicDirectory, "IMG_" + System.currentTimeMillis() + ".jpg");
        if (Build.VERSION.SDK_INT >= 24) {
            Context applicationContext = getApplicationContext();
            fromFile = FileProvider.getUriForFile(applicationContext, AppUtils.getAppPackageName() + ".fileprovider", this.tempPhotoFile);
        } else {
            fromFile = Uri.fromFile(this.tempPhotoFile);
        }
        this.tempUri = fromFile;
        intent.putExtra("output", fromFile);
        intent.putExtra("android.intent.extra.videoQuality", 1);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        try {
            startActivityForResult(intent, 6);
        } catch (Exception unused) {
            Toast.makeText(this, "no camera", Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getTempUri() {
        return this.tempUri;
    }

    private File createImageFile2() {
        String str = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "PicShow");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, str);
    }
}
