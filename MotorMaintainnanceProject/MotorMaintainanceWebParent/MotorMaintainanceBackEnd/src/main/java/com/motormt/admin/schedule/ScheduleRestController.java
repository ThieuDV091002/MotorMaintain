package com.motormt.admin.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ScheduleRestController {

	@Autowired
	private ScheduleService service;
	
	@PostMapping("/schedule-update-{IDschedule}-{status}")
	public Response updateStatus(@PathVariable("IDschedule") Integer IDschedule, @PathVariable("status") String status,
			RedirectAttributes ra) {
		service.updateStatus(IDschedule, status);
		return new Response(IDschedule, status);
	}
	
class Response{
		private Integer IDschedule;
		private String status;
		
		public Response(Integer iDschedule, String status) {
			this.IDschedule = iDschedule;
			this.status = status;
		}
		public Integer getIDschedule() {
			return IDschedule;
		}
		public void setIDschedule(Integer iDschedule) {
			IDschedule = iDschedule;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
	}
}
