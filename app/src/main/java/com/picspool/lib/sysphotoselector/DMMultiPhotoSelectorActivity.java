package com.picspool.lib.sysphotoselector;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentTransaction;
import java.util.List;
import com.picspool.lib.activity.DMFragmentActivityTemplate;
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
public class DMMultiPhotoSelectorActivity extends DMFragmentActivityTemplate implements DMPhotoGridFragment.OnPhotoItemSelected, DMPhotoChooseBarView.OnChooseClickListener {
    DMBucketListAdapter adapter;
    ImageView btBack;
    Button btOK;
    DMPhotoChooseBarView chooseBarView;
    String confirm;
    DMPhotoGridFragment gridFragement;
    ListView listView1;
    TextView tx_middle;
    TextView tx_title;
    int max = 9;
    int min = 1;
    private int GridColumnCnt = 4;
    private int GridColumnSpacingPix = 0;
    private int GridRowSpacingPix = 0;

    @Override // com.picspool.lib.view.DMPhotoChooseBarView.OnChooseClickListener
    public void choosedClick(List<Uri> list) {
    }

    @Override // com.picspool.lib.view.DMPhotoChooseBarView.OnChooseClickListener
    public void choosedClick2(List<Uri> list, List<DMImageMediaItem> list2) {
    }

