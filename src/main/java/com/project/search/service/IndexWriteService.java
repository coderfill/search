package com.project.search.service;

import com.project.spider.info.BaseCrawlerInfo;

/**
 * 索引文件的写操作接口，主要针对更新、删除和添加索引
 *
 * @author ftc
 * @date 2018-12-27
 */
public interface IndexWriteService<T extends BaseCrawlerInfo> {


    public void addIndex(T t);
}
