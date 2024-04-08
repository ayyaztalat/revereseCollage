package com.picspool.lib.sticker.util;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class DM_Vector2D implements Serializable {
    private static final long serialVersionUID = -1844534518528011982L;

    /* renamed from: x */
    protected double f1989x;

    /* renamed from: y */
    protected double f1990y;

    public DM_Vector2D(double d) {
        this(d, d);
    }

    public DM_Vector2D() {
        this(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
    }

    public DM_Vector2D(double d, double d2) {
        this.f1989x = d;
        this.f1990y = d2;
    }

    public DM_Vector2D(DM_Vector2D dM_Vector2D) {
        this.f1989x = dM_Vector2D.f1989x;
        this.f1990y = dM_Vector2D.f1990y;
    }

    public void move(DM_Vector2D dM_Vector2D) {
        this.f1989x += dM_Vector2D.f1989x;
        this.f1990y += dM_Vector2D.f1990y;
    }

    public void move(double d, double d2) {
        this.f1989x += d;
        this.f1990y += d2;
    }

    public double[] getCoords() {
        return new double[]{this.f1989x, this.f1990y};
    }

    public void setLocation(double d, double d2) {
        this.f1989x = d;
        this.f1990y = d2;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DM_Vector2D) {
            DM_Vector2D dM_Vector2D = (DM_Vector2D) obj;
            return dM_Vector2D.f1989x == this.f1989x && dM_Vector2D.f1990y == this.f1990y;
        }
        return false;
    }

    public int hashCode() {
        return (int) (this.f1989x + this.f1990y);
    }

    public void setX(double d) {
        this.f1989x = d;
    }

    public void setY(double d) {
        this.f1990y = d;
    }

    public double getX() {
        return this.f1989x;
    }

    public double getY() {
        return this.f1990y;
    }

    /* renamed from: x */
    public int m6x() {
        return (int) this.f1989x;
    }

    /* renamed from: y */
    public int m5y() {
        return (int) this.f1990y;
    }

    public Object clone() {
        return new DM_Vector2D(this.f1989x, this.f1990y);
    }

    public void set(DM_Vector2D dM_Vector2D) {
        set(dM_Vector2D.getX(), dM_Vector2D.getY());
    }

    public void set(float f, float f2) {
        this.f1989x = f;
        this.f1990y = f2;
    }

    public void set(double d, double d2) {
        this.f1989x = d;
        this.f1990y = d2;
    }

    public DM_Vector2D reverse() {
        this.f1989x = -this.f1989x;
        this.f1990y = -this.f1990y;
        return this;
    }

    public float length() {
        double d = this.f1989x;
        double d2 = this.f1990y;
        return (float) Math.sqrt((d * d) + (d2 * d2));
    }

    public double lengthSquared() {
        double d = this.f1989x;
        double d2 = this.f1990y;
        return (d * d) + (d2 * d2);
    }

    public DM_Vector2D add(DM_Vector2D dM_Vector2D) {
        return new DM_Vector2D(this.f1989x + dM_Vector2D.f1989x, this.f1990y + dM_Vector2D.f1990y);
    }

    public DM_Vector2D addThis(DM_Vector2D dM_Vector2D) {
        this.f1989x += dM_Vector2D.f1989x;
        this.f1990y += dM_Vector2D.f1990y;
        return this;
    }

    public static DM_Vector2D sum(List<DM_Vector2D> list) {
        DM_Vector2D dM_Vector2D = new DM_Vector2D(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        for (DM_Vector2D dM_Vector2D2 : list) {
            dM_Vector2D.addThis(dM_Vector2D2);
        }
        return dM_Vector2D;
    }

    public static DM_Vector2D sum(DM_Vector2D dM_Vector2D, DM_Vector2D dM_Vector2D2) {
        return new DM_Vector2D(dM_Vector2D).addThis(dM_Vector2D2);
    }

    public static DM_Vector2D mean(List<DM_Vector2D> list) {
        int size = list.size();
        if (size == 0) {
            return new DM_Vector2D(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        }
        return sum(list).scale(1.0d / size);
    }

    public DM_Vector2D sub(DM_Vector2D dM_Vector2D) {
        this.f1989x -= dM_Vector2D.getX();
        this.f1990y -= dM_Vector2D.getY();
        return this;
    }

    public DM_Vector2D subtract(DM_Vector2D dM_Vector2D) {
        return new DM_Vector2D(this.f1989x - dM_Vector2D.f1989x, this.f1990y - dM_Vector2D.f1990y);
    }

    public double dot(DM_Vector2D dM_Vector2D) {
        return (this.f1989x * dM_Vector2D.f1989x) + (this.f1990y * dM_Vector2D.f1990y);
    }

    public static double cross(DM_Vector2D dM_Vector2D, DM_Vector2D dM_Vector2D2) {
        return dM_Vector2D.cross(dM_Vector2D2);
    }

    public double cross(DM_Vector2D dM_Vector2D) {
        return (this.f1989x * dM_Vector2D.f1990y) - (this.f1990y * dM_Vector2D.f1989x);
    }

    public double angle(DM_Vector2D dM_Vector2D) {
        return Math.atan2(this.f1990y, this.f1989x) - Math.atan2(dM_Vector2D.f1990y, dM_Vector2D.f1989x);
    }

    public DM_Vector2D multiply(double d) {
        return new DM_Vector2D(this.f1989x * d, d * this.f1990y);
    }

    public double dotProduct(DM_Vector2D dM_Vector2D) {
        return (dM_Vector2D.f1989x * this.f1989x) + (dM_Vector2D.f1990y * this.f1990y);
    }

    public DM_Vector2D scale(double d) {
        this.f1989x *= d;
        this.f1990y *= d;
        return this;
    }

    public DM_Vector2D normalize() {
        double sqrt = Math.sqrt(dotProduct(this));
        return new DM_Vector2D(this.f1989x / sqrt, this.f1990y / sqrt);
    }

    public double level() {
        return Math.sqrt(dotProduct(this));
    }

    public double distanceSquared(DM_Vector2D dM_Vector2D) {
        double x = dM_Vector2D.getX() - getX();
        double y = dM_Vector2D.getY() - getY();
        return (x * x) + (y * y);
    }

    public double distance(DM_Vector2D dM_Vector2D) {
        return Math.sqrt(distanceSquared(dM_Vector2D));
    }

    public DM_Vector2D modulate(DM_Vector2D dM_Vector2D) {
        return new DM_Vector2D(this.f1989x * dM_Vector2D.f1989x, this.f1990y * dM_Vector2D.f1990y);
    }

    public boolean equalsDelta(DM_Vector2D dM_Vector2D, double d) {
        return dM_Vector2D.getX() - d < this.f1989x && dM_Vector2D.getX() + d > this.f1989x && dM_Vector2D.getY() - d < this.f1990y && dM_Vector2D.getY() + d > this.f1990y;
    }

    public static DM_Vector2D difference(DM_Vector2D dM_Vector2D, DM_Vector2D dM_Vector2D2) {
        return new DM_Vector2D(dM_Vector2D).sub(dM_Vector2D2);
    }

    public void rotate90() {
        setLocation(this.f1990y, -this.f1989x);
    }

    public static DM_Vector2D rotate90(DM_Vector2D dM_Vector2D) {
        return new DM_Vector2D(-dM_Vector2D.f1990y, dM_Vector2D.f1989x);
    }

    public static DM_Vector2D rotate90R(DM_Vector2D dM_Vector2D) {
        return new DM_Vector2D(dM_Vector2D.f1990y, -dM_Vector2D.f1989x);
    }

    public static double dot(DM_Vector2D dM_Vector2D, DM_Vector2D dM_Vector2D2) {
        return dM_Vector2D.dot(dM_Vector2D2);
    }

    public static DM_Vector2D mult(DM_Vector2D dM_Vector2D, double d) {
        return new DM_Vector2D(dM_Vector2D).scale(d);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("[DM_Vector2D x:");
        stringBuffer.append(this.f1989x);
        stringBuffer.append(" y:");
        stringBuffer.append(this.f1990y);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
