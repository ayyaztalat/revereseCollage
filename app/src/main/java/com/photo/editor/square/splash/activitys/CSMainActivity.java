package com.photo.editor.square.splash.activitys;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.blankj.utilcode.util.ToastUtils;
import com.magic.video.editor.effect.gallery.view.CSGalleryActivity;
import com.photo.editor.square.splash.rate.BpRateAgent;
import com.photo.editor.square.splash.rate.BpRateUtils;
import com.sky.testproject.R;
import com.winflag.Utils.CacheUtils;
import java.util.List;


/* loaded from: classes2.dex */
public class CSMainActivity extends AppCompatActivity {
    private boolean showRateDialog = false;
    long mExitTime = 0;

    /* renamed from: permissions  reason: collision with root package name */
    String[] f2026permissions = new String[2];

    /* JADX INFO: Access modifiers changed from: private */
    public void openSplashChooseImg() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_cs);
        new BpRateAgent(true, false).showRateDialog(this);
        BpRateUtils.setOldUserFlag(this);
        String[] strArr = this.f2026permissions;
        strArr[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
        strArr[1] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
//        if (Build.VERSION.SDK_INT >= 33) {
//            this.f2026permissions = r4;
//            String[] strArr2 = {"android.permission.READ_MEDIA_IMAGES"};
//        }
//        XXPermissions.setCheckMode(false);
    }

    public void openSplash(View view) {
//        XXPermissions.with(this).permission(this.f2026permissions).request(new OnPermissionCallback() { // from class: com.photo.editor.square.splash.activitys.CSMainActivity.1
//            @Override // com.hjq.permissions.OnPermissionCallback
//            public void onGranted(List<String> list, boolean z) {
//                if (z) {
//                    CSMainActivity.this.openSplashChooseImg();
//                } else {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                }
//            }
//
//            @Override // com.hjq.permissions.OnPermissionCallback
//            public void onDenied(List<String> list, boolean z) {
//                if (z) {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                } else {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                }
//            }
//        });
        CSMainActivity.this.openSplashChooseImg();
    }

    public void openSquare(View view) {
//        XXPermissions.with(this).permission(this.f2026permissions).request(new OnPermissionCallback() { // from class: com.photo.editor.square.splash.activitys.CSMainActivity.2
//            @Override // com.hjq.permissions.OnPermissionCallback
//            public void onGranted(List<String> list, boolean z) {
//                if (z) {
//                    CSMainActivity.this.openSquareActivity();
//                } else {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                }
//            }
//
//            @Override // com.hjq.permissions.OnPermissionCallback
//            public void onDenied(List<String> list, boolean z) {
//                if (z) {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                } else {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                }
//            }
//        });
        CSMainActivity.this.openSquareActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openSquareActivity() {
        Intent intent = new Intent(this, CSSquareMainActivity.class);
        Intent intent2 = new Intent(this, CSGalleryActivity.class);
        intent2.putExtra(CSGalleryActivity.MAX_SELECT_PIC_NUMBER_KEY, 1);
        intent2.putExtra(CSGalleryActivity.SHOW_PEOPLE_TIPS_KEY, false);
        intent2.putExtra(CSGalleryActivity.NEXT_ACTIVITY_INTENT, intent);
        startActivity(intent2);
    }

    public void openCollage(View view) {
//        XXPermissions.with(this).permission(this.f2026permissions).request(new OnPermissionCallback() { // from class: com.photo.editor.square.splash.activitys.CSMainActivity.3
//            @Override // com.hjq.permissions.OnPermissionCallback
//            public void onGranted(List<String> list, boolean z) {
//                if (z) {
//                    CSMainActivity.this.openCollageActivity();
//                } else {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                }
//            }
//
//            @Override // com.hjq.permissions.OnPermissionCallback
//            public void onDenied(List<String> list, boolean z) {
//                if (z) {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                } else {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                }
//            }
//        });
        CSMainActivity.this.openCollageActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openCollageActivity() {
        CacheUtils.setString(this, "collage_or_free", "collage");
        Intent intent = new Intent(this, TemplateActivity.class);
        Intent intent2 = new Intent(this, CSGalleryActivity.class);
        intent2.putExtra(CSGalleryActivity.MAX_SELECT_PIC_NUMBER_KEY, 22);
        intent2.putExtra(CSGalleryActivity.SHOW_PEOPLE_TIPS_KEY, false);
        intent2.putExtra(CSGalleryActivity.NEXT_ACTIVITY_INTENT, intent);
        startActivity(intent2);
    }

    public void openFree(View view) {
//        XXPermissions.with(this).permission(this.f2026permissions).request(new OnPermissionCallback() { // from class: com.photo.editor.square.splash.activitys.CSMainActivity.4
//            @Override // com.hjq.permissions.OnPermissionCallback
//            public void onGranted(List<String> list, boolean z) {
//                if (z) {
//                    CSMainActivity.this.openFreeActivity();
//                } else {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                }
//            }
//
//            @Override // com.hjq.permissions.OnPermissionCallback
//            public void onDenied(List<String> list, boolean z) {
//                if (z) {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                } else {
//                    ToastUtils.showShort(CSMainActivity.this.getString(R.string.no_permission));
//                }
//            }
//        });

        CSMainActivity.this.openFreeActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openFreeActivity() {
        CacheUtils.setString(this, "collage_or_free", "free");
        Intent intent = new Intent(this, FreeTemplateActivity.class);
        Intent intent2 = new Intent(this, CSGalleryActivity.class);
        intent2.putExtra(CSGalleryActivity.MAX_SELECT_PIC_NUMBER_KEY, 9);
        intent2.putExtra(CSGalleryActivity.SHOW_PEOPLE_TIPS_KEY, false);
        intent2.putExtra(CSGalleryActivity.NEXT_ACTIVITY_INTENT, intent);
        startActivity(intent2);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {

        if (!this.showRateDialog) {
            boolean showRateDialog = new BpRateAgent(true, true).showRateDialog(this);
            this.showRateDialog = true;
            if (showRateDialog) {
                return;
            }
        }
        if (System.currentTimeMillis() - this.mExitTime > 2000) {
            Toast.makeText(getApplicationContext(), getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
            this.mExitTime = System.currentTimeMillis();
            return;
        }
        finish();
        super.onBackPressed();
    }

    public void toSetting(View view) {
        startActivity(new Intent(this, CSSettingActivity.class));
    }
}
