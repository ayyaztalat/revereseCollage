package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.sky.testproject.R;


/* loaded from: classes2.dex */
public abstract class CustomFrameLayoutImags<T> extends FrameLayout {
    public static final String TAG = "Jie";
    private View bottomTool;
    private boolean bottomToolShow;
    private T il1;
    private boolean il12;
    private boolean il13;
    public FrameLayout il14;
    public FrameLayout il15;
    private T il2;
    private int il3;
    private int il4;
    protected IL4567123<T> il5;
    private TextView il6;

    /* loaded from: classes2.dex */
    public interface IL4567123<M> {
        /* renamed from: f1 */
        void mo72f1();

        /* renamed from: f2 */
        void mo71f2();

        /* renamed from: f3 */
        void mo70f3(M m);

        /* renamed from: f4 */
        void mo69f4(M m);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$f7$0(View view) {
    }

    protected abstract void onSelectedPosition(int i);

    /* renamed from: f3 */
    public void m77f3() {
        mo67f4();
        m76f5(this.il2);
        IL4567123<T> il4567123 = this.il5;
        if (il4567123 != null) {
            il4567123.mo71f2();
        }
    }

    /* renamed from: f1 */
    public void m78f1() {
        mo68f2();
        IL4567123<T> il4567123 = this.il5;
        if (il4567123 != null) {
            il4567123.mo72f1();
        }
    }

    public CustomFrameLayoutImags(Context context) {
        super(context);
        m75f7(context);
    }

    public CustomFrameLayoutImags(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m75f7(context);
    }

    public CustomFrameLayoutImags(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m75f7(context);
    }

    /* renamed from: f7 */
    private void m75f7(Context context) {
        setBackgroundColor(Color.parseColor("#252525"));
        setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL29$3Ck6JtA1z6vh8oeMlDpo4MNH8Zk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomFrameLayoutImags.lambda$f7$0(view);
            }
        });
        inflate(context, R.layout.view_bottom_base, this);
        this.il14 = (FrameLayout) findViewById(R.id.content);
        this.il15 = (FrameLayout) findViewById(R.id.toolTitle);
        this.bottomTool = findViewById(R.id.img_sub_close);
        this.il6 = (TextView) findViewById(R.id.title);
        findViewById(R.id.close).setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL29$XTL9oPHZOwV2au1lQ6zbqPy_s8g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomFrameLayoutImags.this.lambda$f7$1$IL29(view);
            }
        });
        findViewById(R.id.ok).setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL29$gAgz10YjIZUScU7scxBOkZrPyNE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomFrameLayoutImags.this.lambda$f7$2$IL29(view);
            }
        });
        this.bottomTool.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL29$si6_5BjCcfNEJGC7HiSfvUfGoLg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomFrameLayoutImags.this.lambda$f7$3$IL29(view);
            }
        });
        f10(context, this.il15, this.il14);
        if (this.il12) {
            this.il15.setVisibility(View.VISIBLE);
            this.bottomTool.setVisibility(View.GONE);
        } else if (this.bottomToolShow) {
            this.bottomTool.setVisibility(View.VISIBLE);
            this.il15.setVisibility(View.GONE);
        } else {
            this.bottomTool.setVisibility(View.GONE);
            this.il15.setVisibility(View.GONE);
        }
        post(new Runnable() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$87mzuRkbC0esqRESz2Ic2ixhH_0
            @Override // java.lang.Runnable
            public final void run() {
                CustomFrameLayoutImags.this.mo66f9();
            }
        });
    }

    public void lambda$f7$1$IL29(View view) {
        m77f3();
    }

    public void lambda$f7$2$IL29(View view) {
        m78f1();
    }

    public  void lambda$f7$3$IL29(View view) {
        m78f1();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showToolView(boolean z) {
        this.il12 = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showBottomToolView(boolean z) {
        this.bottomToolShow = z;
        if (z) {
            this.il12 = false;
        }
    }

    public void setIl4(int i) {
        this.il4 = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: f8 */
    public void m74f8(T t, int i) {
        m73f8(t, i, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: f8 */
    public void m73f8(T t, int i, boolean z) {
        this.il1 = t;
        this.il3 = i;
        IL4567123<T> il4567123 = this.il5;
        if (il4567123 != null) {
            il4567123.mo70f3(t);
        }
        if (z) {
            m76f5(t);
        }
    }

    /* renamed from: f5 */
    protected void m76f5(T t) {
        IL4567123<T> il4567123 = this.il5;
        if (il4567123 != null) {
            il4567123.mo69f4(t);
        }
    }

    public void setIl6(String str) {
        this.il6.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: f9 */
    public void mo66f9() {
        post(new Runnable() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL29$VEuNmyIJNI264Wz2HBJ7zwgyAoo
            @Override // java.lang.Runnable
            public final void run() {
                CustomFrameLayoutImags.this.lambda$f9$4$IL29();
            }
        });
    }

    public /* synthetic */ void lambda$f9$4$IL29() {
        onSelectedPosition(this.il4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
    }

    public int f11() {
        if (this.il12 || this.bottomToolShow) {
            return CSScreenInfoUtil.dip2px(getContext(), 50.0f);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: f4 */
    public void mo67f4() {
        onSelectedPosition(this.il4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: f2 */
    public void mo68f2() {
        T t = this.il1;
        this.il2 = t;
        this.il4 = this.il3;
        if (this.il13) {
            this.il13 = false;
            m76f5(t);
        }
    }

    public void setIl2(T t) {
        this.il13 = true;
        this.il2 = t;
    }

    public void setIl5(IL4567123<T> il4567123) {
        this.il5 = il4567123;
    }

    public T getIl1() {
        return this.il1;
    }

    public T getIl2() {
        return this.il2;
    }

    public int getIl4() {
        return this.il4;
    }

    public int getIl3() {
        return this.il3;
    }

    public void setIl1(T t) {
        this.il1 = t;
    }

    public void setIl3(int i) {
        this.il3 = i;
    }
}
