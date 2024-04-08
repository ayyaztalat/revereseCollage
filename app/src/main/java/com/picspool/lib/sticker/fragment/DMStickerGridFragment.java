package com.picspool.lib.sticker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.fragment.app.Fragment;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.sticker.enumoperations.DMStickerTypeOperation;
import com.picspool.lib.sticker.resource.adapter.DMStickersGridAdapter;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMStickerGridFragment extends Fragment {
    private OnStickerIconItemClickListener listener;
    private DMStickersGridAdapter mAdapter;
    private DMStickerTypeOperation.StickerType mCurrDataType;
    private GridView mGridView;

    /* loaded from: classes3.dex */
    public interface OnStickerIconItemClickListener {
        void onStickerIconItemClick(DMWBImageRes dMWBImageRes, DMStickerTypeOperation.StickerType stickerType);
    }

    public DMStickerGridFragment(DMStickerTypeOperation.StickerType stickerType) {
        this.mCurrDataType = stickerType;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.dmsticker_grid_fragment, viewGroup, false);
        this.mGridView = (GridView) inflate.findViewById(R.id.sticker_gridView);
        DMStickersGridAdapter dMStickersGridAdapter = new DMStickersGridAdapter();
        this.mAdapter = dMStickersGridAdapter;
        dMStickersGridAdapter.setContext(getActivity());
        this.mAdapter.initData(this.mCurrDataType);
        this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.picspool.lib.sticker.fragment.DMStickerGridFragment.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                DMWBImageRes dMWBImageRes = (DMWBImageRes) DMStickerGridFragment.this.mAdapter.getItem(i);
                if (DMStickerGridFragment.this.listener != null) {
                    DMStickerGridFragment.this.listener.onStickerIconItemClick(dMWBImageRes, DMStickerGridFragment.this.mCurrDataType);
                }
            }
        });
        this.mGridView.setAdapter((ListAdapter) this.mAdapter);
        return inflate;
    }

    public void setOnStickerIconItemClickListener(OnStickerIconItemClickListener onStickerIconItemClickListener) {
        this.listener = onStickerIconItemClickListener;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        clearBitmapMemory();
        super.onDestroy();
    }

    public void clearBitmapMemory() {
        DMStickersGridAdapter dMStickersGridAdapter = this.mAdapter;
        if (dMStickersGridAdapter != null) {
            dMStickersGridAdapter.clearAll();
        }
    }
}
