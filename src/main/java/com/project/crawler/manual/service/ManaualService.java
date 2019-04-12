package com.project.crawler.manual.service;

import com.project.solr.search.info.base.ResultInfo;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * GRC在线开发手册爬虫服务接口
 *
 * @author ftc
 * @date 2019-04-02
 */
public interface ManaualService {

    /**
     * 爬取目标页面
     * @return
     */
    public void crawler(String target_url) throws IOException;

    public ResultInfo searchByKeyword(String keyword) throws IOException, SolrServerException;
}
