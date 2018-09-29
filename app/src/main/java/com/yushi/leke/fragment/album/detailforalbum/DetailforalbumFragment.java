package com.yushi.leke.fragment.album.detailforalbum;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
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
    private String albumId;
    private String intro;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            albumId=bundle.getString(Global.BUNDLE_KEY_ALBUMID);
            intro=bundle.getString("intro");
        }
       if (intro!=null){
           getVu().showtext(Html.fromHtml(intro));
       }
    }


    @Override
    public void onRefresh() {

    }
}
