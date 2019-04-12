package com.project.solr.search.info.base;

import java.util.List;
import java.util.Map;

/**
 * solr搜索结果类，包含结果列表、总数已经分页信息
 *
 * @author ftc
 * @date 2019-04-12
 */
public class ResultInfo<T extends BaseSearchDocResultInfo> {

    /**
     * 搜索结果总数
     */
    protected long num;

    /**
     * 单次搜索结果集合
     */
    protected List<T> searchDocList;

    protected Map<String, Object> facetMap;


    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public List<T> getSearchDocList() {
        return searchDocList;
    }

    public void setSearchDocList(List<T> searchDocList) {
        this.searchDocList = searchDocList;
    }

    public Map<String, Object> getFacetMap() {
        return facetMap;
    }

    public void setFacetMap(Map<String, Object> facetMap) {
        this.facetMap = facetMap;
    }
}
