package com.yushi.leke.fragment.searcher.activity;

import com.yushi.leke.R;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_search)
@Title("搜索活动")
public class SearchActivityVu extends BaseListVu<SearchActivityContract.Presenter> implements SearchActivityContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    @FindView(R.id.tv_search)
    private TextView tv_search;
    @FindView(R.id.iv_clear_search)
    private ImageView iv_clear_search;
    @FindView(R.id.et_search)
    private EditText et_search;


    @Override
    public void initView(View view) {
        super.initView(view);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("搜索".equals(tv_search.getText().toString())){

                    mPersenter.search(et_search.getText().toString());
                }else if("取消".equals(tv_search.getText().toString())){
                    mPersenter.onBackPressed();
                }
            }
        });
        iv_clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    tv_search.setText("取消");
                    iv_clear_search.setVisibility(View.INVISIBLE);
                }else {
                    tv_search.setText("搜索");
                    iv_clear_search.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        stateLayout.setEmptyView(View.inflate(getContext(),R.layout.view_search_empty,null));
        stateLayout.setErrorView(null);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return false;
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }

    @Override
    public void setTextKey(String textKey) {
        et_search.setText(textKey);
    }
}
