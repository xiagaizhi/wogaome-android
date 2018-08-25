package com.yufan.library.pay;

/**
 * 作者：Created by zhanyangyang on 2018/8/25 11:03
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class PayWay {
    private String payApiId;
    private String payApiDesc;
    private boolean isSelect;//客户端自己判断是否选中状态

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getPayApiId() {
        return payApiId;
    }

    public void setPayApiId(String payApiId) {
        this.payApiId = payApiId;
    }

    public String getPayApiDesc() {
        return payApiDesc;
    }

    public void setPayApiDesc(String payApiDesc) {
        this.payApiDesc = payApiDesc;
    }
}
