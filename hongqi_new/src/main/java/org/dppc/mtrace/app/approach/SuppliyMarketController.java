package org.dppc.mtrace.app.approach;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.approach.service.SupplyMarketService;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.dict.entity.CountyEntity;
import org.dppc.mtrace.app.dict.service.CountyService;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 供货市场管理控制层
 * @author weiyuzhen
 *
 */
@Controller
@RequestMapping("/supplyMarket")
public class SuppliyMarketController{
	
	@Autowired
	private SupplyMarketService supplyMarketService;
	
	@Autowired
	private CountyService countyService;
	
	/**
	 * 展示供货市场的信息
	 * 
	 *@author weiyuzhen
	 */
	@RequestMapping("/listSupplyMarket")
	public String listSuppliyMarket(HttpServletRequest request,HttpServletResponse response,SupplyMarketEntity market){
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
		}else {
			direction ="desc";
		}
		
		//排序
		OrderCondition order = new OrderCondition(field,direction);
		
		//调用业务
		supplyMarketService.listSupplyMarket(page, order, market);
		
		//获取实体类中存储的常量对象
		Map<String, String> map =  AppConstant.supplyType;

	    request.setAttribute("map", map);            //常量中的类型
	    request.setAttribute("marketc", market);    //查询条件
		request.setAttribute("order", order);         //排序
		request.setAttribute("page", page);         //分页及数据展示
		
		return "approach/supplyMarket";
		
	}
	
	/**
	 * 供货市场的信息详情
	 * 
	 *@author weiyuzhen
	 */
	@RequestMapping("/SupplyMarketDetail")
	public String SuppliyMarketInfo(HttpServletRequest request,HttpServletResponse response,String supplyMarketId){
		SupplyMarketEntity supplyMarketInfo=supplyMarketService.SupplyMarketInfo(supplyMarketId);
		//获取实体类中存储的常量对象
		Map<String, String> map =  AppConstant.supplyType;
		request.setAttribute("map", map);
		request.setAttribute("supplyMarketInfo", supplyMarketInfo);
		return "approach/supplyMarketDetail";
		
	}
	
	/**
	 * 跳转到新增供货市场弹窗
	 * @return
	 */
	@RequestMapping("/gotoAddSupplyMarket")
	public String gotoAddSupplyMarket(HttpServletRequest request){
		//获取实体类中存储的常量对象
		Map<String, String> map =  AppConstant.supplyType;
		List<CountyEntity> listArea = countyService.findCountybyPrId("0");
	    request.setAttribute("map", map);
        request.setAttribute("listArea", listArea);
		return "approach/supplyMarketAdd";
		
	}
	
	/*
	 * 新增供货市场
	 */
	@RequestMapping("/addSupplyMarket")
	@OperationLog("供货市场新增操作")
	public void addSupplyMarket(HttpServletRequest request,HttpServletResponse response,SupplyMarketEntity market){
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
			market.setAreaNo(street.split(",")[0]);
		}else {
			if(!"all".equals(city)){                    //城市
				market.setAreaNo(city.split(",")[0]);
			}else{
				if(!"all".equals(province)){             //地区
					market.setAreaNo(province.split(",")[0]);
				}
				
			}
		}
	//---地区控制end
		market.setAreaName(originName.toString());//给地区赋值
		int flag =	supplyMarketService.addSupplyMarket(market);
		
		if(flag==-1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("添加供货市场失败！");
		}else if(flag == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("添加供货市场成功！");
		}else if(flag==2){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("供货市场名称重复");
		}
		
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
	
	/**
	 * 删除供货市场
	 * @throws IOException 
	 */
	@RequestMapping(value="/deleteSupplyMarket")
	@OperationLog("供货市场删除操作")
	public void deleteSupplyMarket(HttpServletRequest request,HttpServletResponse response,SupplyMarketEntity market) {
		DwzResponse dwzResponse = new DwzResponse();
		if(supplyMarketService.deleteSupplyMarket(market)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("supplyMarket/listSupplyMarket.do");
			dwzResponse.setMessage("供货市场删除成功");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("供货市场删除失败");
		}
		
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
	
	/**
	 * 跳转到修改页
	 * @author weiyuzhen
	 */
	@RequestMapping(value="gotoUpdateSupplyMarket")
	public String gotoEditSupplyMarket(SupplyMarketEntity market,HttpServletRequest request ,HttpServletResponse response){
		SupplyMarketEntity suMarket = supplyMarketService.findSupplyMarket(market);
		List<CountyEntity> listArea = countyService.findCountybyPrId("0");
        String areaOriginName = suMarket.getAreaName();
        String[] areas = areaOriginName.split("-");
        String province = "所有省市";
        String city = "所有城市";
        String street = "所有区县";
        switch( areas.length){
        case 3:{province = areas[0]; city = areas[1]; street = areas[2];break;}
        case 2:{province = areas[0]; city = areas[1];break;}
        case 1:{
        	if(!areas[0].equals("")){
	        	province = areas[0]; break;
	        	}
        	}
        }
        request.setAttribute("province", province);
        request.setAttribute("city", city);
        request.setAttribute("street", street);
        request.setAttribute("listArea", listArea);
		request.setAttribute("suMarket", suMarket);
		request.setAttribute("supplyType", AppConstant.supplyType);
		return "approach/supplyMarketEdit";
		
	}
	
	/**
	 * 修改供货市场
	 *@author weiyuzhen
	 */
	@RequestMapping(value="editSupplyMarket")
	@OperationLog("供货市场修改操作")
	public void editSupplyMarket(SupplyMarketEntity suMarket,HttpServletRequest request ,HttpServletResponse response){
		DwzResponse dwzResponse = new DwzResponse();
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
			suMarket.setAreaNo(street.split(",")[0]);
		}else {
			if(!"all".equals(city)){                    //城市
				suMarket.setAreaNo(city.split(",")[0]);
			}else{
				if(!"all".equals(province)){             //地区
					suMarket.setAreaNo(province.split(",")[0]);
				}
				
			}
		}
	//---地区控制end
		suMarket.setAreaName(originName.toString());//给地区赋值
		if(supplyMarketService.editSupplyMarket(suMarket)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("供货市场修改成功");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("供货市场修改失败");
		}
		
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
}
