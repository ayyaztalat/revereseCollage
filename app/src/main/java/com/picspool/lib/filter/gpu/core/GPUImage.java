package com.picspool.lib.filter.gpu.core;

import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.view.WindowManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.picspool.lib.filter.gpu.core.GPUImageRenderer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.util.PixelBuffer;
import com.picspool.lib.filter.gpu.util.Rotation;
import com.sky.testproject.GPUImageNativeLibrary;
import com.sky.testproject.Opcodes;

/* loaded from: classes3.dex */
public class GPUImage {
    private final Context mContext;
    private Bitmap mCurrentBitmap;
    private GPUImageFilter mFilter;
    private boolean mFlipHorizontal;
    private boolean mFlipVertical;
    private GLSurfaceView mGlSurfaceView;
    private final GPUImageRenderer mRenderer;
    private Rotation mRotation;
    private ScaleType mScaleType = ScaleType.CENTER_INSIDE;

    /* loaded from: classes3.dex */
    public interface OnPictureSavedListener {
        void onPictureSaved(Uri uri);
    }

    /* loaded from: classes3.dex */
    public interface ResponseListener<T> {
        void response(T t);
    }

    /* loaded from: classes3.dex */
    public enum ScaleType {
        CENTER_INSIDE,
        CENTER_CROP,
        LEFT_INSIDE,
        RIGHT_INSIDE
    }

    public GPUImage(Context context) {
        if (!supportsOpenGLES2(context)) {
            throw new IllegalStateException("OpenGL ES 2.0 is not supported on this phone.");
        }
        this.mContext = context;
        GPUImageNativeLibrary.initGpuNativeLibrary(context);
        this.mFilter = new GPUImageFilter();
        GPUImageRenderer gPUImageRenderer = new GPUImageRenderer(this.mFilter);
        this.mRenderer = gPUImageRenderer;
        gPUImageRenderer.setOnPreviewRendererListener(new GPUImageRenderer.OnPreviewRendererListener() { // from class: com.picspool.lib.filter.gpu.core.GPUImage.1
            @Override // com.picspool.lib.filter.gpu.core.GPUImageRenderer.OnPreviewRendererListener
            public void onRequestRenderer() {
                GPUImage.this.requestRender();
            }
        });
    }

    private boolean supportsOpenGLES2(Context context) {
        return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 131072;
    }

    public void setGLSurfaceView(GLSurfaceView gLSurfaceView, Boolean bool) {
        this.mGlSurfaceView = gLSurfaceView;
        gLSurfaceView.setEGLContextClientVersion(2);
        if (bool.booleanValue()) {
            this.mGlSurfaceView.setZOrderOnTop(true);
            this.mGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            this.mGlSurfaceView.getHolder().setFormat(-3);
        }
        this.mGlSurfaceView.setRenderer(this.mRenderer);
        this.mGlSurfaceView.setRenderMode(0);
        this.mGlSurfaceView.requestRender();
    }

    public void setBackgroundColor(int i) {
        this.mRenderer.setGpuBgColors(Color.red(i) / 255.0f, Color.green(i) / 255.0f, Color.blue(i) / 255.0f, 1.0f);
    }

    public void setRotate(Rotation rotation) {
        this.mRenderer.setRotate(rotation);
    }

    public void setRotation(Rotation rotation, boolean z, boolean z2) {
        this.mRenderer.setRotation(rotation, z, z2);
    }

    public void setFlipHorizontally(boolean z) {
        this.mRenderer.setFlipHorizontally(z);
    }

    public void setFlipVertically(boolean z) {
        this.mRenderer.setFlipVertically(z);
    }

    public boolean getFlipHorizontally() {
        return this.mRenderer.isFlippedHorizontally();
    }

    public boolean getFlipVertically() {
        return this.mRenderer.isFlippedVertically();
    }

