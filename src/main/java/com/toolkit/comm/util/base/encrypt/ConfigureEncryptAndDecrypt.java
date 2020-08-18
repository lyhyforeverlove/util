package com.toolkit.comm.util.base.encrypt;
/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class ConfigureEncryptAndDecrypt {
    public static final String CHAR_ENCODING = "UTF-8";
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    public static final String AES_ALGORITHM = "AES";
    public static final String DES_ALGORITHM = "DESede";//3des加解密算法工具类;
    public static final String MD5_ALGORITHM = "MD5";
    public static final String SHA_ALGORITHM = "SHA";
    public static final String SEPERATOR = "$";

    /******************************JDBC相关BEGIN***************************************/
    public static final String JDBC_DESC_KEY = "0001000200030004";

    /**数据库类型**/
    public static final String JDBC_DATASOURCE_TYPE_KEY = "datasource.type";

    public static final String JDBC_DATASOURCE_DRIVERCLASSNAME_KEY = "datasource.driverClassName";

    public static final String JDBC_DATASOURCE_URL_KEY = "datasource.url";

    public static final String JDBC_DATASOURCE_USERNAME_KEY = "datasource.username";

    public static final String JDBC_DATASOURCE_PASSWORD_KEY = "datasource.password";

    /******************************JDBC相关END***************************************/
}
