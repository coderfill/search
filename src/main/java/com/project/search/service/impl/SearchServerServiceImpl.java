package com.project.search.service.impl;

import com.project.search.service.SearchServerService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 搜索引擎服务接口实现类
 *
 * @author ftc
 * @date 2018-12-27
 */
@Service("searchServer")
public class SearchServerServiceImpl implements SearchServerService {



    @Value("${spring.data.solr.host}")
    private String baseServerUrl;

    @Value("${solr.connection.timeout}")
    private int connectionTimeout;

    @Value("${solr.socket.timeout}")
    private int socketTimeout;


    @Override
    public SolrClient getDefaultClient() {
        SolrClient client = new HttpSolrClient.Builder(baseServerUrl)
                                                .withConnectionTimeout(connectionTimeout)
                                                .withSocketTimeout(socketTimeout)
                                                .build();
        return client;
    }

    @Override
    public SolrClient getUpdateClient() {
        SolrClient client = new ConcurrentUpdateSolrClient.Builder(baseServerUrl)
                                                            .withConnectionTimeout(connectionTimeout)
                                                            .withSocketTimeout(socketTimeout)
                                                            .build();
        return client;
    }
}
