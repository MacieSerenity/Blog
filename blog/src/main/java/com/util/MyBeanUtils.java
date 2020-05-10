package com.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/10 23:47
 * Description:
 * Version: V1.0
 */
public class MyBeanUtils {
	/**
	 * @Description: 用于将对象中的null值分离出来
	 * @Method: getNullPropertyNames
	 * @Param: [object]
	 * @return: java.lang.String[]
	 * @Author: MacieSerenity
	 * @Date: 2020/5/10
	 */
	public static String[] getNullPropertyNames(Object object){

		/**
		 * BeanWrapperImpl类是对BeanWrapper接口的默认实现，
		 * 它包装了一个bean对象，缓存了bean的内省结果。
		 * 并可以访问bean的属性、设置bean的属性值。
		 * BeanWrapperImpl
		 * 可以将数组、集合类型的属性转换成指定特殊类型的数组或集合。
		 */

		BeanWrapper beanWrapper=new BeanWrapperImpl(object);

		/**
		 * 内省(Introspector) 是Java 语言对JavaBean类属性、事件的一种缺省处理方法。
		 * PropertyDescriptor类表示JavaBean类通过存储器导出一个属性。
		 * 1. getPropertyType()，获得属性的Class对象；
		 * 2. getReadMethod()，获得用于读取属性值的方法；
		 * 3. getWriteMethod()，获得用于写入属性值的方法；
		 * 4. hashCode()，获取对象的哈希值；
		 * 5. setReadMethod(Method readMethod)，设置用于读取属性值的方法；
		 * 6. setWriteMethod(Method writeMethod)，设置用于写入属性值的方法。
		 */
		PropertyDescriptor[] descriptors=beanWrapper.getPropertyDescriptors();

		List<String> nullPropertyNames=new ArrayList<>();

		for (PropertyDescriptor descriptor:descriptors){
			String propertyName=descriptor.getName();
			if (beanWrapper.getPropertyValue(propertyName)==null){
				nullPropertyNames.add(propertyName);
			}
		}
		return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
	}


}
