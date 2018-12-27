package com.project.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 自定义JpaRepository
 *
 * @author ftc
 * @date 2018-12-26
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends
        JpaSpecificationExecutor<T>, JpaRepository<T, ID> {

    /**
     * 将数据对象插入到数据库中
     * @param entity
     */
    public void persist(T entity);

    /**
     * 将数据对象插入到数据库中并执行刷新flush操作
     * @param entity
     */
    public void persistAndFlush(T entity);

    /**
     * 将数据对象的修改保存到数据库中
     * @param entity
     * @return
     */
    public T merge(T entity);

    /**
     * 将数据对象的修改保存到数据库中并执行flush操作
     * @param entity
     * @return
     */
    public T mergeAndFlush(T entity);
}
