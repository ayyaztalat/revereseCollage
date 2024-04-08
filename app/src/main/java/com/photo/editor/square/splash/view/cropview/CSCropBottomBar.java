package com.photo.editor.square.splash.view.cropview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.ConvertUtils;
import java.lang.reflect.Field;
import com.picspool.lib.resource.adapter.DMResImageAdapter;
import com.picspool.lib.resource.view.DMResImageLayout;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSCropBottomBar extends RelativeLayout {
    public static final int B_0618 = 8;
    public static final int B_16_9 = 6;
    public static final int B_1_1 = 3;
    public static final int B_3_4 = 5;
    public static final int B_4_3 = 4;
    public static final int B_9_16 = 7;
    public static final int FREE = 2;
    public static final int ORIGIN = 1;
    private DMResImageAdapter adapter;
    private DMResImageLayout imgLayout;
    private Context mContext;
    public DMResImageLayout.OnItemClickListener mListener;

    public CSCropBottomBar(Context context) {
        super(context);
        init(context);
    }

    public CSCropBottomBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_crop_bar, this, true);
        this.mContext = context;
        DMResImageLayout dMResImageLayout = (DMResImageLayout) findViewById(R.id.bottomImageLayout);
        this.imgLayout = dMResImageLayout;
        dMResImageLayout.mitemListener = new CropItemClickListener();
    }

    public void loadAdapter() {
        DMResImageAdapter dMResImageAdapter = this.adapter;
        if (dMResImageAdapter != null) {
            dMResImageAdapter.dispose();
            this.adapter = null;
        }
        CSCropBottomAdapter cSCropBottomAdapter = new CSCropBottomAdapter(this.mContext);
        this.adapter = cSCropBottomAdapter;
        cSCropBottomAdapter.setImageItemSize(ConvertUtils.dp2px(50.0f), ConvertUtils.dp2px(50.0f));
        this.adapter.SetTextColor(getResources().getColor(R.color.transparent));
        this.imgLayout.setSelectImageLocation(1);
        this.imgLayout.setAdapter(this.adapter);
        try {
            Field declaredField = this.imgLayout.getClass().getDeclaredField("mSelView");
            declaredField.setAccessible(true);
            View view = (View) declaredField.get(this.imgLayout);
            if (view != null) {
                view.findViewById(R.id.item_text).setSelected(true);
                ((CropItemClickListener) this.imgLayout.mitemListener).view = view.findViewById(R.id.item_text);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
        setVisibility(View.VISIBLE);
    }

    public void clearAdapter() {
        DMResImageAdapter dMResImageAdapter = this.adapter;
        if (dMResImageAdapter != null) {
            dMResImageAdapter.dispose();
            this.adapter = null;
            this.imgLayout.setAdapter(null);
        }
        setVisibility(View.INVISIBLE);
    }

    public void setItemClickListener(DMResImageLayout.OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class CropItemClickListener implements DMResImageLayout.OnItemClickListener {
        private View view;

        private CropItemClickListener() {
        }

        @Override // com.picspool.lib.resource.view.DMResImageLayout.OnItemClickListener
        public void ItemClick(View view, int i, String str) {
            View view2 = this.view;
            if (view2 != null) {
                view2.setSelected(false);
            }
            View findViewById = view.findViewById(R.id.item_text);
            this.view = findViewById;
            if (findViewById != null) {
                findViewById.setSelected(true);
            }
            if (CSCropBottomBar.this.mListener != null) {
                CSCropBottomBar.this.mListener.ItemClick(view, i, str);
            }
        }
    }
}
