/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.vo;

import java.util.Date;

public class GoodsCommentVO {
    private Long userId;

    private String nickName;

    private Long goodsId;

    private String goodsName;

    private String goodsImg;

    private String goodsCommentTitle;

    private  String goodsComment;

    private Date createTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsCommentTitle() {
        return goodsCommentTitle;
    }

    public void setGoodsCommentTitle(String goodsCommentTitle) {
        this.goodsCommentTitle = goodsCommentTitle;
    }

    public String getGoodsComment() {
        return goodsComment;
    }

    public void setGoodsComment(String goodsComment) {
        this.goodsComment = goodsComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "GoodsComment{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsCommentTitle='" + goodsCommentTitle + '\'' +
                ", goodsComment='" + goodsComment + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}