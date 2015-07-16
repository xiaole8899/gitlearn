package org.dppc.mtrace.app.dict.service;

import java.util.List;

import javax.transaction.Transactional;

import org.dppc.mtrace.app.dict.entity.CountyEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地区业务类
 * @author weiyuzhen
 * @param <CountyEntity>
 *
 */

@Service
@Transactional
public class CountyService {
	@Autowired
	SessionFactory sessionFanctory ;
	
	@SuppressWarnings("unchecked")
	public List<CountyEntity> findCountybyPrId(String prId){
		StringBuilder hql = new StringBuilder();
		hql.append("select c from CountyEntity c where c.prentId =? ");
		Query query =  sessionFanctory.getCurrentSession().createQuery(hql.toString()).setParameter(0, prId);
		return query.list();
		
	}
}
