package com.adjt.food_service_manager_clean_arch.infra.database.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.UsuarioEntity;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByLogin(String login);
    Optional<UsuarioEntity> findByCpf(String cpf);
}
