package com.motormt.admin.schedule;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.motormt.common.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

	@Query("SELECT s FROM Schedule s WHERE s.account.hoten LIKE %?1%"
			+ " OR s.scheduleStatus LIKE %?1% OR s.loaidv LIKE %?1%")
	public Page<Schedule> findAll(String keyword, Pageable pageable);
	
	public List<Schedule> findAll();
}
