package com.tcf.kid.smart.framework.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tcf.kid.smart.framework.annotation.KidAspect;
import com.tcf.kid.smart.framework.helper.BeanHelper;
import com.tcf.kid.smart.framework.helper.ClassHelper;
import com.tcf.kid.smart.framework.util.ReflectUtil;

/***
 * TODO TCF AOP代理设计模式核心助手类
 * TODO TCF 初始化加载切面基类和自定义切面子类之间的映射Map
 * TODO TCF 初始化加载代理目标和代理目标的所有代理实例之间的映射Map
 * TODO TCF 调用代理工厂根据代理目标和代理目标的所有代理实例批量创建cglib动态代理实例并启动代理实例执行链
 * TODO TCF 将代理目标和代理目标的cglib动态代理实例存入Bean组件和Bean组件实例映射关系Map
 * @author 71485
 *
 */
public class AopHelper {
	
	//TODO TCF 初始化启动AOP代理容器
	static
	{
		try
		{
			//TODO TCF 加载切面基类和切面基类的所有自定义切面子类之间的映射Map
			Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap();
			
			//TODO TCF 加载代理目标和代理目标的所有代理实例之间的映射Map
			Map<Class<?>,List<Proxy>> targetMap=createTargetMap(proxyMap);
			
			if(targetMap!=null)
			{
				for(Map.Entry<Class<?>,List<Proxy>> targetEntry:targetMap.entrySet())
				{
					//TODO TCF 代理目标
					Class<?> targetClass=targetEntry.getKey();
					
					//TODO TCF 代理目标的所有代理实例
					List<Proxy> proxyList=targetEntry.getValue();
					
					//TODO TCF 调用代理工厂根据代理目标和代理目标的所有代理实例批量创建cglib动态代理实例并启动代理实例执行链
				    Object proxy=ProxyManager.newProxyInstance(targetClass,proxyList);
				    
				    //TODO TCF 将代理目标和代理目标的cglib动态代理实例存入Bean组件和Bean组件实例之间的映射关系Map
				    BeanHelper.putBeanMapping(targetClass,proxy);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//TODO TCF 加载使用KidAspect切面注解标注的自定义切面类
	public static Set<Class<?>> loadAspectClass(KidAspect kidAspect)
	{
		Set<Class<?>> classList=new HashSet<Class<?>>();
		
		//TODO TCF 代理目标类型
		Class<? extends Annotation> annotation=kidAspect.value();
		
		if(annotation!=null && !annotation.equals(KidAspect.class))
		{
			classList.addAll(ClassHelper.loadClassByAnnotation(annotation));
		}
		
		return classList;
	}
	
	//TODO TCF 加载切面基类和切面基类的所有切面子类之间的映射Map
	public static Map<Class<?>,Set<Class<?>>> createProxyMap()
	{
		Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<Class<?>,Set<Class<?>>>();
		
		//TODO TCF 加载切面基类的所有切面子类
		Set<Class<?>> aspectClassList=ClassHelper.loadClassBySuperClass(ProxyAspect.class);
		
		if(aspectClassList!=null && aspectClassList.size()>0)
		{
			for(Class<?> aspectClass:aspectClassList)
			{
				//TODO TCF 获取自定义切面子类上方标注的KidAspect切面注解
				if(aspectClass.isAnnotationPresent(KidAspect.class))
				{
					KidAspect kidAspect=aspectClass.getAnnotation(KidAspect.class);
					
					//TODO TCF 加载使用KidAspect切面注解标注的自定义切面子类
					Set<Class<?>> targetClassList=loadAspectClass(kidAspect);
					
					proxyMap.put(aspectClass,targetClassList);
				}
			}
		}
		
		return proxyMap;
	}
	
	//TODO TCF 加载代理目标和代理目标的所有代理实例之间的映射Map
	public static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap)
	{
		Map<Class<?>,List<Proxy>> targetMap=new HashMap<Class<?>,List<Proxy>>();
		
		if(proxyMap!=null)
		{
			for(Map.Entry<Class<?>,Set<Class<?>>> proxyEntry:proxyMap.entrySet())
			{
				//TODO TCF 切面基类
				Class<?> aspectClass=proxyEntry.getKey();
				
				//TODO TCF 切面基类的所有自定义切面子类
				Set<Class<?>> targetClassList=proxyEntry.getValue();
				
				if(targetClassList!=null && targetClassList.size()>0)
				{
					for(Class<?> targetClass:targetClassList)
					{
						//TODO TCF 切面基类实现的代理接口实例
						Proxy proxy=(Proxy)ReflectUtil.newInstance(aspectClass);
						
						if(targetMap.containsKey(targetClass))
						{
							targetMap.get(targetClass).add(proxy);
						}
						else
						{
							List<Proxy> proxyList=new ArrayList<Proxy>();
							proxyList.add(proxy);
							targetMap.put(targetClass,proxyList);
						}
					}
				}
			}
		}
		
		return targetMap;
	}
}
