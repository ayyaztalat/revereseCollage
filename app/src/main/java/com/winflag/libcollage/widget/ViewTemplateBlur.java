package com.winflag.libcollage.widget;

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
import java.util.List;

/* loaded from: classes.dex */
public class ViewTemplateBlur extends FrameLayout {
    private BitmapListAdapter bitmapListAdapter;
    private ArrayList<Bitmap> bitmapResList;
    private ImageView chooseBg;
    private int current_progeress;
    private int default_progress;
    private Context mContext;
    private int mypos;
    private OnTempBlurViewListener onEventListener;
    private RecyclerView recyclerView;
    private SeekBar seekBlur;

    /* loaded from: classes.dex */
    public interface OnTempBlurViewListener {
        void blurChange(int i, int i2);

        void blurResize(int i);

        void chooseBg();

        void resetBg(Bitmap bitmap, int i);
    }

    public void setOnEventListener(OnTempBlurViewListener onTempBlurViewListener) {
        this.onEventListener = onTempBlurViewListener;
    }

    public ViewTemplateBlur(Context context) {
        super(context);
        this.mypos = -1;
        this.default_progress = 20;
        this.bitmapResList = new ArrayList<>();
        initView(context);
    }

    public ViewTemplateBlur(Context context, int i, int i2) {
        super(context);
        this.mypos = -1;
        this.default_progress = 20;
        this.bitmapResList = new ArrayList<>();
        this.current_progeress = i;
        this.mypos = i2;
        initView(context);
    }

    public ViewTemplateBlur(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mypos = -1;
        this.default_progress = 20;
        this.bitmapResList = new ArrayList<>();
        initView(context);
    }

    public ViewTemplateBlur(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mypos = -1;
        this.default_progress = 20;
        this.bitmapResList = new ArrayList<>();
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_template_blur, (ViewGroup) this, true);
        SeekBar seekBar = (SeekBar) findViewById(R.id.blurRatio);
        this.seekBlur = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.winflag.libcollage.widget.ViewTemplateBlur.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                if (ViewTemplateBlur.this.onEventListener != null) {
                    ViewTemplateBlur.this.onEventListener.blurChange(seekBar2.getProgress(), ViewTemplateBlur.this.mypos);
                }
            }
        });
        findViewById(R.id.layout_resize).setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.widget.ViewTemplateBlur.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ViewTemplateBlur.this.onEventListener != null) {
                    ViewTemplateBlur.this.mypos = -1;
                    if (ViewTemplateBlur.this.bitmapListAdapter != null) {
                        ViewTemplateBlur.this.bitmapListAdapter.notifyDataSetChanged();
                    }
                    ViewTemplateBlur.this.seekBlur.setProgress(ViewTemplateBlur.this.default_progress);
                    ViewTemplateBlur.this.onEventListener.blurResize(ViewTemplateBlur.this.default_progress);
                }
            }
        });
        this.seekBlur.setProgress(this.current_progeress);
        ImageView imageView = (ImageView) findViewById(R.id.choose_bg);
        this.chooseBg = imageView;
        imageView.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.widget.ViewTemplateBlur.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ViewTemplateBlur.this.onEventListener != null) {
                    ViewTemplateBlur.this.onEventListener.chooseBg();
                }
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.bitmap_list);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        BitmapListAdapter bitmapListAdapter = new BitmapListAdapter(this.bitmapResList);
        this.bitmapListAdapter = bitmapListAdapter;
        this.recyclerView.setAdapter(bitmapListAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class BitmapListAdapter extends RecyclerView.Adapter<BitmapListAdapter.BitmapViewHolder> {
        private final ArrayList<Bitmap> bitmapList;

        public BitmapListAdapter(ArrayList<Bitmap> arrayList) {
            this.bitmapList = arrayList;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public BitmapViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new BitmapViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_blur_bitmap, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(BitmapViewHolder bitmapViewHolder, int i) {
            bitmapViewHolder.bindData(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            ArrayList<Bitmap> arrayList = this.bitmapList;
            if (arrayList == null || arrayList.isEmpty()) {
                return 0;
            }
            return this.bitmapList.size();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes.dex */
        public class BitmapViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imgBitmap;
            private final ImageView imgStatus;

            public BitmapViewHolder(View view) {
                super(view);
                this.imgBitmap = (ImageView) view.findViewById(R.id.img_bitmap);
                this.imgStatus = (ImageView) view.findViewById(R.id.img_status);
                this.imgBitmap.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.widget.-$$Lambda$ViewTemplateBlur$BitmapListAdapter$BitmapViewHolder$8a8PekcIY38pfwEDWzfwZ7WB2U8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        BitmapViewHolder.this.lambda$new$0$ViewTemplateBlur$BitmapListAdapter$BitmapViewHolder(view2);
                    }
                });
            }

            public /* synthetic */ void lambda$new$0$ViewTemplateBlur$BitmapListAdapter$BitmapViewHolder(View view) {
                int adapterPosition;
                if (ViewTemplateBlur.this.onEventListener == null || (adapterPosition = getAdapterPosition()) >= ViewTemplateBlur.this.bitmapResList.size() || adapterPosition < 0) {
                    return;
                }
                ViewTemplateBlur.this.mypos = adapterPosition;
                ViewTemplateBlur.this.onEventListener.resetBg((Bitmap) ViewTemplateBlur.this.bitmapResList.get(ViewTemplateBlur.this.mypos), ViewTemplateBlur.this.mypos);
                BitmapListAdapter.this.notifyDataSetChanged();
            }

            public void bindData(int i) {
                Bitmap bitmap = (Bitmap) BitmapListAdapter.this.bitmapList.get(i);
                if (bitmap == null || bitmap.isRecycled()) {
                    return;
                }
                this.imgBitmap.setImageBitmap(bitmap);
                if (i == ViewTemplateBlur.this.mypos) {
                    this.imgStatus.setVisibility(View.VISIBLE);
                } else {
                    this.imgStatus.setVisibility(View.GONE);
                }
            }
        }
    }

    public void setCurrentBitmap(List<Bitmap> list) {
        ArrayList<Bitmap> arrayList = this.bitmapResList;
        if (arrayList == null || list == null) {
            return;
        }
        arrayList.addAll(list);
        BitmapListAdapter bitmapListAdapter = this.bitmapListAdapter;
        if (bitmapListAdapter != null) {
            bitmapListAdapter.notifyDataSetChanged();
        }
    }
}
