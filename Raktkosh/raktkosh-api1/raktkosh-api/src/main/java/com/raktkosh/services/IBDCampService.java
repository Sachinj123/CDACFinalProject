package com.raktkosh.services;

import java.util.List;

import com.raktkosh.dto.BDCampDto;
import com.raktkosh.pojos.BDCamp;

public interface IBDCampService {

	List<BDCampDto> getAllCamps();

	BDCamp saveCamp(BDCampDto dto);

	boolean deleteCampById(Long id);

	BDCamp updateCampById(Long id, BDCamp camp);

	boolean registerUserForCamp(Long id, Long campid);

}
