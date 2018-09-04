package com.yushi.leke.fragment.ucenter.personalInfo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.bean.LocationBean;
import com.yufan.library.util.ImageUtil;
import com.yufan.library.view.CustomGlideEngine;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.AreaUtil;
import com.yufan.library.util.SoftInputUtil;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.R;
import com.yushi.leke.YFApi;
import com.yushi.leke.util.OSSClientUtil;
import com.yushi.leke.util.StringUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

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
    private String currentTabName;
    private static final int REQUEST_CODE_CHOOSE = 0x100;
    private EditText currentEdit;

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
                currentEdit = (EditText) s[1];
                getVu().setCurrentInputBox(currentEdit);
                boolean showSoftInput = (boolean) s[2];
                if (showSoftInput) {
                    SoftInputUtil.showOpenKeybord(currentEdit, _mActivity);
                }
                currentTabName = item.tabName;
                if (personalItem == null || !personalItem.tabName.equals(item.tabName)) {
                    upTab((PersonalItem) s[0]);
                }
                personalItem = (PersonalItem) s[0];
                if (TextUtils.equals("用户ID:", item.tabName)) {
                    StringUtil.copyTextToBoard(personalItem.tabValue);
                } else if (TextUtils.equals("性别:", item.tabName)) {
                    getVu().showGenderPickerView(genderList);
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
        updateInfo("", "", "", "", "", "", personalItem.tabValue, "", "");
    }

    @Override
    public void selectGender(String gender) {
        PersonalItem personalItem = (PersonalItem) list.get(2);
        personalItem.tabValue = gender;
        updateInfo("", "", "", "", "", "", "", "", gender);
    }

    @Override
    public void tohideSoftInput() {
        hideSoftInput();
    }

    @Override
    public void toSubmitData(String content) {
        if (!TextUtils.isEmpty(currentTabName)) {
            for (int i = 0; i < list.size(); i++) {
                PersonalItem temp = (PersonalItem) list.get(i);
                if (TextUtils.equals(currentTabName, temp.tabName)) {
                    temp.tabValue = content;
                }
            }
            /**
             * 提交数据
             */
            if (TextUtils.equals("名字:", currentTabName)) {
                updateInfo("", content, "", "", "", "", "", "", "");
            } else if (TextUtils.equals("公司:", currentTabName)) {
                updateInfo("", "", content, "", "", "", "", "", "");
            } else if (TextUtils.equals("职务:", currentTabName)) {
                updateInfo("", "", "", content, "", "", "", "", "");
            } else if (TextUtils.equals("一句话介绍:", currentTabName)) {
                updateInfo("", "", "", "", content, "", "", "", "");
            } else if (TextUtils.equals("邮箱:", currentTabName)) {
                updateInfo("", "", "", "", "", content, "", "", "");
            } else if (TextUtils.equals("详情地址:", currentTabName)) {
                updateInfo("", "", "", "", "", "", "", content, "");
            }
        }
    }

    private void updateInfo(String avatar, String userName, String company,
                            String position, String motto, String email,
                            String city, String adress, String gender) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editMyBaseInfo(avatar, userName, company,
                position, motto, email, city, adress, gender))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {

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
    public void choosePhotos() {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .theme(R.style.Matisse_Zhihu)//主题，夜间模式R.style.Matisse_Dracula
                .countable(true)//是否显示选中数字
                .capture(true)//是否提供拍照功能
                .captureStrategy(new CaptureStrategy(false, "com.yushi.leke.fileprovider"))//存储地址
                .maxSelectable(1)//最大选择数
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))//筛选条件
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.px300))//图片大小
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//屏幕方向
                .thumbnailScale(0.85f)//缩放比例
                .imageEngine(new CustomGlideEngine())//图片加载方式
                .spanCount(50)
                .forResult(REQUEST_CODE_CHOOSE);//请求码
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uriList = Matisse.obtainResult(data);
            uriList.toString();
            uriList.get(0);
            String imagePath = ImageUtil.getRealPathFromUri(_mActivity, uriList.get(0));
            String imageName = ImageUtil.getPicNameFromPath(imagePath);
            Log.e("PersonalInfoFragment", imagePath);
            Log.e("PersonalInfoFragment", imageName);
            OSSClientUtil.getInstance().uploadImgToOss(_mActivity, imageName, imagePath, new OSSClientUtil.UploadImageInterf() {
                @Override
                public void onSuccess(String url) {

                }

                @Override
                public void onFail() {

                }
            });
        }
    }
}
