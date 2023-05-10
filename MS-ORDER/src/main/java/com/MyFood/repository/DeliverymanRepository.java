package com.MyFood.repository;

import com.MyFood.model.Deliveryman;
import com.MyFood.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliverymanRepository extends JpaRepository<Deliveryman, UUID> {
    boolean existsByCpf(String cpf);
    boolean existsByLicensePlate(String licensePlate);
}
