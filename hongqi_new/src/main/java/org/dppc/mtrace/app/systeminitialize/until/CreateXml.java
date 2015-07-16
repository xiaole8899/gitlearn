package org.dppc.mtrace.app.systeminitialize.until;

import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.menu.entity.FunctionEntity;
import org.dppc.mtrace.app.menu.entity.MenuEntity;
import org.dppc.mtrace.app.role.entity.RoleEntity;
import org.dppc.mtrace.app.user.entity.UserEntity;

public class CreateXml{
	public static Document createXml(List<UserEntity> userList,List<RoleEntity> roleList, List<MenuEntity> menuList,List<FunctionEntity> functionList,SupplyMarketEntity sm){
		Document document = DocumentHelper.createDocument();
		 Element mtrace = document.addElement("mtrace");//添加文档根
		//user表
		 mtrace.addComment("This is the user of the table");//加入一行注释
		 
		 for(UserEntity user : userList){
			 Element eleUser = mtrace.addElement("UserEntity"); //添加root的子节点
			 Element userId = eleUser.addElement("userId");
			 userId.addText(String.valueOf(user.getUserId()));
			 Element userName = eleUser.addElement("userName");
			 userName.addText(user.getUserName());
			 Element passWord = eleUser.addElement("passWord");
			 passWord.addText(user.getPassWord());
			 Element realName = eleUser.addElement("realName");
			 realName.addText(user.getRealName());
			 Element department = eleUser.addElement("department");
			 department.addText(user.getDepartment());
			 Element email = eleUser.addElement("email");
			 if(user.getEmail()==null){
				 user.setEmail("");
			 }
			 email.addText(user.getEmail());
			 Element mobilePhone = eleUser.addElement("mobilePhone");
			 if(user.getMobilePhone()==null){
				 user.setMobilePhone("");
			 }
			 mobilePhone.addText(user.getMobilePhone());
			 Element telephone = eleUser.addElement("telephone");
			 if(user.getTelephone()==null){
				 user.setTelephone("");
			 }
			 telephone.addText(user.getTelephone());
			 Element position = eleUser.addElement("position");
			 position.addText(user.getPosition());
			 Element roles = eleUser.addElement("roles");
			 
			 for(RoleEntity role : user.getRoles()){
				 Element roleId = roles.addElement("roleId");
				 roleId.addText(String.valueOf(role.getRoleId()));
			 }
		 }
		 
		//role表
		 mtrace.addComment("This is the role of the table");//加入一行注释
		 
		 for(RoleEntity role : roleList){
			 Element eleRole = mtrace.addElement("RoleEntity"); //添加root的子节点
			 Element roleId = eleRole.addElement("roleId");
			 roleId.addText(String.valueOf(role.getRoleId()));
			 Element roleName = eleRole.addElement("roleName");
			 roleName.addText(role.getRoleName());
			 if(role.getDescriptions()==null){
				 role.setDescriptions("");
			 }
			 Element descriptions = eleRole.addElement("descriptions");
			 descriptions.addText(role.getDescriptions());
			 Element elemenus = eleRole.addElement("menus");
			 for(MenuEntity menu : role.getMenu()){
				 Element menuId = elemenus.addElement("menuId");
				 menuId.addText(String.valueOf(menu.getMenuId()));
			 }
		 }
		 
		//menu表
		 mtrace.addComment("This is the menu of the table");//加入一行注释
		 
		 for(MenuEntity menu : menuList){
			 Element eleMenu = mtrace.addElement("MenuEntity"); //添加root的子节点
			 Element menuId = eleMenu.addElement("menuId");
			 menuId.addText(String.valueOf(menu.getMenuId()));
			 Element menuName = eleMenu.addElement("menuName");
			 menuName.addText(menu.getMenuName());
			 Element url = eleMenu.addElement("url");
			 url.addText(menu.getUrl());
			 Element description = eleMenu.addElement("description");
			 if(menu.getDescription()==null){
				 menu.setDescription("");
			 }
			 description.addText(menu.getDescription());
			 Element sort = eleMenu.addElement("sort");
			 sort.addText(String.valueOf(menu.getSort()));
			 Element parentId = eleMenu.addElement("parentId");
			 parentId.addText(String.valueOf(menu.getParentId()));
			 Element rel = eleMenu.addElement("rel");
			 if(menu.getRel()==null){
				 menu.setRel("/");
			 }
			 rel.addText(menu.getRel());
		 }
		 
		//function表
		 mtrace.addComment("This is the function of the table");//加入一行注释
		 
		 for(FunctionEntity function : functionList){
			 Element eleFunction = mtrace.addElement("FunctionEntity"); //添加root的子节点
			 Element functionId = eleFunction.addElement("functionId");
			 functionId.addText(String.valueOf(function.getFunctionId()));
			 Element functionName = eleFunction.addElement("functionName");
			 functionName.addText(function.getFunctionName());
			 Element url = eleFunction.addElement("url");
			 url.addText(function.getUrl());
			 Element description = eleFunction.addElement("description");
			 if(function.getDescription()==null){
				 function.setDescription("");
			 }
			 description.addText(function.getDescription());
			 Element menuId = eleFunction.addElement("menuId");
			 menuId.addText(String.valueOf(function.getMenu().getMenuId()));
		 }
		 
		//node_info表
		 mtrace.addComment("This is the node_info of the table");//加入一行注释
		 if(sm!=null){
			 Element eleMarket = mtrace.addElement("SupplyMarketEntity"); //添加root的子节点
			 Element suId = eleMarket.addElement("suId");
			 suId.addText(String.valueOf(sm.getSuId()));
			 Element no = eleMarket.addElement("no");
			 if(sm.getNo()==null){
				 sm.setNo("");
			 }
			 no.addText(sm.getNo());
			 Element name = eleMarket.addElement("name");
			 if(sm.getName()==null){
				 sm.setName("");
			 }
			 name.addText(sm.getName());
			 Element regId = eleMarket.addElement("regId");
			 if(sm.getRegId()==null){
				 sm.setRegId("");
			 }
			 regId.addText(sm.getRegId());
			 Element sNodeType = eleMarket.addElement("sNodeType");
			 if(sm.getsNodeType()==null){
				 sm.setsNodeType("");
			 }
			 sNodeType.addText(sm.getsNodeType());
			 Element areaNo = eleMarket.addElement("areaNo");
			 if(sm.getAreaNo()==null){
				 sm.setAreaNo("");
			 }
			 areaNo.addText(String.valueOf(sm.getAreaNo()));
			 Element areaName = eleMarket.addElement("areaName");
			 if(sm.getAreaName()==null){
				 sm.setAreaName("");
			 }
			 areaName.addText(sm.getAreaName());
			 Element recordDate = eleMarket.addElement("recordDate");
			 if(sm.getRecordDate()==null){
				 recordDate.addText(String.valueOf(""));
			 }else{
				 recordDate.addText(String.valueOf(sm.getRecordDate()));
			 }
			 Element legalRepresent = eleMarket.addElement("legalRepresent");
			 if(sm.getLegalRepresent()==null){
				 sm.setLegalRepresent("");
			 }
			 legalRepresent.addText(sm.getLegalRepresent());
			 Element addr = eleMarket.addElement("addr");
			 if(sm.getAddr()==null){
				 sm.setAddr("");
			 }
			 addr.addText(sm.getAddr());
			 Element tel = eleMarket.addElement("tel");
			 if(sm.getTel()==null){
				 sm.setTel("");
			 }
			 tel.addText(sm.getTel());
			 Element updateTime = eleMarket.addElement("updateTime");
			 if(sm.getUpdateTime()==null){
				 updateTime.addText(String.valueOf(""));
			 }else{
				 updateTime.addText(String.valueOf(sm.getUpdateTime()));
			 }
		 }
		 return document;
	}

}
