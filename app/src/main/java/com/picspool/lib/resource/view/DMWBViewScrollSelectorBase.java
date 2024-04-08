package com.picspool.lib.resource.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter;

/* loaded from: classes3.dex */
public class DMWBViewScrollSelectorBase extends FrameLayout implements AdapterView.OnItemClickListener {
    protected DMWBManager mManager;
    protected DMWBMaterialUrlInterface mMaterialUrlBase;
    protected DMWBOnResourceChangedListener mResListener;
    protected DMWBScrollBarArrayAdapter scrollDataAdapter;
    protected DMWBHorizontalListView scrollView;

    protected String resultToUrl(String str) {
        return str;
    }

    public DMWBViewScrollSelectorBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setDataAdapter(DMWBManager dMWBManager) {
        this.mManager = dMWBManager;
        int count = dMWBManager.getCount();
        DMWBRes[] dMWBResArr = new DMWBRes[count];
        for (int i = 0; i < count; i++) {
            dMWBResArr[i] = this.mManager.getRes(i);
        }
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = new DMWBScrollBarArrayAdapter(getContext(), dMWBResArr);
        this.scrollDataAdapter = dMWBScrollBarArrayAdapter;
        this.scrollView.setAdapter((ListAdapter) dMWBScrollBarArrayAdapter);
        this.scrollView.setOnItemClickListener(this);
    }

    public void setWBOnResourceChangedListener(DMWBOnResourceChangedListener dMWBOnResourceChangedListener) {
        this.mResListener = dMWBOnResourceChangedListener;
    }

    public void setWBMaterialUrlInterface(DMWBMaterialUrlInterface dMWBMaterialUrlInterface) {
        this.mMaterialUrlBase = dMWBMaterialUrlInterface;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        String str;
        DMWBRes res = this.mManager.getRes(i);
        if (this.mMaterialUrlBase != null) {
            str = this.mMaterialUrlBase.getUrlBase() + "&name=" + res.getName();
        } else {
            str = null;
        }
        if (res instanceof DMWBImageRes) {
            onImageResProcess(str, (DMWBImageRes) res, i);
        } else {
            this.mResListener.resourceChanged(res, "..", this.mManager.getCount(), i);
        }
    }

    protected void onImageResProcess(String str, final DMWBImageRes dMWBImageRes, final int i) {
        if (dMWBImageRes.getImageType() == DMWBRes.LocationType.ONLINE) {
            if (dMWBImageRes.isImageResInLocal(getContext())) {
                this.mResListener.resourceChanged(dMWBImageRes, "..", this.mManager.getCount(), i);
                return;
            }
            this.scrollDataAdapter.setViewInDownloading(i);
            DMWBAsyncTextHttpExecute.asyncHttpRequest(str, new DMAsyncTextHttpTaskListener() { // from class: com.picspool.lib.resource.view.DMWBViewScrollSelectorBase.1
                @Override // com.picspool.lib.resource.view.DMAsyncTextHttpTaskListener
                public void onRequestDidFinishLoad(String str2) {
                    dMWBImageRes.setImageFileName(DMWBViewScrollSelectorBase.this.resultToUrl(str2));
                    dMWBImageRes.downloadImageOnlineRes(DMWBViewScrollSelectorBase.this.getContext(), new DMWBImageRes.OnResImageDownLoadListener() { // from class: com.picspool.lib.resource.view.DMWBViewScrollSelectorBase.1.1
                        @Override // com.picspool.lib.resource.DMWBImageRes.OnResImageDownLoadListener
                        public void onImageDownLoadFinish(String str3) {
                            DMWBViewScrollSelectorBase.this.mResListener.resourceChanged(dMWBImageRes, "..", DMWBViewScrollSelectorBase.this.mManager.getCount(), i);
                            DMWBViewScrollSelectorBase.this.scrollDataAdapter.setViewInDownloadOk(i);
                        }

                        @Override // com.picspool.lib.resource.DMWBImageRes.OnResImageDownLoadListener
                        public void onImageDownLoadFaile() {
                            DMWBViewScrollSelectorBase.this.scrollDataAdapter.setViewInDownloadOk(i);
                        }
                    });
                }

                @Override // com.picspool.lib.resource.view.DMAsyncTextHttpTaskListener
                public void onRequestDidFailedStatus(Exception exc) {
                    DMWBViewScrollSelectorBase.this.scrollDataAdapter.setViewInDownloadFail(i);
                }
            });
            return;
        }
        this.mResListener.resourceChanged(dMWBImageRes, "..", this.mManager.getCount(), i);
    }
}
