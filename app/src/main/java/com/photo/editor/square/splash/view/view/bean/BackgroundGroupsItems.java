package com.photo.editor.square.splash.view.view.bean;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;

import com.photo.editor.square.splash.app.CSMyApp;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.sky.testproject.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/* loaded from: classes2.dex */
public class BackgroundGroupsItems {
    public static final String ANDROID_ASSETS_PATH = "file:///android_asset/";
    private static final String TAG = "Jie";
    private final List<BackgroundGroup> backgroundGroupInfo;


    private BackgroundGroupsItems() {
        ArrayList<BackgroundGroup> arrayList = new ArrayList<>();
        this.backgroundGroupInfo = arrayList;
        arrayList.add(createColorGroup());
        this.backgroundGroupInfo.add(createGradientGroup());
    }

    private BackgroundGroup createColorGroup() {
        ArrayList<BackgroundItem> arrayList = new ArrayList<>();
        arrayList.add(createColorVector(R.color.white, "P1"));
        arrayList.add(createColorVector(R.color.black, "P2"));
        arrayList.add(createColorVector(R.color.bg_fresh1, "P3"));
        arrayList.add(createColorVector(R.color.bg_fresh2, "P4"));
        arrayList.add(createColorVector(R.color.bg_fresh3, "P5"));
        arrayList.add(createColorVector(R.color.bg_fresh7, "P6"));
        arrayList.add(createColorVector(R.color.bg_fresh8, "P7"));
        BackgroundGroup backgroundGroup = new BackgroundGroup();
        backgroundGroup.icon = "file:///android_asset/bg/icon/bg_color_icon.png";
        backgroundGroup.items = arrayList;
        backgroundGroup.name = "Color";
        return backgroundGroup;
    }

    public List<BackgroundGroup> getBackgroundGroupInfo() {
        return this.backgroundGroupInfo;
    }

    private BackgroundGroup createGradientGroup() {
        ArrayList<BackgroundItem> arrayList = new ArrayList<>();
        arrayList.add(createColorVector(R.color.bg_gradient_3_2, R.color.bg_gradient_3_1, "G1"));
        arrayList.add(createColorVector(R.color.bg_gradient_4_1, R.color.bg_gradient_4_2, "G2"));
        arrayList.add(createColorVector(R.color.bg_gradient_5_2, R.color.bg_gradient_5_1, "G3"));
        arrayList.add(createColorVector(R.color.bg_gradient_6_1, R.color.bg_gradient_6_2, "G4"));
        arrayList.add(createColorVector(R.color.bg_gradient_9_1, R.color.bg_gradient_9_2, "G5"));
        arrayList.add(createColorVector(R.color.bg_gradient_10_2, R.color.bg_gradient_10_1, "G6"));
        arrayList.add(createColorVector(R.color.bg_gradient_11_1, R.color.bg_gradient_11_2, "G7"));
        BackgroundGroup backgroundGroup = new BackgroundGroup();
        backgroundGroup.icon = "file:///android_asset/bg/icon/bg_gradients_icon.png";
        backgroundGroup.items = arrayList;
        backgroundGroup.name = "Gradient";
        return backgroundGroup;
    }

    private BackgroundItem createColorVector(int i, String str) {
        BackgroundItem backgroundItem = new BackgroundItem();
        backgroundItem.name = str;
        backgroundItem.icon = CSMyApp.getContext().getResources().getColor(i);
        backgroundItem.image = backgroundItem.icon;
        return backgroundItem;
    }

    private BackgroundItem createColorVector(int i, int i2, String str) {
        BackgroundItem backgroundItem = new BackgroundItem();
        backgroundItem.name = str;
        Vector<Integer> vector = new Vector<>(2);
        vector.add(0, CSMyApp.getContext().getResources().getColor(i));
        vector.add(1, CSMyApp.getContext().getResources().getColor(i2));
        backgroundItem.image = vector;
        backgroundItem.icon = vector;
        return backgroundItem;
    }

    /* loaded from: classes2.dex */
    public static class BackgroundGroup {
        String icon;
        boolean isDownloading;
        boolean isHot;
        boolean isNew;
        List<BackgroundItem> items;
        String name;
        String onlineZip;

        public String getOnlineZip() {
            return this.onlineZip;
        }

