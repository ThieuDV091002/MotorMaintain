package com.motormt.admin.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.motormt.common.entity.Ser;

@Service
public class SerService {
	public static final int SER_PER_PAGE = 5;

	@Autowired 
	private SerRepository repo;
	
	public List<Ser> listAll(){
		Sort sort = Sort.by(Sort.Direction.ASC, "IDdichvu");
		return (List<Ser>) repo.findAll(sort);
	}
	
	public Page<Ser> listByPage(int pageNum, String keyword){
		Pageable pageable = PageRequest.of(pageNum - 1, SER_PER_PAGE);
		if(keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		return repo.findAll(pageable);
	}

	public Ser save(Ser ser) {
	    return repo.save(ser);
	} 

	public Ser get(Integer iDdichvu) throws SerNotFoundException {
		try {
			return repo.findById(iDdichvu).get();
		}
		catch(NoSuchElementException ex){
			throw new SerNotFoundException("Không tìm thấy dịch vụ nào với ID " + iDdichvu);
		}
	}

	public void delete(Integer IDdichvu) throws SerNotFoundException {
		Long countById = repo.countByIDdichvu(IDdichvu);
		if(countById == null || countById == 0) {
			throw new SerNotFoundException("Không tìm thấy dịch vụ có ID " + IDdichvu);
		}
		repo.deleteById(IDdichvu);
	}

	public Ser update(Ser serInForm) {
		Ser serInDB = repo.findById(serInForm.getIDdichvu()).get();
		if (serInForm.getHinhanh() != null) {
			serInDB.setHinhanh(serInForm.getHinhanh());
		}
		serInDB.setIDdichvu(serInForm.getIDdichvu());
		serInDB.setTendichvu(serInForm.getTendichvu());
		serInDB.setLoaidv(serInForm.getLoaidv());
		serInDB.setDongia(serInForm.getDongia());
		serInDB.setHotro(serInForm.getHotro());
		serInDB.setMota(serInForm.getMota());
		serInDB.setDiscountPercent(serInForm.getDiscountPercent());
		serInDB.setPrice(serInForm.getPrice());
		
		return repo.save(serInDB);
	}
	
	public boolean isTendichvuUnique(String tendichvu) {
		Ser serByTendichvu = repo.getServiceByTendichvu(tendichvu);
		return serByTendichvu == null;
	}
}
