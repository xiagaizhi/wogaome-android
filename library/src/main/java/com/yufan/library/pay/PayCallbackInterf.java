package com.yufan.library.pay;

/**
 * 作者：Created by zhanyangyang on 2018/8/28 14:28
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public interface PayCallbackInterf {
    /**
     * 成功回调
     **/
    void onSuccess();

    /**
     * 失败回调
     **/
    void onFail(String message);

}
