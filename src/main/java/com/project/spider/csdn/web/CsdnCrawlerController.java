package com.project.spider.csdn.web;

import com.project.search.service.IndexWriteService;
import com.project.search.service.SearchServerService;
import com.project.spider.csdn.service.CsdnIndexWriteServiceImpl;
import com.project.spider.csdn.service.CsdnPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * @author ftc
 * @date 2018-12-27
 */
@RestController
@RequestMapping("/csdn")
public class CsdnCrawlerController {


    @Autowired
    private CsdnPageProcessor csdnPageProcessor;

    @Autowired
    private SearchServerService searchServer;

    @Autowired
    @Qualifier("csdnIndexWriteServiceImpl")
    private IndexWriteService indexWriteService;


    @RequestMapping("/crawler")
    public void crawler() {
        searchServer.getDefaultClient();
        indexWriteService.addIndex();
//        Spider.create(csdnPageProcessor)
//                .addUrl("https://blog.csdn.net/")
//                .thread(5)
//                .run();
    }
}
