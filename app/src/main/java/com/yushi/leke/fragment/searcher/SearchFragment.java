package com.yushi.leke.fragment.searcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.YFListHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.SoftInputUtil;
import com.yufan.library.widget.anim.AFVerticalAnimator;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.album.audioList.MediaBrowserFragment;
import com.yushi.leke.fragment.home.bean.AudioInfo;
import com.yushi.leke.fragment.home.bean.Homeinfo;
import com.yushi.leke.fragment.home.binder.SubscriptionsViewBinder;
import com.yushi.leke.fragment.searcher.activity.SearchActivityFragment;
import com.yushi.leke.fragment.searcher.audio.SearchAudioFragment;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(SearchVu.class)
public class SearchFragment extends BaseListFragment<SearchContract.IView> implements SearchContract.Presenter {
    private MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        adapter.register(SearchActionInfo.class,new SearchActionViewBinder());
        adapter.register(Homeinfo.class,new SubscriptionsViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                getRootFragment().start(UIHelper.creat(MediaBrowserFragment.class).build());
            }
        }));
        adapter.register(String.class,new SearchTabTitleViewBinder());
        adapter.register(SearchBottomInfo.class,new SearchTabBottomViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
             switch ((int)s[0]){
                 case SearchTabBottomViewBinder.SEARCH_MORE_AUDIO:
                    start(UIHelper.creat(SearchAudioFragment.class).put(Global.SEARCH_KEY,getVu().getSearchKey()).build());
                     break;
                 case SearchTabBottomViewBinder.SEARCH_MORE_ACTIVITY:
                     start(UIHelper.creat(SearchActivityFragment.class).put(Global.SEARCH_KEY,getVu().getSearchKey()).build());
                     break;
             }
            }
        }));
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onLoadMore(int index) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        SoftInputUtil.hideSoftInput(getActivity(),getView());
    }


    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new AFVerticalAnimator();
    }

    @Override
    public void search(String searchKey) {

        ApiManager.getCall( ApiManager.getInstance()
                .create(YFApi.class)
                .globalSearch(searchKey))
                .enqueue(new YFListHttpCallBack(vu) {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                super.onSuccess(mApiBean);
                Log.d("LOGH",mApiBean.getCode()+"\n"+mApiBean.getData()+"\n"+mApiBean.getMessage());
                JSONObject jsonObject= JSON.parseObject(mApiBean.data);
                int moreActivity=  jsonObject.getInteger("moreActivity");
                int moreAudio=jsonObject.getInteger("moreAudio");
                List<SearchActionInfo> searchActionInfos= JSON.parseArray(jsonObject.getString("activity"),SearchActionInfo.class);
                List<Homeinfo> audioInfos= JSON.parseArray(jsonObject.getString("audio"),Homeinfo.class);
                list.clear();
                list.add("音频");
                list.addAll(audioInfos);
                list.add(new SearchBottomInfo(moreAudio==1,"查看更多项目"));
                list.add("活动");
                list.addAll(searchActionInfos);
                list.add(new SearchBottomInfo(moreActivity==1,"查看更多活动"));
                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
            }


        });
        SoftInputUtil.hideSoftInput(getActivity(),getView());
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            //先隐藏键盘
            ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            search(vu.getSearchKey());
        }
        return false;
    }

}
