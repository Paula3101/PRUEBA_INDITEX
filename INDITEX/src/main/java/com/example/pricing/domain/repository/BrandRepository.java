package com.example.pricing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pricing.domain.model.Brand;

/**
 * Repositorio de acceso a BRand
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
