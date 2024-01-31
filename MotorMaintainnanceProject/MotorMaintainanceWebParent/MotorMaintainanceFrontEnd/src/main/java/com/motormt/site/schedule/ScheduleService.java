package com.motormt.site.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.motormt.common.entity.Account;
import com.motormt.common.entity.Schedule;
import com.motormt.common.entity.ScheduleStatus;

@Service
public class ScheduleService {
public static final int SCHEDULE_PER_PAGE = 5;
	
	@Autowired
	private ScheduleRepository repo;
	
	public Page<Schedule> listByPage(int pageNum, String keyword, Account account){
		Pageable pageable = PageRequest.of(pageNum - 1, SCHEDULE_PER_PAGE);
		if(keyword != null) {
			return repo.findByCustomer(account.getIDtaikhoan(),keyword, pageable);
		}
		return repo.findByCustomer(account.getIDtaikhoan(), pageable);
	}
	
	public Schedule getByCustomerAndId(Account account, Integer IDschedule) throws ScheduleNotFoundException {
		Schedule request = repo.findByCustomerAndIDschedule(account.getIDtaikhoan(), IDschedule);
		if (request == null) 
			throw new ScheduleNotFoundException("Bạn không có lịch đặt nào với ID " + IDschedule);
		
		return request;
	}
	
	public Schedule save(Schedule schedule) {
		schedule.setCreatedAt(new Date());
		schedule.setScheduleStatus(ScheduleStatus.PROCESSING);
		Schedule savedRequest = repo.save(schedule);
		return savedRequest;
	}
}
