package com.picspool.instatextview.textview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.picspool.instatextview.edit.DM_BitmapColorAdapter3;
import com.picspool.instatextview.edit.DM_TextFixedView3;
import com.picspool.lib.color.DMSysColors;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.widget.colorgallery.DMColorGalleryView;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DM_BasicColorView3 extends FrameLayout {
    private DM_BitmapColorAdapter3 bcAdapter;
    private DMColorGalleryView colorGalleryView;
    private GridView colorGridView;
    private Context context;
    private DM_TextFixedView3 textFixedView;

    public DM_BasicColorView3(Context context) {
        super(context);
        iniView(context);
    }

    public DM_BasicColorView3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView(context);
    }

    public DM_BasicColorView3(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_basic_view_color, (ViewGroup) null);
        addView(inflate);
        DMColorGalleryView dMColorGalleryView = (DMColorGalleryView) inflate.findViewById(R.id.color_gallery_view);
        this.colorGalleryView = dMColorGalleryView;
        dMColorGalleryView.setFocusable(true);
        GridView gridView = (GridView) inflate.findViewById(R.id.color_grid);
        this.colorGridView = gridView;
        gridView.setSelector(new ColorDrawable(0));
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Context context = this.context;
        int px2dip = DMScreenInfoUtil.px2dip(context, context.getResources().getDimension(R.dimen.basic_color_gallery_h));
        this.colorGalleryView.setLayoutParams(new LinearLayout.LayoutParams(i, DMScreenInfoUtil.dip2px(this.context, px2dip), 48.0f));
        int i5 = px2dip / 5;
        this.colorGalleryView.setGalleryItemSize(i5, i5 * 4, 0, true);
        if (i3 == 0 && i4 == 0) {
            this.colorGalleryView.setPointTo(33);
        }
    }

    public void setColorListener(final DM_TextFixedView3 dM_TextFixedView3) {
        this.textFixedView = dM_TextFixedView3;
        DM_BitmapColorAdapter3 dM_BitmapColorAdapter3 = new DM_BitmapColorAdapter3(getContext(), dM_TextFixedView3);
        this.bcAdapter = dM_BitmapColorAdapter3;
        this.colorGridView.setAdapter((ListAdapter) dM_BitmapColorAdapter3);
        this.colorGridView.setOnItemClickListener(this.bcAdapter);
        this.colorGalleryView.setListener(new OnDMColorChangedListener() { // from class: com.picspool.instatextview.textview.DM_BasicColorView3.1
            private boolean iniFlag = false;

            @Override // com.picspool.lib.widget.listener.OnDMColorChangedListener
            public void onColorChanged(int i) {
                DM_TextFixedView3 dM_TextFixedView32;
                int i2 = 0;
                while (true) {
                    if (!this.iniFlag || i2 >= DMSysColors.length) {
                        break;
                    } else if (i != DMSysColors.getColor(i2) || (dM_TextFixedView32 = dM_TextFixedView3) == null || dM_TextFixedView32.getTextDrawer() == null) {
                        i2++;
                    } else {
                        dM_TextFixedView3.setTextColor(i);
                        dM_TextFixedView3.getTextDrawer().setPaintColorIndex(i2);
                        if (DM_BasicColorView3.this.bcAdapter != null) {
                            DM_BasicColorView3.this.bcAdapter.iniSelectionItem();
                        }
                    }
                }
                if (this.iniFlag) {
                    return;
                }
                this.iniFlag = true;
            }
        });
    }

    public void iniData() {
        int paintColorIndex;
        DM_TextFixedView3 dM_TextFixedView3 = this.textFixedView;
        if (dM_TextFixedView3 == null || dM_TextFixedView3.getTextDrawer() == null || (paintColorIndex = this.textFixedView.getTextDrawer().getPaintColorIndex()) < 0) {
            return;
        }
        this.bcAdapter.iniSelectionItem();
        this.colorGalleryView.setPointTo(paintColorIndex);
    }
}
