package com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.asyncload.CSAsyncDownloadFileLoad;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.zip.DMTZipUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
/* loaded from: classes.dex */
public class CSWBMaterialRes extends DMWBRes implements Serializable {
    private String contentDownFilePath;
    private String contentFilePath;
    private String contentHot;
    private String contentMinVersion;
    private int contentOrder;
    private DMWBRes.LocationType contentType;
    private String contentUriPath;
    private String content_backup_1;
    private String content_backup_2;
    private String content_backup_3;
    private String content_backup_4;
    private String content_backup_5;
    private int content_size;
    private String funName;
    private CSWBMaterialGroupRes group_res;
    private String group_unique_name;
    private String iconUriPath;
    private String materialID;
    private String materialJSONInfo;
    private String materialUTC;
    private String rootFileName;
    private String uniqueName;

    public void downloadFileOnlineRes(Context context, CSAsyncDownloadFileLoad.AsyncDownloadFileListener asyncDownloadFileListener) {
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
            CSAsyncDownloadFileLoad cSAsyncDownloadFileLoad = new CSAsyncDownloadFileLoad();
            cSAsyncDownloadFileLoad.setAsyncDownloadFileListener(asyncDownloadFileListener);
            cSAsyncDownloadFileLoad.execute(this.contentUriPath, this.contentDownFilePath);
        }
    }

    public void downloadGroupIconOnlineRes(Context context, CSAsyncDownloadFileLoad.AsyncDownloadFileListener asyncDownloadFileListener) {
        if (context == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else if (this.group_res.getGroupIconUriPath() == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
            }
        } else {
            CSAsyncDownloadFileLoad cSAsyncDownloadFileLoad = new CSAsyncDownloadFileLoad();
            cSAsyncDownloadFileLoad.setAsyncDownloadFileListener(asyncDownloadFileListener);
            cSAsyncDownloadFileLoad.execute(this.group_res.getGroupIconUriPath(), this.group_res.getGroupIconFilePath());
        }
    }

    public void downloadIconOnlineRes(Context context, CSAsyncDownloadFileLoad.AsyncDownloadFileListener asyncDownloadFileListener) {
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
            CSAsyncDownloadFileLoad cSAsyncDownloadFileLoad = new CSAsyncDownloadFileLoad();
            cSAsyncDownloadFileLoad.setAsyncDownloadFileListener(asyncDownloadFileListener);
            cSAsyncDownloadFileLoad.execute(this.iconUriPath, getIconFileName());
        }
    }

    public void deleteZip() {
        File file = new File(this.contentDownFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public void upZip() throws ZipException, IOException {
        String str = this.contentDownFilePath;
        DMTZipUtil.unZip(str, this.rootFileName + "/" + this.uniqueName + "/");
    }

    private int upZipFile(File file, String str, String str2) throws ZipException, IOException {
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        byte[] bArr = new byte[1024];
        while (entries.hasMoreElements()) {
            ZipEntry nextElement = entries.nextElement();
            if (nextElement.isDirectory()) {
                new File(new String((str + str2).getBytes("8859_1"), "GB2312")).mkdir();
            } else {
                String[] split = nextElement.getName().split("/");
                if (split.length != 0) {
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(getRealFileName(str, str2 + "/" + split[split.length - 1])));
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(nextElement));
                    while (true) {
                        int read = bufferedInputStream.read(bArr, 0, 1024);
                        if (read == -1) {
                            break;
                        }
                        bufferedOutputStream.write(bArr, 0, read);
                    }
                    bufferedInputStream.close();
                    bufferedOutputStream.close();
                }
            }
        }
        zipFile.close();
        return 0;
    }

    private File getRealFileName(String str, String str2) {
        String[] split = str2.split("/");
        File file = new File(str);
        if (split.length > 1) {
            int i = 0;
            while (i < split.length - 1) {
                String str3 = split[i];
                try {
                    str3 = new String(str3.getBytes("8859_1"), "GB2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;
                file = new File(file, str3);
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            String str4 = split[split.length - 1];
            try {
                str4 = new String(str4.getBytes("8859_1"), "GB2312");
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
            return new File(file, str4);
        }
        return file;
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
                    if (!str.equals(".icon")) {
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

    @Override // org.picspool.lib.resource.DMWBRes
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

    public boolean isContentExist(String str) {
        if (getContentFilePath() != null && new File(this.contentFilePath).isDirectory()) {
            if (new File(this.contentFilePath + "/" + str).exists()) {
                return true;
            }
            delAllFile(this.contentFilePath);
        }
        return false;
    }

    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                file.delete();
                return;
            }
            for (File file2 : listFiles) {
                delete(file2);
            }
            file.delete();
        }
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

    public String getUniqueName() {
        return this.uniqueName;
    }

    public void setUniqueName(String str) {
        this.uniqueName = str;
    }

    public CSWBMaterialGroupRes getWBMaterialGroupRes() {
        return this.group_res;
    }

    public void setWBMaterialGroupRes(CSWBMaterialGroupRes cSWBMaterialGroupRes) {
        this.group_res = cSWBMaterialGroupRes;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CSWBMaterialRes) {
            return getUniqueName().equals(((CSWBMaterialRes) obj).getUniqueName());
        }
        return super.equals(obj);
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

    public String getGroupUniqueName() {
        return this.group_unique_name;
    }

    public void setGroupUniqueName(String str) {
        this.group_unique_name = str;
    }

    public String getContentDownFilePath() {
        return this.contentDownFilePath;
    }

    public void setContentDownFilePath(String str) {
        this.contentDownFilePath = str;
    }

    public String getContent_backup_1() {
        return this.content_backup_1;
    }

    public void setContent_backup_1(String str) {
        this.content_backup_1 = str;
    }

    public String getContent_backup_2() {
        return this.content_backup_2;
    }

    public void setContent_backup_2(String str) {
        this.content_backup_2 = str;
    }

    public String getContent_backup_3() {
        return this.content_backup_3;
    }

    public void setContent_backup_3(String str) {
        this.content_backup_3 = str;
    }

    public String getContent_backup_4() {
        return this.content_backup_4;
    }

    public void setContent_backup_4(String str) {
        this.content_backup_4 = str;
    }

    public String getContent_backup_5() {
        return this.content_backup_5;
    }

    public void setContent_backup_5(String str) {
        this.content_backup_5 = str;
    }

    public int getContent_size() {
        return this.content_size;
    }

    public void setContent_size(int i) {
        this.content_size = i;
    }
}
