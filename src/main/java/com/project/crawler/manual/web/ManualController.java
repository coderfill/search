package com.project.crawler.manual.web;

import com.project.base.logger.LoggerBaseSupport;
import com.project.crawler.manual.service.ManaualService;
import com.project.solr.search.info.base.ResultInfo;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author ftc
 * @date 2019-04-01
 */
@RestController
@RequestMapping(value = "/grcManaual")
public class ManualController extends LoggerBaseSupport {

    /**
     * 此地址是在线开发手册搜索结果的地址，搜索关键字为*，并且不对搜索结果进行分页
     */
    protected static final String target_page_url = "http://dmserver.smartdot.com.cn/product/grcv5/xmzd.nsf/vwalldocformyviewsearch?searchview&searchorder=3&view=vwalldocformyviewsearch&query=*";

    @Autowired
    private ManaualService grcManaualService;


    @RequestMapping(value = "/spider.do")
    public void spider() {
        try {
            grcManaualService.crawler(target_page_url);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("crawler " + target_page_url + ", error");
        }
    }


    @RequestMapping(value = "/search.do")
    public ResultInfo search(String keyword) {
        try {
            return grcManaualService.searchByKeyword(keyword);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
