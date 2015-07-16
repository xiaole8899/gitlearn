package org.dppc.mtrace.app;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.user.entity.UserEntity;
import org.dppc.mtrace.frame.interceptor.OperatorInterceptor;

 /** 放置一些常量，比如UserKey什么的
 * 
 * @author maomh
 *
 */
public class AppConstant {
	
	//session 验证码 key
	public static final String SESSION_YZM_TOKEN=AppConstant.class.getName()+"Session_Yzm_Token";

	//request中的日志编号
	public static final String REQUEST_OPR_LOG_ID=OperatorInterceptor.class.getName()+"OPR_LOG_ID";
	
    // 供货市场的企业类型
	public final static Map<String,String> supplyType  = new LinkedHashMap<String,String>();
	
	//商户类型
	public final static Map<String,String> businessType=new HashMap<String,String>();
	
	//商户卡类型
	public final static Map<String,String> cardType=new HashMap<String, String>();
	
	//商户卡性质
	public final static Map<String,String> cardKind=new HashMap<String, String>();
	
	//进场的凭证类型有关肉
	public final static Map<String,String> voucherType = new HashMap<String,String>();
	
	//进场凭证类型有关蔬菜
	public final static Map<String,String> voucherTypeV =new HashMap<String,String>();
	
	//出场订单状态
	public final static Map<String,String> orderStatus =new HashMap<String,String >();

	//交易类型
	public final static Map<String,String> tradeType=new HashMap<String, String>();
	

	//商户经营类型
	public final static Map<String,String> bizType=new HashMap<String, String>();
	
	//买方类型
	public final static Map<String,String> buyerType=new  HashMap<String, String>();
	
	
	static{
		orderStatus.put("0", "已完成");
		orderStatus.put("1", "未完成");
		orderStatus.put("2", "取消");
		
		supplyType.put("0001", "屠宰企业");
		supplyType.put("0002", "批发企业");
		supplyType.put("0003", "菜市场企业");
		supplyType.put("0004", "超市企业");
		supplyType.put("0005", "团体消费单位");
		supplyType.put("0006", "其他");
		
		//businessType.put("1001","摊户");
		businessType.put("1002","供货商");
		businessType.put("1003","批发商");
		//businessType.put("1004","散户");
		//businessType.put("1005","其他");
		
		cardType.put("01","服务卡");
		/*cardType.put("02","银行卡");*/
		
		cardKind.put("01","主卡");
		cardKind.put("02","副卡");
		
		voucherType.put("1", "交易凭证号");
		voucherType.put("2", "动物产品检疫合格证号");
		voucherType.put("3", "肉品质检验合格证号");
		voucherType.put("6", "批次号"); 

		voucherTypeV.put("4", "蔬菜产地证明号"); 
		voucherTypeV.put("5", "蔬菜检测合格证号"); 
		voucherTypeV.put("6", "批次号"); 
		//自动生成11
		
		tradeType.put("0","现金");
		tradeType.put("1","刷卡");
		
		bizType.put("0","肉类");
		bizType.put("1","蔬菜");
		
		buyerType.put("0","未注册");
		buyerType.put("1","已注册");
	}
	
	/**
	 * 
	 * 获取当前登陆
	 * @author SunLong
	 * @return UserEntity 
	 */
	public static UserEntity getUserEntity (HttpServletRequest request){
		
		return (UserEntity)request.getSession().getAttribute("USER_SESSION_KEY");
	
	}
	
	/**
	 * 
	 * 获取当前节点信息
	 * @author SunLong
	 * @return SupplyMarketEntity
	 */
	
	public static SupplyMarketEntity getSupplyMarketEntity (HttpServletRequest request){
		
		return (SupplyMarketEntity)request.getServletContext().getAttribute("NODE_INFO");
	
	}
	/**
	 * 从session中获取验证码
	 * @param request
	 * @return 
	 */
	public static  String getYzmFromSession(HttpServletRequest request){
		return (String) request.getSession().getAttribute(SESSION_YZM_TOKEN);
	}
}


