package com.picspool.lib.text.edit;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import com.picspool.lib.color.DMSysColors;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.widget.colorgallery.DMColorGalleryView;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMBasicColorView extends FrameLayout {
    private DMBitmapColorAdapter bcAdapter;
    private DMColorGalleryView colorGalleryView;
    private GridView colorGridView;
    private Context context;
    private DMTextFixedView textFixedView;

    public DMBasicColorView(Context context) {
        super(context);
        iniView(context);
    }

    public DMBasicColorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView(context);
    }

    public DMBasicColorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_systext_basic_view_color, (ViewGroup) null);
        addView(inflate);
        DMColorGalleryView dMColorGalleryView = (DMColorGalleryView) inflate.findViewById(R.id.color_gallery_view);
        this.colorGalleryView = dMColorGalleryView;
        dMColorGalleryView.setFocusable(true);
        GridView gridView = (GridView) inflate.findViewById(R.id.color_grid);
        this.colorGridView = gridView;
        gridView.setSelector(new ColorDrawable(0));
        int px2dip = DMScreenInfoUtil.px2dip(context, context.getResources().getDimension(R.dimen.basic_color_gallery_h)) / 5;
        this.colorGalleryView.setGalleryItemSize(px2dip, px2dip * 4, 0, true);
        this.colorGalleryView.setPointTo(33);
    }

    public void setColorListener(final DMTextFixedView dMTextFixedView) {
        this.textFixedView = dMTextFixedView;
        DMBitmapColorAdapter dMBitmapColorAdapter = new DMBitmapColorAdapter(getContext(), dMTextFixedView);
        this.bcAdapter = dMBitmapColorAdapter;
        this.colorGridView.setAdapter((ListAdapter) dMBitmapColorAdapter);
        this.colorGridView.setOnItemClickListener(this.bcAdapter);
        this.colorGalleryView.setListener(new OnDMColorChangedListener() { // from class: com.picspool.lib.text.edit.DMBasicColorView.1
            private boolean iniFlag = false;

            @Override // com.picspool.lib.widget.listener.OnDMColorChangedListener
            public void onColorChanged(int i) {
                DMTextFixedView dMTextFixedView2;
                int i2 = 0;
                while (true) {
                    if (!this.iniFlag || i2 >= DMSysColors.length) {
                        break;
                    } else if (i != DMSysColors.getColor(i2) || (dMTextFixedView2 = dMTextFixedView) == null || dMTextFixedView2.getTextDrawer() == null) {
                        i2++;
                    } else {
                        dMTextFixedView.setTextColor(i);
                        dMTextFixedView.getTextDrawer().setPaintColorIndex(i2);
                        if (DMBasicColorView.this.bcAdapter != null) {
                            DMBasicColorView.this.bcAdapter.iniSelectionItem();
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
        DMTextFixedView dMTextFixedView = this.textFixedView;
        if (dMTextFixedView == null || dMTextFixedView.getTextDrawer() == null || (paintColorIndex = this.textFixedView.getTextDrawer().getPaintColorIndex()) < 0) {
            return;
        }
        this.bcAdapter.iniSelectionItem();
        this.colorGalleryView.setPointTo(paintColorIndex);
    }
}
