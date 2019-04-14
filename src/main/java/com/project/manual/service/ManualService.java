package com.project.manual.service;

import com.project.solr.search.info.base.ResultInfo;
import org.apache.solr.client.solrj.SolrServerException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * GRC在线开发手册爬虫服务接口
 *
 * @author ftc
 * @date 2019-04-02
 */
public interface ManualService {

    /**
     * 爬取目标页面
     * @return
     */
    public void crawler(String target_url) throws IOException;

    /**
     * 根据关键字进行模糊查询
     * @param keyword 查询关键字
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public ResultInfo searchByKeyword(String keyword) throws IOException, SolrServerException;

    /**
     * 设置跳转至在线开发手册详情页的{@link HttpServletResponse}对象
     * @param response
     * @return
     */
    public HttpServletResponse setResponse(HttpServletResponse response);

}