        public void setOnlineZip(String str) {
            this.onlineZip = str;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String str) {
            this.icon = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public boolean isDownloading() {
            return this.isDownloading;
        }

        public void setDownloading(boolean z) {
            this.isDownloading = z;
        }

        public boolean isNew() {
            return this.isNew;
        }

        public void setNew(boolean z) {
            this.isNew = z;
        }

        public boolean isHot() {
            return this.isHot;
        }

        public void setHot(boolean z) {
            this.isHot = z;
        }

        public List<BackgroundItem> getList(){
            return items;
        }


        public  List<BackgroundItem> imageFiles(File file) {
            File[] listFiles;
            if (this.items.isEmpty()) {
                for (File file2 : file.listFiles()) {
                    BackgroundItem backgroundItem = new BackgroundItem();
                    backgroundItem.image = file2.getPath();
                    backgroundItem.icon = file2.toURI().toString();
                    this.items.add(backgroundItem);
                    backgroundItem.name = this.name.substring(0, 1).toUpperCase() + this.items.size();
                }
            }
            this.isDownloading = false;
            return this.items;
        }

        public List<BackgroundItem> getItems() {
            return this.items;
        }
    }

    /* loaded from: classes2.dex */
    public static class BackgroundItem {
        Object icon;
        Object image;
        String name;

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public Object getIcon() {
            Object obj = this.image;
            if (obj instanceof Integer) {
                int intValue = ((Integer) obj).intValue();
                ColorDrawable colorDrawable = new ColorDrawable();
                colorDrawable.setColor(intValue);
                colorDrawable.setBounds(0, 0, 100, 100);
                return colorDrawable;
            } else if (obj instanceof Vector) {
                int intValue2 = ((Integer) ((Vector) obj).get(0)).intValue();
                int intValue3 = ((Integer) ((Vector) this.image).get(1)).intValue();
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setColors(new int[]{intValue2, intValue3});
                gradientDrawable.setSize(100, 100);
                return gradientDrawable;
            } else {
                return Uri.parse(this.icon.toString());
            }
        }

        public void setIcon(Object obj) {
            this.icon = obj;
        }

        public Object getImage() {
            return this.image;
        }

        public void setImage(Object obj) {
            this.image = obj;
        }

        public void getImageBitmap(){

        }

//        public Observable getImageBitmap() {
//            return Observable.concat(Observable.just(this.image).ofType(Integer.class).
//                    map((Function) obj -> BackgroundItem.lambda$getImageBitmap$0((Integer) obj)),
//                    Observable.just(this.image).ofType(Vector.class)
//                            .map((Function) obj -> BackgroundItem.lambda$getImageBitmap$1((Vector) obj)),
//                    Observable.just(this.image)
//                            .ofType(String.class)
//                            .filter((Predicate) obj -> {
//                boolean startsWith;
//                startsWith = ((String) obj).startsWith(IL23.ANDROID_ASSETS_PATH);
//                return startsWith;
//            }
//            ).map(obj -> BackgroundItem.lambda$getImageBitmap$3((String) obj)), Observable.just(this.image).ofType(String.class).filter((Predicate) obj -> BackgroundItem.lambda$getImageBitmap$4((String) obj)).map(obj -> BackgroundItem.lambda$getImageBitmap$5((String) obj)));
//        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static  Drawable lambda$getImageBitmap$0(Integer num) {
            int intValue = num.intValue();
            ColorDrawable colorDrawable = new ColorDrawable();
            colorDrawable.setColor(intValue);
            colorDrawable.setBounds(0, 0, 100, 100);
            return colorDrawable;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static  Drawable lambda$getImageBitmap$1(Vector vector) {
            int intValue = ((Integer) vector.get(0)).intValue();
            int intValue2 = ((Integer) vector.get(1)).intValue();
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setColors(new int[]{intValue, intValue2});
            gradientDrawable.setSize(100, 100);
            return gradientDrawable;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static  Drawable lambda$getImageBitmap$3(String str) throws Exception {
            return new BitmapDrawable(DMBitmapDbUtil.getImageFromAssetsFile(CSMyApp.getContext(), str));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static  boolean lambda$getImageBitmap$4(String str) throws Exception {
            Log.e("Jie", "getImageBitmap: " + str);
            return !str.startsWith("ANDROID_ASSETS_PATH");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static  Drawable lambda$getImageBitmap$5(String str) throws Exception {
            Drawable createFromPath = Drawable.createFromPath(str);
            Log.e("Jie", "getImageBitmap: " + createFromPath);
            return createFromPath;
        }

    }

    public static BackgroundGroupsItems getInstance() {
        return Builder.instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Builder {
        private static final BackgroundGroupsItems instance = new BackgroundGroupsItems();

        private Builder() {
        }
    }
}
