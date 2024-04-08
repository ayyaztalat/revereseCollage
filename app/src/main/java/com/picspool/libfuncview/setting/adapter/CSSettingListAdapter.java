package com.picspool.libfuncview.setting.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.libfuncview.res.CSGroupRes;
import com.picspool.libfuncview.res.CSResManagerInterface;
import com.picspool.libfuncview.xlbsticker.stickerbar.CSStickerGroup;
import com.picspool.libfuncview.xlbsticker.stickerbar.CSStickerRes;
import java.io.File;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSettingListAdapter extends RecyclerView.Adapter<CSSettingListAdapter.MyViewHolder> {
    private List<CSGroupRes> BMWBResList;
    private Context mContext;
    private onStickerSettingItemClikListener mListener;
    private CSResManagerInterface resManagerInterface;
    private int selectedPos = -1;

    /* loaded from: classes.dex */
    public interface onStickerSettingItemClikListener {
        void onClick(int i, DMWBRes dMWBRes);

        void onDragTouched(boolean z);

        void onDragTouched(boolean z, MyViewHolder myViewHolder);
    }

    private void deleteOnlineStickerRes() {
    }

    public CSSettingListAdapter(Context context, List<CSGroupRes> list, CSResManagerInterface cSResManagerInterface) {
        this.mContext = context;
        this.BMWBResList = list;
        this.resManagerInterface = cSResManagerInterface;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_adapteritem_settinglist, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.BMWBResList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.BMWBResList.size();
    }

    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        View btn_delete;
        ImageView imageView_main;
        View ly_drag;
        View ly_line;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ly_line = view.findViewById(R.id.bottom_line);
            this.ly_drag = view.findViewById(R.id.btn_drag);
            this.btn_delete = view.findViewById(R.id.btn_delete);
            this.ly_drag.setOnTouchListener(new View.OnTouchListener() { // from class: com.picspool.libfuncview.setting.adapter.CSSettingListAdapter.MyViewHolder.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    CSSettingListAdapter.this.mListener.onDragTouched(true, MyViewHolder.this);
                    return false;
                }
            });
            this.btn_delete.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.setting.adapter.CSSettingListAdapter.MyViewHolder.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition > CSSettingListAdapter.this.BMWBResList.size()) {
                        return;
                    }
                    CSGroupRes cSGroupRes = (CSGroupRes) CSSettingListAdapter.this.BMWBResList.get(adapterPosition);
                    String name = cSGroupRes.getName();
                    CSSettingListAdapter.delAllFile(cSGroupRes.getOlFilePath());
                    if (DMPreferencesUtil.get(CSSettingListAdapter.this.mContext, CSSettingListAdapter.this.resManagerInterface.getOrderKey(), name) != null) {
                        DMPreferencesUtil.save(CSSettingListAdapter.this.mContext, CSSettingListAdapter.this.resManagerInterface.getOrderKey(), name, "0");
                    }
                    CSSettingListAdapter.this.BMWBResList.remove(MyViewHolder.this.getAdapterPosition());
                    CSSettingListAdapter.this.notifyItemRemoved(MyViewHolder.this.getAdapterPosition());
                }
            });
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.setting.adapter.CSSettingListAdapter.MyViewHolder.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (CSSettingListAdapter.this.mListener != null) {
                        CSSettingListAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSSettingListAdapter.this.BMWBResList.get(adapterPosition));
                    }
                    CSSettingListAdapter.this.selectedPos = adapterPosition;
                    CSSettingListAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setData(List<CSGroupRes> list, int i) {
            Bitmap decodeFile;
            CSGroupRes cSGroupRes = list.get(i);
            if (cSGroupRes == null || cSGroupRes.getList_res() == null || cSGroupRes.getList_res().size() <= 0) {
                return;
            }
            if (cSGroupRes != null) {
                if (cSGroupRes instanceof CSStickerGroup) {
                    CSStickerRes cSStickerRes = (CSStickerRes) cSGroupRes.getList_res().get(0);
                    if (cSStickerRes.getImageType() == DMWBRes.LocationType.ASSERT) {
                        this.imageView_main.setImageBitmap(DMBitmapCrop.cropCenterScaleBitmap(cSGroupRes.getIconFileName() != null ? DMBitmapDbUtil.getImageFromAssetsFile(CSSettingListAdapter.this.mContext, cSGroupRes.getIconFileName()) : DMBitmapDbUtil.getImageFromAssetsFile(CSSettingListAdapter.this.mContext, cSStickerRes.getImageFileName()), 100, 100));
                    } else if (cSStickerRes.getImageType() == DMWBRes.LocationType.ONLINE) {
                        if (cSGroupRes.getIconFileName() != null) {
                            decodeFile = BitmapFactory.decodeFile(cSGroupRes.getIconFileName());
                        } else {
                            decodeFile = BitmapFactory.decodeFile(cSStickerRes.getImageFileName());
                        }
                        this.imageView_main.setImageBitmap(DMBitmapCrop.cropCenterScaleBitmap(decodeFile, 100, 100));
                    }
                    if (cSGroupRes.getGroupType() == CSGroupRes.GroupType.ONLINE) {
                        String str = DMPreferencesUtil.get(CSSettingListAdapter.this.mContext, "group_names", cSGroupRes.getName());
                        if (str != null) {
                            this.textView_main.setText(str);
                        } else {
                            this.textView_main.setText("");
                        }
                    } else if (cSGroupRes.getName() != null) {
                        TextView textView = this.textView_main;
                        textView.setText(cSGroupRes.getName() + "");
                    } else {
                        this.textView_main.setText("");
                    }
                } else {
                    if (cSGroupRes.getIconType() == DMWBRes.LocationType.ASSERT) {
                        this.imageView_main.setImageBitmap(DMBitmapDbUtil.getImageFromAssetsFile(CSSettingListAdapter.this.mContext, cSGroupRes.getIconFileName()));
                    } else if (cSGroupRes.getIconType() == DMWBRes.LocationType.ONLINE) {
                        this.imageView_main.setImageBitmap(DMBitmapUtil.getImageFromSDFile(CSSettingListAdapter.this.mContext, cSGroupRes.getIconFileName()));
                    }
                    if (cSGroupRes.getName() != null) {
                        TextView textView2 = this.textView_main;
                        textView2.setText(cSGroupRes.getShowText() + "");
                    } else {
                        this.textView_main.setText("");
                    }
                }
            }
            if (cSGroupRes.getGroupType() == CSGroupRes.GroupType.ONLINE) {
                this.btn_delete.setVisibility(View.VISIBLE);
            } else {
                this.btn_delete.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setOnStickerSettingItemClikListener(onStickerSettingItemClikListener onstickersettingitemcliklistener) {
        this.mListener = onstickersettingitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }

    private static void delFolder(String str) {
        if (str == null) {
            return;
        }
        try {
            delAllFile(str);
            new File(str.toString()).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean delAllFile(String str) {
        File file;
        File file2 = new File(str);
        if (file2.exists() && file2.isDirectory()) {
            String[] list = file2.list();
            boolean z = false;
            for (int i = 0; i < list.length; i++) {
                if (str.endsWith(File.separator)) {
                    file = new File(str + list[i]);
                } else {
                    file = new File(str + File.separator + list[i]);
                }
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    delAllFile(str + "/" + list[i]);
                    delFolder(str + "/" + list[i]);
                    z = true;
                }
            }
            return z;
        }
        return false;
    }
}
