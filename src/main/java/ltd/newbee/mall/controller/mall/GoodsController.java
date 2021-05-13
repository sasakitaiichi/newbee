/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.*;
import ltd.newbee.mall.entity.GoodsComment;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.SearchHistroy;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.nullValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Controller
public class GoodsController {
    @Lazy
    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    @Lazy
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
//    	if(!StringUtils.isEmpty(params.get("goodsCategoryId"))) {
//    	Long goodsCategoryId = Long.valueOf((String) params.get("goodsCategoryId"));
//    	List<GoodsSale> saleList = newBeeMallGoodsService.getSaleGoodsByCategoryId(goodsCategoryId);
//    	}
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = newBeeMallCategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", newBeeMallGoodsService.searchNewBeeMallGoods(pageUtil));
//        request.setAttribute("saleGoods", saleList);
        return "mall/search";
    }
    
    @RequestMapping(value = "/searchHistory/getSearchHistory", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchHistory(HttpSession httpSession) {
    	
//    	NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
//    	if(user!=null) {
//    		goodsReviewHelpNum.setUserId(user.getUserId());
//    	}
    	List<NewBeeMallGoods> list = new ArrayList<NewBeeMallGoods>();
    	NewBeeMallGoods goods1 = new NewBeeMallGoods();
    	NewBeeMallGoods goods2 = new NewBeeMallGoods();
    	NewBeeMallGoods goods3 = new NewBeeMallGoods();
    	goods1.setGoodsId(10700L);
    	goods1.setGoodsName("iphone10");
    	list.add(goods1);
    	goods2.setGoodsId(10003L);
    	goods2.setGoodsName("无印良品 MUJI 基础润肤化妆水");
    	list.add(goods2);
    	goods3.setGoodsId(10004L);
    	goods3.setGoodsName("无印良品 MUJI 柔和洁面泡沫");
    	list.add(goods3);
    	return ResultGenerator.genSuccessResult(list);
	    
    }
    
//    @RequestMapping(value = "/goods/search", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getHitGoodsList(@RequestBody String goodsName) {
//    	Map<String, Object> params = new HashMap<String, Object>();
//    	params.put("keyword", goodsName);
//    	params.put("page", 1);
//    	params.put("limit", 9);
//        PageQueryUtil pageUtil = new PageQueryUtil(params);
//        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.searchNewBeeMallGoods(pageUtil));
//    }
    
    
    
    
//    @RequestMapping(value = "/searchHistroy/save", method = RequestMethod.POST)
//    @ResponseBody
//    public Result saveSearchHistroy(@RequestBody SearchHistroy searchHistroy) {
//        if (Objects.isNull(searchHistroy.getGoodsId())
//                || Objects.isNull(searchHistroy.getUserId())
//                || Objects.isNull(searchHistroy.getKeyword())
//                || Objects.isNull(searchHistroy.getDate())
//        ) {
//            return ResultGenerator.genFailResult("参数异常！");
//        }
//        String result = newBeeMallGoodsService.saveSearchHistroy(searchHistroy);
//        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
//            return ResultGenerator.genSuccessResult();
//        } else {
//            return ResultGenerator.genFailResult(result);
//        }
//    }
//    

    
    


    @GetMapping({"/searchCat", "/search.html"})
    public String searchPageCat(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = newBeeMallCategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", newBeeMallGoodsService.searchNewBeeMallGoodsCat(pageUtil));
        return "mall/searchCat";
    }

    @GetMapping({"/secondLevelCategory", "/searchCell.html"})
    public String searchCommonPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
    	if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
//            SearchPageCategoryVO searchPageCategoryVO = newBeeMallCategoryService.getCategoriesForSearch(categoryId);
//            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
//                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
//            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", newBeeMallGoodsService.searchSecondLevel(pageUtil));

        return "mall/searchCell";
    }
    
    @GetMapping({"/searchCat2", "/searchCat.html"})
    public String searchPageCat2(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //params.put("limit", 15);
        Long parentId = 0L;
        //封装分类数据;
        if (params.containsKey("parentId") && !StringUtils.isEmpty(params.get("parentId") + "")) {
            parentId = Long.valueOf(params.get("parentId") + "");
            SearchPageCategoryVO searchPageCategoryVO = newBeeMallCategoryService.getCategoriesForSearchParentId(parentId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("parentId", parentId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        params.put("parentId", parentId);
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", newBeeMallCategoryService.selectCategoryId(pageUtil));
        return "mall/searchCat";
    }


    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {

        if (goodsId < 1) {
            return "error/error_5xx";
        }
        NewBeeMallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        Map goodsImg = newBeeMallGoodsService.searchGoodsImg(goodsId);
        List<GoodsComment> goodsComment = newBeeMallGoodsService.getCommentById(goodsId);
//        int salePrice = newBeeMallGoodsService.getSalePriceById(goodsId);

        if (goods == null) {
            NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            NewBeeMallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        List bigOrderBy = (List) goodsImg.get("bigOrderBy");
        List smallOrderBy = (List) goodsImg.get("smallOrderBy");

        NewBeeMallGoodsDetailVO goodsDetailVO = new NewBeeMallGoodsDetailVO();

        BeanUtil.copyProperties(goods, goodsDetailVO);

        List<NewBeeMallGoodsImgDetailVO> newBeeMallSearchGoodsBigVOS = BeanUtil.copyList(bigOrderBy, NewBeeMallGoodsImgDetailVO.class);
        List<NewBeeMallGoodsImgDetailVO> newBeeMallSearchGoodsSmallVOS = BeanUtil.copyList(smallOrderBy, NewBeeMallGoodsImgDetailVO.class);

        List<GoodsCommentVO> goodsCommentVOS = BeanUtil.copyList(goodsComment, GoodsCommentVO.class);


        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));


        request.setAttribute("goodsDetail", goodsDetailVO);
        request.setAttribute("goodsBigImgDetail", newBeeMallSearchGoodsBigVOS);
        request.setAttribute("goodsSmallImgDetail", newBeeMallSearchGoodsSmallVOS);
        request.setAttribute("goodsComment",goodsCommentVOS);
//        request.setAttribute("salePrice",salePrice);
        return "mall/detail";
    }
    
    @RequestMapping(value = "/goods/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insertHistroy(HttpSession httpSession,@RequestBody String key) {
    	
    	NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
    	Long userId = user.getUserId();
    	
    	Random r = new Random();
    	Long randomId = (long) r.nextInt(100);
    	
    	SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        Date date = new Date();
        
        SearchHistroy searchHistroy = new SearchHistroy();
        searchHistroy.setId(randomId);
        searchHistroy.setUserId(userId);
        searchHistroy.setKeyword(key);
        searchHistroy.setDate(date);
        
    	String result = newBeeMallGoodsService.saveSearchHistroy(searchHistroy);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }
    
    
    
}
