package com.picspool.libfuncview.setting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.effect.res.CSEffectRes;
import com.picspool.libfuncview.res.CSResManagerInterface;
import java.io.File;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSettingEffectListAdapter extends RecyclerView.Adapter<CSSettingEffectListAdapter.MyViewHolder> {
    private List<DMWBRes> BMWBResList;
    private Context mContext;
    private onStickerSettingItemClikListener mListener;
    private CSResManagerInterface resManagerInterface;
    private int selectedPos = -1;

    /* loaded from: classes.dex */
    public interface onStickerSettingItemClikListener {
        void onClick(int i);

        void onDragTouched(boolean z);

        void onDragTouched(boolean z, MyViewHolder myViewHolder);
    }

    public CSSettingEffectListAdapter(Context context, List<DMWBRes> list, CSResManagerInterface cSResManagerInterface) {
        this.mContext = context;
        this.BMWBResList = list;
        this.resManagerInterface = cSResManagerInterface;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_adapteritem_settingeffectlist, viewGroup, false));
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
            this.ly_drag.setOnTouchListener(new View.OnTouchListener() { // from class: com.picspool.libfuncview.setting.adapter.CSSettingEffectListAdapter.MyViewHolder.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    CSSettingEffectListAdapter.this.mListener.onDragTouched(true, MyViewHolder.this);
                    return false;
                }
            });
            this.btn_delete.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.setting.adapter.CSSettingEffectListAdapter.MyViewHolder.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    DMWBRes dMWBRes = (DMWBRes) CSSettingEffectListAdapter.this.BMWBResList.get(MyViewHolder.this.getAdapterPosition());
                    StringBuilder sb = new StringBuilder();
                    CSEffectRes cSEffectRes = (CSEffectRes) dMWBRes;
                    sb.append(cSEffectRes.getGroupName());
                    sb.append(dMWBRes.getName());
                    String sb2 = sb.toString();
                    if (dMWBRes instanceof CSEffectRes) {
                        CSSettingEffectListAdapter.delFolder(cSEffectRes.getOlFilePath());
                        if (DMPreferencesUtil.get(CSSettingEffectListAdapter.this.mContext, CSSettingEffectListAdapter.this.resManagerInterface.getOrderKey(), sb2) != null) {
                            DMPreferencesUtil.save(CSSettingEffectListAdapter.this.mContext, CSSettingEffectListAdapter.this.resManagerInterface.getOrderKey(), sb2, "0");
                        }
                    }
                    CSSettingEffectListAdapter.this.BMWBResList.remove(MyViewHolder.this.getAdapterPosition());
                    CSSettingEffectListAdapter.this.notifyItemRemoved(MyViewHolder.this.getAdapterPosition());
                }
            });
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.setting.adapter.CSSettingEffectListAdapter.MyViewHolder.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (CSSettingEffectListAdapter.this.mListener != null) {
                        CSSettingEffectListAdapter.this.mListener.onClick(MyViewHolder.this.getAdapterPosition());
                    }
                    CSSettingEffectListAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    CSSettingEffectListAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            if (dMWBRes != null) {
                if (dMWBRes.getIconType() == DMWBRes.LocationType.ASSERT) {
                    this.imageView_main.setImageBitmap(DMBitmapCrop.cropCenterScaleBitmap(DMBitmapDbUtil.getImageFromAssetsFile(CSSettingEffectListAdapter.this.mContext, dMWBRes.getIconFileName()), 100, 100));
                } else if (dMWBRes.getIconType() == DMWBRes.LocationType.ONLINE) {
                    this.imageView_main.setImageBitmap(DMBitmapCrop.cropCenterScaleBitmap(DMBitmapUtil.getImageFromSDFile(CSSettingEffectListAdapter.this.mContext, dMWBRes.getIconFileName()), 100, 100));
                }
                if (dMWBRes.getName() != null) {
                    TextView textView = this.textView_main;
                    textView.setText(dMWBRes.getShowText() + "");
                } else {
                    this.textView_main.setText("");
                }
            }
            if (dMWBRes.getIconType() == DMWBRes.LocationType.ONLINE) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static void delFolder(String str) {
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

    private static boolean delAllFile(String str) {
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
