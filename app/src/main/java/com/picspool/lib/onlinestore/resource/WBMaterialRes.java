package com.picspool.lib.onlinestore.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.zip.DMTZipUtil;

/* loaded from: classes3.dex */
public class WBMaterialRes extends DMWBRes {
    private Object[] backups;
    private String contentDownFilePath;
    private String contentFilePath;
    private String contentHot;
    private String contentMinVersion;
    private int contentOrder;
    private DMWBRes.LocationType contentType;
    private String contentUriPath;
    private String funName;
    private String groupID;
    private String groupIconFilePath;
    private String groupIconUriPath;
    private String groupName;
    private int groupOrder;
    private String iconUriPath;
    private String materialID;
    private String materialJSONInfo;
    private String materialUTC;
    private String rootFileName;
    private String uniqueGroupName;
    private String uniqueName;

    public void downloadFileOnlineRes(Context context, DMAsyncDownloadFileLoad.AsyncDownloadFileListener asyncDownloadFileListener) {
        if (context == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else if (this.contentType != DMWBRes.LocationType.ONLINE) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else if (getContentUriPath() == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else {
            DMAsyncDownloadFileLoad dMAsyncDownloadFileLoad = new DMAsyncDownloadFileLoad();
            dMAsyncDownloadFileLoad.setAsyncDownloadFileListener(asyncDownloadFileListener);
            dMAsyncDownloadFileLoad.execute(this.contentUriPath, this.contentDownFilePath);
        }
    }

    public void downloadGroupIconOnlineRes(Context context, DMAsyncDownloadFileLoad.AsyncDownloadFileListener asyncDownloadFileListener) {
        if (context == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else if (getGroupIconUriPath() == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else {
            DMAsyncDownloadFileLoad dMAsyncDownloadFileLoad = new DMAsyncDownloadFileLoad();
            dMAsyncDownloadFileLoad.setAsyncDownloadFileListener(asyncDownloadFileListener);
            dMAsyncDownloadFileLoad.execute(this.groupIconUriPath, this.groupIconFilePath);
        }
    }

    public void downloadIconOnlineRes(Context context, DMAsyncDownloadFileLoad.AsyncDownloadFileListener asyncDownloadFileListener) {
        if (context == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else if (getIconType() != DMWBRes.LocationType.ONLINE) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else if (getIconUriPath() == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else {
            DMAsyncDownloadFileLoad dMAsyncDownloadFileLoad = new DMAsyncDownloadFileLoad();
            dMAsyncDownloadFileLoad.setAsyncDownloadFileListener(asyncDownloadFileListener);
            dMAsyncDownloadFileLoad.execute(this.iconUriPath, getIconFileName());
        }
    }

    public void upZip() throws IOException {
        String str = this.contentDownFilePath;
        DMTZipUtil.unZip(str, this.rootFileName + "/" + this.uniqueName + "/");
    }

    public void delMaterialFromFile() {
        delFolder(this.rootFileName);
    }

    public void delContentDownFromFile() {
        delFolder(this.contentDownFilePath);
    }

    public void delContentFromFile() {
        try {
            String[] list = new File(this.rootFileName).list();
            if (list != null) {
                for (String str : list) {
                    if (!str.equals(WBMaterialFactory.MaterialIconName) && !str.equals(WBMaterialFactory.MaterialJSONName)) {
                        delFolder(this.rootFileName + "/" + str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delFolder(String str) {
        if (str == null) {
            return;
        }
        try {
            delAllFile(str);
            new File(str.toString()).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean delAllFile(String str) {
        File file;
        File file2 = new File(str);
        if (file2.exists() && file2.isDirectory()) {
            String[] list = file2.list();
            boolean z = false;
            for (int i = 0; i < list.length; i++) {
                if (str.endsWith(File.separator)) {
                    file = new File(str + list[i]);
                } else {
                    file = new File(str + File.separator + list[i]);
                }
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    delAllFile(str + "/" + list[i]);
                    delFolder(str + "/" + list[i]);
                    z = true;
                }
            }
            return z;
        }
        return false;
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        if (getIconFileName() != null && new File(getIconFileName()).exists()) {
            if (getIconType() == DMWBRes.LocationType.ONLINE) {
                return getCacheBitmap(this.context, getIconFileName(), 1);
            }
            return super.getIconBitmap();
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

    public boolean isContentExist() {
        if (getContentFilePath() == null) {
            return false;
        }
        File file = new File(this.contentFilePath);
        if (file.isDirectory()) {
            return true;
        }
        return file.exists();
    }

    public String getFunName() {
        return this.funName;
    }

    public void setFunName(String str) {
        this.funName = str;
    }

    public String getContentUriPath() {
        return this.contentUriPath;
    }

    public void setContentUriPath(String str) {
        this.contentUriPath = str;
    }

    public String getContentFilePath() {
        return this.contentFilePath;
    }

    public void setContentFilePath(String str) {
        this.contentFilePath = str;
    }

    public String getMaterialUTC() {
        return this.materialUTC;
    }

    public void setMaterialUTC(String str) {
        this.materialUTC = str;
    }

    public String getMaterialID() {
        return this.materialID;
    }

    public void setMaterialID(String str) {
        this.materialID = str;
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

    public DMWBRes.LocationType getContentType() {
        return this.contentType;
    }

    public void setContentType(DMWBRes.LocationType locationType) {
        this.contentType = locationType;
    }

    public String getIconUriPath() {
        return this.iconUriPath;
    }

    public void setIconUriPath(String str) {
        this.iconUriPath = str;
    }

    public int getContentOrder() {
        return this.contentOrder;
    }

    public void setContentOrder(int i) {
        this.contentOrder = i;
    }

    public String getRootFileName() {
        return this.rootFileName;
    }

    public void setRootFileName(String str) {
        this.rootFileName = str;
    }

    public String getMaterialJSONInfo() {
        return this.materialJSONInfo;
    }

    public void setMaterialJSONInfo(String str) {
        this.materialJSONInfo = str;
    }

    public String getUniqueName() {
        return this.uniqueName;
    }

    public void setUniqueName(String str) {
        this.uniqueName = str;
    }

    public boolean equals(Object obj) {
        if (obj instanceof WBMaterialRes) {
            return getUniqueName().equals(((WBMaterialRes) obj).getUniqueName());
        }
        return super.equals(obj);
    }

    public String getUniqueGroupName() {
        return this.uniqueGroupName;
    }

    public void setUniqueGroupName(String str) {
        this.uniqueGroupName = str;
    }

    public String getContentMinVersion() {
        return this.contentMinVersion;
    }

    public void setContentMinVersion(String str) {
        this.contentMinVersion = str;
    }

    public String getContentHot() {
        return this.contentHot;
    }

    public void setContentHot(String str) {
        this.contentHot = str;
    }

    public Object[] getBackups() {
        return this.backups;
    }

    public String toString() {
        return "WBMaterialRes [contentType=" + this.contentType + ", funName=" + this.funName + ", rootFileName=" + this.rootFileName + ", materialJSONInfo=" + this.materialJSONInfo + ", iconUriPath=" + this.iconUriPath + ", contentUriPath=" + this.contentUriPath + ", contentFilePath=" + this.contentFilePath + ", materialUTC=" + this.materialUTC + ", materialID=" + this.materialID + ", contentOrder=" + this.contentOrder + ", contentMinVersion=" + this.contentMinVersion + ", contentHot=" + this.contentHot + ", uniqueName=" + this.uniqueName + ", groupID=" + this.groupID + ", groupName=" + this.groupName + ", uniqueGroupName=" + this.uniqueGroupName + ", groupOrder=" + this.groupOrder + ", groupIconUriPath=" + this.groupIconUriPath + ", groupIconFilePath=" + this.groupIconFilePath + "]";
    }

    public String getContentDownFilePath() {
        return this.contentDownFilePath;
    }

    public void setContentDownFilePath(String str) {
        this.contentDownFilePath = str;
    }

    public void setBackups(Object... objArr) {
        this.backups = objArr;
    }
}
