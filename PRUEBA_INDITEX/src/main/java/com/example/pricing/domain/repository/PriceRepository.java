package com.example.pricing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pricing.domain.model.Price;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

	@Query(value = "SELECT p " + "FROM Price p " + "WHERE productId = :productId AND brand.id = :brandId "
			+ "AND startDate <= :applicationDate AND endDate >= :applicationDate " + "ORDER BY priority DESC LIMIT 1")
	Optional<Price> findPrueba(@Param("brandId") Long brandId, @Param("productId") Long productId,
			@Param("applicationDate") Timestamp applicationDateStart);

}