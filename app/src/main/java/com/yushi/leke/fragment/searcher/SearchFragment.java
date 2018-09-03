package com.yushi.leke.fragment.searcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.util.SoftInputUtil;
import com.yufan.library.widget.anim.AFVerticalAnimator;
import com.yushi.leke.fragment.home.SubscriptionInfo;
import com.yushi.leke.fragment.home.SubscriptionsViewBinder;

import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

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
        adapter.register(SubscriptionInfo.class,new SubscriptionsViewBinder());
        adapter.register(String.class,new SearchTabTitleViewBinder());
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
    public void onDestroyView() {
        SoftInputUtil.hideSoftInput(getActivity(),getView());
        super.onDestroyView();
    }

    @Override
    public void search() {
        list.add("音频");
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add("活动");
        list.add(new SearchActionInfo());
        list.add(new SearchActionInfo());
        list.add(new SearchActionInfo());
        list.add(new SearchActionInfo());
        list.add(new SearchActionInfo());
        list.add(new SearchActionInfo());
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        SoftInputUtil.hideSoftInput(getActivity(),getView());
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new AFVerticalAnimator();
    }
}
