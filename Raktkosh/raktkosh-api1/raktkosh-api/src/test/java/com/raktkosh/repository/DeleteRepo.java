package com.raktkosh.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.raktkosh.pojos.BloodBank;

@SpringBootTest
public class DeleteRepo {
	@Autowired
	private com.raktkosh.dao.BankRepository bankRepo;
	
	
	@Autowired
	private com.raktkosh.dao.BloodBankRepository bbRepo;

	@Test
	void testFindAll() {
		Long id =(long) 14;
		BloodBank bank = bankRepo.findById(id).orElseThrow();
		//bbRepo.findByIdBank(bank).forEach(System.out::println);
	}
}
