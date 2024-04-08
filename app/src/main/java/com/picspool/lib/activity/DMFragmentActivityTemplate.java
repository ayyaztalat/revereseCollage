package com.picspool.lib.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.sky.testproject.R;


/* loaded from: classes3.dex */
public class DMFragmentActivityTemplate extends FragmentActivity {
    protected DMProcessDialogFragment dlg;
    private int processType = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

    public void setProcessDialogType(int i) {
        this.processType = i;
        try {
            if (this.dlg != null) {
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                if (beginTransaction != null) {
                    beginTransaction.remove(this.dlg);
                    beginTransaction.addToBackStack(null);
                    beginTransaction.commit();
                }
                this.dlg = null;
            }
        } catch (Exception unused) {
        }
    }

    public void showProcessDialog() {
        try {
            if (this.dlg != null) {
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                if (beginTransaction != null) {
                    beginTransaction.remove(this.dlg);
                    beginTransaction.addToBackStack(null);
                    beginTransaction.commit();
                }
                this.dlg = null;
            }
            if (this.dlg == null) {
                DMProcessDialogFragment dMProcessDialogFragment = new DMProcessDialogFragment();
                this.dlg = dMProcessDialogFragment;
                dMProcessDialogFragment.setProcessType(this.processType);
                Bundle bundle = new Bundle();
                bundle.putString("text", getResources().getString(R.string.dlg_processing));
                this.dlg.setArguments(bundle);
            }
            this.dlg.show(getSupportFragmentManager(), "process");
        } catch (Exception unused) {
        }
    }

    public void dismissProcessDialog() {
        try {
            if (this.dlg != null) {
                this.dlg.dismissAllowingStateLoss();
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                if (this.dlg != null && beginTransaction != null) {
                    beginTransaction.remove(this.dlg);
                    beginTransaction.addToBackStack(null);
                    beginTransaction.commit();
                }
                this.dlg = null;
            }
        } catch (Exception unused) {
        }
    }

    /* loaded from: classes3.dex */
    public class BtnBackOnClickListener implements View.OnClickListener {
        public BtnBackOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            DMFragmentActivityTemplate.this.finish();
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
            return false;
        }
        return false;
    }
}
