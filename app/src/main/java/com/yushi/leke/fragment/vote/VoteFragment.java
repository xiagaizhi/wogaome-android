package com.yushi.leke.fragment.vote;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.util.PxUtil;
import com.yushi.leke.R;

/**
 * 作者：Created by zhanyangyang on 2018/9/10 10:26
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class VoteFragment extends DialogFragment implements View.OnClickListener {
    private Button btn_vote;
    private SimpleDraweeView img_logo;
    private TextView tv_title;
    private TextView tv_area_industry;
    private TextView tv_username;
    private TextView tv_vote_num;
    private TextView tv_lkc_num;
    private EditText et_lkc;
    private LinearLayout ll_edit_lkc;
    private ImageView img_vote_success;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置样式
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置从底部弹出
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setAttributes(params);
        View view = inflater.inflate(R.layout.dialog_fragment_vote, container);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getDialog().setCanceledOnTouchOutside(true);

    }

    private void initView(View view) {
        view.findViewById(R.id.img_close).setOnClickListener(this);
        view.findViewById(R.id.view_vote_top).setOnClickListener(this);
        btn_vote = view.findViewById(R.id.btn_vote);
        btn_vote.setOnClickListener(this);
        img_logo = view.findViewById(R.id.img_logo);
        tv_title = view.findViewById(R.id.tv_title);
        tv_area_industry = view.findViewById(R.id.tv_area_industry);
        tv_username = view.findViewById(R.id.tv_username);
        tv_vote_num = view.findViewById(R.id.tv_vote_num);
        tv_lkc_num = view.findViewById(R.id.tv_lkc_num);
        view.findViewById(R.id.img_vote_subtract).setOnClickListener(this);
        et_lkc = view.findViewById(R.id.et_lkc);
        ll_edit_lkc = view.findViewById(R.id.ll_edit_lkc);
        img_vote_success = view.findViewById(R.id.img_vote_success);
        et_lkc.setTextSize(17);
        view.findViewById(R.id.img_vote_add).setOnClickListener(this);
        tv_lkc_num.setText(Html.fromHtml("我的LKC余额:<<font color='#FA5A5A'>" + "12344" + "</font>(1LKC等于1票)"));
        et_lkc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    et_lkc.setTextSize(17);
                    btn_vote.setEnabled(false);
                } else {
                    et_lkc.setTextSize(29);
                    btn_vote.setEnabled(true);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                dismiss();
                break;
            case R.id.view_vote_top:
                dismiss();
                break;
            case R.id.btn_vote:
                if (TextUtils.equals("立即投票", btn_vote.getText().toString())) {
                    DialogManager.getInstance().showLoadingDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            voteSuccessToUpdateView();
                            DialogManager.getInstance().dismiss();
                        }
                    }, 500);
                } else {
                    resetView();
                }
                break;
            case R.id.img_vote_subtract:
                if (getCurrentChoiceVoteNum() > 1) {
                    et_lkc.setText(String.valueOf(getCurrentChoiceVoteNum() - 1));
                } else {
                    et_lkc.setText("");
                }
                break;
            case R.id.img_vote_add:
                et_lkc.setText(String.valueOf(getCurrentChoiceVoteNum() + 1));
                break;
        }
    }

    private long getCurrentChoiceVoteNum() {
        long num;
        if (TextUtils.isEmpty(et_lkc.getText().toString())) {
            num = 0;
        } else {
            num = Long.parseLong(et_lkc.getText().toString());
        }
        return num;
    }


    private void voteSuccessToUpdateView() {
        ll_edit_lkc.setVisibility(View.GONE);
        img_vote_success.setVisibility(View.VISIBLE);
        btn_vote.setText("继续投票");
        btn_vote.setEnabled(true);
    }

    private void resetView() {
        ll_edit_lkc.setVisibility(View.VISIBLE);
        img_vote_success.setVisibility(View.GONE);
        btn_vote.setText("立即投票");
        et_lkc.setText("");
    }
}
