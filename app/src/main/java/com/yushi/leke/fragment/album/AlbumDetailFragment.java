package com.yushi.leke.fragment.album;

import android.content.Intent;
import android.net.Uri;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.activity.MusicPlayerActivity;
import com.yushi.leke.fragment.album.audioList.MediaBrowserFragment;
import com.yushi.leke.fragment.album.detail.DetailFragment;
import com.yushi.leke.fragment.album.detailforalbum.DetailforalbumFragment;
import com.yushi.leke.fragment.searcher.SearchFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(AlbumDetailVu.class)
public class AlbumDetailFragment extends BaseFragment<AlbumDetailContract.IView> implements AlbumDetailContract.Presenter {
    private SupportFragment[] fragments=new SupportFragment[2];
    private int albumId;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            albumId=bundle.getInt(Global.BUNDLE_KEY_ALBUMID);
        }
        fragments[0] = UIHelper.creat(MediaBrowserFragment.class).build();
        fragments[1] = UIHelper.creat(DetailforalbumFragment.class)
                .put(Global.BUNDLE_KEY_ALBUMID,albumId)
                .build();
                getVu().getViewPager().setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }


            @Override
            public CharSequence getPageTitle(int position) {
                if(position==0){
                    return "课程内容";
                }else if(position ==1){
                    return "专辑简介";
                }
                return "";

            }

        });
      getVu().getDraweeView().setImageURI("http://oss.cyzone.cn/2018/0913/efc0926cbb1b445240345aa343134958.jpg");
      getdata();
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onMusicMenuClick() {
        Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
        startActivity(intent);
    }
    private void getdata(){
        //获取专辑数据
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .albumdetail(String.valueOf(albumId)))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            AlbumDetailinfo infolist = JSON.parseObject(mApiBean.getData(), AlbumDetailinfo.class);
                            getVu().showtext(infolist);
                            getstatedate();
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
    private void getstatedate(){
        //获取订阅状态
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .substate(albumId))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            int state= Integer.parseInt(mApiBean.getData());
                            getVu().showsubstate(state);
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
    @Override
    public void register() {
        //订阅专辑
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .registeralbum(albumId))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        Toast.makeText(getContext(),"订阅成功",Toast.LENGTH_SHORT).show();
                        getstatedate();
                    }

                    @Override
                    public void onError(int id, Exception e) {
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }

    @Override
    public void unregister() {
        //取消订阅专辑
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .unregisteralbum(albumId))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        Toast.makeText(getContext(),"取消订阅成功",Toast.LENGTH_SHORT).show();
                        getstatedate();
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
