package com.MyFood.repository;

import com.MyFood.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressModel, UUID> {
    AddressModel findByCepAndLogradouroAndNumber(String cep, String logradouro, short number);
    boolean existsByCepAndLogradouroAndNumber(String cep, String logradouro, short number);
}
