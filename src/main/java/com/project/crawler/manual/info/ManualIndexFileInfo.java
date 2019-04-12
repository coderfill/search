package com.project.crawler.manual.info;

import com.project.solr.index.info.IndexFileInfo;

import java.io.Serializable;

/**
 * 在线开发手册索引文件信息
 *
 * @author ftc
 * @date 2019-04-11
 */
public class ManualIndexFileInfo extends IndexFileInfo implements Serializable {

    /**
     * 版本
     */
    private String version;

    /**
     * 类别
     */
    private String type;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "ManualIndexInfo{" +
                "title='" + getTitle() + '\'' +
                ", version='" + version + '\'' +
                ", type='" + type + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", keyword='" + getKeyword() + '\'' +
                ", url='" + getUrl() + '\'' +
                '}';
    }
}
