/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.config.NeeBeeMallWebMvcConfigurer;
import ltd.newbee.mall.entity.CampaignSet;
import ltd.newbee.mall.entity.CategoryLevel;
import ltd.newbee.mall.entity.CategorySale;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.SaleIndexCategory;
import ltd.newbee.mall.entity.GoodsImg;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@Controller
@RequestMapping("/admin")
public class SaleGoodsController {

	@Resource
	private NewBeeMallGoodsService newBeeMallGoodsService;
	@Resource
	private NewBeeMallCategoryService newBeeMallCategoryService;

	@GetMapping({ "/saleGoods", "/saleGoods.html" })
	public String firstLevel(HttpServletRequest request) {
		List<GoodsSale> goodsSales = newBeeMallGoodsService.selectAllSale();
		List<SaleIndexCategory> saleIndexCategories = newBeeMallCategoryService.getCategoryForIndex();

		request.setAttribute("saleIndexCategories", saleIndexCategories);
		request.setAttribute("goodsSales", goodsSales);

		return "admin/saleGoods";
	}

	
	@RequestMapping(value = "/saleGoods/nextCategory", method = RequestMethod.POST)
    @ResponseBody
    public Result nextCategory(@RequestBody Long categoryId) {
		Long parentId = categoryId;
//		List<GoodsSale> goodsSales = new ArrayList<GoodsSale>();
    	List<SaleIndexCategory> goodsList = newBeeMallCategoryService.getCategoryForNextLevel(parentId);
    	List<GoodsSale> goodsSales = newBeeMallGoodsService.selectAllSale();
//    	for(int i=0;i<goodsList.size();i++) {
//    		if (goodsList.get(i).getId() != null) {
//    			List<GoodsSale> temp = newBeeMallGoodsService.getGoodsSaleById(goodsList.get(i).getId());
//    			goodsSales.addAll(temp);
//			}
//    	}
    	Map<Object, List> result = new HashMap<>();
    	result.put("goodsList", goodsList);
    	result.put("goodsSales", goodsSales);
        return ResultGenerator.genSuccessResult(result);
    }
	
	@PostMapping(value = "/saleCategory/delete")
	@ResponseBody
	public Result deleteCategory(@RequestBody Long categoryId) {
		if (newBeeMallGoodsService.deleteCategoryByCateId(categoryId)) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("删除失败");
		}
	}

	@PostMapping(value = "/saleCategory/save")
	@ResponseBody
	public Result saveCategory(@RequestBody CategorySale categorySale) {
		if (Objects.isNull(categorySale.getId()) || Objects.isNull(categorySale.getCategoryId())
				|| Objects.isNull(categorySale.getStartDate()) || Objects.isNull(categorySale.getEndDate())) {
			return ResultGenerator.genFailResult("参数异常！");
		}
		String result = newBeeMallGoodsService.saveSaleCategory(categorySale);
		if (ServiceResultEnum.SUCCESS.getResult().equals(result)
				&& newBeeMallGoodsService.isAvailable(categorySale) == true) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult(result);
		}
	}
	
	@RequestMapping(value = "/sale/campaignSet", method = RequestMethod.POST)
    @ResponseBody
    public Result insertCampaign(@RequestBody CampaignSet campaignSet) {
    	Long maxId = newBeeMallGoodsService.getGoodsSaleId();
        CampaignSet list = new CampaignSet();
        list.setId(maxId);
        list.setPrimaryGoodsId(campaignSet.getPrimaryGoodsId());
        list.setSubGoodsId(campaignSet.getSubGoodsId());
        String result = newBeeMallGoodsService.saveCampaignSet(list);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult(result);
        } else {
            return ResultGenerator.genFailResult("failed");
        }
    }
	
	@RequestMapping(value = "/giftGoods", method = RequestMethod.POST)
    @ResponseBody
    public Result getGiftGoods(@RequestBody Long goodsId) {
    	List<NewBeeMallGoods> goodsList = newBeeMallGoodsService.findGiftGoods(goodsId);
        return ResultGenerator.genSuccessResult(goodsList);
    }

}