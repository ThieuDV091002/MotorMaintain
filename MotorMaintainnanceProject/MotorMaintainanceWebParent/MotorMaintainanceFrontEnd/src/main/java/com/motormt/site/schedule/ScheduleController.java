package com.motormt.site.schedule;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.motormt.common.entity.Account;
import com.motormt.common.entity.Schedule;
import com.motormt.site.customer.CustomerService;

@Controller
public class ScheduleController {

	@Autowired
	private ScheduleService service;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/schedule")
	public String listFirstPage(Model model, HttpServletRequest request) {
		return listSchedulesByCustomerByPage(model, request, 1, null);
	}
	
	@GetMapping("/schedule-page-{pageNum}") 
	public String listSchedulesByCustomerByPage(Model model, HttpServletRequest request,
							@PathVariable(name = "pageNum") int pageNum, String keyword) {
		Account account = getAuthenticatedAccount(request);
		Page<Schedule> page = service.listByPage(pageNum, keyword, account);		
		List<Schedule> listSchedules = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("keyword", keyword);
		
		model.addAttribute("listSchedules", listSchedules);

		long startCount = (pageNum - 1) * ScheduleService.SCHEDULE_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + ScheduleService.SCHEDULE_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		return "schedule/schedule_customer";
	}
	
	@GetMapping("/schedule-detail-{IDschedule}")
	public String viewReviewDetails(Model model,
			@PathVariable(name = "IDschedule") Integer IDschedule, HttpServletRequest httpRequest) throws ScheduleNotFoundException {
		Account account = getAuthenticatedAccount(httpRequest);
		Schedule schedule = service.getByCustomerAndId(account, IDschedule);
		
		model.addAttribute("request", schedule);
		model.addAttribute("account", account);
		
		return "schedule/schedule_detail";
	}
	
	@GetMapping("/schedule-new")
	public String viewRequestForm(Model model, HttpServletRequest httpRequest) {
		Schedule schedule = new Schedule();
		Account account = getAuthenticatedAccount(httpRequest);		
		model.addAttribute("account", account);
		model.addAttribute("schedule", schedule);
		return "schedule/schedule_form";
	}
	
	@PostMapping("/schedule-save")
	public String saveSchedule(Model model, Schedule schedule, HttpServletRequest httpRequest) throws IOException{
		Account account = getAuthenticatedAccount(httpRequest);
		schedule.setAccount(account);
    	service.save(schedule);
    	model.addAttribute("schedule", schedule);
		model.addAttribute("account", account);
		
		return "schedule/schedule_customer";
	}
	
	private Account getAuthenticatedAccount(HttpServletRequest request) {
		String customerName = getUsernameOfAuthenticatedCustomer(request);
		Account account = customerService.getAccountByUsername(customerName);
		String customerEmail = account.getEmail();
		return customerService.getAccountByEmail(customerEmail);
	}
	
	public static String getUsernameOfAuthenticatedCustomer(HttpServletRequest request) {
	    Object principal = request.getUserPrincipal();
	    String customerName = null;
	    if (principal instanceof UsernamePasswordAuthenticationToken) {
	        customerName = request.getUserPrincipal().getName();
	    }
	    return customerName;
	}
}