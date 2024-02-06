package com.motormt.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.motormt.common.entity.Ser;

@Repository
public interface SerRepository extends PagingAndSortingRepository<Ser, Integer>{

	public Long countByIDdichvu(Integer IDdichvu);
	
	@Query("SELECT s FROM Ser s WHERE s.tendichvu LIKE %?1%")
	public Page<Ser> findAll(String keyword, Pageable pageable);

	public void deleteById(Integer iDdichvu);
	
	@Query("SELECT s FROM Ser s WHERE s.tendichvu = :tendichvu")
	public Ser getSerByTendichvu(@Param("tendichvu") String tendichvu);
	
}
