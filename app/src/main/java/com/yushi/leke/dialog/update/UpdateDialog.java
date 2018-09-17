package com.yushi.leke.dialog.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.ApkUtils;
import com.yushi.leke.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 作者：Created by zhanyangyang on 2018/9/12 19:37
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {
    private UpdateInfo mUpdateInfo;
    private ICallBack mICallBack;
    private TextView tv_version;
    private RecyclerView rey_upgrade_info;
    private Button btn_upgrade;
    private ImageView img_upgrade_close;
    private List<String> upgradeInfos = new ArrayList<>();
    private UpdateInfoAdapter mUpdateInfoAdapter;
    private Context mContext;

    public UpdateDialog(@NonNull Context context, UpdateInfo updateInfo, ICallBack callBack) {
        super(context);
        this.mUpdateInfo = updateInfo;
        this.mICallBack = callBack;
        this.mContext = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_update_layout, null);
        setContentView(rootView);
        initView(rootView);
        setCanceledOnTouchOutside(false);
    }

    private void initView(View view) {
        tv_version = view.findViewById(R.id.tv_version);
        tv_version.setText(mUpdateInfo.getAppVersion());
        rey_upgrade_info = view.findViewById(R.id.rey_upgrade_info);
        btn_upgrade = view.findViewById(R.id.btn_upgrade);
        img_upgrade_close = view.findViewById(R.id.img_upgrade_close);
        img_upgrade_close.setOnClickListener(this);
        btn_upgrade.setOnClickListener(this);
        mUpdateInfoAdapter = new UpdateInfoAdapter(mContext, upgradeInfos);
        rey_upgrade_info.setAdapter(mUpdateInfoAdapter);
        if (mUpdateInfo.isForceUpdate()) {
            setOnKeyListener(new DialogInterface.OnKeyListener() {//点返回键强制不让dialog消失
                @Override
                public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
                        return true;
                    }
                    return false;
                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setType(WindowManager.LayoutParams.TYPE_APPLICATION);
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = d.getWidth();
        window.setAttributes(p);
        window.setDimAmount(0.6f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upgrade:
                btn_upgrade.setEnabled(false);
                UpgradeUtil.upgrade(mContext,mUpdateInfo.getPkgPath());
                if (!mUpdateInfo.isForceUpdate()) {
                    dismiss();
                }
                break;
            case R.id.img_upgrade_close:
                if (mUpdateInfo.isForceUpdate()) {
                    if (mICallBack != null) {
                        mICallBack.OnBackResult(2, mUpdateInfo);
                    }
                }
                dismiss();
                break;
        }
    }
}
