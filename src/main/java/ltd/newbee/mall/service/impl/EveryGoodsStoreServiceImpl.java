/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service.impl;


import ltd.newbee.mall.controller.vo.NewBeeMallSearchGoodsVO;
import ltd.newbee.mall.controller.vo.SearchPageCategoryVO;
import ltd.newbee.mall.dao.AdminUserMapper;
import ltd.newbee.mall.dao.GoodsCategoryMapper;
import ltd.newbee.mall.dao.GoodsImgMapper;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.GoodsImg;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.EveryGoodsStore;
import ltd.newbee.mall.service.EveryGoodsStoreService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ltd.newbee.mall.controller.vo.EveryGoodsStoreVO;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EveryGoodsStoreServiceImpl implements EveryGoodsStoreService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private NewBeeMallGoodsMapper goodsMapper;
    @Autowired
    private GoodsImgMapper goodsImgMapper;

    @Override
    public List<GoodsCategory> FetchSecLeveLCateList() {
        List<GoodsCategory> goodsCategory = goodsCategoryMapper.selectLevelTwoList();
        return goodsCategory;
    }

    @Override
    public List<EveryGoodsStoreVO> Selectgoodslist(Long categoryId) {
        List<EveryGoodsStore> everyGoodsStoreentity = new ArrayList<EveryGoodsStore>();//一時List
        List<EveryGoodsStoreVO> everyGoodsStoreVO = new ArrayList<>();//一時List
        //取所有为level3的数据
        long parentId = categoryId;
        List<GoodsCategory> sortedCategoryList = goodsCategoryMapper.selectLevelThreeList(parentId);

        //取所商品的数据
        for (int i = 0; i < sortedCategoryList.size(); i++) {//i等于等级3的categoryId

            List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsListByGoodsCategoryId((sortedCategoryList.get(i)).getCategoryId());
            for (int j = 0; j < goodsList.size(); j++) {//j等于goodsId
                //取所img的数据
                List<String> goodsImgList = goodsImgMapper.selectGoodsImgBylimitFive((goodsList.get(j)).getGoodsId());
                //取所5张img并生成集合
                EveryGoodsStore goodsStrore = new EveryGoodsStore();
                if (goodsImgList.size() > 0) {//没有图片的不显示
                    goodsStrore.setGoodsId((goodsList.get(j)).getGoodsId());
                    goodsStrore.setImg(goodsImgList);
                    everyGoodsStoreentity.add(goodsStrore);
                    everyGoodsStoreVO = BeanUtil.copyList(everyGoodsStoreentity, EveryGoodsStoreVO.class);
                }
            }
        }
        return everyGoodsStoreVO;
    }
}
