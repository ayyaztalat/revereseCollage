package com.winflag.libsquare.uiview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sky.testproject.R;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class CSSquareUiBlurAdjustView extends FrameLayout {
    private RecyclerView bitmapList;
    private BitmapListAdapter bitmapListAdapter;
    private ArrayList<BitmapStatus> bitmapResList;
    private ImageView chooseBg;
    private View layout_close;
    private OnSquareUiBlurAdjustViewListener onEventListener;
    private SeekBar seekBlur;

    /* loaded from: classes3.dex */
    public interface OnSquareUiBlurAdjustViewListener {
        void blurRatioChange(float f);

        void chooseBg();

        void onBlurClose();

        void resetBg(Bitmap bitmap);
    }

    public void setCurrentBitmap(Bitmap bitmap) {
        for (int i = 0; i < this.bitmapResList.size(); i++) {
            this.bitmapResList.get(i).selected = false;
        }
        this.bitmapResList.add(new BitmapStatus(bitmap, true));
        BitmapListAdapter bitmapListAdapter = this.bitmapListAdapter;
        if (bitmapListAdapter != null) {
            bitmapListAdapter.notifyDataSetChanged();
        }
    }

    public CSSquareUiBlurAdjustView(Context context) {
        super(context);
        this.bitmapResList = new ArrayList<>();
        init(context);
    }

    public CSSquareUiBlurAdjustView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.bitmapResList = new ArrayList<>();
        init(context);
    }

    public void setOnSquareUiBlurAdjustViewListener(OnSquareUiBlurAdjustViewListener onSquareUiBlurAdjustViewListener) {
        this.onEventListener = onSquareUiBlurAdjustViewListener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_blur_adjust, (ViewGroup) this, true);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBlur);
        this.seekBlur = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.winflag.libsquare.uiview.CSSquareUiBlurAdjustView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                float progress = CSSquareUiBlurAdjustView.this.seekBlur.getProgress() / 100.0f;
                if (progress != 0.0f || CSSquareUiBlurAdjustView.this.onEventListener == null) {
                    return;
                }
                CSSquareUiBlurAdjustView.this.onEventListener.blurRatioChange(progress);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                CSSquareUiBlurAdjustView.this.onEventListener.blurRatioChange(CSSquareUiBlurAdjustView.this.seekBlur.getProgress() / 100.0f);
            }
        });
        View findViewById = findViewById(R.id.ly_close);
        this.layout_close = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.winflag.libsquare.uiview.CSSquareUiBlurAdjustView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiBlurAdjustView.this.onEventListener != null) {
                    CSSquareUiBlurAdjustView.this.onEventListener.onBlurClose();
                }
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.choose_bg);
        this.chooseBg = imageView;
        imageView.setOnClickListener(new OnClickListener() { // from class: com.winflag.libsquare.uiview.CSSquareUiBlurAdjustView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareUiBlurAdjustView.this.onEventListener != null) {
                    CSSquareUiBlurAdjustView.this.onEventListener.chooseBg();
                }
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.bitmap_list);
        this.bitmapList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        BitmapListAdapter bitmapListAdapter = new BitmapListAdapter(this.bitmapResList);
        this.bitmapListAdapter = bitmapListAdapter;
        this.bitmapList.setAdapter(bitmapListAdapter);
    }

    public void setCurrentRatio(float f) {
        this.seekBlur.setProgress((int) (f * 100.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class BitmapListAdapter extends RecyclerView.Adapter<BitmapListAdapter.BitmapViewHolder> {
        private final ArrayList<BitmapStatus> bitmapList;

        public BitmapListAdapter(ArrayList<BitmapStatus> arrayList) {
            this.bitmapList = arrayList;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public BitmapViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new BitmapViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_blur_bitmap, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(BitmapViewHolder bitmapViewHolder, @SuppressLint("RecyclerView") final int i) {
            BitmapStatus bitmapStatus = this.bitmapList.get(i);
            final Bitmap bitmap = bitmapStatus.bitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmapViewHolder.imgBitmap.setImageBitmap(bitmap);
                if (bitmapStatus.selected) {
                    bitmapViewHolder.imgStatus.setVisibility(View.VISIBLE);
                } else {
                    bitmapViewHolder.imgStatus.setVisibility(View.GONE);
                }
            }
            bitmapViewHolder.imgBitmap.setOnClickListener(new OnClickListener() { // from class: com.winflag.libsquare.uiview.CSSquareUiBlurAdjustView.BitmapListAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (CSSquareUiBlurAdjustView.this.onEventListener != null) {
                        for (int i2 = 0; i2 < CSSquareUiBlurAdjustView.this.bitmapResList.size(); i2++) {
                            if (i == i2) {
                                ((BitmapStatus) CSSquareUiBlurAdjustView.this.bitmapResList.get(i2)).selected = true;
                            } else {
                                ((BitmapStatus) CSSquareUiBlurAdjustView.this.bitmapResList.get(i2)).selected = false;
                            }
                        }
                        CSSquareUiBlurAdjustView.this.onEventListener.resetBg(bitmap);
                        BitmapListAdapter.this.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            ArrayList<BitmapStatus> arrayList = this.bitmapList;
            if (arrayList == null || arrayList.isEmpty()) {
                return 0;
            }
            return this.bitmapList.size();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes3.dex */
        public class BitmapViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imgBitmap;
            private final ImageView imgStatus;

            public BitmapViewHolder(View view) {
                super(view);
                this.imgBitmap = (ImageView) view.findViewById(R.id.img_bitmap);
                this.imgStatus = (ImageView) view.findViewById(R.id.img_status);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class BitmapStatus {
        Bitmap bitmap;
        boolean selected;

        BitmapStatus(Bitmap bitmap, boolean z) {
            this.selected = false;
            this.bitmap = bitmap;
            this.selected = z;
        }
    }
}
