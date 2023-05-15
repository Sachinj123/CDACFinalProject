package com.raktkosh.services;

import java.rmi.activation.ActivationException;

import javax.mail.MessagingException;

import com.raktkosh.dto.BloodBankDTO;

public interface IAccountService {
	void userSignUp(BloodBankDTO dto) throws ActivationException, MessagingException;
}
