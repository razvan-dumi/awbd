package com.razvan.proiect.services;

import com.razvan.proiect.models.Address;
import com.razvan.proiect.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public void save(Address address) {
        addressRepository.save(address);
    }
}
