package com.project.search.info.csdn;

import com.project.search.info.base.BaseSearchDocInfo;

import java.io.Serializable;

/**
 * @author ftc
 * @date 2019-01-03
 */
public class CsdnSearchDocInfo extends BaseSearchDocInfo implements Serializable {

    private static final long serialVersionUID = -3688628118792556510L;

    private String title;

    private String url;

    private String author;

    private String tag;

    private String createTime;


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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
