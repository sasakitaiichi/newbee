/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallSearchGoodsVO;
import ltd.newbee.mall.controller.vo.GoodsSaleVO;
import ltd.newbee.mall.controller.vo.NewBeeMallGoodsImgDetailVO;
import ltd.newbee.mall.dao.GoodsCategoryMapper;
import ltd.newbee.mall.dao.GoodsCommentMapper;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.GoodsComment;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.Sale;
import ltd.newbee.mall.dao.GoodsImgMapper;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ltd.newbee.mall.entity.GoodsImg;
import ltd.newbee.mall.entity.GoodsSale;

import java.awt.Color;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewBeeMallGoodsServiceImpl implements NewBeeMallGoodsService {

	@Autowired
	private NewBeeMallGoodsMapper goodsMapper;
	@Autowired
	private GoodsCategoryMapper goodsCategoryMapper;
	@Autowired
	private GoodsImgMapper goodsImgMapper;
	@Autowired
	private GoodsCommentMapper commentMapper;

	@Override
	public PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil) {
		List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsList(pageUtil);
		int total = goodsMapper.getTotalNewBeeMallGoods(pageUtil);
		PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
		return pageResult;
	}

	@Override
	public String saveNewBeeMallGoods(NewBeeMallGoods goods) {
		if (goodsMapper.insertSelective(goods) > 0) {
			return ServiceResultEnum.SUCCESS.getResult();
		}
		return ServiceResultEnum.DB_ERROR.getResult();
	}

	@Override
	public void batchSaveNewBeeMallGoods(List<NewBeeMallGoods> newBeeMallGoodsList) {
		if (!CollectionUtils.isEmpty(newBeeMallGoodsList)) {
			goodsMapper.batchInsert(newBeeMallGoodsList);
		}
	}

	@Override
	public String updateNewBeeMallGoods(NewBeeMallGoods goods) {
		NewBeeMallGoods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
		if (temp == null) {
			return ServiceResultEnum.DATA_NOT_EXIST.getResult();
		}
		goods.setUpdateTime(new Date());
		if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
			return ServiceResultEnum.SUCCESS.getResult();
		}
		return ServiceResultEnum.DB_ERROR.getResult();
	}

	@Override
	public NewBeeMallGoods getNewBeeMallGoodsById(Long id) {
		return goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
		return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
	}

	@Override
	public PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil) {
		List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsListBySearch(pageUtil);
		int total = goodsMapper.getTotalNewBeeMallGoodsBySearch(pageUtil);
		List<NewBeeMallSearchGoodsVO> newBeeMallSearchGoodsVOS = new ArrayList<>();
		if (!CollectionUtils.isEmpty(goodsList)) {
			newBeeMallSearchGoodsVOS = BeanUtil.copyList(goodsList, NewBeeMallSearchGoodsVO.class);
			for (NewBeeMallSearchGoodsVO newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) {
				String goodsName = newBeeMallSearchGoodsVO.getGoodsName();
				String goodsIntro = newBeeMallSearchGoodsVO.getGoodsIntro();
				// 字符串过长导致文字超出的问题
				if (goodsName.length() > 28) {
					goodsName = goodsName.substring(0, 28) + "...";
					newBeeMallSearchGoodsVO.setGoodsName(goodsName);
				}
				if (goodsIntro.length() > 30) {
					goodsIntro = goodsIntro.substring(0, 30) + "...";
					newBeeMallSearchGoodsVO.setGoodsIntro(goodsIntro);
				}
			}
		}
		PageResult pageResult = new PageResult(newBeeMallSearchGoodsVOS, total, pageUtil.getLimit(),
				pageUtil.getPage());
		return pageResult;
	}

