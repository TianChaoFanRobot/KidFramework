package com.tcf.kid.smart.framework.models;

import java.util.Map;

/***
 * TODO TCF ��װ�����������������ص���ͼ������ʽ����
 * @author 71485
 *
 */
public class View {

	//TODO TCF ��ͼ����·��
	private String viewPath;
	
	//TODO TCF �󶨵�ģ�Ͳ���
	private Map<String,Object> modelParams;
	
	public String getViewPath() {
		return viewPath;
	}
	public Map<String, Object> getModelParams() {
		return modelParams;
	}
	
	//TODO TCF ����ע��
	public View(String viewPath,Map<String,Object> modelParams)
	{
		this.viewPath=viewPath;
		this.modelParams=modelParams;
	}
	
	//TODO TCF Ĭ���޲ι���
	public View()
	{
		
	}
}