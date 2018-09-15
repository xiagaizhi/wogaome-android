package com.yushi.leke.fragment.exhibition.voteing.allproject;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yushi.leke.R;
import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.exhibition.voteing.spinner.SpinerPopWindow;
import java.util.ArrayList;
import java.util.List;

@FindLayout(layout = R.layout.xx_allproject_main)
@Title("参赛项目活动")
public class AllprojectsVu extends BaseListVu<AllprojectsContract.Presenter> implements AllprojectsContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    private SpinerPopWindow<String> cityspinner;
    private SpinerPopWindow<String> workspinner;
    private List<String> list;
    private List<String> listwork;
    private TextView tv_choose_city, tv_choose_work;
    Industryinfolist industryinfolist;
    List<String>worklist=new ArrayList<>();
    @Override
    public void initView(View view) {
        super.initView(view);
        initData();
    }

    @Override
    public void showCityPickerView() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getindustrylist("1")).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    industryinfolist = JSON.parseObject(mApiBean.getData(), Industryinfolist.class);
                    for (int i = 0; i< industryinfolist.getIndustryList().size(); i++){
                        worklist.add(industryinfolist.getIndustryList().get(i).getIndustryName());
                    }
                    OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_choose_work.setText( worklist.get(options1));
                        }
                    }).setTitleText("城市选择")
                            .setDividerColor(Color.BLACK)
                            .setTextColorCenter(Color.BLACK)
                            .setContentTextSize(20)
                            .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                                @Override
                                public void onOptionsSelectChanged(int options1, int options2, int options3) {
                                    String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                                    Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .build();
                    pvOptions.setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(Object o) {

                        }
                    });
                    pvOptions.setPicker(worklist);//一级选择器
                    pvOptions.show();
                }
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
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }


    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return super.initTitle(appToolbar);
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add("北京");
        }
        listwork = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            listwork.add("金融行业");
        }
        tv_choose_city = (TextView) findViewById(R.id.tv_choose_city);
        tv_choose_city.setClickable(true);
        tv_choose_city.setOnClickListener(clickListener);
        tv_choose_work = (TextView) findViewById(R.id.tv_choose_work);
        tv_choose_work.setClickable(true);
        tv_choose_work.setOnClickListener(clickListener);
        cityspinner = new SpinerPopWindow<>(getContext(), list, itemClickListener);
        cityspinner.setOnDismissListener(dismissListener);
        workspinner = new SpinerPopWindow<>(getContext(), listwork, workitemClickListener);
        workspinner.setOnDismissListener(dismissListener);

    }

    /**
     * 给TextView右边设置图片
     *
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getContext().getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        tv_choose_city.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 显示PopupWindow
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_choose_city:
                    //cityspinner.setWidth(tv_choose_city.getWidth());
                    //cityspinner.showAsDropDown(tv_choose_city);
                    // setTextImage(R.drawable.icon_up);
                    break;
                case R.id.tv_choose_work:
                    //workspinner.setWidth(tv_choose_work.getWidth());
                    //workspinner.showAsDropDown(tv_choose_work);
                    showCityPickerView();
                    worklist.removeAll(worklist);
                    // setTextImage(R.drawable.icon_up);
                    break;
            }
        }
    };
    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            cityspinner.dismiss();
            tv_choose_city.setText(list.get(position));
            Toast.makeText(getContext(), "点击了:" + list.get(position), Toast.LENGTH_LONG).show();
        }
    };
    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener workitemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            workspinner.dismiss();
            tv_choose_work.setText(listwork.get(position));
            Toast.makeText(getContext(), "点击了:" + listwork.get(position), Toast.LENGTH_LONG).show();
        }
    };
    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            //setTextImage(R.drawable.icon_down);
        }
    };
}
