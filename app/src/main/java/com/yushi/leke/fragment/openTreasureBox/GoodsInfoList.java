package com.yushi.leke.fragment.openTreasureBox;

import java.util.List;

/**
 * 作者：Created by zhanyangyang on 2018/8/25 10:31
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class GoodsInfoList {
    private int goodsType;
    private String treatureBoxDetailUrl;
    private List<GoodsInfo> goodsInfo;

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public List<GoodsInfo> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<GoodsInfo> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getTreatureBoxDetailUrl() {
        return treatureBoxDetailUrl;
    }

    public void setTreatureBoxDetailUrl(String treatureBoxDetailUrl) {
        this.treatureBoxDetailUrl = treatureBoxDetailUrl;
    }
}
