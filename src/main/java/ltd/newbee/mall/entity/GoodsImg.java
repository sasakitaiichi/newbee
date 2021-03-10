/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class GoodsImg {
    private Long goodsId;

    private String img;

    private Byte imgStatus;

    private Byte orderby;


    public Long getGoodsId() {
        return goodsId;
    }

    public String getImg() {
        return img;
    }

    public Byte getImgStatus() {
        return imgStatus;
    }

    public Byte getOrderby() {
        return orderby;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public void setImgStatus(Byte imgStatus) {
        this.imgStatus = imgStatus;
    }

    public void setOrderby(Byte orderby) {
        this.orderby = orderby;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsId=").append(goodsId);
        sb.append(", img=").append(img);
        sb.append(", imgStatus=").append(imgStatus);
        sb.append(", orderby=").append(orderby);

        sb.append("]");
        return sb.toString();
    }

}