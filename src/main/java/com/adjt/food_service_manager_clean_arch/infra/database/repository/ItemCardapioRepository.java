package com.adjt.food_service_manager_clean_arch.infra.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adjt.food_service_manager_clean_arch.infra.database.entity.ItemCardapioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.RestauranteEntity;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntity, Long>{
        Optional<ItemCardapioEntity> findById(Long id);
}