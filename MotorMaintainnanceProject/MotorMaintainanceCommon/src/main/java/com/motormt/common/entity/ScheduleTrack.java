package com.motormt.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "schedule_track")
public class ScheduleTrack {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	@Column(length = 256)
	private String notes;
	
	private Date updatedTime;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 48, nullable = false)
	private ScheduleStatus status;
	
	@ManyToOne
	@JoinColumn(name = "IDschedule")
	private Schedule schedule;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public ScheduleStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduleStatus status) {
		this.status = status;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
}