    public Context getMyContext() {
        return this;
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_dm_ps_multi_selector);
        DMAsyncThumbnailLoader.initThumbLoader();
        this.listView1 = (ListView) findViewById(R.id.listView1);
        this.btOK = (Button) findViewById(R.id.btOK);
        this.confirm = getResources().getString(R.string.photo_selected);
        this.tx_middle = (TextView) findViewById(R.id.tx_middle);
        this.tx_middle.setText(String.format(this.confirm, 0, Integer.valueOf(this.max)));
        this.btOK.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMMultiPhotoSelectorActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMMultiPhotoSelectorActivity.this.chooseBarView.ClickToNext();
            }
        });
        if (Build.VERSION.SDK_INT <= 10) {
            DMAsyncMediaDbScan23 dMAsyncMediaDbScan23 = new DMAsyncMediaDbScan23(getMyContext(), new DMMediaDbScan());
            dMAsyncMediaDbScan23.setOnMediaDbScanListener(new OnDMMediaDbScanListener() { // from class: com.picspool.lib.sysphotoselector.DMMultiPhotoSelectorActivity.2
                @Override // com.picspool.lib.service.OnDMMediaDbScanListener
                public void onMediaDbScanFinish(DMMediaBucket dMMediaBucket) {
                    DMMultiPhotoSelectorActivity.this.onScanFinish(dMMediaBucket);
                    DMMultiPhotoSelectorActivity.this.dismissProcessDialog();
                }
            });
            dMAsyncMediaDbScan23.execute();
        } else {
            DMAsyncMediaDbScanExecutor.initThumbLoader(this, new DMMediaDbScan());
            DMAsyncMediaDbScanExecutor dMAsyncMediaDbScanExecutor = DMAsyncMediaDbScanExecutor.getInstance();
            dMAsyncMediaDbScanExecutor.setOnMediaDbScanListener(new OnDMMediaDbScanListener() { // from class: com.picspool.lib.sysphotoselector.DMMultiPhotoSelectorActivity.3
                @Override // com.picspool.lib.service.OnDMMediaDbScanListener
                public void onMediaDbScanFinish(DMMediaBucket dMMediaBucket) {
                    DMMultiPhotoSelectorActivity.this.onScanFinish(dMMediaBucket);
                    DMAsyncMediaDbScanExecutor.shutdownThumbLoder();
                    DMMultiPhotoSelectorActivity.this.dismissProcessDialog();
                }
            });
            dMAsyncMediaDbScanExecutor.execute();
        }
        this.tx_title = (TextView) findViewById(R.id.tx_title);
        ImageView imageView = (ImageView) findViewById(R.id.btBack);
        this.btBack = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMMultiPhotoSelectorActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMMultiPhotoSelectorActivity.this.listView1.getVisibility() == View.VISIBLE) {
                    DMMultiPhotoSelectorActivity.this.onBackImpl();
                } else if (DMMultiPhotoSelectorActivity.this.gridFragement.isHidden()) {
                } else {
                    DMMultiPhotoSelectorActivity.this.tx_title.setText(DMMultiPhotoSelectorActivity.this.getResources().getString(R.string.lib_album));
                    FragmentTransaction beginTransaction = DMMultiPhotoSelectorActivity.this.getSupportFragmentManager().beginTransaction();
                    DMMultiPhotoSelectorActivity.this.gridFragement.clearBitmapMemory();
                    DMMultiPhotoSelectorActivity.this.gridFragement.setContext(DMMultiPhotoSelectorActivity.this);
                    beginTransaction.hide(DMMultiPhotoSelectorActivity.this.gridFragement);
                    beginTransaction.commitAllowingStateLoss();
                    DMMultiPhotoSelectorActivity.this.listView1.setVisibility(View.VISIBLE);
                }
            }
        });
        DMPhotoChooseBarView dMPhotoChooseBarView = (DMPhotoChooseBarView) findViewById(R.id.photoChooseBarView1);
        this.chooseBarView = dMPhotoChooseBarView;
        dMPhotoChooseBarView.setOnChooseClickListener(this);
        this.listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.picspool.lib.sysphotoselector.DMMultiPhotoSelectorActivity.5
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                List<DMImageMediaItem> list = (List) DMMultiPhotoSelectorActivity.this.adapter.getItem(i);
                if (DMMultiPhotoSelectorActivity.this.gridFragement == null) {
                    DMMultiPhotoSelectorActivity dMMultiPhotoSelectorActivity = DMMultiPhotoSelectorActivity.this;
                    dMMultiPhotoSelectorActivity.gridFragement = DMPhotoGridFragment.newInstance(DMScreenInfoUtil.screenWidth(dMMultiPhotoSelectorActivity) / 4);
                    DMMultiPhotoSelectorActivity.this.gridFragement.setGridViewColumnSpacing(DMMultiPhotoSelectorActivity.this.GridColumnSpacingPix, DMMultiPhotoSelectorActivity.this.GridRowSpacingPix);
                    DMMultiPhotoSelectorActivity.this.gridFragement.setGrideColumnCnt(DMMultiPhotoSelectorActivity.this.GridColumnCnt);
                    DMMultiPhotoSelectorActivity.this.gridFragement.setContext(DMMultiPhotoSelectorActivity.this);
                    DMMultiPhotoSelectorActivity.this.gridFragement.setOnPhotoItemSelected(DMMultiPhotoSelectorActivity.this);
                    DMMultiPhotoSelectorActivity.this.gridFragement.setDisplayData(list, false);
                    DMMultiPhotoSelectorActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.container, DMMultiPhotoSelectorActivity.this.gridFragement).commitAllowingStateLoss();
                } else {
                    DMMultiPhotoSelectorActivity.this.gridFragement.clearBitmapMemory();
                    DMMultiPhotoSelectorActivity.this.gridFragement.setContext(DMMultiPhotoSelectorActivity.this);
                    DMMultiPhotoSelectorActivity.this.gridFragement.setDisplayData(list, true);
                    FragmentTransaction beginTransaction = DMMultiPhotoSelectorActivity.this.getSupportFragmentManager().beginTransaction();
                    beginTransaction.show(DMMultiPhotoSelectorActivity.this.gridFragement);
                    beginTransaction.commitAllowingStateLoss();
                }
                DMMultiPhotoSelectorActivity.this.tx_title.setText(DMMultiPhotoSelectorActivity.this.adapter.getBuckDisName(i));
                DMMultiPhotoSelectorActivity.this.listView1.setVisibility(View.INVISIBLE);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScanFinish(DMMediaBucket dMMediaBucket) {
        if (dMMediaBucket != null) {
            List<List<DMImageMediaItem>> bucketList = dMMediaBucket.getBucketList();
            Log.v("lb", String.valueOf(bucketList.size()));
            DMBucketListAdapter dMBucketListAdapter = new DMBucketListAdapter(this);
            this.adapter = dMBucketListAdapter;
            ListView listView = this.listView1;
            if (listView != null) {
                dMBucketListAdapter.setListView(listView);
            }
            this.adapter.setBuckets(dMMediaBucket, bucketList);
            this.listView1.setAdapter((ListAdapter) this.adapter);
            return;
        }
        Toast.makeText(this, "No picture!", Toast.LENGTH_LONG).show();
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        DMAsyncThumbnailLoader.shutdownThumbLoder();
        DMBucketListAdapter dMBucketListAdapter = this.adapter;
        if (dMBucketListAdapter != null) {
            dMBucketListAdapter.dispose();
        }
        this.adapter = null;
        DMPhotoGridFragment dMPhotoGridFragment = this.gridFragement;
        if (dMPhotoGridFragment != null) {
            dMPhotoGridFragment.clearBitmapMemory();
        }
        this.gridFragement = null;
        DMPhotoChooseBarView dMPhotoChooseBarView = this.chooseBarView;
        if (dMPhotoChooseBarView != null) {
            dMPhotoChooseBarView.dispose();
        }
        this.chooseBarView = null;
        super.onDestroy();
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

    public void setMaxPickPhotos(int i) {
        this.tx_middle.setText(String.format(this.confirm, 0, Integer.valueOf(i)));
        this.chooseBarView.setMax(i);
        this.max = i;
    }

    @Override // com.picspool.lib.sysphotoselector.DMPhotoGridFragment.OnPhotoItemSelected
    public void photoItemSelected(DMImageMediaItem dMImageMediaItem, View view) {
        this.chooseBarView.addItem(dMImageMediaItem);
        this.tx_middle.setText(String.format(this.confirm, Integer.valueOf(this.chooseBarView.getItemCount()), Integer.valueOf(this.max)));
    }

    @Override // com.picspool.lib.view.DMPhotoChooseBarView.OnChooseClickListener
    public void ItemDelete(DMImageMediaItem dMImageMediaItem) {
        this.tx_middle.setText(String.format(this.confirm, Integer.valueOf(this.chooseBarView.getItemCount()), Integer.valueOf(this.max)));
    }

    public List<Uri> getChoosedUris() {
        return this.chooseBarView.getChoosedUris();
    }

    public void ChooseBarViewAddMediaItem(List<DMImageMediaItem> list) {
        if (this.chooseBarView == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            DMImageMediaItem dMImageMediaItem = list.get(i);
            if (dMImageMediaItem != null) {
                this.chooseBarView.addItem(dMImageMediaItem);
            }
        }
        if (this.tx_middle != null) {
            this.tx_middle.setText(String.format(this.confirm, Integer.valueOf(this.chooseBarView.getItemCount()), Integer.valueOf(this.max)));
        }
    }

    protected void setSelectorColor(int i) {
        findViewById(R.id.topbar).setBackgroundColor(getResources().getColor(i));
        findViewById(R.id.listView1).setBackgroundColor(getResources().getColor(i));
        findViewById(R.id.middlelayout).setBackgroundColor(getResources().getColor(i));
        findViewById(R.id.photoChooseBarView1).setBackgroundColor(getResources().getColor(i));
        findViewById(R.id.layout_bottom).setBackgroundColor(getResources().getColor(i));
    }

    public void onBackImpl() {
        finish();
    }
}
