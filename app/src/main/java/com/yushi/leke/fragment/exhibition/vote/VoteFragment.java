package com.yushi.leke.fragment.exhibition.vote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.manager.UserManager;
import com.yushi.leke.R;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.CommonDialog;
import com.yushi.leke.util.AliDotId;
import com.yushi.leke.util.ArgsUtil;
import com.yushi.leke.util.FormatImageUtil;
import com.yushi.leke.util.RechargeUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：Created by zhanyangyang on 2018/9/10 10:26
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class VoteFragment extends DialogFragment implements View.OnClickListener, RechargeUtil.CheckRechargePwdInterf {
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
    private VoteInitInfo voteInitInfo;
    private ICallBack mICallBack;
    private String projectId;


    public void setmICallBack(ICallBack mICallBack) {
        this.mICallBack = mICallBack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置样式
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);
        Bundle args = getArguments();
        projectId = args.getString(Global.BUNDLE_PROJECT_ID);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                    .vote(projectId))
                    .useCache(false).enqueue(new BaseHttpCallBack() {
                @Override
                public void onSuccess(ApiBean mApiBean) {
                    if (!TextUtils.isEmpty(mApiBean.getData())) {
                        voteInitInfo = JSON.parseObject(mApiBean.getData(), VoteInitInfo.class);
                        if (voteInitInfo != null) {
                            bindData(voteInitInfo);
                        }
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
    }

    private void bindData(VoteInitInfo voteInitInfo) {
        img_logo.setImageURI(FormatImageUtil.converImageUrl(voteInitInfo.getLogo(), 160, 160));
        tv_title.setText(voteInitInfo.getTitle());
        tv_area_industry.setText(voteInitInfo.getAddress() + "/" + voteInitInfo.getIndustry());
        tv_username.setText("创业者：" + voteInitInfo.getEntrepreneur());
        tv_vote_num.setText(String.valueOf(voteInitInfo.getVoteCount()) + "票");
        tv_lkc_num.setText(Html.fromHtml("我的LKC余额:<<font color='#FA5A5A'>" + voteInitInfo.getLkc() + "</font>(1LKC等于1票)"));
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
        et_lkc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s) || getCurrentChoiceVoteNum() == 0) {
                    et_lkc.setTextSize(17);
                    btn_vote.setEnabled(false);
                } else {
                    et_lkc.setTextSize(29);
                    btn_vote.setEnabled(true);
                    if (voteInitInfo != null) {
                        int temp = voteInitInfo.getLkc().setScale(0, BigDecimal.ROUND_DOWN).compareTo(new BigDecimal(getCurrentChoiceVoteNum()));
                        if (temp == -1) {//lkc币不足
                            if (voteInitInfo.getLkc().setScale(0, BigDecimal.ROUND_DOWN).compareTo(new BigDecimal(0)) == 0) {
                                et_lkc.setText("");
                                et_lkc.setTextSize(17);
                                btn_vote.setEnabled(false);
                            } else {
                                et_lkc.setText(String.valueOf(voteInitInfo.getLkc().setScale(0, BigDecimal.ROUND_DOWN)));
                            }

                            DialogManager.getInstance().toast("投票数不可大于LKC余额");
                        }
                    }

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
                    if (voteInitInfo != null && voteInitInfo.getIsHaveTradePwd() == 1) {//校验交易密码，通过后发起投票
                        RechargeUtil.getInstance().checkRechargePwd(getActivity(), "支付", this);
                    } else {
                        new CommonDialog(getContext())
                                .setTitle("您还未设置交易密码，请先设置您的交易密码")
                                .setPositiveName("去设置")
                                .setNegativeName("取消")
                                .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                                    @Override
                                    public void onClick(CommonDialog commonDialog, int actionType) {
                                        commonDialog.dismiss();
                                        if (actionType == CommonDialog.COMMONDIALOG_ACTION_POSITIVE) {
                                            if (mICallBack != null) {
                                                mICallBack.OnBackResult(1);
                                            }
                                            dismiss();
                                        }
                                    }
                                }).show();
                    }
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

    @Override
    public void returnCheckResult(boolean isSuccess, String token) {
        //发起投票
        if (TextUtils.equals("立即投票", btn_vote.getText().toString())) {
            DialogManager.getInstance().showLoadingDialog();
            ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).tradeLKCForVote(token, String.valueOf(getCurrentChoiceVoteNum()), projectId))
                    .useCache(false)
                    .enqueue(new BaseHttpCallBack() {
                        @Override
                        public void onSuccess(ApiBean mApiBean) {
                            //更新页面数据
                            voteSuccessToUpdateView();
                            if (mICallBack != null) {
                                mICallBack.OnBackResult(2, getCurrentChoiceVoteNum());
                            }
                            voteInitInfo.setLkc(voteInitInfo.getLkc().subtract(new BigDecimal(getCurrentChoiceVoteNum())));
                            voteInitInfo.setVoteCount(voteInitInfo.getVoteCount().add(new BigDecimal(getCurrentChoiceVoteNum())));
                            bindData(voteInitInfo);
                            //投票数据埋点
                            Map<String, String> params = new HashMap<>();
                            params.put("uid", UserManager.getInstance().getUid());
                            params.put("projectId", projectId);
                            ArgsUtil.getInstance().datapoint(AliDotId.id_0900, params);
                        }

                        @Override
                        public void onError(int id, Exception e) {

                        }

                        @Override
                        public void onFinish() {
                            DialogManager.getInstance().dismiss();
                        }
                    });
        } else {
            resetView();
        }
    }
}
