package com.picspool.lib.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/* loaded from: classes3.dex */
public class DMProcessDialogFragment extends DialogFragment {
    int processType = 0;

    public static DMProcessDialogFragment getInstance(String str) {
        DMProcessDialogFragment dMProcessDialogFragment = new DMProcessDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", str);
        dMProcessDialogFragment.setArguments(bundle);
        return dMProcessDialogFragment;
    }

    public void setProcessType(int i) {
        this.processType = i;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog createDialog;
        String string = getArguments().getString("text");
        if (this.processType == 0) {
            createDialog = new ProgressDialog(getActivity());
            ((ProgressDialog) createDialog).setMessage(string);
        } else {
            createDialog = DMCustomProgressDialog.createDialog(getContext(), string, false, null);
        }
        createDialog.setCanceledOnTouchOutside(false);
        return createDialog;
    }

    @Override // androidx.fragment.app.DialogFragment
    public void show(FragmentManager fragmentManager, String str) {
        super.show(fragmentManager, str);
    }

    @Override // androidx.fragment.app.DialogFragment
    public int show(FragmentTransaction fragmentTransaction, String str) {
        return super.show(fragmentTransaction, str);
    }
}
