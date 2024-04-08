package com.picspool.snappic.widget;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import com.picspool.snappic.adapter.CSSceneRecyclerViewAdapter;
import com.picspool.snappic.manager.CSSceneManager;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSceneBar extends FrameLayout {
    private List<DMWBRes> gpuFilterRes;
    private FrameLayout ly_root;
    private Context mContext;
    private onSceneBarClickListner mListenrer;
    private int oriPos;
    private RecyclerView recyclerView;
    private CSSceneRecyclerViewAdapter sceneRecyclerViewAdapter;
    private int selectPos;

    /* loaded from: classes.dex */
    public interface onSceneBarClickListner {
        void onCancel(DMWBRes dMWBRes);

        void onListItemClick(int i, DMWBRes dMWBRes, boolean z);

        void onOk(int i);
    }

    public CSSceneBar(Context context) {
        super(context);
        this.selectPos = -1;
        this.oriPos = -1;
        initView(context);
    }

    public CSSceneBar(Context context, int i) {
        super(context);
        this.selectPos = -1;
        this.oriPos = -1;
        this.oriPos = i;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_scenebar, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        this.ly_root = (FrameLayout) findViewById(R.id.ly_scenebar_container);
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSSceneBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSceneBar.this.mListenrer != null) {
                    CSSceneBar.this.mListenrer.onOk(CSSceneBar.this.selectPos);
                }
            }
        });
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSSceneBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSceneBar.this.mListenrer != null) {
                    if (CSSceneBar.this.oriPos > 0 && CSSceneBar.this.oriPos < CSSceneBar.this.gpuFilterRes.size()) {
                        CSSceneBar.this.mListenrer.onCancel((DMWBRes) CSSceneBar.this.gpuFilterRes.get(CSSceneBar.this.oriPos));
                        return;
                    }
                    GPUFilterRes gPUFilterRes = new GPUFilterRes();
                    gPUFilterRes.setFilterType(GPUFilterType.NOFILTER);
                    CSSceneBar.this.mListenrer.onCancel(gPUFilterRes);
                }
            }
        });
        CSSceneManager cSSceneManager = new CSSceneManager(this.mContext);
        this.gpuFilterRes = cSSceneManager.getBMWBResList();
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        CSSceneRecyclerViewAdapter cSSceneRecyclerViewAdapter = new CSSceneRecyclerViewAdapter(this.mContext, cSSceneManager.getBMWBResList());
        this.sceneRecyclerViewAdapter = cSSceneRecyclerViewAdapter;
        this.recyclerView.setAdapter(cSSceneRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.sceneRecyclerViewAdapter.setOnRecyclerViewItemClikListener(new CSSceneRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.picspool.snappic.widget.CSSceneBar.3
            @Override // com.picspool.snappic.adapter.CSSceneRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                CSSceneBar.this.selectPos = i;
                CSLibUiConfig.pointEventSingleSaveMode1(CSSceneBar.this.mContext, "scene", dMWBRes.getName());
                if (CSSceneBar.this.mListenrer != null) {
                    CSSceneBar.this.mListenrer.onListItemClick(i, dMWBRes, z);
                }
            }
        });
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context2 = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context2, context2.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    public void setOnSceneBarClickListner(onSceneBarClickListner onscenebarclicklistner) {
        this.mListenrer = onscenebarclicklistner;
    }

    public CSSceneRecyclerViewAdapter getSceneRecyclerViewAdapter() {
        return this.sceneRecyclerViewAdapter;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || this.mListenrer == null) {
            return true;
        }
        GPUFilterRes gPUFilterRes = new GPUFilterRes();
        gPUFilterRes.setFilterType(GPUFilterType.NOFILTER);
        this.mListenrer.onCancel(gPUFilterRes);
        return true;
    }
}
