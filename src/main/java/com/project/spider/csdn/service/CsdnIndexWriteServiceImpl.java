package com.project.spider.csdn.service;

import com.project.search.info.stroage.IndexDocStroageInfo;
import com.project.search.service.IndexWriteService;
import com.project.search.service.SearchServerService;
import com.project.spider.csdn.dao.CsdnRepository;
import com.project.spider.csdn.info.CsdnCrawlerEntity;
import com.project.spider.csdn.info.CsdnCrawlerInfo;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * CSDN索引数据写接口的实现
 *
 * @author ftc
 * @date 2018-12-27
 */
@Service("csdnIndexWriteServiceImpl")
public class CsdnIndexWriteServiceImpl implements IndexWriteService<CsdnCrawlerInfo> {

    private LinkedBlockingQueue<SolrInputDocument> solrInputDocuments = new LinkedBlockingQueue<SolrInputDocument>();

    @Value("${solr.batch.addIndex.num.threshold}")
    private int threshold;

    @Autowired
    private SearchServerService searchServer;

    @Autowired
    private CsdnRepository csdnRepository;

    @Autowired
    private IndexDocStroageInfo indexDocStroage() {
        return new IndexDocStroageInfo(solrInputDocuments, searchServer, threshold);
    }


    @Override
    public void addIndex(CsdnCrawlerInfo csdnCrawlerInfo) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.setField("id", UUID.randomUUID().toString());
        solrInputDocument.setField("title", csdnCrawlerInfo.getTitle());
        solrInputDocument.setField("url", csdnCrawlerInfo.getUrl());
        solrInputDocument.setField("author", csdnCrawlerInfo.getAuthor());
        solrInputDocument.setField("create_time", csdnCrawlerInfo.getCreateTime());
        solrInputDocument.setField("tag", csdnCrawlerInfo.getTag());
        indexDocStroage().push(solrInputDocument);

        CsdnCrawlerEntity entity = toEntity(csdnCrawlerInfo);
        csdnRepository.persistAndFlush(entity);
    }

    /**
     * 将页面的爬虫信息转化为对应的entity
     * @param info
     * @return
     */
    private CsdnCrawlerEntity toEntity(CsdnCrawlerInfo info) {
        CsdnCrawlerEntity entity = new CsdnCrawlerEntity();
        entity.setAuthor(info.getAuthor());
        entity.setCreateTime(info.getCreateTime());
        entity.setTag(info.getTag());
        entity.setTitle(info.getTitle());
        entity.setUrl(info.getUrl());
        entity.setIsIndex(0);
        return entity;
    }
}
