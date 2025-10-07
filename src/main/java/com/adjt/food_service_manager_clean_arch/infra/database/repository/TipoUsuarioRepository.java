package com.adjt.food_service_manager_clean_arch.infra.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adjt.food_service_manager_clean_arch.infra.database.entity.TipoUsuarioEntity;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {

}