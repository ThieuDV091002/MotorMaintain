package com.motormt.site.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.motormt.common.entity.Ser;

@Service
public class SerService {

public static final int SEARCH_RESULTS_PER_PAGE = 6;
	
	@Autowired
	private SerRepository repo;

	public Page<Ser> search(String keyword, int pageNum){
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		return repo.search(keyword, pageable);
	}

	public Ser get(Integer iDdichvu) throws SerNotFoundException {
		try {
			return repo.findById(iDdichvu).get();
		}catch(NoSuchElementException ex) {
			throw new SerNotFoundException("Không tìm thấy dịch vụ với ID: " + iDdichvu);
		}
	}
}
