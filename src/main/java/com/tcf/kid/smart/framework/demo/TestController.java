package com.tcf.kid.smart.framework.demo;

import java.util.HashMap;
import java.util.Map;

import com.tcf.kid.smart.framework.annotation.KidAction;
import com.tcf.kid.smart.framework.annotation.KidController;
import com.tcf.kid.smart.framework.models.Data;
import com.tcf.kid.smart.framework.models.Param;
import com.tcf.kid.smart.framework.models.View;

/***
 * TODO TCF �Զ���MVC������
 * @author 71485
 *
 */
@KidController
public class TestController {

	//TODO TCF ����Ŀ��
	public void showProxyResult(Param param)
	{
		System.out.println("====����Ŀ�귽��ִ��====");
	}
	
	//TODO TCF EXAMPLE 1 ������ͼ������ʽ����
	@KidAction(url="/toPage.do",method="GET")
	public View toPage(Param param)
	{
		return new View("example.jsp",null);
	}
	
	//TODO TCF EXAMPLE 2 ���ص�����Ӧ���ݣ�JSON���л���ֱ��д���������������Ӧ
	@KidAction(url="/show.do",method="GET")
	public Data show(Param param)
	{
		if(param!=null)
		{
			//TODO TCF ��̨���յ����������
			Map<String,Object> paramMap=param.getParamMap();
			
			if(paramMap!=null)
			{
				for(Map.Entry<String,Object> paramEntry:paramMap.entrySet())
				{
					//TODO TCF ���������
					String paramName=paramEntry.getKey();
					
					//TODO TCF �������ֵ
					String paramValue=paramEntry.getValue()!=null?paramEntry.getValue().toString():"";
					
					System.out.println("��̨���յ������������===>"+paramName);
					System.out.println("��̨���յ����������ֵ===>"+paramValue);
				}
			}
		}
		
		//TODO TCF ������Ӧ����
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("name","����");
		
		return new Data(dataMap);
	}
}