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
import ltd.newbee.mall.controller.vo.NewBeeMallGoodsImgDetailVO;
import ltd.newbee.mall.dao.GoodsCategoryMapper;
import ltd.newbee.mall.dao.GoodsCommentMapper;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.GoodsComment;
import ltd.newbee.mall.entity.NewBeeMallGoods;
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

	// 2021/04/17 added by sasaki for sale
	@Override
	public PageResult searchSaleGoods(PageQueryUtil pageUtil) {
		Long saleId = Long.parseLong((String) pageUtil.get("saleId"));
		Long goodsCategoryId = Long.parseLong((String) pageUtil.get("goodsCategoryId"));
		Long categoryLeve = Long.parseLong((String) pageUtil.get("categoryLevel"));
		List<NewBeeMallGoods> newBeeMallGoods = new ArrayList<NewBeeMallGoods>();
		List<NewBeeMallGoods> goodsList = new ArrayList<NewBeeMallGoods>();
		
		List<GoodsSale> goodsSales = goodsMapper.selectBySaleId(saleId);
		List<Double> saleValue = goodsSales.stream().map(GoodsSale::getSaleValue).collect(Collectors.toList());
		if (categoryLeve == 1) {
			Long firstLevelId = goodsCategoryId;
			List<GoodsCategory> secondLevelList = goodsCategoryMapper.selectByPrimaryKeyParentId(firstLevelId);
			for (int i = 0; i < secondLevelList.size(); i++) {
				List<GoodsCategory> categories = 
						goodsCategoryMapper.selectByPrimaryKeyParentId(secondLevelList.get(i).getCategoryId());
				List<Long> categoryIds = categories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
				for (int j = 0; j < categoryIds.size(); j++) {
					List<NewBeeMallGoods> temp = goodsMapper.findNewBeeMallGoodsListByGoodsCategoryId(categoryIds.get(j));
					newBeeMallGoods.addAll(temp);
				}
			}
			
		} else if (categoryLeve == 2) {
			Long parentId = goodsCategoryId;
			List<GoodsCategory> categories = goodsCategoryMapper.selectByPrimaryKeyParentId(parentId);
			List<Long> categoryIds = categories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
			for (int i = 0; i < categoryIds.size(); i++) {
				newBeeMallGoods = goodsMapper.findNewBeeMallGoodsListByGoodsCategoryId(categoryIds.get(i));
			}
		} else if (categoryLeve == 3) {
			newBeeMallGoods = goodsMapper.findNewBeeMallGoodsListByGoodsCategoryId(goodsCategoryId);
		}
		
		for (int j = 0; j < newBeeMallGoods.size(); j++) {
						if (goodsSales.get(j).getGoodsCategoryId()==newBeeMallGoods.get(j).getGoodsCategoryId()) {
							Integer salePrice = (int) (newBeeMallGoods.get(j).getSellingPrice()*saleValue.get(j));
							newBeeMallGoods.get(j).setSellingPrice(salePrice);
						}
						goodsList.addAll(newBeeMallGoods);
			if (!CollectionUtils.isEmpty(goodsList)) {
			newBeeMallSearchGoodsVOS = BeanUtil.copyList(goodsList, NewBeeMallSearchGoodsVO.class);
			for (NewBeeMallSearchGoodsVO newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) 
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
	}
	PageResult pageResult = new PageResult(newBeeMallSearchGoodsVOS, total, pageUtil.getLimit(),
			pageUtil.getPage());return pageResult;
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
