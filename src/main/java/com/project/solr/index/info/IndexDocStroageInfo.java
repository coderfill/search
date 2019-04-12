package com.project.solr.index.info;

import com.project.solr.server.service.SolrServerService;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 索引文件仓库类
 */
public class IndexDocStroageInfo {

    private final static Logger logger = LoggerFactory.getLogger(IndexDocStroageInfo.class);

    /**
     * 待索引数据队列，使用volatie关键字，保证数据的可见性
     */
    private volatile LinkedBlockingQueue<SolrInputDocument> documents;

    private SolrServerService searchServer;

    /**
     * 索引数据队列长度的阀值，当达到阀值时，触发提交操作
     * 将队列中的待索引数据提交至服务器中
     */
    private int threshold;

    private SolrClient client = null;

    public IndexDocStroageInfo(LinkedBlockingQueue<SolrInputDocument> documents, SolrServerService searchServer, int threshold) {
        this.documents = documents;
        this.searchServer = searchServer;
        this.threshold = threshold;
    }

    /**
     * 向待索引数据队列中添加数据<br>
     * 当队列长度等于阀值时，触发提交操作,将队列中的数据提交至服务器中
     * @param document
     */
    public void push(SolrInputDocument document) {
        SolrClient client = getClient();
        synchronized (documents) {
            try {
                if (StringUtils.equals(document.getFieldValue("id").toString(), "end")) {
                    logger.debug("检测到终止标识符...");
                    client.add(documents);
                    client.commit();
                    logger.debug("提交成功，清除待索引数据队列...");
                    documents.clear();
                    return;
                }
                while (documents.size() == threshold) {
                    logger.debug("待索引数据已经达到阀值，准备提交至服务器...");
                    logger.debug("获取搜索引擎客户端...");
                    client.add(documents);
                    logger.debug("提交至服务器...");
                    client.commit();
                    logger.debug("提交成功，清除待索引数据队列...");
                    documents.clear();
                }
                documents.offer(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                logger.error("提交失败");
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
