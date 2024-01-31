package com.motormt.site.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.motormt.common.entity.Ser;

public interface SerRepository extends PagingAndSortingRepository<Ser, Integer>{

	@Query("SELECT s FROM Ser s WHERE s.tendichvu LIKE %:keyword% OR s.loaidv LIKE %:keyword%")
	public Page<Ser> search(@Param("keyword") String keyword, Pageable pageable);

}
