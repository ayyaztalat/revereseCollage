package com.baiwang.libuiinstalens.xlbsticker.onlinestore;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialFactory;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialRes;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import java.lang.ref.WeakReference;
import java.util.zip.ZipException;

/* loaded from: classes.dex */
public class CSOnlineStoreDownLoadView extends FrameLayout {
    private TextView btn_download;
    private CSOnlineStoreDownLoadViewGridAdapter downLoadViewGridAdapter;
    private FrameLayout fl_stickerlistview;
    private ImageView img_main;
    private boolean isDownloading;
    private boolean isShowAd;
    private boolean isVungleAd;
    private View ly_adlock;
    private View ly_back;
    private LinearLayout ly_container;
    private LinearLayout ly_download_container;
    private Context mContext;
    private CSWBMaterialRes mRes;
    private onDownLoadViewItemClickListener onDownLoadViewItemClickListener;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SimpleTarget<Bitmap> simpleTarget;
    private TextView txt_name;
    private TextView txt_timeline;
    private TextView txt_title;
    private TextView txt_zipcontent;

    /* loaded from: classes.dex */
    public interface onDownLoadViewItemClickListener {
        boolean isAdLoaded();

        void onApplyClick(String str, CSWBMaterialRes cSWBMaterialRes);

        void onBackClick();

        void onDownLoadAdClikc(String str, DMWBRes dMWBRes);

        void onDownLoadClick(String str, DMWBRes dMWBRes);

        void onDownLoadProgress(int i);
    }

