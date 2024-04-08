package com.picspool.lib.collagelib.resource;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import com.bumptech.glide.load.Key;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.picspool.lib.collagelib.resource.collage.LibDMCollagePoint;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes3.dex */
public class DMPuzzleParser {
    private static Context mContext;

    public static LibDMTemplateRes parse(InputStream inputStream, LibDMTemplateRes libDMTemplateRes) throws Exception {
        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
        try {
            newPullParser.setInput(inputStream, Key.STRING_CHARSET_NAME);
            LibDMCollageInfo libDMCollageInfo = null;
            ArrayList arrayList = null;
            char c = 0;
            boolean z = false;
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType == 0) {
                    if (libDMTemplateRes == null) {
                        libDMTemplateRes = new LibDMTemplateRes();
                    }
                    arrayList = new ArrayList();
                } else if (eventType == 2) {
                    String name = newPullParser.getName();
                    if (name.equalsIgnoreCase("puzzle")) {
                        c = 1;
                    } else if (name.equalsIgnoreCase("photoPuzzlePieces")) {
                        c = 2;
                    } else {
                        if (c == 1) {
                            if (name.equalsIgnoreCase("resId")) {
                                newPullParser.next();
                                String text = newPullParser.getText();
                                if (text != null) {
                                    libDMTemplateRes.setResId(text);
                                }
                            } else if (name.equalsIgnoreCase("version")) {
                                newPullParser.next();
                                String text2 = newPullParser.getText();
                                if (text2 != null) {
                                    libDMTemplateRes.setVersion(Integer.parseInt(text2));
                                }
                            } else if (name.equalsIgnoreCase("name")) {
                                newPullParser.next();
                                String text3 = newPullParser.getText();
                                if (text3 != null && (libDMTemplateRes.getName() == null || libDMTemplateRes.getName().length() < 1)) {
                                    libDMTemplateRes.setName(text3);
                                }
                            } else if (name.equalsIgnoreCase("iconPath")) {
                                newPullParser.next();
                                String text4 = newPullParser.getText();
                                if (text4 != null && (libDMTemplateRes.getIconPath() == null || libDMTemplateRes.getIconPath().length() < 1)) {
                                    libDMTemplateRes.setIconPath(text4);
                                }
                            } else if (name.equalsIgnoreCase("width")) {
                                newPullParser.next();
                                String text5 = newPullParser.getText();
                                if (text5 != null) {
                                    libDMTemplateRes.setWidth(Integer.parseInt(text5));
                                }
                            } else if (name.equalsIgnoreCase("height")) {
                                newPullParser.next();
                                String text6 = newPullParser.getText();
                                if (text6 != null) {
                                    libDMTemplateRes.setHeight(Integer.parseInt(text6));
                                }
                            } else if (name.equalsIgnoreCase("photoAmount")) {
                                newPullParser.next();
                                String text7 = newPullParser.getText();
                                if (text7 != null) {
                                    libDMTemplateRes.setPhotoAmount(Integer.parseInt(text7));
                                }
                            }
                        }
                        if (c == 2) {
                            if (name.equalsIgnoreCase("frameRectArray")) {
                                z = true;
                            } else if (!z) {
                                if (name.equalsIgnoreCase("photoPuzzle")) {
                                    LibDMCollageInfo libDMCollageInfo2 = new LibDMCollageInfo();
                                    libDMCollageInfo2.setOutFrameWidth(libDMTemplateRes.getOutFrameWidth());
                                    libDMCollageInfo2.setInnerFrameWidth(libDMTemplateRes.getInnerFrameWidth());
                                    libDMCollageInfo2.setRadius(libDMTemplateRes.getRoundRadius());
                                    libDMCollageInfo = libDMCollageInfo2;
                                } else if (name.equalsIgnoreCase("maskPath")) {
                                    newPullParser.next();
                                    String text8 = newPullParser.getText();
                                    if (text8 != null && libDMCollageInfo != null) {
                                        if (libDMTemplateRes.getRootPath() != null && libDMTemplateRes.getRootPath().length() > 0) {
                                            libDMCollageInfo.setMaskUri(libDMTemplateRes.getRootPath() + text8);
                                        } else {
                                            libDMCollageInfo.setMaskUri(text8);
                                        }
                                        if (libDMCollageInfo.getMaskUri() != null && libDMCollageInfo.getMaskUri().length() > 1) {
                                            libDMCollageInfo.setIsShowFrame(false);
                                        }
                                    }
                                } else if (name.equalsIgnoreCase("isShowFrame")) {
                                    newPullParser.next();
                                    String text9 = newPullParser.getText();
                                    if (text9 != null && libDMCollageInfo != null) {
                                        if (text9.equals("true")) {
                                            libDMCollageInfo.setIsShowFrame(true);
                                        }
                                        if (libDMCollageInfo.getMaskUri() != null && libDMCollageInfo.getMaskUri().length() > 1) {
                                            libDMCollageInfo.setIsShowFrame(false);
                                        }
                                    }
                                } else if (name.equalsIgnoreCase("isCanCorner")) {
                                    newPullParser.next();
                                    String text10 = newPullParser.getText();
                                    if (text10 != null && libDMCollageInfo != null && text10.equals("false")) {
                                        libDMCollageInfo.setIsCanCorner(false);
                                    }
                                }
                            } else {
                                if (name.equalsIgnoreCase("points")) {
                                    newPullParser.next();
                                    String text11 = newPullParser.getText();
                                    if (text11 != null && libDMCollageInfo != null) {
                                        libDMCollageInfo.setOldpoints(getFrameRectFromString(text11));
                                        arrayList.add(libDMCollageInfo);
                                    }
                                }
                                if (name.equalsIgnoreCase("isOutRectLinepoints")) {
                                    newPullParser.next();
                                    String text12 = newPullParser.getText();
                                    if (text12 != null && libDMCollageInfo != null) {
                                        int[] pointOutterStateFromString = getPointOutterStateFromString(text12, libDMCollageInfo.getOldpoints().size());
                                        for (int i = 0; i < libDMCollageInfo.getOldpoints().size(); i++) {
                                            libDMCollageInfo.getOldpoints().get(i).setIsOutRectLinePoint(pointOutterStateFromString[i]);
                                        }
                                    }
                                    z = false;
                                }
                            }
                        }
                    }
                } else if (eventType == 3) {
                    String name2 = newPullParser.getName();
                    if (libDMCollageInfo != null) {
                        name2.equalsIgnoreCase("photoPuzzle");
                    }
                    if (libDMCollageInfo != null && name2.equalsIgnoreCase("photoPuzzlePieces")) {
                        libDMTemplateRes.setCollageInfo(arrayList);
                        c = 0;
                    }
                    if (libDMCollageInfo != null) {
                        if (!name2.equalsIgnoreCase("frameRectArray")) {
                        }
                        z = false;
                    }
                }
            }
            inputStream.close();
            return libDMTemplateRes;
        } catch (Exception e) {
            inputStream.close();
            e.printStackTrace();
            libDMTemplateRes.setParseFailed(true);
            return libDMTemplateRes;
        }
    }

    public static PointF getPointFFromString(String str) {
        if (str == null || str == "") {
            return null;
        }
        try {
            String[] split = str.replace("{", "").replace("}", "").split(",");
            if (split.length < 2) {
                return null;
            }
            PointF pointF = new PointF();
            for (int i = 0; i < split.length; i++) {
                float parseFloat = Float.parseFloat(split[i]);
                if (i == 0) {
                    pointF.x = parseFloat;
                } else if (i == 1) {
                    pointF.y = parseFloat;
                }
            }
            return pointF;
        } catch (Exception unused) {
            return null;
        }
    }

    public static List<LibDMCollagePoint> getFrameRectFromString(String str) {
        if (str == null || str == "") {
            return null;
        }
        try {
            String[] split = str.split("\\}");
            ArrayList arrayList = new ArrayList();
            for (String str2 : split) {
                String[] split2 = str2.replace("{", "").replace("}", "").split(",");
                if (split2.length >= 2) {
                    LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
                    int i = 0;
                    int i2 = 0;
                    int i3 = 0;
                    int i4 = 0;
                    int i5 = 0;
                    for (int i6 = 0; i6 < split2.length; i6++) {
                        if (!split2[i6].replace(" ", "").equals("")) {
                            int parseInt = Integer.parseInt(split2[i6]);
                            if (i5 == 0) {
                                i = parseInt;
                            } else if (i5 == 1) {
                                i2 = parseInt;
                            } else if (i5 == 2) {
                                i3 = parseInt;
                            } else if (i5 == 3) {
                                i4 = parseInt;
                            }
                            i5++;
                        }
                    }
                    libDMCollagePoint.setOriPoint(new Point(i, i2));
                    libDMCollagePoint.sethLineMode(i3);
                    libDMCollagePoint.setvLineMode(i4);
                    arrayList.add(libDMCollagePoint);
                }
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[] getPointOutterStateFromString(String str, int i) {
        if (str == null || str == "") {
            return null;
        }
        try {
            int[] iArr = new int[i];
            String[] split = str.replace("{", "").replace("}", "").split(",");
            if (split.length < 2) {
                return null;
            }
            for (int i2 = 0; i2 < split.length; i2++) {
                int parseInt = Integer.parseInt(split[i2].replace(" ", ""));
                if (i2 < i) {
                    iArr[i2] = parseInt;
                }
            }
            return iArr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setContex(Context context) {
        mContext = context;
    }

    protected LibDMCollagePoint initCollagePoint(int i, int i2, int i3, int i4) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        libDMCollagePoint.setOriPoint(new Point(i, i2));
        libDMCollagePoint.sethLineMode(i3);
        libDMCollagePoint.setvLineMode(i4);
        return libDMCollagePoint;
    }
}
