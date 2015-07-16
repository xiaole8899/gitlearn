package org.dppc.mtrace.frame.base;

import java.io.Serializable;
import java.util.List;

import org.dppc.mtrace.frame.kit.ArrayKit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 公共 Dao
 * <br/>
 * 
 * @author maomh
 */
@Repository
public class PublicDao {
	
	@Autowired
	private SessionFactory sf;
	
	/**
	 * 获取 Hibernate-Session
	 * 
	 * @return
	 */
	public Session getSession() {
		if (sf == null) {
			throw new RuntimeException("SessionFactory 没有注入到 BaseDao 中， 请检查 root-context.xml！");
		}
		return sf.getCurrentSession();
	}
	
	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	public <T> Serializable insert(T entity) {
		return getSession().save(entity);
	}
	
	
	/**
	 * 保存或修改
	 * 
	 * @param entity
	 */
	public <T> void insertOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}
	
	
	/**
	 * 删除
	 * 
	 * @param entity
	 */
	public <T> void delete(T entity) {
		getSession().delete(entity);
	}
	
	
	/**
	 * 根据ID查找实体
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T select(Class<T> entityClass, Serializable id) {
		return (T) getSession().get(entityClass, id);
	}
	
	
	/**
	 * 创建Query对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public Query createQuery(String hql, Object...params) {
		Query query =getSession().createQuery(hql);
		if (ArrayKit.isNotEmpty(params)) {
			for (int i =0; i <params.length; ++i) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}
	
	
	/**
	 * 根据hql和参数查询实体集合
	 * 
	 * @param entityClass
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> select(String hql, Object...params) {
		return createQuery(hql, params).list();
	}
	
	
	/**
	 * 查询单一结果
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T selectSingleResult(String hql, Object...params) {
		Query query =createQuery(hql, params);
		List<T> list =query.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> void fillPages(Page<T> page, String hql, Object...params) {
		if (page == null) {
			throw new RuntimeException("分页对象不能为null");
		}
		Query query =createQuery(hql, params);
		
		// 分页控制
		query.setFirstResult(page.getPageIndex()-1);
		query.setMaxResults(page.getPageSize());
		page.setRows(query.list());
	}
}
