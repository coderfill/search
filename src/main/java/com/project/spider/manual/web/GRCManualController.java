package com.project.spider.manual.web;

import com.project.base.LoggerBaseSupport;
import com.project.spider.manual.service.GRCManaualService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GRCManualController extends LoggerBaseSupport {

    /**
     * 此地址是在线开发手册搜索结果的地址，搜索关键字为*，并且不对搜索结果进行分页
     */
    protected static final String target_page_url = "http://dmserver.smartdot.com.cn/product/grcv5/xmzd.nsf/vwalldocformyviewsearch?searchview&searchorder=3&view=vwalldocformyviewsearch&query=*";

    @Autowired
    private GRCManaualService grcManaualService;


    @RequestMapping(value = "/spider.do")
    public void spider() {
        try {
            grcManaualService.crawler(target_page_url);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("crawler " + target_page_url + ", error");
        }
    }
}
