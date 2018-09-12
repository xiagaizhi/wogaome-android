package com.yushi.leke.fragment.exhibition.vote;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yushi.leke.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by zhanyangyang on 2018/9/10 17:09
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class WinlistDialogFragment extends DialogFragment implements View.OnClickListener {
    private RecyclerView recycler_win_list;
    private List<WinProjectInfo> winListInfoList = new ArrayList<>();
    private WinListAdapter mWinListAdapter;

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
        int dialogHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
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

    private void initView(View view) {
        view.findViewById(R.id.img_close).setOnClickListener(this);
        recycler_win_list = view.findViewById(R.id.recycler_win_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_win_list.setLayoutManager(layoutManager);
        recycler_win_list.setHasFixedSize(true);
        mWinListAdapter = new WinListAdapter(getContext(),winListInfoList);
        recycler_win_list.setAdapter(mWinListAdapter);
        for (int i = 0;i<10;i++){
            winListInfoList.add(new WinProjectInfo());
        }
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
