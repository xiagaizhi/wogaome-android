package com.yushi.leke.fragment.album;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.activity.MusicPlayerActivity;
import com.yushi.leke.fragment.album.audioList.MediaBrowserFragment;
import com.yushi.leke.fragment.searcher.SearchFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(AlbumDetailVu.class)
public class AlbumDetailFragment extends BaseFragment<AlbumDetailContract.IView> implements AlbumDetailContract.Presenter {
    private SupportFragment[] fragments=new SupportFragment[2];

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments[0] = UIHelper.creat(MediaBrowserFragment.class).build();
        fragments[1] = UIHelper.creat(SearchFragment.class).build();
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
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onMusicMenuClick() {
        Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
        startActivity(intent);
    }
}
