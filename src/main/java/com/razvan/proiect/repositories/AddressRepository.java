package com.razvan.proiect.repositories;

import com.razvan.proiect.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
