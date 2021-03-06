/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service;

 
import ltd.newbee.mall.entity.CampaignSet;
import ltd.newbee.mall.entity.CategorySale;
import ltd.newbee.mall.entity.GoodsComment;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.Sale;
import ltd.newbee.mall.entity.SaleIndexCategory;
import ltd.newbee.mall.entity.SearchHistroy;
import ltd.newbee.mall.entity.GoodsImg;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import java.util.List;
import java.util.Map;

public interface NewBeeMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveNewBeeMallGoods(NewBeeMallGoods goods);

    /**
     * 批量新增商品数据
     *
     * @param newBeeMallGoodsList
     * @return
     */
    void batchSaveNewBeeMallGoods(List<NewBeeMallGoods> newBeeMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateNewBeeMallGoods(NewBeeMallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    NewBeeMallGoods getNewBeeMallGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids, int sellStatus);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchNewBeeMallGoodsCat(PageQueryUtil pageUtil);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */

    PageResult searchBySecondLevelCat(PageQueryUtil pageUtil);

    /**
     * 图片搜索
     *
     * @param goodsId
     * @return
     */
    Map searchGoodsImg(Long goodsId);
    /*    *//**
     * 大图片搜索
     *
     * @param goodsId
     * @return
     *//*
    PageResult searchGoodsBigImg(Long goodsId);*/

    /**
     * 增加图片
     *
     * @param goodsid
     * @return
     */


    int ImgInsertA(GoodsImg goodsid);

//    2021/04/05 added by sasaki for comment
    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    String saveComment(GoodsComment comment);

//   2021/04/05 added by sasaki for comment
    /**
     * 获取评论
     *
     * @param goodsId
     * @return
     */
    List<GoodsComment> getCommentById(Long goodsId);
    
    /**
     * 商品搜索 cell phone
     *
     * @param pageUtil
     * @return
     */
    PageResult searchSecondLevel(PageQueryUtil pageUtil);
    
//    //2021/04/19 added by sasaki for sale
//    /**
//     * 折扣商品搜索
//     *
//     * @param pageUtil
//     * @return
//     */
//    List<GoodsSaleVO> searchSaleGoods(Long saleId);
    
//  //2021/04/21 added by sasaki for sale
//    /**
//     * 折扣商品搜索
//     *
//     * @param pageUtil
//     * @return
//     */
//    List<GoodsSale> searchSaleGoods(Long saleId);
//    
//    //2021/04/21 added by sasaki for sale
//    /**
//     * 添加折扣商品
//     *
//     * @param goods
//     * @return
//     */
//    String saveSalesGoods(GoodsSale goods);
//    
//  //2021/04/21 added by sasaki for sale
//    Boolean deleteSaleGoods(Long goodsId);
//    
//  //2021/04/21 added by sasaki for sale
//    /**
//     * 修改打折商品信息
//     *
//     * @param goods
//     * @return
//     */
//    String updateSaleGoods(GoodsSale goods);
//    
//  //2021/04/21 added by sasaki for sale
//    /**
//     * 判斷是否打折
//     *
//     * @param goodsId
//     * @return
//     */
//    Boolean isSale(Long goodsId);
//
//    //2021/04/21 added by sasaki for sale
//    Boolean deleteSale(Long saleId);
//    
//  //2021/04/21 added by sasaki for sale
//    /**
//     * 修改打折キャンペーン信息
//     *
//     * @param sale
//     * @return
//     */
//    String updateSale(Sale sale);
//    
//  //2021/04/21 added by sasaki for sale
//    /**
//     * 添加折扣キャンペーン
//     *
//     * @param sale
//     * @return
//     */
//    String saveSales(Sale sale);
//    
//    //2021/04/21 added by sasaki for sale
//    /**
//     * 折扣キャンペーン搜索
//     *
//     * @param saleId
//     * @return
//     */
//    List<Sale> searchSale(Long saleId);
//    
//  //2021/04/30 added by sasaki for sale
//    /**
//     * 折扣價格
//     *
//     * @param goodsId
//     * @return
//     */
//    int getSalePriceById(Long goodsId);
//    
//    //2021/04/30 added by sasaki for sale
//    /**
//     * 折扣價格
//     *
//     * @param goodsCategoryId
//     * @return
//     */
//    List<Integer> getSalePriceByCategoryId(Long goodsCategoryId);
//    
//  //2021/04/30 added by sasaki for sale
//    /**
//     * 查詢折扣商品
//     *
//     * @param goodsCategoryId
//     * @return
//     */
//    List<GoodsSale> getSaleGoodsByCategoryId(Long goodsCategoryId);
    
//  //2021/05/07 added by sasaki for searchHistroy
//    /**
//     * 搜索欄提示
//     *
//     * @param pageUtil
//     * @return
//     */
//    List<String> getGoodsNameForSearch(String keyword);
    
  //2021/05/08 added by sasaki for searchHistroy
    /**
     * 插入搜索欄歷史
     *
     * @param searchHistroy
     * @return
     */
    String saveSearchHistroy(SearchHistroy searchHistroy);
    
//  //2021/05/08 added by sasaki for searchHistroy
//    /**
//     * 插入搜索欄歷史
//     *
//     * @param userId
//     * @return
//     */
//    List<SearchHistroy> getSearchHistroy(Long userId);
    
//  //2021/05/08 added by sasaki for searchHistroy
//    /**
//     * あいまい検索
//     *
//     * @param 
//     * @return
//     */
//    PageResult getHitGoodsPage(PageQueryUtil pageUtil);
    
    //2021/05/13 added by sasaki for sale
    /**
     * 新增キャンペーン
     *
     * @param sale
     * @return
     */
    String saveSale(Sale sale);
    
  //2021/05/14 added by sasaki for download
    /**
     * 获取商品详情
     * 
     *
     * @param ids
     * @return
     */
    List<NewBeeMallGoods> getNewBeeMallGoodsByIds(List<Long> ids);
    
  //2021/05/15 added by sasaki for sale
    /**
     * sale
     * 
     *
     * @param
     * @return
     */
    PageResult getSalesByLikeSearch(PageQueryUtil pageUtil);
    
  //2021/05/15 added by sasaki for sale
    /**
     * sale
     * 
     *
     * @param
     * @return
     */
    List<Sale> getSalesById(Long id);
    
    /**
     * 
     *
     * @param pageUtil
     * @return
     */
    PageResult getSales(PageQueryUtil pageUtil);
    
    /**
     * 
     *
     * @param pageUtil
     * @return
     */
    PageResult goodsSalePagAndSort(PageQueryUtil pageUtil);

  //2021/05/21 added by sasaki for sale
    /**
     * 获取キャンペーン
     * 
     *
     * @param ids
     * @return
     */
    List<Sale> getSalesByIds(Integer[] ids);
    
  //2021/05/21 added by sasaki for sale download
    /**
     * download
     * 
     *
     * @param
     * @return
     */
    void fileWriter(List<Sale> list);
    
  //2021/05/25 added by sasaki for sale sort
    /**
     * sale
     * 
     *
     * @param
     * @return
     */
    List<Sale> getSalesBySort(String orderBy,String ascOrDesc);
    
  //2021/05/26 added by sasaki for sale insert
    /**
     * sale
     * 
     *
     * @param
     * @return
     */
    Long getGoodsSaleId();
    
    /**
     * 新增キャンペーン
     *
     * @param goodsSale
     * @return
     */
    String saveSaleGoods(GoodsSale goodsSale);
    
    /**
     * 查询折扣商品名字
     *
     * @param 
     * @return
     */
    List<GoodsSale> findGoodsSaleName();
    
    /**
     * 判斷是否在tb_category存在
     *
     * @param 
     * @return
     */
    Boolean booleanCategoryIds(List<Long> categoryId);
    
    /**
     * 判斷是否在tb_category存在
     *
     * @param 
     * @return
     */
    Boolean booleanCategoryId(Long categoryId);
    
    /**
     * 判斷是否在tb_goods存在
     *
     * @param 
     * @return
     */
    Boolean booleanGoodsIds(List<Long> goodsId);
    
    /**
     * 判斷是否在tb_goods存在
     *
     * @param 
     * @return
     */
    Boolean booleanGoodsId(Long goodsId);
    
    /**
     * 抽出キャンペーン
     *
     * @param categoryId
     * @return
     */
    List<GoodsSale> getSaleListByCategoryIds(List<Long> categoryId);
    
    /**
     * 抽出キャンペーン
     *
     * @param categoryId
     * @return
     */
    List<GoodsSale> getSaleListByCategoryId(Long categoryId);
    
    /**
     * 抽出キャンペーン
     *
     * @param goodsId
     * @return
     */
    List<GoodsSale> getSaleListByGoodsIds(List<Long> goodsId);
    
    /**
     * 抽出キャンペーン
     *
     * @param goodsId
     * @return
     */
    List<GoodsSale> getSaleListByGoodsId(Long goodsId);
    
    /**
     * 抽出キャンペーン
     *
     * @param goodsId
     * @return
     */
    List<GoodsSale> selectAllSale();
    
    /**
     * 刪除sale category
     *
     * @param categoryId
     * @return
     */
    Boolean deleteCategoryByCateId(Long categoryId);
    
    /**
     * 插入sale category
     *
     * @param 
     * @return
     */
    String saveSaleCategory(CategorySale categorySale);
    
    /**
     * 判斷是否有效時間内
     *
     * @param categoryId
     * @return
     */
    Boolean isAvailable(CategorySale categorySale);
    
    /**
     * 插入campaign set
     *
     * @param 
     * @return
     */
    String saveCampaignSet(CampaignSet campaignSet);
    
    /**
     * 抽取赠送商品
     *
     * @param goodsId
     * @return
     */
    List<NewBeeMallGoods>findGiftGoods(Long goodsId);
    
    /**
     * 抽取goodsSale
     *
     * @param id
     * @return
     */
    List<GoodsSale>getGoodsSaleById(Long id);
}
