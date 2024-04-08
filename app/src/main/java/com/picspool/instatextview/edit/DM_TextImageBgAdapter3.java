package com.picspool.instatextview.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.sky.testproject.R;
import com.picspool.instatextview.resource.manager.DMTextBgJsonParse;
import com.picspool.instatextview.resource.manager.DMTextBgManager;
import com.picspool.instatextview.resource.manager.DMTextBgRes;
import com.picspool.lib.color.DMColorConvert;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes3.dex */
public class DM_TextImageBgAdapter3 extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private DMEditTextView3 editTextView;
    private DMTextBgManager mBMTextBgManager;
    private DM_TextFixedView3 textFixedView;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DM_TextImageBgAdapter3(Context context, DM_TextFixedView3 dM_TextFixedView3, DMEditTextView3 dMEditTextView3) {
        this.context = context;
        this.textFixedView = dM_TextFixedView3;
        this.editTextView = dMEditTextView3;
        this.mBMTextBgManager = DMTextBgManager.getSingletonInstance(context);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mBMTextBgManager.getCount();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.mBMTextBgManager.getRes(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = (LinearLayout) ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_image_background_item, (ViewGroup) null);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.img_text_image_bg);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
        }
        if (this.mBMTextBgManager != null) {
            holder.imageView.setImageBitmap(this.mBMTextBgManager.getRes(i).getIconBitmap());
        }
        return view;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.editTextView.resetTextBgResNullable();
        if (i == 0) {
            clearBgRes();
            return;
        }
        DMTextBgRes res = this.mBMTextBgManager.getRes(i);
        if (res == null) {
            return;
        }
        bgClicked(res);
    }

    public void clearBgRes() {
        this.textFixedView.getTextDrawer().setEditTextBackgroundDrawable(null, null, 0, 0, 0, 0, 0, 0);
        this.editTextView.resetEditTextLayout(false, 0.0f, 0.0f);
        this.textFixedView.setTextFixedViewBackgroundDrawable(null);
        this.textFixedView.invalidate();
    }

    public void bgClicked(DMTextBgRes dMTextBgRes) {
        Bitmap localImageBitmap;
        int i;
        int i2;
        DMTextBgRes CreateObjectFromJSON = DMTextBgJsonParse.CreateObjectFromJSON(dMTextBgRes, this.context);
        if (CreateObjectFromJSON == null || (localImageBitmap = CreateObjectFromJSON.getLocalImageBitmap()) == null || localImageBitmap.isRecycled()) {
            return;
        }
        int textBgContainerW = CreateObjectFromJSON.getTextBgContainerW();
        int textBgContainerH = CreateObjectFromJSON.getTextBgContainerH();
        int screenWidth = DMScreenInfoUtil.screenWidth(this.context);
        float f = textBgContainerW;
        float f2 = textBgContainerH;
        float f3 = ((f * 1.0f) / f2) * 1.0f;
        if (textBgContainerW > textBgContainerH) {
            i2 = (int) (screenWidth / 2.0f);
            i = (int) (((i2 * 1.0f) / f3) * 1.0f);
        } else {
            i = (int) (screenWidth / 2.0f);
            i2 = (int) (i * 1.0f * f3);
        }
        int i3 = i2;
        int i4 = i;
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.context.getResources(), localImageBitmap);
        this.textFixedView.getTextDrawer().setEditTextBackgroundDrawable(bitmapDrawable, CreateObjectFromJSON, i3, i4, (int) ((((CreateObjectFromJSON.getTextBgX() * i3) * 1.0f) / f) * 1.0f), (int) ((((CreateObjectFromJSON.getTextBgY() * i4) * 1.0f) / f2) * 1.0f), (int) ((((CreateObjectFromJSON.getTextBgW() * i3) * 1.0f) / f) * 1.0f), (int) ((((CreateObjectFromJSON.getTextBgH() * i4) * 1.0f) / f2) * 1.0f));
        this.textFixedView.getTextDrawer().getPaint().setColor(DMColorConvert.convertToColorInt(CreateObjectFromJSON.getTextBgTextColor()));
        bitmapDrawable.setBounds(0, 0, i3, i4);
        this.editTextView.resetEditTextLayout(true, i3, i4);
        this.textFixedView.setTextFixedViewBackgroundDrawable(bitmapDrawable);
        this.textFixedView.invalidate();
    }

    /* loaded from: classes3.dex */
    private static class Holder {
        public ImageView imageView;

        private Holder() {
        }

        private void recycleImageView(ImageView imageView) {
            if (imageView != null) {
                Drawable drawable = imageView.getDrawable();
                imageView.setImageBitmap(null);
                if (drawable != null) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bitmapDrawable.setCallback(null);
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    if (bitmap == null || bitmap.isRecycled()) {
                        return;
                    }
                    bitmap.recycle();
                }
            }
        }

        public void dispose() {
            recycleImageView(this.imageView);
        }
    }
}
