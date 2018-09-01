package com.yushi.leke.fragment.ucenter.personalInfo;

import android.os.AsyncTask;
import android.os.Bundle;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.bean.LocationBean;
import com.yufan.library.inject.FindView;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.AreaUtil;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.fragment.exhibition.ExhibitionInfo;
import com.yushi.leke.fragment.exhibition.ExhibitionTopInfo;
import com.yushi.leke.fragment.exhibition.ExhibitionTopViewBinder;
import com.yushi.leke.fragment.exhibition.ExhibitionViewBinder;
import com.yushi.leke.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(PersonalInfoVu.class)
public class PersonalInfoFragment extends BaseListFragment<PersonalInfoContract.IView> implements PersonalInfoContract.Presenter {
    private MultiTypeAdapter adapter;
    private List<PersonalItem> tempList = new ArrayList<>();
    private PersonalItem personalItem;
    private List<String> genderList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AreaUtil.getInstance().init(getContext());
                return null;
            }
        }.execute();
        genderList.add("男");
        genderList.add("女");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MultiTypeAdapter();
        adapter.register(PersonalItem.class, new PersonalInfoViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                PersonalItem item = (PersonalItem) s[0];
                if (personalItem == null || !personalItem.tabName.equals(item.tabName)) {
                    upTab((PersonalItem) s[0]);
                }
                personalItem = (PersonalItem) s[0];

                if (TextUtils.equals("用户ID:", item.tabName)) {
                    StringUtil.copyTextToBoard(personalItem.tabValue);
                } else if (TextUtils.equals("性别:", item.tabName)) {
                    getVu().showGenderPickerView(genderList);
                } else if (TextUtils.equals("", item.tabName)) {

                } else if (TextUtils.equals("", item.tabName)) {

                } else if (TextUtils.equals("", item.tabName)) {

                } else if (TextUtils.equals("城市:", item.tabName)) {
                    getVu().showCityPickerView();
                }
            }
        }, _mActivity));
        getVu().getRecyclerView().setAdapter(adapter);
        list.add(new PersonalItem("用户ID:", "123456", "", true, false));
        list.add(new PersonalItem("名字:", "", "请输入名字", false, true));
        list.add(new PersonalItem("性别:", "男", "", false, false));
        list.add(new PersonalItem("公司:", "", "请填写您的公司名称", false, true));
        list.add(new PersonalItem("职务:", "", "您在公司的职务", false, true));
        list.add(new PersonalItem("一句话介绍:", "", "请输入简介", false, true));
        list.add(new PersonalItem("邮箱:", "", "请填写邮箱", false, true));
        list.add(new PersonalItem("城市:", "请选择城市", "请选择城市", false, false));
        list.add(new PersonalItem("详情地址:", "", "请填写您的详细地址", false, true));
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void openPlayer() {

    }

    @Override
    public void resetTab() {
        for (int i = tempList.size() - 1; i >= 0; i--) {
            list.add(0, tempList.get(i));
        }
        getVu().getRecyclerView().getAdapter().notifyDataSetChanged();
        tempList.clear();
    }


    @Override
    public void upTab(PersonalItem personalItem) {
        if (personalItem != null) {
            for (int i = 0; i < list.size(); i++) {
                PersonalItem tempItem = (PersonalItem) list.get(i);
                if (!tempItem.equals(personalItem)) {
                    tempList.add(tempItem);
                } else {
                    break;
                }
            }
            list.removeAll(tempList);
            getVu().getRecyclerView().getAdapter().notifyDataSetChanged();
        }

    }

    @Override
    public void resetVu() {
        personalItem = null;
    }

    @Override
    public void selectedCityInfo(int options1, int options2, int options3) {
        LocationBean province = AreaUtil.getInstance().getOptions1Items().get(options1);
        LocationBean city = AreaUtil.getInstance().getOptions2Items().get(options1).get(options2);
        LocationBean county = AreaUtil.getInstance().getOptions3Items().get(options1).get(options2).get(options3);
        PersonalItem personalItem = (PersonalItem) list.get(7);
        personalItem.tabValue = province.getName() + city.getName() + county.getName();
    }

    @Override
    public void selectGender(String gender) {
        PersonalItem personalItem = (PersonalItem) list.get(2);
        personalItem.tabValue = gender;
    }
}
