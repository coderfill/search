package com.project.solr.server.service;

import org.apache.solr.client.solrj.SolrClient;

/**
 * 搜索引擎服务接口，用于获取搜索引擎客户端
 *
 * @author ftc
 * @date 2018-12-27
 */
public interface SolrServerService {


    /**
     * 获取默认的客户端
     * @return
     */
    public SolrClient getDefaultClient();

    /**
     * 获取用于更新和添加索引的客户端
     * @return
     */
    public SolrClient getUpdateClient();
}
