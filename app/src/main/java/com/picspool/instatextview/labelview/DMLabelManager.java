package com.picspool.instatextview.labelview;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.util.ArrayList;
import java.util.List;
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.lib.text.DMImager;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.Opcodes;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMLabelManager {
    private Context context;
    private List<Typeface> typeFaces;
    private List<TextDrawerFactory> textDrawers = new ArrayList();
    private Drawable[][] drawables = new Drawable[][]{new Drawable[24]};

    /* loaded from: classes3.dex */
    public interface TextDrawerFactory {
        DMTextDrawer createTextDrawer();
    }

    public DMLabelManager(Context context) {
        this.context = context;
        iniDrawbles();
        iniTypeFace();
        iniTextDrawers();
    }

    private void iniDrawbles() {
        Resources resources = this.context.getResources();
        Drawable[][] drawableArr = this.drawables;
        Drawable[] drawableArr2 = new Drawable[1];
        drawableArr2[0] = resources.getDrawable(R.drawable.dm_img_label_2);
        drawableArr[0] = drawableArr2;
        Drawable[][] drawableArr3 = this.drawables;
        Drawable[] drawableArr4 = new Drawable[1];
        drawableArr4[0] = resources.getDrawable(R.drawable.dm_img_label_3);
        drawableArr3[1] = drawableArr4;
        Drawable[][] drawableArr5 = this.drawables;
        Drawable[] drawableArr6 = new Drawable[1];
        drawableArr6[0] = resources.getDrawable(R.drawable.dm_img_label_4);
        drawableArr5[2] = drawableArr6;
        Drawable[][] drawableArr7 = this.drawables;
        Drawable[] drawableArr8 = new Drawable[1];
        drawableArr8[0] = resources.getDrawable(R.drawable.dm_img_label_7);
        drawableArr7[3] = drawableArr8;
        Drawable[][] drawableArr9 = this.drawables;
        Drawable[] drawableArr10 = new Drawable[1];
        drawableArr10[0] = resources.getDrawable(R.drawable.dm_img_label_15);
        drawableArr9[9] = drawableArr10;
        Drawable[][] drawableArr11 = this.drawables;
        Drawable[] drawableArr12 = new Drawable[1];
        drawableArr12[0] = resources.getDrawable(R.drawable.dm_img_label_16);
        drawableArr11[10] = drawableArr12;
        Drawable[][] drawableArr13 = this.drawables;
        Drawable[] drawableArr14 = new Drawable[1];
        drawableArr14[0] = resources.getDrawable(R.drawable.dm_img_label_17);
        drawableArr13[11] = drawableArr14;
        Drawable[][] drawableArr15 = this.drawables;
        Drawable[] drawableArr16 = new Drawable[1];
        drawableArr16[0] = resources.getDrawable(R.drawable.dm_img_label_18);
        drawableArr15[12] = drawableArr16;
        Drawable[][] drawableArr17 = this.drawables;
        Drawable[] drawableArr18 = new Drawable[1];
        drawableArr18[0] = resources.getDrawable(R.drawable.dm_img_label_19);
        drawableArr17[13] = drawableArr18;
        Drawable[][] drawableArr19 = this.drawables;
        Drawable[] drawableArr20 = new Drawable[1];
        drawableArr20[0] = resources.getDrawable(R.drawable.dm_img_label_20);
        drawableArr19[14] = drawableArr20;
        Drawable[][] drawableArr21 = this.drawables;
        Drawable[] drawableArr22 = new Drawable[1];
        drawableArr22[0] = resources.getDrawable(R.drawable.dm_img_label_21);
        drawableArr21[15] = drawableArr22;
        Drawable[][] drawableArr23 = this.drawables;
        Drawable[] drawableArr24 = new Drawable[1];
        drawableArr24[0] = resources.getDrawable(R.drawable.dm_img_label_8);
        drawableArr23[16] = drawableArr24;
        Drawable[][] drawableArr25 = this.drawables;
        Drawable[] drawableArr26 = new Drawable[2];
        drawableArr26[0] = resources.getDrawable(R.drawable.dm_img_label_9);
        drawableArr26[1] = resources.getDrawable(R.drawable.dm_img_label_9_2);
        drawableArr25[17] = drawableArr26;
        Drawable[][] drawableArr27 = this.drawables;
        Drawable[] drawableArr28 = new Drawable[2];
        drawableArr28[0] = resources.getDrawable(R.drawable.dm_img_label_10);
        drawableArr28[1] = resources.getDrawable(R.drawable.dm_img_label_10_2);
        drawableArr27[18] = drawableArr28;
        Drawable[][] drawableArr29 = this.drawables;
        Drawable[] drawableArr30 = new Drawable[2];
        drawableArr30[0] = resources.getDrawable(R.drawable.dm_img_label_11);
        drawableArr30[1] = resources.getDrawable(R.drawable.dm_img_label_11_2);
        drawableArr29[19] = drawableArr30;
        Drawable[][] drawableArr31 = this.drawables;
        Drawable[] drawableArr32 = new Drawable[1];
        drawableArr32[0] = resources.getDrawable(R.drawable.dm_img_label_12);
        drawableArr31[20] = drawableArr32;
        Drawable[][] drawableArr33 = this.drawables;
        Drawable[] drawableArr34 = new Drawable[2];
        drawableArr34[0] = resources.getDrawable(R.drawable.dm_img_label_14);
        drawableArr34[1] = resources.getDrawable(R.drawable.dm_img_label_14_2);
        drawableArr33[21] = drawableArr34;
    }

    private void iniTextDrawers() {
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.1
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "ON MY WAY");
                dMTextDrawer.setImagerDrawable(null, null, null, null, null);
                dMTextDrawer.setColor(Color.rgb(248, (int) Opcodes.INVOKEVIRTUAL, 77));
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(0));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.2
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "SUNSHINE");
                dMTextDrawer.setImagerDrawable(null, null, null, new DMImager.RightDrawable(dMTextDrawer, DMLabelManager.this.drawables[0][0], new RectF(0.0f, -0.7f, 0.8f, 1.04f)), null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(1));
                dMTextDrawer.setColor(-1);
                dMTextDrawer.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM);
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.3
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "Love you");
                dMTextDrawer.setImagerDrawable(null, null, null, new DMImager.RightDrawable(dMTextDrawer, DMLabelManager.this.drawables[16][0], new RectF(5.0f, -0.7f, 0.9f, 0.5f)), null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(7));
                dMTextDrawer.setColor(Color.rgb(230, 0, 18));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.4
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "HOLIDAY CHEER");
                dMTextDrawer.setImagerDrawable(null, null, null, new DMImager.RightDrawable(dMTextDrawer, DMLabelManager.this.drawables[2][0], new RectF(0.0f, -0.85f, 0.9f, 0.71f)), null);
                dMTextDrawer.setColor(Color.rgb(73, 145, 87));
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(3));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.5
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "HOLIDAYS");
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(4));
                dMTextDrawer.setColor(Color.rgb(244, 68, 68));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.6
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "Best wishes");
                dMTextDrawer.setColor(Color.rgb(214, (int) Opcodes.IF_ICMPEQ, 71));
                dMTextDrawer.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(5));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.7
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "Snow day");
                dMTextDrawer.setImagerDrawable(null, new DMImager.LeftDrawable(dMTextDrawer, DMLabelManager.this.drawables[3][0], new RectF(0.0f, -0.8f, 1.1f, 1.0f)), null, new DMImager.RightDrawable(dMTextDrawer, DMLabelManager.this.drawables[3][0], new RectF(0.0f, -0.8f, 1.1f, 1.0f)), null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(6));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.8
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "I love you");
                dMTextDrawer.setImagerDrawable(null, new DMImager.LeftDrawable(dMTextDrawer, DMLabelManager.this.drawables[19][0], new RectF(-3.0f, -0.2f, 0.4f, 2.0f)), null, new DMImager.RightDrawable(dMTextDrawer, DMLabelManager.this.drawables[19][1], new RectF(3.0f, -0.55f, 0.4f, 1.82f)), null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(10));
                dMTextDrawer.setColor(Color.rgb(230, 0, 18));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.9
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "GoOd DAY");
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(8));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.10
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "Candy time");
                dMTextDrawer.setImagerDrawable(null, new DMImager.LeftDrawable(dMTextDrawer, DMLabelManager.this.drawables[18][0], new RectF(0.0f, -0.7f, 0.45f, 0.98f)), null, new DMImager.RightDrawable(dMTextDrawer, DMLabelManager.this.drawables[18][1], new RectF(0.0f, -0.9f, 1.0f, 0.89f)), null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(9));
                dMTextDrawer.setColor(Color.rgb(0, (int) Opcodes.IFEQ, 204));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.11
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "Hello Sunshine");
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(11));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.12
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "GET LOST");
                dMTextDrawer.setImagerDrawable(null, null, null, new DMImager.RightDrawable(dMTextDrawer, DMLabelManager.this.drawables[20][0], new RectF(0.0f, -0.65f, 0.75f, 1.19f)), null);
                dMTextDrawer.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(2));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.13
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "ON THE WAY");
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(12));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.14
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "Beautiful");
                dMTextDrawer.setImagerDrawable(null, new DMImager.LeftDrawable(dMTextDrawer, DMLabelManager.this.drawables[21][0], new RectF(5.0f, -0.35f, 1.0f, 1.05f)), null, new DMImager.RightDrawable(dMTextDrawer, DMLabelManager.this.drawables[21][1], new RectF(0.0f, -1.0f, 1.3f, 0.72f)), null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(13));
                dMTextDrawer.setColor(Color.rgb(249, 209, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.15
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "HELLO 2015");
                dMTextDrawer.setImagerDrawable(new DMImager.StretchDrawable(dMTextDrawer, DMLabelManager.this.drawables[9][0], new Rect(-35, -20, 35, 20)), null, null, null, null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(14));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.16
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "Miss you");
                dMTextDrawer.setImagerDrawable(new DMImager.StretchDrawable(dMTextDrawer, DMLabelManager.this.drawables[10][0], new Rect(-35, -20, 35, 20)), null, null, null, null);
                dMTextDrawer.setColor(ViewCompat.MEASURED_STATE_MASK);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(15));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.17
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "FONT OVER");
                dMTextDrawer.setImagerDrawable(new DMImager.StretchDrawable(dMTextDrawer, DMLabelManager.this.drawables[11][0], new Rect(-10, -15, 10, 15)), null, null, null, null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(16));
                dMTextDrawer.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.RIGHT_BOTTOM);
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.18
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "NEW YEAR");
                dMTextDrawer.setImagerDrawable(new DMImager.StretchDrawable(dMTextDrawer, DMLabelManager.this.drawables[12][0], new Rect(-45, -10, 45, 20)), null, null, null, null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(17));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.19
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "FONT OVER");
                dMTextDrawer.setImagerDrawable(new DMImager.StretchDrawable(dMTextDrawer, DMLabelManager.this.drawables[13][0], new Rect(-25, -20, 10, 20)), null, null, null, null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(18));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.20
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "FASHION");
                dMTextDrawer.setImagerDrawable(new DMImager.StretchDrawable(dMTextDrawer, DMLabelManager.this.drawables[14][0], new Rect(-35, -25, 35, 25)), null, null, null, null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(19));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
        this.textDrawers.add(new TextDrawerFactory() { // from class: com.picspool.instatextview.labelview.DMLabelManager.21
            @Override // com.picspool.instatextview.labelview.DMLabelManager.TextDrawerFactory
            public DMTextDrawer createTextDrawer() {
                DMTextDrawer dMTextDrawer = new DMTextDrawer(DMLabelManager.this.context, "NICE LIFE");
                dMTextDrawer.setImagerDrawable(new DMImager.StretchDrawable(dMTextDrawer, DMLabelManager.this.drawables[15][0], new Rect(-45, -20, 45, 20)), null, null, null, null);
                dMTextDrawer.setTypeface((Typeface) DMLabelManager.this.typeFaces.get(20));
                dMTextDrawer.setIgnoreLine(false);
                return dMTextDrawer;
            }
        });
    }

    private void iniTypeFace() {
        ArrayList arrayList = new ArrayList();
        this.typeFaces = arrayList;
        arrayList.add(DMInstaTextView.getTfList().get(18));
        this.typeFaces.add(DMInstaTextView.getTfList().get(19));
        this.typeFaces.add(DMInstaTextView.getTfList().get(24));
        this.typeFaces.add(DMInstaTextView.getTfList().get(16));
        this.typeFaces.add(DMInstaTextView.getTfList().get(9));
        this.typeFaces.add(DMInstaTextView.getTfList().get(3));
        this.typeFaces.add(DMInstaTextView.getTfList().get(10));
        this.typeFaces.add(DMInstaTextView.getTfList().get(20));
        this.typeFaces.add(DMInstaTextView.getTfList().get(22));
        this.typeFaces.add(DMInstaTextView.getTfList().get(12));
        this.typeFaces.add(DMInstaTextView.getTfList().get(1));
        this.typeFaces.add(DMInstaTextView.getTfList().get(23));
        this.typeFaces.add(DMInstaTextView.getTfList().get(25));
        this.typeFaces.add(DMInstaTextView.getTfList().get(11));
        this.typeFaces.add(DMInstaTextView.getTfList().get(6));
        this.typeFaces.add(DMInstaTextView.getTfList().get(19));
        this.typeFaces.add(DMInstaTextView.getTfList().get(4));
        this.typeFaces.add(DMInstaTextView.getTfList().get(14));
        this.typeFaces.add(DMInstaTextView.getTfList().get(4));
        this.typeFaces.add(DMInstaTextView.getTfList().get(8));
        this.typeFaces.add(DMInstaTextView.getTfList().get(16));
    }

    public DMTextDrawer getTextDrawer(int i) {
        return this.textDrawers.get(i).createTextDrawer();
    }
}
