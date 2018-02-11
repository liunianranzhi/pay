package com.yijia360.pay.admin.dao;

import java.util.List;

public interface BaseDao<T, V> {
	
	int count(T t);

	List<V> list(T t);

	V get(T t);

	int update(T t);

	int delete(T t);

	int save(T t);
	
}
