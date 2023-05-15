package com.raktkosh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.pojos.BloodBank;
import com.raktkosh.pojos.BloodRepository;
import com.raktkosh.pojos.BloodRepositoryID;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodRepository, BloodRepositoryID> {
  /**
   * Find list of bloods available in repository by blood type and antigen.
   * @param type
   * @param antigen
   * @return List<BloodRepository>
   */
  List<BloodRepository> findByIdTypeAndIdAntigen(BloodTypes type, Antigens antigen);
  
  /**
   * Find all available bloods by blood bank.
   * @param bankID
   * @return List<BloodRepository>
   */
  List<BloodRepository> findByIdBank(BloodBank bank);
}
