package com.eeduspace.uuims.comm.util.base.encrypt;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import java.util.Properties;

/**
 * Author: dingran
 * Date: 2016/3/7
 * Description:
 */
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{

    private static final String key = ConfigureEncryptAndDecrypt.JDBC_DESC_KEY;

    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        try {
//                DesEncrypt des = new DesEncrypt();
            String username = props.getProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_USERNAME_KEY);
            if (username != null) {
                props.setProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_USERNAME_KEY, DESede.decrypt2String(Hex.fromHex(username), Hex.fromHex(key)));
            }

            String password = props.getProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_PASSWORD_KEY);
            if (password != null) {
                props.setProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_PASSWORD_KEY, DESede.decrypt2String(Hex.fromHex(password), Hex.fromHex(key)));
            }

            String url = props.getProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_URL_KEY);
            if (url != null) {
                props.setProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_URL_KEY, DESede.decrypt2String(Hex.fromHex(url), Hex.fromHex(key)));
            }

            String driverClassName = props.getProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_DRIVERCLASSNAME_KEY);
            if(driverClassName != null){
                props.setProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_DRIVERCLASSNAME_KEY, DESede.decrypt2String(Hex.fromHex(driverClassName), Hex.fromHex(key)));
            }
            String dbtype = props.getProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_TYPE_KEY);
            if(dbtype != null){
                props.setProperty(ConfigureEncryptAndDecrypt.JDBC_DATASOURCE_TYPE_KEY, DESede.decrypt2String(Hex.fromHex(dbtype), Hex.fromHex(key)));
            }
            super.processProperties(beanFactory, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanInitializationException(e.getMessage());
        }
    }
}
