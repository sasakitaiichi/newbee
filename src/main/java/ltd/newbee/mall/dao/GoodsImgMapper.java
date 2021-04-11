/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.GoodsImg;


import java.util.List;

public interface GoodsImgMapper {

    int updateByPrimaryKey(GoodsImg record);

    int updateByPrimaryKeySelective(GoodsImg record);

    int insertSelective(GoodsImg record);

    int insert(GoodsImg record);


    int ImgInsert(GoodsImg record);

    List<GoodsImg> selectGoodsImg(Long goodsId);

    List<GoodsImg> selectGoodsBigImg(Long goodsId);

    List<GoodsImg> selectGoodsSmallImg(Long goodsId);

    List<String> selectGoodsImgBylimitFive(Long goodsId);

    List<GoodsImg> selectGoodsIdOrderby(GoodsImg orderby);


 /*   int deleteByPrimaryKey(Long categoryId);

    GoodsCategory selectByPrimaryKey(Long categoryId);

    List<GoodsCategory> selectByPrimaryKeyParentId(Long parentId);

    GoodsCategory selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel, @Param("categoryName") String categoryName);

    List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageUtil);

    int getTotalGoodsCategories(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);

    List<Long> selectCategoryId (PageQueryUtil pageUtil);//mapper.selectByCategoryId(parentId);*/
}

