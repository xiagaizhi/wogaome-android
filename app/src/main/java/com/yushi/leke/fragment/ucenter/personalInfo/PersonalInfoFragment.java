package com.yushi.leke.fragment.ucenter.personalInfo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.bean.LocationBean;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.util.CustomDisplayer;
import com.yufan.library.util.FileUtil;
import com.yufan.library.util.ImageUtil;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.AreaUtil;
import com.yufan.library.util.SoftInputUtil;


import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.yufan.library.inject.VuClass;
import com.yufan.library.util.StringUtil;
import com.yushi.leke.YFApi;
import com.yushi.leke.util.OSSClientUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import top.zibin.luban.OnCompressListener;

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
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x100:
                    String url = (String) msg.obj;
                    ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editavatar(url))
                            .useCache(false)
                            .enqueue(editBaseHttpCallBack);
                    getVu().updateHead(url);
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String avatar = "";
        String userName = "";
        String gender = "";
        String company = "";
        String position = "";
        String motto = "";
        String email = "";
        String city = "";
        String address = "";
        String uid = "";
        Bundle bundle = getArguments();
        if (bundle != null) {
            avatar = bundle.getString("avatar");
            userName = bundle.getString("userName");
            gender = bundle.getString("gender");
            company = bundle.getString("company");
            position = bundle.getString("position");
            motto = bundle.getString("motto");
            email = bundle.getString("email");
            city = bundle.getString("city");
            address = bundle.getString("address");
            uid = bundle.getString("uid");
        }
        if (TextUtils.isEmpty(city)) {
            city = "请选择城市";
        }
        if (TextUtils.equals("2", gender)) {
            gender = "女";
        } else {
            gender = "男";
        }
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
                }else {
                    hideSoftInput();
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
        getVu().updateHead(avatar);
        list.add(new PersonalItem("用户ID:", "" + uid, "", true, false));
        list.add(new PersonalItem("名字:", "" + userName, "请输入名字", false, true));
        list.add(new PersonalItem("性别:", "" + gender, "", false, false));
        list.add(new PersonalItem("公司:", "" + company, "请填写您的公司名称", false, true));
        list.add(new PersonalItem("职务:", "" + position, "您在公司的职务", false, true));
        list.add(new PersonalItem("一句话介绍:", "" + motto, "请输入简介", false, true));
        list.add(new PersonalItem("邮箱:", "" + email, "请填写邮箱", false, true));
        list.add(new PersonalItem("城市:", city, "请选择城市", false, false));
        list.add(new PersonalItem("详情地址:", "" + address, "请填写您的详细地址", false, true));
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        if (AreaUtil.getInstance().getOptions1Items().size() == 0 ||
                AreaUtil.getInstance().getOptions2Items().size() == 0 ||
                AreaUtil.getInstance().getOptions3Items().size() == 0) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    AreaUtil.getInstance().init(getContext());
                    return null;
                }
            }.execute();
        }
        genderList.add("男");
        genderList.add("女");
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
        DialogManager.getInstance().showLoadingDialog();
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editcity(personalItem.tabValue))
                .useCache(false)
                .enqueue(editBaseHttpCallBack);
    }

    @Override
    public void selectGender(String gender) {
        PersonalItem personalItem = (PersonalItem) list.get(2);
        personalItem.tabValue = gender;
        if (TextUtils.equals("女", gender)) {
            DialogManager.getInstance().showLoadingDialog();
            ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editgender("2"))
                    .useCache(false)
                    .enqueue(editBaseHttpCallBack);
        } else {
            DialogManager.getInstance().showLoadingDialog();
            ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editgender("1"))
                    .useCache(false)
                    .enqueue(editBaseHttpCallBack);
        }

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
                if (TextUtils.isEmpty(content)) {//名字为空不再上传服务器
//                    DialogManager.getInstance().toast("昵称不能为空！");
                } else {
                    DialogManager.getInstance().showLoadingDialog();
                    ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).edituserName(content))
                            .useCache(false)
                            .enqueue(editBaseHttpCallBack);
                }
            } else if (TextUtils.equals("公司:", currentTabName)) {
                DialogManager.getInstance().showLoadingDialog();
                ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editcompany(content))
                        .useCache(false)
                        .enqueue(editBaseHttpCallBack);
            } else if (TextUtils.equals("职务:", currentTabName)) {
                DialogManager.getInstance().showLoadingDialog();
                ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editposition(content))
                        .useCache(false)
                        .enqueue(editBaseHttpCallBack);
            } else if (TextUtils.equals("一句话介绍:", currentTabName)) {
                DialogManager.getInstance().showLoadingDialog();
                ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editmotto(content))
                        .useCache(false)
                        .enqueue(editBaseHttpCallBack);
            } else if (TextUtils.equals("邮箱:", currentTabName)) {
                DialogManager.getInstance().showLoadingDialog();
                ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editemail(content))
                        .useCache(false)
                        .enqueue(editBaseHttpCallBack);
            } else if (TextUtils.equals("详情地址:", currentTabName)) {
                DialogManager.getInstance().showLoadingDialog();
                ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).editaddress(content))
                        .useCache(false)
                        .enqueue(editBaseHttpCallBack);
            }
        }
    }

    private BaseHttpCallBack editBaseHttpCallBack = new BaseHttpCallBack() {
        @Override
        public void onSuccess(ApiBean mApiBean) {

        }

        @Override
        public void onError(int id, Exception e) {

        }

        @Override
        public void onFinish() {
            DialogManager.getInstance().dismiss();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isAll", false);
            setFragmentResult(RESULT_OK, bundle);
            DialogManager.getInstance().dismiss();
        }
    };


    @Override
    public void choosePhotos() {
        new ImagePicker()
                .pickType(ImagePickType.SINGLE)//设置选取类型(拍照、单选、多选)
                .maxNum(1)//设置最大选择数量(拍照和单选都是1，修改后也无效)
                .needCamera(true)//是否需要在界面中显示相机入口(类似微信)
                .cachePath(Global.SAVE_TAILORING_IMAGE_PATH)//自定义缓存路径
                .doCrop(1, 1, 300, 300)//裁剪功能需要调用这个方法，多选模式下无效
                .displayer(new CustomDisplayer())//自定义图片加载器，默认是Glide实现的,可自定义图片加载器
                .start(this, REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK && data != null) {
            List<ImageBean> resultList = data.getExtras().getParcelableArrayList(ImagePicker.INTENT_RESULT_DATA);
            Log.i("ImagePickerDemo", "选择的图片：" + resultList.toString());
            if (resultList != null && resultList.size() > 0) {
                ImageBean imageBean = resultList.get(0);
                String imagePath = imageBean.getImagePath();
                DialogManager.getInstance().showLoadingDialog();
                if (FileUtil.getFileSize(imagePath) > 300) {
                    ImageUtil.compressionImage(_mActivity, new File(imagePath), Global.SAVE_COMPRESSION_IMAGE_PATH, new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            //压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // 压缩成功后调用，返回压缩后的图片文件
                            String newImagepath = file.getAbsolutePath();
                            String imageName = ImageUtil.getPicNameFromPath(newImagepath);
                            uploadImage(imageName, newImagepath);
                        }

                        @Override
                        public void onError(Throwable e) {
                            //当压缩过程出现问题时调用
                            DialogManager.getInstance().dismiss();
                        }
                    });
                } else {
                    uploadImage(ImageUtil.getPicNameFromPath(imagePath), imagePath);
                }
            }
        }
    }

    private void uploadImage(String imageName, String imagePath) {
        OSSClientUtil.getInstance().uploadImgToOss(_mActivity, imageName, imagePath, new OSSClientUtil.UploadImageInterf() {
            @Override
            public void onSuccess(String url) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isAll", false);
                setFragmentResult(RESULT_OK, bundle);
                Message message = mHandler.obtainMessage();
                message.obj = url;
                message.what = 0x100;
                mHandler.sendMessage(message);
            }

            @Override
            public void onFail() {
                DialogManager.getInstance().dismiss();
            }
        });
    }
}
