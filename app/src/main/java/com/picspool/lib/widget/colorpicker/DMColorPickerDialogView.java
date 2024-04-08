package com.picspool.lib.widget.colorpicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Locale;
import com.picspool.lib.color.DMColorConvert;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMColorPickerDialogView extends LinearLayout implements OnDMColorChangedListener {
    private DMColorPickerView mColorPicker;
    private ColorStateList mHexDefaultTextColor;
    private EditText mHexVal;
    private boolean mHexValueEnabled;
    private OnDMColorChangedListener mListener;
    private DMColorPanelView mNewColor;
    private DMColorPanelView mOldColor;

    public DMColorPickerDialogView(Context context, int i) {
        super(context);
        this.mHexValueEnabled = false;
        init(i);
    }

    private void init(int i) {
        setUp(i);
    }

    private void setUp(int i) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_dialog_colorpicker, (ViewGroup) this, true);
        this.mColorPicker = (DMColorPickerView) findViewById(R.id.color_picker_view);
        this.mOldColor = (DMColorPanelView) findViewById(R.id.old_color_panel);
        this.mNewColor = (DMColorPanelView) findViewById(R.id.new_color_panel);
        EditText editText = (EditText) findViewById(R.id.hex_val);
        this.mHexVal = editText;
        editText.setInputType(524288);
        this.mHexDefaultTextColor = this.mHexVal.getTextColors();
        this.mHexVal.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.picspool.lib.widget.colorpicker.DMColorPickerDialogView.1
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                if (i2 == 6) {
                    ((InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    String obj = DMColorPickerDialogView.this.mHexVal.getText().toString();
                    if (obj.length() <= 5 && obj.length() >= 10) {
                        DMColorPickerDialogView.this.mHexVal.setTextColor(-65536);
                    } else {
                        try {
                            DMColorPickerDialogView.this.mColorPicker.setColor(DMColorConvert.convertToColorInt(obj.toString()), true);
                            DMColorPickerDialogView.this.mHexVal.setTextColor(DMColorPickerDialogView.this.mHexDefaultTextColor);
                        } catch (IllegalArgumentException unused) {
                            DMColorPickerDialogView.this.mHexVal.setTextColor(-65536);
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        ((LinearLayout) this.mOldColor.getParent()).setPadding(Math.round(this.mColorPicker.getDrawingOffset()), 0, Math.round(this.mColorPicker.getDrawingOffset()), 0);
        this.mColorPicker.setOnColorChangedListener(this);
        this.mOldColor.setColor(i);
        this.mColorPicker.setColor(i, true);
    }

    public void setColor(int i) {
        this.mOldColor.setColor(i);
        this.mColorPicker.setColor(i, true);
    }

    @Override // com.picspool.lib.widget.listener.OnDMColorChangedListener
    public void onColorChanged(int i) {
        this.mNewColor.setColor(i);
        if (this.mHexValueEnabled) {
            updateHexValue(i);
        }
    }

    public void setHexValueEnabled(boolean z) {
        this.mHexValueEnabled = z;
        if (z) {
            this.mHexVal.setVisibility(View.VISIBLE);
            updateHexLengthFilter();
            updateHexValue(getColor());
            return;
        }
        this.mHexVal.setVisibility(View.GONE);
    }

    public boolean getHexValueEnabled() {
        return this.mHexValueEnabled;
    }

    private void updateHexLengthFilter() {
        if (getAlphaSliderVisible()) {
            this.mHexVal.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
        } else {
            this.mHexVal.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
        }
    }

    private void updateHexValue(int i) {
        if (getAlphaSliderVisible()) {
            this.mHexVal.setText(DMColorConvert.convertToARGB(i).toUpperCase(Locale.getDefault()));
        } else {
            this.mHexVal.setText(DMColorConvert.convertToRGB(i).toUpperCase(Locale.getDefault()));
        }
        this.mHexVal.setTextColor(this.mHexDefaultTextColor);
    }

    public void setAlphaSliderVisible(boolean z) {
        this.mColorPicker.setAlphaSliderVisible(z);
        if (this.mHexValueEnabled) {
            updateHexLengthFilter();
            updateHexValue(getColor());
        }
    }

    public boolean getAlphaSliderVisible() {
        return this.mColorPicker.getAlphaSliderVisible();
    }

    public void setOnColorChangedListener(OnDMColorChangedListener onDMColorChangedListener) {
        this.mListener = onDMColorChangedListener;
    }

    public int getColor() {
        return this.mColorPicker.getColor();
    }

    public void onOKClick() {
        OnDMColorChangedListener onDMColorChangedListener = this.mListener;
        if (onDMColorChangedListener != null) {
            onDMColorChangedListener.onColorChanged(this.mNewColor.getColor());
        }
    }
}
