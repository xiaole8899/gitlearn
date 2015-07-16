package org.dppc.mtrace.frame.base;

import org.apache.commons.lang.StringUtils;


/**
 * 排序条件对象
 * 
 * @author maomh
 *
 */
public class OrderCondition {
	private String field;
	private String direction =StringUtils.EMPTY;
	
	public OrderCondition() {}
	
	public OrderCondition(String field, String direction) {
		this.field =StringUtils.defaultIfBlank(field, StringUtils.EMPTY);
		if (StringUtils.isNotEmpty(field)) {
			this.direction =StringUtils.defaultIfBlank(direction, StringUtils.EMPTY);
		}
	}
	
	@Override
	public String toString() {
		return field + " " +direction;
	}
	
	public String toSql() {
		if (StringUtils.isNotEmpty(field)) {
			return " order by " +toString();
		}
		return StringUtils.EMPTY;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}
