package com.raktkosh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raktkosh.pojos.BDCamp;
@Repository
public interface IBDCampRepo extends JpaRepository<BDCamp, Long> {

}
