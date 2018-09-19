package com.yushi.leke.fragment.album.detailforalbum;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.album.AlbumDetailinfo;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(DetailforalbumVu.class)
public class DetailforalbumFragment extends BaseFragment<DetailforalbumContract.IView> implements DetailforalbumContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getdata();
    }


    @Override
    public void onRefresh() {

    }
    private void getdata(){
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .albumdetail("121"))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            AlbumDetailinfo infolist = JSON.parseObject(mApiBean.getData(), AlbumDetailinfo.class);
                            getVu().showtext(Html.fromHtml(infolist.getAlbum().getIntroduction()));
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
}
