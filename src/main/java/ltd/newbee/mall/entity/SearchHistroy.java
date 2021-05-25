/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.entity;

import java.util.Date;

public class SearchHistroy {
   private Long id;
   
   private Long userId;
   
   private String keyword;
   
   private Date date;



public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getUserId() {
	return userId;
}

public void setUserId(Long userId) {
	this.userId = userId;
}

public String getKeyword() {
	return keyword;
}

public void setKeyword(String keyword) {
	this.keyword = keyword;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

@Override
public String toString() {
	return "SearchHistroy [id=" + id + ", userId=" + userId + ", keyword=" + keyword + ", date=" + date + "]";
}


   
   
}