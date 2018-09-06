package com.yushi.leke.fragment.searcher.audio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.Global;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.SoftInputUtil;
import com.yufan.library.widget.anim.AFVerticalAnimator;
import com.yushi.leke.fragment.home.SubscriptionInfo;
import com.yushi.leke.fragment.home.SubscriptionsViewBinder;
import com.yushi.leke.fragment.searcher.SearchActionInfo;
import com.yushi.leke.fragment.searcher.SearchActionViewBinder;
import com.yushi.leke.fragment.searcher.SearchBottomInfo;
import com.yushi.leke.fragment.searcher.SearchTabBottomViewBinder;
import com.yushi.leke.fragment.searcher.SearchTabTitleViewBinder;

import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(SearchAudioVu.class)
public class SearchAudioFragment extends BaseListFragment<SearchAudioContract.IView> implements SearchAudioContract.Presenter {
    private MultiTypeAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MultiTypeAdapter();
        adapter.register(SubscriptionInfo.class, new SubscriptionsViewBinder());
        adapter.register(String.class, new SearchTabTitleViewBinder());
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        Bundle bundle = getArguments();
        if (bundle != null) {
            vu.setTextKey(bundle.getString(Global.SEARCH_KEY));
            search(bundle.getString(Global.SEARCH_KEY));
        }
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
        SoftInputUtil.hideSoftInput(getActivity(), getView());
    }


    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new AFVerticalAnimator();
    }

    @Override
    public void search(String searchKey) {
        list.add("音频");
        list.add(new SubscriptionInfo(false, "http://oss.cyzone.cn/2018/0823/20180823043455198.jpg"));
        list.add(new SubscriptionInfo(false, "http://oss.cyzone.cn/2018/0824/20180824122615453.jpeg"));
        list.add(new SubscriptionInfo(true, "http://oss.cyzone.cn/2018/0823/20180823043455198.jpg"));
        list.add(new SubscriptionInfo(false, "http://oss.cyzone.cn/2018/0824/20180824122615453.jpeg"));
        list.add(new SubscriptionInfo(true, "http://oss.cyzone.cn/2018/0823/20180823043455198.jpg"));
        list.add(new SubscriptionInfo(false, "http://oss.cyzone.cn/2018/0823/20180823043455198.jpg"));
        list.add(new SubscriptionInfo(false, "http://oss.cyzone.cn/2018/0824/20180824122615453.jpeg"));
        list.add(new SubscriptionInfo(true, "http://oss.cyzone.cn/2018/0823/20180823043455198.jpg"));
        list.add(new SubscriptionInfo(false, "http://oss.cyzone.cn/2018/0824/20180824122615453.jpeg"));
        list.add(new SubscriptionInfo(true, "http://oss.cyzone.cn/2018/0823/20180823043455198.jpg"));

        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        SoftInputUtil.hideSoftInput(getActivity(), getView());
    }
}