    public void requestRender() {
        GLSurfaceView gLSurfaceView = this.mGlSurfaceView;
        if (gLSurfaceView != null) {
            gLSurfaceView.requestRender();
        }
    }

    public void setUpCamera(Camera camera) {
        setUpCamera(camera, 0, false, false);
    }

    public void setUpCamera(Camera camera, int i, boolean z, boolean z2) {
        Rotation rotation = Rotation.NORMAL;
        if (i == 90) {
            rotation = Rotation.ROTATION_90;
        } else if (i == 180) {
            rotation = Rotation.ROTATION_180;
        } else if (i == 270) {
            rotation = Rotation.ROTATION_270;
        }
        this.mRenderer.setRotationCamera(rotation, z, z2);
        this.mGlSurfaceView.setRenderMode(1);
        if (Build.VERSION.SDK_INT > 10) {
            setUpCameraGingerbread(camera);
            return;
        }
        camera.setPreviewCallback(this.mRenderer);
        camera.startPreview();
    }

    private void setUpCameraGingerbread(Camera camera) {
        this.mRenderer.setUpSurfaceTexture(camera);
    }

    public void setFilter(GPUImageFilter gPUImageFilter) {
        this.mFilter = gPUImageFilter;
        this.mRenderer.setFilter(gPUImageFilter);
        requestRender();
    }

    public void setFilterWithoutRender(GPUImageFilter gPUImageFilter) {
        this.mFilter = gPUImageFilter;
        this.mRenderer.setFilter(gPUImageFilter);
    }

    public void setImage(Bitmap bitmap) {
        setImage(bitmap, false);
        this.mCurrentBitmap = bitmap;
    }

    public Bitmap getImage() {
        return this.mCurrentBitmap;
    }

    public void setImageWithOutRender(Bitmap bitmap) {
        this.mRenderer.setImageBitmap(bitmap, false);
        this.mCurrentBitmap = bitmap;
    }

    private void setImage(Bitmap bitmap, boolean z) {
        this.mRenderer.setImageBitmap(bitmap, z);
        requestRender();
    }

    public void setScaleType(ScaleType scaleType) {
        this.mScaleType = scaleType;
        this.mRenderer.setScaleType(scaleType);
        this.mRenderer.deleteImage();
        this.mCurrentBitmap = null;
        requestRender();
    }

    public void deleteImage() {
        this.mRenderer.deleteImage();
        this.mCurrentBitmap = null;
        requestRender();
    }

    public void setImage(Uri uri) {
        new LoadImageUriTask(this, uri).execute(new Void[0]);
    }

    public void setImage(File file) {
        new LoadImageFileTask(this, file).execute(new Void[0]);
    }

    public int getmImageWidth() {
        return this.mRenderer.getmImageWidth();
    }

    public int getmImageHeight() {
        return this.mRenderer.getmImageHeight();
    }

    private String getPath(Uri uri) {
        Cursor query = this.mContext.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }

    public Bitmap getBitmapWithFilterApplied() {
        return getBitmapWithFilterApplied(this.mCurrentBitmap);
    }

