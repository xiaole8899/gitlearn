package org.dppc.mtrace.app.analysis.entity;



/**
 *  货品价格走势实体类
 * @author hle
 *
 */
public class GoodsPriceEntity {

	private String goodsName;  //商品名称
	
	private Double goodsPrice=0.0; //商品单价
	
	private String startDate;  //开始时间
	
	private String endDate;  //结束时间
	
	private String detailDate;    //统计的具体日期

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDetailDate() {
		return detailDate;
	}

	public void setDetailDate(String detailDate) {
		this.detailDate = detailDate;
	}
	
	
	
	
}
