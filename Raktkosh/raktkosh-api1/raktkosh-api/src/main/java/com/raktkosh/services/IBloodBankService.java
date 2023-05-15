package com.raktkosh.services;

import java.util.List;

import com.raktkosh.dto.BloodBankDTO;
import com.raktkosh.pojos.BloodBank;

public interface IBloodBankService {
	BloodBank saveBankDetails(BloodBankDTO bloodBank);

	BloodBank getBankDetailsById(Long bankId);
//	BloodBankDTO getBankDetailsById(Long bankId);

	BloodBank getBankDetailsByBankName(String bankName);

	boolean deleteBankDetails(Long bankId);

	BloodBank updateBankDetails(BloodBank bloodBank);
	
	List<BloodBank> getAllBanks();
	
	boolean deleteById(Long id);

	
}
