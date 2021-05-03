/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.GoodsComment;
import ltd.newbee.mall.entity.GoodsIdCatId;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.Sale;
import ltd.newbee.mall.entity.StockNumDTO;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NewBeeMallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(NewBeeMallGoods record);

    int insertSelective(NewBeeMallGoods record);

    NewBeeMallGoods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(NewBeeMallGoods record);

    int updateByPrimaryKeyWithBLOBs(NewBeeMallGoods record);

    int updateByPrimaryKey(NewBeeMallGoods record);

    List<NewBeeMallGoods> findNewBeeMallGoodsList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoods(PageQueryUtil pageUtil);

    List<NewBeeMallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<NewBeeMallGoods> findNewBeeMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("newBeeMallGoodsList") List<NewBeeMallGoods> newBeeMallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds") Long[] orderIds, @Param("sellStatus") int sellStatus);

    List<NewBeeMallGoods> findNewBeeMallGoodsListBySearchCat(PageQueryUtil pageUtil);

    List<NewBeeMallGoods> findNewBeeSearchBySecondLevelCat(PageQueryUtil pageUtil);

    List<NewBeeMallGoods> findNewBeeMallGoodsListByGoodsCategoryId(Long categoryId);

    List<NewBeeMallGoods> findNewBeeMallGoodsListByGoodsId(Long goodsId);

    int updateGoods(GoodsIdCatId record);

    List<GoodsComment> selectById(Long goodsId);

    int insertComment(GoodsComment record);
    
    List<NewBeeMallGoods> searchSecondLevel(PageQueryUtil pageUtil);
    
    List<NewBeeMallGoods> selectByCategoryId(List<Long> categoryIds);
    
//    2021/04/15 added by sasaki for categorySearch
    int getTotalNewBeeMallGoodsByCategory(PageQueryUtil pageUtil);
    
    //2021/04/16 added by sasaki for sale
    int insertSale(GoodsSale record);
    
  //2021/04/16 added by sasaki for sale
    int deleteById(Long saleId);
    
  //2021/04/17 added by sasaki for sale
    List<GoodsSale> selectBySaleId(Long saleId);
    
  //2021/04/21 added by sasaki for sale
    List<GoodsSale> selectByGoodsId(Long goodsId);
    
  //2021/04/29 added by sasaki for sale
    List<GoodsSale> selectByCategoryIdForSaleGoods(Long goodsCategoryId);
    
  //2021/04/17 added by sasaki for sale
    int updateSaleBySaleId(GoodsSale record);
    
  //2021/04/21 added by sasaki for sale
    int insertSaleList(Sale record);
    
  //2021/04/21 added by sasaki for sale
    int deleteSaleListById(Long saleId);
    
  //2021/04/21 added by sasaki for sale
    int updateSaleListBySaleId(Sale record);
    
  //2021/04/21 added by sasaki for sale
    List<Sale> selectSaleListBySaleId(Long saleId);
}