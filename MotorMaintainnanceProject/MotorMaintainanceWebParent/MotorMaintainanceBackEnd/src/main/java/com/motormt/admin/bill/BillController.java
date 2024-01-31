package com.motormt.admin.bill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.motormt.common.entity.Bill;

@Controller
public class BillController {
	@Autowired
	private BillService service;
	
	@GetMapping("/bill")
	public String listFirstPage(Model model) {
		return listByPage(1, model, null);
	}
	
	@GetMapping("/bill-page-{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("keyword")String keyword) {
		Page<Bill> page = service.listByPage(pageNum, keyword);
		List<Bill> listBills = page.getContent();
		
		long startCount = (pageNum - 1)*BillService.BILL_PER_PAGE + 1;
		long endCount = startCount + BillService.BILL_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listBills", listBills);
		model.addAttribute("keyword", keyword);
		return "bill/bill";
	}
	
	@GetMapping("/bill-detail-{IDhoadon}")
	public String viewAccountDetail(@PathVariable(name = "IDhoadon") Integer IDhoadon, 
			Model model, 
			RedirectAttributes redirectAttributes) {
		try {
			Bill bill = service.get(IDhoadon);
			model.addAttribute("bill", bill);
			return "bill_detail";
		}catch(BillNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return"redirect:bill";
		}
	}
	
	@GetMapping("/bill-{IDhoadon}-trangthai-{trangthai}")
	public String updateBillStatus(@PathVariable("IDhoadon") Integer IDhoadon,
			@PathVariable("trangthai") boolean trangthai, RedirectAttributes redirectAttributes) {
		service.updateBillStatus(IDhoadon, trangthai);
		String status = trangthai ? "đã hoàn thành" : "chưa hoàn thành";
		String message = "Hóa đơn có ID " + IDhoadon + status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:bill";
	}

}
