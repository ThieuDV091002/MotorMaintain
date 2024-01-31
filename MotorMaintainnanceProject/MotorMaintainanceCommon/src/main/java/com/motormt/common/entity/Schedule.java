package com.motormt.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="schedule")
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer IDschedule;
	private Date requestTime;
	private Date createdAt;
	private String loaidv;
	private String phuongtien;

	private String notes;
	
	@Enumerated(EnumType.STRING)
	private ScheduleStatus scheduleStatus;
	
	@ManyToOne
	@JoinColumn(name = "IDtaikhoan")
	private Account account;
	
	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
	@OrderBy("updatedTime ASC")
	private List<ScheduleTrack> requestTracks = new ArrayList<>();

	public Integer getIDschedule() {
		return IDschedule;
	}

	public void setIDschedule(Integer iDschedule) {
		IDschedule = iDschedule;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getLoaidv() {
		return loaidv;
	}

	public void setLoaidv(String loaidv) {
		this.loaidv = loaidv;
	}

	public String getPhuongtien() {
		return phuongtien;
	}

	public void setPhuongtien(String phuongtien) {
		this.phuongtien = phuongtien;
	}

	public ScheduleStatus getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(ScheduleStatus scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<ScheduleTrack> getRequestTracks() {
		return requestTracks;
	}

	public void setRequestTracks(List<ScheduleTrack> requestTracks) {
		this.requestTracks = requestTracks;
	}
	
	public boolean hasStatus(ScheduleStatus status) {
		for(ScheduleTrack oTrack : requestTracks) {
			if(oTrack.getStatus().equals(status))
				return true;
		}
		return false;
	}
	
	@Transient
	public boolean isProcessing() {
		return hasStatus(ScheduleStatus.PROCESSING);
	}
	
	@Transient
	public boolean isAccepted() {
		return hasStatus(ScheduleStatus.ACCEPTED);
	}
	
	@Transient
	public boolean isRejected() {
		return hasStatus(ScheduleStatus.REJECTED);
	}
}
