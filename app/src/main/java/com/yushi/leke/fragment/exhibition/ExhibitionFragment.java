package com.yushi.leke.fragment.exhibition;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.activity.MusicPlayerActivity;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(ExhibitionVu.class)
public class ExhibitionFragment extends BaseListFragment<ExhibitionContract.IView> implements ExhibitionContract.Presenter {
    private MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        adapter.register(ExhibitionTopInfo.class,new ExhibitionTopViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                if((int)s[0]==ExhibitionTopViewBinder.MUSIC_EVENT){
                    onMusicMenuClick();
                }
            }
        }));
        adapter.register(ExhibitionInfo.class,new ExhibitionViewBinder());
        vu.getRecyclerView().setAdapter(adapter);
        list.add(new ExhibitionTopInfo());
        list.add(new ExhibitionInfo("http://oss.cyzone.cn/2018/0724/20180724094636938.png"));
        list.add(new ExhibitionInfo("http://oss.cyzone.cn/2018/0614/20180614093809181.jpg"));
        list.add(new ExhibitionInfo("http://oss.cyzone.cn/2018/0724/20180724094636938.png"));
        list.add(new ExhibitionInfo("http://oss.cyzone.cn/2018/0609/20180609064513960.png"));
        list.add(new ExhibitionInfo("http://oss.cyzone.cn/2018/0609/20180609064513960.png"));
        list.add(new ExhibitionInfo("http://oss.cyzone.cn/2018/0724/20180724094636938.png"));
        list.add(new ExhibitionInfo("http://oss.cyzone.cn/2018/0609/20180609064513960.png"));
        list.add(new ExhibitionInfo("http://oss.cyzone.cn/2018/0903/8a46c3cdd06dd931d05eebcdcb5ad8a9.png"));
        list.add(new ExhibitionInfo("http://oss.cyzone.cn/2018/0609/20180609064513960.png"));
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(int index) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getVu().getRecyclerView().getPTR().refreshComplete();
                getVu(). getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NONE);
            }
        },1000);
    }

    private Handler handler=new Handler();
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getVu().getRecyclerView().getPTR().refreshComplete();
                getVu(). getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NONE);
            }
        },1000);
    }

    @Override
    public void onMusicMenuClick() {
        Intent intent = new Intent(getContext(), MusicPlayerActivity.class);
        startActivity(intent);
    }
}
