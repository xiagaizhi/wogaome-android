package com.yufan.library.pay;

/**
 * 作者：Created by zhanyangyang on 2018/8/25 11:03
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class PayWay {
    private String tradeApiId;
    private String tradeApiDesc;
    private boolean isSelect;//客户端自己判断是否选中状态

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTradeApiId() {
        return tradeApiId;
    }

    public void setTradeApiId(String tradeApiId) {
        this.tradeApiId = tradeApiId;
    }

    public String getTradeApiDesc() {
        return tradeApiDesc;
    }

    public void setTradeApiDesc(String tradeApiDesc) {
        this.tradeApiDesc = tradeApiDesc;
    }
}
