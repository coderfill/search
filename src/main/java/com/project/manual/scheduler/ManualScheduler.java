package com.project.manual.scheduler;

import com.project.base.logger.LoggerBaseSupport;
import com.project.solr.index.info.storage.ManualIndexStorageInfo;
import com.project.solr.index.service.IndexService;
import com.project.solr.server.service.SolrServerService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * 在线开发手册定时任务配置类
 * @author fausto0613
 */
@Service
@EnableScheduling
public class ManualScheduler extends LoggerBaseSupport implements InitializingBean {


    @Autowired
    @Qualifier(value = "manualIndexService")
    private IndexService indexService;

    @Autowired
    private SolrServerService searchServerService;

    /**
     * 定义在线开发手册索引仓库类，并作为构造函数参数注入
     */
    private ManualIndexStorageInfo manualIndexStorage;

    public ManualScheduler(ManualIndexStorageInfo manualIndexStorage) {
        this.manualIndexStorage = manualIndexStorage;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(indexService, IndexService.class + " is required.....");
        Assert.notNull(searchServerService, SolrServerService.class + " is required.....");
    }



    /**
     * 自动创建索引的定时任务
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void index() {
        logger.debug("扫描【在线开发手册】待索引队列.....");
        SolrClient client = searchServerService.getUpdateClient();
        try {
            if (!manualIndexStorage.isEmpty()) {
                logger.debug("检测到待索引数据，准备提交至服务器.....");
                client.add(manualIndexStorage.getIndexFiles());
                client.commit();
                logger.debug("提交至服务器成功.....");
            }
        } catch (SolrServerException | IOException | InterruptedException e) {
            e.printStackTrace();
            logger.error("创建索引时出现异常，message：" + e.getMessage());
        }
    }
}
