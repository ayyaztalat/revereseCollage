package com.picspool.lib.sysphotoselector;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.fragment.app.Fragment;
import java.util.List;
import com.picspool.lib.service.DMImageMediaItem;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMPhotoGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "DMPhotoGridFragment";
    private Context context;
    private OnPhotoItemSelected itemSelectedListener;
    private DMPhotoGridAdapter mAdapter;
    private List<DMImageMediaItem> mData;
    private GridView mGridView;
    private boolean isSingleSelector = false;
    private int thumbPicWidth = 0;
    private int gridColumnCnt = 4;
    private int GridColumnSpacingPix = 0;
    private int GridRowSpacingPix = 0;

    /* loaded from: classes3.dex */
    public interface OnPhotoItemSelected {
        void photoItemSelected(DMImageMediaItem dMImageMediaItem, View view);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public static DMPhotoGridFragment newInstance(String str) {
        DMPhotoGridFragment dMPhotoGridFragment = new DMPhotoGridFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG, str);
        dMPhotoGridFragment.setArguments(bundle);
        return dMPhotoGridFragment;
    }

    public static DMPhotoGridFragment newInstance(int i) {
        DMPhotoGridFragment dMPhotoGridFragment = new DMPhotoGridFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("thumbPicWidth", i);
        dMPhotoGridFragment.setArguments(bundle);
        return dMPhotoGridFragment;
    }

    public void removeFromSelectImgList(String str) {
        this.mAdapter.removeFromSelectImgList(str);
    }

    public void addToSelectImgList(String str) {
        this.mAdapter.addToSelectImgList(str);
    }

    public void resetSelectImgList(List<String> list) {
        this.mAdapter.resetSelectImgList(list);
    }

    public void setSingleSelector(boolean z) {
        this.isSingleSelector = z;
    }

    public void setGrideColumnCnt(int i) {
        this.gridColumnCnt = i;
        GridView gridView = this.mGridView;
        if (gridView != null) {
            gridView.setNumColumns(i);
            if (this.mAdapter != null) {
                int screenWidth = DMScreenInfoUtil.screenWidth(this.context);
                int i2 = this.GridColumnSpacingPix;
                int i3 = this.gridColumnCnt;
                int i4 = (screenWidth - (i2 * (i3 + 1))) / i3;
                this.mAdapter.setThumbBitmapInfo(i4, (((DMScreenInfoUtil.screenHeight(this.context) / i4) + 2) * this.gridColumnCnt) + 3);
            }
        }
    }

    public void setGridViewColumnSpacing(int i, int i2) {
        this.GridColumnSpacingPix = i;
        this.GridRowSpacingPix = i2;
        GridView gridView = this.mGridView;
        if (gridView != null) {
            gridView.setVerticalSpacing(i2);
            this.mGridView.setHorizontalSpacing(this.GridColumnSpacingPix);
        }
    }

    public void setOnPhotoItemSelected(OnPhotoItemSelected onPhotoItemSelected) {
        this.itemSelectedListener = onPhotoItemSelected;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDisplayData(List<DMImageMediaItem> list, boolean z) {
        clearBitmapMemory();
        this.mData = list;
        DMPhotoGridAdapter dMPhotoGridAdapter = new DMPhotoGridAdapter(this.context);
        this.mAdapter = dMPhotoGridAdapter;
        dMPhotoGridAdapter.setGridView(this.mGridView);
        this.mAdapter.registerDataSetObserver(new MyDataSetObserver());
        this.mAdapter.setPhotoItems(list);
        GridView gridView = this.mGridView;
        if (gridView != null) {
            gridView.setAdapter((ListAdapter) this.mAdapter);
            this.mGridView.setNumColumns(this.gridColumnCnt);
            this.mGridView.setVerticalSpacing(this.GridRowSpacingPix);
            this.mGridView.setHorizontalSpacing(this.GridColumnSpacingPix);
            int screenWidth = DMScreenInfoUtil.screenWidth(this.context);
            int i = this.GridColumnSpacingPix;
            int i2 = this.gridColumnCnt;
            int i3 = (screenWidth - (i * (i2 + 1))) / i2;
            this.mAdapter.setThumbBitmapInfo(i3, (((DMScreenInfoUtil.screenHeight(this.context) / i3) + 2) * this.gridColumnCnt) + 3);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(false);
        try {
            this.thumbPicWidth = getArguments().getInt("thumbPicWidth");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        clearBitmapMemory();
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate;
        if (this.context == null) {
            this.context = getActivity();
        }
        if (this.isSingleSelector) {
            inflate = layoutInflater.inflate(R.layout.dm_single_image_grid_fragment, viewGroup, false);
        } else {
            inflate = layoutInflater.inflate(R.layout.dm_mult_image_grid_fragment, viewGroup, false);
        }
        GridView gridView = (GridView) inflate.findViewById(R.id.gridView);
        this.mGridView = gridView;
        gridView.setNumColumns(this.gridColumnCnt);
        this.mGridView.setVerticalSpacing(this.GridRowSpacingPix);
        this.mGridView.setHorizontalSpacing(this.GridColumnSpacingPix);
        this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.picspool.lib.sysphotoselector.DMPhotoGridFragment.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (DMPhotoGridFragment.this.itemSelectedListener != null) {
                    DMPhotoGridFragment.this.itemSelectedListener.photoItemSelected((DMImageMediaItem) DMPhotoGridFragment.this.mAdapter.getItem(i), view);
                }
            }
        });
        if (this.mAdapter == null) {
            DMPhotoGridAdapter dMPhotoGridAdapter = new DMPhotoGridAdapter(getActivity());
            this.mAdapter = dMPhotoGridAdapter;
            dMPhotoGridAdapter.registerDataSetObserver(new MyDataSetObserver());
        }
        this.mAdapter.setGridView(this.mGridView);
        int screenWidth = DMScreenInfoUtil.screenWidth(this.context);
        int i = this.GridColumnSpacingPix;
        int i2 = this.gridColumnCnt;
        int i3 = (screenWidth - (i * (i2 + 1))) / i2;
        this.mAdapter.setThumbBitmapInfo(i3, (((DMScreenInfoUtil.screenHeight(this.context) / i3) + 2) * this.gridColumnCnt) + 3);
        this.mGridView.setAdapter((ListAdapter) this.mAdapter);
        return inflate;
    }

    public void clearBitmapMemory() {
        DMPhotoGridAdapter dMPhotoGridAdapter = this.mAdapter;
        if (dMPhotoGridAdapter != null) {
            dMPhotoGridAdapter.clearAll();
        }
    }

    public void dispose() {
        DMPhotoGridAdapter dMPhotoGridAdapter = this.mAdapter;
        if (dMPhotoGridAdapter != null) {
            dMPhotoGridAdapter.dispose();
        }
    }

    /* loaded from: classes3.dex */
    class MyDataSetObserver extends DataSetObserver {
        @Override // android.database.DataSetObserver
        public void onChanged() {
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
        }

        MyDataSetObserver() {
        }
    }

    public void setThumbPicWidth(int i) {
        this.thumbPicWidth = i;
    }
}
