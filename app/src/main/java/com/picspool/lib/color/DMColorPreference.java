package com.picspool.lib.color;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.view.ViewCompat;
import com.picspool.lib.widget.colorpicker.DMAlphaPatternDrawable;
import com.picspool.lib.widget.colorpicker.DMColorPickerDialogView;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;

/* loaded from: classes3.dex */
public class DMColorPreference extends Preference implements Preference.OnPreferenceClickListener, OnDMColorChangedListener {
    private boolean mAlphaSliderEnabled;
    private float mDensity;
    DMColorPickerDialogView mDialog;
    private boolean mHexValueEnabled;
    private int mValue;
    View mView;

    public DMColorPreference(Context context) {
        super(context);
        this.mValue = ViewCompat.MEASURED_STATE_MASK;
        this.mDensity = 0.0f;
        this.mAlphaSliderEnabled = false;
        this.mHexValueEnabled = false;
        init(context, null);
    }

    public DMColorPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mValue = ViewCompat.MEASURED_STATE_MASK;
        this.mDensity = 0.0f;
        this.mAlphaSliderEnabled = false;
        this.mHexValueEnabled = false;
        init(context, attributeSet);
    }

    public DMColorPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mValue = ViewCompat.MEASURED_STATE_MASK;
        this.mDensity = 0.0f;
        this.mAlphaSliderEnabled = false;
        this.mHexValueEnabled = false;
        init(context, attributeSet);
    }

    @Override // android.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getColor(i, ViewCompat.MEASURED_STATE_MASK));
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, Object obj) {
        onColorChanged(z ? getPersistedInt(this.mValue) : ((Integer) obj).intValue());
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mDensity = getContext().getResources().getDisplayMetrics().density;
        setOnPreferenceClickListener(this);
        if (attributeSet != null) {
            this.mAlphaSliderEnabled = attributeSet.getAttributeBooleanValue(null, "alphaSlider", false);
            this.mHexValueEnabled = attributeSet.getAttributeBooleanValue(null, "hexValue", false);
        }
    }

    @Override // android.preference.Preference
    protected void onBindView(View view) {
        super.onBindView(view);
        this.mView = view;
        setPreviewColor();
    }

    private void setPreviewColor() {
        if (this.mView == null) {
            return;
        }
        ImageView imageView = new ImageView(getContext());
        LinearLayout linearLayout = (LinearLayout) this.mView.findViewById(16908312);
        if (linearLayout == null) {
            return;
        }
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setPadding(linearLayout.getPaddingLeft(), linearLayout.getPaddingTop(), (int) (this.mDensity * 8.0f), linearLayout.getPaddingBottom());
        int childCount = linearLayout.getChildCount();
        if (childCount > 0) {
            linearLayout.removeViews(0, childCount);
        }
        linearLayout.addView(imageView);
        linearLayout.setMinimumWidth(0);
        imageView.setBackgroundDrawable(new DMAlphaPatternDrawable((int) (this.mDensity * 5.0f)));
        imageView.setImageBitmap(getPreviewBitmap());
    }

    private Bitmap getPreviewBitmap() {
        int i = (int) (this.mDensity * 31.0f);
        int i2 = this.mValue;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        int width = createBitmap.getWidth();
        int height = createBitmap.getHeight();
        int i3 = 0;
        while (i3 < width) {
            int i4 = i3;
            while (i4 < height) {
                int i5 = (i3 <= 1 || i4 <= 1 || i3 >= width + (-2) || i4 >= height + (-2)) ? -7829368 : i2;
                createBitmap.setPixel(i3, i4, i5);
                if (i3 != i4) {
                    createBitmap.setPixel(i4, i3, i5);
                }
                i4++;
            }
            i3++;
        }
        return createBitmap;
    }

    @Override // com.picspool.lib.widget.listener.OnDMColorChangedListener
    public void onColorChanged(int i) {
        if (isPersistent()) {
            persistInt(i);
        }
        this.mValue = i;
        setPreviewColor();
        try {
            getOnPreferenceChangeListener().onPreferenceChange(this, Integer.valueOf(i));
        } catch (NullPointerException unused) {
        }
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        showDialog(null);
        return false;
    }

    protected void showDialog(Bundle bundle) {
        DMColorPickerDialogView dMColorPickerDialogView = new DMColorPickerDialogView(getContext(), this.mValue);
        this.mDialog = dMColorPickerDialogView;
        dMColorPickerDialogView.setOnColorChangedListener(this);
        if (this.mAlphaSliderEnabled) {
            this.mDialog.setAlphaSliderVisible(true);
        }
        if (this.mHexValueEnabled) {
            this.mDialog.setHexValueEnabled(true);
        }
    }

    public void setAlphaSliderEnabled(boolean z) {
        this.mAlphaSliderEnabled = z;
    }

    public void setHexValueEnabled(boolean z) {
        this.mHexValueEnabled = z;
    }

    @Override // android.preference.Preference
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        showDialog(savedState.dialogBundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() { // from class: com.picspool.lib.color.DMColorPreference.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        Bundle dialogBundle;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.dialogBundle = parcel.readBundle();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeBundle(this.dialogBundle);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }
}
