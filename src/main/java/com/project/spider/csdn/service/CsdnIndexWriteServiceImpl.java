package com.project.spider.csdn.service;

import com.project.search.service.IndexWriteService;
import com.project.search.service.SearchServerService;
import com.project.spider.SolrInputDocStroage;
import com.project.spider.csdn.dao.CsdnRepository;
import com.project.spider.csdn.info.CsdnCrawlerEntity;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * CSDN索引数据写接口的实现
 *
 * @author ftc
 * @date 2018-12-27
 */
@Service("csdnIndexWriteServiceImpl")
public class CsdnIndexWriteServiceImpl implements IndexWriteService {

    private LinkedBlockingQueue<SolrInputDocument> solrInputDocuments = new LinkedBlockingQueue<SolrInputDocument>();

    @Value("${solr.batch.addIndex.num.threshold}")
    private int threshold;

    @Autowired
    private SearchServerService searchServer;

    @Autowired
    private CsdnRepository csdnRepository;

    @Autowired
    private SolrInputDocStroage solrInputDocStroage() {
        return new SolrInputDocStroage(solrInputDocuments, searchServer, threshold);
    }


    @Override
    public void addIndex() {
        List<CsdnCrawlerEntity> list = csdnRepository.findAll();
        list.forEach(entity -> {
            SolrInputDocument solrInputDocument = new SolrInputDocument();
            solrInputDocument.setField("id", entity.getId());
            solrInputDocument.setField("title", entity.getTitle());
            solrInputDocument.setField("url", entity.getUrl());
            solrInputDocument.setField("author", entity.getAuthor());
            solrInputDocument.setField("create_time", entity.getCreateTime());
            solrInputDocument.setField("tag", entity.getTag());
            solrInputDocStroage().push(solrInputDocument);
        });
    }
}
