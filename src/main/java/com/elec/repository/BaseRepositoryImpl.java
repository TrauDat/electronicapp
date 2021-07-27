package com.elec.repository;

import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseRepositoryImpl<E, ID> extends SimpleJpaRepository<E, ID> implements BaseRepository<E, ID> {

	private EntityManager em;

	public BaseRepositoryImpl(JpaEntityInformation<E, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.em = em;
	}

	@Override
	public Optional<E> findByQuery(String jpql, Map<String, Object> params) {
		TypedQuery<E> query = em.createQuery(jpql, getDomainClass());

		if (null != params) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		Optional<E> opt = Optional.of(query.getSingleResult());
		return opt;
	}

}
