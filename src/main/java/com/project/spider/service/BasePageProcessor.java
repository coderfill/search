package com.project.spider.service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author ftc
 * @date 2018-12-25
 */
public class BasePageProcessor implements PageProcessor {

    protected Site site = Site.me()
                                .setRetrySleepTime(3)
                                .setSleepTime(1000)
                                .setTimeOut(10000);



    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return site;
    }
}
