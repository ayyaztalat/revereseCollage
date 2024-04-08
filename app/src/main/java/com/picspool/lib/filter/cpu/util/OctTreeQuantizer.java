package com.picspool.lib.filter.cpu.util;

import java.io.PrintStream;
import java.util.Vector;

/* loaded from: classes3.dex */
public class OctTreeQuantizer implements Quantizer {
    static final int MAX_LEVEL = 5;
    private Vector[] colorList;
    private int maximumColors;
    private int reduceColors;
    private OctTreeNode root;
    private int nodes = 0;
    private int colors = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class OctTreeNode {
        int children;
        int count;
        int index;
        boolean isLeaf;
        OctTreeNode[] leaf = new OctTreeNode[8];
        int level;
        OctTreeNode parent;
        int totalBlue;
        int totalGreen;
        int totalRed;

        OctTreeNode() {
        }

        public void list(PrintStream printStream, int i) {
            for (int i2 = 0; i2 < i; i2++) {
                System.out.print(' ');
            }
            if (this.count == 0) {
                PrintStream printStream2 = System.out;
                printStream2.println(this.index + ": count=" + this.count);
            } else {
                PrintStream printStream3 = System.out;
                printStream3.println(this.index + ": count=" + this.count + " red=" + (this.totalRed / this.count) + " green=" + (this.totalGreen / this.count) + " blue=" + (this.totalBlue / this.count));
            }
            for (int i3 = 0; i3 < 8; i3++) {
                OctTreeNode[] octTreeNodeArr = this.leaf;
                if (octTreeNodeArr[i3] != null) {
                    octTreeNodeArr[i3].list(printStream, i + 2);
                }
            }
        }
    }

    public OctTreeQuantizer() {
        setup(256);
        this.colorList = new Vector[6];
        for (int i = 0; i < 6; i++) {
            this.colorList[i] = new Vector();
        }
        this.root = new OctTreeNode();
    }

    @Override // com.picspool.lib.filter.cpu.util.Quantizer
    public void setup(int i) {
        this.maximumColors = i;
        this.reduceColors = Math.max(512, i * 2);
    }

