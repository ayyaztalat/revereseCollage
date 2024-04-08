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
import java.util.ArrayList;
import java.util.List;
import com.sky.testproject.R;
import com.picspool.instatextview.resource.manager.DMTextEmojiManager;
import com.picspool.instatextview.resource.manager.DMTextEmojiSelectedManager;

/* loaded from: classes3.dex */
public class DM_TextEmojiAdapter3 extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private DM_TextFixedView3 editText;
    private List<Holder> holderList = new ArrayList();
    private DMTextEmojiManager mEmojiManager;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DM_TextEmojiAdapter3(Context context, DMTextEmojiManager dMTextEmojiManager) {
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
            view = (LinearLayout) ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.dm_text_text_emoji_item, (ViewGroup) null);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.text_emoji_iamge);
            view.setTag(holder);
            this.holderList.add(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
        }
        if (this.mEmojiManager != null) {
            holder.imageView.setImageBitmap(this.mEmojiManager.getRes(i).getIconBitmap(holder.imageView));
        }
        return view;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        String contentText = this.editText.getContentText();
        DMTextEmojiSelectedManager.getInstance().addSelectedTextEmoji(contentText.replaceAll("\n", "").length(), i);
        DM_TextFixedView3 dM_TextFixedView3 = this.editText;
        dM_TextFixedView3.setContentText(contentText + "人");
        DM_TextFixedView3 dM_TextFixedView32 = this.editText;
        dM_TextFixedView32.setText(contentText + "人");
        this.editText.invalidate();
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

    public void setEditText(DM_TextFixedView3 dM_TextFixedView3) {
        this.editText = dM_TextFixedView3;
    }
}
