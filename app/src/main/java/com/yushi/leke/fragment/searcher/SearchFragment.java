package com.yushi.leke.fragment.searcher;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.yufan.library.Global;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.SoftInputUtil;
import com.yufan.library.widget.anim.AFVerticalAnimator;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.home.SubscriptionInfo;
import com.yushi.leke.fragment.home.SubscriptionsViewBinder;
import com.yushi.leke.fragment.searcher.activity.SearchActivityFragment;
import com.yushi.leke.fragment.searcher.audio.SearchAudioFragment;

import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(SearchVu.class)
public class SearchFragment extends BaseListFragment<SearchContract.IView> implements SearchContract.Presenter {
    private MultiTypeAdapter adapter;
    private Handler handler=new Handler();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        adapter.register(SearchActionInfo.class,new SearchActionViewBinder());
        adapter.register(SubscriptionInfo.class,new SubscriptionsViewBinder());
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
        list.add("音频");
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0823/20180823043455198.jpg"));
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0824/20180824122615453.jpeg"));
        list.add(new SubscriptionInfo(true,"http://oss.cyzone.cn/2018/0823/20180823043455198.jpg"));
        list.add(new SearchBottomInfo(true));
        list.add("活动");
        list.add(new SearchActionInfo(false,"http://oss.cyzone.cn/2018/0822/20180822015244231.png"));
        list.add(new SearchActionInfo(false,"http://oss.cyzone.cn/2018/0818/20180818024625113.jpg"));
        list.add(new SearchActionInfo(true,"http://oss.cyzone.cn/2018/0817/20180817095437396.jpg"));
        list.add(new SearchBottomInfo(false));
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
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
