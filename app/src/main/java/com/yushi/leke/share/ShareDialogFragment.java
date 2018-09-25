package com.yushi.leke.share;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yushi.leke.R;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.exhibition.win.WinListAdapter;
import com.yushi.leke.fragment.exhibition.win.WinProjectInfo;
import com.yushi.leke.fragment.exhibition.win.WinProjectInfoList;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by zhanyangyang on 2018/9/10 17:09
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class ShareDialogFragment extends DialogFragment implements View.OnClickListener {
    private RecyclerView recycler_win_list;
    private List<WinProjectInfo> winListInfoList = new ArrayList<>();
    private WinListAdapter mWinListAdapter;
    private WinProjectInfoList winProjectInfoList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置样式
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.65);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置从底部弹出
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setAttributes(params);
        View view = inflater.inflate(R.layout.dialog_fragment_win, container);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String activityId = "";
        Bundle args = getArguments();
        if (args != null) {
            activityId = args.getString("activityId");
        }
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).winlist(activityId))
                .useCache(true)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (TextUtils.isEmpty(mApiBean.getData())) {
                            winProjectInfoList = JSON.parseObject(mApiBean.getData(), WinProjectInfoList.class);
                            if (winProjectInfoList != null && winProjectInfoList.getWinList() != null && winProjectInfoList.getWinList().size() > 0) {
                                winListInfoList.clear();
                                winListInfoList.addAll(winProjectInfoList.getWinList());
                                mWinListAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    private void initView(View view) {
        view.findViewById(R.id.img_close).setOnClickListener(this);
        recycler_win_list = view.findViewById(R.id.recycler_win_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_win_list.setLayoutManager(layoutManager);
        recycler_win_list.setHasFixedSize(true);
        mWinListAdapter = new WinListAdapter(getContext(), winListInfoList);
        recycler_win_list.setAdapter(mWinListAdapter);
        mWinListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                dismiss();
                break;
        }
    }
}