    public CSOnlineStoreDownLoadView(Context context, CSWBMaterialRes cSWBMaterialRes, boolean z) {
        super(context);
        this.isShowAd = false;
        this.isVungleAd = false;
        this.isDownloading = false;
        this.mRes = cSWBMaterialRes;
        this.isVungleAd = z;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_onlinestore_download, (ViewGroup) this, true);
        this.fl_stickerlistview = (FrameLayout) findViewById(R.id.fl_stickerlistview);
        this.ly_container = (LinearLayout) findViewById(R.id.ly_container);
        this.img_main = (ImageView) findViewById(R.id.img_main);
        this.txt_name = (TextView) findViewById(R.id.sticker_name);
        this.txt_zipcontent = (TextView) findViewById(R.id.zip_content);
        this.txt_timeline = (TextView) findViewById(R.id.service_life);
        this.progressBar = (ProgressBar) findViewById(R.id.download_progress);
        this.btn_download = (TextView) findViewById(R.id.btn_download);
        this.ly_back = findViewById(R.id.btn_back);
        this.ly_adlock = findViewById(R.id.img_adlock);
        this.ly_download_container = (LinearLayout) findViewById(R.id.ly_download_container);
        this.txt_title = (TextView) findViewById(R.id.txt_title);
        this.ly_back.setOnClickListener(new OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSOnlineStoreDownLoadView.this.onBackClicked();
            }
        });
        this.btn_download.setOnClickListener(new OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!CSOnlineStoreDownLoadView.this.mRes.isContentExist(CSWBMaterialFactory.PhotolabContentMain)) {
                    if (CSOnlineStoreDownLoadView.this.onDownLoadViewItemClickListener != null) {
                        CSOnlineStoreDownLoadView.this.onOnlineResDownLoad();
                        CSOnlineStoreDownLoadView.this.onDownLoadViewItemClickListener.onDownLoadClick(CSOnlineStoreDownLoadView.this.mRes.getName(), CSOnlineStoreDownLoadView.this.mRes);
                        return;
                    }
                    return;
                }
                CSOnlineStoreDownLoadView.this.onDownLoadViewItemClickListener.onApplyClick(CSOnlineStoreDownLoadView.this.mRes.getName(), CSOnlineStoreDownLoadView.this.mRes);
            }
        });
        int screenWidth = DMScreenInfoUtil.screenWidth(this.mContext);
        this.img_main.getLayoutParams().width = screenWidth;
        this.img_main.getLayoutParams().height = (int) ((screenWidth * 354.0f) / 720.0f);
        if (this.mRes.isContentExist(CSWBMaterialFactory.PhotolabContentMain)) {
            onResDowanloaded();
        }
        this.txt_name.setText(this.mRes.getName());
        this.txt_title.setText(this.mRes.getName());
        String string = getResources().getString(R.string.stickerbar_store_zip_content);
        String string2 = getResources().getString(R.string.stickerbar_store_zip_content_m);
        TextView textView = this.txt_zipcontent;
        textView.setText(string + " " + (Math.round((this.mRes.getContent_size() / 1000000.0f) * 100.0f) / 100.0f) + string2);
        this.simpleTarget = new SimpleTarget<Bitmap>() {
//            @Override // com.bumptech.glide.request.target.Target
//            public  void onResourceReady(Object obj, Transition transition) {
//                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
//            }


            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                synchronized (CSOnlineStoreDownLoadView.this.img_main) {
                    CSOnlineStoreDownLoadView.this.img_main.setImageBitmap(bitmap);
                    CSWBMaterialFactory.saveIconBitmapDisk(bitmap, CSOnlineStoreDownLoadView.this.mRes.getIconFileName());
                }
            }
        };
        String[] split = this.mRes.getIconUriPath().split("/");
        if (split != null && split.length > 0) {
            String str = split[split.length - 1];
        }
        ImageView imageView = (ImageView) new WeakReference(this.img_main).get();
        Bitmap iconBitmap = this.mRes.getIconBitmap();
        if (iconBitmap == null || (iconBitmap != null && iconBitmap.isRecycled())) {
            Glide.with(this.mContext).load(this.mRes.getIconUriPath()).apply((BaseRequestOptions<?>) new RequestOptions().signature(new ObjectKey(this.mRes.getIconUriPath())).override(720, 354)).into(imageView);
        } else {
            this.img_main.setImageBitmap(iconBitmap);
        }
        initAd();
        initrecyclerview();
        initColor();
    }

    public void initAd() {
        this.isShowAd = false;
        if (0 != 0) {
            this.ly_adlock.setVisibility(View.VISIBLE);
        } else {
            this.ly_adlock.setVisibility(View.GONE);
        }
    }

    private void initColor() {
        String content_backup_2 = this.mRes.getContent_backup_2();
        if (content_backup_2 == null || !content_backup_2.matches("^\\d+$")) {
            return;
        }
        if (content_backup_2.equals("1")) {
            this.ly_container.setBackgroundColor(this.mContext.getResources().getColor(R.color.style1_color2));
            this.txt_name.setTextColor(this.mContext.getResources().getColor(R.color.style2_color2));
            this.txt_zipcontent.setTextColor(this.mContext.getResources().getColor(R.color.style2_color2));
            this.txt_timeline.setTextColor(this.mContext.getResources().getColor(R.color.style2_color2));
        } else if (content_backup_2.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
            this.ly_container.setBackgroundColor(this.mContext.getResources().getColor(R.color.style2_color2));
            this.txt_name.setTextColor(this.mContext.getResources().getColor(R.color.style1_color2));
            this.txt_zipcontent.setTextColor(this.mContext.getResources().getColor(R.color.style1_color2));
            this.txt_timeline.setTextColor(this.mContext.getResources().getColor(R.color.style1_color2));
        }
    }

    private void initrecyclerview() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        if (this.mRes.getContent_backup_1() == null || !this.mRes.getContent_backup_1().matches("^\\d+$")) {
            return;
        }
        CSOnlineStoreDownLoadViewGridAdapter cSOnlineStoreDownLoadViewGridAdapter = new CSOnlineStoreDownLoadViewGridAdapter(this.mContext, this.mRes);
        this.downLoadViewGridAdapter = cSOnlineStoreDownLoadViewGridAdapter;
        this.recyclerView.setAdapter(cSOnlineStoreDownLoadViewGridAdapter);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, CSXlbStickerBarView.grid_item_count / CSXlbStickerBarView.grid_line_count));
    }

    public void onResDowanloaded() {
        this.progressBar.setVisibility(View.INVISIBLE);
        this.btn_download.setText(R.string.alreadydownload);
        this.ly_download_container.setVisibility(View.VISIBLE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onOnlineResDownLoad() {
        if (this.mRes.isContentExist(CSWBMaterialFactory.PhotolabContentMain) || this.isDownloading) {
            return;
        }
        this.isDownloading = true;
        this.mRes.downloadFileOnlineRes(this.mContext, new CSAsyncDownloadFileLoad.AsyncDownloadFileListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView.4
            @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
            public void onProgressUpdate(Integer... numArr) {
                if (CSOnlineStoreDownLoadView.this.onDownLoadViewItemClickListener != null) {
                    CSOnlineStoreDownLoadView.this.onDownLoadViewItemClickListener.onDownLoadProgress(numArr[0].intValue());
                }
            }

            @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
            public void onPostExecute(Object obj) {
                if (!((Boolean) obj).booleanValue() || CSOnlineStoreDownLoadView.this.mRes == null) {
                    return;
                }
                CSOnlineStoreDownLoadView.this.isDownloading = false;
                try {
                    CSOnlineStoreDownLoadView.this.mRes.upZip();
                    DMPreferencesUtil.save(CSOnlineStoreDownLoadView.this.mContext, "ad_lock_manager", CSOnlineStoreDownLoadView.this.mRes.getName() + "", "unlocked");
                    DMPreferencesUtil.save(CSOnlineStoreDownLoadView.this.mContext, "group_names", CSOnlineStoreDownLoadView.this.mRes.getUniqueName() + "", CSOnlineStoreDownLoadView.this.mRes.getName() + "");
                    DMPreferencesUtil.save(CSOnlineStoreDownLoadView.this.mContext, "sticker_colorstyle", CSOnlineStoreDownLoadView.this.mRes.getUniqueName() + "", CSOnlineStoreDownLoadView.this.mRes.getContent_backup_2() + "");
                    CSOnlineStoreDownLoadView.this.mRes.deleteZip();
                } catch (ZipException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
            public void onImageDownLoadFaile() {
                CSOnlineStoreDownLoadView.this.isDownloading = false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackClicked() {
        onDownLoadViewItemClickListener ondownloadviewitemclicklistener = this.onDownLoadViewItemClickListener;
        if (ondownloadviewitemclicklistener != null) {
            ondownloadviewitemclicklistener.onBackClick();
        }
    }

    public void setOnDownLoadViewItemClickListener(onDownLoadViewItemClickListener ondownloadviewitemclicklistener) {
        this.onDownLoadViewItemClickListener = ondownloadviewitemclicklistener;
    }
}
