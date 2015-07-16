package org.dppc.mtrace.app.trade;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.dppc.mtrace.app.trade.entity.CostsSet;
import org.dppc.mtrace.app.trade.service.CostsSetService;
import org.dppc.mtrace.app.user.entity.UserEntity;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/costs")
public class CostsSetController {

	@Autowired
	private CostsSetService costsService;
	
	/**
	 * 获取费用列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/costsList")
	public String costsList(HttpServletRequest request,CostsSet costsSet){
		Page<CostsSet> page=new Page<CostsSet>();
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
		costsService.getCostsList(page, order, costsSet);
		request.setAttribute("page",page);
		request.setAttribute("costsSet",costsSet);
		return "trade/listcosts";
	}
	
	/**
	 * 跳转新增费用设置页面
	 * @return
	 */
	@RequestMapping(value="/toAddCostsSet")
	public String toAddCostsSet(){
		
		return "trade/addcosts";
	}
	
	/**
	 * 新增费用信息
	 * @param costs
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/doAddCost")
	@ResponseBody
	public DwzResponse doAddCost(CostsSet costs,HttpServletRequest request){
		DwzResponse dwzResponse = new DwzResponse();
		costs.setGoodsNames(request.getParameter("costs.goodsName"));
		costs.setGoodsCodes(request.getParameter("costs.goodsCode"));
		costs.setStatus("0");
		if(StringUtils.isEmpty(costs.getSellerQuota()) && StringUtils.isEmpty(costs.getBuyerRate())){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("卖方定额与比例至少填写一项!");
			return dwzResponse;
		}
		if(StringUtils.isEmpty(costs.getSellerQuota()) && StringUtils.isEmpty(costs.getSellerRate())){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("买方定额与比例至少填写一项!");
			return dwzResponse;
		}
		UserEntity user=AppConstant.getUserEntity(request);
		costs.setUserId(String.valueOf(user.getUserId()));
		costs.setUsrName(user.getUserName());
		costs.setUpdateTime(new Timestamp(new Date().getTime()));
		costs.setCreateTime(new Timestamp(new Date().getTime()));
		int num=costsService.addCosts(costs);
		if(num>0){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("costs/toAddCostsSet.do");
			dwzResponse.setMessage("添加费用成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			
			dwzResponse.setMessage("添加费用失败!");
		}
		return dwzResponse;
	}
	
	/**
	 * 查找带回商品信息
	 * @param request
	 * @param goodsEntity
	 * @return
	 */
	@RequestMapping(value="/lookUpGoods")
	public String lookUpGoods(HttpServletRequest request,GoodsEntity goodsEntity){
		List<GoodsEntity> list=costsService.getGoodsList(goodsEntity);
		request.setAttribute("goodsList",list);
		request.setAttribute("goods",goodsEntity);
		return "trade/lookupgoods";
	}
	
	
	/**
	 * 删除费用信息
	 * @param costs
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteCosts")
	@ResponseBody
	public DwzResponse deleteCosts(String costsId,HttpServletRequest request){
		DwzResponse dwzResponse = new DwzResponse();
		
		int num=costsService.deleteCost(Integer.parseInt(costsId));
		if(num>0){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("costs/costsList.do");
			dwzResponse.setMessage("删除费用成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			
			dwzResponse.setMessage("删除费用失败!");
		}
		return dwzResponse;
	}
	
	/**
	 * 跳转新增费用设置页面
	 * @return
	 */
	@RequestMapping(value="/getDetailsCosts")
	public String getDetailsCosts(String costsId,HttpServletRequest request){
		CostsSet costs=costsService.getCosts(Integer.parseInt(costsId));
		request.setAttribute("costs",costs);
		return "trade/costsdetails";
	}
	
	/**
	 * 跳转修改费用页面
	 * @param costsId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toUpdateCosts")
	public String toUpdateCosts(String costsId,HttpServletRequest request){
		CostsSet costs=costsService.getCosts(Integer.parseInt(costsId));
		request.setAttribute("costs",costs);
		return "trade/updatecosts";
	}
	
	/**
	 * 修改费用信息
	 * @param costs
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/doUpdateCost")
	@ResponseBody
	public DwzResponse doUpdateCost(CostsSet costs,HttpServletRequest request){
		DwzResponse dwzResponse = new DwzResponse();
		costs.setGoodsNames(request.getParameter("costs.goodsName"));
		costs.setGoodsCodes(request.getParameter("costs.goodsCode"));
		if(StringUtils.isEmpty(costs.getSellerQuota()) && StringUtils.isEmpty(costs.getBuyerRate())){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("卖方定额与比例至少填写一项!");
			return dwzResponse;
		}
		if(StringUtils.isEmpty(costs.getSellerQuota()) && StringUtils.isEmpty(costs.getSellerRate())){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("买方定额与比例至少填写一项!");
			return dwzResponse;
		}
		costs.setUpdateTime(new Timestamp(new Date().getTime()));
		int num=costsService.updateCosts(costs);
		if(num>0){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("修改结算规则成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			
			dwzResponse.setMessage("修改结算规则失败!");
		}
		return dwzResponse;
	}
	
	/**
	 * 启用或关闭交易规则
	 * @param costs
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/openOrCloseCosts")
	@ResponseBody
	public DwzResponse openOrCloseCosts(String status,String costsId){
		DwzResponse dwzResponse = new DwzResponse();
		CostsSet costs=costsService.getCosts(Integer.parseInt(costsId));
		costs.setStatus(status);
		int num=costsService.updateCosts(costs);
		if(num>0){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("costs/costsList.do");
			if(StringUtils.isNotEmpty(status) && status.endsWith("1")){
				dwzResponse.setMessage("启用规则成功");
			}else{
				dwzResponse.setMessage("关闭规则成功");
			}
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			if(StringUtils.isNotEmpty(status) && status.endsWith("1")){
				dwzResponse.setMessage("启用规则失败");
			}else{
				dwzResponse.setMessage("关闭规则失败");
			}
		}
		return dwzResponse;
	}
	
	
	
}
