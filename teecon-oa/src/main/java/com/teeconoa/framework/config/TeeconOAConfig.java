package com.teeconoa.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* 读取项目相关配置
* 
*  Created by AndyYau
*  2019年1月6日 - 下午10:49:15
*  Company: Teecon
**/
@Component
@ConfigurationProperties(prefix = "teecon")
public class TeeconOAConfig
{
    /** 项目名称 */
    private String name;
    /** 版本 */
    private String version;
    /** 版权年份 */
    private String copyrightYear;
    /** 上传路径 */
    private static String profile;
    /** 获取地址开关 */
    private static boolean addressEnabled;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        TeeconOAConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        TeeconOAConfig.addressEnabled = addressEnabled;
    }

    public static String getAvatarPath()
    {
        return profile + "avatar/";
    }

    public static String getDownloadPath()
    {
        return profile + "download/";
    }
}
