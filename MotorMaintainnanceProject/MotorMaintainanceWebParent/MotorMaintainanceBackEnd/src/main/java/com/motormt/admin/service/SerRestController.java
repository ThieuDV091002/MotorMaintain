package com.motormt.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerRestController {

	@Autowired
	private SerService service;
	
	@PostMapping("/service-check_tendichvu")
	public String checkDuplicateTendichvu(@Param("tendichvu") String tendichvu) {
		return service.isTendichvuUnique(tendichvu) ? "OK" : "Duplicated";
	}
}
