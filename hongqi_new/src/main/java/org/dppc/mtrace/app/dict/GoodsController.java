package org.dppc.mtrace.app.dict;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.dppc.mtrace.app.dict.service.GoodsService;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.kit.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 获取
	 * @return
	 * @throws IOException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 */
	@RequestMapping(value="/getList")
	public String getGoodsList(HttpServletRequest request,HttpServletResponse response,GoodsEntity gs) throws IOException, TransformerFactoryConfigurationError, TransformerException{
		List<GoodsEntity> goodsList=goodsService.recursiveGoodsList(gs);
		request.setAttribute("goodsList",goodsList);
		request.setAttribute("goods",gs);
		//加载商品类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		return "dic/goodslist";
	}
	
	/**
	 * 跳转添加页面
	 * @param gs
	 * @return
	 */
	@RequestMapping(value="/toAddGoods")
	public String toAddGoods(String preCode,HttpServletRequest request){
		//加载商品类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		request.setAttribute("preCode",preCode);
		return "dic/addgoods";
	}
	
	
	/**
	 * 添加商品
	 * @param merchants
	 * @throws IOException 
	 */
	@RequestMapping(value="/doAddGoods")
	@OperationLog(value="添加商品操作")
	public void doAddGoods(GoodsEntity gs,HttpServletRequest request,HttpServletResponse response) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		dwzResponse.setRel("");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		//默认设置未上传
		//更新数据时间
		gs.setGoodsState("1");
		int num=goodsService.addOrUpdateGoods(gs);
		if(num>0){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setMessage("操作成功!");
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("操作失败!");
		}
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 跳转编辑页面
	 * @param gs
	 * @return
	 */
	@RequestMapping(value="/toUpdateGoods")
	public String toUpdateGoods(String goodsId,HttpServletRequest request){
		GoodsEntity goods=goodsService.getGoods(goodsId);
		request.setAttribute("goods",goods);
		//加载商品类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		return "dic/updategoods";
	}
	
	/**
	 * 编辑商品
	 * @param merchants
	 * @throws IOException 
	 */
	@RequestMapping(value="/doUpdateGoods")
	@OperationLog(value="修改商品操作")
	public void doUpdateGoods(GoodsEntity gs,HttpServletRequest request,HttpServletResponse response) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		//dwzResponse.setNavTabId("");
		dwzResponse.setRel("");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		//默认设置未上传
		//更新数据时间
		gs.setGoodsState("1");
		int num=goodsService.addOrUpdateGoods(gs);
		if(num>0){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setMessage("操作成功!");
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			//dwzResponse.setNavTabId("merchantManage");
			//dwzResponse.setForwardUrl("goods/getList.do");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("操作失败!");
			//dwzResponse.setCallbackType("closeCurrent");
		}
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
}
