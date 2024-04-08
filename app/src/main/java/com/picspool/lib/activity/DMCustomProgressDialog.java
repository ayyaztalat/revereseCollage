package com.picspool.lib.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMCustomProgressDialog extends Dialog {
    public DMCustomProgressDialog(Context context) {
        super(context);
    }

    public DMCustomProgressDialog(Context context, int i) {
        super(context, i);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        ((AnimationDrawable) ((ImageView) findViewById(R.id.spinnerImageView)).getBackground()).start();
    }

    public void setMessage(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() <= 0) {
            return;
        }
        findViewById(R.id.message).setVisibility(View.VISIBLE);
        TextView textView = (TextView) findViewById(R.id.message);
        textView.setText(charSequence);
        textView.invalidate();
    }

    public static DMCustomProgressDialog show(Context context, CharSequence charSequence, boolean z, OnCancelListener onCancelListener) {
        DMCustomProgressDialog createDialog = createDialog(context, charSequence, z, onCancelListener);
        createDialog.show();
        return createDialog;
    }

    public static DMCustomProgressDialog createDialog(Context context, CharSequence charSequence, boolean z, OnCancelListener onCancelListener) {
        DMCustomProgressDialog dMCustomProgressDialog = new DMCustomProgressDialog(context, R.style.Custom_Progress);
        dMCustomProgressDialog.setTitle("");
        dMCustomProgressDialog.setContentView(R.layout.dm_progress_custom);
        if (charSequence == null || charSequence.length() == 0) {
            dMCustomProgressDialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            ((TextView) dMCustomProgressDialog.findViewById(R.id.message)).setText(charSequence);
        }
        dMCustomProgressDialog.setCancelable(z);
        dMCustomProgressDialog.setOnCancelListener(onCancelListener);
        dMCustomProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams attributes = dMCustomProgressDialog.getWindow().getAttributes();
        attributes.dimAmount = 0.2f;
        dMCustomProgressDialog.getWindow().setAttributes(attributes);
        return dMCustomProgressDialog;
    }
}
