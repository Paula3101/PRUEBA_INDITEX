package com.example.pricing.infraestructure;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.pricing.aplication.PriceService;
import com.example.pricing.domain.model.Price;

@RestController
@RequestMapping("/prices")
public class PriceController {
	
    private final PriceService pricingService;

    public PriceController(PriceService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping
    public Price getPrice(@RequestParam("applicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String applicationDate,
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Long brandId) {

        return pricingService.getApplicablePrice(brandId, productId, applicationDate);
    }
}
