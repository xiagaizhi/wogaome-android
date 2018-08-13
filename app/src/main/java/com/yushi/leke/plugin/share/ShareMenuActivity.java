package com.yushi.leke.plugin.share;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yufan.library.base.BaseActivity;
import com.yufan.library.manager.DialogManager;
import com.yufan.share.IShareCallback;
import com.yufan.share.ShareModel;
import com.yufan.share.ShareUtils;
import com.yushi.leke.plugin.R;

/**
 * Created by mengfantao on 17/8/28.
 * <p>
 * 统一分享
 */

public class ShareMenuActivity extends BaseActivity {
    private Handler handler=new Handler();
    private ShareUtils su;
    private ShareModel model;
    public static final int SHARE_REQUEST_CODE=201;
    public static final String TYPES = "TYPES";
    public static final String SHARE_DATA = "SHARE_DATA";
    protected MenuType[] defaultType = new MenuType[]{MenuType.WEIXIN, MenuType.QQ, MenuType.WEIXIN_MOMENTS, MenuType.WEIBO, MenuType.QQ_SPACE};
    private int[] types;
    private MenuDialog mMenuDialog;
    public static void startShare(Fragment context, ShareModel model, int[] types) {
        Intent intent = new Intent(context.getContext(), ShareMenuActivity.class);
        Bundle bundle = new Bundle();
        if (model != null) {
            bundle.putSerializable(SHARE_DATA, model);
        }
        if (types != null) {
            bundle.putSerializable(TYPES, types);
        }
        intent.putExtras(bundle);
        context.startActivityForResult(intent,SHARE_REQUEST_CODE);
    }

    /**
     * 不传type 用默认的defaulttype
     *
     * @param context
     * @param model
     */
    public static void startShare(Fragment context, ShareModel model) {
        startShare(context, model, null);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
      setTheme(R.style.ThemeActivity);
        super.onCreate(savedInstanceState);
         Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            model = (ShareModel) bundle.getSerializable(SHARE_DATA);
            types=  bundle.getIntArray(TYPES);
        }
        if(types==null){
            types=new int []{};
        }
        MenuType[] menus=new MenuType[types.length];
        for (int i=0;i<types.length;i++){
            menus[i]=  MenuType.getMenuTypeByType(types[i]);
        }
        if(types.length==0){
            menus=defaultType;
        }

        setContentView(R.layout.fragment_blank_detail);
        su = new ShareUtils(this);
        mMenuDialog = new MenuDialog(this, menus,true) {
            @Override
            protected void dispatchClick(MenuType shareItem) {
                switch (shareItem) {
                    case WEIXIN:
                        boolean weixininstall = su.isInstall(ShareMenuActivity.this, SHARE_MEDIA.WEIXIN);
                        if (weixininstall) {
                            share(SHARE_MEDIA.WEIXIN);
                        } else {
                            DialogManager.getInstance().toast("未安装微信客户端");

                        }

                        break;
                    case QQ:
                        boolean qqinstall = su.isInstall(ShareMenuActivity.this, SHARE_MEDIA.QQ);
                        if (qqinstall) {
                            share(SHARE_MEDIA.QQ);
                        } else {
                            DialogManager.getInstance().toast("未安装QQ客户端");

                        }

                        break;
                    case WEIXIN_MOMENTS:
                        boolean weixinmomentsinstall = su.isInstall(ShareMenuActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                        if (weixinmomentsinstall) {
                            share(SHARE_MEDIA.WEIXIN_CIRCLE);
                        } else {
                            DialogManager.getInstance().toast("未安装微信客户端");

                        }

                        break;
                    case WEIBO:
                      share(SHARE_MEDIA.SINA);

                        break;
                    case QQ_SPACE:

                        boolean qqSpaceinstall = su.isInstall(ShareMenuActivity.this, SHARE_MEDIA.QQ);
                        if (qqSpaceinstall) {
                            share(SHARE_MEDIA.QZONE);
                        } else {
                            DialogManager.getInstance().toast("未安装QQ客户端");

                        }
                        break;

                }
            }
        };

        mMenuDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
               handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       finish();
                    }
                },500);
            }
        });
        mMenuDialog.setCancelable(true);
        mMenuDialog.setCanceledOnTouchOutside(true);
      mMenuDialog.show();
    }
    @Deprecated
    protected UMImage getShareImage(String url) {
        UMImage img = new UMImage(this, url);
        return img;
    }
    private UMWeb getUMWeb(String imageUrl, String url, String title, String desc){
        UMImage img = new UMImage(this, imageUrl);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(img);  //缩略图
        web.setDescription(desc);//描述
        return web;
    }
    private void share(SHARE_MEDIA share_media) {

        if (model == null) {
           return;
        }
        model.setmUMWeb(getUMWeb(model.getIcon(),model.getTargetUrl(),model.getTitle(),model.getContent()));
        su.sWebShare(share_media, ShareMenuActivity.this, model, new IShareCallback() {
            @Override
            public void onStart(SHARE_MEDIA var1) {
                DialogManager.getInstance().showLoadingDialog("正在调起分享");
            }

            @Override
            public void onSuccess() {
                DialogManager.getInstance().dismiss();
                DialogManager.getInstance().toast("分享成功");
                Intent intent=new Intent();
                setResult(Activity.RESULT_OK,intent);

                finish();
            }

            @Override
            public void onFaild() {
                DialogManager.getInstance().dismiss();
                DialogManager.getInstance().toast("分享失败");
                finish();
            }

            @Override
            public void onCancel() {
                DialogManager.getInstance().dismiss();
                DialogManager.getInstance().toast("取消分享");
                finish();
            }
        });
    }

    @Override
    public void finish() {
        if(mMenuDialog!=null){
            mMenuDialog.dismiss();
        }
        super.finish();
    }



    @Override
    protected void onDestroy() {
        DialogManager.getInstance().dismiss();
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        DialogManager.getInstance().dismiss();
        su.onActivityResult(requestCode, resultCode, data);
    }


}