//	// 2021/04/17 added by sasaki for sale
//	@Override
//	public List<GoodsSaleVO> searchSaleGoods(Long saleId) {
//		List<NewBeeMallGoods> goodsList = new ArrayList<>();
//		List<GoodsSaleVO> goodsSaleVOs = new ArrayList<>();
//		List<GoodsSale> goodsSales = goodsMapper.selectBySaleId(saleId);
//		for (int i = 0; i < goodsSales.size(); i++) {
//			if (goodsSales.get(i).getGoodsId() != null) {
//				NewBeeMallGoods temp =  goodsMapper.selectByPrimaryKey(goodsSales.get(i).getGoodsId());
//				Integer salePrice = (int) Math.round(temp.getSellingPrice()*goodsSales.get(i).getSaleValue());
//				temp.setSellingPrice(salePrice);
//				goodsList.add(temp);
//			}
//			else if (goodsSales.get(i).getGoodsCategoryId() != null && goodsSales.get(i).getCategoryLevel() == 3) {
//				List<NewBeeMallGoods> temp = 
//						goodsMapper.findNewBeeMallGoodsListByGoodsCategoryId(goodsSales.get(i).getGoodsCategoryId());
//				if (temp != null)
//				{				for (int j = 0; j < temp.size(); j++) {
//					if (temp.get(j).getGoodsCategoryId() == goodsSales.get(j).getGoodsCategoryId()) {
//						Integer salePrice = (int) Math.round(temp.get(j).getSellingPrice()*goodsSales.get(j).getSaleValue());
//						temp.get(j).setSellingPrice(salePrice);
//				goodsList.addAll(temp);
//					   }
//					}
//				}
//			}
//			else if (goodsSales.get(i).getGoodsCategoryId() != null && goodsSales.get(i).getCategoryLevel() == 2) {
//				List<GoodsCategory> secondLevelCategories = 
//						goodsCategoryMapper.selectByPrimaryKeyParentId(goodsSales.get(i).getGoodsCategoryId());
//				if (secondLevelCategories != null) {
//				List<Long> categoryIds = secondLevelCategories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
//				for (int j = 0; j < categoryIds.size(); j++) {
//					List<NewBeeMallGoods> newBeeMallGoods = goodsMapper.findNewBeeMallGoodsListByGoodsCategoryId(categoryIds.get(j));
//					for (int k = 0; k < newBeeMallGoods.size(); k++) {
//						Integer salePrice = (int) Math.round(newBeeMallGoods.get(k).getSellingPrice()*goodsSales.get(i).getSaleValue());
//						newBeeMallGoods.get(k).setSellingPrice(salePrice);
//						goodsList.addAll(newBeeMallGoods);
//					}
//				   }
//				}
//			}
//			else if (goodsSales.get(i).getGoodsCategoryId() != null && goodsSales.get(i).getCategoryLevel() == 1) {
//				GoodsCategory firstLevelCategory = 
//						goodsCategoryMapper.selectByLevelAndId(goodsSales.get(i).getGoodsCategoryId(),goodsSales.get(i).getCategoryLevel());
//				if (firstLevelCategory != null) {
//				List<GoodsCategory> secondLevelCategories = 
//						goodsCategoryMapper.selectByPrimaryKeyParentId(firstLevelCategory.getCategoryId());
//				List<Long> categoryIds = secondLevelCategories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
//				List<NewBeeMallGoods> temp = goodsMapper.selectByCategoryId(categoryIds);
//				for (int j = 0; j < temp.size(); j++) {
//					if (temp.get(j).getGoodsCategoryId() == categoryIds.get(j)) {
//						Integer salePrice = (int) Math.round(temp.get(j).getSellingPrice()*goodsSales.get(j).getSaleValue());
//						temp.get(j).setSellingPrice(salePrice);
//						goodsList.addAll(temp);
//					   }
//					}
//				}
//			}
//		}
//		for (int i = 0; i < goodsList.size(); i++) {
//			GoodsSaleVO goodsSaleVO = new GoodsSaleVO();
//			goodsSaleVO.setGoodsId(goodsList.get(i).getGoodsId());
//			goodsSaleVO.setSalePrice(goodsList.get(i).getSellingPrice());
//			goodsSaleVO.setSaleValue(goodsSales.get(i).getSaleValue());
//			goodsSaleVOs.add(goodsSaleVO);
//		}
//		return goodsSaleVOs;
//		}
	
//	2021/04/21 added by sasaki for sale
	@Override
	public List<GoodsSale> searchSaleGoods(Long saleId) {
		List<GoodsSale> goodsSales = goodsMapper.selectBySaleId(saleId);
		return goodsSales;
	}
	
// 2021/04/21 added by sasaki for sale	
	@Override
	public String saveSalesGoods(GoodsSale goods) {
		if (goodsMapper.insertSale(goods) > 0) {
			return ServiceResultEnum.SUCCESS.getResult();
		}
		return ServiceResultEnum.DB_ERROR.getResult();
	}
	
	// 2021/04/21 added by sasaki for sale	
	@Override
	public String updateSaleGoods(GoodsSale goods) {
		GoodsSale temp = (GoodsSale) goodsMapper.selectBySaleId(goods.getSaleId());
		if (temp == null) {
			return ServiceResultEnum.DATA_NOT_EXIST.getResult();
		}
		if (goodsMapper.updateSaleBySaleId(goods) > 0) {
			return ServiceResultEnum.SUCCESS.getResult();
		}
		return ServiceResultEnum.DB_ERROR.getResult();
	}
	
	// 2021/04/21 added by sasaki for sale
	@Override
    public Boolean deleteSaleGoods(Long goodsId) {
        return goodsMapper.deleteById(goodsId) > 0;
    }
	
