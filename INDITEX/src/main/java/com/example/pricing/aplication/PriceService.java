package com.example.pricing.aplication;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pricing.domain.model.Brand;
import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.repository.BrandRepository;
import com.example.pricing.domain.repository.PriceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PriceService {

	@Autowired
	PriceRepository pricesRepository;

	@Autowired
	BrandRepository brandRepository;

	public Price getApplicablePrice(Long brandId, Long productId, String applicationDate) {

		//Controlamos que el formato de la fecha sea el esperado
		LocalDateTime date;
	    try {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        date = LocalDateTime.parse(applicationDate, formatter);
	    } catch (DateTimeParseException e) {
	        String message = "Fecha no válida. El formato esperado es 'yyyy-MM-dd HH:mm:ss'.";
	        System.out.println(message); 
            throw new IllegalArgumentException(message);
	    }

		Timestamp applicationDateTime = Timestamp.valueOf(date);

		//Si no existe ningun brand con el id proporcionado devolvemos una excepcion
		Brand brand = brandRepository.findById(brandId)
				.orElseThrow(() -> {
					String message = "Brand con ID " + brandId + " no encontrada";
	                System.out.println(message);
	                return new EntityNotFoundException(message);
				});

		//Si no existe ningun registro con los datos proporcionados devolvemos una excepcion 
		Optional<Price> priceEntityOptional = pricesRepository.findPrueba(brandId, productId, applicationDateTime);

		return priceEntityOptional
				.orElseThrow(() -> {
					String message = "No se encontró ningun articulo";
	                System.out.println(message);
	                return new EntityNotFoundException(message);
				});
	}

}
