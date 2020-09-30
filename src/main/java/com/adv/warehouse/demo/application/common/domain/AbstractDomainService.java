package com.adv.warehouse.demo.application.common.domain;

import com.adv.warehouse.demo.application.common.exception.ErrorType;
import com.adv.warehouse.demo.application.common.exception.ParameterizedException;
import com.adv.warehouse.demo.infrastructure.annotations.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collections;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public abstract class AbstractDomainService<T, TId, R extends ElasticsearchRepository<T, TId>> {

	protected final R r;

	public T get(TId tid) {
		return r.findById(tid)
				.orElseThrow(() -> new ParameterizedException(ErrorType.ENTITY_NOT_FOUND, Collections.singletonMap("id", tid)));
	}

	public Optional<T> find(TId tid) {
		return r.findById(tid);
	}

	public T createOrUpdate(T t) {
		return r.save(t);
	}

	public void delete(T t) {
		r.delete(t);
	}

	public void deleteById(TId tid) {
		r.deleteById(tid);
	}

	//TODO:
	public T getActive(TId id) {
		throw new UnsupportedOperationException();
	}

}