// 2021/04/21 added by sasaki for sale
	@Override
	public Boolean isSale(Long goodsId) {
		List<GoodsSale> goodsSales = goodsMapper.selectByGoodsId(goodsId);
		if(goodsSales != null) {
			return true;
		}
		return true;
	}
	
	// 2021/04/21 added by sasaki for sale	
		@Override
		public String saveSales(Sale sale) {
			if (goodsMapper.insertSaleList(sale) > 0) {
				return ServiceResultEnum.SUCCESS.getResult();
			}
			return ServiceResultEnum.DB_ERROR.getResult();
		}
	
	// 2021/04/21 added by sasaki for sale
		@Override
	    public Boolean deleteSale(Long saleId) {
	        return goodsMapper.deleteSaleListById(saleId) > 0;
	    }
	
		// 2021/04/21 added by sasaki for sale	
		@Override
		public String updateSale(Sale sale) {
			Sale temp = (Sale) goodsMapper.selectSaleListBySaleId(sale.getSaleId());
			if (temp == null) {
				return ServiceResultEnum.DATA_NOT_EXIST.getResult();
			}
			if (goodsMapper.updateSaleListBySaleId(sale) > 0) {
				return ServiceResultEnum.SUCCESS.getResult();
			}
			return ServiceResultEnum.DB_ERROR.getResult();
		}
		
//		2021/04/21 added by sasaki for sale
		@Override
		public List<Sale> searchSale(Long saleId) {
			List<Sale> Sales = goodsMapper.selectSaleListBySaleId(saleId);
			return Sales;
		}
	
		
	@Override
	public PageResult searchNewBeeMallGoodsCat(PageQueryUtil pageUtil) {
		List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsListBySearchCat(pageUtil);
		int total = goodsMapper.getTotalNewBeeMallGoodsBySearch(pageUtil);
		List<NewBeeMallSearchGoodsVO> newBeeMallSearchGoodsVOS = new ArrayList<>();
		if (!CollectionUtils.isEmpty(goodsList)) {
			newBeeMallSearchGoodsVOS = BeanUtil.copyList(goodsList, NewBeeMallSearchGoodsVO.class);// vos复制vo
			for (NewBeeMallSearchGoodsVO newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) {// vo遍历vos
				String goodsName = newBeeMallSearchGoodsVO.getGoodsName();
				String goodsIntro = newBeeMallSearchGoodsVO.getGoodsIntro();
				// 字符串过长导致文字超出的问题
				if (goodsName.length() > 28) {
					goodsName = goodsName.substring(0, 28) + "...";
					newBeeMallSearchGoodsVO.setGoodsName(goodsName);
				}
				if (goodsIntro.length() > 30) {
					goodsIntro = goodsIntro.substring(0, 30) + "...";
					newBeeMallSearchGoodsVO.setGoodsIntro(goodsIntro);
				}
			}
		}
		PageResult pageResult = new PageResult(newBeeMallSearchGoodsVOS, total, pageUtil.getLimit(),
				pageUtil.getPage());
		return pageResult;
	}

	@Override
	public PageResult searchSecondLevel(PageQueryUtil pageUtil) {
		List<NewBeeMallGoods> goodsList = new ArrayList<NewBeeMallGoods>();
		Long parentId = null;
		List<NewBeeMallSearchGoodsVO> newBeeMallSearchGoodsVOS = new ArrayList<>();
//		int total = goodsMapper.getTotalNewBeeMallGoodsByCategory(pageUtil);

		parentId = Long.parseLong((String) pageUtil.get("goodsCategoryId"));
		List<GoodsCategory> categories = goodsCategoryMapper.selectByPrimaryKeyParentId(parentId);
		List<Long> categoryIds = categories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());

		for (int i = 0; i < categoryIds.size(); i++) {

			List<NewBeeMallGoods> temp = goodsMapper.findNewBeeMallGoodsListByGoodsCategoryId(categoryIds.get(i));
			if (temp != null) {
				goodsList.addAll(temp);
			}
		}
		int currPage = (int) pageUtil.get("page");// 当前页
		int pageSize = (int) pageUtil.get("limit");// 每页几条
		int startIndex = (currPage - 1) * pageSize;// 开始下标
		int endIndex = currPage * pageSize;// 结束下标
		int total = goodsList.size();// list总条数
		int pageCount = 0;// 总页数
		if (currPage == pageCount) {
			endIndex = total;
		}
		List<NewBeeMallGoods> pageList = goodsList.subList(startIndex, endIndex);
