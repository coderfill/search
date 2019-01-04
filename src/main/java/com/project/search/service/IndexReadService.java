package com.project.search.service;

import com.project.search.info.base.BaseSearchResultInfo;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * 索引文件读操作接口，主要用于数据的检索
 *
 * @author ftc
 * @date 2018-12-27
 */
public interface IndexReadService<T extends BaseSearchResultInfo> {

    public T searchByKeyword(String keyword) throws IOException, SolrServerException;

}
