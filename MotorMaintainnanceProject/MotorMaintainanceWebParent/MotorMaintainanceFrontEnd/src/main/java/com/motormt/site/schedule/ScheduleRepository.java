package com.motormt.site.schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.motormt.common.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

	@Query("SELECT s FROM Schedule s WHERE s.account.IDtaikhoan = ?1")
	public Page<Schedule> findByCustomer(Integer IDtaikhoan, Pageable pageable);
	
	@Query("SELECT s FROM Schedule s WHERE s.account.IDtaikhoan = ?1 AND ("
			+ "s.phuongtien LIKE %?2% OR "
			+ "s.loaidv LIKE %?2%)")
	public Page<Schedule> findByCustomer(Integer IDtaikhoan, String keyword, Pageable pageable);
	
	@Query("SELECT s FROM Schedule s WHERE s.account.IDtaikhoan = ?1 AND s.IDschedule = ?2")
	public Schedule findByCustomerAndIDschedule(Integer IDtaikhoan, Integer IDschedule);

}
