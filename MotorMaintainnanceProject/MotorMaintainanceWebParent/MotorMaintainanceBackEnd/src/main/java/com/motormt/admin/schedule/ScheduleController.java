package com.motormt.admin.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.motormt.common.entity.Schedule;

@Controller
public class ScheduleController {

	@Autowired
	private ScheduleService service;
	
	@GetMapping("/schedule")
	public String listFirstPage(Model model) {
		return listByPage(1, model, null);
	}
	
	@GetMapping("/schedule-page-{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("keyword")String keyword) {
		Page<Schedule> page = service.listRequestByPage(pageNum, keyword);
		List<Schedule> listSchedules = page.getContent();
		
		long startCount = (pageNum - 1)*ScheduleService.SCHEDULE_PER_PAGE + 1;
		long endCount = startCount + ScheduleService.SCHEDULE_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listSchedules", listSchedules);
		model.addAttribute("keyword", keyword);
		return "schedule/schedule";
	}
	
	@GetMapping("/schedule-detail-{IDschedule}")
	public String viewRequestDetail(@PathVariable(name = "IDschedule") Integer IDschedule, 
			Model model, RedirectAttributes ra) {
		try {
			Schedule schedule = service.getSchedule(IDschedule);
			model.addAttribute("schedule", schedule);
			return "schedule/schedule_detail";
		}catch(ScheduleNotFoundException ex){
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:schedule";
		}
	}
}
