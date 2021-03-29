/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.controller.vo.*;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.EveryGoodsStore;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.EveryGoodsStoreService;
import ltd.newbee.mall.util.*;
import ltd.newbee.mall.entity.GoodsIdCatId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class EveryGoodsStoreController {
    @Lazy
    @Resource
    private EveryGoodsStoreService everyGoodsStoreService;
    @Autowired
    private NewBeeMallGoodsMapper goodsMapper;


    @GetMapping({"/recommendAjaxGoodsId/{goodsId}"})
    @ResponseBody
    public Result GoodsStoreControllerByGoodsId(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        List<EveryGoodsStore> everyGoodsStoreentity = new ArrayList<EveryGoodsStore>();//一時List
        List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsListByGoodsId(goodsId);
        for (int i = 0; i < goodsList.size(); i++) {
            EveryGoodsStore goodsStrore = new EveryGoodsStore();
            goodsStrore.setGoodsId(goodsId);
            goodsStrore.setGoodsCoverImg(goodsList.get(i).getGoodsCoverImg());
            goodsStrore.setGoodsName(goodsList.get(i).getGoodsName());
            everyGoodsStoreentity.add(goodsStrore);
        }
        return ResultGenerator.genSuccessResult(everyGoodsStoreentity);
    }

    @PostMapping({"/recommendAjaxUpdate"})
    @ResponseBody
    public Result GoodsStoreControllerByGoodsId(@RequestBody GoodsIdCatId goodIdCatId, HttpServletRequest request) {
        List<EveryGoodsStore> everyGoodsStoreentity = new ArrayList<EveryGoodsStore>();//一時List
        goodsMapper.updateGoods(goodIdCatId);
        EveryGoodsStore goodsStrore = new EveryGoodsStore();
        goodsStrore.setGoodsId(goodIdCatId.getGoodsId());
        goodsStrore.setGoodsName(goodIdCatId.getGoodsName());
        everyGoodsStoreentity.add(goodsStrore);
        return ResultGenerator.genSuccessResult(everyGoodsStoreentity);
    }
/*        List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsListByGoodsId(goodsId);
        for (int i = 0 ; i <goodsList.size() ; i ++ ){
            EveryGoodsStore goodsStrore = new EveryGoodsStore();
            goodsStrore.setGoodsId(goodsId);
            goodsStrore.setGoodsCoverImg(goodsList.get(i).getGoodsCoverImg());
            goodsStrore.setGoodsName(goodsList.get(i).getGoodsName());
            everyGoodsStoreentity.add(goodsStrore);
        }*/


    @PostMapping({"/recommendAjax"})
    @ResponseBody
    public Result everyGoodsStoreControllerCall(@RequestBody GoodsIdCatId goodIdCatId, HttpServletRequest request) {
        List<EveryGoodsStoreVO> goodsImg = everyGoodsStoreService.Selectgoodslist(goodIdCatId.getCategoryId());
        List<Long> goodsImgList = new ArrayList<>();
        List<EveryGoodsStoreVO> goodsImg2 = new ArrayList<>();
        goodsImgList = goodIdCatId.getGoodsArray();

        for (int i = 0; i < goodsImg.size(); i++) {
            for (int j = 0; j < goodsImgList.size(); j++) {

                if (goodIdCatId.getGoodsArray().get(j).equals(goodsImg.get(i).getGoodsId())) {
                    goodsImg.remove(goodsImg.get(i));
                }
            }
        }
        if (goodsImg.size() > 5) {
            goodsImg = goodsImg.subList(0, 6);
            return ResultGenerator.genSuccessResult(goodsImg);
        } else if (goodsImg.size() > 0) {
            return ResultGenerator.genSuccessResult(goodsImg);
        }
        return ResultGenerator.genSuccessResult();
    }


    @GetMapping({"/recommend", "/recommend?{categoryId}", "/recommend.html"})
    public String everyGoodsStoreController(Long categoryId, Long goodsId, HttpServletRequest request) {
        //int total = 10 ;
        // @RequestParam("categoryId")
        //上端菜单?{categoryId}....,List<GoodsCategory> goodsCategory...., PageQueryUtil pageUtil.., HttpServletRequest httpServletRequest
        List<GoodsCategory> categoryLevelTwo = everyGoodsStoreService.FetchSecLeveLCateList();
        if (categoryId == null) {
            categoryId = 19l;
        }
        //下端内容
        List<EveryGoodsStoreVO> goodsImg = everyGoodsStoreService.Selectgoodslist(categoryId);
        if (goodsImg.size() > 5) {
            List<EveryGoodsStoreVO> goodsImgFive = goodsImg.subList(0, 6);
            request.setAttribute("goodsImgFive", goodsImgFive);
        } else {
            List<EveryGoodsStoreVO> goodsImgFive = goodsImg;
            request.setAttribute("goodsImgFive", goodsImgFive);
        }
        request.setAttribute("categoryLevelTwo", categoryLevelTwo);//Level2菜单
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("goodsId", goodsId);
        return "mall/recommend";
    }
}
