package com.MyFood.repository;

import com.MyFood.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    CustomerModel findByCpf(String cpf);
    CustomerModel findByEmail(String email);
}
