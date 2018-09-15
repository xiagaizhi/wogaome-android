package com.yushi.leke.fragment.exhibition.voteing.allproject;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.yufan.library.util.AreaUtil;
import com.yushi.leke.R;
import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yushi.leke.fragment.exhibition.voteing.spinner.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

@FindLayout(layout = R.layout.xx_allproject_main)
@Title("参赛项目活动")
public class allprojectsVu extends BaseListVu<allprojectsContract.Presenter> implements allprojectsContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    private SpinerPopWindow<String> cityspinner;
    private SpinerPopWindow<String> workspinner;
    private List<String> list;
    private List<String> listwork;
    private TextView tv_choose_city, tv_choose_work;
    private List<String> firstlist = new ArrayList<>();
    private List<String> secendlist = new ArrayList<>();
    int index2=0,index1=0;
    @Override
    public void initView(View view) {
        super.initView(view);
        initData();
    }

    @Override
    public void showCityPickerView() {
        if (AreaUtil.getInstance().getOptions1Items().size() == 0 ||
                AreaUtil.getInstance().getOptions2Items().size() == 0
                || AreaUtil.getInstance().getOptions3Items().size() == 0) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    AreaUtil.getInstance().init(getContext());
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    toShowCityPickView();
                }
            }.execute();

        } else {
            toShowCityPickView();
        }

    }

    private void toShowCityPickView() {
        if (AreaUtil.getInstance().getOptions1Items().size() == 0 ||
                AreaUtil.getInstance().getOptions2Items().size() == 0
                || AreaUtil.getInstance().getOptions3Items().size() == 0) {return;}
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_choose_city.setText( mPersenter.selectedCityInfo(options1, options2));
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
                        index2=options2;
                        index1=options1;
                    }
                })
                .build();
        pvOptions.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {

            }
        });
        pvOptions.setPicker(AreaUtil.getInstance().getOptions1Items(), AreaUtil.getInstance().getOptions2Items());//三级选择器
        pvOptions.show();
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
                    showCityPickerView();
                    break;
                case R.id.tv_choose_work:
                    //workspinner.setWidth(tv_choose_work.getWidth());
                    //workspinner.showAsDropDown(tv_choose_work);
                    showCityPickerView();
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
