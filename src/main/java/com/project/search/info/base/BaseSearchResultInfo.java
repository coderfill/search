package com.project.search.info.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 搜索结果基础类
 *
 * @author ftc
 * @date 2019-01-03
 */
public class BaseSearchResultInfo<T extends BaseSearchDocInfo> implements Serializable {

    private static final long serialVersionUID = 4479214320643817503L;
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
