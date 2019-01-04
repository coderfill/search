package com.project.spider.info;

import java.io.Serializable;
import java.util.UUID;

public class BaseCrawlerInfo implements Serializable {


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
     * 标签
     */
    private String tag;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = UUID.randomUUID().toString();
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
