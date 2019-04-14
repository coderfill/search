package com.project.solr.index.service;


import com.project.solr.index.info.IndexFileInfo;
import org.apache.solr.common.SolrInputDocument;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 索引文件的写操作接口，主要针对更新、删除和添加索引
 *
 * @author ftc
 * @date 2018-12-27
 */
public interface IndexService<T extends IndexFileInfo> {

    /**
     * 待索引文件队列
     */
    public LinkedBlockingDeque<SolrInputDocument> documents = new LinkedBlockingDeque<SolrInputDocument>();

    /**
     * 添加索引文件
     * @param t 索引文件信息，{@link IndexFileInfo}的子类
     */
    void addIndex(T t);

}
