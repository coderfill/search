package com.project.solr.index.service.impl;

import com.project.solr.index.info.IndexDocStroageInfo;
import com.project.solr.index.service.IndexService;
import com.project.crawler.manual.info.ManualIndexFileInfo;
import com.project.solr.server.service.SolrServerService;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 在线开发服务手册索引文件写服务的实现
 *
 * @author ftc
 * @date 2019-04-11
 */
@Service("manualIndexWriteService")
public class ManualIndexServiceImpl implements IndexService<ManualIndexFileInfo> {


    private LinkedBlockingQueue<SolrInputDocument> solrInputDocuments = new LinkedBlockingQueue<SolrInputDocument>();

    @Value("${solr.batch.addIndex.num.threshold}")
    private int threshold;

    @Autowired
    private SolrServerService serverService;

    @Autowired
    private IndexDocStroageInfo indexDocStroageInfo() {
        return new IndexDocStroageInfo(solrInputDocuments, serverService, threshold);
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
        indexDocStroageInfo().push(document);
    }
}
