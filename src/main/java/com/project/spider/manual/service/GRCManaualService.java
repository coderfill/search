package com.project.spider.manual.service;

import java.io.IOException;

/**
 * GRC在线开发手册爬虫服务接口
 *
 * @author ftc
 * @date 2019-04-02
 */
public interface GRCManaualService {

    /**
     * 爬取目标页面
     * @return
     */
    public void crawler(String target_url) throws IOException;
}
