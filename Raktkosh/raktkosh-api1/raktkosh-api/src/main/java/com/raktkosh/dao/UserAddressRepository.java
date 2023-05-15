package com.raktkosh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.raktkosh.pojos.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long>{
	//@Query("select id,antigen ,created_on , post_category,type user_id,city,district,locality,state from post join users_address where post.user_id=users_address.user_id",nativeQuery = true)
//	@Query("select p.users_address.* from Post p join fetch p.user.address where p.userId=users_address.userId ")
	@Query(value = "select users_address.* from post join users_address where post.user_id = users_address.user_id", nativeQuery = true)
	List<UserAddress> getAddAddressByPost();
	@Query(value="select users_address.* from bd_camp join users_address where users_address.user_id= bd_camp.user_id and date > curdate();", nativeQuery = true)
	List<UserAddress> getAddressForBDCamp();
//	
}
