package com.tcf.kid.smart.framework.models;

/***
 * TODO TCF 封装控制器处理方法直接返回的响应数据，JSON序列化后直接写入输出流返回响应
 * @author TCF田超凡
 *
 */
public class Data {

	//TODO TCF 响应数据
	private Object data;
	
	public Object getData() {
		return data;
	}
	
	//TODO TCF 构造注入
	public Data(Object data)
	{
		this.data=data;
	}
	
	//TODO TCF 默认无参构造
	public Data()
	{
		
	}
}
