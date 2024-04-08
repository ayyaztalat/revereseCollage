package com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad;
import com.picspool.lib.resource.DMWBRes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/* loaded from: classes.dex */
public class CSWBMaterialGroupRes extends DMWBRes {
    private int contentCount;
    private String groupID;
    private String groupIconFilePath;
    private String groupIconUriPath;
    private String groupName;
    private int groupOrder;
    private DMWBRes.LocationType groupType;
    private List<CSWBMaterialRes> list_material_res;
    private String uniqueGroupName;

    public int getContentCount() {
        return this.contentCount;
    }

    public void setContentCount(int i) {
        this.contentCount = i;
    }

    public int getGroupOrder() {
        return this.groupOrder;
    }

    public void setGroupOrder(int i) {
        this.groupOrder = i;
    }

    public String getGroupID() {
        return this.groupID;
    }

    public void setGroupID(String str) {
        this.groupID = str;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public String getGroupIconUriPath() {
        return this.groupIconUriPath;
    }

    public void setGroupIconUriPath(String str) {
        this.groupIconUriPath = str;
    }

    public String getGroupIconFilePath() {
        return this.groupIconFilePath;
    }

    public void setGroupIconFilePath(String str) {
        this.groupIconFilePath = str;
    }

    public String getUniqueGroupName() {
        return this.uniqueGroupName;
    }

    public void setUniqueGroupName(String str) {
        this.uniqueGroupName = str;
    }

    public DMWBRes.LocationType getGroupType() {
        return this.groupType;
    }

    public void setGroupType(DMWBRes.LocationType locationType) {
        this.groupType = locationType;
    }

    public List<CSWBMaterialRes> getWBMaterialResList() {
        return this.list_material_res;
    }

    public void setWBMaterialResList(List<CSWBMaterialRes> list) {
        this.list_material_res = list;
    }

    public Bitmap getGroupIconBitmap() {
        if (getGroupIconFilePath() != null && new File(getGroupIconFilePath()).exists() && getGroupType() == DMWBRes.LocationType.ONLINE) {
            return getCacheBitmap(this.context, getGroupIconFilePath(), 1);
        }
        return null;
    }

    private Bitmap getCacheBitmap(Context context, String str, int i) {
        Bitmap bitmap = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = i;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeStream(fileInputStream, null, options);
            fileInputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public void downloadGroupIconOnlineGroupRes(Context context, CSAsyncDownloadFileLoad.AsyncDownloadFileListener asyncDownloadFileListener) {
        if (context == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else if (getGroupType() != DMWBRes.LocationType.ONLINE) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else if (getGroupIconUriPath() == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else {
            CSAsyncDownloadFileLoad cSAsyncDownloadFileLoad = new CSAsyncDownloadFileLoad();
            cSAsyncDownloadFileLoad.setAsyncDownloadFileListener(asyncDownloadFileListener);
            cSAsyncDownloadFileLoad.execute(this.groupIconUriPath, getGroupIconFilePath());
        }
    }

    public void addContentItem(CSWBMaterialRes cSWBMaterialRes) {
        if (this.list_material_res == null) {
            this.list_material_res = new ArrayList();
        }
        this.list_material_res.add(cSWBMaterialRes);
    }

    public String toString() {
        return "CSWBMaterialGroupRes [groupID=" + this.groupID + ", groupName=" + this.groupName + ", uniqueGroupName=" + this.uniqueGroupName + ", groupOrder=" + this.groupOrder + ", groupIconUriPath=" + this.groupIconUriPath + ", groupIconFilePath=" + this.groupIconFilePath + ", groupType=" + this.groupType + ", contentCount=" + this.contentCount + "]";
    }
}
