package org.dppc.mtrace.app.log.service;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.log.entity.OperationLogEntity;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationLogService {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	/**
	 * 添加操作日志
	 * @param log
	 * @return
	 */
	@Transactional
	public boolean addOperationLog(OperationLogEntity log){
		sessionFactory.getCurrentSession().save(log);
		return true;
	}
	
	
	/**
	 * 根据编号查询操作日志
	 * @param logId
	 * @return
	 */
	@Transactional
	public OperationLogEntity getOperationLogEntity(int logId){
		return (OperationLogEntity) sessionFactory.getCurrentSession().load(OperationLogEntity.class,logId);
	}
	
	
	
	/**
	 * 根据编号修改操作日志备注及结果
	 * @param logId
	 * @return
	 */
	@Transactional
	public boolean alerCommentsByLogId(int logId){
		OperationLogEntity operationLogEntity=getOperationLogEntity(logId);
		operationLogEntity.setOptComment("服务器错误!");
		operationLogEntity.setOptResult("操作失败!");
		sessionFactory.getCurrentSession().update(operationLogEntity);
		return true;
	}
	
	/**
	 * 根据条件分页并排序
	 * @param page
	 * @param order
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void getOptList(Page<OperationLogEntity> page,OrderCondition order,OperationLogEntity operationLogEntity){
		StringBuffer hql=new StringBuffer();
		
		hql.append("select opr from OperationLogEntity opr where 1=1 ");
		if(StringUtils.isNotEmpty(operationLogEntity.getOptUserName())){
			hql.append(" and opr.optUserName like '%"+operationLogEntity.getOptUserName()+"%'");
		}
		if(StringUtils.isNotEmpty(operationLogEntity.getOptRealName())){
			hql.append(" and opr.optRealName like '%"+operationLogEntity.getOptRealName()+"%'");
		}
		if(StringUtils.isNotEmpty(operationLogEntity.getStartTime())){
			hql.append(" and opr.optTime >='"+operationLogEntity.getStartTime()+"'");
		}
		if(StringUtils.isNotEmpty(operationLogEntity.getEndTime())){
			hql.append(" and opr.optTime <='"+operationLogEntity.getEndTime()+"'");
		}
		Query count=sessionFactory.getCurrentSession().createQuery(hql.toString());
		if(count.list()!=null){
			page.setTotalRows(count.list().size());
		}else{
			page.setTotalRows(0);
		}
		//排序
		if(order!=null){
			hql.append(order.toSql());
		}
		Query queryList=sessionFactory.getCurrentSession().createQuery(hql.toString());
		queryList.setMaxResults(page.getPageSize());
		//设置开始位置(按下标)
		queryList.setFirstResult(page.getPageSize()*(page.getPageIndex()-1));
		page.setRows(queryList.list());
	}
	
	/**
	 * 根据操作记录删除日志
	 * @param OperlogId
	 * @return
	 */
	@Transactional
	public boolean deleteOperationLog(int  OperlogId){
		OperationLogEntity oprLog=getOperationLogEntity(OperlogId);
		sessionFactory.getCurrentSession().delete(oprLog);
		return true;
	}
}
