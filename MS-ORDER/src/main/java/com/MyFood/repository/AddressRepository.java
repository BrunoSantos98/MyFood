package com.MyFood.repository;

import com.MyFood.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressModel, UUID> {
    AddressModel findByCepAndLogradouroAndnumber(String cep, String logradouro, short number);
    boolean existsByCepAndLogradouroAndnumber(String cep, String logradouro, short number);
}
