package com.motormt.site.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.motormt.common.entity.Ser;

@Controller
public class SerController {

	@Autowired
	private SerService serService;

	@GetMapping("/search")
	public String searchFirstPage(Model model, @Param("keyword") String keyword) {
		return searchByPage(keyword, 1, model);
	}
	
	@GetMapping("/search-page-{pageNum}")
	public String searchByPage(@Param("keyword") String keyword,
			@PathVariable("pageNum") int pageNum, Model model) {
		Page<Ser> pageSers = serService.search(keyword, pageNum);
		List<Ser> listResult = pageSers.getContent();
		
		long startCount = (pageNum - 1)*SerService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + SerService.SEARCH_RESULTS_PER_PAGE - 1;
		if(endCount > pageSers.getTotalElements()) {
			endCount = pageSers.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageSers.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageSers.getTotalElements());
		model.addAttribute("keyword", keyword);
		model.addAttribute("listResult", listResult);
		return "book/search_result";
	}
	
	@GetMapping("/service-detail-{IDdichvu}")
	public String viewSerDetail(@PathVariable(name = "IDdichvu") Integer IDdichvu, Model model) throws SerNotFoundException {
		try {
			Ser service = serService.get(IDdichvu);
			model.addAttribute("service", service);        
			return "service/service_detail";
		}catch(SerNotFoundException ex) {
			return "redirect:book/search_result";
		}
	}
}
