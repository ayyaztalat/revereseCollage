package com.picspool.libfuncview.adjust;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.Opcodes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSCurveBarViewAdapter extends RecyclerView.Adapter<CSCurveBarViewAdapter.MyViewHolder> {
    private Context mContext;
    private onABarViewItemClikListener mListener;
    private List<DMWBRes> resList;
    private int selectedPos = 0;
    private Bitmap srcbmp;

    /* loaded from: classes.dex */
    public interface onABarViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes);
    }

    public CSCurveBarViewAdapter(Context context, List<DMWBRes> list, Bitmap bitmap) {
        this.mContext = context;
        this.resList = list;
        this.srcbmp = DMBitmapUtil.sampeZoomFromBitmap(bitmap, Opcodes.IF_ICMPNE);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_adapter_item_curveadjust, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.resList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.resList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_main;
        TextView textView_main;
        View view_select;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.view_select = view.findViewById(R.id.ly_select);
            this.textView_main.setBackgroundResource(R.drawable.shape_rect_curve_img_titlebg);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.adjust.CSCurveBarViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSCurveBarViewAdapter.this.resList.size()) {
                        return;
                    }
                    if (CSCurveBarViewAdapter.this.mListener != null) {
                        CSCurveBarViewAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSCurveBarViewAdapter.this.resList.get(adapterPosition));
                    }
                    CSCurveBarViewAdapter.this.selectedPos = adapterPosition;
                    CSCurveBarViewAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            this.textView_main.setText(dMWBRes.getShowText());
            this.imageView_main.setVisibility(View.INVISIBLE);
            if (dMWBRes instanceof CSCurveMode) {
                ((CSCurveMode) dMWBRes).getAsyncIconBitmap(CSCurveBarViewAdapter.this.srcbmp, new DMWBAsyncPostIconListener() { // from class: com.picspool.libfuncview.adjust.CSCurveBarViewAdapter.MyViewHolder.2
                    @Override // com.picspool.lib.resource.DMWBAsyncPostIconListener
                    public void postIcon(Bitmap bitmap) {
                        MyViewHolder.this.imageView_main.setImageBitmap(bitmap);
                        MyViewHolder.this.imageView_main.setVisibility(View.VISIBLE);
                    }
                });
            }
            if (i == CSCurveBarViewAdapter.this.selectedPos) {
                this.view_select.setVisibility(View.VISIBLE);
            } else {
                this.view_select.setVisibility(View.GONE);
            }
        }
    }

    public void setOnBarViewItemClikListener(onABarViewItemClikListener onabarviewitemcliklistener) {
        this.mListener = onabarviewitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }

    private static List<PointF[]> creatListPointF() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(initPointFs(0.0f, 0.0f, 1.0f, 1.0f));
        arrayList.add(initPointFs(0.0f, 0.0f, 1.0f, 1.0f));
        arrayList.add(initPointFs(0.0f, 0.0f, 1.0f, 1.0f));
        arrayList.add(initPointFs(0.0f, 0.0f, 1.0f, 1.0f));
        return arrayList;
    }

    private static PointF[] initPointFs(float f, float f2, float f3, float f4) {
        return new PointF[]{new PointF(f, f2), new PointF(f3, f4)};
    }
}
