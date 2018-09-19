package com.yufan.library.api.config;

/**
 * Created by mengfantao on 18/8/24.
 */

public class ApiConfig {

    private String[] domains = new String[]{"http://app.leke-dev.com/", "http://app.leke-dev.com/", "http://app.test.leke.com/", "http://app.leke-dev.com/", "http://yapi.youximao.cn/mock/29/"};//域名
    private String[] webDomains = new String[]{"http://web.leke.com/", "http://web.leke.com/", "http://web.test.leke.com/", "http://web.leke-dev.com/", "http://web.leke-dev.com/"};//h5主域名
    private int apiType;//环境
    private final String  protocol="http://alifile.leke.com/public/protocol.html";


    public ApiConfig(int apiType) {
        this.apiType = apiType;

    }

    public String getBaseUrl() {
        return domains[apiType];
    }

    public String getProtocol() {
        return protocol;
    }

    /**
     * LKC明细
     */
    public String getLkcDetail() {
        return webDomains[apiType] + "#/myWallet/LKCDetail";
    }

    /**
     * LKC说明
     */
    public String getLKCInstruction() {
        return webDomains[apiType] + "#/myWallet/LKCInstruction";
    }

    /**
     * 我的会员
     */
    public String getMineVip() {
        return webDomains[apiType] + "#/myLeaguer";
    }

    /**
     * 我的等级
     */
    public String getMyGrades() {
        return webDomains[apiType] + "#/myGrade";
    }

    /**
     * 开宝箱明细
     */
    public String getTreasureDetail() {
        return webDomains[apiType] + "#/myWallet/treasureDetail";
    }

    /**
     * 我的算力
     */
    public String getMyPower() {
        return webDomains[apiType] + "#/myPower";
    }

    /**
     * 算力说明
     */
    public String getPowerInstruction() {
        return webDomains[apiType] + "#/myPower/powerInstruction";
    }

    /**
     * 我的路演
     */
    public String getMyRoadShow() {
        return webDomains[apiType] + "#/";
    }

    /**
     * 我的订阅
     */
    public String getMySubscribe() {
        return webDomains[apiType] + "#/";
    }

    /**
     * 我的投票
     */
    public String getMyVote() {
        return webDomains[apiType] + "#/";
    }

    /**
     * 分享好友
     */
    public String getFriendShare() {
        return webDomains[apiType] + "#/";
    }


    public String getTestH5(){
        return "http://web.leke.com/#/nickName";
    }

    /**
     * 活动详情页 未开始／报名中
     * @param activityId
     * @return
     */
    public String getExhibitionDetail(String activityId){
        return webDomains[apiType] + "#/";
    }


}
