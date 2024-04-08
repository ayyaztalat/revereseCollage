package com.picspool.lib.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.List;
import com.picspool.lib.service.DMImageMediaItem;
import com.picspool.lib.view.DMPhotoChooseScrollView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMPhotoChooseBarView extends FrameLayout implements DMPhotoChooseScrollView.ItemDeleteCallback {
    String confirm;
    OnChooseClickListener listener;
    int max;
    int min;
    DMPhotoChooseScrollView photoChooseScrollView1;
    TextView txTip;

    /* loaded from: classes3.dex */
    public interface OnChooseClickListener {
        void ItemDelete(DMImageMediaItem dMImageMediaItem);

        void choosedClick(List<Uri> list);

        void choosedClick2(List<Uri> list, List<DMImageMediaItem> list2);
    }

    public DMPhotoChooseBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.max = 9;
        this.min = 1;
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_selector_bar_view, (ViewGroup) this, true);
        DMPhotoChooseScrollView dMPhotoChooseScrollView = (DMPhotoChooseScrollView) findViewById(R.id.photoChooseScrollView1);
        this.photoChooseScrollView1 = dMPhotoChooseScrollView;
        dMPhotoChooseScrollView.setCallback(this);
    }

    public void setMax(int i) {
        this.max = i;
        String.valueOf(i);
    }

    public int getMax() {
        return this.max;
    }

    public void ClickToNext() {
        if (this.listener != null) {
            List<Uri> choosedUris = this.photoChooseScrollView1.getChoosedUris();
            List<DMImageMediaItem> choosedMeidiaItem = this.photoChooseScrollView1.getChoosedMeidiaItem();
            if (choosedUris.size() > 0) {
                this.listener.choosedClick(choosedUris);
                this.listener.choosedClick2(choosedUris, choosedMeidiaItem);
            }
        }
    }

    public List<Uri> getChoosedUris() {
        return this.photoChooseScrollView1.getChoosedUris();
    }

    public void setOnChooseClickListener(OnChooseClickListener onChooseClickListener) {
        this.listener = onChooseClickListener;
    }

    public int getItemCount() {
        return this.photoChooseScrollView1.getCount();
    }

    public void addItem(DMImageMediaItem dMImageMediaItem) {
        if (this.photoChooseScrollView1.getCount() < this.max) {
            this.photoChooseScrollView1.addItem(dMImageMediaItem);
        }
    }

    @Override // com.picspool.lib.view.DMPhotoChooseScrollView.ItemDeleteCallback
    public void ItemDelete(DMImageMediaItem dMImageMediaItem) {
        OnChooseClickListener onChooseClickListener = this.listener;
        if (onChooseClickListener != null) {
            onChooseClickListener.ItemDelete(dMImageMediaItem);
        }
    }

    public void dispose() {
        DMPhotoChooseScrollView dMPhotoChooseScrollView = this.photoChooseScrollView1;
        if (dMPhotoChooseScrollView != null) {
            dMPhotoChooseScrollView.dispose();
        }
        this.photoChooseScrollView1 = null;
    }
}
