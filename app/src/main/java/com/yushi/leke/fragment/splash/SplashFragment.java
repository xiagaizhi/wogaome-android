package com.yushi.leke.fragment.splash;

import android.Manifest;
import android.os.Bundle;

import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(SplashVu.class)
public class SplashFragment extends BaseFragment<SplashContract.IView> implements SplashContract.Presenter , EasyPermissions.PermissionCallbacks{
    private static final int RC_LOCATION = 201;
    private Handler handler = new Handler();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMapLocation();
    }

    //初始化定位
    private void initMapLocation() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(_mActivity, perms)) {
            //有权限
            jumpToMain();
        } else {
            //没有权限
            EasyPermissions.requestPermissions(this, "请求获取卫星定位权限",
                    RC_LOCATION, perms);
        }
    }
    @Override
    public void onRefresh() {

    }

    private void jumpToMain(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                    lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);

                    getActivity().getWindow().setAttributes(lp);
                    getActivity().getWindow().setBackgroundDrawableResource(com.yufan.library.R.color.white);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().remove(SplashFragment.this).commit();
                }
            }
        }, 1000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == RC_LOCATION) {

        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        jumpToMain();
    }


}
