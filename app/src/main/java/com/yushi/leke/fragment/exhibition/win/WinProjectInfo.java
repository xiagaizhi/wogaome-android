package com.yushi.leke.fragment.exhibition.win;

import java.math.BigDecimal;

/**
 * 作者：Created by zhanyangyang on 2018/9/12 09:30
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class WinProjectInfo {
    private String logo;
    private String title;
    private String address;
    private String industry;
    private String entrepreneur;
    private BigDecimal haveVote;

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

    public String getEntrepreneur() {
        return entrepreneur;
    }

    public void setEntrepreneur(String entrepreneur) {
        this.entrepreneur = entrepreneur;
    }

    public BigDecimal getHaveVote() {
        return haveVote;
    }

    public void setHaveVote(BigDecimal haveVote) {
        this.haveVote = haveVote;
    }
}
