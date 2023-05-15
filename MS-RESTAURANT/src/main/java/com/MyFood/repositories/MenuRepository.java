package com.MyFood.repositories;

import com.MyFood.models.MenuModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuRepository extends JpaRepository<MenuModel, UUID> {
}
