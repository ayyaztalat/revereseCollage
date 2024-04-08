package com.sky.testproject

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSStickerBarConfig
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSClearStickerSDMaterialFile
import com.google.firebase.FirebaseApp
import com.photo.editor.square.splash.activitys.CSStickerOnlineStoreAcitvity
import com.photo.editor.square.splash.rate.BpFirebaseConfig
import com.picspool.instatextview.resource.DMWBFontRes
import com.picspool.instatextview.resource.manager.DMFontManager
import com.picspool.instatextview.textview.DMInstaTextView
import com.picspool.instatextview.textview.DMInstaTextView3
import com.picspool.lib.sysutillib.DMPreferencesUtil
import java.util.LinkedList


class MyApp: Application() {
    var mContext: Context? = null
    private var swapBitmap: Bitmap? = null

    // android.app.Application
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        FirebaseApp.initializeApp(this)
        BpFirebaseConfig.getInstance(this).init()
//        AdUtils.getInstance().init(this)
        try {
            GPUImageNativeLibrary.initGpuNativeLibrary(this)
        } catch (unused: Throwable) {
            unused.printStackTrace()
        }
        val linkedList: LinkedList<Typeface> = LinkedList<Typeface>()
        val dMFontManager = DMFontManager(applicationContext)
        val count: Int = dMFontManager.count
        for (i in 0 until count) {
            try {
                val res: DMWBFontRes = dMFontManager.getRes(i)
                if (res.getFontTypeface(applicationContext) != null) {
                    linkedList.add(res.getFontTypeface(applicationContext))
                }
            } catch (unused2: Throwable) {
            }
        }
        DMInstaTextView.setTfList(linkedList)
        DMInstaTextView3.setTfList(linkedList)
        CSStickerBarConfig.setStickerOnlineStoreAcitvity(CSStickerOnlineStoreAcitvity::class.java)
        try {
            if (DMPreferencesUtil.get(
                    applicationContext,
                    "stickerbar_clean",
                    "clear_All_StickerFile"
                ) == null
            ) {
                Thread {
                    CSClearStickerSDMaterialFile.clearFile(applicationContext)
                    DMPreferencesUtil.save(
                        getApplicationContext(),
                        "stickerbar_clean",
                        "clear_All_StickerFile",
                        "removed"
                    )
                }.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        ForegroundCallbacks.init(this)
//        ForegroundCallbacks.get(this as Application).addListener(object : Listener() {
//            // from class: com.photo.editor.square.splash.app.CSMyApp.2
//            // com.mhh.libraryads.libads.ForegroundCallbacks.Listener
//            fun onBecameBackground() {}
//
//            // com.mhh.libraryads.libads.ForegroundCallbacks.Listener
//            fun onBecameForeground(activity: Activity?) {
//                adsBaseAdActivity.isBecameForeground = true
//            }
//        })
    }

    fun getSwapBitmap(): Bitmap? {
        return swapBitmap
    }

    fun setSwapBitmap(bitmap: Bitmap?) {
        var bitmap2: Bitmap? = null
        if (bitmap == null && swapBitmap.also { bitmap2 = it!! } != null) {
            if (!bitmap2!!.isRecycled) {
                swapBitmap!!.recycle()
            }
            swapBitmap = null
        }
        swapBitmap = bitmap
    }
}