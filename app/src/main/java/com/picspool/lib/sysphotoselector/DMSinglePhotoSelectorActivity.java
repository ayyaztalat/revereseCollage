package com.picspool.lib.sysphotoselector;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentTransaction;
import java.io.File;
import java.util.List;
import com.picspool.lib.activity.DMFragmentActivityTemplate;
import com.picspool.lib.p017io.DMCameraTakenUri;
import com.picspool.lib.service.DMAsyncMediaDbScan23;
import com.picspool.lib.service.DMAsyncMediaDbScanExecutor;
import com.picspool.lib.service.DMAsyncThumbnailLoader;
import com.picspool.lib.service.DMImageMediaItem;
import com.picspool.lib.service.DMMediaBucket;
import com.picspool.lib.service.DMMediaDbScan;
import com.picspool.lib.service.OnDMMediaDbScanListener;
import com.picspool.lib.sysphotoselector.DMPhotoGridFragment;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.view.DMBucketListAdapter;
import com.picspool.lib.view.DMPhotoChooseBarView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public abstract class DMSinglePhotoSelectorActivity extends DMFragmentActivityTemplate implements DMPhotoGridFragment.OnPhotoItemSelected {
    public static final int CAMERA_WITH_DATA = 2;
    public static final int SIZE_PICK_IMAGE = 1;
    DMBucketListAdapter adapter;
    ImageView btn_camera;
    ImageView btn_gallery;
    DMPhotoChooseBarView chooseBarView;
    GridView mGridView;
    protected ListView mListView;
    private ImageView selectDir;
    TextView tx_title;
    DMPhotoGridFragment gridFragement = null;
    int mSelectPostion = -1;
    public boolean isShowHideDirIcon = false;
    public boolean isShowDirList = false;
    public boolean isFirstLoadNull = false;
    private int GridColumnCnt = 4;
    private int GridColumnSpacingPix = 0;
    private int GridRowSpacingPix = 0;
    private boolean firstImageIsCamera = false;

    public void OnSelectDictoryClicked() {
    }

    public Context getMyContext() {
        return this;
    }

    public abstract void onCameraTakePictureException(String str);

    public abstract void onCameraTakePictureFinish(Uri uri);

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public abstract void onSelectPictureException(String str);

    public abstract void onSelectPictureFinish(Uri uri);

    public void pictureSelected(Uri uri) {
    }

    public void pictureSelected2(Uri uri, Uri uri2) {
    }

    public void pictureSelected3(DMImageMediaItem dMImageMediaItem) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.activity_dm_ps_single_selector);
        int dip2px = DMScreenInfoUtil.dip2px(this, 5.0f);
        this.GridColumnSpacingPix = dip2px;
        this.GridRowSpacingPix = dip2px;
        init();
        setGridViewColumnCnt(this.GridColumnCnt);
        setGridViewColumnSpacing(this.GridColumnSpacingPix, this.GridRowSpacingPix);
    }

    public void init() {
        DMPhotoGridFragment dMPhotoGridFragment;
        DMPhotoGridFragment dMPhotoGridFragment2;
        DMAsyncThumbnailLoader.initThumbLoader();
        ListView listView = this.mListView;
        if (listView != null) {
            listView.removeAllViewsInLayout();
        }
        this.mListView = null;
        this.mGridView = (GridView) findViewById(R.id.gridView);
        this.mListView = (ListView) findViewById(R.id.listView1);
        this.tx_title = (TextView) findViewById(R.id.tx_title);
        ImageView imageView = (ImageView) findViewById(R.id.single_selector_camera);
        this.btn_camera = imageView;
        imageView.setOnClickListener(new BtnCameraOnClickListener());
        ImageView imageView2 = (ImageView) findViewById(R.id.single_selector_gallery);
        this.btn_gallery = imageView2;
        imageView2.setOnClickListener(new BtnGellaryOnClickListener());
        findViewById(R.id.back_container).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMSinglePhotoSelectorActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMSinglePhotoSelectorActivity.this.finish();
            }
        });
        this.selectDir = (ImageView) findViewById(R.id.selectDoc);
        findViewById(R.id.selectDoc_container).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMSinglePhotoSelectorActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!DMSinglePhotoSelectorActivity.this.isShowDirList) {
                    DMSinglePhotoSelectorActivity.this.isShowDirList = true;
                    DMSinglePhotoSelectorActivity.this.showProcessDialog();
                    if (DMSinglePhotoSelectorActivity.this.adapter == null) {
                        DMAsyncMediaDbScanExecutor.initThumbLoader(DMSinglePhotoSelectorActivity.this, new DMMediaDbScan());
                        DMAsyncMediaDbScanExecutor dMAsyncMediaDbScanExecutor = DMAsyncMediaDbScanExecutor.getInstance();
                        dMAsyncMediaDbScanExecutor.setOnMediaDbScanListener(new OnDMMediaDbScanListener() { // from class: com.picspool.lib.sysphotoselector.DMSinglePhotoSelectorActivity.2.2
                                @Override // com.picspool.lib.service.OnDMMediaDbScanListener
                                public void onMediaDbScanFinish(DMMediaBucket dMMediaBucket) {
                                    DMSinglePhotoSelectorActivity.this.onScanFinish(dMMediaBucket);
                                    DMAsyncMediaDbScanExecutor.shutdownThumbLoder();
                                }
                            });
                        dMAsyncMediaDbScanExecutor.execute();
                    } else {
                        DMSinglePhotoSelectorActivity.this.mListView.setVisibility(View.VISIBLE);
                        DMSinglePhotoSelectorActivity.this.dismissProcessDialog();
                        if (DMSinglePhotoSelectorActivity.this.isShowHideDirIcon) {
                            DMSinglePhotoSelectorActivity.this.selectDir.setImageResource(R.drawable.dm_ps_ic_select_dir_hide);
                        } else {
                            DMSinglePhotoSelectorActivity.this.findViewById(R.id.selectDoc_container).setVisibility(View.INVISIBLE);
                        }
                        DMSinglePhotoSelectorActivity.this.tx_title.setText(DMSinglePhotoSelectorActivity.this.getResources().getString(R.string.select_pic_doc));
                        DMSinglePhotoSelectorActivity.this.showListAnimation();
                    }
                    DMSinglePhotoSelectorActivity.this.OnSelectDictoryClicked();
                    return;
                }
                DMSinglePhotoSelectorActivity.this.isShowDirList = false;
                DMSinglePhotoSelectorActivity.this.hideDictoryList();
                DMSinglePhotoSelectorActivity.this.selectDir.setImageResource(R.drawable.dm_ps_ic_select_dir);
            }
        });
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.picspool.lib.sysphotoselector.DMSinglePhotoSelectorActivity.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (DMSinglePhotoSelectorActivity.this.adapter == null) {
                    DMSinglePhotoSelectorActivity.this.findViewById(R.id.selectDoc_container).performClick();
                    return;
                }
                List<DMImageMediaItem> list = (List) DMSinglePhotoSelectorActivity.this.adapter.getItem(i);
                if (DMSinglePhotoSelectorActivity.this.firstImageIsCamera && list.size() > 0 && !list.get(0).isCamera()) {
                    DMImageMediaItem dMImageMediaItem = new DMImageMediaItem();
                    dMImageMediaItem.setCamera(true);
                    list.add(0, dMImageMediaItem);
                }
                DMSinglePhotoSelectorActivity.this.mSelectPostion = i;
                if (DMSinglePhotoSelectorActivity.this.gridFragement == null) {
                    DMSinglePhotoSelectorActivity dMSinglePhotoSelectorActivity = DMSinglePhotoSelectorActivity.this;
                    dMSinglePhotoSelectorActivity.gridFragement = DMPhotoGridFragment.newInstance(DMScreenInfoUtil.screenWidth(dMSinglePhotoSelectorActivity) / 3);
                    DMSinglePhotoSelectorActivity.this.gridFragement.setGridViewColumnSpacing(DMSinglePhotoSelectorActivity.this.GridColumnSpacingPix, DMSinglePhotoSelectorActivity.this.GridRowSpacingPix);
                    DMSinglePhotoSelectorActivity.this.gridFragement.setGrideColumnCnt(DMSinglePhotoSelectorActivity.this.GridColumnCnt);
                    DMSinglePhotoSelectorActivity.this.gridFragement.setSingleSelector(true);
                    DMSinglePhotoSelectorActivity.this.gridFragement.setContext(DMSinglePhotoSelectorActivity.this);
                    DMSinglePhotoSelectorActivity.this.gridFragement.setOnPhotoItemSelected(DMSinglePhotoSelectorActivity.this);
                    DMSinglePhotoSelectorActivity.this.gridFragement.setDisplayData(list, false);
                    DMSinglePhotoSelectorActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.container, DMSinglePhotoSelectorActivity.this.gridFragement).commitAllowingStateLoss();
                } else {
                    DMSinglePhotoSelectorActivity.this.gridFragement.clearBitmapMemory();
                    DMSinglePhotoSelectorActivity.this.gridFragement.setContext(DMSinglePhotoSelectorActivity.this);
                    DMSinglePhotoSelectorActivity.this.gridFragement.setDisplayData(list, true);
                    FragmentTransaction beginTransaction = DMSinglePhotoSelectorActivity.this.getSupportFragmentManager().beginTransaction();
                    beginTransaction.show(DMSinglePhotoSelectorActivity.this.gridFragement);
                    beginTransaction.commitAllowingStateLoss();
                }
                DMSinglePhotoSelectorActivity.this.tx_title.setText(DMSinglePhotoSelectorActivity.this.adapter.getBuckDisName(i));
                Animation loadAnimation = AnimationUtils.loadAnimation(DMSinglePhotoSelectorActivity.this, R.anim.dm_disappear);
                DMSinglePhotoSelectorActivity.this.mListView.startAnimation(loadAnimation);
                loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.picspool.lib.sysphotoselector.DMSinglePhotoSelectorActivity.3.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        DMSinglePhotoSelectorActivity.this.isShowDirList = false;
                        DMSinglePhotoSelectorActivity.this.mListView.clearAnimation();
                        DMSinglePhotoSelectorActivity.this.mListView.setVisibility(View.INVISIBLE);
                        if (DMSinglePhotoSelectorActivity.this.isShowHideDirIcon) {
                            DMSinglePhotoSelectorActivity.this.selectDir.setImageResource(R.drawable.dm_ic_select_dir);
                        } else {
                            DMSinglePhotoSelectorActivity.this.findViewById(R.id.selectDoc_container).setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
        DMMediaDbScan dMMediaDbScan = new DMMediaDbScan();
        DMMediaBucket scanPath = dMMediaDbScan.scanPath(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/camera");
        if (scanPath != null) {
            List<List<DMImageMediaItem>> bucketList = scanPath.getBucketList();
            if (bucketList.size() == 0) {
                bucketList = dMMediaDbScan.scanPath(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()).getBucketList();
            }
            if (bucketList.size() == 0) {
                DMBucketListAdapter dMBucketListAdapter = this.adapter;
                if (dMBucketListAdapter == null) {
                    this.isFirstLoadNull = true;
                    fillAdapter();
                    return;
                }
                List list = (List) dMBucketListAdapter.getItem(0);
                if (!this.firstImageIsCamera || list.size() <= 0 || ((DMImageMediaItem) list.get(0)).isCamera()) {
                    return;
                }
                DMImageMediaItem dMImageMediaItem = new DMImageMediaItem();
                dMImageMediaItem.setCamera(true);
                list.add(0, dMImageMediaItem);
                return;
            }
            List<DMImageMediaItem> list2 = bucketList.get(0);
            if (this.firstImageIsCamera && list2.size() > 0 && !list2.get(0).isCamera()) {
                DMImageMediaItem dMImageMediaItem2 = new DMImageMediaItem();
                dMImageMediaItem2.setCamera(true);
                list2.add(0, dMImageMediaItem2);
            }
            if (bucketList.size() != 0 && this.gridFragement == null) {
                DMPhotoGridFragment newInstance = DMPhotoGridFragment.newInstance(DMScreenInfoUtil.screenWidth(this) / 3);
                this.gridFragement = newInstance;
                newInstance.setGridViewColumnSpacing(this.GridColumnSpacingPix, this.GridRowSpacingPix);
                this.gridFragement.setGrideColumnCnt(this.GridColumnCnt);
                this.gridFragement.setSingleSelector(true);
                this.gridFragement.setContext(this);
                this.gridFragement.setOnPhotoItemSelected(this);
                this.gridFragement.setDisplayData(list2, false);
                getSupportFragmentManager().beginTransaction().add(R.id.container, this.gridFragement).commitAllowingStateLoss();
            } else if (bucketList.size() != 0 && (dMPhotoGridFragment2 = this.gridFragement) != null) {
                dMPhotoGridFragment2.clearBitmapMemory();
                this.gridFragement.setContext(this);
                this.gridFragement.setDisplayData(list2, true);
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                beginTransaction.show(this.gridFragement);
                beginTransaction.commitAllowingStateLoss();
            } else if (list2.size() > 0 && this.gridFragement == null) {
                DMPhotoGridFragment newInstance2 = DMPhotoGridFragment.newInstance(DMScreenInfoUtil.screenWidth(this) / 3);
                this.gridFragement = newInstance2;
                newInstance2.setGridViewColumnSpacing(this.GridColumnSpacingPix, this.GridRowSpacingPix);
                this.gridFragement.setGrideColumnCnt(this.GridColumnCnt);
                this.gridFragement.setSingleSelector(true);
                this.gridFragement.setContext(this);
                this.gridFragement.setOnPhotoItemSelected(this);
                this.gridFragement.setDisplayData(list2, false);
                getSupportFragmentManager().beginTransaction().add(R.id.container, this.gridFragement).commitAllowingStateLoss();
            } else if (list2.size() == 0 || (dMPhotoGridFragment = this.gridFragement) == null) {
            } else {
                dMPhotoGridFragment.clearBitmapMemory();
                this.gridFragement.setContext(this);
                this.gridFragement.setDisplayData(list2, true);
                FragmentTransaction beginTransaction2 = getSupportFragmentManager().beginTransaction();
                beginTransaction2.show(this.gridFragement);
                beginTransaction2.commitAllowingStateLoss();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    private void fillAdapter() {
        if (this.adapter == null) {
            if (Build.VERSION.SDK_INT <= 10) {
                DMAsyncMediaDbScan23 dMAsyncMediaDbScan23 = new DMAsyncMediaDbScan23(getMyContext(), new DMMediaDbScan());
                dMAsyncMediaDbScan23.setOnMediaDbScanListener(new OnDMMediaDbScanListener() { // from class: com.picspool.lib.sysphotoselector.DMSinglePhotoSelectorActivity.4
                    @Override // com.picspool.lib.service.OnDMMediaDbScanListener
                    public void onMediaDbScanFinish(DMMediaBucket dMMediaBucket) {
                        DMSinglePhotoSelectorActivity.this.onScanFinish(dMMediaBucket);
                    }
                });
                dMAsyncMediaDbScan23.execute();
                return;
            }
            DMAsyncMediaDbScanExecutor.initThumbLoader(this, new DMMediaDbScan());
            DMAsyncMediaDbScanExecutor dMAsyncMediaDbScanExecutor = DMAsyncMediaDbScanExecutor.getInstance();
            dMAsyncMediaDbScanExecutor.setOnMediaDbScanListener(new OnDMMediaDbScanListener() { // from class: com.picspool.lib.sysphotoselector.DMSinglePhotoSelectorActivity.5
                @Override // com.picspool.lib.service.OnDMMediaDbScanListener
                public void onMediaDbScanFinish(DMMediaBucket dMMediaBucket) {
                    DMSinglePhotoSelectorActivity.this.onScanFinish(dMMediaBucket);
                    DMAsyncMediaDbScanExecutor.shutdownThumbLoder();
                }
            });
            dMAsyncMediaDbScanExecutor.execute();
        }
    }

    public void setFirstImageIsCamera(boolean z) {
        this.firstImageIsCamera = z;
    }

    protected void resetGrid() {
        DMPhotoGridFragment dMPhotoGridFragment;
        int i = this.mSelectPostion;
        if (i == -1) {
            DMMediaDbScan dMMediaDbScan = new DMMediaDbScan();
            DMMediaBucket scanPath = dMMediaDbScan.scanPath(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/camera");
            if (scanPath != null) {
                List<List<DMImageMediaItem>> bucketList = scanPath.getBucketList();
                if (bucketList.size() == 0) {
                    bucketList = dMMediaDbScan.scanPath(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()).getBucketList();
                }
                List<DMImageMediaItem> list = bucketList.get(0);
                if (this.firstImageIsCamera) {
                    list.add(0, new DMImageMediaItem());
                }
                if (bucketList.size() != 0 && this.gridFragement == null) {
                    DMPhotoGridFragment newInstance = DMPhotoGridFragment.newInstance(DMScreenInfoUtil.screenWidth(this) / 3);
                    this.gridFragement = newInstance;
                    newInstance.setGridViewColumnSpacing(this.GridColumnSpacingPix, this.GridRowSpacingPix);
                    this.gridFragement.setGrideColumnCnt(this.GridColumnCnt);
                    this.gridFragement.setSingleSelector(true);
                    this.gridFragement.setContext(this);
                    this.gridFragement.setOnPhotoItemSelected(this);
                    this.gridFragement.setDisplayData(list, false);
                    getSupportFragmentManager().beginTransaction().add(R.id.container, this.gridFragement).commitAllowingStateLoss();
                } else if (bucketList.size() != 0 && (dMPhotoGridFragment = this.gridFragement) != null) {
                    dMPhotoGridFragment.clearBitmapMemory();
                    this.gridFragement.setContext(this);
                    this.gridFragement.setDisplayData(list, true);
                    FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                    beginTransaction.show(this.gridFragement);
                    beginTransaction.commitAllowingStateLoss();
                }
            }
            this.tx_title.setText(getResources().getString(R.string.lib_album));
            return;
        }
        DMBucketListAdapter dMBucketListAdapter = this.adapter;
        if (dMBucketListAdapter == null) {
            findViewById(R.id.selectDoc_container).performClick();
            return;
        }
        List<DMImageMediaItem> list2 = (List) dMBucketListAdapter.getItem(i);
        if (this.firstImageIsCamera) {
            list2.add(0, new DMImageMediaItem());
        }
        DMPhotoGridFragment dMPhotoGridFragment2 = this.gridFragement;
        if (dMPhotoGridFragment2 == null) {
            DMPhotoGridFragment newInstance2 = DMPhotoGridFragment.newInstance(DMScreenInfoUtil.screenWidth(this) / 3);
            this.gridFragement = newInstance2;
            newInstance2.setGridViewColumnSpacing(this.GridColumnSpacingPix, this.GridRowSpacingPix);
            this.gridFragement.setGrideColumnCnt(this.GridColumnCnt);
            this.gridFragement.setSingleSelector(true);
            this.gridFragement.setContext(this);
            this.gridFragement.setOnPhotoItemSelected(this);
            this.gridFragement.setDisplayData(list2, false);
            getSupportFragmentManager().beginTransaction().add(R.id.container, this.gridFragement).commitAllowingStateLoss();
        } else {
            dMPhotoGridFragment2.clearBitmapMemory();
            this.gridFragement.setContext(this);
            this.gridFragement.setDisplayData(list2, true);
            FragmentTransaction beginTransaction2 = getSupportFragmentManager().beginTransaction();
            beginTransaction2.show(this.gridFragement);
            beginTransaction2.commitAllowingStateLoss();
        }
        this.tx_title.setText(this.adapter.getBuckDisName(this.mSelectPostion));
    }

    public int dip2px(Context context, float f) {
        context.getResources();
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showListAnimation() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.dm_appear);
        this.mListView.startAnimation(loadAnimation);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.picspool.lib.sysphotoselector.DMSinglePhotoSelectorActivity.6
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScanFinish(DMMediaBucket dMMediaBucket) {
        DMPhotoGridFragment dMPhotoGridFragment;
        if (dMMediaBucket != null) {
            dismissProcessDialog();
            List<List<DMImageMediaItem>> bucketList = dMMediaBucket.getBucketList();
            bucketList.size();
            DMBucketListAdapter dMBucketListAdapter = new DMBucketListAdapter(this);
            this.adapter = dMBucketListAdapter;
            ListView listView = this.mListView;
            if (listView != null) {
                dMBucketListAdapter.setListView(listView);
                this.adapter.setBuckets(dMMediaBucket, bucketList);
                this.mListView.setAdapter((ListAdapter) this.adapter);
                if (this.isFirstLoadNull) {
                    this.isFirstLoadNull = false;
                    if (this.adapter.getCount() <= 0) {
                        return;
                    }
                    List<DMImageMediaItem> list = (List) this.adapter.getItem(0);
                    if (this.firstImageIsCamera && list.size() > 0 && !list.get(0).isCamera()) {
                        DMImageMediaItem dMImageMediaItem = new DMImageMediaItem();
                        dMImageMediaItem.setCamera(true);
                        list.add(0, dMImageMediaItem);
                    }
                    if (list.size() > 0 && this.gridFragement == null) {
                        DMPhotoGridFragment newInstance = DMPhotoGridFragment.newInstance(DMScreenInfoUtil.screenWidth(this) / 3);
                        this.gridFragement = newInstance;
                        newInstance.setGridViewColumnSpacing(this.GridColumnSpacingPix, this.GridRowSpacingPix);
                        this.gridFragement.setGrideColumnCnt(this.GridColumnCnt);
                        this.gridFragement.setSingleSelector(true);
                        this.gridFragement.setContext(this);
                        this.gridFragement.setOnPhotoItemSelected(this);
                        this.gridFragement.setDisplayData(list, false);
                        getSupportFragmentManager().beginTransaction().add(R.id.container, this.gridFragement).commitAllowingStateLoss();
                        return;
                    } else if (list.size() == 0 || (dMPhotoGridFragment = this.gridFragement) == null) {
                        return;
                    } else {
                        dMPhotoGridFragment.clearBitmapMemory();
                        this.gridFragement.setContext(this);
                        this.gridFragement.setDisplayData(list, true);
                        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                        beginTransaction.show(this.gridFragement);
                        beginTransaction.commitAllowingStateLoss();
                        return;
                    }
                }
                this.mListView.setVisibility(View.VISIBLE);
                findViewById(R.id.container).setVisibility(View.VISIBLE);
                this.tx_title.setText(getResources().getString(R.string.select_pic_doc));
                if (this.isShowHideDirIcon) {
                    this.selectDir.setImageResource(R.drawable.dm_ps_ic_select_dir_hide);
                } else {
                    findViewById(R.id.selectDoc_container).setVisibility(View.INVISIBLE);
                }
                showListAnimation();
                return;
            }
            return;
        }
        dismissProcessDialog();
        Toast.makeText(this, "No picture!", Toast.LENGTH_LONG).show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        DMAsyncThumbnailLoader.shutdownThumbLoder();
        DMPhotoGridFragment dMPhotoGridFragment = this.gridFragement;
        if (dMPhotoGridFragment != null) {
            dMPhotoGridFragment.dispose();
            this.gridFragement = null;
        }
        ListView listView = this.mListView;
        if (listView != null) {
            listView.removeAllViewsInLayout();
        }
        this.mListView = null;
        DMBucketListAdapter dMBucketListAdapter = this.adapter;
        if (dMBucketListAdapter != null) {
            dMBucketListAdapter.dispose();
        }
        this.adapter = null;
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        dismissProcessDialog();
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        DMAsyncThumbnailLoader.shutdownThumbLoder();
        super.onStop();
    }

    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.picspool.lib.sysphotoselector.DMPhotoGridFragment.OnPhotoItemSelected
    public void photoItemSelected(DMImageMediaItem dMImageMediaItem, View view) {
        if (dMImageMediaItem.isCamera()) {
            onCameraClick();
            return;
        }
        Uri fromFile = Uri.fromFile(new File(dMImageMediaItem.getData()));
        pictureSelected(fromFile);
        pictureSelected2(fromFile, dMImageMediaItem.getImgUri());
        pictureSelected3(dMImageMediaItem);
    }

    public void setDirListHideIconVisible(boolean z) {
        this.isShowHideDirIcon = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class BtnGellaryOnClickListener implements View.OnClickListener {
        private BtnGellaryOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            DMSinglePhotoSelectorActivity.this.onGellaryClick();
        }
    }

    public void onCameraClick() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/.tmpb/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, "capture.jpg");
            if (file2.exists()) {
                try {
                    file2.delete();
                } catch (Exception unused) {
                }
            }
            Uri fromFile = Uri.fromFile(file2);
            try {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("output", fromFile);
                startActivityForResult(intent, 2);
            } catch (Exception e) {
                onCameraTakePictureException(e.toString());
            }
        }
    }

    public void onGellaryClick() {
        try {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            onSelectPictureException(e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class BtnCameraOnClickListener implements View.OnClickListener {
        private BtnCameraOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            DMSinglePhotoSelectorActivity.this.onCameraClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 1) {
                Uri data = intent.getData();
                if (data == null && intent.getExtras() != null) {
                    data = DMCameraTakenUri.uriFromCamera(intent);
                }
                if (data == null) {
                    onSelectPictureException(getResources().getString(R.string.take_pic_fail));
                } else {
                    onSelectPictureFinish(data);
                }
            } else if (i != 2) {
            } else {
                Uri fromFile = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/.tmpb/capture.jpg"));
                if (fromFile != null) {
                    onCameraTakePictureFinish(fromFile);
                } else if (intent.getExtras() != null) {
                    onCameraTakePictureFinish(DMCameraTakenUri.uriFromCamera(intent));
                } else {
                    onCameraTakePictureException(getResources().getString(R.string.pic_not_exist));
                }
            }
        }
    }

    public void hideDictoryList() {
        if (this.mListView == null) {
            return;
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.dm_disappear);
        this.mListView.startAnimation(loadAnimation);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.picspool.lib.sysphotoselector.DMSinglePhotoSelectorActivity.7
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (DMSinglePhotoSelectorActivity.this.mListView == null) {
                    return;
                }
                DMSinglePhotoSelectorActivity.this.mListView.clearAnimation();
                DMSinglePhotoSelectorActivity.this.mListView.setVisibility(View.INVISIBLE);
                if (DMSinglePhotoSelectorActivity.this.isShowHideDirIcon) {
                    DMSinglePhotoSelectorActivity.this.selectDir.setImageResource(R.drawable.dm_ps_ic_select_dir);
                } else {
                    DMSinglePhotoSelectorActivity.this.findViewById(R.id.selectDoc_container).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setGridViewColumnCnt(int i) {
        this.GridColumnCnt = i;
        DMPhotoGridFragment dMPhotoGridFragment = this.gridFragement;
        if (dMPhotoGridFragment != null) {
            dMPhotoGridFragment.setGrideColumnCnt(i);
        }
    }

    public void setGridViewColumnSpacing(int i, int i2) {
        this.GridColumnSpacingPix = i;
        this.GridRowSpacingPix = i2;
        DMPhotoGridFragment dMPhotoGridFragment = this.gridFragement;
        if (dMPhotoGridFragment != null) {
            dMPhotoGridFragment.setGridViewColumnSpacing(i, i2);
        }
    }
}
