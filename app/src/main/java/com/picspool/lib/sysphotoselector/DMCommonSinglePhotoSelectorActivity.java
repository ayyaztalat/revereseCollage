package com.picspool.lib.sysphotoselector;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.viewpager.widget.ViewPager;
import java.io.File;
import java.util.List;
import com.picspool.lib.activity.DMFragmentActivityTemplate;
import com.picspool.lib.service.DMAsyncMediaDbScan23;
import com.picspool.lib.service.DMAsyncMediaDbScanExecutor;
import com.picspool.lib.service.DMAsyncThumbnailLoader;
import com.picspool.lib.service.DMImageMediaItem;
import com.picspool.lib.service.DMMediaBucket;
import com.picspool.lib.service.DMMediaDbScan;
import com.picspool.lib.service.OnDMMediaDbScanListener;
import com.picspool.lib.sysphotoselector.DMCommonPhotoAdapter;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMCommonSinglePhotoSelectorActivity extends DMFragmentActivityTemplate {
    File mCurrentPhotoFile;
    DMCommonPhotoAdapter mainPagerAdapter;
    ViewPager pager;
    DMPagerSlidingTabStrip tabs;

    public void pictureSelected(Uri uri) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_dm_common_single_photo_selector);
        DMPagerSlidingTabStrip dMPagerSlidingTabStrip = (DMPagerSlidingTabStrip) findViewById(R.id.tabs);
        this.tabs = dMPagerSlidingTabStrip;
        dMPagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.photoselector_common_main_color));
        this.tabs.setDividerColor(-65536);
        this.pager = (ViewPager) findViewById(R.id.pager);
        DMAsyncThumbnailLoader.initThumbLoader();
//        if (Build.VERSION.SDK_INT <= 10) {
//            DMAsyncMediaDbScan23 dMAsyncMediaDbScan23 = new DMAsyncMediaDbScan23(this, new DMMediaDbScan());
//            dMAsyncMediaDbScan23.setOnMediaDbScanListener(new OnDMMediaDbScanListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonSinglePhotoSelectorActivity.1
//                @Override // com.picspool.lib.service.OnDMMediaDbScanListener
//                public void onMediaDbScanFinish(DMMediaBucket dMMediaBucket) {
//                    DMCommonSinglePhotoSelectorActivity.this.onScanFinish(dMMediaBucket);
//                    DMCommonSinglePhotoSelectorActivity.this.dismissProcessDialog();
//                }
//            });
//            dMAsyncMediaDbScan23.execute();
//        } else {
            DMAsyncMediaDbScanExecutor.initThumbLoader(this, new DMMediaDbScan());
            DMAsyncMediaDbScanExecutor dMAsyncMediaDbScanExecutor = DMAsyncMediaDbScanExecutor.getInstance();
            dMAsyncMediaDbScanExecutor.setOnMediaDbScanListener(new OnDMMediaDbScanListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonSinglePhotoSelectorActivity.2
                @Override // com.picspool.lib.service.OnDMMediaDbScanListener
                public void onMediaDbScanFinish(DMMediaBucket dMMediaBucket) {
                    DMCommonSinglePhotoSelectorActivity.this.onScanFinish(dMMediaBucket);
                    DMAsyncMediaDbScanExecutor.shutdownThumbLoder();
                    DMCommonSinglePhotoSelectorActivity.this.dismissProcessDialog();
                }
            });
            dMAsyncMediaDbScanExecutor.execute();
//        }

        findViewById(R.id.btBack).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonSinglePhotoSelectorActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMCommonSinglePhotoSelectorActivity.this.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScanFinish(DMMediaBucket dMMediaBucket) {
        if (dMMediaBucket != null) {
            List<List<DMImageMediaItem>> bucketList = dMMediaBucket.getBucketList();
            DMCommonPhotoAdapter dMCommonPhotoAdapter = new DMCommonPhotoAdapter(getSupportFragmentManager(), getApplicationContext());
            this.mainPagerAdapter = dMCommonPhotoAdapter;
            dMCommonPhotoAdapter.setBudgetList(bucketList);
            this.mainPagerAdapter.setMultiSelector(2);
            this.mainPagerAdapter.setOnCommonPhotoAdapterItemSelectedListener(new DMCommonPhotoAdapter.OnCommonPhotoAdapterItemSelectedListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonSinglePhotoSelectorActivity.4
                @Override // com.picspool.lib.sysphotoselector.DMCommonPhotoAdapter.OnCommonPhotoAdapterItemSelectedListener
                public void exceedGivenMaxPhotoCount() {
                }

                @Override // com.picspool.lib.sysphotoselector.DMCommonPhotoAdapter.OnCommonPhotoAdapterItemSelectedListener
                public void commonPhotoAdapterItemSelected(DMImageMediaItem dMImageMediaItem, View view) {
                    DMCommonSinglePhotoSelectorActivity.this.pictureSelected(Uri.fromFile(new File(dMImageMediaItem.getData())));
                }
            });
            this.pager.setAdapter(this.mainPagerAdapter);
            this.tabs.setViewPager(this.pager);
            return;
        }
        Toast.makeText(this, "No picture!", Toast.LENGTH_LONG).show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        DMAsyncThumbnailLoader.initThumbLoader();
        super.onStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        DMAsyncThumbnailLoader.shutdownThumbLoder();
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
        DMCommonSelectedManager.getInstance().removeAllSelectImgList();
        super.onDestroy();
    }
}
