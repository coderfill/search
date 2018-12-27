package com.project.spider.csdn.info;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * csdn爬取信息对应的entity实体
 *
 * @author ftc
 * @date 2018-12-26
 */
@Entity(name = "csdn")
@Table(name = "CRAWLER_INFO_CSDN")
public class CsdnCrawlerEntity implements Serializable {

    private static final long serialVersionUID = -3647446961784269024L;

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

    /**
     * 该资源是否被索引，0表示未索引，1表示已索引
     */
    private int isIndex;

    /**
     * 创建索引的时间
     */
    private String indexTime;


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "CREATE_TIME")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Column(name = "AUTHOR")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "TAG")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Column(name = "IS_INDEX")
    public int getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(int isIndex) {
        this.isIndex = isIndex;
    }

    @Column(name = "INDEX_TIME")
    public String getIndexTime() {
        return indexTime;
    }

    public void setIndexTime(String indexTime) {
        this.indexTime = indexTime;
    }
}
