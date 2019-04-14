package com.project.manual.service.impl;

import com.project.manual.service.ManualService;
import com.project.solr.search.info.base.ResultInfo;
import com.project.solr.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * GRC在线开发手册爬虫服务接口实现类
 *
 * @author ftc
 * @date 2019-04-02
 */
@Service
public class ManaualServiceImpl implements ManualService, InitializingBean {

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


    @Override
    public HttpServletResponse setResponse(HttpServletResponse response) {
        Map<String, String> headerMap = manualPageListProcessor.getSite().getHeaders();
        for (Map.Entry entry : headerMap.entrySet()) {
            response.setHeader(entry.getKey().toString(), entry.getValue().toString());
        }
        response.addCookie(new Cookie("myusername", "fangzz"));
        return response;
    }

    protected void simuliateLogin (String username, String password) {

    }
}
