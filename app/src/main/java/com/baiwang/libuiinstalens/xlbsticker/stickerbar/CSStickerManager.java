package com.baiwang.libuiinstalens.xlbsticker.stickerbar;

import android.content.Context;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSStickerBarConfig;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialFactory;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerGroup;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
public class CSStickerManager {
    private int[] group_itemscount;
    private int[] group_pagerscount;
    private Context mContext;
    private String onlinestciker_path;
    private int onlinestickegroup_count;
    private int pagercount = 0;
    private List<CSStickerGroup> list_groups = new ArrayList();

    public CSStickerManager(Context context) {
        this.mContext = context;
        initOnlineStickerGroup();
        initLocalStickerGroup();
        listCollections();
        this.group_itemscount = new int[this.list_groups.size()];
        this.group_pagerscount = new int[this.list_groups.size()];
        for (int i = 0; i < this.list_groups.size(); i++) {
            this.group_itemscount[i] = this.list_groups.get(i).getList_sticker().size();
            this.group_pagerscount[i] = (int) Math.ceil(this.list_groups.get(i).getList_sticker().size() / CSXlbStickerBarView.grid_item_count);
            this.pagercount += this.group_pagerscount[i];
        }
    }

    private void listCollections() {
        Collections.sort(this.list_groups, new Comparator<CSStickerGroup>() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerManager.1
            @Override // java.util.Comparator
            public int compare(CSStickerGroup cSStickerGroup, CSStickerGroup cSStickerGroup2) {
                if (cSStickerGroup.getOrder() == cSStickerGroup2.getOrder()) {
                    return 0;
                }
                return cSStickerGroup.getOrder() > cSStickerGroup2.getOrder() ? 1 : -1;
            }
        });
    }

    private void initLocalStickerGroup() {
        try {
            String[] list = this.mContext.getAssets().list("stickers");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    this.list_groups.add(initGroup1Item(list[i], this.onlinestickegroup_count + i));
                }
            }
            int i2 = -1;
            for (int i3 = 0; i3 < this.list_groups.size(); i3++) {
                if (this.list_groups.get(i3).getGroup_name().equals("s_instalens_sticker_sticker_0012")) {
                    i2 = i3;
                }
            }
            if (i2 < 0 || i2 >= this.list_groups.size()) {
                return;
            }
            this.list_groups.remove(i2);
            CSStickerBarConfig.deleteOnlineStickerRes("s_instalens_sticker_sticker_0012", this.mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CSStickerGroup initEmojiItem() {
        CSStickerGroup cSStickerGroup = new CSStickerGroup(this.mContext);
        cSStickerGroup.setGroup_name("Emoji2");
        cSStickerGroup.setStickerGroupType(CSStickerGroup.StickerGroupType.ASSERT);
        String str = DMPreferencesUtil.get(this.mContext, "xlbsticker", "sticker_groupEmoji2");
        if (str != null) {
            cSStickerGroup.setOrder(Integer.valueOf(str).intValue());
        } else {
            cSStickerGroup.setOrder(0);
        }
        try {
            String[] list = this.mContext.getAssets().list("stickers/emoji");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    String str2 = "stickers/emoji/" + list[i];
                    if (list[i].equals("icon.png")) {
                        cSStickerGroup.setIconFileName(str2);
                        cSStickerGroup.setIconType(DMWBRes.LocationType.ASSERT);
                    } else {
                        cSStickerGroup.addStickers(initStickersItem(list[i], str2, "Emoji2", false));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cSStickerGroup;
    }

    private CSStickerGroup initGroup1Item(String str, int i) {
        CSStickerGroup cSStickerGroup = new CSStickerGroup(this.mContext);
        cSStickerGroup.setGroup_name(str);
        cSStickerGroup.setStickerGroupType(CSStickerGroup.StickerGroupType.ASSERT);
        String str2 = DMPreferencesUtil.get(this.mContext, "xlbsticker", "sticker_group" + str);
        if (str2 != null) {
            cSStickerGroup.setOrder(Integer.valueOf(str2).intValue());
        } else {
            cSStickerGroup.setOrder(1);
        }
        try {
            String[] list = this.mContext.getAssets().list("stickers/" + str);
            if (list != null) {
                for (int i2 = 0; i2 < list.length; i2++) {
                    String str3 = "stickers/" + str + "/" + list[i2];
                    if (list[i2].equals("icon.png")) {
                        cSStickerGroup.setIconFileName(str3);
                        cSStickerGroup.setIconType(DMWBRes.LocationType.ASSERT);
                    } else {
                        cSStickerGroup.addStickers(initStickersItem(list[i2], str3, str, false));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cSStickerGroup;
    }

    private void initOnlineStickerGroup() {
        initOnlineStickerPath();
        File[] listFiles = new File(this.onlinestciker_path).listFiles();
        Log.e("mpath", "onlinestciker_path!!!!!!!!!!!");
        if (listFiles != null) {
            Log.e("mpath", "onlinestciker_path");
            this.onlinestickegroup_count = listFiles.length;
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i] != null) {
                    File[] listFiles2 = new File(listFiles[i].getPath() + "/" + listFiles[i].getName()).listFiles();
                    if (listFiles2 != null) {
                        this.list_groups.add(initOnlineGroupItem(listFiles[i], listFiles2));
                    }
                }
            }
        }
    }

    private CSStickerGroup initOnlineGroupItem(File file) {
        String name = file.getName();
        File file2 = new File(file.getPath() + "/" + file.getName());
        File[] listFiles = file2.listFiles();
        if (listFiles != null) {
            CSStickerGroup cSStickerGroup = new CSStickerGroup(this.mContext);
            cSStickerGroup.setGroup_name(name);
            long lastModified = file2.lastModified();
            Log.d("xlb", lastModified + "");
            for (int i = 0; i < listFiles.length; i++) {
                cSStickerGroup.addStickers(initStickersItem(listFiles[i].getName(), listFiles[i].getPath(), name, true));
                cSStickerGroup.setOrder(i);
            }
            return cSStickerGroup;
        }
        return null;
    }

    private CSStickerGroup initOnlineGroupItem(File file, File[] fileArr) {
        String name = file.getName();
        CSStickerGroup cSStickerGroup = new CSStickerGroup(this.mContext);
        cSStickerGroup.setGroup_name(name);
        cSStickerGroup.setStickerGroupType(CSStickerGroup.StickerGroupType.ONLINE);
        String str = DMPreferencesUtil.get(this.mContext, "sticker_colorstyle", file.getName());
        if (str != null && str.matches("^\\d+$")) {
            if (str.equals("1")) {
                cSStickerGroup.setStickercolorstyle(1);
            } else if (str.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                cSStickerGroup.setStickercolorstyle(2);
            }
        } else {
            cSStickerGroup.setStickercolorstyle(1);
        }
        int lastModified = (int) file.lastModified();
        Log.d("xlb", lastModified + "");
        cSStickerGroup.setLastModified(lastModified);
        for (int i = 0; i < fileArr.length; i++) {
            String name2 = fileArr[i].getName();
            if (name2.contains(".data")) {
                if (name2.equals("icon.data")) {
                    cSStickerGroup.setIconType(DMWBRes.LocationType.ONLINE);
                    cSStickerGroup.setIconFileName(fileArr[i].getPath());
                } else {
                    cSStickerGroup.addStickers(initStickersItem(name2, fileArr[i].getPath(), name, true));
                    Context context = this.mContext;
                    String str2 = DMPreferencesUtil.get(context, "xlbsticker", "sticker_group" + name);
                    if (str2 != null) {
                        cSStickerGroup.setOrder(Integer.valueOf(str2).intValue());
                    }
                }
            }
        }
        return cSStickerGroup;
    }

    private CSStickerRes initStickersItem(String str, String str2, String str3, boolean z) {
        CSStickerRes cSStickerRes = new CSStickerRes();
        cSStickerRes.setName(str);
        cSStickerRes.setImageFileName(str2);
        cSStickerRes.setImageType(z ? DMWBRes.LocationType.ONLINE : DMWBRes.LocationType.ASSERT);
        cSStickerRes.setGroup_name(str3);
        return cSStickerRes;
    }

    public int[] getGroup_itemscount() {
        return this.group_itemscount;
    }

    public int[] getGroup_pagerscount() {
        return this.group_pagerscount;
    }

    public List<CSStickerGroup> getList_groups() {
        return this.list_groups;
    }

    public void setList_groups(List<CSStickerGroup> list) {
        this.list_groups = list;
    }

    private void initOnlineStickerPath() {
        File externalFilesDir;
        this.onlinestciker_path = null;
        String packageName = this.mContext.getApplicationContext().getPackageName();
        if (CSWBMaterialFactory.isSDAvailable() && (externalFilesDir = this.mContext.getExternalFilesDir(null)) != null) {
            this.onlinestciker_path = externalFilesDir.getPath();
            this.onlinestciker_path += "/" + packageName + CSWBMaterialFactory.SDRootDirName + "/.material/sticker";
        }
        if (this.onlinestciker_path == null) {
            File filesDir = this.mContext.getFilesDir();
            this.onlinestciker_path = filesDir.getAbsolutePath() + "/" + packageName + CSWBMaterialFactory.SDRootDirName + "/.material/sticker";
        }
        Log.e("patj", this.onlinestciker_path);
    }
}
