package com.project.spider.csdn.dao;

import com.project.data.jpa.GenericRepository;
import com.project.spider.csdn.info.CsdnCrawlerEntity;
import org.springframework.stereotype.Repository;

/**
 * csdn数据持久层接口
 *
 * @author ftc
 * @date 2018-12-26
 */
@Repository
public interface CsdnRepository extends GenericRepository<CsdnCrawlerEntity, String> {


}
