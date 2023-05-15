package com.raktkosh.services;

import java.util.List;

import com.raktkosh.dto.BloodBankRepositoryDTO;
import com.raktkosh.dto.BloodBankRepositoryIdDTO;
import com.raktkosh.pojos.BloodRepository;

public interface IBloodBankRepositoryService {
  /**
   * Persist a blood repository entity in database.
   * @param repository
   * @return persisted blood repository entity
   */
  BloodRepository addRepository(BloodBankRepositoryDTO repository);
  
  /**
   * Update available units in repository.
   * @param id
   * @param quantity
   * @return updated blood repository entity
   */
  BloodRepository updateRepositoryById(BloodBankRepositoryIdDTO id, int quantity);
  
  /**
   * Delete a repository by id.
   * @param id
   * @return boolean value indicating deleted or not.
   */
  boolean deleteRepositoryById(BloodBankRepositoryIdDTO id);
  
  /**
   * Fetch list of blood repository by blood bank.
   * @param id
   * @return List<BloodRepository>
   */
  List<BloodRepository> findByBloodBank(Long id);
  
  /**
   * Fetch list of blood repository by blood type and antigen.
   * @param id
   * @return List<BloodRepository>
   */
  List<BloodRepository> findByBloodTypeAndAntigen(BloodBankRepositoryIdDTO id);
}