//			List<NewBeeMallGoods> newBeeMallGoods = goodsMapper.selectByCategoryId(categoryIds);
//			Map<Long, List<NewBeeMallGoods>> goodsGroupByCategoryId = newBeeMallGoods.stream()
//					.collect(Collectors.groupingBy(NewBeeMallGoods::getGoodsCategoryId, Collectors.collectingAndThen(
//							Collectors.toList(), value -> value.stream().limit(5).collect(Collectors.toList()))));
//
//			for (Map.Entry<Long, List<NewBeeMallGoods>> entry : goodsGroupByCategoryId.entrySet()) {
//				List<NewBeeMallGoods> temp = entry.getValue();

		if (!CollectionUtils.isEmpty(pageList)) {
			newBeeMallSearchGoodsVOS = BeanUtil.copyList(pageList, NewBeeMallSearchGoodsVO.class);// vos复制vo
			for (NewBeeMallSearchGoodsVO newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) {// vo遍历vos
				String goodsName = newBeeMallSearchGoodsVO.getGoodsName();
				String goodsIntro = newBeeMallSearchGoodsVO.getGoodsIntro();
				// 字符串过长导致文字超出的问题
				if (goodsName.length() > 28) {
					goodsName = goodsName.substring(0, 28) + "...";
					newBeeMallSearchGoodsVO.setGoodsName(goodsName);
				}
				if (goodsIntro.length() > 30) {
					goodsIntro = goodsIntro.substring(0, 30) + "...";
					newBeeMallSearchGoodsVO.setGoodsIntro(goodsIntro);
				}
			}
		}

		PageResult pageResult = new PageResult(newBeeMallSearchGoodsVOS, total, pageUtil.getLimit(),
				pageUtil.getPage());
		return pageResult;
	}

	@Override
	public PageResult searchBySecondLevelCat(PageQueryUtil pageUtil) {
		List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeSearchBySecondLevelCat(pageUtil);// sql
		int total = goodsMapper.getTotalNewBeeMallGoodsBySearch(pageUtil);// 翻页
		List<NewBeeMallSearchGoodsVO> newBeeMallSearchGoodsVOS = new ArrayList<>();
		if (!CollectionUtils.isEmpty(goodsList)) {
			newBeeMallSearchGoodsVOS = BeanUtil.copyList(goodsList, NewBeeMallSearchGoodsVO.class);
			for (NewBeeMallSearchGoodsVO newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) {
				String goodsName = newBeeMallSearchGoodsVO.getGoodsName();
				String goodsIntro = newBeeMallSearchGoodsVO.getGoodsIntro();
				// 字符串过长导致文字超出的问题
				if (goodsName.length() > 28) {
					goodsName = goodsName.substring(0, 28) + "...";
					newBeeMallSearchGoodsVO.setGoodsName(goodsName);
				}
				if (goodsIntro.length() > 30) {
					goodsIntro = goodsIntro.substring(0, 30) + "...";
					newBeeMallSearchGoodsVO.setGoodsIntro(goodsIntro);
				}
			}
		}
		PageResult pageResult = new PageResult(newBeeMallSearchGoodsVOS, total, pageUtil.getLimit(),
				pageUtil.getPage());
		return pageResult;
	}

	@Override
	public Map searchGoodsImg(Long goodsId) {
		List<GoodsImg> goodsImgList = new ArrayList<GoodsImg>();
		List<GoodsImg> listBig = new ArrayList<GoodsImg>();// 一時List
		List<GoodsImg> listSmall = new ArrayList<GoodsImg>();// 一時List
		List<GoodsImg> bigOrderBy = new ArrayList<GoodsImg>();
		List<GoodsImg> smallOrderBy = new ArrayList<GoodsImg>();
		goodsImgList = goodsImgMapper.selectGoodsImg(goodsId);
		for (int i = 0; i < goodsImgList.size(); i++) {
			GoodsImg sortedGoodsList = goodsImgList.get(i);
			if (sortedGoodsList.getImgStatus() == 0) {
				listBig.add(sortedGoodsList);
			} else {
				listSmall.add(sortedGoodsList);
			}
		}
		bigOrderBy = listBig.stream().sorted(Comparator.comparing(GoodsImg::getOrderby)).collect(Collectors.toList());
		smallOrderBy = listSmall.stream().sorted(Comparator.comparing(GoodsImg::getOrderby))
				.collect(Collectors.toList());
		Map map = new HashMap();
		map.put("bigOrderBy", bigOrderBy);
		map.put("smallOrderBy", smallOrderBy);

		return map;

	}

	@Override
	public int ImgInsertA(GoodsImg record) {
		int insertImg = goodsImgMapper.ImgInsert(record);

		return insertImg;
	}

	@Override
	public String saveComment(GoodsComment comment) {
		if (commentMapper.insert(comment) > 0) {
			return ServiceResultEnum.SUCCESS.getResult();
		}
		return ServiceResultEnum.DB_ERROR.getResult();
	}

	@Override
	public List<GoodsComment> getCommentById(Long goodsId) {
		List<GoodsComment> goodsComments = goodsMapper.selectById(goodsId);
		return goodsComments;
	}
}
