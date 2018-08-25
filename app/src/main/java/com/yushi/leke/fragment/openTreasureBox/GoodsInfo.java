package com.yushi.leke.fragment.openTreasureBox;

/**
 * 作者：Created by zhanyangyang on 2018/8/25 10:29
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class GoodsInfo {
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品单价
     */
    private String goodsPrice;
    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 客户端自己控制item选中状态
     */
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
