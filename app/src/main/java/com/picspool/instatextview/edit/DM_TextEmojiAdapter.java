package com.picspool.instatextview.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import com.sky.testproject.R;
import com.picspool.instatextview.resource.DMTextEmojiRes;
import com.picspool.instatextview.resource.manager.DMTextEmojiManager;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes3.dex */
public class DM_TextEmojiAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private DM_TextFixedView editText;
    private List<Holder> holderList = new ArrayList();
    private DMTextEmojiManager mEmojiManager;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DM_TextEmojiAdapter(Context context, DMTextEmojiManager dMTextEmojiManager) {
        this.mEmojiManager = null;
        this.context = context;
        this.mEmojiManager = dMTextEmojiManager;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        DMTextEmojiManager dMTextEmojiManager = this.mEmojiManager;
        if (dMTextEmojiManager != null) {
            return dMTextEmojiManager.getCount();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        DMTextEmojiManager dMTextEmojiManager = this.mEmojiManager;
        if (dMTextEmojiManager != null) {
            return dMTextEmojiManager.getRes(i);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = (LinearLayout) ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_text_emoji_item, (ViewGroup) null);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.text_emoji_iamge);
            view.setTag(holder);
            this.holderList.add(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
        }
        if (this.mEmojiManager != null) {
            holder.imageView.setImageBitmap(this.mEmojiManager.getRes(i).getIconBitmap());
        }
        return view;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        String str;
        DMTextEmojiRes res = this.mEmojiManager.getRes(i);
        int dip2px = DMScreenInfoUtil.dip2px(this.context, 25.0f);
        Bitmap iconBitmap = res.getIconBitmap(dip2px, dip2px);
        if (iconBitmap == null || iconBitmap.isRecycled()) {
            return;
        }
        if (i < 10) {
            str = "00" + i;
        } else if (i < 100) {
            str = "0" + i;
        } else {
            str = "" + i;
        }
        SpannableString spannableString = new SpannableString("[text_" + str + "_text]");
        BitmapDrawable bitmapDrawable = new BitmapDrawable(iconBitmap);
        bitmapDrawable.setBounds(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
        spannableString.setSpan(new DM_VerticalImageSpan(bitmapDrawable), 0, 15, 33);
        this.editText.append(spannableString);
    }

    public void dispose() {
        for (Holder holder : this.holderList) {
            holder.dispose();
        }
        this.holderList.clear();
    }

    /* loaded from: classes3.dex */
    private static class Holder {
        public Bitmap bitmap;
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

    public void setEditText(DM_TextFixedView dM_TextFixedView) {
        this.editText = dM_TextFixedView;
    }
}
