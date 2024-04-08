package com.photo.editor.square.splash.view.glitch;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.photo.editor.square.splash.view.glitch.IL1;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class IL1 extends FrameLayout {
    private View bottomTool;
    private GPUImageFilter il1;
    private SeekBar il2;
    private IL234567 il3;

    /* loaded from: classes2.dex */
    public interface IL234567 {
        /* renamed from: f1 */
        void mo84f1(Bitmap bitmap);

        /* renamed from: f2 */
        void mo83f2(GPUImageFilter gPUImageFilter);
    }

    public IL1(Context context) {
        super(context);
        this.il1 = new GPUImageFilter();
        f11();
    }

    public IL1(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.il1 = new GPUImageFilter();
        f11();
    }

    public IL1(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.il1 = new GPUImageFilter();
        f11();
    }

    protected void f11() {
        inflate(getContext(), R.layout.activity_glitch, this);
        View findViewById = findViewById(R.id.img_sub_close);
        this.bottomTool = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.glitch.-$$Lambda$IL1$_Ml2L8FdckLVrrQsnDhfRKKe9Ps
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IL1.this.lambda$f11$0$IL1(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.glitch_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(new IL4());
        SeekBar seekBar = (SeekBar) findViewById(R.id.glitch_progress);
        this.il2 = seekBar;
        seekBar.setEnabled(false);
        this.il2.setAlpha(0.5f);
        this.il2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.photo.editor.square.splash.view.glitch.IL1.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                float progress = seekBar2.getProgress() / seekBar2.getMax();
                if (IL1.this.il1 instanceof IL123456) {
                    ((IL123456) IL1.this.il1).il3(progress);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                IL1.this.f13();
            }
        });
    }

    public /* synthetic */ void lambda$f11$0$IL1(View view) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
    }

    public void f12(Bitmap bitmap) {
        final IL20 il20 = new IL20();
        GPUImageFilter gPUImageFilter = this.il1;
        il20.setData(bitmap, gPUImageFilter instanceof IL123456 ? ((IL123456) gPUImageFilter).il4() : null, new OnPostFilteredListener() { // from class: com.photo.editor.square.splash.view.glitch.-$$Lambda$IL1$uw4tWeTZrP4Kb4-D_n_PwILvEM0
            @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
            public final void postFiltered(Bitmap bitmap2) {
                IL1.this.lambda$f12$1$IL1(il20, bitmap2);
            }
        });
        il20.execute(new Void[0]);
    }

    public /* synthetic */ void lambda$f12$1$IL1(IL20 il20, Bitmap bitmap) {
        il20.shutdownLoder();
        IL234567 il234567 = this.il3;
        if (il234567 != null) {
            il234567.mo84f1(bitmap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f13() {
        IL234567 il234567 = this.il3;
        if (il234567 != null) {
            il234567.mo83f2(this.il1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f14(IL5 il5) {
        if (il5 != null) {
            this.il2.setEnabled(true);
            this.il2.setAlpha(1.0f);
            SeekBar seekBar = this.il2;
            if (seekBar != null) {
                seekBar.setProgress((int) (il5.progress * 100.0f));
            }
            switch (C22042.$SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5[il5.ordinal()]) {
                case 1:
                    this.il1 = new IL9();
                    break;
                case 2:
                    this.il1 = new IL8();
                    break;
                case 3:
                    this.il1 = new IL16();
                    break;
                case 4:
                    this.il1 = new IL12();
                    break;
                case 5:
                    this.il1 = new IL11();
                    break;
                case 6:
                    this.il1 = new IL17();
                    break;
                case 7:
                    this.il1 = new IL19();
                    break;
                case 8:
                    this.il1 = new IL10();
                    break;
                case 9:
                    this.il1 = new IL13();
                    this.il2.setEnabled(false);
                    this.il2.setAlpha(0.5f);
                    break;
            }
        }
        f13();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.photo.editor.square.splash.view.glitch.IL1$2 */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class C22042 {
        static final /* synthetic */ int[] $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5;

        static {
            int[] iArr = new int[IL5.values().length];
            $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5 = iArr;
            try {
                iArr[IL5.SLICER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5[IL5.EDGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5[IL5.MELTING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5[IL5.PRESENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5[IL5.RGBSCAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5[IL5.RGBSHIFT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5[IL5.CROSSHATCH.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5[IL5.HALFTONECMYK.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$photo$editor$square$splash$view$glitch$IL1$IL5[IL5.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public void setIl3(IL234567 il234567) {
        this.il3 = il234567;
    }

    public void setShowBottomTool(boolean z) {
        if (z) {
            this.bottomTool.setVisibility(View.VISIBLE);
        } else {
            this.bottomTool.setVisibility(View.GONE);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class IL4 extends RecyclerView.Adapter<IL4.MyViewholder> {
        IL5[] il1 = IL5.values();
        private int il2;
        private int il3;

        IL4() {}

        public void defaultItem(int i) {
            m82f1(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public MyViewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewholder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.glitch_item, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(MyViewholder viewholder, int i) {
            IL5 il5 = this.il1[i];
            if (this.il3 <= 0) {
                this.il3 = CSScreenInfoUtil.dip2px(viewholder.itemView.getContext(), 4.0f);
            }
            Glide.with(viewholder.itemView.getContext()).load(Integer.valueOf(il5.iconId)).into(viewholder.icon);
            viewholder.itemView.setSelected(i == this.il2 && i != 0);
            viewholder.selectedIcon.setVisibility(i == this.il2 ? View.VISIBLE : View.GONE);
            viewholder.name.setVisibility(i == 0 ? View.GONE : View.VISIBLE);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.il1.length;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: f1 */
        public void m82f1(int i) {
            if (i < 0 || i >= this.il1.length) {
                return;
            }
            int i2 = this.il2;
            this.il2 = i;
            notifyItemChanged(i2);
            notifyItemChanged(this.il2);
            IL1.this.f14(this.il1[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public class MyViewholder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView name;
            ImageView selectedIcon;

            MyViewholder(View view) {
                super(view);
                this.icon = (ImageView) view.findViewById(R.id.glitch_item_icon);
                this.name = (TextView) view.findViewById(R.id.glitch_item_name);
                this.selectedIcon = (ImageView) view.findViewById(R.id.selectedIcon);
                view.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.glitch.-$$Lambda$IL1$IL4$IL6$xD9w53yl2nI1sXlSy6hu1W9F1ms
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        MyViewholder.this.lambda$new$0$IL1$IL4$IL6(view2);
                    }
                });
            }

            public /* synthetic */ void lambda$new$0$IL1$IL4$IL6(View view) {
                IL4.this.m82f1(getAdapterPosition());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public enum IL5 {
        NONE("NONE", "None", R.drawable.icon_glitch_none, 0.2f),
        RGBSHIFT("RGBSHIFT", "E1", R.drawable.icon_glitch_rgbshift, 0.45f),
        CROSSHATCH("CROSSHATCH", "E2", R.drawable.icon_glitch_cross, 0.45f),
        SLICER("SLICER", "E3", R.drawable.icon_glitch_slicer, 0.2f),
        RGBSCAN("RGBSCAN", "E4", R.drawable.icon_glitch_rgbscan, 0.4f),
        EDGE("EDGE", "E5", R.drawable.icon_glitch_edge, 0.2f),
        HALFTONECMYK("HALFTONECMYK", "E6", R.drawable.icon_glitch_half, 0.0f),
        PRESENT("PRESENT", "E7", R.drawable.icon_glitch_present, 0.0f),
        MELTING("MELTING", "E8", R.drawable.icon_glitch_melting, 0.47f);
        
        int iconId;
        String name;
        float progress;

        IL5(String str, String str2, int i, float f) {
            this.name = str2;
            this.iconId = i;
            this.progress = f;
        }
    }
}
