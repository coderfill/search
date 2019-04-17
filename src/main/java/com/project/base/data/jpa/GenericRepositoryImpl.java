package com.project.base.data.jpa;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 自定义JpaRepository的实现
 *
 * @author ftc
 * @date 2018-12-26
 */
public class GenericRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements GenericRepository<T, ID>{

    private EntityManager em;

    public GenericRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    public GenericRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    @Override
    @Transactional
    public void persist(T entity) {
        em.persist(entity);
    }

    @Override
    @Transactional
    public void persistAndFlush(T entity) {
        persist(entity);
        flush();
    }

    @Override
    @Transactional
    public T merge(T entity) {
        return em.merge(entity);
    }

    @Override
    @Transactional
    public T mergeAndFlush(T entity) {
        T t = merge(entity);
        flush();
        return t;
    }
}
