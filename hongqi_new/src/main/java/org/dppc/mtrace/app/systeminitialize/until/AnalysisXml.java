package org.dppc.mtrace.app.systeminitialize.until;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.menu.entity.FunctionEntity;
import org.dppc.mtrace.app.menu.entity.MenuEntity;
import org.dppc.mtrace.app.role.entity.RoleEntity;
import org.dppc.mtrace.app.user.entity.UserEntity;

public class AnalysisXml {

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static Map analisisXml(Document document) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Map resultMap = new HashMap();
		List<UserEntity> userList = new ArrayList<UserEntity>();
		List<RoleEntity> roleList = new ArrayList<RoleEntity>();
		List<MenuEntity> menuList = new ArrayList<MenuEntity>();
		List<FunctionEntity> functionList = new ArrayList<FunctionEntity>();
		SupplyMarketEntity sm = new SupplyMarketEntity();
		Element rootElt = document.getRootElement(); // 获取根节点
       
		//  UserEntity 节点信息
		Iterator UserEntity = rootElt.elementIterator("UserEntity"); 
        
        // 遍历head节点
        while (UserEntity.hasNext()) {
        	UserEntity userEntity = new UserEntity();
            Element user = (Element) UserEntity.next();
            int userId = Integer.parseInt(user.elementTextTrim("userId"));					
        	String userName = user.elementTextTrim("userName");			
        	String passWord = user.elementTextTrim("passWord");			
        	String realName = user.elementTextTrim("realName");			
        	String department = user.elementTextTrim("department");	 		
        	String email = user.elementTextTrim("email");	 			
        	String mobilePhone = user.elementTextTrim("mobilePhone");	 		
        	String telephone = user.elementTextTrim("telephone");	 		
        	String position = user.elementTextTrim("position");	 		
        	userEntity.setUserId(userId);
        	userEntity.setUserName(userName);
        	userEntity.setPassWord(passWord);
        	userEntity.setRealName(realName);
        	userEntity.setDepartment(department);
        	userEntity.setEmail(email);
        	userEntity.setMobilePhone(mobilePhone);
        	userEntity.setTelephone(telephone);
        	userEntity.setPosition(position);
            Iterator roles = user.elementIterator("roles"); // 获取子节点head下的子节点script
            Set<RoleEntity> sr = new HashSet<RoleEntity>();
            
            // 遍历Header节点下的Response节点
            while (roles.hasNext()) {
                Element itemEle = (Element) roles.next();
                List<Element> eleRole = itemEle.elements("roleId");
                for(int i = 0;i<eleRole.size();i++){
                	int ss = Integer.parseInt(eleRole.get(i).getText());
                	RoleEntity roleEntity = new RoleEntity();
                	roleEntity.setRoleId(Integer.parseInt(eleRole.get(i).getText()));
                	sr.add(roleEntity);
                }
            }
            userEntity.setRoles(sr);
            userList.add(userEntity);
        }
        
        
        //  RoleEntity 节点信息
		Iterator RoleEntity = rootElt.elementIterator("RoleEntity"); 
        
        // 遍历head节点
        while (RoleEntity.hasNext()) {
        	RoleEntity roleEntity = new RoleEntity();
            Element role = (Element) RoleEntity.next();
            int roleId = Integer.parseInt(role.elementTextTrim("roleId"));					
        	String roleName = role.elementTextTrim("roleName");			
        	String descriptions = role.elementTextTrim("descriptions");			
        	roleEntity.setRoleId(roleId);
        	roleEntity.setRoleName(roleName);
        	roleEntity.setDescriptions(descriptions);
            Iterator menus = role.elementIterator("menus"); // 获取子节点head下的子节点script
            Set<MenuEntity> smenu = new HashSet<MenuEntity>();
            // 遍历Header节点下的Response节点
            while (menus.hasNext()) {
                Element itemEle = (Element) menus.next();
                List<Element> eleMenu = itemEle.elements("menuId");
                for(int i = 0;i<eleMenu.size();i++){
                	int ss = Integer.parseInt(eleMenu.get(i).getText());
                	MenuEntity menuEntity = new MenuEntity();
                	menuEntity.setMenuId(Integer.parseInt(eleMenu.get(i).getText()));
                	smenu.add(menuEntity);
                }
            }
            roleEntity.setMenu(smenu);
            roleList.add(roleEntity);
        }
        
        
        
