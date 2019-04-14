package com.project.solr.index.info.storage;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 在线开发手册索引文件仓库类
 */
@Component
public class ManualIndexStorageInfo {

    private volatile LinkedBlockingDeque<SolrInputDocument> documents = new LinkedBlockingDeque<SolrInputDocument>();


    public void add(SolrInputDocument document) {
        documents.offer(document);
    }

    public boolean isEmpty() {
        return documents.isEmpty();
    }

    public LinkedList<SolrInputDocument> getIndexFiles() throws InterruptedException {
        synchronized (this) {
            LinkedList<SolrInputDocument> linkedList = new LinkedList<SolrInputDocument>();
            while (!documents.isEmpty()) {
                linkedList.add(documents.take());
            }
            return linkedList;
        }
    }

    public SolrInputDocument getOne() {
        return documents.poll();
    }

    public void clean() {
        documents.clear();
    }
}
