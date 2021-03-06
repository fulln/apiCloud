package com.fulln.me.api.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @AUthor: fulln
 * @Description: 获取bean(要想扫描到必须放到同一个包下面)
 * @Date : Created in  12:58  2019/1/19.
 */

@Component
public class SpringContextsUtil implements ApplicationContextAware {

  /**
   * Spring应用上下文环境
   */

  private static ApplicationContext applicationContext;
  /**
  * 实现ApplicationContextAware接口的回调方法，设置上下文环境  
  * @param applicationContext
  * @throws BeansException
  */
  @Override
  public void setApplicationContext(@Nullable @Autowired ApplicationContext applicationContext) throws BeansException {
    if(SpringContextsUtil.applicationContext == null){
      SpringContextsUtil.applicationContext  = applicationContext;
    }
  }
 
  /**
  * @return ApplicationContext
  */
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }
 
  /**
  * 获取对象  
  * @param name
  * @return Object 一个以所给名字注册的bean的实例
  * @throws BeansException
  */
  public static Object getBean(String name) throws BeansException {
    return applicationContext.getBean(name);
  }
  /**
  * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
  * @param name
  * @return boolean
  */
  public static boolean containsBean(String name) {
    return applicationContext.containsBean(name);
  }
 
  /**
  * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
  * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）  
  * @param name
  * @return boolean
  * @throws NoSuchBeanDefinitionException
  */
  public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
    return applicationContext.isSingleton(name);
  }
 
  /**
  * @param name
  * @return Class 注册对象的类型
  * @throws NoSuchBeanDefinitionException
  */
  public static Class getType(String name) throws NoSuchBeanDefinitionException {
    return applicationContext.getType(name);
  }
 
  /**
  * 如果给定的bean名字在bean定义中有别名，则返回这些别名  
  * @param name
  * @return
  * @throws NoSuchBeanDefinitionException
  */
  public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
    return applicationContext.getAliases(name);
  }

  /**
   * 通过class获取Bean.
   * @param clazz
   *          class
   * @param <T>
   * @return
   */
  public static <T> T getBean(Class<T> clazz){
    return getApplicationContext().getBean(clazz);
  }

  /**
   * 获取类型为requiredType的对象
   * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
   * @param name       bean注册名
   * @param clazz 返回对象类型
   * @return Object 返回requiredType类型对象
   * @throws BeansException
   */
  public static <T> T getBean(String name,Class<T> clazz) throws BeansException{
    return getApplicationContext().getBean(name, clazz);
  }
}