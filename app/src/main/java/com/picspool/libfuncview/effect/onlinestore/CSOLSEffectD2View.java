package com.picspool.libfuncview.effect.onlinestore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.effect.CSEffectBar;
import com.picspool.libfuncview.effect.onlinestore.adapter.CSOLSEffectListStyle2Adapter;
import com.picspool.libfuncview.effect.onlinestore.manager.CSOLSWBED2Manager;
import com.picspool.libfuncview.effect.onlinestore.resource.CSAsyncDownloadFileLoad;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialRes;
import com.sky.testproject.R;

import java.io.IOException;

/* loaded from: classes.dex */
public class CSOLSEffectD2View extends FrameLayout implements View.OnClickListener, CSAsyncDownloadFileLoad.AsyncDownloadFileListener {
    private ImageView img_main;
    private boolean isShowAd;
    private ViewGroup lyRoot;
    private Context mContext;
    private onOnlineStoreEffectD2ViewCallBack mListener;
    private RecyclerView mRecyclerView;
    private CSEMaterialRes mWbeMaterialRes;
    private CSOLSEffectListStyle2Adapter olsEffectListStyle2Adapter;
    private CSOLSWBED2Manager olswbed2Manager;
    private ProgressBar progressBar;
    private TextView text_Btn;
    private TextView txt_title;

    /* loaded from: classes.dex */
    public interface onOnlineStoreEffectD2ViewCallBack {
        void onBackClicked();
    }

    public CSOLSEffectD2View(Context context, CSEMaterialRes cSEMaterialRes, ViewGroup viewGroup) {
        super(context);
        this.isShowAd = false;
        this.mContext = context;
        this.mWbeMaterialRes = cSEMaterialRes;
        this.lyRoot = viewGroup;
        if (cSEMaterialRes != null) {
            initView();
        }
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_ols_download_effectd2, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.effect.onlinestore.CSOLSEffectD2View.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSOLSEffectD2View.this.mListener != null) {
                    CSOLSEffectD2View.this.mListener.onBackClicked();
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.mContext, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.picspool.libfuncview.effect.onlinestore.CSOLSEffectD2View.2
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                return (i == 0 || i == 1) ? 2 : 1;
            }
        });
        this.mRecyclerView.setLayoutManager(gridLayoutManager);
        CSOLSWBED2Manager cSOLSWBED2Manager = new CSOLSWBED2Manager(this.mContext, this.mWbeMaterialRes);
        this.olswbed2Manager = cSOLSWBED2Manager;
        cSOLSWBED2Manager.setIconsloaded(new CSOLSWBED2Manager.Iconsloaded() { // from class: com.picspool.libfuncview.effect.onlinestore.CSOLSEffectD2View.3
            @Override // com.picspool.libfuncview.effect.onlinestore.manager.CSOLSWBED2Manager.Iconsloaded
            public void notifyiconslist() {
                if (CSOLSEffectD2View.this.olsEffectListStyle2Adapter != null) {
                    CSOLSEffectD2View.this.olsEffectListStyle2Adapter.notifyDataSetChanged();
                }
            }
        });
        CSOLSEffectListStyle2Adapter cSOLSEffectListStyle2Adapter = new CSOLSEffectListStyle2Adapter(this.mContext, this.olswbed2Manager.getBmwbImageResList());
        this.olsEffectListStyle2Adapter = cSOLSEffectListStyle2Adapter;
        this.mRecyclerView.setAdapter(cSOLSEffectListStyle2Adapter);
        this.progressBar = (ProgressBar) findViewById(R.id.material_progress);
        this.text_Btn = (TextView) findViewById(R.id.btn_download);
        if (this.mWbeMaterialRes.isContentExist()) {
            this.text_Btn.setText(this.mContext.getString(R.string.libui_ols_download_btn_apply));
        } else {
            this.text_Btn.setText(this.mContext.getString(R.string.libui_ols_download_btn_download));
        }
        this.text_Btn.setOnClickListener(this);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        onOnlineStoreEffectD2ViewCallBack ononlinestoreeffectd2viewcallback;
        if (i != 4 || (ononlinestoreeffectd2viewcallback = this.mListener) == null) {
            return true;
        }
        ononlinestoreeffectd2viewcallback.onBackClicked();
        return true;
    }

    public void setOnOnlineStoreEffectD2ViewCallBack(onOnlineStoreEffectD2ViewCallBack ononlinestoreeffectd2viewcallback) {
        this.mListener = ononlinestoreeffectd2viewcallback;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btn_download) {
            if (this.mWbeMaterialRes.isContentExist()) {
                if (((Activity) this.mContext).getIntent().getStringExtra("effectbar") != null) {
                    Intent intent = new Intent();
                    intent.putExtra(CSEffectBar.EffectApply, this.mWbeMaterialRes);
                    ((Activity) this.mContext).setResult(-1, intent);
                    ((Activity) this.mContext).finish();
                    return;
                } else if (((Activity) this.mContext).getIntent().getStringExtra(CSOLSEffectActivity.EffectOlsMore) != null) {
                    Intent intent2 = new Intent();
                    intent2.putExtra(CSOLSEffectActivity.EffectSelect, this.mWbeMaterialRes);
                    ((Activity) this.mContext).setResult(113, intent2);
                    ((Activity) this.mContext).finish();
                    return;
                } else {
                    Intent intent3 = new Intent();
                    intent3.putExtra(CSOLSEffectActivity.EffectSelect, this.mWbeMaterialRes);
                    ((Activity) this.mContext).setResult(-1, intent3);
                    ((Activity) this.mContext).finish();
                    return;
                }
            }
            CSEMaterialRes cSEMaterialRes = this.mWbeMaterialRes;
            cSEMaterialRes.downloadcontentRes(this.mContext, cSEMaterialRes.getData_zip(), this.mWbeMaterialRes.getContentDownFilePath(), this);
            this.text_Btn.setClickable(false);
        }
    }

    @Override // com.picspool.libfuncview.effect.onlinestore.resource.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
    public void onProgressUpdate(Integer... numArr) {
        this.text_Btn.setBackgroundColor(0);
        this.progressBar.setProgress(numArr[0].intValue());
        this.text_Btn.setText(this.mContext.getString(R.string.libui_ols_download_btn_downloading));
    }

    @Override // com.picspool.libfuncview.effect.onlinestore.resource.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
    public void onPostExecute(Object obj) {
        if (!((Boolean) obj).booleanValue() || this.mWbeMaterialRes == null) {
            return;
        }
        this.isShowAd = true;
        this.text_Btn.setClickable(true);
        this.text_Btn.setBackgroundColor(this.mContext.getResources().getColor(R.color.libui_main_color_red));
        this.text_Btn.setText(this.mContext.getString(R.string.libui_ols_download_btn_apply));
        try {
            this.mWbeMaterialRes.upZip(this.mWbeMaterialRes.getContentDownFilePath(), this.mWbeMaterialRes.getContentFilePath());
            this.mWbeMaterialRes.deleteZip(this.mWbeMaterialRes.getContentDownFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.picspool.libfuncview.effect.onlinestore.resource.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
    public void onImageDownLoadFaile() {
        this.text_Btn.setClickable(true);
    }
}
