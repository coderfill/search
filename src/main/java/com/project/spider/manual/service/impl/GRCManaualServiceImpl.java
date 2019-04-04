package com.project.spider.manual.service.impl;

import com.project.spider.manual.service.GRCManaualService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;

/**
 * GRC在线开发手册爬虫服务接口实现类
 *
 * @author ftc
 * @date 2019-04-02
 */
@Service
public class GRCManaualServiceImpl implements GRCManaualService, InitializingBean {

    @Autowired
    @Qualifier("manual")
    private PageProcessor manualPageListProcessor;


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void crawler(String target_url) throws IOException {
        Spider.create(manualPageListProcessor).addUrl(target_url).run();
    }


}
