package com.yushi.leke.fragment.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.api.EnhancedCall;
import com.yufan.library.base.BaseFragment;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.browser.BrowserBaseFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.manager.SPManager;
import com.yufan.library.manager.UserManager;
import com.yufan.library.util.SoftInputUtil;
import com.yufan.library.widget.anim.AFVerticalAnimator;
import com.yufan.share.ILoginCallback;
import com.yufan.share.ShareUtils;
import com.yushi.leke.BuildConfig;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.activity.MainActivity;
import com.yushi.leke.fragment.login.loginPhone.LoginPhoneFragment;
import com.yushi.leke.fragment.main.MainFragment;
import com.yushi.leke.fragment.musicplayer.MusicPlayerFragment;
import com.yushi.leke.fragment.register.RegisterFragment;

import java.util.Map;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(LoginVu.class)
public class LoginFragment extends BaseFragment<LoginContract.IView> implements LoginContract.Presenter {
    private long[] mHits = new long[5];
    private ShareUtils mShareUtils;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private  MainActivity.IActivityResult  mResult=   new MainActivity.IActivityResult() {
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            UMShareAPI.get(getActivity()).onActivityResult(requestCode,resultCode,data);
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mShareUtils = new ShareUtils(getActivity());
        ((MainActivity) getActivity()).registerIActivityResult(mResult);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).unregisterIActivityResult(mResult);
    }

    @Override
    public void onRefresh() {

    }



    @Override
    public void onRegisterClick() {
        start(UIHelper.creat(RegisterFragment.class).build());
    }

    @Override
    public void onPhoneLoginClick() {
        start(UIHelper.creat(LoginPhoneFragment.class).build());
    }

    @Override
    public void onWeixinLoginClick() {

        DialogManager.getInstance().showLoadingDialog();
        mShareUtils.login(SHARE_MEDIA.WEIXIN, new ILoginCallback() {
            @Override
            public void onSuccess(Map<String, String> map, SHARE_MEDIA share_media, Map<String, String> map1) {
                DialogManager.getInstance().dismiss();
                if(share_media==SHARE_MEDIA.WEIXIN){
               EnhancedCall call= ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).registerViaOAuth(map.get("accessToken"),"1"));
                    call.enqueue(new BaseHttpCallBack() {
                        @Override
                        public void onSuccess(ApiBean mApiBean) {
                            JSONObject jsonObject= JSON.parseObject(mApiBean.getData());
                            UserManager.getInstance().setToken(jsonObject.getString("token"));
                            UserManager.getInstance().setToken(jsonObject.getString("uid"));
                            startWithPopTo(UIHelper.creat(MainFragment.class).build(), LoginFragment.class,true);
                            mShareUtils.logout(SHARE_MEDIA.WEIXIN);
                        }

                        @Override
                        public void onError(int id, Exception e) {

                        }

                        @Override
                        public void onFinish() {

                        }
                    });
                }else {
                   // ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).registerViaOAuth(map.get("accessToken"),"2"));
                }

            }

            @Override
            public void onFaild(String s) {
                DialogManager.getInstance().dismiss();
                DialogManager.getInstance().toast("取消失败");
            }

            @Override
            public void onCancel() {
                DialogManager.getInstance().dismiss();
                DialogManager.getInstance().toast("取消登录");
            }
        });
    }


    @Override
    public void onAgreementClick() {
        start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getProtocol()).build());
    }

    @Override
    public void onServiceSelector(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        dialog.dismiss();
        SPManager.getInstance().saveValue(Global.SP_KEY_SERVICE_TYPE, dialog.getSelectedIndex());
        DialogManager.getInstance().toast("修改成功");
        ApiManager.getInstance().init(SPManager.getInstance().getInt(Global.SP_KEY_SERVICE_TYPE,BuildConfig.API_TYPE));
    }

    @Override
    public void onLogoClick() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
            mHits=new long[5];
            int apiType = SPManager.getInstance().getInt(Global.SP_KEY_SERVICE_TYPE, BuildConfig.API_TYPE);
            getVu().showServiceSelector(apiType);
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new AFVerticalAnimator(); //super.onCreateFragmentAnimator();
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        SoftInputUtil.hideSoftInput(getActivity(),getView());
    }
}
