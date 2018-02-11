package com.yijia360.pay.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<T, V> {
	
	int count(T t);

	List<V> list(T t);

	V get(T t);

	int update(T t);

	int delete(T t);

	int save(T t);
	
}

