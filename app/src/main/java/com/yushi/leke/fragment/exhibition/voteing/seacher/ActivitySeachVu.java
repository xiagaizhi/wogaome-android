package com.yushi.leke.fragment.exhibition.voteing.seacher;

import com.yushi.leke.R;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
public class ActivitySeachVu extends BaseListVu<ActivitySeachContract.Presenter> implements ActivitySeachContract.IView {
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
                mPersenter.onBackPressed();
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

                    iv_clear_search.setVisibility(View.INVISIBLE);
                }else {

                    iv_clear_search.setVisibility(View.VISIBLE);
                }
            }
        });
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return mPersenter.onKey(v,keyCode,event);
            }
        });
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        stateLayout.setErrorView(View.inflate(getContext(),R.layout.view_search_empty,null));
        super.initStatusLayout(stateLayout);
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
    public EditText getEditText() {
        return et_search;
    }
}
