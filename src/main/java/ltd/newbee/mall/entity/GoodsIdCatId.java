/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.entity;

import java.util.List;

public class GoodsIdCatId {
    private Long categoryId;

    private List<Long> goodsArray;

    private Long goodsId;

    private String goodsName;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getGoodsArray() {
        return goodsArray;
    }

    public void setGoodsArray(List<Long> goodsArray) {
        this.goodsArray = goodsArray;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String toString() {
        return "GoodsIdCatId{" +
                "categoryId=" + categoryId +
                ", goodsArray=" + goodsArray +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                '}';
    }
}