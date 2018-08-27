package com.yufan.library.api.config;

/**
 * Created by mengfantao on 18/8/24.
 */

public class ApiConfig {
    private String[] domains=new String []{"https://gitee.com/","https://gitee.com/","https://gitee.com/","https://gitee.com/","http://yapi.youximao.cn/mock/29/"};//域名
    private int apiType;//环境

    public ApiConfig(int apiType) {
        this.apiType = apiType;
    }

    public String getBaseUrl(){
       return domains[apiType];
    }
}
