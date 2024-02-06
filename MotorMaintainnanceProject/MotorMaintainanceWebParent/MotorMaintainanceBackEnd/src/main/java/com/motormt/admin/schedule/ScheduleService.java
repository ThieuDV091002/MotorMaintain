package com.motormt.admin.schedule;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.motormt.common.entity.Schedule;
import com.motormt.common.entity.ScheduleStatus;
import com.motormt.common.entity.ScheduleTrack;

@Service
public class ScheduleService {

	public static final int SCHEDULE_PER_PAGE=5;

	@Autowired
	private ScheduleRepository repo;
	
	public Page<Schedule> listRequestByPage(int pageNum, String keyword){
		Pageable pageable = PageRequest.of(pageNum - 1, SCHEDULE_PER_PAGE);
		if(keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		return repo.findAll(pageable);
	}
	
	public Schedule getSchedule(Integer IDschedule) throws ScheduleNotFoundException {
		try {
			return repo.findById(IDschedule).get();
		}catch(NoSuchElementException ex){
			throw new ScheduleNotFoundException("Không tìm thấy lịch có ID: " + IDschedule);
		}
	}
	
	public void updateStatus(Integer IDschedule, String status) {
		Schedule scheduleInDB = repo.findById(IDschedule).get();
		ScheduleStatus statusToUpdate = ScheduleStatus.valueOf(status);
		if(!scheduleInDB.hasStatus(statusToUpdate)) {
			List<ScheduleTrack> scheduleTracks = scheduleInDB.getScheduleTracks();
			ScheduleTrack track = new ScheduleTrack();
			
			track.setSchedule(scheduleInDB);
			track.setUpdatedTime(new Date());
			track.setStatus(statusToUpdate);
			track.setNotes(statusToUpdate.defaultDescription());
			
			scheduleTracks.add(track);
			scheduleInDB.setScheduleStatus(statusToUpdate);
			repo.save(scheduleInDB);
		}
	}
}
