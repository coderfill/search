package com.project.solr.search.info.base;

/**
 * 单条搜索记录文档信息的基类
 * @author ftc
 * @date 2019-01-03
 */
public class BaseSearchDocResultInfo {

    /**
     * 主键Id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 对应的url资源
     */
    private String url;

    /**
     * 创建该资源的时间
     */
    private String createTime;

    /**
     * 资源作者
     */
    private String author;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 资源描述信息
     */
    private String description;

    /**
     * 所属类别
     */
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BaseSearchDocResultInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", createTime='" + createTime + '\'' +
                ", author='" + author + '\'' +
                ", keyword='" + keyword + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
