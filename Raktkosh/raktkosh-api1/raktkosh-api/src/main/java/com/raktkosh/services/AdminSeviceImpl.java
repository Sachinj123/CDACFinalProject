package com.raktkosh.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.raktkosh.dao.AdminRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.dto.BloodBankRepositoryIdDTO;
import com.raktkosh.enums.Role;
import com.raktkosh.exceptions.AdminException;
import com.raktkosh.exceptions.BloodRepositoryException;
import com.raktkosh.pojos.BloodRepository;
import com.raktkosh.pojos.BloodRepositoryID;
import com.raktkosh.pojos.User;

@Service
@Transactional
public class AdminSeviceImpl implements IAdminService {
	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<User> featchAllUsers() {

		return adminRepo.findAll();
	}


	@Override
	public List<User> findByRole(Role user) {
		return adminRepo.findByRole(user);

	}

	@Override
	public User updateActivatedById(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new AdminException("Fetching by Id failed inside service"));
		user.setActivated(!user.isActivated());
		return userRepo.save(user);
	}

//find all suspended users list
	@Override
	public List<User> findByActivated(boolean activated) {
		return adminRepo.findByActivated(activated);
	}
}
