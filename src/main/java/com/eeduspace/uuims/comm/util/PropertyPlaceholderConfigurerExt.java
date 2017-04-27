package com.eeduspace.uuims.comm.util;

import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author zhuchaowei
 * 2016年4月11日
 * Description  spring 加载properties文件解密类  unicode解码操作
 */
public class PropertyPlaceholderConfigurerExt extends PropertyPlaceholderConfigurer{
    private final Logger logger = LoggerFactory.getLogger(PropertyPlaceholderConfigurer.class);

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		// TODO Auto-generated method stub
		Set<Object> set=props.keySet();
		String temp=null;
		for (Object object : set) {
			logger.debug("properties.KEY="+ object.toString());
			temp=props.getProperty(object.toString());
			logger.debug("properties.VALUE="+ temp);
			if(temp!=null){
				props.setProperty(object.toString(), UnicodeConvert.unicodeToString(temp));
			}
		}
		super.processProperties(beanFactoryToProcess, props);
	}
}
