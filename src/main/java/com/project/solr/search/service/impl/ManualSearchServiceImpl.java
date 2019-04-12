package com.project.solr.search.service.impl;

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
public class ManualSearchServiceImpl implements SearchService<ManualSearchDocResultInfo>, InitializingBean {


    @Autowired
    private SolrServerService serverService;

    @Override
    public void afterPropertiesSet() throws Exception {

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
        query.addField("tag");
        query.addField("url");
        query.addField("create_time");

        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("title");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");
        //设置高亮部分每段显示的长度(以字符为单位)
        query.setHighlightFragsize(30);
        //高亮部分分为几段显示
        query.setHighlightSnippets(3);

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
//            info.setTitle(doc.getFieldValue("title") == null ? null : doc.getFieldValue("title").toString());
            Map<String, List<String>> highlighterMap = highlighter.get(doc.getFieldValue("id"));
            if (highlighterMap.size() == 0 || highlighterMap.isEmpty()) {
                info.setTitle(doc.getFieldValue("title") == null ? null : doc.getFieldValue("title").toString());
            } else {
                info.setTitle(highlighter.get(doc.getFieldValue("id")).get("title").get(0));
            }
            info.setKeyword(doc.getFieldValue("keyword") == null ? null : doc.getFieldValue("keyword").toString());
            info.setUrl(doc.getFieldValue("url") == null ? null : doc.getFieldValue("url").toString());
            info.setCreateTime(doc.getFieldValue("create_time") == null ? null : doc.getFieldValue("create_time").toString());
            docs.add(info);
        });
        Map<String, Object> facetMap = new LinkedHashMap<String, Object>();
        response.getFacetFields().forEach(facetField -> {
            List<FacetField.Count> counts = facetField.getValues();
            counts.forEach(count -> {
                facetMap.put(count.getName(), count.getCount());
                System.out.println("_name:" + count.getName() + ", _count:" + count.getCount());
            });
        });
        ResultInfo info = new ResultInfo();
        info.setNum(list.getNumFound());
        info.setSearchDocList(docs);
        info.setFacetMap(facetMap);
        return info;
    }


}