    public Bitmap getBitmapWithFilterApplied(Bitmap bitmap) {
        PixelBuffer pixelBuffer;
        if (this.mGlSurfaceView != null) {
            this.mRenderer.deleteImage();
            final Semaphore semaphore = new Semaphore(0);
            this.mRenderer.runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.core.GPUImage.2
                @Override // java.lang.Runnable
                public void run() {
                    GPUImage.this.mFilter.destroy();
                    semaphore.release();
                }
            });
            requestRender();
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GPUImageRenderer gPUImageRenderer = new GPUImageRenderer(this.mFilter);
        if (this.mRenderer.getRotation() == Rotation.ROTATION_90) {
            gPUImageRenderer.setRotation(Rotation.ROTATION_90, false, true);
        } else if (this.mRenderer.getRotation() == Rotation.ROTATION_180) {
            gPUImageRenderer.setRotation(Rotation.ROTATION_180, false, true);
        } else if (this.mRenderer.getRotation() == Rotation.ROTATION_270) {
            gPUImageRenderer.setRotation(Rotation.ROTATION_270, false, true);
        } else if (this.mRenderer.getRotation() == Rotation.NORMAL) {
            gPUImageRenderer.setRotation(this.mRenderer.getRotation(), false, true);
        }
        gPUImageRenderer.setScaleType(this.mScaleType);
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        if (this.mRenderer.getRotation() == Rotation.ROTATION_90 || this.mRenderer.getRotation() == Rotation.ROTATION_270) {
            pixelBuffer = new PixelBuffer(bitmap.getHeight(), bitmap.getWidth());
        } else {
            pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
        }
        pixelBuffer.setRenderer(gPUImageRenderer);
        gPUImageRenderer.setImageBitmap(bitmap, false);
        Bitmap bitmap2 = pixelBuffer.getBitmap();
        this.mFilter.destroy();
        gPUImageRenderer.deleteImage();
        pixelBuffer.destroy();
        this.mRenderer.setFilter(this.mFilter);
        Bitmap bitmap3 = this.mCurrentBitmap;
        if (bitmap3 != null) {
            this.mRenderer.setImageBitmap(bitmap3, false);
        }
        requestRender();
        return bitmap2;
    }

    public static void getBitmapForMultipleFilters(Bitmap bitmap, List<GPUImageFilter> list, ResponseListener<Bitmap> responseListener) {
        if (list.isEmpty()) {
            return;
        }
        GPUImageRenderer gPUImageRenderer = new GPUImageRenderer(list.get(0));
        gPUImageRenderer.setImageBitmap(bitmap, false);
        PixelBuffer pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
        pixelBuffer.setRenderer(gPUImageRenderer);
        for (GPUImageFilter gPUImageFilter : list) {
            gPUImageRenderer.setFilter(gPUImageFilter);
            responseListener.response(pixelBuffer.getBitmap());
            gPUImageFilter.destroy();
        }
        gPUImageRenderer.deleteImage();
        pixelBuffer.destroy();
    }

    public static void getBitmapForSingleFilter(Bitmap bitmap, GPUImageFilter gPUImageFilter, ResponseListener<Bitmap> responseListener) {
        if (gPUImageFilter == null || bitmap == null || bitmap.isRecycled()) {
            return;
        }
        GPUImageRenderer gPUImageRenderer = new GPUImageRenderer(gPUImageFilter);
        gPUImageRenderer.setImageBitmap(bitmap, false);
        PixelBuffer pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
        pixelBuffer.setRenderer(gPUImageRenderer);
        responseListener.response(pixelBuffer.getBitmap());
        gPUImageFilter.destroy();
        gPUImageRenderer.deleteImage();
        pixelBuffer.destroy();
    }

    public void saveToPictures(String str, String str2, OnPictureSavedListener onPictureSavedListener) {
        saveToPictures(this.mCurrentBitmap, str, str2, onPictureSavedListener);
    }

    public void saveToPictures(Bitmap bitmap, String str, String str2, OnPictureSavedListener onPictureSavedListener) {
        new SaveTask(bitmap, str, str2, onPictureSavedListener).execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getOutputWidth() {
        GPUImageRenderer gPUImageRenderer = this.mRenderer;
        if (gPUImageRenderer != null && gPUImageRenderer.getFrameWidth() != 0) {
            return this.mRenderer.getFrameWidth();
        }
        Bitmap bitmap = this.mCurrentBitmap;
        if (bitmap != null) {
            return bitmap.getWidth();
        }
        return ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getOutputHeight() {
        GPUImageRenderer gPUImageRenderer = this.mRenderer;
        if (gPUImageRenderer != null && gPUImageRenderer.getFrameHeight() != 0) {
            return this.mRenderer.getFrameHeight();
        }
        Bitmap bitmap = this.mCurrentBitmap;
        if (bitmap != null) {
            return bitmap.getHeight();
        }
        return ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class SaveTask extends AsyncTask<Void, Void, Void> {
        private final Bitmap mBitmap;
        private final String mFileName;
        private final String mFolderName;
        private final Handler mHandler = new Handler();
        private final OnPictureSavedListener mListener;

        public SaveTask(Bitmap bitmap, String str, String str2, OnPictureSavedListener onPictureSavedListener) {
            this.mBitmap = bitmap;
            this.mFolderName = str;
            this.mFileName = str2;
            this.mListener = onPictureSavedListener;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            saveImage(this.mFolderName, this.mFileName, GPUImage.this.getBitmapWithFilterApplied(this.mBitmap));
            return null;
        }

        private void saveImage(String str, String str2, Bitmap bitmap) {
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(externalStoragePublicDirectory, str + "/" + str2);
            try {
                file.getParentFile().mkdirs();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
                MediaScannerConnection.scanFile(GPUImage.this.mContext, new String[]{file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.picspool.lib.filter.gpu.core.GPUImage.SaveTask.1
                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                    public void onScanCompleted(String str3, final Uri uri) {
                        if (SaveTask.this.mListener != null) {
                            SaveTask.this.mHandler.post(new Runnable() { // from class: com.picspool.lib.filter.gpu.core.GPUImage.SaveTask.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    SaveTask.this.mListener.onPictureSaved(uri);
                                }
                            });
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /* loaded from: classes3.dex */
    private class LoadImageUriTask extends LoadImageTask {
        private final Uri mUri;

        public LoadImageUriTask(GPUImage gPUImage, Uri uri) {
            super(gPUImage);
            this.mUri = uri;
        }

        @Override // com.picspool.lib.filter.gpu.core.GPUImage.LoadImageTask
        protected Bitmap decode(BitmapFactory.Options options) {
            InputStream openStream;
            try {
                if (!this.mUri.getScheme().startsWith("http") && !this.mUri.getScheme().startsWith("https")) {
                    openStream = GPUImage.this.mContext.getContentResolver().openInputStream(this.mUri);
                    return BitmapFactory.decodeStream(openStream, null, options);
                }
                openStream = new URL(this.mUri.toString()).openStream();
                return BitmapFactory.decodeStream(openStream, null, options);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override // com.picspool.lib.filter.gpu.core.GPUImage.LoadImageTask
        protected int getImageOrientation() throws IOException {
            Cursor query = GPUImage.this.mContext.getContentResolver().query(this.mUri, new String[]{"orientation"}, null, null, null);
            if (query == null || query.getCount() != 1) {
                return 0;
            }
            query.moveToFirst();
            int i = query.getInt(0);
            query.close();
            return i;
        }
    }

    /* loaded from: classes3.dex */
    private class LoadImageFileTask extends LoadImageTask {
        private final File mImageFile;

        public LoadImageFileTask(GPUImage gPUImage, File file) {
            super(gPUImage);
            this.mImageFile = file;
        }

        @Override // com.picspool.lib.filter.gpu.core.GPUImage.LoadImageTask
        protected Bitmap decode(BitmapFactory.Options options) {
            return BitmapFactory.decodeFile(this.mImageFile.getAbsolutePath(), options);
        }

        @Override // com.picspool.lib.filter.gpu.core.GPUImage.LoadImageTask
        protected int getImageOrientation() throws IOException {
            int attributeInt = new ExifInterface(this.mImageFile.getAbsolutePath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt != 3) {
                if (attributeInt != 6) {
                    return attributeInt != 8 ? 0 : 270;
                }
                return 90;
            }
            return Opcodes.GETFIELD;
        }
    }

    /* loaded from: classes3.dex */
    private abstract class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {
        private final GPUImage mGPUImage;
        private int mOutputHeight;
        private int mOutputWidth;

        protected abstract Bitmap decode(BitmapFactory.Options options);

        protected abstract int getImageOrientation() throws IOException;

        public LoadImageTask(GPUImage gPUImage) {
            this.mGPUImage = gPUImage;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            if (GPUImage.this.mRenderer != null && GPUImage.this.mRenderer.getFrameWidth() == 0) {
                try {
                    synchronized (GPUImage.this.mRenderer.mSurfaceChangedWaiter) {
                        GPUImage.this.mRenderer.mSurfaceChangedWaiter.wait(3000L);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.mOutputWidth = GPUImage.this.getOutputWidth();
            this.mOutputHeight = GPUImage.this.getOutputHeight();
            return loadResizedImage();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            this.mGPUImage.deleteImage();
            this.mGPUImage.setImage(bitmap);
        }

        private Bitmap loadResizedImage() {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            decode(options);
            int i = 1;
            while (true) {
                if (!checkSize(options.outWidth / i > this.mOutputWidth, options.outHeight / i > this.mOutputHeight)) {
                    break;
                }
                i++;
            }
            int i2 = i - 1;
            if (i2 < 1) {
                i2 = 1;
            }
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = i2;
            options2.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options2.inPurgeable = true;
            options2.inTempStorage = new byte[32768];
            Bitmap decode = decode(options2);
            if (decode == null) {
                return null;
            }
            return decode;
        }

        private Bitmap scaleBitmap(Bitmap bitmap) {
            int[] scaleSize = getScaleSize(bitmap.getWidth(), bitmap.getHeight());
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, scaleSize[0], scaleSize[1], true);
            if (bitmap != createScaledBitmap) {
                bitmap.recycle();
            }
            if (GPUImage.this.mScaleType == ScaleType.CENTER_CROP) {
                int i = scaleSize[0] - this.mOutputWidth;
                int i2 = scaleSize[1] - this.mOutputHeight;
                Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap, i / 2, i2 / 2, scaleSize[0] - i, scaleSize[1] - i2);
                if (createScaledBitmap != createBitmap) {
                    createScaledBitmap.recycle();
                }
                return createBitmap;
            }
            return createScaledBitmap;
        }

        private int[] getScaleSize(int i, int i2) {
            float f;
            float f2;
            float f3 = i;
            float f4 = f3 / this.mOutputWidth;
            float f5 = i2;
            float f6 = f5 / this.mOutputHeight;
            if (GPUImage.this.mScaleType != ScaleType.CENTER_CROP ? f4 < f6 : f4 > f6) {
                f2 = this.mOutputHeight;
                f = (f2 / f5) * f3;
            } else {
                float f7 = this.mOutputWidth;
                float f8 = (f7 / f3) * f5;
                f = f7;
                f2 = f8;
            }
            return new int[]{Math.round(f), Math.round(f2)};
        }

        private boolean checkSize(boolean z, boolean z2) {
            return GPUImage.this.mScaleType == ScaleType.CENTER_CROP ? z && z2 : z || z2;
        }

        private Bitmap rotateImage(Bitmap bitmap) {
            Bitmap bitmap2;
            IOException e;
            int imageOrientation = 0;
            if (bitmap == null) {
                return null;
            }
            try {
                imageOrientation = getImageOrientation();
            } catch (IOException e2) {
                bitmap2 = bitmap;
                e = e2;
            }
            if (imageOrientation != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(imageOrientation);
                bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return bitmap2;
            }
            return bitmap;
        }
    }

    public void destroy() {
        this.mFilter.destroy();
        this.mRenderer.deleteImage();
    }

    public GPUImageRenderer getRenderer() {
        return this.mRenderer;
    }

    public void setCleanBeforeDraw(boolean z) {
        GPUImageRenderer gPUImageRenderer = this.mRenderer;
        if (gPUImageRenderer != null) {
            gPUImageRenderer.setCleanBeforeDraw(z);
        }
    }
}
