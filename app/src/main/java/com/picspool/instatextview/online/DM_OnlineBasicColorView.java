package com.picspool.instatextview.online;

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
import com.sky.testproject.R;
import com.picspool.instatextview.edit.DM_BitmapColorAdapter;
import com.picspool.instatextview.edit.DM_TextFixedView;
import com.picspool.lib.color.DMSysColors;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.widget.colorgallery.DMColorGalleryView;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;

/* loaded from: classes3.dex */
public class DM_OnlineBasicColorView extends FrameLayout {
    private DM_BitmapColorAdapter bcAdapter;
    private DMColorGalleryView colorGalleryView;
    private GridView colorGridView;
    private Context context;
    private DM_TextFixedView textFixedView;

    public DM_OnlineBasicColorView(Context context) {
        super(context);
        iniView(context);
    }

    public DM_OnlineBasicColorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView(context);
    }

    public DM_OnlineBasicColorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.dm_text_online_basic_view_color, (ViewGroup) null);
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

    public void setColorListener(final DM_TextFixedView dM_TextFixedView) {
        this.textFixedView = dM_TextFixedView;
        DM_BitmapColorAdapter dM_BitmapColorAdapter = new DM_BitmapColorAdapter(getContext(), dM_TextFixedView);
        this.bcAdapter = dM_BitmapColorAdapter;
        this.colorGridView.setAdapter((ListAdapter) dM_BitmapColorAdapter);
        this.colorGridView.setOnItemClickListener(this.bcAdapter);
        this.colorGalleryView.setListener(new OnDMColorChangedListener() { // from class: com.picspool.instatextview.online.DM_OnlineBasicColorView.1
            private boolean iniFlag = false;

            @Override // com.picspool.lib.widget.listener.OnDMColorChangedListener
            public void onColorChanged(int i) {
                int i2 = 0;
                while (true) {
                    if (!this.iniFlag || i2 >= DMSysColors.length) {
                        break;
                    } else if (i == DMSysColors.getColor(i2)) {
                        dM_TextFixedView.setTextColor(i);
                        dM_TextFixedView.getTextDrawer().setPaintColorIndex(i2);
                        if (DM_OnlineBasicColorView.this.bcAdapter != null) {
                            DM_OnlineBasicColorView.this.bcAdapter.iniSelectionItem();
                        }
                    } else {
                        i2++;
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
        DM_TextFixedView dM_TextFixedView = this.textFixedView;
        if (dM_TextFixedView == null || dM_TextFixedView.getTextDrawer() == null || (paintColorIndex = this.textFixedView.getTextDrawer().getPaintColorIndex()) < 0) {
            return;
        }
        this.bcAdapter.iniSelectionItem();
        this.colorGalleryView.setPointTo(paintColorIndex);
    }
}