    @Override // com.picspool.lib.filter.cpu.util.Quantizer
    public void addPixels(int[] iArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            insertColor(iArr[i3 + i]);
            int i4 = this.colors;
            int i5 = this.reduceColors;
            if (i4 > i5) {
                reduceTree(i5);
            }
        }
    }

    @Override // com.picspool.lib.filter.cpu.util.Quantizer
    public int getIndexForColor(int i) {
        int i2 = (i >> 16) & 255;
        int i3 = (i >> 8) & 255;
        int i4 = i & 255;
        OctTreeNode octTreeNode = this.root;
        int i5 = 0;
        while (i5 <= 5) {
            int i6 = 128 >> i5;
            int i7 = (i2 & i6) != 0 ? 4 : 0;
            if ((i3 & i6) != 0) {
                i7 += 2;
            }
            if ((i6 & i4) != 0) {
                i7++;
            }
            OctTreeNode octTreeNode2 = octTreeNode.leaf[i7];
            if (octTreeNode2 == null) {
                return octTreeNode.index;
            }
            if (octTreeNode2.isLeaf) {
                return octTreeNode2.index;
            }
            i5++;
            octTreeNode = octTreeNode2;
        }
        System.out.println("getIndexForColor failed");
        return 0;
    }

    private void insertColor(int i) {
        int i2 = (i >> 16) & 255;
        int i3 = (i >> 8) & 255;
        int i4 = i & 255;
        OctTreeNode octTreeNode = this.root;
        for (int i5 = 0; i5 <= 5; i5++) {
            int i6 = 128 >> i5;
            int i7 = (i2 & i6) != 0 ? 4 : 0;
            if ((i3 & i6) != 0) {
                i7 += 2;
            }
            if ((i6 & i4) != 0) {
                i7++;
            }
            OctTreeNode octTreeNode2 = octTreeNode.leaf[i7];
            if (octTreeNode2 == null) {
                octTreeNode.children++;
                octTreeNode2 = new OctTreeNode();
                octTreeNode2.parent = octTreeNode;
                octTreeNode.leaf[i7] = octTreeNode2;
                octTreeNode.isLeaf = false;
                this.nodes++;
                this.colorList[i5].addElement(octTreeNode2);
                if (i5 == 5) {
                    octTreeNode2.isLeaf = true;
                    octTreeNode2.count = 1;
                    octTreeNode2.totalRed = i2;
                    octTreeNode2.totalGreen = i3;
                    octTreeNode2.totalBlue = i4;
                    octTreeNode2.level = i5;
                    this.colors++;
                    return;
                }
            } else if (octTreeNode2.isLeaf) {
                octTreeNode2.count++;
                octTreeNode2.totalRed += i2;
                octTreeNode2.totalGreen += i3;
                octTreeNode2.totalBlue += i4;
                return;
            }
            octTreeNode = octTreeNode2;
        }
        System.out.println("insertColor failed");
    }

    private void reduceTree(int i) {
        for (int i2 = 4; i2 >= 0; i2--) {
            Vector vector = this.colorList[i2];
            if (vector != null && vector.size() > 0) {
                for (int i3 = 0; i3 < vector.size(); i3++) {
                    OctTreeNode octTreeNode = (OctTreeNode) vector.elementAt(i3);
                    if (octTreeNode.children > 0) {
                        for (int i4 = 0; i4 < 8; i4++) {
                            OctTreeNode octTreeNode2 = octTreeNode.leaf[i4];
                            if (octTreeNode2 != null) {
                                if (!octTreeNode2.isLeaf) {
                                    System.out.println("not a leaf!");
                                }
                                octTreeNode.count += octTreeNode2.count;
                                octTreeNode.totalRed += octTreeNode2.totalRed;
                                octTreeNode.totalGreen += octTreeNode2.totalGreen;
                                octTreeNode.totalBlue += octTreeNode2.totalBlue;
                                octTreeNode.leaf[i4] = null;
                                octTreeNode.children--;
                                this.colors--;
                                this.nodes--;
                                this.colorList[i2 + 1].removeElement(octTreeNode2);
                            }
                        }
                        octTreeNode.isLeaf = true;
                        int i5 = this.colors + 1;
                        this.colors = i5;
                        if (i5 <= i) {
                            return;
                        }
                    }
                }
                continue;
            }
        }
        System.out.println("Unable to reduce the OctTree");
    }

    @Override // com.picspool.lib.filter.cpu.util.Quantizer
    public int[] buildColorTable() {
        int[] iArr = new int[this.colors];
        buildColorTable(this.root, iArr, 0);
        return iArr;
    }

    public void buildColorTable(int[] iArr, int[] iArr2) {
        this.maximumColors = iArr2.length;
        for (int i : iArr) {
            insertColor(i);
            int i2 = this.colors;
            int i3 = this.reduceColors;
            if (i2 > i3) {
                reduceTree(i3);
            }
        }
        int i4 = this.colors;
        int i5 = this.maximumColors;
        if (i4 > i5) {
            reduceTree(i5);
        }
        buildColorTable(this.root, iArr2, 0);
    }

    private int buildColorTable(OctTreeNode octTreeNode, int[] iArr, int i) {
        int i2 = this.colors;
        int i3 = this.maximumColors;
        if (i2 > i3) {
            reduceTree(i3);
        }
        if (octTreeNode.isLeaf) {
            int i4 = octTreeNode.count;
            iArr[i] = ((octTreeNode.totalGreen / i4) << 8) | (-16777216) | ((octTreeNode.totalRed / i4) << 16) | (octTreeNode.totalBlue / i4);
            int i5 = i + 1;
            octTreeNode.index = i;
            return i5;
        }
        for (int i6 = 0; i6 < 8; i6++) {
            if (octTreeNode.leaf[i6] != null) {
                octTreeNode.index = i;
                i = buildColorTable(octTreeNode.leaf[i6], iArr, i);
            }
        }
        return i;
    }
}
