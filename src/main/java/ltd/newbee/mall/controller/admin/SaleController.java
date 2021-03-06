
package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.Download;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.Sale;
import ltd.newbee.mall.entity.GoodsImg;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sun.el.parser.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@Controller
@RequestMapping("/admin")
public class SaleController {

    private static final String FILE_UPLOAD = null;
	@Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;



    @RequestMapping(value = "/goods/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result fileUpload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file)
    		throws URISyntaxException, ParseException, java.text.ParseException {
    	int i = 0;
    	String result = "";
        List<Sale> list = new ArrayList<>();
        String[] arr = null;
        try {
//        	String dateStr = "Tue Oct 13 11:41:59 JST 2020";
//            DateFormat formatter = new SimpleDateFormat("E MMM dd hh:mm:ss 'JST' yyyy", Locale.ENGLISH);
//            java.util.Date date =  formatter.parse(dateStr);
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            InputStream fileStream2 = file.getInputStream();
            Reader reader = new InputStreamReader(fileStream2, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            bufferedReader.readLine();
            do {
                line = bufferedReader.readLine();
                System.out.println(line);
                if (line == null) {
                    break;
                }
                if (i == 0) {
                    arr = line.split(",");
                }
                Sale sale = new Sale();
                if (arr[0] != null && !arr[0].equals("")) {
                	sale.setId(Long.parseLong(arr[0]));
                	sale.setName(arr[1]);
                	sale.setStartDate(formatter.parse(arr[2]));
                    sale.setEndDate(formatter.parse(arr[3]));
                    list.add(sale);
                   result = newBeeMallGoodsService.saveSale(sale);
                }
            } while (!line.equals(""));

            Result resultSuccess = ResultGenerator.genSuccessResult();
            resultSuccess.setData(list);
            return resultSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }
    
    @RequestMapping(value = "/sale/download", method = RequestMethod.POST)
    @ResponseBody
    public Result download(@RequestBody Download download) {
    	StringBuilder txCv = new StringBuilder();
        txCv.append("."+ download.getFormat());
        String test= "test" + txCv;
          File f = new File(Constants.FILE_UPLOAD_DIC + test);
     BufferedWriter bw=null;
     try {
      bw = new BufferedWriter(new FileWriter(f));
     } catch (IOException e1) {
      e1.printStackTrace();
     }
     List<Sale> saleList =newBeeMallGoodsService.getSalesByIds(download.getIds());
               for(int i = 0; i < saleList.size();i++) {
                 Sale sales = saleList.get(i);
                if(sales != null) {
                 try {
         bw.write(sales.toString());
         bw.newLine();        
        } catch (IOException e) {
         
         e.printStackTrace();
        }
                         
                 }
    
               }
               
               try {
       bw.close();
      } catch (IOException e) {
       
       e.printStackTrace();
      }
               
     
               Result resultSuccess = ResultGenerator.genSuccessResult();
               resultSuccess.setData("/upload/"+ test);
               return resultSuccess;
                
      }

    
    @GetMapping({"/goods/sale", "/newbee_mall_goods_sale.html"})
    public String searchSalesByLikeSearch(@RequestParam Map<String, Object> params, HttpServletRequest request) {
    	if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", 3);
        
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
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", newBeeMallGoodsService.getSales(pageUtil));

        return "admin/newbee_mall_goods_sale";
    }
    
    @RequestMapping(value = "/goods/searchSale", method = RequestMethod.GET)
    public Result searchSale(@RequestParam String name) {
     Map<String, Object> paramsSale = new HashMap<String, Object>();
     paramsSale.put("keyword", name);
     paramsSale.put("page", 1);
     paramsSale.put("limit", 5);
        PageQueryUtil pageUtil = new PageQueryUtil(paramsSale);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.goodsSalePagAndSort(pageUtil));
    }
    
    @RequestMapping(value = "/sale/save", method = RequestMethod.POST)
    @ResponseBody
    public Result insertSale(@RequestBody GoodsSale goodsSale) {
    	Long maxId = newBeeMallGoodsService.getGoodsSaleId();
        GoodsSale list = new GoodsSale();
        list.setId(maxId);
        list.setName(goodsSale.getName());
        list.setStartDate(goodsSale.getStartDate());
        list.setEndDate(goodsSale.getEndDate());
        list.setCampaign(goodsSale.getCampaign());
        list.setContent1(goodsSale.getContent1());
        list.setContent2(goodsSale.getContent2());
        list.setContent3(goodsSale.getContent3());
        list.setContent4(goodsSale.getContent4());
        list.setContent5(goodsSale.getContent5());
        list.setFlag(goodsSale.getFlag());
        String result = newBeeMallGoodsService.saveSaleGoods(list);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }
    
    @RequestMapping(value = "/sale-order", method = RequestMethod.GET)
    @ResponseBody
    public Result cartListPageOrder(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        String orderBy = "";
        String ascOrDesc = "";
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            orderBy = params.get("orderBy") + "";
        }
        if (params.containsKey("ascOrDesc") && !StringUtils.isEmpty(params.get("ascOrDesc") + "")) {
        	ascOrDesc = params.get("ascOrDesc") + "";
        }
        Map saleResult = new HashMap();
       List<Sale> sale = newBeeMallGoodsService.getSalesBySort(orderBy, ascOrDesc);

        saleResult.put("saleList",sale);

        return ResultGenerator.genSuccessResult(saleResult);
    }
 }
    
