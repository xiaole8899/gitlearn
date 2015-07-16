package org.dppc.mtrace.app.dict.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 商品类
 * @author weiyuzhen
 */
@Entity
@Table(name="t_goods")
public class GoodsEntity extends BaseEntity {
	

	private static final long serialVersionUID = 1L;
	private int goodsId;            //主键
	private String goodsCode ;      //商品编码
	private String goodsName;       //商品名称
	private String goodsAlias;      //商品别名
	private String preCode;         //上级编码
	private String goodsStatus;     // 0肉1蔬菜
	private String goodsState;      //状态   0 无效   1-有效（默认为1）
	private String pinYin;          //拼音编码
	private String firstLetter;     //首字母
	
	/**
	 * 存放子级
	 */
	private List<GoodsEntity> goodsList=new ArrayList<GoodsEntity>();
	
	@Id
	@Column(name="goods_Id",length=20)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	
	@Column(name="goods_name",length=200)
	public String getGoodsName() {
		return goodsName;
	}
	
	@Column(name="goods_code", length=10)
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@Column(name="pre_code",length=10)
	public String getPreCode() {
		return preCode;
	}
	public void setPreCode(String preCode) {
		this.preCode = preCode;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Column(name="goods_alias" ,length=200)
	public String getGoodsAlias() {
		return goodsAlias;
	}
	public void setGoodsAlias(String goodsAlias) {
		this.goodsAlias = goodsAlias;
	}

	@Column(name="goods_status",length=10)
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
	@Column(name="goods_state",length=4)
	public String getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}
	@Column(name="pin_yin",length=200)
	public String getPinYin() {
		return pinYin;
	}
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}
	
	@Column(name="first_letter")
	public String getFirstLetter() {
		return firstLetter;
	}
	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
	
	@Transient 
	public List<GoodsEntity> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<GoodsEntity> goodsList) {
		this.goodsList = goodsList;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Integer.parseInt(goodsCode);
	}
	
	
	
	
	
}
