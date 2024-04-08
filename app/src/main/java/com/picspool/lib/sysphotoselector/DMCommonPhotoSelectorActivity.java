package com.picspool.lib.sysphotoselector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.viewpager.widget.ViewPager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.picspool.lib.activity.DMFragmentActivityTemplate;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.service.DMAsyncMediaDbScan23;
import com.picspool.lib.service.DMAsyncMediaDbScanExecutor;
import com.picspool.lib.service.DMAsyncThumbnailLoader;
import com.picspool.lib.service.DMImageMediaItem;
import com.picspool.lib.service.DMMediaBucket;
import com.picspool.lib.service.DMMediaDbScan;
import com.picspool.lib.service.OnDMMediaDbScanListener;
import com.picspool.lib.sysphotoselector.DMCommonPhotoAdapter;
import com.picspool.lib.view.DMCommonPhotoChooseBarView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMCommonPhotoSelectorActivity extends DMFragmentActivityTemplate implements DMCommonPhotoChooseBarView.OnChooseClickListener {
    static final int CAMERA_PICK_IMAGE = 1;
    private static final String CURRENT_CHOSE_KEY = "CURRENT_CHOSE_KEY";
    private static final String SELECTED_INFO_COUNT = "SELECTED_INFO_COUNT";
    private static final String SELECTED_INFO_ID = "SELECTED_INFO_ID";
    Bitmap bottomBitmap;
    FrameLayout btCamera;
    Button btOK;
    public DMCommonPhotoChooseBarView chooseBarView;
    String confirm;
    ImageView img_photoselector_common_bottom_bg;
    ImageView img_photoselector_common_top_bg;
    View ly_tx_remove_all;
    File mCurrentPhotoFile;
    public DMCommonPhotoAdapter mainPagerAdapter;
    int max = 9;
    int min = 1;
    ViewPager pager;
    DMPagerSlidingTabStrip tabs;
    Bitmap topBitmap;
    public TextView tx_middle;
    TextView tx_middle_all;
    View tx_remove_all;
    TextView tx_title;
    private ArrayList<View> viewList;

    @Override // com.picspool.lib.view.DMCommonPhotoChooseBarView.OnChooseClickListener
    public void choosedClick(List<Uri> list) {
    }

    @Override // com.picspool.lib.view.DMCommonPhotoChooseBarView.OnChooseClickListener
    public void choosedClick2(List<Uri> list, List<DMImageMediaItem> list2) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_dm_common_photo_selector);
        DMPagerSlidingTabStrip dMPagerSlidingTabStrip = (DMPagerSlidingTabStrip) findViewById(R.id.tabs);
        this.tabs = dMPagerSlidingTabStrip;
        dMPagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.photoselector_common_main_color));
        this.tabs.setDividerColor(-65536);
        this.pager = (ViewPager) findViewById(R.id.pager);
        DMCommonPhotoChooseBarView dMCommonPhotoChooseBarView = (DMCommonPhotoChooseBarView) findViewById(R.id.photoChooseBarView1);
        this.chooseBarView = dMCommonPhotoChooseBarView;
        dMCommonPhotoChooseBarView.setOnChooseClickListener(this);
        this.btOK = (Button) findViewById(R.id.btOK);
        this.tx_middle = (TextView) findViewById(R.id.tx_middle);
        this.tx_middle.setText(String.format(getResources().getString(R.string.photoselector_common_photo_selected_number), 0));
        this.tx_middle_all = (TextView) findViewById(R.id.tx_middle_all);
        this.tx_middle_all.setText(String.format(getResources().getString(R.string.photoselector_common_photo_max_number), Integer.valueOf(this.max)));
        this.btOK.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoSelectorActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMCommonPhotoSelectorActivity.this.showProcessDialog();
                DMCommonPhotoSelectorActivity.this.chooseBarView.ClickToNext();
            }
        });
        findViewById(R.id.btBack).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoSelectorActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMCommonPhotoSelectorActivity.this.finish();
            }
        });
        this.img_photoselector_common_top_bg = (ImageView) findViewById(R.id.img_photoselector_common_top_bg);
        this.img_photoselector_common_bottom_bg = (ImageView) findViewById(R.id.img_photoselector_common_bottom_bg);
        DMCommonPhotoAdapter dMCommonPhotoAdapter = new DMCommonPhotoAdapter(getSupportFragmentManager(), getApplicationContext());
        this.mainPagerAdapter = dMCommonPhotoAdapter;
        dMCommonPhotoAdapter.setMultiSelector(1);
        this.mainPagerAdapter.setOnCommonPhotoAdapterItemSelectedListener(new DMCommonPhotoAdapter.OnCommonPhotoAdapterItemSelectedListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoSelectorActivity.3
            @Override // com.picspool.lib.sysphotoselector.DMCommonPhotoAdapter.OnCommonPhotoAdapterItemSelectedListener
            public void commonPhotoAdapterItemSelected(DMImageMediaItem dMImageMediaItem, View view) {
                DMCommonPhotoSelectorActivity.this.chooseBarView.addItem(dMImageMediaItem);
                DMCommonPhotoSelectorActivity.this.tx_middle.setText(String.format(DMCommonPhotoSelectorActivity.this.getResources().getString(R.string.photoselector_common_photo_selected_number), Integer.valueOf(DMCommonPhotoSelectorActivity.this.chooseBarView.getItemCount())));
            }

            @Override // com.picspool.lib.sysphotoselector.DMCommonPhotoAdapter.OnCommonPhotoAdapterItemSelectedListener
            public void exceedGivenMaxPhotoCount() {
                Toast.makeText(DMCommonPhotoSelectorActivity.this, String.format(DMCommonPhotoSelectorActivity.this.getResources().getString(R.string.prompt_max_photo_count), Integer.valueOf(DMCommonPhotoSelectorActivity.this.max)), Toast.LENGTH_SHORT).show();
            }
        });
        this.pager.setAdapter(this.mainPagerAdapter);
        this.tabs.setViewPager(this.pager);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.btCamera);
        this.btCamera = frameLayout;
        frameLayout.setVisibility(View.INVISIBLE);
        this.btCamera.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoSelectorActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/.tmpb/");
                    file.mkdirs();
                    DMCommonPhotoSelectorActivity.this.mCurrentPhotoFile = new File(file, "capture.jpg");
                    Uri fromFile = Uri.fromFile(DMCommonPhotoSelectorActivity.this.mCurrentPhotoFile);
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra("output", fromFile);
                    intent.putExtra("output", fromFile);
                    DMCommonPhotoSelectorActivity.this.startActivityForResult(intent, 1);
                }
            }
        });
        View findViewById = findViewById(R.id.ly_removeall);
        this.ly_tx_remove_all = findViewById(R.id.ly_tx_remove_all);
        this.tx_remove_all = findViewById(R.id.tx_remove_all);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoSelectorActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMCommonPhotoSelectorActivity.this.ly_tx_remove_all.setVisibility(View.VISIBLE);
            }
        });
        this.tx_remove_all.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoSelectorActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMCommonPhotoSelectorActivity.this.ly_tx_remove_all.setVisibility(View.INVISIBLE);
                DMCommonPhotoSelectorActivity.this.chooseBarView.deleteAll();
                DMCommonPhotoSelectorActivity.this.tx_middle.setText(String.format(DMCommonPhotoSelectorActivity.this.getResources().getString(R.string.photoselector_common_photo_selected_number), 0));
                DMCommonSelectedManager.getInstance().removeAllSelectImgList();
                DMCommonPhotoSelectorActivity.this.mainPagerAdapter.notifyDataSetChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScanFinish(DMMediaBucket dMMediaBucket) {
        if (dMMediaBucket != null && this.mainPagerAdapter != null) {
            List<List<DMImageMediaItem>> bucketList = dMMediaBucket.getBucketList();
            if (bucketList != null) {
                this.mainPagerAdapter.setBudgetList(bucketList);
                return;
            } else {
                Toast.makeText(this, "No picture!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        Toast.makeText(this, "No picture!", Toast.LENGTH_LONG).show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        DMAsyncThumbnailLoader.initThumbLoader();
        if (Build.VERSION.SDK_INT <= 10) {
            DMAsyncMediaDbScan23 dMAsyncMediaDbScan23 = new DMAsyncMediaDbScan23(this, new DMMediaDbScan());
            dMAsyncMediaDbScan23.setOnMediaDbScanListener(new OnDMMediaDbScanListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoSelectorActivity.7
                @Override // com.picspool.lib.service.OnDMMediaDbScanListener
                public void onMediaDbScanFinish(DMMediaBucket dMMediaBucket) {
                    DMCommonPhotoSelectorActivity.this.onScanFinish(dMMediaBucket);
                    DMCommonPhotoSelectorActivity.this.dismissProcessDialog();
                }
            });
            dMAsyncMediaDbScan23.execute();
        } else {
            DMAsyncMediaDbScanExecutor.initThumbLoader(this, new DMMediaDbScan());
            DMAsyncMediaDbScanExecutor dMAsyncMediaDbScanExecutor = DMAsyncMediaDbScanExecutor.getInstance();
            dMAsyncMediaDbScanExecutor.setOnMediaDbScanListener(new OnDMMediaDbScanListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoSelectorActivity.8
                @Override // com.picspool.lib.service.OnDMMediaDbScanListener
                public void onMediaDbScanFinish(DMMediaBucket dMMediaBucket) {
                    DMCommonPhotoSelectorActivity.this.onScanFinish(dMMediaBucket);
                    DMAsyncMediaDbScanExecutor.shutdownThumbLoder();
                    DMCommonPhotoSelectorActivity.this.dismissProcessDialog();
                }
            });
            dMAsyncMediaDbScanExecutor.execute();
        }
        this.img_photoselector_common_top_bg.setImageBitmap(null);
        Bitmap bitmap = this.topBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.topBitmap.recycle();
        }
        this.topBitmap = null;
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(getResources(), "photoselector/bm_img_photoselector_common_top_bg.pngpng", 4);
        this.topBitmap = imageFromAssetsFile;
        this.img_photoselector_common_top_bg.setImageBitmap(imageFromAssetsFile);
        this.img_photoselector_common_bottom_bg.setImageBitmap(null);
        Bitmap bitmap2 = this.bottomBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.bottomBitmap.recycle();
        }
        this.bottomBitmap = null;
        Bitmap imageFromAssetsFile2 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "photoselector/img_photoselector_common_bottom_bg.png", 4);
        this.bottomBitmap = imageFromAssetsFile2;
        this.img_photoselector_common_bottom_bg.setImageBitmap(imageFromAssetsFile2);
        super.onStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        DMAsyncThumbnailLoader.shutdownThumbLoder();
        this.img_photoselector_common_top_bg.setImageBitmap(null);
        Bitmap bitmap = this.topBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.topBitmap.recycle();
        }
        this.topBitmap = null;
        this.img_photoselector_common_bottom_bg.setImageBitmap(null);
        Bitmap bitmap2 = this.bottomBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.bottomBitmap.recycle();
        }
        this.bottomBitmap = null;
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        DMAsyncThumbnailLoader.shutdownThumbLoder();
        DMCommonPhotoAdapter dMCommonPhotoAdapter = this.mainPagerAdapter;
        if (dMCommonPhotoAdapter != null) {
            dMCommonPhotoAdapter.clearAll();
            this.mainPagerAdapter = null;
        }
        DMCommonPhotoChooseBarView dMCommonPhotoChooseBarView = this.chooseBarView;
        if (dMCommonPhotoChooseBarView != null) {
            dMCommonPhotoChooseBarView.dispose();
        }
        this.chooseBarView = null;
        DMCommonSelectedManager.getInstance().removeAllSelectImgList();
        super.onDestroy();
    }

    @Override // com.picspool.lib.view.DMCommonPhotoChooseBarView.OnChooseClickListener
    public void ItemDelete(DMImageMediaItem dMImageMediaItem) {
        this.tx_middle.setText(String.format(getResources().getString(R.string.photoselector_common_photo_selected_number), Integer.valueOf(this.chooseBarView.getItemCount())));
        DMCommonSelectedManager.getInstance().removeFromSelectImgList(dMImageMediaItem.getImgId());
        this.mainPagerAdapter.notifyDataSetChanged();
    }

    @Override // com.picspool.lib.view.DMCommonPhotoChooseBarView.OnChooseClickListener
    public void choosedNoneClick() {
        Toast.makeText(this, getResources().getString(R.string.photoselector_common_photo_atleast), Toast.LENGTH_SHORT).show();
    }

    public void setMaxPickPhotos(int i) {
        this.chooseBarView.setMax(i);
        DMCommonSelectedManager.getInstance().setMax(i);
        this.tx_middle_all.setText(String.format(getResources().getString(R.string.photoselector_common_photo_max_number), Integer.valueOf(i)));
        this.max = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(CURRENT_CHOSE_KEY, this.chooseBarView.getChoseMediaItem());
        int size = DMCommonSelectedManager.getInstance().getSelectImageList().entrySet().size();
        String[] strArr = new String[size];
        int[] iArr = new int[size];
        Iterator<Map.Entry<String, Integer>> it2 = DMCommonSelectedManager.getInstance().getSelectImageList().entrySet().iterator();
        for (int i = 0; i < size; i++) {
            Map.Entry<String, Integer> next = it2.next();
            strArr[i] = next.getKey();
            iArr[i] = next.getValue().intValue();
        }
        bundle.putStringArray(SELECTED_INFO_ID, strArr);
        bundle.putIntArray(SELECTED_INFO_COUNT, iArr);
        super.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle != null) {
            ArrayList<DMImageMediaItem> parcelableArrayList = bundle.getParcelableArrayList(CURRENT_CHOSE_KEY);
            if (parcelableArrayList != null && parcelableArrayList.size() > 0) {
                for (DMImageMediaItem dMImageMediaItem : parcelableArrayList) {
                    this.chooseBarView.addItem(dMImageMediaItem);
                }
                this.tx_middle.setText(String.format(getResources().getString(R.string.photoselector_common_photo_selected_number), Integer.valueOf(parcelableArrayList.size())));
            }
            String[] stringArray = bundle.getStringArray(SELECTED_INFO_ID);
            int[] intArray = bundle.getIntArray(SELECTED_INFO_COUNT);
            if (stringArray == null || intArray == null || stringArray.length != intArray.length) {
                return;
            }
            HashMap hashMap = new HashMap();
            for (int i = 0; i < stringArray.length; i++) {
                hashMap.put(stringArray[i], Integer.valueOf(intArray[i]));
            }
            DMCommonSelectedManager.getInstance().putAllSelectedMap(hashMap);
        }
    }
}
