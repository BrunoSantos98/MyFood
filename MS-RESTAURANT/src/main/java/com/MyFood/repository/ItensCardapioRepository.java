package com.MyFood.repository;

import com.MyFood.model.ItensCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItensCardapioRepository extends JpaRepository<ItensCardapio, UUID> {

    ItensCardapio findByName(String name);
}
