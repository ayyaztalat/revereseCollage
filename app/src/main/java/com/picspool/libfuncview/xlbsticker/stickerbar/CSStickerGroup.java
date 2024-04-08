package com.picspool.libfuncview.xlbsticker.stickerbar;

import android.content.Context;
import com.picspool.libfuncview.res.CSGroupRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSStickerGroup extends CSGroupRes {
    public static final int COLORSTYLE1 = 1;
    public static final int COLORSTYLE2 = 2;
    private int lastModified;
    private Context mContext;
    private int stickercolorstyle;
    private int stylecolor1;
    private int stylecolor2;

    public CSStickerGroup(Context context) {
        super(context);
        this.stickercolorstyle = 1;
        this.mContext = context;
        this.stylecolor1 = context.getResources().getColor(R.color.style1_color1);
        this.stylecolor2 = this.mContext.getResources().getColor(R.color.style1_color2);
    }

    public int getStylecolor2() {
        return this.stylecolor2;
    }

    public int getStylecolor1() {
        return this.stylecolor1;
    }

    public int getStickercolorstyle() {
        return this.stickercolorstyle;
    }

    public void setStickercolorstyle(int i) {
        this.stickercolorstyle = i;
        if (i == 1) {
            this.stylecolor1 = this.mContext.getResources().getColor(R.color.style1_color1);
            this.stylecolor2 = this.mContext.getResources().getColor(R.color.style1_color2);
        }
        if (i == 2) {
            this.stylecolor1 = this.mContext.getResources().getColor(R.color.style2_color1);
            this.stylecolor2 = this.mContext.getResources().getColor(R.color.style2_color2);
        }
    }

    @Override // com.picspool.libfuncview.res.CSGroupRes
    public int getLastModified() {
        return this.lastModified;
    }

    @Override // com.picspool.libfuncview.res.CSGroupRes
    public void setLastModified(int i) {
        this.lastModified = i;
    }
}
