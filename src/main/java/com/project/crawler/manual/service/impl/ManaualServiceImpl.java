package com.project.crawler.manual.service.impl;

import com.project.crawler.manual.service.ManaualService;
import com.project.solr.search.info.base.ResultInfo;
import com.project.solr.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
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
public class ManaualServiceImpl implements ManaualService, InitializingBean {

    @Autowired
    @Qualifier("manual")
    private PageProcessor manualPageListProcessor;

    @Autowired
    @Qualifier("manualSearchService")
    private SearchService searchService;


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void crawler(String target_url) throws IOException {
        Spider.create(manualPageListProcessor).addUrl(target_url).run();
    }

    @Override
    public ResultInfo searchByKeyword(String keyword) throws IOException, SolrServerException {
        return searchService.searchByKeyword(keyword);
    }


}
