package br.com.fiapfood.infraestructure.repositories.interfaces.jpa;

import br.com.fiapfood.infraestructure.entities.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRestauranteJpaRepository extends JpaRepository<RestauranteEntity, UUID> {
}