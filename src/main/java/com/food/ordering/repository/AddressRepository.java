package com.food.ordering.repository;

import com.food.ordering.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
