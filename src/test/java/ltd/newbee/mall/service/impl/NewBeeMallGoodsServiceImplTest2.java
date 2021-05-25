package ltd.newbee.mall.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.Sale;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
@SpringBootTest
class NewBeeMallGoodsServiceImplTest2 {
	
	@Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	final void testGetSalesByLikeSearch() {
		 
		 Map<String,Object> params = new HashMap<String,Object>(); 
		 params.put("page",1); 
		 params.put("limit",3);
		 params.put("keyword","半价"); 
		 params.put("orderBy","start_date"); 
		 params.put("descAsc","asc"); 
		    
		 PageQueryUtil pageUtil = new PageQueryUtil(params); 
		 PageResult pageResult =newBeeMallGoodsService.getSalesByLikeSearch(pageUtil); 
	     List<Sale> sales = (List<Sale>) pageResult.getList();
	     int total = pageResult.getTotalCount();
	     assertEquals(2, total);
	     
		}
	

}
