package com.yushi.leke.fragment.searcher.audio;

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
import com.yushi.leke.fragment.album.AlbumDetailFragment;
import com.yushi.leke.fragment.album.audioList.MediaBrowserFragment;
import com.yushi.leke.fragment.home.bean.AudioInfo;
import com.yushi.leke.fragment.home.bean.Homeinfo;
import com.yushi.leke.fragment.home.binder.SubscriptionsViewBinder;
import com.yushi.leke.fragment.searcher.SearchTabTitleViewBinder;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
        adapter.register(Homeinfo.class, new SubscriptionsViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                Homeinfo homeinfo= (Homeinfo) s[0];
                getRootFragment().start(UIHelper.creat(AlbumDetailFragment.class)
                        .put(Global.BUNDLE_KEY_ALBUMID,homeinfo.getAlbumId())
                        .build());
            }
        }));
        adapter.register(String.class, new SearchTabTitleViewBinder());
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        Bundle bundle = getArguments();
        if (bundle != null) {
            vu.getEditText().setText(bundle.getString(Global.SEARCH_KEY));
            search(bundle.getString(Global.SEARCH_KEY));
        }
    }


    @Override
    public void onLoadMore(final int index) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .audioSearch(getVu().getEditText().getText().toString(),index))
                .enqueue(new YFListHttpCallBack(vu) {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                super.onSuccess(mApiBean);
                JSONObject jsonObject= JSON.parseObject(mApiBean.data);
                List<Homeinfo> audioInfos= JSON.parseArray(jsonObject.getString("list"),Homeinfo.class);
                list.addAll(audioInfos);
                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
            }

        });
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
        //默认从第一页开始加载
        ApiManager.getCall(ApiManager.getInstance()
                .create(YFApi.class)
                .audioSearch(getVu().getEditText().getText().toString(),1)).enqueue(new YFListHttpCallBack(vu) {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                super.onSuccess(mApiBean);
                JSONObject jsonObject= JSON.parseObject(mApiBean.data);
                List<Homeinfo> audioInfos= JSON.parseArray(jsonObject.getString("list"),Homeinfo.class);
                list.clear();
                list.add("音频");
                list.addAll(audioInfos);
                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
            }

        });

        SoftInputUtil.hideSoftInput(getActivity(), getView());
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            //先隐藏键盘
            ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            search(vu.getEditText().getText().toString());
        }
        return false;
    }

}
