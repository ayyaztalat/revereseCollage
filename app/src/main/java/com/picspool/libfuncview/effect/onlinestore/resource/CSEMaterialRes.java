package com.picspool.libfuncview.effect.onlinestore.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.picspool.libfuncview.effect.onlinestore.resource.CSAsyncDownloadFileLoad;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.ZipException;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.zip.DMTZipUtil;

/* loaded from: classes.dex */
public class CSEMaterialRes extends DMWBRes implements Serializable {
    private String banner;
    private String contentD2IconFilePath;
    private String contentDownFilePath;
    private String contentFilePath;
    private DMWBRes.LocationType contentType;
    private String contentUriPath;
    private String data_number;
    private String data_size;
    private String data_zip;
    private String desc;
    private String desiconFilePath;
    private String desiconsZipFilePath;
    private String effect_zip;
    private String g_id;
    private String icon;

    /* renamed from: id */
    private int f1662id;
    private String image;
    private String is_h_banner;
    private String is_h_cell;
    private String is_hot;
    private String is_lock;
    private String is_m_banner;
    private String is_new;
    private String is_paid;
    private String is_rec;
    private String max_version;
    private String min_version;
    private String name;
    private String position;
    private String rootFileName;
    private String sort_num;
    private String uniqid;
    private String uniqueName;
    private String update_time;

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

    public void downloadcontentRes(Context context, String str, String str2, CSAsyncDownloadFileLoad.AsyncDownloadFileListener asyncDownloadFileListener) {
        if (context == null) {
            if (asyncDownloadFileListener != null) {
                asyncDownloadFileListener.onImageDownLoadFaile();
                return;
            }
            return;
        }
        CSAsyncDownloadFileLoad cSAsyncDownloadFileLoad = new CSAsyncDownloadFileLoad();
        cSAsyncDownloadFileLoad.setAsyncDownloadFileListener(asyncDownloadFileListener);
        cSAsyncDownloadFileLoad.execute(str, str2);
    }

    public void deleteZip(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
    }

    public void upZip(String str, String str2) throws ZipException, IOException {
        DMTZipUtil.unZip(str, str2);
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

    public void delFolder(String str) {
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

    public boolean delAllFile(String str) {
        File file;
        File file2 = new File(str);
        if (file2.exists() && file2.isDirectory()) {
            String[] list = file2.list();
            if (list == null) {
                file2.delete();
                return false;
            }
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
        if (getContentFilePath() != null && new File(this.contentFilePath).isDirectory()) {
            File file = new File(this.contentFilePath);
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0 && file.exists()) {
                return true;
            }
            delAllFile(this.contentFilePath);
        }
        return false;
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

    public boolean equals(Object obj) {
        if (obj instanceof CSEMaterialRes) {
            return getUniqueName().equals(((CSEMaterialRes) obj).getUniqueName());
        }
        return super.equals(obj);
    }

    public String getContentDownFilePath() {
        return this.contentDownFilePath;
    }

    public void setContentDownFilePath(String str) {
        this.contentDownFilePath = str;
    }

    public String getDesiconFilePath() {
        return this.desiconFilePath;
    }

    public void setDesiconFilePath(String str) {
        this.desiconFilePath = str;
    }

    public String getDesiconsZipFilePath() {
        return this.desiconsZipFilePath;
    }

    public void setDesiconsZipFilePath(String str) {
        this.desiconsZipFilePath = str;
    }

    public DMWBRes.LocationType getContentType() {
        return this.contentType;
    }

    public void setContentType(DMWBRes.LocationType locationType) {
        this.contentType = locationType;
    }

    public String getUniqid() {
        return this.uniqid;
    }

    public void setUniqid(String str) {
        this.uniqid = str;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String str) {
        this.position = str;
    }

    public String getIs_lock() {
        return this.is_lock;
    }

    public void setIs_lock(String str) {
        this.is_lock = str;
    }

    public String getIs_hot() {
        return this.is_hot;
    }

    public void setIs_hot(String str) {
        this.is_hot = str;
    }

    public String getIs_new() {
        return this.is_new;
    }

    public void setIs_new(String str) {
        this.is_new = str;
    }

    public String getIs_rec() {
        return this.is_rec;
    }

    public void setIs_rec(String str) {
        this.is_rec = str;
    }

    public String getIs_m_banner() {
        return this.is_m_banner;
    }

    public void setIs_m_banner(String str) {
        this.is_m_banner = str;
    }

    public String getIs_h_banner() {
        return this.is_h_banner;
    }

    public void setIs_h_banner(String str) {
        this.is_h_banner = str;
    }

    public String getIs_h_cell() {
        return this.is_h_cell;
    }

    public void setIs_h_cell(String str) {
        this.is_h_cell = str;
    }

    public String getIs_paid() {
        return this.is_paid;
    }

    public void setIs_paid(String str) {
        this.is_paid = str;
    }

    public String getSort_num() {
        return this.sort_num;
    }

    public void setSort_num(String str) {
        this.sort_num = str;
    }

    public String getMin_version() {
        return this.min_version;
    }

    public void setMin_version(String str) {
        this.min_version = str;
    }

    public String getMax_version() {
        return this.max_version;
    }

    public void setMax_version(String str) {
        this.max_version = str;
    }

    public String getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(String str) {
        this.update_time = str;
    }

    public String getG_id() {
        return this.g_id;
    }

    public void setG_id(String str) {
        this.g_id = str;
    }

    public int getId() {
        return this.f1662id;
    }

    public void setId(int i) {
        this.f1662id = i;
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public String getName() {
        return this.name;
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public void setName(String str) {
        this.name = str;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String getBanner() {
        return this.banner;
    }

    public void setBanner(String str) {
        this.banner = str;
    }

    public String getEffect_zip() {
        return this.effect_zip;
    }

    public void setEffect_zip(String str) {
        this.effect_zip = str;
    }

    public String getData_number() {
        return this.data_number;
    }

    public void setData_number(String str) {
        this.data_number = str;
    }

    public String getData_zip() {
        return this.data_zip;
    }

    public void setData_zip(String str) {
        this.data_zip = str;
    }

    public String getData_size() {
        return this.data_size;
    }

    public void setData_size(String str) {
        this.data_size = str;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }
}
