package com.project.spider.manual.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * GRC在线开发手册爬虫服务接口
 *
 * @author ftc
 * @date 2019-04-02
 */
public interface GRCManaualService {

    /**
     * 爬取目标页面的html内容
     * @return
     */
    public String getPageHtmlContent(String target_url) throws IOException;
}
