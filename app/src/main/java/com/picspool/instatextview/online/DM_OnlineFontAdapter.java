package com.picspool.instatextview.online;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.sky.testproject.R;
import com.picspool.instatextview.edit.DM_TextFixedView;
import com.picspool.instatextview.online.DM_OnlineFontDownloadInterface;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.packages.DMAppPackages;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class DM_OnlineFontAdapter extends BaseAdapter {
    private static List<DM_OnlineFontRes> resList;
    private String appName;
    private DM_TextFixedView editText;
    private Context mContext;
    private int selectionItem = 0;
    HashMap<Integer, Holder> holderMap = new HashMap<>();
    private List<Bitmap> bmpList = new ArrayList();
    private List<Typeface> tfList = DM_OnlineInstaTextView.getTfList();

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DM_OnlineFontAdapter(Context context) {
        this.mContext = context;
        resList = DM_OnlineInstaTextView.getResList();
        this.appName = DMAppPackages.getAppName(context.getPackageName());
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.tfList.size() % 2 == 0 ? this.tfList.size() / 2 : (this.tfList.size() / 2) + 1;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public int dip2px(Context context, float f) {
        context.getResources();
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        ImageView imageView;
        ImageView imageView2;
        if (view == null) {
            view = (LinearLayout) ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.dm_text_online_text_font_item_view, (ViewGroup) null);
            imageView = (ImageView) view.findViewById(R.id.font_name1);
            imageView2 = (ImageView) view.findViewById(R.id.font_name2);
            holder = new Holder();
            holder.mImageView1 = imageView;
            holder.mImageView2 = imageView2;
            holder.mProgressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
            holder.mProgressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);
            holder.mDownloadView1 = (ImageView) view.findViewById(R.id.font_download1);
            holder.mDownloadView2 = (ImageView) view.findViewById(R.id.font_download2);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
            imageView = holder.mImageView1;
            imageView2 = holder.mImageView2;
            ProgressBar progressBar = holder.mProgressBar1;
            ProgressBar progressBar2 = holder.mProgressBar2;
        }
        imageView.getLayoutParams().height = dip2px(this.mContext, 25.0f);
        imageView2.getLayoutParams().height = dip2px(this.mContext, 25.0f);
        int i2 = i * 2;
        if (resList.size() > i2) {
            Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), resList.get(i2).getFontImgPath());
            imageView.setImageBitmap(imageFromAssetsFile);
            this.bmpList.add(imageFromAssetsFile);
            view.findViewById(R.id.font_download1).setVisibility(4);
            if (resList.get(i2).getLocationType() == DMWBRes.LocationType.ONLINE && !resList.get(i2).isDownLoad()) {
                view.findViewById(R.id.font_download1).setVisibility(0);
            }
            view.findViewById(R.id.view1).setTag(Integer.valueOf(i2));
            view.findViewById(R.id.view1).setOnClickListener(new BtnFontClickListener());
            view.findViewById(R.id.progressBar1).setVisibility(4);
        }
        int i3 = i2 + 1;
        if (resList.size() > i3) {
            Bitmap imageFromAssetsFile2 = DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), resList.get(i3).getFontImgPath());
            imageView2.setImageBitmap(imageFromAssetsFile2);
            this.bmpList.add(imageFromAssetsFile2);
            view.findViewById(R.id.font_download2).setVisibility(4);
            if (resList.get(i3).getLocationType() == DMWBRes.LocationType.ONLINE && !resList.get(i3).isDownLoad()) {
                view.findViewById(R.id.font_download2).setVisibility(0);
            }
            view.findViewById(R.id.view2).setTag(Integer.valueOf(i3));
            view.findViewById(R.id.view2).setOnClickListener(new BtnFontClickListener());
            view.findViewById(R.id.progressBar2).setVisibility(4);
        }
        this.holderMap.put(Integer.valueOf(i), holder);
        return view;
    }

    public boolean checkNetwork(Context context) {
        ConnectivityManager connectivityManager;
        if (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        return (networkInfo2 != null ? networkInfo2.isConnectedOrConnecting() : false) | (networkInfo != null ? networkInfo.isConnectedOrConnecting() : false);
    }

    /* loaded from: classes3.dex */
    private class BtnFontClickListener implements View.OnClickListener {
        private BtnFontClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            final int intValue = ((Integer) view.getTag()).intValue();
            DM_OnlineFontRes dM_OnlineFontRes = (DM_OnlineFontRes) DM_OnlineFontAdapter.resList.get(intValue);
            if (dM_OnlineFontRes.getLocationType() != DMWBRes.LocationType.ONLINE) {
                DM_OnlineFontAdapter.this.editText.setTextTypeface(DM_OnlineInstaTextView.getTfList().get(intValue));
                DM_OnlineFontAdapter.this.editText.getTextDrawer().setTypefaceIndex(intValue);
                DM_OnlineFontAdapter.this.setSelection(intValue);
            } else if (dM_OnlineFontRes.isDownLoad()) {
                DM_OnlineFontAdapter.this.editText.setTextTypeface(DM_OnlineInstaTextView.getTfList().get(intValue));
                DM_OnlineFontAdapter.this.editText.getTextDrawer().setTypefaceIndex(intValue);
                DM_OnlineFontAdapter.this.setSelection(intValue);
            } else {
                DM_OnlineFontAdapter dM_OnlineFontAdapter = DM_OnlineFontAdapter.this;
                if (!dM_OnlineFontAdapter.checkNetwork(dM_OnlineFontAdapter.mContext)) {
                    Toast.makeText(DM_OnlineFontAdapter.this.mContext, DM_OnlineFontAdapter.this.mContext.getResources().getString(R.string.no_network), 0).show();
                    return;
                }
                Holder holder = DM_OnlineFontAdapter.this.holderMap.get(Integer.valueOf(intValue / 2));
                if (intValue % 2 == 0) {
                    holder.mProgressBar1.setVisibility(0);
                    holder.mDownloadView1.setVisibility(4);
                } else {
                    holder.mProgressBar2.setVisibility(0);
                    holder.mDownloadView2.setVisibility(4);
                }
                dM_OnlineFontRes.downloadFontOnlineRes(DM_OnlineFontAdapter.this.mContext, new DM_OnlineFontDownloadInterface.OnFontResDownLoadListener() { // from class: com.picspool.instatextview.online.DM_OnlineFontAdapter.BtnFontClickListener.1
                    @Override // com.picspool.instatextview.online.DM_OnlineFontDownloadInterface.OnFontResDownLoadListener
                    public void onDownLoadFinish(String str) {
                        Holder holder2 = DM_OnlineFontAdapter.this.holderMap.get(Integer.valueOf(intValue / 2));
                        if (intValue % 2 == 0) {
                            holder2.mProgressBar1.setVisibility(4);
                        } else {
                            holder2.mProgressBar2.setVisibility(4);
                        }
                        DM_OnlineFontRes dM_OnlineFontRes2 = (DM_OnlineFontRes) DM_OnlineFontAdapter.resList.get(intValue);
                        dM_OnlineFontRes2.setDownLoad(true);
                        DM_OnlineFontAdapter.resList.set(intValue, dM_OnlineFontRes2);
                        DM_OnlineFontAdapter.this.tfList.set(intValue, ((DM_OnlineFontRes) DM_OnlineFontAdapter.resList.get(intValue)).getFontTypeface(DM_OnlineFontAdapter.this.mContext));
                    }

                    @Override // com.picspool.instatextview.online.DM_OnlineFontDownloadInterface.OnFontResDownLoadListener
                    public void onDownLoadFaile(String str) {
                        Holder holder2 = DM_OnlineFontAdapter.this.holderMap.get(Integer.valueOf(intValue / 2));
                        if (intValue % 2 == 0) {
                            holder2.mProgressBar1.setVisibility(4);
                            holder2.mDownloadView1.setVisibility(0);
                            return;
                        }
                        holder2.mProgressBar2.setVisibility(4);
                        holder2.mDownloadView2.setVisibility(0);
                    }
                });
            }
        }
    }

    public void setSelection(int i) {
        this.selectionItem = i;
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class Holder {
        public ImageView mDownloadView1;
        public ImageView mDownloadView2;
        public ImageView mImageView1;
        public ImageView mImageView2;
        public ProgressBar mProgressBar1;
        public ProgressBar mProgressBar2;

        private Holder() {
        }
    }

    public void setEditText(DM_TextFixedView dM_TextFixedView) {
        this.editText = dM_TextFixedView;
    }

    public void dispose() {
        int size = resList.size();
        if (size % 2 == 1) {
            size++;
        }
        for (int i = 0; i < size; i += 2) {
            Holder holder = this.holderMap.get(Integer.valueOf(i / 2));
            if (holder != null) {
                holder.mImageView1.setImageBitmap(null);
                if (resList.size() % 2 == 0) {
                    holder.mImageView2.setImageBitmap(null);
                }
            }
        }
        int size2 = this.bmpList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            Bitmap bitmap = this.bmpList.get(0);
            this.bmpList.remove(0);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        this.bmpList.clear();
        System.gc();
    }
}
