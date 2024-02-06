package com.motormt.admin.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.motormt.admin.FileUploadUtil;
import com.motormt.common.entity.Ser;

@Controller
public class SerController {

    @Autowired 
    private SerService Serservice;
    
    @GetMapping("/service")
    public String listFirstSerPage(Model model) {
        return listByPage(1, model, null);
    }
    
    @GetMapping("/service-page-{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model, @Param("keyword") String keyword) {
    	Page<Ser> page = Serservice.listByPage(pageNum, keyword);
    	List<Ser> listSers = page.getContent();
    	
    	long startCount = (pageNum -1)*SerService.SER_PER_PAGE + 1;
    	long endCount = startCount + SerService.SER_PER_PAGE - 1;
    	if(endCount > page.getTotalElements()) {
    		endCount = page.getTotalElements();
    	}
    	
    	model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
    	model.addAttribute("totalItems", page.getTotalElements());
    	model.addAttribute("startCount", startCount);
    	model.addAttribute("endCount", endCount);
    	model.addAttribute("listSers", listSers);
    	model.addAttribute("keyword", keyword);
    	return "service/service";
    }
    
    @GetMapping("/service-new")
    public String newSer(Model model) {
    	Ser service = new Ser();
		model.addAttribute("service", service);
		model.addAttribute("pageTitle", "Thêm dịch vụ");
    	return "service/service_form";
    }
    
    @PostMapping("/service-save")
    public String saveSer(Ser service, RedirectAttributes redirectAttributes,
    		@RequestParam("image") MultipartFile multipartFile) throws IOException {
    	if(!multipartFile.isEmpty()) {
    		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    		service.setHinhanh(fileName);
    		Ser savedSer = Serservice.update(service);
        	String uploadDir = "../service-photos/" + savedSer.getIDdichvu();
        	FileUploadUtil.cleanDir(uploadDir);
        	FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    	}
    	else {
    		if(service.getHinhanh().isEmpty()) service.setHinhanh(null);
    		Serservice.update(service);
    	}
		redirectAttributes.addFlashAttribute("message", service.getTendichvu() + " đã được thêm thành công");
		return "redirect:/service";
	}
    
    @PostMapping("/service-update")
    public String updateSer(Ser service, RedirectAttributes redirectAttributes,
    		@RequestParam("image") MultipartFile multipartFile) throws IOException {
    	if(!multipartFile.isEmpty()) {
    		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    		service.setHinhanh(fileName);
    		Ser savedSer = Serservice.save(service);
        	String uploadDir = "../service-photos/" + savedSer.getIDdichvu();
        	FileUploadUtil.cleanDir(uploadDir);
        	FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    	}
    	else {
    		if(service.getHinhanh().isEmpty()) service.setHinhanh(null);
    		Serservice.save(service);
    	}
		redirectAttributes.addFlashAttribute("message", service.getTendichvu() + " đã được lưu thành công");
		return "redirect:/service";
	}
    

	@GetMapping("/service-edit-{IDdichvu}")
    public String editSer(@PathVariable(name = "IDdichvu") Integer IDdichvu, RedirectAttributes redirectAttributes, Model model) {
    	try {
    		Ser service = Serservice.get(IDdichvu);
    		model.addAttribute("service", service);
    		model.addAttribute("pageTitle", "Sửa thông tin dịch vụ(ID :" + IDdichvu + ")");
    		return "service/service_update";
    	}catch(SerNotFoundException ex) {
    		redirectAttributes.addFlashAttribute("message", ex.getMessage());
    		return "redirect:/service";
    	}
	}
	
	@GetMapping("/service-detail-{IDdichvu}")
    public String viewSerDetail(@PathVariable(name = "IDdichvu") Integer IDdichvu, RedirectAttributes redirectAttributes, Model model) {
    	try {
    		Ser service = Serservice.get(IDdichvu);
    		model.addAttribute("service", service);
    		return "service/service_detail";
    	}catch(SerNotFoundException ex) {
    		redirectAttributes.addFlashAttribute("message", ex.getMessage());
    		return "redirect:/service";
    	}
	}
    
    @GetMapping("/service-delete-{IDdichvu}")
    public String deleteSer(@PathVariable(name = "IDdichvu") Integer IDdichvu, RedirectAttributes redirectAttributes, Model model) {
    	try {
    		Serservice.delete(IDdichvu);
    		redirectAttributes.addFlashAttribute("message", "Dịch vụ có ID: " + IDdichvu + " đã được xoá thành công");
    	}catch(SerNotFoundException ex) {
    		redirectAttributes.addFlashAttribute("message", ex.getMessage());
    	}
    	return "redirect:/service";
    }
    
}