        //  MenuEntity 节点信息
		Iterator MenuEntity = rootElt.elementIterator("MenuEntity"); 
        
        // 遍历head节点
        while (MenuEntity.hasNext()) {
        	MenuEntity menuEntity = new MenuEntity();
            Element menu = (Element) MenuEntity.next();
            int menuId = Integer.parseInt(menu.elementTextTrim("menuId"));					
        	String menuName = menu.elementTextTrim("menuName");			
        	String url = menu.elementTextTrim("url");	
        	String description = menu.elementTextTrim("description");	
        	String sort = menu.elementTextTrim("sort");	
        	String parentId = menu.elementTextTrim("parentId");	
        	String rel = menu.elementTextTrim("rel");	
        	menuEntity.setMenuId(menuId);
        	menuEntity.setMenuName(menuName);
        	menuEntity.setUrl(url);
        	menuEntity.setDescription(description);
        	menuEntity.setSort(Integer.parseInt(sort));
        	menuEntity.setParentId(Integer.parseInt(parentId));
        	menuEntity.setRel(rel);
        	menuList.add(menuEntity);
        }
        
        //  FunctionEntity 节点信息
		Iterator FunctionEntity = rootElt.elementIterator("FunctionEntity"); 
        
        // 遍历head节点
        while (FunctionEntity.hasNext()) {
        	FunctionEntity functionEntity = new FunctionEntity();
            Element function = (Element) FunctionEntity.next();
            int functionId = Integer.parseInt(function.elementTextTrim("functionId"));					
        	String functionName = function.elementTextTrim("functionName");			
        	String url = function.elementTextTrim("url");	
        	String description = function.elementTextTrim("description");	
        	String menuId = function.elementTextTrim("menuId");	
        	functionEntity.setFunctionId(functionId);
        	functionEntity.setFunctionName(functionName);
        	functionEntity.setUrl(url);
        	functionEntity.setDescription(description);
        	MenuEntity me = new MenuEntity();
        	me.setMenuId(Integer.parseInt(menuId));
        	functionEntity.setMenu(me);
        	functionList.add(functionEntity);
        }
        
        //  SupplyMarketEntity 节点信息
		Iterator SupplyMarketEntity = rootElt.elementIterator("SupplyMarketEntity"); 
        
        // 遍历head节点
        while (SupplyMarketEntity.hasNext()) {
        	
            Element smele = (Element) SupplyMarketEntity.next();
            int suId = Integer.parseInt(smele.elementTextTrim("suId"));					
        	String no = smele.elementTextTrim("no");			
        	String name = smele.elementTextTrim("name");	
        	String regId = smele.elementTextTrim("regId");	
        	String sNodeType = smele.elementTextTrim("sNodeType");	
        	String areaNo = smele.elementTextTrim("areaNo");
        	String areaName = smele.elementTextTrim("areaName");
        	String recordDate = smele.elementTextTrim("recordDate");
        	String legalRepresent = smele.elementTextTrim("legalRepresent");
        	String addr = smele.elementTextTrim("addr");
        	String tel = smele.elementTextTrim("tel");	
        	String updateTime = smele.elementTextTrim("updateTime");
        	sm.setSuId(suId);
        	sm.setNo(areaNo);
        	sm.setName(areaName);
        	sm.setRegId(regId);
        	sm.setsNodeType(sNodeType);
        	sm.setAreaNo(areaNo);
        	sm.setAreaName(areaName);
        	sm.setIsModified("0");
        	if(recordDate.equals("")){
        		sm.setRecordDate(null);
        	}else{
        		sm.setRecordDate(sdf.parse(recordDate));
        	}
        	sm.setLegalRepresent(legalRepresent);
        	sm.setAddr(addr);
        	sm.setTel(tel);
        	if(updateTime.equals("")){
        		sm.setUpdateTime(null);
        	}else{
        		sm.setUpdateTime(sdf.parse(updateTime));
        	}
        }
        resultMap.put("userList", userList);
        resultMap.put("roleList", roleList);
        resultMap.put("menuList", menuList);
        resultMap.put("functionList", functionList);
        resultMap.put("sm", sm);
        return resultMap;
	}
}
