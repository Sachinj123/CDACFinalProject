package com.raktkosh.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raktkosh.dao.IBDCampRepo;
import com.raktkosh.dao.ICampRegRepo;
import com.raktkosh.dao.UserAddressRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.dto.BDCampDto;
import com.raktkosh.exceptions.ResourceNotFoundException;
import com.raktkosh.pojos.BDCamp;
import com.raktkosh.pojos.RegisterForCamp;
import com.raktkosh.pojos.User;
import com.raktkosh.pojos.UserAddress;

@Service
@Transactional
public class BDCampServiceImpl implements IBDCampService {
	@Autowired
	private IBDCampRepo bdCampRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ICampRegRepo campRegRepo;
	@Autowired
	private UserAddressRepository addRepo;

	@Override
	public List<BDCampDto> getAllCamps() {
		List<BDCamp> bdCamp = new ArrayList<BDCamp>();
		bdCamp = bdCampRepo.findAll();
		List<BDCamp> campList = bdCamp.stream().filter(p -> p.getCampDate().isAfter(LocalDate.now()))
				.collect(Collectors.toList());
		List<UserAddress> addList = new ArrayList<UserAddress>();
		addList = addRepo.getAddressForBDCamp();
		// List<>
		List<BDCampDto> dtoList = new ArrayList<BDCampDto>();
		for (int i = 0; i < campList.size(); i++) {
			UserAddress address = addList.get(i);
			BDCamp camp = campList.get(i);
			BDCampDto dto = mapper.map(address, BDCampDto.class);
			dto.setName(camp.getName());
			dto.setCampDate(camp.getCampDate());
			dto.setId(camp.getId());
			dto.setStartTime(camp.getStartTime());
			dto.setEndTime(camp.getEndTime());
			dtoList.add(dto);
			System.out.println(dto);

		}
		// dtoList=campList.stream().forEach(p->(mapper.map(p,
		// BDCamp.class))).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public BDCamp saveCamp(BDCampDto dto) {
		BDCamp camp = mapper.map(dto, BDCamp.class);
		return bdCampRepo.save(camp);
	}

	@Override
	public boolean deleteCampById(Long id) {

		BDCamp camp = bdCampRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("camp is not available with" + id));

		userRepo.deleteById(camp.getUserId());

		// BDCamp camp=
		bdCampRepo.deleteById(id);
		// addRepo.deleteById(camp.getUserId());
		return true;
	}

	@Override
	public BDCamp updateCampById(Long id, BDCamp camp) {
		BDCamp cmp = bdCampRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("camp is not available with" + id));
//		cmp.setCampDate(camp.getCampDate());
//		cmp.setCity(camp.getCity());
//		cmp.setDistrict(camp.getDistrict());
//		cmp.setEndTime(camp.getEndTime());
//		cmp.setStartTime(camp.getStartTime());
//		cmp.setLocality(camp.getLocality());
//		cmp.setName(camp.getName());
//		cmp.setZipcode(camp.getZipcode());
		return bdCampRepo.save(cmp);
	}

	@Override
	public boolean registerUserForCamp(Long id, Long campid) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user is not available with" + id));
		BDCamp camp = bdCampRepo.findById(campid)
				.orElseThrow(() -> new ResourceNotFoundException("camp is not available with" + campid));
		RegisterForCamp regCamp = new RegisterForCamp();
		regCamp.setCamp(camp);
		regCamp.setUser(user);
		campRegRepo.save(regCamp);
		return true;
	}

//	@Override
//	public List<BDCampDto> getAllCamps() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
