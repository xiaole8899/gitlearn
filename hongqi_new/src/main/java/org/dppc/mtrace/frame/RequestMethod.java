package org.dppc.mtrace.frame;

public enum RequestMethod {
	GET,	/** 请求指定的页面信息，并返回实体主体。 */
	
	POST,	/** 请求服务器接受所指定的文档作为对所标识的URI的新的从属实体。 */
	
	PUT,	/** 从客户端向服务器传送的数据取代指定的文档的内容。 */
	
	DELETE,	/** 请求服务器删除指定的页面。 */
	
	ALL		/** 所有类型的请求 */
}
