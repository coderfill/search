package com.project.solr.index.service.impl;

import com.project.base.logger.LoggerBaseSupport;
import com.project.solr.index.info.ManualIndexFileInfo;
import com.project.manual.scheduler.ManualScheduler;
import com.project.solr.index.info.storage.ManualIndexStorageInfo;
import com.project.solr.index.service.IndexService;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 在线开发服务手册索引文件写服务的实现
 *
 * @author ftc
 * @date 2019-04-11
 */
@Service("manualIndexService")
public class ManualIndexServiceImpl extends LoggerBaseSupport implements IndexService<ManualIndexFileInfo>, InitializingBean {


    @Autowired
    private ManualIndexStorageInfo indexStorageInfo;

    @Autowired
    private ManualScheduler manualScheduler() {
        return new ManualScheduler(indexStorageInfo);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void addIndex(ManualIndexFileInfo indexFileInfo) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", indexFileInfo.getId());
        document.setField("title", indexFileInfo.getTitle());
        document.setField("url", indexFileInfo.getUrl());
        document.setField("author", indexFileInfo.getAuthor());
        document.setField("create_time", indexFileInfo.getCreateTime());
        document.setField("keyword", indexFileInfo.getKeyword());
        document.setField("description", indexFileInfo.getDescription());
        document.setField("type", indexFileInfo.getType());

        //添加至待索引队列中
        indexStorageInfo.add(document);
//        documents.offer(document);
    }

}
