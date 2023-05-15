package com.raktkosh.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktkosh.dao.UserAddressRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.pojos.User;
import com.raktkosh.pojos.UserAddress;

@Service
@Transactional
public class UserAddressServiceImpl implements IUserAddressService {

	@Autowired
	private UserAddressRepository userAddressRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public User saveAddress(Long userId, UserAddress address) {
		User user = userRepo.findById(userId).orElseThrow();
		address.setUser(user);
		userAddressRepo.save(address);
		return user;
	}

	@Override
	public boolean deleteAddressById(Long userId) {
		//User user = userRepo.findById(userId).orElseThrow();
		userAddressRepo.deleteById(userId);
		return true;
	}
	
}
