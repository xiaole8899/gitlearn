package org.dppc.mtrace.app.analysis;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.analysis.entity.GoodsPriceEntity;
import org.dppc.mtrace.app.analysis.service.GoodsPriceService;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.kit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/goodsPrice")
public class GoodsPriceController {

	@Autowired
	private GoodsPriceService goodsPriceService;
	
	/**
	 * 
	 * @param status 标示若为0则直接跳转页面,否则需要加载数据带回
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/getGoodsPrice")
	public String getGoodsPrice(String status,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		List<GoodsEntity> goodsList=goodsPriceService.getGoodsEntities();
		request.setAttribute("goodsList", goodsList);
		if(StringUtils.isEmpty(status)){
			return "analysis/goodspricelist";
		}
		//判断开始日期不能为空!
		String startDate=request.getParameter("startDate");
		if(StringUtils.isEmpty(startDate)){
			DwzResponse dwzResponse=new DwzResponse();
    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
    		dwzResponse.setMessage("开始日期不能为空!");
    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
    		PrintWriterUtil.writeObject(response, dwzResponse);
    		return "analysis/goodspricelist";
		}
		request.setAttribute("startDate",startDate);
		String endDate=request.getParameter("endDate");
		//判断结束日期不能为空!
		if(StringUtils.isEmpty(endDate)){
			DwzResponse dwzResponse=new DwzResponse();
    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
    		dwzResponse.setMessage("结束日期不能为空!");
    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
    		PrintWriterUtil.writeObject(response, dwzResponse);
    		return "analysis/goodspricelist";
		}
		request.setAttribute("endDate",endDate);
		//判断开始日期不能大于结束日期!
		if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
			if(!DateUtil.compareTwoDate(startDate,endDate,DateUtil.DATEFORMAT)){
	    		DwzResponse dwzResponse=new DwzResponse();
	    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
	    		dwzResponse.setMessage("开始日期不能大于结束日期!");
	    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
	    		PrintWriterUtil.writeObject(response, dwzResponse);
	    		return "analysis/goodspricelist";
	    	}
			int df=DateUtil.compareTwoDateDiff(startDate,endDate,DateUtil.DATEFORMAT);
			
			if(df>15){
	    		DwzResponse dwzResponse=new DwzResponse();
	    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
	    		dwzResponse.setMessage("开始日期与结束日期间隔天数超过15天!");
	    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
	    		PrintWriterUtil.writeObject(response, dwzResponse);
	    		return "analysis/goodspricelist";
	    	}
		}
		//判断选择一种商品进行查询
		String goodsCode=request.getParameter("goodsList");
		
		if(StringUtils.isNotEmpty(goodsCode) && goodsCode.equals("0")){
			DwzResponse dwzResponse=new DwzResponse();
    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
    		dwzResponse.setMessage("请选择一种商品进行查询");
    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
    		PrintWriterUtil.writeObject(response, dwzResponse);
    		return "analysis/goodspricelist";
		}
		GoodsEntity goods=goodsPriceService.getGoodsEntity(goodsCode);
		request.setAttribute("goods",goods);
		request.setAttribute("goodsCode",goodsCode);
		int width=100;
		//条形图控件默认宽度为150
		int kjwidth=50;
		List<GoodsPriceEntity> goodsPriceList=goodsPriceService.getGoodsPriceList(goodsCode,startDate,endDate);;
		if(goodsPriceList!=null && goodsPriceList.size()>0){
			//日期数组
			String  allDate="";
			//价格数组
			String allPrice="";
			for(int i=0;i<goodsPriceList.size();i++){
				allDate+="'"+getStr(goodsPriceList.get(i).getDetailDate())+"', ";
				allPrice+=goodsPriceList.get(i).getGoodsPrice()+", ";
				width+=40;
				kjwidth+=30;
			}
			request.setAttribute("goodsPriceList",goodsPriceList);
			request.setAttribute("axisxstep",goodsPriceList.size());
			request.setAttribute("allDate",allDate);
			request.setAttribute("allPrice",allPrice);
			request.setAttribute("width",width);
			request.setAttribute("kjwidth",kjwidth);
		}else{
			request.setAttribute("result","未找到符合条件的记录!");
			return "analysis/goodspricelist";
		}
		return "analysis/goodspricesearch";
	}
	
	/**
	 * 处理日期后生成新的字符日期
	 * @param date
	 * @return
	 */
	public  String getStr(String date){
		int  month=Integer.parseInt(date.substring(5,7));
		int day=Integer.parseInt(date.substring(8,10));
		String newDateFormat=month+"-"+day;
		return newDateFormat;
	}
}
