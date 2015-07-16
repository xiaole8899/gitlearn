package org.dppc.mtrace.frame.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.collections.CollectionUtils;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;

public class LoadGoods extends SimpleTagSupport {

	//商品集合
	private List<GoodsEntity> goodsList;
	
	private String result="";
	
	//表格开头
	private static final String TABLE_PREFIX="<table class=\"table\" width=\"1200\" layoutH=\"115\">";
	
	//表头开始
	private static final String THEAD_PREFIX="<thead><tr>";
	//表头结束
	private static final String THEAD_SUFFIX="</tr></thead>";
	
	//表体开始
	private static final String TBODY_PREFIX="<tbody>";
	//表体结束
	private static final String TBODY_SUFFIX="</tbody>";
	
	//表格结尾
	private static final String TABLE_SUFFIX="</table>";
	
	private int paddingTd=15;
	
	@Override
	public void doTag() throws JspException, IOException {
		PageContext pc =(PageContext) this.getJspContext();
		JspWriter jw=pc.getOut();
		jw.write(TABLE_PREFIX);
		jw.write(THEAD_PREFIX);
		jw.write("<th width='90px;'>图标</th>");
		jw.write("<th>商品编号</th>");
		jw.write("<th>商品名称</th>");
		jw.write("<th align='center' width='90px;'>上级商品编码</th>");
		jw.write("<th align='center' width='70px;'>商品类型</th>");
		jw.write("<th align='center'>商品昵称</th>");
		jw.write("<th align='center'>商品首字母</th>");
		jw.write("<th align='center'>商品拼音</th>");
		jw.write("<th align='center' width='60'>操作</th>");
		jw.write(THEAD_SUFFIX);
		jw.write(TBODY_PREFIX);
		for (GoodsEntity goods : goodsList) {
			recursiveGoodsList(goods,jw,0, "");
		}
		jw.write(TBODY_SUFFIX);
		jw.write(TABLE_SUFFIX);
		jw.flush();
		super.doTag();
	}
	
	public String recursiveGoodsList(GoodsEntity goodsEntity,JspWriter jw,int level, String css) throws IOException{
		jw.write("<tr level='" +level +"' class='" +css +"' goodsCode='"+goodsEntity.getGoodsCode()+"' precode='"+goodsEntity.getPreCode()+"'>");
		paddingTd=level*15;
		paddingTd+=3;
		if(goodsEntity.getGoodsList().size()==0){
			jw.write("<td style='padding-left:"+paddingTd+"px;'>");
			jw.write("</td>");
		}else{
			jw.write("<td style='padding-left:"+paddingTd+"px;'>");
			jw.write("<a level='" +level +"' goodsCode='"+goodsEntity.getGoodsCode()+" ' precode='"+goodsEntity.getPreCode()+"' style='cursor:pointer;' class='close-goods' title='关闭'></a>");
			jw.write("</td>");
			
		}
		jw.write("<td style='padding-left:"+paddingTd+"px;'>"+goodsEntity.getGoodsCode()+"</td>");
		jw.write("<td style='padding-left:"+paddingTd+"px;'>");
		jw.write(goodsEntity.getGoodsName()+"</td>");
		jw.write("<td>"+goodsEntity.getPreCode()+"</td>");
		String goodsType=goodsEntity.getGoodsStatus();
		if(goodsType.equals("0")){
			goodsType="肉";
		}else{
			goodsType="蔬菜";
		}
		jw.write("<td>"+goodsType+"</td>");
		jw.write("<td>"+goodsEntity.getGoodsAlias()+"</td>");
		jw.write("<td>"+goodsEntity.getFirstLetter()+"</td>");
		jw.write("<td>"+goodsEntity.getPinYin()+"</td>");
		jw.write("<td><a class=\"btnAdd\" href=\"goods/toAddGoods.do?preCode="+goodsEntity.getGoodsCode()+"\" target=\"dialog\" width=\"520\" height=\"340\" title=\"新增子级商品\">新增子级商品</a>");
		jw.write("<a class=\"btnEdit\" href=\"goods/toUpdateGoods.do?goodsId="+goodsEntity.getGoodsId()+"\" target=\"dialog\" width=\"520\" height=\"340\" title=\"编辑商品信息\">编辑</a>");
		jw.write("</td>");
		jw.write("</tr>");
		css =css +goodsEntity.getGoodsCode() +"-child ";
		if(CollectionUtils.isNotEmpty(goodsEntity.getGoodsList())){
			level++;
			for(GoodsEntity childGoods:goodsEntity.getGoodsList()){
				recursiveGoodsList(childGoods,jw,level, css);
			}
		}
		return result;
	}
	
	
	public List<GoodsEntity> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<GoodsEntity> goodsList) {
		this.goodsList = goodsList;
	}
	
	
}
