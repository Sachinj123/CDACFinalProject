package com.raktkosh.services;

import com.raktkosh.pojos.User;
import com.raktkosh.pojos.UserAddress;

public interface IUserAddressService {
	
	// save user address details
	User saveAddress(Long userId,UserAddress address);
	
	// delete user address details
	boolean deleteAddressById(Long userId);

	
	
}
