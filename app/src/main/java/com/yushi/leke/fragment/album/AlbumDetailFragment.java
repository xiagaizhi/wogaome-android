package com.yushi.leke.fragment.album;

import android.content.Intent;
import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.manager.UserManager;
import com.yufan.share.ShareModel;
import com.yushi.leke.App;
import com.yufan.library.base.BaseFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.activity.MusicPlayerActivity;
import com.yushi.leke.fragment.album.audioList.MediaBrowserFragment;
import com.yushi.leke.fragment.album.detail.DetailFragment;
import com.yushi.leke.share.ShareMenuActivity;
import com.yushi.leke.util.AliDotId;
import com.yushi.leke.util.ArgsUtil;
import java.util.HashMap;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(AlbumDetailVu.class)
public class AlbumDetailFragment extends BaseFragment<AlbumDetailContract.IView> implements AlbumDetailContract.Presenter {
    private SupportFragment[] fragments = new SupportFragment[2];
    private String albumId;
    private String intro;
    AlbumDetailinfo infolist;
    ICallBack mICallBack;
    public void setmICallBack(ICallBack mICallBack) {
        this.mICallBack = mICallBack;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            albumId = bundle.getString(Global.BUNDLE_KEY_ALBUMID);
            intro = bundle.getString("intro");
        }
        fragments[0] = UIHelper.creat(MediaBrowserFragment.class).put(Global.BUNDLE_KEY_ALBUMID, albumId).build();
        DetailFragment detailFragment= (DetailFragment) UIHelper.creat(DetailFragment.class).build();
        fragments[1] =detailFragment;
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
                if (position == 0) {
                    return "课程内容";
                } else if (position == 1) {
                    return "专辑简介";
                }
                return "";

            }

        });
        getdata();
    }


    @Override
    public void onRefresh() {
        getdata();
        ((MediaBrowserFragment) fragments[0]).onRefresh();
    }

    @Override
    public void onMusicMenuClick() {
        Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
        startActivity(intent);
    }

    private void getdata() {
        //获取专辑数据
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .albumdetail(albumId))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            infolist = JSON.parseObject(mApiBean.getData(), AlbumDetailinfo.class);
                            mICallBack.OnBackResult(infolist);
                            getVu().showtext(infolist);
                            getstatedate();
                            getVu().getPTR().refreshComplete();
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

    private void getstatedate() {
        //获取订阅状态
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .substate(albumId))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            int state = Integer.parseInt(mApiBean.getData());
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
                        Toast.makeText(getContext(), "订阅成功", Toast.LENGTH_SHORT).show();
                        getstatedate();
                        //订阅专辑数据埋点
                        Map<String, String> params = new HashMap<>();
                        params.put("uid", UserManager.getInstance().getUid());
                        params.put("albumId", albumId);
                        ArgsUtil.getInstance().datapoint(AliDotId.id_0500, params);
                        //发送更新订阅数量广播
                        Intent intent = new Intent();
                        intent.putExtra("more", 1);
                        intent.setAction(Global.BROADCAST_ACTION_SUBCRIBE);
                        LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcast(intent);
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
                        Toast.makeText(getContext(), "取消订阅成功", Toast.LENGTH_SHORT).show();
                        getstatedate();
                        //发送更新订阅数量广播
                        Intent intent = new Intent();
                        intent.putExtra("more", -1);
                        intent.setAction(Global.BROADCAST_ACTION_SUBCRIBE);
                        LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcast(intent);
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
    public void onShareclick() {
        ShareModel model = new ShareModel();
        if (infolist != null && infolist.getAlbumDetailInfo() != null) {
            model.setTitle(infolist.getAlbumDetailInfo().getAlbumName());
            model.setContent(infolist.getAlbumDetailInfo().getCreatorInfo());
            model.setIcon(infolist.getAlbumDetailInfo().getHorizontalIcon());
            model.setTargetUrl(infolist.getAlbumDetailInfo().getShareIcon());
            model.setNeedCount(true);
            ShareMenuActivity.startShare(getRootFragment(), model);
            //专辑分享数据埋点
            Map<String, String> params = new HashMap<>();
            params.put("uid", UserManager.getInstance().getUid());
            params.put("albumId", albumId);
            ArgsUtil.getInstance().datapoint(AliDotId.id_0801, params);
        }

    }

}
