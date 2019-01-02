package com.project.spider;

import com.project.search.service.SearchServerService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.eclipse.jetty.util.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 索引文件仓库类
 *
 * @author ftc
 * @date 2019-01-02
 */
public class SolrInputDocStroage {

    private final static Logger logger = LoggerFactory.getLogger(SolrInputDocStroage.class);

    private SolrClient client = null;
    private LinkedBlockingQueue<SolrInputDocument> solrInputDocuments;
    private SearchServerService searchServer;
    private int threshold;

    public SolrInputDocStroage(LinkedBlockingQueue<SolrInputDocument> solrInputDocuments, SearchServerService searchServer, int threshold) {
        this.solrInputDocuments = solrInputDocuments;
        this.searchServer = searchServer;
        this.threshold = threshold;
    }

    public void push(SolrInputDocument document) {
        logger.debug(searchServer + "," +threshold);
        solrInputDocuments.offer(document);
        while (solrInputDocuments.size() == 5) {
            logger.debug("待索引数据已经达到阀值，准备提交至服务器...");
            logger.debug("获取搜索引擎客户端...");
            SolrClient client = getClient();
            try {
                client.add(solrInputDocuments);
                logger.debug("提交至服务器...");
                client.commit();
                logger.debug("提交成功，清楚待索引数据队列...");
                solrInputDocuments.clear();
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取更新索引的搜索引擎客户端
     * @return
     */
    private SolrClient getClient() {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    client = searchServer.getUpdateClient();
                }
            }
        }
        return client;
    }
}
