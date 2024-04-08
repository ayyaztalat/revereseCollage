package com.baiwang.libuiinstalens.xlbsticker.onlinestore;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialFactory;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialGroupRes;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialRes;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.widget.DownloadDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.zip.ZipException;

/* loaded from: classes.dex */
public class CSOnlineStoreListAdapter extends RecyclerView.Adapter<CSOnlineStoreListAdapter.MyViewHolder> {
    private Context context;
    private DownloadDialog dialog;
    int height;
    private ItemClickListener itemClickListener;
    private CSWBMaterialGroupRes mDatas;
    private SimpleTarget<Bitmap> simpleTarget;
    int width;

    /* loaded from: classes.dex */
    public interface ItemClickListener {
        void onItemClick(View view, int i, CSWBMaterialRes cSWBMaterialRes);
    }

    public CSOnlineStoreListAdapter(Context context) {
        this.context = context;
    }

    public CSOnlineStoreListAdapter(Context context, CSWBMaterialGroupRes cSWBMaterialGroupRes) {
        this.context = context;
        this.mDatas = cSWBMaterialGroupRes;
        this.dialog = new DownloadDialog(context, R.style.MyDialog);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.view_photolab_recycleview_itemview, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setDataItem(this.mDatas, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDatas.getWBMaterialResList().size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgmain;
        View ly_line;
        TextView txt_download;

        public MyViewHolder(final View view) {
            super(view);
            this.imgmain = (ImageView) view.findViewById(R.id.img_main);
            this.ly_line = view.findViewById(R.id.bottom_line);
            this.txt_download = (TextView) view.findViewById(R.id.txt_download);
            view.getLayoutParams().height = (int) ((DMScreenInfoUtil.screenWidth(CSOnlineStoreListAdapter.this.context) * 354.0f) / 720.0f);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreListAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    CSWBMaterialRes cSWBMaterialRes = (CSOnlineStoreListAdapter.this.mDatas == null || CSOnlineStoreListAdapter.this.mDatas.getWBMaterialResList() == null || MyViewHolder.this.getAdapterPosition() < 0 || CSOnlineStoreListAdapter.this.mDatas.getWBMaterialResList().size() <= MyViewHolder.this.getAdapterPosition()) ? null : CSOnlineStoreListAdapter.this.mDatas.getWBMaterialResList().get(MyViewHolder.this.getAdapterPosition());
                    if (CSOnlineStoreListAdapter.this.itemClickListener != null) {
                        CSOnlineStoreListAdapter.this.itemClickListener.onItemClick(view, MyViewHolder.this.getAdapterPosition(), cSWBMaterialRes);
                    }
                }
            });
        }

        public void setDataItem(CSWBMaterialGroupRes cSWBMaterialGroupRes, int i) {
            List<CSWBMaterialRes> wBMaterialResList;
            final CSWBMaterialRes cSWBMaterialRes;
            if (cSWBMaterialGroupRes == null || cSWBMaterialGroupRes.getWBMaterialResList() == null || CSOnlineStoreListAdapter.this.mDatas.getWBMaterialResList().size() <= 0 || (wBMaterialResList = cSWBMaterialGroupRes.getWBMaterialResList()) == null || wBMaterialResList.size() <= i || (cSWBMaterialRes = wBMaterialResList.get(i)) == null) {
                return;
            }
            CSOnlineStoreListAdapter.this.simpleTarget = new SimpleTarget<Bitmap>() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreListAdapter.MyViewHolder.2
//                @Override // com.bumptech.glide.request.target.Target
//                public void onResourceReady(Object obj, Transition transition) {
//                    onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
//                }

                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    synchronized (MyViewHolder.this.imgmain) {
                        MyViewHolder.this.imgmain.setImageBitmap(bitmap);
                        CSWBMaterialFactory.saveIconBitmapDisk(bitmap, cSWBMaterialRes.getIconFileName());
                    }
                }
            };
            if (cSWBMaterialRes.getIconType() == DMWBRes.LocationType.ASSERT) {
                this.imgmain.setImageBitmap(DMBitmapUtil.getImageFromAssetsFile(CSOnlineStoreListAdapter.this.context.getResources(), cSWBMaterialRes.getIconFileName()));
            } else {
                String iconUriPath = cSWBMaterialRes.getIconUriPath();
                String[] split = iconUriPath.split("/");
                if (split != null && split.length > 0) {
                    iconUriPath = split[split.length - 1];
                }
                ImageView imageView = (ImageView) new WeakReference(this.imgmain).get();
                Bitmap iconBitmap = cSWBMaterialRes.getIconBitmap();
                if (iconBitmap == null || (iconBitmap != null && iconBitmap.isRecycled())) {
                    Glide.with(CSOnlineStoreListAdapter.this.context).load(cSWBMaterialRes.getIconUriPath()).apply((BaseRequestOptions<?>) new RequestOptions().signature(new ObjectKey(iconUriPath)).override(720, 354)).into(imageView);
                } else {
                    this.imgmain.setImageBitmap(iconBitmap);
                }
            }
            if (cSWBMaterialRes.isContentExist(CSWBMaterialFactory.PhotolabContentMain) || cSWBMaterialRes.getIconType() == DMWBRes.LocationType.ASSERT) {
                this.txt_download.setText(CSOnlineStoreListAdapter.this.context.getResources().getString(R.string.stickerbar_store_downloaded));
                this.txt_download.setBackgroundDrawable(CSOnlineStoreListAdapter.this.context.getResources().getDrawable(R.drawable.xml_btn_stickerbar_added));
            } else {
                this.txt_download.setText(CSOnlineStoreListAdapter.this.context.getResources().getString(R.string.stickerbar_store_downloadget));
                this.txt_download.setBackgroundDrawable(CSOnlineStoreListAdapter.this.context.getResources().getDrawable(R.drawable.xml_btn_stickerbar_setting_delete));
            }
            if (i == CSOnlineStoreListAdapter.this.mDatas.getWBMaterialResList().size() - 1) {
                this.ly_line.setVisibility(View.INVISIBLE);
            } else {
                this.ly_line.setVisibility(View.VISIBLE);
            }
        }
    }

    /* loaded from: classes.dex */
    protected class WallpaperDownloadListener implements CSAsyncDownloadFileLoad.AsyncDownloadFileListener {
        int pos;
        CSWBMaterialRes res;

        public WallpaperDownloadListener(CSWBMaterialRes cSWBMaterialRes, int i) {
            this.res = null;
            this.res = cSWBMaterialRes;
            this.pos = i;
        }

        @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
        public void onProgressUpdate(Integer... numArr) {
            if (CSOnlineStoreListAdapter.this.dialog != null) {
                CSOnlineStoreListAdapter.this.dialog.updateCursor(numArr[0].intValue());
            }
        }

        @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
        public void onPostExecute(Object obj) {
            CSWBMaterialRes cSWBMaterialRes;
            if (!((Boolean) obj).booleanValue() || (cSWBMaterialRes = this.res) == null) {
                return;
            }
            try {
                cSWBMaterialRes.upZip();
            } catch (ZipException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.res.deleteZip();
            if (!this.res.isContentExist(CSWBMaterialFactory.PhotolabContentMain)) {
                if (CSOnlineStoreListAdapter.this.dialog != null) {
                    CSOnlineStoreListAdapter.this.dialog.dismiss();
                }
                Toast.makeText(CSOnlineStoreListAdapter.this.context,R.string.download_failure, Toast.LENGTH_LONG).show();
                return;
            }
            CSOnlineStoreListAdapter.this.notifyItemChanged(this.pos);
            if (CSOnlineStoreListAdapter.this.dialog != null) {
                CSOnlineStoreListAdapter.this.dialog.dismiss();
            }
            CSWBMaterialRes cSWBMaterialRes2 = this.res;
            if (cSWBMaterialRes2 == null) {
                Toast.makeText(CSOnlineStoreListAdapter.this.context, "data wrong", 0).show();
            } else if (cSWBMaterialRes2.isContentExist(CSWBMaterialFactory.PhotolabContentMain) || this.res.getContentType() == DMWBRes.LocationType.ASSERT) {
            } else {
                Toast.makeText(CSOnlineStoreListAdapter.this.context, "data wrong", 0).show();
            }
        }

        @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
        public void onImageDownLoadFaile() {
            new Handler(CSOnlineStoreListAdapter.this.context.getMainLooper()).post(new Runnable() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreListAdapter.WallpaperDownloadListener.1
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(CSOnlineStoreListAdapter.this.context,R.string.download_failure, Toast.LENGTH_LONG).show();
                    if (WallpaperDownloadListener.this.res != null) {
                        WallpaperDownloadListener.this.res.delContentFromFile();
                    }
                    if (CSOnlineStoreListAdapter.this.dialog != null) {
                        CSOnlineStoreListAdapter.this.dialog.dismiss();
                    }
                }
            });
        }
    }
}
