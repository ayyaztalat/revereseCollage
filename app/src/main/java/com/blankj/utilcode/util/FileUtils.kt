package com.blankj.utilcode.util

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.StatFs
import android.text.TextUtils
import com.blankj.utilcode.constant.RegexConstants
import com.google.common.net.HttpHeaders
import java.io.BufferedInputStream
import java.io.File
import java.io.FileFilter
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URL
import java.util.Collections
import javax.net.ssl.HttpsURLConnection
import kotlin.Byte.Companion.MAX_VALUE
import kotlin.Byte.Companion.MIN_VALUE

/* loaded from: classes.dex */
class FileUtils private constructor() {
    /* loaded from: classes.dex */
    interface OnReplaceListener {
        fun onReplace(file: File?, file2: File?): Boolean
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {
        private val LINE_SEP = System.getProperty("line.separator")
        fun getFileByPath(str: String?): File? {
            return if (UtilsBridge.isSpace(str)) {
                null
            } else File(str)
        }

        fun isFileExists(file: File?): Boolean {
            if (file == null) {
                return false
            }
            return if (file.exists()) {
                true
            } else isFileExists(file.absolutePath)
        }

        fun isFileExists(str: String?): Boolean {
            val fileByPath = getFileByPath(str) ?: return false
            return if (fileByPath.exists()) {
                true
            } else isFileExistsApi29(
                str
            )
        }

        private fun isFileExistsApi29(str: String?): Boolean {
            if (Build.VERSION.SDK_INT >= 29) {
                try {
                    val openAssetFileDescriptor =
                        C0879Utils.getApp().contentResolver.openAssetFileDescriptor(
                            Uri.parse(str), "r"
                        )
                            ?: return false
                    try {
                        openAssetFileDescriptor.close()
                        return true
                    } catch (unused: IOException) {
                        return true
                    }
                } catch (unused2: FileNotFoundException) {
                }
            }
            return false
        }

        fun rename(str: String?, str2: String): Boolean {
            return rename(getFileByPath(str), str2)
        }

        fun rename(file: File?, str: String): Boolean {
            if ((file == null) || !file.exists() || UtilsBridge.isSpace(str)) {
                return false
            }
            if ((str == file.name)) {
                return true
            }
            val file2 = File(file.parent + File.separator + str)
            return !file2.exists() && file.renameTo(file2)
        }

        fun isDir(str: String?): Boolean {
            return isDir(getFileByPath(str))
        }

        fun isDir(file: File?): Boolean {
            return (file != null) && file.exists() && file.isDirectory
        }

        fun isFile(str: String?): Boolean {
            return isFile(getFileByPath(str))
        }

        fun isFile(file: File?): Boolean {
            return (file != null) && file.exists() && file.isFile
        }

        fun createOrExistsDir(str: String?): Boolean {
            return createOrExistsDir(getFileByPath(str))
        }

        fun createOrExistsDir(file: File?): Boolean {
            return file != null && (if (!file.exists()) !file.mkdirs() else !file.isDirectory)
        }

        fun createOrExistsFile(str: String?): Boolean {
            return createOrExistsFile(getFileByPath(str))
        }

        fun createOrExistsFile(file: File?): Boolean {
            if (file == null) {
                return false
            }
            if (file.exists()) {
                return file.isFile
            }
            if (createOrExistsDir(file.parentFile)) {
                try {
                    return file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    return false
                }
            }
            return false
        }

        fun createFileByDeleteOldFile(str: String?): Boolean {
            return createFileByDeleteOldFile(getFileByPath(str))
        }

        fun createFileByDeleteOldFile(file: File?): Boolean {
            if (file == null) {
                return false
            }
            if ((!file.exists() || file.delete()) && createOrExistsDir(file.parentFile)) {
                try {
                    return file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    return false
                }
            }
            return false
        }

        fun copy(str: String?, str2: String?): Boolean {
            return copy(getFileByPath(str), getFileByPath(str2), null as OnReplaceListener?)
        }

        fun copy(str: String?, str2: String?, onReplaceListener: OnReplaceListener?): Boolean {
            return copy(getFileByPath(str), getFileByPath(str2), onReplaceListener)
        }

        @JvmOverloads
        fun copy(
            file: File?,
            file2: File?,
            onReplaceListener: OnReplaceListener? = null as OnReplaceListener?
        ): Boolean {
            if (file == null) {
                return false
            }
            return if (file.isDirectory) {
                copyDir(file, file2, onReplaceListener)
            } else copyFile(
                file,
                file2,
                onReplaceListener
            )
        }

        private fun copyDir(
            file: File,
            file2: File?,
            onReplaceListener: OnReplaceListener?
        ): Boolean {
            return copyOrMoveDir(file, file2, onReplaceListener, false)
        }

        private fun copyFile(
            file: File,
            file2: File?,
            onReplaceListener: OnReplaceListener?
        ): Boolean {
            return copyOrMoveFile(file, file2, onReplaceListener, false)
        }

        fun move(str: String?, str2: String?): Boolean {
            return move(getFileByPath(str), getFileByPath(str2), null as OnReplaceListener?)
        }

        fun move(str: String?, str2: String?, onReplaceListener: OnReplaceListener?): Boolean {
            return move(getFileByPath(str), getFileByPath(str2), onReplaceListener)
        }

        @JvmOverloads
        fun move(
            file: File?,
            file2: File?,
            onReplaceListener: OnReplaceListener? = null as OnReplaceListener?
        ): Boolean {
            if (file == null) {
                return false
            }
            return if (file.isDirectory) {
                moveDir(file, file2, onReplaceListener)
            } else moveFile(
                file,
                file2,
                onReplaceListener
            )
        }

        fun moveDir(file: File?, file2: File?, onReplaceListener: OnReplaceListener?): Boolean {
            return copyOrMoveDir(file, file2, onReplaceListener, true)
        }

        fun moveFile(file: File?, file2: File?, onReplaceListener: OnReplaceListener?): Boolean {
            return copyOrMoveFile(file, file2, onReplaceListener, true)
        }

        private fun copyOrMoveDir(
            file: File?,
            file2: File?,
            onReplaceListener: OnReplaceListener?,
            z: Boolean
        ): Boolean {
            if (file == null || file2 == null) {
                return false
            }
            val str = file2.path + File.separator
            if (!str.contains(file.path + File.separator) && file.exists() && file.isDirectory && createOrExistsDir(
                    file2
                )
            ) {
                val listFiles = file.listFiles()
                if (listFiles != null && listFiles.size > 0) {
                    for (file3: File in listFiles) {
                        val file4 = File(str + file3.name)
                        if (file3.isFile) {
                            if (!copyOrMoveFile(file3, file4, onReplaceListener, z)) {
                                return false
                            }
                        } else if (file3.isDirectory && !copyOrMoveDir(
                                file3,
                                file4,
                                onReplaceListener,
                                z
                            )
                        ) {
                            return false
                        }
                    }
                }
                return !z || deleteDir(file)
            }
            return false
        }

        private fun copyOrMoveFile(
            file: File?,
            file2: File?,
            onReplaceListener: OnReplaceListener?,
            z: Boolean
        ): Boolean {
            if ((file != null) && (file2 != null) && file != file2 && file.exists() && file.isFile) {
                if (file2.exists()) {
                    if (onReplaceListener != null && !onReplaceListener.onReplace(file, file2)) {
                        return true
                    }
                    if (!file2.delete()) {
                        return false
                    }
                }
                if (!createOrExistsDir(file2.parentFile)) {
                    return false
                }
                try {
                    if (UtilsBridge.writeFileFromIS(file2.absolutePath, FileInputStream(file))) {
                        if (z) {
                            if (!deleteFile(file)) {
                                return false
                            }
                        }
                        return true
                    }
                    return false
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
            return false
        }

        fun delete(str: String?): Boolean {
            return delete(getFileByPath(str))
        }

        fun delete(file: File?): Boolean {
            if (file == null) {
                return false
            }
            return if (file.isDirectory) {
                deleteDir(file)
            } else deleteFile(
                file
            )
        }

        private fun deleteDir(file: File?): Boolean {
            if (file == null) {
                return false
            }
            if (file.exists()) {
                if (file.isDirectory) {
                    val listFiles = file.listFiles()
                    if (listFiles != null && listFiles.size > 0) {
                        for (file2: File in listFiles) {
                            if (file2.isFile) {
                                if (!file2.delete()) {
                                    return false
                                }
                            } else if (file2.isDirectory && !deleteDir(file2)) {
                                return false
                            }
                        }
                    }
                    return file.delete()
                }
                return false
            }
            return true
        }

        private fun deleteFile(file: File?): Boolean {
            return file != null && (!file.exists() || (file.isFile && file.delete()))
        }

        fun deleteAllInDir(str: String?): Boolean {
            return deleteAllInDir(getFileByPath(str))
        }

        fun deleteAllInDir(file: File?): Boolean {
            return deleteFilesInDirWithFilter(file, FileFilter {
                // from class: com.blankj.utilcode.util.FileUtils.1
                // java.io.FileFilter
                true
            })
        }

        fun deleteFilesInDir(str: String?): Boolean {
            return deleteFilesInDir(getFileByPath(str))
        }

        fun deleteFilesInDir(file: File?): Boolean {
            return deleteFilesInDirWithFilter(file, object : FileFilter {
                // from class: com.blankj.utilcode.util.FileUtils.2
                // java.io.FileFilter
                override fun accept(file2: File): Boolean {
                    return file2.isFile
                }
            })
        }

        fun deleteFilesInDirWithFilter(str: String?, fileFilter: FileFilter?): Boolean {
            return deleteFilesInDirWithFilter(getFileByPath(str), fileFilter)
        }

        fun deleteFilesInDirWithFilter(file: File?, fileFilter: FileFilter?): Boolean {
            if (file == null || fileFilter == null) {
                return false
            }
            if (file.exists()) {
                if (file.isDirectory) {
                    val listFiles = file.listFiles()
                    if (listFiles != null && listFiles.size != 0) {
                        for (file2: File in listFiles) {
                            if (fileFilter.accept(file2)) {
                                if (file2.isFile) {
                                    if (!file2.delete()) {
                                        return false
                                    }
                                } else if (file2.isDirectory && !deleteDir(file2)) {
                                    return false
                                }
                            }
                        }
                    }
                    return true
                }
                return false
            }
            return true
        }

        @JvmOverloads
        fun listFilesInDir(
            str: String?,
            comparator: Comparator<File?>? = null as Comparator<File?>?
        ): List<File?> {
            return listFilesInDir(getFileByPath(str), false, comparator)
        }

        @JvmOverloads
        fun listFilesInDir(
            file: File?,
            comparator: Comparator<File?>? = null as Comparator<File?>?
        ): List<File?> {
            return listFilesInDir(file, false, comparator)
        }

        fun listFilesInDir(str: String?, z: Boolean): List<File?> {
            return listFilesInDir(getFileByPath(str), z)
        }

        fun listFilesInDir(str: String?, z: Boolean, comparator: Comparator<File?>?): List<File?> {
            return listFilesInDir(getFileByPath(str), z, comparator)
        }

        @JvmOverloads
        fun listFilesInDir(
            file: File?,
            z: Boolean,
            comparator: Comparator<File?>? = null as Comparator<File?>?
        ): List<File?> {
            return listFilesInDirWithFilter(file, object : FileFilter {
                // from class: com.blankj.utilcode.util.FileUtils.3
                // java.io.FileFilter
                override fun accept(file2: File): Boolean {
                    return true
                }
            }, z, comparator)
        }

        fun listFilesInDirWithFilter(str: String?, fileFilter: FileFilter): List<File?> {
            return listFilesInDirWithFilter(getFileByPath(str), fileFilter)
        }

        fun listFilesInDirWithFilter(
            str: String?,
            fileFilter: FileFilter,
            comparator: Comparator<File?>?
        ): List<File?> {
            return listFilesInDirWithFilter(getFileByPath(str), fileFilter, comparator)
        }

        fun listFilesInDirWithFilter(
            file: File?,
            fileFilter: FileFilter,
            comparator: Comparator<File?>?
        ): List<File?> {
            return listFilesInDirWithFilter(file, fileFilter, false, comparator)
        }

        fun listFilesInDirWithFilter(
            str: String?,
            fileFilter: FileFilter,
            z: Boolean
        ): List<File?> {
            return listFilesInDirWithFilter(getFileByPath(str), fileFilter, z)
        }

        fun listFilesInDirWithFilter(
            str: String?,
            fileFilter: FileFilter,
            z: Boolean,
            comparator: Comparator<File?>?
        ): List<File?> {
            return listFilesInDirWithFilter(getFileByPath(str), fileFilter, z, comparator)
        }

        @JvmOverloads
        fun listFilesInDirWithFilter(
            file: File?,
            fileFilter: FileFilter,
            z: Boolean = false,
            comparator: Comparator<File?>? = null as Comparator<File?>?
        ): List<File?> {
            val listFilesInDirWithFilterInner = listFilesInDirWithFilterInner(file, fileFilter, z)
            if (comparator != null) {
                Collections.sort(listFilesInDirWithFilterInner, comparator)
            }
            return listFilesInDirWithFilterInner
        }

        private fun listFilesInDirWithFilterInner(
            file: File?,
            fileFilter: FileFilter,
            z: Boolean
        ): ArrayList<File> {
            var listFiles: Array<File?>? = null
            val arrayList: ArrayList<File> = ArrayList()
            if (isDir(file) && ((file!!.listFiles()
                    .also { listFiles = it }) != null) && (listFiles!!.isNotEmpty())
            ) {
                for (file2: File? in listFiles!!) {
                    if (fileFilter.accept(file2)) {
                        arrayList.add(file2!!)
                    }
                    if (z && file2!!.isDirectory) {
                       val list = listFilesInDirWithFilterInner(file2, fileFilter, true)
                        for (i in 0 until list.size){
                            arrayList.add(list[i])
                        }
                    }
                }
            }
            return arrayList
        }

        fun getFileLastModified(str: String?): Long {
            return getFileLastModified(getFileByPath(str))
        }

        fun getFileLastModified(file: File?): Long {
            return if (file == null) {
                -1L
            } else file.lastModified()
        }

        fun getFileCharsetSimple(str: String?): String {
            return getFileCharsetSimple(getFileByPath(str))
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x0044  */ /* JADX WARN: Removed duplicated region for block: B:35:0x004f A[RETURN] */ /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
        fun getFileCharsetSimple(r4: File?): String {
            /*
            if (r4 != 0) goto L5
            java.lang.String r4 = ""
            return r4
        L5:
            boolean r0 = isUtf8(r4)
            if (r0 == 0) goto Le
            java.lang.String r4 = "UTF-8"
            return r4
        Le:
            r0 = 0
            r1 = 0
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L36
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L36
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L36
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L36
            int r4 = r2.read()     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L31
            int r4 = r4 << 8
            int r0 = r2.read()     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L31
            int r0 = r0 + r4
            r2.close()     // Catch: java.io.IOException -> L29
            goto L3f
        L29:
            r4 = move-exception
            r4.printStackTrace()
            goto L3f
        L2e:
            r4 = move-exception
            r1 = r2
            goto L52
        L31:
            r4 = move-exception
            r1 = r2
            goto L37
        L34:
            r4 = move-exception
            goto L52
        L36:
            r4 = move-exception
        L37:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L34
            if (r1 == 0) goto L3f
            r1.close()     // Catch: java.io.IOException -> L29
        L3f:
            r4 = 65279(0xfeff, float:9.1475E-41)
            if (r0 == r4) goto L4f
            r4 = 65534(0xfffe, float:9.1833E-41)
            if (r0 == r4) goto L4c
            java.lang.String r4 = "GBK"
            return r4
        L4c:
            java.lang.String r4 = "Unicode"
            return r4
        L4f:
            java.lang.String r4 = "UTF-16BE"
            return r4
        L52:
            if (r1 == 0) goto L5c
            r1.close()     // Catch: java.io.IOException -> L58
            goto L5c
        L58:
            r0 = move-exception
            r0.printStackTrace()
        L5c:
            throw r4
        */
            throw UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileUtils.getFileCharsetSimple(java.io.File):java.lang.String")
        }

        fun isUtf8(str: String?): Boolean {
            return isUtf8(getFileByPath(str))
        }

        fun isUtf8(file: File?): Boolean {
            if (file == null) {
                return false
            }
            var bufferedInputStream: BufferedInputStream? = null
            try {
                try {
                    val bArr = ByteArray(24)
                    val bufferedInputStream2 = BufferedInputStream(FileInputStream(file))
                    try {
                        val read = bufferedInputStream2.read(bArr)
                        if (read == -1) {
                            try {
                                bufferedInputStream2.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            return false
                        }
                        val bArr2 = ByteArray(read)
                        System.arraycopy(bArr, 0, bArr2, 0, read)
                        val z = isUtf8(bArr2) == 100
                        try {
                            bufferedInputStream2.close()
                        } catch (e2: IOException) {
                            e2.printStackTrace()
                        }
                        return z
                    } catch (e3: IOException) {
                        bufferedInputStream = bufferedInputStream2
                        e3.printStackTrace()
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close()
                            } catch (e4: IOException) {
                                e4.printStackTrace()
                            }
                        }
                        return false
                    } catch (th: Throwable) {

                        bufferedInputStream = bufferedInputStream2
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close()
                            } catch (e5: IOException) {
                                e5.printStackTrace()
                            }
                        }
                        throw th
                    }
                } catch (e6: IOException) {
                    e6.printStackTrace()
                }
            } catch (th2: Throwable) {
                th2.printStackTrace()
            }
            return false
        }

        private fun isUtf8(bArr: ByteArray): Int {
            if ((bArr.size > 3) && (bArr[0].toInt() == -17) && (bArr[1].toInt() == -69) && (bArr[2].toInt() == -65)) {
                return 100
            }
            val length = bArr.size
            var i = 0
            var i2 = 0
            var i3 = 0
            loop0@ while (true) {
                var i4 = 0
                while (i < length) {
                    if ((bArr[i].toInt() and (-1)) == -1 || (bArr[i].toInt() and (-2)) == -2) {
                        break@loop0
                    } else if (i4 == 0) {
                        if ((bArr[i].toInt() and MAX_VALUE.toInt()) == bArr[i].toInt() && bArr[i].toInt() != 0) {
                            i2++
                        } else if ((bArr[i].toInt() and (-64)) == -64) {
                            var i5 = i4
                            for (i6 in 0..7) {
                                val b = (128 shr i6).toByte()
                                if ((bArr[i].toInt() and b.toInt()) != b.toInt()) {
                                    break
                                }
                                i5 = i6
                            }
                            i3++
                            i4 = i5
                        }
                        i++
                    } else {
                        if (bArr.size - i <= i4) {
                            i4 = bArr.size - i
                        }
                        var z = false
                        for (i7 in 0 until i4) {
                            val i8 = i + i7
                            if ((bArr[i8].toInt() and MIN_VALUE.toInt()) != -128) {
                                if ((bArr[i8].toInt() and MAX_VALUE.toInt()) == bArr[i8].toInt() && bArr[i].toInt() != 0) {
                                    i2++
                                }
                                z = true
                            }
                        }
                        if (z) {
                            i3--
                            i++
                        } else {
                            i3 += i4
                            i += i4
                        }
                    }
                }
                return if (i2 == length) {
                    100
                } else (((i3 + i2) / length) * 100.0f).toInt()
            }
            return 0
        }

        fun getFileLines(str: String?): Int {
            return getFileLines(getFileByPath(str))
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0023, code lost:
        if (r6 >= r3) goto L19;
     */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0029, code lost:
        if (r1[r6] != 10) goto L17;
     */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x002b, code lost:
        r0 = r0 + 1;
     */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
        r6 = r6 + 1;
     */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0030, code lost:
        r3 = r2.read(r1, 0, 1024);
     */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0034, code lost:
        if (r3 == (-1)) goto L39;
     */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0036, code lost:
        r6 = 0;
     */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0037, code lost:
        if (r6 >= r3) goto L38;
     */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x003d, code lost:
        if (r1[r6] != 13) goto L36;
     */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x003f, code lost:
        r0 = r0 + 1;
     */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0041, code lost:
        r6 = r6 + 1;
     */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0044, code lost:
        r2.close();
        r1 = r1;
     */
        /* JADX WARN: Code restructure failed: missing block: B:6:0x001a, code lost:
        if (com.blankj.utilcode.util.FileUtils.LINE_SEP.endsWith("\n") != false) goto L9;
     */
        /* JADX WARN: Code restructure failed: missing block: B:7:0x001c, code lost:
        r3 = r2.read(r1, 0, 1024);
     */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x0020, code lost:
        if (r3 == (-1)) goto L20;
     */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0022, code lost:
        r6 = 0;
     */
        /* JADX WARN: Multi-variable type inference failed */ /* JADX WARN: Type inference failed for: r1v0 */ /* JADX WARN: Type inference failed for: r1v12 */ /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x0049 -> B:45:0x005e). Please submit an issue!!! */ /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
        fun getFileLines(r9: File?): Int {
            /*
            r0 = 1
            r1 = 0
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55
            r3.<init>(r9)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55
            r9 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r9]     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L50
            java.lang.String r3 = com.blankj.utilcode.util.FileUtils.LINE_SEP     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L50
            java.lang.String r4 = "\n"
            boolean r3 = r3.endsWith(r4)     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L50
            r4 = -1
            r5 = 0
            if (r3 == 0) goto L30
        L1c:
            int r3 = r2.read(r1, r5, r9)     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L50
            if (r3 == r4) goto L44
            r6 = 0
        L23:
            if (r6 >= r3) goto L1c
            r7 = r1[r6]     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L50
            r8 = 10
            if (r7 != r8) goto L2d
            int r0 = r0 + 1
        L2d:
            int r6 = r6 + 1
            goto L23
        L30:
            int r3 = r2.read(r1, r5, r9)     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L50
            if (r3 == r4) goto L44
            r6 = 0
        L37:
            if (r6 >= r3) goto L30
            r7 = r1[r6]     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L50
            r8 = 13
            if (r7 != r8) goto L41
            int r0 = r0 + 1
        L41:
            int r6 = r6 + 1
            goto L37
        L44:
            r2.close()     // Catch: java.io.IOException -> L48
            goto L5e
        L48:
            r9 = move-exception
            r9.printStackTrace()
            goto L5e
        L4d:
            r9 = move-exception
            r1 = r2
            goto L5f
        L50:
            r9 = move-exception
            r1 = r2
            goto L56
        L53:
            r9 = move-exception
            goto L5f
        L55:
            r9 = move-exception
        L56:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L53
            if (r1 == 0) goto L5e
            r1.close()     // Catch: java.io.IOException -> L48
        L5e:
            return r0
        L5f:
            if (r1 == 0) goto L69
            r1.close()     // Catch: java.io.IOException -> L65
            goto L69
        L65:
            r0 = move-exception
            r0.printStackTrace()
        L69:
            throw r9
        */
            throw UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileUtils.getFileLines(java.io.File):int")
        }

        fun getSize(str: String?): String {
            return getSize(getFileByPath(str))
        }

        fun getSize(file: File?): String {
            if (file == null) {
                return ""
            }
            return if (file.isDirectory) {
                getDirSize(file)
            } else getFileSize(
                file
            )
        }

        private fun getDirSize(file: File): String {
            val dirLength = getDirLength(file)
            return if (dirLength == -1L) "" else UtilsBridge.byte2FitMemorySize(dirLength)
        }

        private fun getFileSize(file: File): String {
            val fileLength = getFileLength(file)
            return if (fileLength == -1L) "" else UtilsBridge.byte2FitMemorySize(fileLength)
        }

        fun getLength(str: String?): Long {
            return getLength(getFileByPath(str))
        }

        fun getLength(file: File?): Long {
            if (file == null) {
                return 0L
            }
            return if (file.isDirectory) {
                getDirLength(file)
            } else getFileLength(file)
        }

        private fun getDirLength(file: File): Long {
            var length: Long
            var j: Long = 0
            if (isDir(file)) {
                val listFiles = file.listFiles()
                if (listFiles != null && listFiles.size > 0) {
                    for (file2: File in listFiles) {
                        if (file2.isDirectory) {
                            length = getDirLength(file2)
                        } else {
                            length = file2.length()
                        }
                        j += length
                    }
                }
                return j
            }
            return 0L
        }

        fun getFileLength(str: String): Long {
            if (str.matches(RegexConstants.REGEX_URL.toRegex())) {
                try {
//                    val httpsURLConnection = URL(str).openConnection() as HttpsURLConnection
//                    httpsURLConnection.setRequestProperty(
//                        HttpHeaders.HEAD_KEY_ACCEPT_ENCODING,
//                        "identity"
//                    )
//                    httpsURLConnection.connect()
//                    return if (httpsURLConnection.responseCode == 200) {
//                        httpsURLConnection.contentLength.toLong()
//                    } else -1L
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return getFileLength(getFileByPath(str))
        }

        private fun getFileLength(file: File?): Long {
            return if (isFile(file)) {
                file!!.length()
            } else -1L
        }

        fun getFileMD5ToString(str: String?): String {
            return getFileMD5ToString(if (UtilsBridge.isSpace(str)) null else File(str))
        }

        fun getFileMD5ToString(file: File?): String {
            return UtilsBridge.bytes2HexString(getFileMD5(file))
        }

        fun getFileMD5(str: String?): ByteArray {
            return getFileMD5(getFileByPath(str))
        }

        /* JADX WARN: Not initialized variable reg: 2, insn: 0x0048: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:34:0x0048 */ /* JADX WARN: Removed duplicated region for block: B:44:0x003e A[EXC_TOP_SPLITTER, SYNTHETIC] */ /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
        fun getFileMD5(r3: File?): ByteArray {
            /*
            r0 = 0
            if (r3 != 0) goto L4
            return r0
        L4:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            java.lang.String r3 = "MD5"
            java.security.MessageDigest r3 = java.security.MessageDigest.getInstance(r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            java.security.DigestInputStream r2 = new java.security.DigestInputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r2.<init>(r1, r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r3 = 262144(0x40000, float:3.67342E-40)
            byte[] r3 = new byte[r3]     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
        L18:
            int r1 = r2.read(r3)     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            if (r1 > 0) goto L18
            java.security.MessageDigest r3 = r2.getMessageDigest()     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            byte[] r3 = r3.digest()     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            r2.close()     // Catch: java.io.IOException -> L2a
            goto L2e
        L2a:
            r0 = move-exception
            r0.printStackTrace()
        L2e:
            return r3
        L2f:
            r3 = move-exception
            goto L39
        L31:
            r3 = move-exception
            goto L39
        L33:
            r3 = move-exception
            goto L49
        L35:
            r3 = move-exception
            goto L38
        L37:
            r3 = move-exception
        L38:
            r2 = r0
        L39:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L47
            if (r2 == 0) goto L46
            r2.close()     // Catch: java.io.IOException -> L42
            goto L46
        L42:
            r3 = move-exception
            r3.printStackTrace()
        L46:
            return r0
        L47:
            r3 = move-exception
            r0 = r2
        L49:
            if (r0 == 0) goto L53
            r0.close()     // Catch: java.io.IOException -> L4f
            goto L53
        L4f:
            r0 = move-exception
            r0.printStackTrace()
        L53:
            throw r3
        */
            throw UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileUtils.getFileMD5(java.io.File):byte[]")
        }

        fun getDirName(file: File?): String {
            return if (file == null) "" else getDirName(file.absolutePath)
        }

        fun getDirName(str: String): String {
            var lastIndexOf: Int =0
            return if ((UtilsBridge.isSpace(str) || (str.lastIndexOf(File.separator)
                    .also { lastIndexOf = it }) == -1)
            ) "" else str.substring(0, lastIndexOf + 1)
        }

        fun getFileName(file: File?): String {
            return if (file == null) "" else getFileName(file.absolutePath)
        }

        fun getFileName(str: String): String {
            if (UtilsBridge.isSpace(str)) {
                return ""
            }
            val lastIndexOf = str.lastIndexOf(File.separator)
            return if (lastIndexOf == -1) str else str.substring(lastIndexOf + 1)
        }

        fun getFileNameNoExtension(file: File?): String {
            return if (file == null) "" else getFileNameNoExtension(file.path)
        }

        fun getFileNameNoExtension(str: String): String {
            if (UtilsBridge.isSpace(str)) {
                return ""
            }
            val lastIndexOf = str.lastIndexOf(46.toChar())
            val lastIndexOf2 = str.lastIndexOf(File.separator)
            if (lastIndexOf2 == -1) {
                return if (lastIndexOf == -1) str else str.substring(0, lastIndexOf)
            } else return if (lastIndexOf == -1 || lastIndexOf2 > lastIndexOf) {
                str.substring(lastIndexOf2 + 1)
            } else {
                str.substring(lastIndexOf2 + 1, lastIndexOf)
            }
        }

        fun getFileExtension(file: File?): String {
            return if (file == null) "" else getFileExtension(file.path)
        }

        fun getFileExtension(str: String): String {
            if (UtilsBridge.isSpace(str)) {
                return ""
            }
            val lastIndexOf = str.lastIndexOf(46.toChar())
            return if ((lastIndexOf == -1 || str.lastIndexOf(File.separator) >= lastIndexOf)) "" else str.substring(
                lastIndexOf + 1
            )
        }

        fun notifySystemToScan(str: String?) {
            notifySystemToScan(getFileByPath(str))
        }

        fun notifySystemToScan(file: File?) {
            if (file == null || !file.exists()) {
                return
            }
            val intent = Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE")
            intent.setData(Uri.parse("file://" + file.absolutePath))
            C0879Utils.getApp().sendBroadcast(intent)
        }

        fun getFsTotalSize(str: String?): Long {
            val blockSize: Long
            val blockCount: Long
            if (TextUtils.isEmpty(str)) {
                return 0L
            }
            val statFs = StatFs(str)
            if (Build.VERSION.SDK_INT >= 18) {
                blockSize = statFs.blockSizeLong
                blockCount = statFs.blockCountLong
            } else {
                blockSize = statFs.blockSize.toLong()
                blockCount = statFs.blockCount.toLong()
            }
            return blockSize * blockCount
        }

        fun getFsAvailableSize(str: String?): Long {
            val blockSize: Long
            val availableBlocks: Long
            if (TextUtils.isEmpty(str)) {
                return 0L
            }
            val statFs = StatFs(str)
            if (Build.VERSION.SDK_INT >= 18) {
                blockSize = statFs.blockSizeLong
                availableBlocks = statFs.availableBlocksLong
            } else {
                blockSize = statFs.blockSize.toLong()
                availableBlocks = statFs.availableBlocks.toLong()
            }
            return blockSize * availableBlocks
        }
    }
}
