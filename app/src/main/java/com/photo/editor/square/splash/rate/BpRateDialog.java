package com.photo.editor.square.splash.rate;

import static android.view.View.ALPHA;
import static android.view.View.SCALE_X;
import static android.view.View.SCALE_Y;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.motion.widget.Key;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.HashMap;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class BpRateDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "BpRateDialog";
    private BpRateEntity entity;
    private String feedback;
    private Handler handler;
    private TextView iv_rate;
    private View iv_star_1;
    private View iv_star_2;
    private View iv_star_3;
    private View iv_star_4;
    private View iv_star_5;
    private Context mContext;
    private EditText problemEt;
    private RadioGroup problemRg;
    private TextView problemTxt;
    private boolean rate;
    private View rateHand;
    private View rateHandArrow;
    private TextView rate_msg;
    private TextView rate_title;
    private Runnable star1Scale;
    private Runnable star2Scale;
    private Runnable star3Scale;
    private Runnable star4Scale;
    private Runnable star5Scale;
    private int starNum;

    public BpRateDialog(Context context, BpRateEntity bpRateEntity) {
        super(context, R.style.dialog);
        this.feedback = "_noteasy";
        this.rate = true;
        this.handler = new Handler();
        this.star1Scale = new Runnable() { // from class: com.photo.editor.square.splash.rate.BpRateDialog.1
            @Override // java.lang.Runnable
            public void run() {
                BpRateDialog bpRateDialog = BpRateDialog.this;
                bpRateDialog.animScale(bpRateDialog.iv_star_1, 248L);
            }
        };
        this.star2Scale = new Runnable() { // from class: com.photo.editor.square.splash.rate.BpRateDialog.2
            @Override // java.lang.Runnable
            public void run() {
                BpRateDialog bpRateDialog = BpRateDialog.this;
                bpRateDialog.animScale(bpRateDialog.iv_star_2, 248L);
            }
        };
        this.star3Scale = new Runnable() { // from class: com.photo.editor.square.splash.rate.BpRateDialog.3
            @Override // java.lang.Runnable
            public void run() {
                BpRateDialog bpRateDialog = BpRateDialog.this;
                bpRateDialog.animScale(bpRateDialog.iv_star_3, 248L);
            }
        };
        this.star4Scale = new Runnable() { // from class: com.photo.editor.square.splash.rate.BpRateDialog.4
            @Override // java.lang.Runnable
            public void run() {
                BpRateDialog bpRateDialog = BpRateDialog.this;
                bpRateDialog.animScale(bpRateDialog.iv_star_4, 248L);
            }
        };
        this.star5Scale = new Runnable() { // from class: com.photo.editor.square.splash.rate.BpRateDialog.5
            @Override // java.lang.Runnable
            public void run() {
                BpRateDialog bpRateDialog = BpRateDialog.this;
                bpRateDialog.animScale(bpRateDialog.iv_star_5, 248L);
            }
        };
        this.mContext = context;
        this.entity = bpRateEntity;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
        this.mContext.getSharedPreferences(BpRateAgent.RATE_ED_T, 0).edit().putInt(BpRateAgent.RATE_ED_T, this.mContext.getSharedPreferences(BpRateAgent.RATE_ED_T, 0).getInt(BpRateAgent.RATE_ED_T, 0) + 1).commit();
    }

    private void init() {
        View inflate = View.inflate(this.mContext, R.layout.bp_layout_rate_dialog, null);
        inflate.findViewById(R.id.iv_rate_close).setOnClickListener(this);
        View findViewById = inflate.findViewById(R.id.iv_star_1);
        this.iv_star_1 = findViewById;
        findViewById.setOnClickListener(this);
        View findViewById2 = inflate.findViewById(R.id.iv_star_2);
        this.iv_star_2 = findViewById2;
        findViewById2.setOnClickListener(this);
        View findViewById3 = inflate.findViewById(R.id.iv_star_3);
        this.iv_star_3 = findViewById3;
        findViewById3.setOnClickListener(this);
        View findViewById4 = inflate.findViewById(R.id.iv_star_4);
        this.iv_star_4 = findViewById4;
        findViewById4.setOnClickListener(this);
        View findViewById5 = inflate.findViewById(R.id.iv_star_5);
        this.iv_star_5 = findViewById5;
        findViewById5.setOnClickListener(this);
        TextView textView = (TextView) inflate.findViewById(R.id.rate_btn);
        this.iv_rate = textView;
        textView.setOnClickListener(this);
        this.rateHand = inflate.findViewById(R.id.rate_hand);
        this.rateHandArrow = inflate.findViewById(R.id.rate_hand_arrow);
        this.rate_title = (TextView) inflate.findViewById(R.id.rate_title);
        this.rate_msg = (TextView) inflate.findViewById(R.id.rate_msg);
        this.problemTxt = (TextView) inflate.findViewById(R.id.problemTxt);
        this.problemRg = (RadioGroup) inflate.findViewById(R.id.problemRg);
        this.problemEt = (EditText) inflate.findViewById(R.id.problemEt);
        this.problemTxt.setVisibility(View.GONE);
        this.problemRg.setVisibility(View.GONE);
        this.problemEt.setVisibility(View.GONE);
        this.iv_rate.setVisibility(View.GONE);
        this.problemRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.photo.editor.square.splash.rate.-$$Lambda$BpRateDialog$thXNdW30vKhjPGNmFJwuU4mra5M
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup, int i) {
                BpRateDialog.this.lambda$init$0$BpRateDialog(radioGroup, i);
            }
        });
        setContentView(inflate);
        disableClick();
        inflate.postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.rate.-$$Lambda$BpRateDialog$8fH6R0AQ1vEpxS8qMMz6dhj0FdU
            @Override // java.lang.Runnable
            public final void run() {
                BpRateDialog.this.playIndicateAni();
            }
        }, 500L);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.dimAmount = 0.5f;
        window.setGravity(17);
        window.setAttributes(attributes);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @SuppressLint("NonConstantResourceId")
    public  void lambda$init$0$BpRateDialog(RadioGroup radioGroup, int i) {
//        switch (radioGroup.getId()) {
//            case R.id.radioBtn1:
//                this.feedback = "_noteasy";
//                return;
//            case R.id.radioBtn2:
//                this.feedback = "_manyads";
//                return;
//            case R.id.radioBtn3 :
//                this.feedback = "_havebugs";
//                return;
//            default:
//                return;
//        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableClick() {
        this.iv_star_1.setClickable(true);
        this.iv_star_2.setClickable(true);
        this.iv_star_3.setClickable(true);
        this.iv_star_4.setClickable(true);
        this.iv_star_5.setClickable(true);
    }

    private void disableClick() {
        this.iv_star_1.setClickable(false);
        this.iv_star_2.setClickable(false);
        this.iv_star_3.setClickable(false);
        this.iv_star_4.setClickable(false);
        this.iv_star_5.setClickable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playIndicateAni() {
        View view;
        if (this.iv_star_1 == null || this.iv_star_5 == null || this.rateHand == null || (view = this.rateHandArrow) == null) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        this.rateHand.setVisibility(View.VISIBLE);
        int left = this.iv_star_1.getLeft() + (this.iv_star_1.getWidth() / 2);
        int left2 = this.iv_star_5.getLeft() + (this.iv_star_5.getWidth() / 2);
        playAnim(5);
        ObjectAnimator.ofFloat(this.rateHand, "X", left, left2).setDuration(687L).start();
        this.iv_star_1.postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.rate.-$$Lambda$BpRateDialog$6m1_VvPJsm2uvoc0eGW5cwfs7mI
            @Override // java.lang.Runnable
            public final void run() {
                BpRateDialog.this.lambda$playIndicateAni$1$BpRateDialog();
            }
        }, 1300L);
    }

    public /* synthetic */ void lambda$playIndicateAni$1$BpRateDialog() {
        this.iv_star_1.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
        this.iv_star_2.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
        this.iv_star_3.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
        this.iv_star_4.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
        this.iv_star_5.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
        View view = this.rateHand;
        if (view != null) {
            view.setVisibility(View.GONE);
        }
        View view2 = this.rateHandArrow;
        if (view2 != null) {
            view2.setVisibility(View.GONE);
        }
        enableClick();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_rate_close) {
            try {
                Context context = this.mContext;
                BpEventUtils.pointItemEvent(context, "Rate", "Rate", "Close_" + this.starNum);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dismiss();
        } else if (id == R.id.iv_star_1) {
            disableClick();
            this.iv_star_2.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.iv_star_3.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.iv_star_4.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.iv_star_5.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.rate = false;
            this.iv_rate.setText(this.mContext.getResources().getString(R.string.feed_us));
            checkRateButton();
            playAnim(1);
        } else if (id == R.id.iv_star_2) {
            disableClick();
            this.iv_star_3.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.iv_star_4.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.iv_star_5.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.rate = false;
            this.iv_rate.setText(this.mContext.getResources().getString(R.string.feed_us));
            checkRateButton();
            playAnim(2);
        } else if (id == R.id.iv_star_3) {
            disableClick();
            this.iv_star_4.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.iv_star_5.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.rate = false;
            this.iv_rate.setText(this.mContext.getResources().getString(R.string.feed_us));
            checkRateButton();
            playAnim(3);
        } else if (id == R.id.iv_star_4) {
            disableClick();
            this.iv_star_5.setBackgroundResource(R.drawable.rate_view_dialog_star_unchecked);
            this.rate = false;
            this.iv_rate.setText(this.mContext.getResources().getString(R.string.feed_us));
            checkRateButton();
            playAnim(4);
        } else if (id == R.id.iv_star_5) {
            disableClick();
            this.rate = true;
            this.iv_rate.setText(this.mContext.getResources().getString(R.string.rate_us));
            checkRateButton();
            playAnim(5);
        } else if (id == R.id.rate_btn) {
            if (this.rate) {
                try {
                    Context context2 = this.mContext;
                    BpEventUtils.pointItemEvent(context2, "Rate", "Rate", "Rate_" + this.starNum);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    try {
                        BpRateAgent.showRate(this.mContext);
                        DMPreferencesUtil.save(this.mContext.getApplicationContext(), "is_rate_this_time", "CacheEntity.KEY", "1");
                        if (BpRateUtils.getExactrafromRandom(this.entity.getRateShowToast()).booleanValue()) {
                            Toast.makeText(this.mContext, "Thank you very much for your Rate, best wishes to you.", Toast.LENGTH_SHORT).show();
                        }
                    } finally {
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                return;
            }
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("Rate", "Feedback_" + this.starNum + this.feedback);
                if (this.problemEt.getText().length() > 0) {
                    hashMap.put("Rate", "Feedback_" + this.starNum + this.problemEt.getText().toString());
                }
//                FlurryAgent.logEvent("Rate", hashMap);
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            try {
                try {
                    DMPreferencesUtil.save(this.mContext.getApplicationContext(), "is_rate_this_time", "CacheEntity.KEY", "1");
                    if (BpRateUtils.getExactrafromRandom(this.entity.getRateShowToast()).booleanValue()) {
                        Toast.makeText(this.mContext, "Thank you very much for your Rate, best wishes to you.", Toast.LENGTH_LONG).show();
                    }
                } finally {
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
    }

    private void checkRateButton() {
        TextView textView = this.iv_rate;
        if (textView != null && textView.getVisibility() == View.GONE) {
            this.iv_rate.setVisibility(View.VISIBLE);
        }
        View view = this.rateHand;
        if (view != null && view.getVisibility() == View.VISIBLE) {
            this.rateHand.setVisibility(View.GONE);
        }
        View view2 = this.rateHandArrow;
        if (view2 != null && view2.getVisibility() == View.VISIBLE) {
            this.rateHandArrow.setVisibility(View.GONE);
        }
        checkUI();
    }

    private void checkUI() {
        this.rate_title.setVisibility(this.rate ? View.VISIBLE : View.GONE);
        this.rate_msg.setVisibility(this.rate ? View.VISIBLE : View.GONE);
        this.problemTxt.setVisibility(this.rate ? View.GONE : View.VISIBLE);
        this.problemRg.setVisibility(this.rate ? View.GONE : View.VISIBLE);
        this.problemEt.setVisibility(this.rate ? View.GONE : View.VISIBLE);
        try {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.iv_rate.getLayoutParams();
            layoutParams.topMargin = DMScreenInfoUtil.dip2px(getContext(), this.rate ? 30.0f : 20.0f);
            this.iv_rate.setLayoutParams(layoutParams);
        } catch (Exception unused) {
        }
        if (this.iv_rate.isShown()) {
            return;
        }
        this.iv_rate.setVisibility(View.VISIBLE);
    }

    private void playAnim(int i) {
        this.starNum = i;
        if (i == 1) {
            animAlpha(186L, this.iv_star_1);
            this.handler.postDelayed(this.star1Scale, 186L);
            this.handler.postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.rate.-$$Lambda$BpRateDialog$JK537ViQV20vYwYEklXz6wg1nNA
                @Override // java.lang.Runnable
                public final void run() {
                    BpRateDialog.this.enableClick();
                }
            }, 186L);
        } else if (i == 2) {
            animAlpha(186L, this.iv_star_1, this.iv_star_2);
            this.handler.postDelayed(this.star1Scale, 186L);
            this.handler.postDelayed(this.star2Scale, 248L);
            this.handler.postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.rate.-$$Lambda$BpRateDialog$JK537ViQV20vYwYEklXz6wg1nNA
                @Override // java.lang.Runnable
                public final void run() {
                    BpRateDialog.this.enableClick();
                }
            }, 248L);
        } else if (i == 3) {
            animAlpha(186L, this.iv_star_1, this.iv_star_2, this.iv_star_3);
            this.handler.postDelayed(this.star1Scale, 186L);
            this.handler.postDelayed(this.star2Scale, 248L);
            this.handler.postDelayed(this.star3Scale, 310L);
            this.handler.postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.rate.-$$Lambda$BpRateDialog$JK537ViQV20vYwYEklXz6wg1nNA
                @Override // java.lang.Runnable
                public final void run() {
                    BpRateDialog.this.enableClick();
                }
            }, 310L);
        } else if (i == 4) {
            animAlpha(186L, this.iv_star_1, this.iv_star_2, this.iv_star_3, this.iv_star_4);
            this.handler.postDelayed(this.star1Scale, 186L);
            this.handler.postDelayed(this.star2Scale, 248L);
            this.handler.postDelayed(this.star3Scale, 310L);
            this.handler.postDelayed(this.star4Scale, 372L);
            this.handler.postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.rate.-$$Lambda$BpRateDialog$JK537ViQV20vYwYEklXz6wg1nNA
                @Override // java.lang.Runnable
                public final void run() {
                    BpRateDialog.this.enableClick();
                }
            }, 372L);
        } else {
            animAlpha(186L, this.iv_star_1, this.iv_star_2, this.iv_star_3, this.iv_star_4, this.iv_star_5);
            this.handler.postDelayed(this.star1Scale, 186L);
            this.handler.postDelayed(this.star2Scale, 248L);
            this.handler.postDelayed(this.star3Scale, 310L);
            this.handler.postDelayed(this.star4Scale, 372L);
            this.handler.postDelayed(this.star5Scale, 434L);
            this.handler.postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.rate.-$$Lambda$BpRateDialog$JK537ViQV20vYwYEklXz6wg1nNA
                @Override // java.lang.Runnable
                public final void run() {
                    BpRateDialog.this.enableClick();
                }
            }, 434L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animScale(View view, long j) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, SCALE_X, 0.9f, 1.2f, 1.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, SCALE_Y, 0.9f, 1.2f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setDuration(j);
        animatorSet.start();
        view.setBackgroundResource(R.drawable.rate_view_dialog_star_checked);
        view.setAlpha(1.0f);
    }

    private void animAlpha(long j, View... viewArr) {
        if (viewArr == null || viewArr.length == 0) {
            return;
        }
        for (View view : viewArr) {
            if (view == this.iv_star_1) {
                ObjectAnimator.ofFloat(view, ALPHA, 1.0f, 0.0f, 1.0f).setDuration(j).start();
            } else {
                ObjectAnimator.ofFloat(view, ALPHA, 1.0f, 0.0f).setDuration(j).start();
            }
        }
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        try {
            BpEventUtils.pointItemEvent(this.mContext, "Rate", "Rate", "Show");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
