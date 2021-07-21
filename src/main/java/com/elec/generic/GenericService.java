package com.elec.generic;

import java.util.List;

public interface GenericService<T extends Object> {
	
	T save(T entity);

	T update(T entity);

	void delete(T entity);

	void delete(Long id);
	
	void delete(String id);

	void deleteInBatch(List<T> entities);

	T find(Long id);
	
	T find(String id);

	List<T> findAll();
}
