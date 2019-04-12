package com.project.solr.index.service;


import com.project.solr.index.info.IndexFileInfo;

/**
 * 索引文件的写操作接口，主要针对更新、删除和添加索引
 *
 * @author ftc
 * @date 2018-12-27
 */
public interface IndexService<T extends IndexFileInfo> {


    public void addIndex(T t);
}
