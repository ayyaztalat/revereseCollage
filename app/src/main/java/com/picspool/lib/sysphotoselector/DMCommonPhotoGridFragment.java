package com.picspool.lib.sysphotoselector;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
public class DMCommonPhotoGridFragment extends Fragment {
    private static final String TAG = "CommonPhotoGridFragment";
    private Context context;
    private OnCommonPhotoItemSelectedListener itemSelectedListener;
    private DMCommonPhotoGridAdapter mAdapter;
    private GridView mGridView;
    private boolean isSingleSelector = false;
    private int isMultiSelector = 1;
    private int gridColumnCnt = 3;
    private int GridColumnSpacingPix = 0;
    private int GridRowSpacingPix = 0;

    /* loaded from: classes3.dex */
    public interface OnCommonPhotoItemSelectedListener {
        void commonPhotoItemSelected(DMImageMediaItem dMImageMediaItem, View view);

        void exceedGivenMaxPhotoCount();
    }

    public static DMCommonPhotoGridFragment newInstance(String str) {
        DMCommonPhotoGridFragment dMCommonPhotoGridFragment = new DMCommonPhotoGridFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TAG", str);
        dMCommonPhotoGridFragment.setArguments(bundle);
        return dMCommonPhotoGridFragment;
    }

    public void setGridViewColumnCnt(int i) {
        this.gridColumnCnt = i;
        GridView gridView = this.mGridView;
        if (gridView != null) {
            gridView.setNumColumns(i);
        }
    }

    public void setSingleSelector(boolean z) {
        this.isSingleSelector = z;
    }

    public void setMultiSelector(int i) {
        this.isMultiSelector = i;
    }

    public void setOnCommonPhotoItemSelectedListener(OnCommonPhotoItemSelectedListener onCommonPhotoItemSelectedListener) {
        this.itemSelectedListener = onCommonPhotoItemSelectedListener;
    }

    public void setContext(Context context) {
        this.context = context;
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

    public void setDisplayData(final List<DMImageMediaItem> list, boolean z) {
        clearBitmapMemory();
        new Handler().post(new Runnable() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoGridFragment.1
            @Override // java.lang.Runnable
            public void run() {
                DMCommonPhotoGridFragment.this.mAdapter.setPhotoItems(list);
                DMCommonPhotoGridFragment.this.mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(false);
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
            this.context = getActivity().getApplicationContext();
        }
        if (this.isSingleSelector) {
            inflate = layoutInflater.inflate(R.layout.dm_single_image_grid_fragment, viewGroup, false);
        } else {
            inflate = layoutInflater.inflate(R.layout.dm_mult_image_grid_fragment, viewGroup, false);
        }
        this.mGridView = (GridView) inflate.findViewById(R.id.gridView);
        if (this.mAdapter == null) {
            this.mAdapter = new DMCommonPhotoGridAdapter(this.context, this.isMultiSelector);
        }
        int screenWidth = DMScreenInfoUtil.screenWidth(this.context);
        int i = this.GridColumnSpacingPix;
        int i2 = this.gridColumnCnt;
        int i3 = (screenWidth - (i * (i2 + 1))) / i2;
        this.mAdapter.setThumbBitmapInfo(i3, (((DMScreenInfoUtil.screenHeight(this.context) / i3) + 2) * this.gridColumnCnt) + 3);
        this.mAdapter.setGridView(this.mGridView);
        this.mGridView.setAdapter((ListAdapter) this.mAdapter);
        this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoGridFragment.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i4, long j) {
                if (DMCommonPhotoGridFragment.this.itemSelectedListener != null) {
                    if (DMCommonSelectedManager.getInstance().getCountOfAllValus()) {
                        DMImageMediaItem dMImageMediaItem = (DMImageMediaItem) DMCommonPhotoGridFragment.this.mAdapter.getItem(i4);
                        DMCommonPhotoGridFragment.this.itemSelectedListener.commonPhotoItemSelected(dMImageMediaItem, view);
                        DMCommonSelectedManager.getInstance().addToSelectImgList(dMImageMediaItem.getImgId());
                        DMCommonPhotoGridFragment.this.mAdapter.notifyDataSetChanged();
                        return;
                    }
                    DMCommonPhotoGridFragment.this.itemSelectedListener.exceedGivenMaxPhotoCount();
                }
            }
        });
        return inflate;
    }

    public void clearBitmapMemory() {
        DMCommonPhotoGridAdapter dMCommonPhotoGridAdapter = this.mAdapter;
        if (dMCommonPhotoGridAdapter != null) {
            dMCommonPhotoGridAdapter.clearAll();
        }
    }

    public void dispose() {
        DMCommonPhotoGridAdapter dMCommonPhotoGridAdapter = this.mAdapter;
        if (dMCommonPhotoGridAdapter != null) {
            dMCommonPhotoGridAdapter.dispose();
        }
    }
}
