/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.controller.vo.SaleIndexVO;
import ltd.newbee.mall.entity.CampaignSet;
import ltd.newbee.mall.entity.CategorySale;
import ltd.newbee.mall.entity.GoodsComment;
import ltd.newbee.mall.entity.GoodsCoupon;
import ltd.newbee.mall.entity.GoodsIdCatId;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.Sale;
import ltd.newbee.mall.entity.SearchHistroy;
import ltd.newbee.mall.entity.StockNumDTO;
import ltd.newbee.mall.entity.TbGoods;
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
    
//    //2021/04/16 added by sasaki for sale
//    int insertSale(GoodsSale record);
//    
//  //2021/04/16 added by sasaki for sale
//    int deleteById(Long saleId);
//    
//  //2021/04/17 added by sasaki for sale
//    List<GoodsSale> selectBySaleId(Long saleId);
//    
//  //2021/04/21 added by sasaki for sale
//    List<GoodsSale> selectByGoodsId(Long goodsId);
//    
//  //2021/04/29 added by sasaki for sale
//    List<GoodsSale> selectByCategoryIdForSaleGoods(Long goodsCategoryId);
//    
//  //2021/04/17 added by sasaki for sale
//    int updateSaleBySaleId(GoodsSale record);
//    
//  //2021/04/21 added by sasaki for sale
//    int insertSaleList(Sale record);
//    
//  //2021/04/21 added by sasaki for sale
//    int deleteSaleListById(Long saleId);
//    
//  //2021/04/21 added by sasaki for sale
//    int updateSaleListBySaleId(Sale record);
//    
//  //2021/04/21 added by sasaki for sale
//    List<Sale> selectSaleListBySaleId(Long saleId);
//    
//  //2021/04/21 added by sasaki for sale
//    List<NewBeeMallGoods> findGoodsByName(String keyword);
    
  //2021/05/08 added by sasaki for searchHistroy
    int insertHistroy(SearchHistroy searchHistroy);
    
  //2021/05/08 added by sasaki for searchHistroy
    List<SearchHistroy> selectHistroy(Long userId);
    
//   // 2021/05/09 added by sasaki for あいまい検索
//    List<NewBeeMallGoods> findHitGoodsList(PageQueryUtil pageUtil);
    
  //2021/05/13 added by sasaki for sale
    int insertSale(Sale sale);
    
  //2021/05/13 added by sasaki for sale
    List<Sale> findSale(Long id);
    
  //2021/05/13 added by sasaki for sale
   int insertCategorySale(CategorySale categorySale);
    
  //2021/05/13 added by sasaki for sale
    List<CategorySale> findCategorySale(Long id);
    
  //2021/05/13 added by sasaki for sale
    int insertGoodsSale(GoodsSale goodsSale);
    
  //2021/05/13 added by sasaki for sale
    List<GoodsSale> findGoodsSale(Long id);
    
  //2021/05/13 added by sasaki for sale
    List<GoodsCoupon> insertGoodsCoupon(List<GoodsCoupon> goodsCoupon);
    
  //2021/05/13 added by sasaki for sale
    List<GoodsCoupon> findGoodsCoupon(Long couponId);
    
  //2021/05/15 added by sasaki for sale
    List<Sale> findSalesByLikeSearch(PageQueryUtil pageUtil);
    
  //2021/05/15 added by sasaki for sale
    int getTotalSalesBySearch(PageQueryUtil pageUtil);
    
  //2021/05/15 added by sasaki for sale
    List<Sale> findSalesById(Long id);
    
    //2021/05/19 added by sasaki for sale
    List<Sale> findSalesList(PageQueryUtil pageUtil);
    
  //2021/05/21 added by sasaki for sale
    List<Sale> selectBySaleIds(Integer[] ids);
    
  //2021/05/25 added by sasaki for sale sort
    List<Sale> findSalesBySort(String orderBy,String ascOrDesc);
    
  //2021/05/26 added by sasaki for sale insert
    Long  insertSaleId();
    
  //2021/05/27 added by sasaki for goods sale name
    List<GoodsSale>  findGoodsSaleName();
    
  //2021/05/28 added by sasaki for goods sale category
    List<CategorySale>  findCategorySaleByIds(List<Long> categoryId);
    
  //2021/05/29 added by sasaki for goods sale category
    List<CategorySale>  findCategorySaleById(Long categoryId);
    
  //2021/05/28 added by sasaki for goods sale goods
    List<TbGoods>  findSaleGoodsByIds(List<Long> goodsId);
    
  //2021/05/29 added by sasaki for goods sale goods
    List<TbGoods>  findSaleGoodsById(Long goodsId);
    
  //2021/05/28 added by sasaki for goods sale 
    List<GoodsSale>  findGoodsSaleById(List<Long> id);
    
  //2021/05/30 added by sasaki for goods sale 
    List<SaleIndexVO>  selectCategoryIdJoin();
    
  //2021/05/30 added by sasaki for goods sale 
    List<GoodsSale>  selectAllSale();
    
  //2021/05/30 added by sasaki for sale deleta category
  int deleteCategoryByCategoryId(Long categoryId);
  
//2021/06/02 added by sasaki for campaign set
  int insertCampaignSet(CampaignSet campaignSet);
}