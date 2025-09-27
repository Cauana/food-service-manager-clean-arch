package com.adjt.food_service_manager_clean_arch.infra.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adjt.food_service_manager_clean_arch.infra.database.entity.ItemCardapioEntity;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntity, Long>{
}