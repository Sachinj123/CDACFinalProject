package com.raktkosh.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raktkosh.pojos.RegisterForCamp;

public interface ICampRegRepo extends JpaRepository<RegisterForCamp, Long> {

}
