package com.MyFood.repository;

import com.MyFood.model.ItensModel;
import com.MyFood.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItensRepository extends JpaRepository<ItensModel, UUID> {
}
