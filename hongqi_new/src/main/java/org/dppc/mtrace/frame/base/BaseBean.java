package org.dppc.mtrace.frame.base;

import java.io.Serializable;

import org.dppc.mtrace.frame.kit.ObjectKit;

/**
 * 重写了一些基本方法的 Bean 基类
 * 
 * @author maomh
 */
@SuppressWarnings("serial")
public abstract class BaseBean implements Serializable {
	
	@Override
	public int hashCode() {
		return ObjectKit.objectHashCode(this);
	}
	
	@Override
	public String toString() {
		return ObjectKit.objectToString(this);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return ObjectKit.objectEquals(this, obj);
	}
}
