package com.tcf.kid.smart.framework.aop;

/***
 * TODO TCF AOP切面基类，实现代理接口
 * TODO TCF 定义每个AOP代理实例都需要调用的代理方法，定义各类增强处理方法供继承该类的自定义切面子类重写
 * TODO TCF 在代理方法中在目标方法执行前后动态织入增强处理
 * @author 71485
 *
 */
public class ProxyAspect implements Proxy{

	//TODO TCF 代理方法主体，定义每个代理实例都需要调用的代理方法，在该方法中织入增强处理
	@Override
	public Object doProxy(ProxyChain proxyChain) 
	{
		Object invokeResult=null;
		
		try
		{
			//TODO TCF 三层汉堡模式织入增强处理
			before();
			
			invokeResult=proxyChain.doProxyChain();
			
			afterReturning();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return invokeResult;
	}
	
	//TODO TCF ...自定义各类增强处理方法
	//TODO TCF 前置增强
	public void before()
	{
		System.out.println("====切面基类的前置增强处理方法执行====");
	}
	
	//TODO TCF 后置增强
	public void afterReturning()
	{
		System.out.println("====切面基类的后置增强处理方法执行====");
	}
}
