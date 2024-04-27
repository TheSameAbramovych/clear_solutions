package com.clear.solutions.task.core.repository;

import com.clear.solutions.task.core.repository.model.AddressRecord;
import com.clear.solutions.task.core.repository.model.UserRecord;
import com.clear.solutions.task.core.service.model.Address;

/**
 * The AddressRepository interface defines methods for managing address data.
 */
public interface AddressRepository extends CrudRepository<AddressRecord, Address>  {

}

