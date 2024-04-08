package com.picspool.lib.widget.colorgradient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.picspool.lib.widget.colorpicker.DMColorPickerDialogView;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMColorGradientDialogView extends LinearLayout implements OnDMColorChangedListener {
    private ImageView color1ImageView;
    private ImageView color2ImageView;
    private DMColorPickerDialogView colorPicker;
    private AlertDialog colorPickerDialog;
    private int curColor;
    private int curType;
    private GradientDrawable gradientDrawable;
    private ImageView gradientImageView;
    private int[] mColors;
    private Context mContext;
    private GradientDrawable.Orientation mOrientation;
    private int[] mPrevColors;
    private int mType;

    static /* synthetic */ int access$208(DMColorGradientDialogView dMColorGradientDialogView) {
        int i = dMColorGradientDialogView.curType;
        dMColorGradientDialogView.curType = i + 1;
        return i;
    }

    public DMColorGradientDialogView(Context context, int[] iArr) {
        super(context);
        this.mColors = new int[2];
        this.mType = 0;
        this.mOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
        this.gradientDrawable = new GradientDrawable();
        this.curType = 0;
        this.mPrevColors = new int[2];
        this.mContext = context;
        this.mColors = iArr;
        for (int i = 0; i < 2; i++) {
            this.mPrevColors[i] = iArr[i];
        }
        init();
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_dialog_colorgradient, (ViewGroup) this, true);
        this.color1ImageView = (ImageView) findViewById(R.id.color1Image);
        this.color2ImageView = (ImageView) findViewById(R.id.color2Image);
        this.gradientImageView = (ImageView) findViewById(R.id.gradientImage);
        this.color1ImageView.setBackgroundColor(this.mColors[0]);
        this.color2ImageView.setBackgroundColor(this.mColors[1]);
        updateGradient();
        this.color1ImageView.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.widget.colorgradient.DMColorGradientDialogView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMColorGradientDialogView.this.curColor = 0;
                DMColorGradientDialogView dMColorGradientDialogView = DMColorGradientDialogView.this;
                dMColorGradientDialogView.showColorPickerDialog(dMColorGradientDialogView.mColors[0]);
            }
        });
        this.color2ImageView.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.widget.colorgradient.DMColorGradientDialogView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMColorGradientDialogView.this.curColor = 1;
                DMColorGradientDialogView dMColorGradientDialogView = DMColorGradientDialogView.this;
                dMColorGradientDialogView.showColorPickerDialog(dMColorGradientDialogView.mColors[1]);
            }
        });
        this.gradientImageView.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.widget.colorgradient.DMColorGradientDialogView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMColorGradientDialogView.access$208(DMColorGradientDialogView.this);
                if (DMColorGradientDialogView.this.curType == 12) {
                    DMColorGradientDialogView.this.curType = 0;
                }
                DMColorGradientDialogView.this.mType = 0;
                switch (DMColorGradientDialogView.this.curType) {
                    case 0:
                        DMColorGradientDialogView.this.mOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
                        break;
                    case 1:
                        DMColorGradientDialogView.this.mOrientation = GradientDrawable.Orientation.RIGHT_LEFT;
                        break;
                    case 2:
                        DMColorGradientDialogView.this.mOrientation = GradientDrawable.Orientation.BOTTOM_TOP;
                        break;
                    case 3:
                        DMColorGradientDialogView.this.mOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
                        break;
                    case 4:
                        DMColorGradientDialogView.this.mOrientation = GradientDrawable.Orientation.TL_BR;
                        break;
                    case 5:
                        DMColorGradientDialogView.this.mOrientation = GradientDrawable.Orientation.TR_BL;
                        break;
                    case 6:
                        DMColorGradientDialogView.this.mOrientation = GradientDrawable.Orientation.BR_TL;
                        break;
                    case 7:
                        DMColorGradientDialogView.this.mOrientation = GradientDrawable.Orientation.BL_TR;
                        break;
                    case 8:
                        DMColorGradientDialogView.this.mType = 2;
                        break;
                    case 9:
                        DMColorGradientDialogView.this.mType = 2;
                        break;
                    case 10:
                        DMColorGradientDialogView.this.mType = 1;
                        break;
                    case 11:
                        DMColorGradientDialogView.this.mType = 1;
                        break;
                }
                DMColorGradientDialogView.this.updateGradient();
            }
        });
    }

    public void updateGradient() {
        this.gradientDrawable = new GradientDrawable(this.mOrientation, this.mColors);
        if (this.curType == 8) {
            int[] iArr = this.mColors;
            this.gradientDrawable = new GradientDrawable(this.mOrientation, new int[]{iArr[0], iArr[1], iArr[0]});
        }
        if (this.curType == 9) {
            int[] iArr2 = this.mColors;
            this.gradientDrawable = new GradientDrawable(this.mOrientation, new int[]{iArr2[1], iArr2[0], iArr2[1]});
        }
        if (this.curType == 11) {
            int[] iArr3 = this.mColors;
            this.gradientDrawable = new GradientDrawable(this.mOrientation, new int[]{iArr3[1], iArr3[0]});
        }
        this.gradientDrawable.setGradientType(this.mType);
        int i = this.curType;
        if (i == 10 || i == 11) {
            this.gradientDrawable.setGradientRadius(this.gradientImageView.getWidth());
        }
        if (Build.VERSION.SDK_INT < 16) {
            this.gradientImageView.setBackgroundDrawable(this.gradientDrawable);
        } else {
            setBackground16(this.gradientImageView, this.gradientDrawable);
        }
    }

    private void setBackground16(ImageView imageView, GradientDrawable gradientDrawable) {
        imageView.setBackground(gradientDrawable);
    }

    public void setGradientOrientation(GradientDrawable.Orientation orientation) {
        this.mOrientation = orientation;
        updateGradient();
    }

    public void setGradientType(int i) {
        this.mType = i;
        updateGradient();
    }

    public GradientDrawable getGradientDrawable() {
        return this.gradientDrawable;
    }

    public Boolean getIsRadial() {
        int i = this.curType;
        if (i == 10 || i == 11) {
            return true;
        }
        return false;
    }

    public void showColorPickerDialog(int i) {
        if (this.colorPickerDialog == null) {
            DMColorPickerDialogView dMColorPickerDialogView = new DMColorPickerDialogView(this.mContext, i);
            this.colorPicker = dMColorPickerDialogView;
            dMColorPickerDialogView.setOnColorChangedListener(this);
            this.colorPicker.setAlphaSliderVisible(false);
            this.colorPicker.setHexValueEnabled(false);
            this.colorPickerDialog = new AlertDialog.Builder(this.mContext).setTitle((CharSequence) null).setView(this.colorPicker).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() { // from class: com.picspool.lib.widget.colorgradient.DMColorGradientDialogView.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    DMColorGradientDialogView.this.colorPicker.onOKClick();
                }
            }).setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() { // from class: com.picspool.lib.widget.colorgradient.DMColorGradientDialogView.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                }
            }).create();
        } else {
            this.colorPicker.setColor(i);
        }
        this.colorPickerDialog.show();
    }

    @Override // com.picspool.lib.widget.listener.OnDMColorChangedListener
    public void onColorChanged(int i) {
        for (int i2 = 0; i2 < 2; i2++) {
            this.mPrevColors[i2] = this.mColors[i2];
        }
        if (this.curColor == 0) {
            this.color1ImageView.setBackgroundColor(i);
            this.mColors[0] = i;
        }
        if (this.curColor == 1) {
            this.color2ImageView.setBackgroundColor(i);
            this.mColors[1] = i;
        }
        updateGradient();
    }

    public void ColorCancel() {
        for (int i = 0; i < 2; i++) {
            this.mColors[i] = this.mPrevColors[i];
        }
        this.color1ImageView.setBackgroundColor(this.mColors[0]);
        this.color2ImageView.setBackgroundColor(this.mColors[1]);
        updateGradient();
    }
}
