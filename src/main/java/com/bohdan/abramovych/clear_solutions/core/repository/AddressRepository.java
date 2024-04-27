package com.bohdan.abramovych.clear_solutions.core.repository;


import com.bohdan.abramovych.clear_solutions.core.service.model.Address;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.AddressRecord;

/**
 * The AddressRepository interface defines methods for managing address data.
 */
public interface AddressRepository extends CrudRepository<AddressRecord, Address>  {

}

