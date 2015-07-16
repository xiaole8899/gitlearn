package org.dppc.cardws.app.rmi;

import org.dppc.cardws.frame.card.exp.CardException;

/**
 * 卡操作 RMI 远程服务接口
 * 
 * @author maomh
 *
 */
public interface RMICardService {
	
	/**
	 * 读取用户信息 - 返回 Json 字符串
	 * 
	 * @return
	 * @throws CardException
	 */
	public String readUserInfo() throws CardException;
	
	
	/**
	 * 写入用户信息 - 传入 Json 字符串
	 * 
	 * @throws CardException
	 */
	public void writeUserInfo(String infoJson) throws CardException;
}
