package com.project.manual.crawler.info;

/**
 * 在线开发手册页面配置信息
 *
 * @author ftc
 * @date 2019-04-04
 */
public class ManualConfig {

    /**
     * 在线开发手册域名地址
     */
    public static final String domain = "http://dmserver.smartdot.com.cn/";

    /**
     * 在线开发手册登录地址
     */
    public static final String login_url = domain + "names.nsf?Login";

    /**
     * 进行爬虫时，模拟登录的账号
     */
    public static final String crawler_username = "fangzz";

    /**
     * 进行爬虫时，模拟登录账号的密码
     */
    public static final String crawler_password = "fausto@100804";

    /**
     * 登录成功后的跳转地址
     */
    public static final String redirect_to = "/indishare/securtrac.nsf/agttrac?openagent&url=/product/grcv5/xmzd.nsf/frmindex";
}
