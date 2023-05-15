package com.raktkosh.services;

import java.util.List;
import java.util.Optional;

import com.raktkosh.enums.Role;
import com.raktkosh.pojos.BloodRepository;
import com.raktkosh.pojos.User;

public interface IAdminService {
	List<User> featchAllUsers();

	List<User> findByRole(Role role);

	// User updateUserActivation(User user);
	User updateActivatedById(Long id);

     //list activation status of users   
	List<User> findByActivated(boolean activated);

}
