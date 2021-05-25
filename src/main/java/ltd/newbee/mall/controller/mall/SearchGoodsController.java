package ltd.newbee.mall.controller.mall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;

import ltd.newbee.mall.controller.vo.NewBeeMallGoodsDetailVO;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.controller.vo.SearchPageCategoryVO;

import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.SearchHistroy;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;

@RestController
public class SearchGoodsController {

    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

   
    //get hit goods
    @RequestMapping(value = "/goods/search", method = RequestMethod.GET)
    //public GoodsQa getHitGoodsList(@RequestBody String goodsName) {
    //getの場合、RequestBodyは使えない、postのみです。
    //getは@RequestParam
    public Result getHitGoodsList(@RequestParam String goodsName) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("keyword", goodsName);
    	params.put("page", 1);
    	params.put("limit", 9);
        //params.put("start", 0);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.searchNewBeeMallGoods(pageUtil));
    }
    
//    @RequestMapping(value = "/goods/insert", method = RequestMethod.POST)
//    public Result insertHistroy(HttpSession httpSession,@RequestParam String key,@RequestParam Long GoodsId) {
//    	
//    	NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
//    	Long userId = user.getUserId();
//    	
//    	SimpleDateFormat sdf = new SimpleDateFormat();
//        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
//        Date date = new Date();
//        
//        SearchHistroy searchHistroy = new SearchHistroy();
//        searchHistroy.setGoodsId(GoodsId);
//        searchHistroy.setUserId(userId);
//        searchHistroy.setKeyword(key);
//        searchHistroy.setDate(date);
//        
//    	String result = newBeeMallGoodsService.saveSearchHistroy(searchHistroy);
//        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
//            return ResultGenerator.genSuccessResult();
//        } else {
//            return ResultGenerator.genFailResult(result);
//        }
//    }
    
    
}