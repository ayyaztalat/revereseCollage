package com.photoart.libsticker.sticker2;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;
import com.photoart.libsticker.sticker3.DMLibStickerManager;
import com.photoart.libsticker.sticker3.DMViewLockOnlineStickerInterface;
import com.picspool.lib.resource.DMWBRes;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes2.dex */
public class DMStickerGroupManager implements DMWBManager {
    private static DMStickerGroupManager instance;
    private boolean isFirstLockInit = true;
    private OnStickerGroupManagerListener listener;
    private DMViewLockOnlineStickerInterface lockOnlineStickerInterface;
    private Context mContext;
    private List<DMStickerImageRes> resList;

    /* loaded from: classes2.dex */
    public interface OnStickerGroupManagerListener {
        void adClick();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ef, code lost:
        r30 = r0;
        r14 = r25;
        r25 = r13;
        r13 = r26;
        r26 = r12;
        r31 = r28;
        r32 = r4;
        r33 = r5;
        r34 = r6;
        r35 = r7;
        r36 = r19;
        r19 = r8;
        r37 = r14;
        r14 = r9;
        r41 = r23;
        r23 = r2;
        r38 = r15;
        r15 = r44;
        r44 = r41;
        r12 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x012f, code lost:
        r11.resList.add(r14, initOnLineItem(r10, r20, r20, r22, r9, r24, r8, false, false));
        android.util.Log.e(r13, "1");
        r0 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0282, code lost:
        r0 = e;
     */
    /* JADX WARN: Removed duplicated region for block: B:60:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x03b6 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x02b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x007e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private DMStickerGroupManager(Context r43, DMViewLockOnlineStickerInterface r44) {
        /*
            Method dump skipped, instructions count: 951
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photoart.libsticker.sticker2.DMStickerGroupManager.<init>(android.content.Context, com.photoart.libsticker.sticker3.DMViewLockOnlineStickerInterface):void");
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getConfigJSON(Context context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedInputStream bufferedInputStream = null;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(context.getAssets().open("native_sticker_json.json"));
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = bufferedInputStream2.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                closeQuietly(bufferedInputStream2);
            } catch (IOException e) {
                e = e;
                bufferedInputStream = bufferedInputStream2;
                Log.e("", "IOException :" + e.getMessage());
                closeQuietly(bufferedInputStream);
                return byteArrayOutputStream.toString();
            } catch (Throwable th) {
                th = th;
                bufferedInputStream = bufferedInputStream2;
                closeQuietly(bufferedInputStream);
                throw th;
            }
        } catch (Throwable th2) {
          th2.printStackTrace();
        }
        return byteArrayOutputStream.toString();
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }

    public static DMStickerGroupManager getSingletManager(Context context) {
        if (instance == null) {
            instance = new DMStickerGroupManager(context, null);
        }
        return instance;
    }

    public static DMStickerGroupManager getSingletManager(Context context, DMViewLockOnlineStickerInterface dMViewLockOnlineStickerInterface) {
        if (instance == null) {
            instance = new DMStickerGroupManager(context, dMViewLockOnlineStickerInterface);
        }
        return instance;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        List<DMStickerImageRes> list = this.resList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        List<DMStickerImageRes> list = this.resList;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < this.resList.size(); i++) {
                DMStickerImageRes dMStickerImageRes = this.resList.get(i);
                if (dMStickerImageRes.getName().compareTo(str) == 0) {
                    return dMStickerImageRes;
                }
            }
        }
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMStickerImageRes getRes(int i) {
        List<DMStickerImageRes> list = this.resList;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return this.resList.get(i);
    }

    protected DMStickerImageRes initAssetItem(String str, String str2, String str3) {
        DMStickerImageRes dMStickerImageRes = new DMStickerImageRes();
        dMStickerImageRes.setContext(this.mContext);
        dMStickerImageRes.setName(str);
        dMStickerImageRes.setIconFileName(str2);
        dMStickerImageRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMStickerImageRes.setIconPressedFileName(str3);
        return dMStickerImageRes;
    }

    protected DMStickerImageRes initOnLineItem(String str, String str2, String str3, String str4, int i, int i2, int i3, boolean z, boolean z2) {
        DMStickerImageRes dMStickerImageRes = new DMStickerImageRes();
        dMStickerImageRes.setContext(this.mContext);
        dMStickerImageRes.setName(str);
        dMStickerImageRes.setIconType(DMWBRes.LocationType.ONLINE);
        dMStickerImageRes.setNativeJsonSticker(z2);
        dMStickerImageRes.setIconFileName(str2);
        dMStickerImageRes.setIconPressedFileName(str3);
        dMStickerImageRes.setStickersUrlBase(str4);
        dMStickerImageRes.setStickerNumber(i2);
        dMStickerImageRes.setPositionInleftMenu(i);
        dMStickerImageRes.setIslock(i3 == 1);
        dMStickerImageRes.setShowNewTag(z);
        return dMStickerImageRes;
    }

    private void isLoadAd(boolean z, String str) {
        if (this.lockOnlineStickerInterface != null && DMPreferencesUtil.get(this.mContext, DMLibStickerManager.CONFIG, str) == null && z) {
            this.isFirstLockInit = false;
            this.lockOnlineStickerInterface.initNative(this.mContext);
            this.lockOnlineStickerInterface.setOnLockAdListener(new DMViewLockOnlineStickerInterface.OnLockAdListener() { // from class: com.photoart.libsticker.sticker2.DMStickerGroupManager.1
                @Override // com.photoart.libsticker.sticker3.DMViewLockOnlineStickerInterface.OnLockAdListener
                public void adClick() {
                    DMStickerGroupManager.this.listener.adClick();
                }
            });
        }
    }

    public boolean isShowLockView(Activity activity) {
        DMViewLockOnlineStickerInterface dMViewLockOnlineStickerInterface = this.lockOnlineStickerInterface;
        if (dMViewLockOnlineStickerInterface == null) {
            return false;
        }
        return dMViewLockOnlineStickerInterface.isShowLockView(activity);
    }

    public void setOnStickerGroupManagerListener(OnStickerGroupManagerListener onStickerGroupManagerListener) {
        this.listener = onStickerGroupManagerListener;
    }
}
