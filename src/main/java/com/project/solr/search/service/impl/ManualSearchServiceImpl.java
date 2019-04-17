package com.project.solr.search.service.impl;

import com.project.base.logger.LoggerBaseSupport;
import com.project.solr.search.info.base.ResultInfo;
import com.project.solr.search.info.manual.ManualSearchDocResultInfo;
import com.project.solr.search.service.SearchService;
import com.project.solr.server.service.SolrServerService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.FacetParams;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 在线开发手册搜索接口实现类
 *
 * @author ftc
 * @date 2019-04-12
 */
@Service("manualSearchService")
public class ManualSearchServiceImpl extends LoggerBaseSupport implements SearchService<ManualSearchDocResultInfo>, InitializingBean {

    private final String highlightSimplePre = "<font color='red'>";

    private final String highlightSimplePost = "</font>";

    /**
     * 设置高亮部分每段显示的长度(以字符为单位)
     */
    private final int highlightFragsize = 30;

    /**
     * 高亮部分分为几段显示
     */
    private final int highlightSnippets = 3;
    @Autowired
    private SolrServerService serverService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(serverService, "SolrServerService is required.....");
    }

    /**
     * 使用SolrJ进行全文检索，进行全文检索的方式有两种：<br>
     * 1.创建SolrQuery，具体例子如下：<br>
     *     <p>
     *         SolrQuery query = new SolrQuery(); <br>
     *         query.set("q", "title:" + keyword); <br>
     *         //确定数据回显的field域 <br>
     *         query.addField("title"); <br>
     *         query.addField("author"); <br>
     *         query.addField("tag"); <br>
     *         query.addField("url"); <br>
     *         query.addField("create_time"); <br>
     *         query.setRows(row); <br>
     *     </p>
     * 2.使用MapSolrParams进行，将查询参数封装 <br>
     *     <p>
     *         Map<String, String> queryMap = new HashMap<String, String>();
     *         queryMap.put("q", "大数据");
     *         queryMap.put("fl", "title, author, tag, url, create_time");
     *         queryMap.put("rows", String.valueOf(row));
     *         MapSolrParams params = new MapSolrParams(queryMap);
     *     </p>
     * @param keyword
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Override
    public ResultInfo searchByKeyword(String keyword) throws IOException, SolrServerException {
        SolrClient client = serverService.getDefaultClient();
        SolrQuery query = new SolrQuery();
        query.set("q", keyword);
        //确定数据回显的field域
        query.addField("id");
        query.addField("title");
        query.addField("author");
        query.addField("url");
        query.addField("create_time");
        query.addField("type");
        query.addField("keyword");
        query.addField("description");
        setHighlighter(query, new String[]{"title"});
        //设置分组查询
        query.addFacetQuery(keyword);
//        query.addFacetPivotField("tag", "title");
        query.addFacetField("type");
        query.setFacetSort(FacetParams.FACET_SORT_COUNT);
        query.setFacetMinCount(1);

        query.setRows(20);
        QueryResponse response = client.query(query);
        SolrDocumentList list = response.getResults();
        Map<String, Map<String, List<String>>> highlighter = response.getHighlighting();
        List<ManualSearchDocResultInfo> docs = new LinkedList<ManualSearchDocResultInfo>();
        list.forEach(doc -> {
            ManualSearchDocResultInfo info = new ManualSearchDocResultInfo();
            info.setAuthor(doc.getFieldValue("author") == null ? null : doc.getFieldValue("author").toString());
            Map<String, List<String>> highlighterMap = highlighter.get(doc.getFieldValue("id"));
            if (highlighterMap.size() == 0 || highlighterMap.isEmpty()) {
                info.setTitle(doc.getFieldValue("title") == null ? null : doc.getFieldValue("title").toString());
            } else {
                info.setTitle(highlighter.get(doc.getFieldValue("id")).get("title").get(0));
            }
            info.setKeyword(doc.getFieldValue("keyword") == null ? null : doc.getFieldValue("keyword").toString());
            info.setUrl(doc.getFieldValue("url") == null ? null : doc.getFieldValue("url").toString());
            info.setCreateTime(doc.getFieldValue("create_time") == null ? null : doc.getFieldValue("create_time").toString());
            info.setType(doc.getFieldValue("type") == null ? null : doc.getFieldValue("type").toString());
            info.setDescription(doc.getFieldValue("description") == null ? null : doc.getFieldValue("description").toString());
            docs.add(info);
            logger.debug(info.toString());
        });
        Map<String, Object> facetMap = new LinkedHashMap<String, Object>();
        response.getFacetFields().forEach(facetField -> {
            List<FacetField.Count> counts = facetField.getValues();
            counts.forEach(count -> {
                facetMap.put(count.getName(), count.getCount());
                logger.debug("_name:" + count.getName() + ", _count:" + count.getCount());
            });
        });
        ResultInfo info = new ResultInfo();
        info.setNum(list.getNumFound());
        info.setSearchDocList(docs);
        info.setFacetMap(facetMap);
        return info;
    }

    @Override
    public ResultInfo seniorSearchByParams(Map<String, String> params) throws IOException, SolrServerException {
        return null;
    }


    /**
     * 设置查询结果高亮
     * @param query 待查询的{@link SolrQuery}语句
     * @param highlighterFields 需要进行高亮的field
     */
    protected void setHighlighter(SolrQuery query, String[] highlighterFields) {
        //设置高亮
        query.setHighlight(true);
        for (String field : highlighterFields) {
            query.addHighlightField(field);
        }
        query.setHighlightSimplePre(highlightSimplePre);
        query.setHighlightSimplePost(highlightSimplePost);
        query.setHighlightFragsize(highlightFragsize);
        query.setHighlightSnippets(highlightSnippets);
    }

}
