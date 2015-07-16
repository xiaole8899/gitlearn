package org.dppc.mtrace.app.approach;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.approach.entity.ApproachEntity;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.approach.service.ApproachService;
import org.dppc.mtrace.app.approach.service.SupplyMarketService;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.dict.entity.CountyEntity;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.dppc.mtrace.app.dict.service.CountyService;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.app.merchant.service.MerchantsService;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/approach")
public class ApproachController {
	@Autowired
	private ApproachService approachService;
	@Autowired
	private MerchantsService merchantsService;
	@Autowired
	private CountyService countyService;
	@Autowired
	private SupplyMarketService supplyMarketService;
	
	/**
	 * 展示肉菜进场信息列表
	 *@author weiyuzhen
	 * @throws ParseException 
	 */
	@RequestMapping("/listApproMeat")
	@OperationLog("进场展示操作")
	public String listMeatApproach(HttpServletRequest request,HttpServletResponse response,ApproachEntity approach) throws ParseException{
		Page<ApproachEntity> page = new Page<ApproachEntity>();
		//当前页
		String pageIndex=request.getParameter("pageNum");
		if(StringUtils.isEmpty(pageIndex)){
			page.setPageIndex(1);
		}else{
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		//当前页格式
		String pageSize=request.getParameter("numPerPage");
		if(StringUtils.isEmpty(pageSize)){
			page.setPageSize(20);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		
		//排序条件
		String field = request.getParameter("orderField");
		//dd
		
		//排序规则
		String direction = request.getParameter("orderDirection");
		//排序
		OrderCondition order = new OrderCondition(field,direction);
		
		//调用业务
		approachService.listApproach(page, order, approach,request);
		
	    request.setAttribute("approch", approach);    //查询条件
	    if(request.getParameter("goodsType")!=null){
	    	request.setAttribute("approch_state", request.getParameter("goodsType"));
	    }else {
	    	request.setAttribute("approch_state", 0);
	    }
	    //开始时间
	    String startDate= request.getParameter("startDate");
	    request.setAttribute("startDate",startDate);
	    //结束时间
	    String endDate=request.getParameter("endDate");
	    if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
	    	if(!DateUtil.compareTwoDate(startDate,endDate,DateUtil.DATETIMEFORMAR)){
	    		DwzResponse dwzResponse=new DwzResponse();
	    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
	    		dwzResponse.setMessage("开始时间不能大于结束时间!");
	    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
	    		PrintWriterUtil.writeObject(response, dwzResponse);
	    	}
	    }
	    request.setAttribute("endDate", endDate);
		request.setAttribute("order", order);         //排序
		request.setAttribute("page", page);         //分页及数据展示
		request.setAttribute("businessType", AppConstant.businessType);
		return "approach/approachMeat";		
	}
	
    /**
     * 跳转到肉菜登记页面
     * 
     *@author weiyuzhen
     */
	@RequestMapping("/goApprMeatAdd")
	@OperationLog("跳转到进场新增页操作")
	public String gotoAddApproachMeat(HttpServletRequest request,HttpServletResponse response,ApproachEntity approach){
		
		List<CountyEntity> listArea = countyService.findCountybyPrId("0");
		String approach_state = approach.getApproachState();
		//展示静态类中的凭证类型
		if(approach_state.equals("0")){
			request.setAttribute("voucherType",  AppConstant.voucherType);
		}else{
			request.setAttribute("voucherType",  AppConstant.voucherTypeV);
		}
        request.setAttribute("listArea", listArea);
        request.setAttribute("approachState", approach_state);
		return "approach/approachMeatAdd";
		
	}
	
	/**
	 * 查找带回商户信息
	 * 
	 *@author weiyuzhen
	 */
	@RequestMapping("/findMerchant")
	public String findMerchant(HttpServletRequest request,HttpServletResponse response,Merchant merchant){
		merchant.setBizType(request.getParameter("approachBizType"));
		request.setAttribute("approachState", request.getParameter("approachBizType"));
		Page<Merchant> page=new Page<Merchant>();
		//当前页
		String pageIndex=request.getParameter("pageNum");
		if(StringUtils.isEmpty(pageIndex)){
			page.setPageIndex(1);
		}else{
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		//当前页格式
		String pageSize=request.getParameter("numPerPage");
		if(StringUtils.isEmpty(pageSize)){
			page.setPageSize(20);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		
		//排序条件
		String filed=request.getParameter("orderField");
		if(StringUtils.isEmpty(filed)){
			filed="updateTime";
		}
		//排序规则（升序或降序）
		String direction=request.getParameter("orderDirection");
		if(StringUtils.isEmpty(direction)){
			direction="desc";
		}
		//排序
		OrderCondition order=new OrderCondition(filed,direction);
		request.setAttribute("order",order);
		merchantsService.getMerchatList(page,order,merchant);
		request.setAttribute("page",page);
		//查询条件
		request.setAttribute("merchant",merchant);
		//商户类型
		Map<String,String> businessTypeList=AppConstant.businessType;
		request.setAttribute("businessTypeList",businessTypeList);
		//加载商户经营类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		return "approach/lookUpMerchant";
		
	}
	
	/**
	 * 查找带回商品的信息
	 * 
	 *@author weiyuzhen
	 */
	@RequestMapping("/findGoods")
	public String findGoods(HttpServletRequest request,HttpServletResponse response,GoodsEntity goods){
		goods.setGoodsState( request.getParameter("approachStateAdd"));
		Page<GoodsEntity> page = new Page<GoodsEntity>();
		//当前页
		String pageIndex=request.getParameter("pageNum");
		if(StringUtils.isEmpty(pageIndex)){
			page.setPageIndex(1);
		}else{
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		//当前页格式
		String pageSize=request.getParameter("numPerPage");
		if(StringUtils.isEmpty(pageSize)){
			page.setPageSize(20);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		
		//排序条件
		String field = request.getParameter("orderField");
		
		//排序规则
		String direction = request.getParameter("orderDirection");
	
		//排序
		OrderCondition order = new OrderCondition(field,direction);
		
		//调用业务
		approachService.listGoodsChild(page, order, goods);
		
		request.setAttribute("approachState", request.getParameter("approachStateAdd"));
	    request.setAttribute("goods", goods);    //查询条件
		request.setAttribute("order", order);    //排序
		request.setAttribute("page", page);      //分页及数据展示
		
		return "approach/lookUpGoods";
		
	}
	
	/**
	 * 查找带回供货市场信息
	 * 
	 *@author weiyuzhen
	 */
	@RequestMapping("findSuppMarket")
	public String findSuppliyMarket(HttpServletRequest request,HttpServletResponse response,SupplyMarketEntity market){
		Page<SupplyMarketEntity> page = new Page<SupplyMarketEntity>();
		//当前页
		String pageIndex=request.getParameter("pageNum");
		if(StringUtils.isEmpty(pageIndex)){
			page.setPageIndex(1);
		}else{
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		//当前页格式
		String pageSize=request.getParameter("numPerPage");
		if(StringUtils.isEmpty(pageSize)){
			page.setPageSize(20);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		
		//排序条件
		String field = request.getParameter("orderField");
		
		//排序规则
		String direction = request.getParameter("orderDrection");
		if(StringUtils.isEmpty(direction)){
			direction = "asc";
		}
		
		//排序
		OrderCondition order = new OrderCondition(field,direction);
		
		//调用业务
		supplyMarketService.listSupplyMarket(page, order, market);
		
		//获取实体类中存储的常量对象
		Map<String, String> map =  AppConstant.supplyType;

	    request.setAttribute("map", map);            //常量中的类型
	    request.setAttribute("market", market);    //查询条件
		request.setAttribute("order", order);         //排序
		request.setAttribute("page", page);         //分页及数据展示
		
		
		return "approach/lookUpSupplyMarket";
		
	}
	
	/**
	 * 新增肉菜进场数据
	 * 
	 *@author weiyuzhen
	 */
	@OperationLog("新增进场信息操作")
	@RequestMapping("/addApproach")
	@Transactional
	public void addApproach(HttpServletRequest request,HttpServletResponse response,ApproachEntity approach){
		DwzResponse dwzResponse = new DwzResponse();
		response.setContentType("text/html;chaset=utf-8");
		
		String province =  request.getParameter("province");
		String city   = request.getParameter("city");
		String street = request.getParameter("street");
		StringBuilder originName = new StringBuilder();
		if(!"all".equals(province)){
			originName.append(province.split(",")[1]).append("-");
		}
		if(!"all".equals(city)){
			originName.append(city.split(",")[1]).append("-");

		}
		
		if(!"all".equals(street)){
			originName.append(street.split(",")[1]);
		}
		//----地区控制start
		if(!"all".equals(street)){       //省份
			approach.setAreaOriginId(street.split(",")[0]);
		}else {
			if(!"all".equals(city)){                    //城市
				approach.setAreaOriginId(city.split(",")[0]);
			}else{
				if(!"all".equals(province)){             //地区
					approach.setAreaOriginId(province.split(",")[0]);
				}
				
			}
		}
	//---地区控制end
		approach.setAreaOriginName(originName.toString());//给地区赋值
		approach.setWholesalerId(request.getParameter("bizLookup.bizNo"));
		approach.setWholesalerName(request.getParameter("bizLookup.bizName"));
		approach.setBizType(request.getParameter("bizLookup.businessType"));
		approach.setGoodsCode(request.getParameter("goodsLookup.goodsCode"));
		approach.setGoodsName(request.getParameter("goodsLookup.goodsName"));
		approach.setGoodsType(Integer.parseInt(request.getParameter("goodsLookup.goodsStatus")));
		approach.setWsSupplierId(request.getParameter("marketLookup.no"));
		approach.setWsSupplierName(request.getParameter("marketLookup.name"));
		
		int flag =	approachService.addApproachIn(approach);
		
		if(flag==0){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("添加进场数据失败！");
		}else if(flag == 1){
			/*
			 * 如果是自动生产批次号，有下面的代码生产批次号
			 */
			if(approach.getVoucherType().equals("6")){
				String bouchiId = String.format("%07d", approach.getApproachId());
				approach.setBatchId(AppConstant.getSupplyMarketEntity(request).getNo()+bouchiId);
				approachService.editApproach(approach);
				//往小秤中写文件
				approachService.writeBalanceTxt(approach);
			}
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("添加进场数据成功！");
		}
		
		PrintWriterUtil.writeObject(response, dwzResponse);

	}
	
	/**
	 * 删除进场数据
	 * 
	 *@author weiyuzhen
	 */
	@RequestMapping("/deleteApproach")
	@OperationLog("进场删除操作")
	public void deleteApproach(HttpServletRequest request,HttpServletResponse response,ApproachEntity approach){
		//查找此条数据是否上传如果上传的数据就不能删除
		ApproachEntity app = approachService.findApproach(approach);
		DwzResponse dwzResponse = new DwzResponse();
		if(app.getUploadTime() == null){
			if(approachService.deleteApproach(app)){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
				dwzResponse.setForwardUrl("approach/listApproMeat.do");
				dwzResponse.setMessage("进场删除成功");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("进场删除失败");
			}
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("该条数据已经上传，不能删除");
		}
		
		
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
	
	/**
	 * 跳转到修改页面
	 * 
	 *@author weiyuzhen
	 */
	@RequestMapping("/goToApproachEdit")
	public String goToApproachEdit(HttpServletRequest request,HttpServletResponse response,ApproachEntity approach){
		ApproachEntity approachD = approachService.findApproach(approach);
		//DwzResponse dwzResponse = new DwzResponse();
		request.setAttribute("approachState", request.getParameter("approachState"));
		if(approachD.getUploadTime() !=null){
			return "approach/approachMeatEditTip";
		}else{
		   request.setAttribute("approachD", approachD);
	        //展示静态类中的凭证类型
	        request.setAttribute("voucherType",  AppConstant.voucherType);
	      	List<CountyEntity> listArea = countyService.findCountybyPrId("0");
	        request.setAttribute("listArea", listArea);
	        String areaOriginName = approachD.getAreaOriginName();
	        String[] areas = areaOriginName.split("-");
	        String province = "所有省市";
	        String city = "所有城市";
	        String street = "所有区县";
	        switch(areas.length){
	        case 3:{province = areas[0]; city = areas[1]; street = areas[2]; break;}
	        case 2:{province = areas[0]; city = areas[1]; break;}
	        case 1:{ if(!areas[0].equals("")){
	        	province = areas[0]; break;
	        	}
	          }
	        }
	        request.setAttribute("wyzprovince", province);
	        request.setAttribute("city", city);
	        request.setAttribute("street", street);
	        return "approach/approachMeatEdit";
	}
     
		
	}
	
	/**
	 * 修改进场数据
	 * 
	 *@author weiyuzhen
	 */
	@RequestMapping("/editApproach")
	@OperationLog("进场修改操作")
	public void editApproach(HttpServletRequest request,HttpServletResponse response,ApproachEntity approach){
		DwzResponse dwzResponse = new DwzResponse();
        response.setContentType("text/html;chaset=utf-8");
		
		String province =  request.getParameter("province");
		String city   = request.getParameter("city");
		String street = request.getParameter("street");
		StringBuilder originName = new StringBuilder();
		if(!"all".equals(province)){
			originName.append(province.split(",")[1]).append("-");
		}
		if(!"all".equals(city)){
			originName.append(city.split(",")[1]).append("-");
		}
		
		if(!"all".equals(street)){
			originName.append(street.split(",")[1]);
		}
		//----地区控制start
		if(!"all".equals(street)){       //省份
			approach.setAreaOriginId(street.split(",")[0]);
		}else {
			if(!"all".equals(city)){                    //城市
				approach.setAreaOriginId(city.split(",")[0]);
			}else{
				if(!"all".equals(province)){             //地区
					approach.setAreaOriginId(province.split(",")[0]);
				}
				
			}
		}
		//---地区控制end
		approach.setAreaOriginName(originName.toString());//给地区赋值
		approach.setWholesalerId(request.getParameter("bizLookup.bizNo"));
		approach.setWholesalerName(request.getParameter("bizLookup.bizName"));
		approach.setBizType(request.getParameter("bizLookup.businessType"));
		approach.setGoodsCode(request.getParameter("goodsLookup.goodsCode"));
		approach.setGoodsName(request.getParameter("goodsLookup.goodsName"));
		approach.setGoodsType(Integer.parseInt(request.getParameter("goodsLookup.goodsStatus")));
		approach.setWsSupplierId(request.getParameter("marketLookup.no"));
		approach.setWsSupplierName(request.getParameter("marketLookup.name"));
		if(approachService.editApproach(approach)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("进场修改成功");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("进场修改失败");
		}
		
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
	
	/**
	 * 根据编号获取进场详细信息
	 * @param approachId
	 * @return
	 */
	@RequestMapping(value="/getApproachById")
	public String getApproachById(String approachId,HttpServletRequest request){
		ApproachEntity approachEntity=new ApproachEntity();
		approachEntity.setApproachId(Integer.parseInt(approachId));
		ApproachEntity approach=approachService.findApproach(approachEntity);
		request.setAttribute("approachD", approach);
		Map<String,String> map=new HashMap<String, String>();
		map.putAll(AppConstant.voucherType);
		map.putAll(AppConstant.voucherTypeV);
		request.setAttribute("voucherTypes",map);
		return "approach/detailsapproach";
	}
}
