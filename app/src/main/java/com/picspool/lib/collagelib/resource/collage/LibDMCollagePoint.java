package com.picspool.lib.collagelib.resource.collage;

import android.graphics.Point;

/* loaded from: classes3.dex */
public class LibDMCollagePoint {
    public Point moriPoint;
    private int mOutFrameWidth = 0;
    private int mInnerFrameWidth = 0;
    private int vLineMode = 0;
    private int hLineMode = 0;
    LibCollagePointState Xstate = LibCollagePointState.noMove;
    LibCollagePointState Ystate = LibCollagePointState.noMove;
    private boolean mIsArcPoint = false;
    private int mIsOutRectLinePoint = 0;

    /* loaded from: classes3.dex */
    public enum LibCollagePointState {
        canMove,
        noMove
    }

    public int getvLineMode() {
        return this.vLineMode;
    }

    public void setvLineMode(int i) {
        this.vLineMode = i;
    }

    public int gethLineMode() {
        return this.hLineMode;
    }

    public void sethLineMode(int i) {
        this.hLineMode = i;
    }

    public void setIsArcPoint(boolean z) {
        this.mIsArcPoint = z;
    }

    public boolean getIsArcPoint() {
        return this.mIsArcPoint;
    }

    public void setIsOutRectLinePoint(int i) {
        this.mIsOutRectLinePoint = i;
    }

    public int getIsOutRectLinePoint() {
        return this.mIsOutRectLinePoint;
    }

    public void setXState(LibCollagePointState libCollagePointState) {
        this.Xstate = libCollagePointState;
    }

    public LibCollagePointState getXState() {
        return this.Xstate;
    }

    public void setYState(LibCollagePointState libCollagePointState) {
        this.Ystate = libCollagePointState;
    }

    public LibCollagePointState getYState() {
        return this.Ystate;
    }

    public void setOriPoint(Point point) {
        this.moriPoint = point;
    }

    public Point getOriPoint() {
        return this.moriPoint;
    }

    public int getOutFrameWidth() {
        return this.mOutFrameWidth;
    }

    public void setOutFrameWidth(int i) {
        this.mOutFrameWidth = i;
    }

    public int getInnerFrameWidth() {
        return this.mInnerFrameWidth;
    }

    public void setInnerFrameWidth(int i) {
        this.mInnerFrameWidth = i;
    }

    public Point GetPoint() {
        return this.moriPoint;
    }
}
