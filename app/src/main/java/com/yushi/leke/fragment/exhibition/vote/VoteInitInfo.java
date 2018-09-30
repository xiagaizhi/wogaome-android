package com.yushi.leke.fragment.exhibition.vote;


import java.math.BigDecimal;

/**
 * 作者：Created by zhanyangyang on 2018/9/12 13:18
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class VoteInitInfo {
    private String logo;
    private String title;
    private String address;
    private String industry;
    private BigDecimal voteCount = new BigDecimal(0);
    private String entrepreneur;
    private BigDecimal lkc;
    private int isHaveTradePwd;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public BigDecimal getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(BigDecimal voteCount) {
        this.voteCount = voteCount;
    }

    public String getEntrepreneur() {
        return entrepreneur;
    }

    public void setEntrepreneur(String entrepreneur) {
        this.entrepreneur = entrepreneur;
    }

    public BigDecimal getLkc() {
        return lkc;
    }

    public void setLkc(BigDecimal lkc) {
        this.lkc = lkc;
    }

    public int getIsHaveTradePwd() {
        return isHaveTradePwd;
    }

    public void setIsHaveTradePwd(int isHaveTradePwd) {
        this.isHaveTradePwd = isHaveTradePwd;
    }
}
