package com.project.spider.csdn.web;

import com.project.search.info.csdn.CsdnSearchResultInfo;
import com.project.search.service.IndexReadService;
import com.project.search.service.IndexWriteService;
import com.project.search.service.SearchServerService;
import com.project.spider.csdn.service.CsdnPageProcessor;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.io.IOException;

/**
 * csdn爬虫以及搜索controller
 * @author ftc
 * @date 2018-12-27
 */
@RestController
@RequestMapping("/csdn")
public class CsdnController {

    @Autowired
    private CsdnPageProcessor csdnPageProcessor;

    @Autowired
    @Qualifier("csdnIndexReadServiceImpl")
    private IndexReadService indexReadService;

    @RequestMapping("/crawler.do")
    public void crawler() {
        Spider.create(csdnPageProcessor)
                .addUrl("https://blog.csdn.net/")
                .thread(5)
                .run();
    }


    @RequestMapping("/search.do")
    public void search(@RequestParam("keyword") String keyword) {
        try {
            CsdnSearchResultInfo info = (CsdnSearchResultInfo) indexReadService.searchByKeyword(keyword);
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
    }
}
