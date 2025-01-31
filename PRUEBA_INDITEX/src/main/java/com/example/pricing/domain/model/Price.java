package com.example.pricing.domain.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "prices")
@AllArgsConstructor
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "brand_id")
	private Brand brand;
	@Column(name = "start_date")
	private Timestamp startDate;
	@Column(name = "end_date")
	private Timestamp endDate;
	@Column(name = "price_list")
	private Integer priceList;
	@Column(name = "product_id")
	private Long productId;
	private Short priority;
	private Double price;
	private String currency;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Integer getPriceList() {
		return priceList;
	}
	public void setPriceList(Integer priceList) {
		this.priceList = priceList;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Short getPriority() {
		return priority;
	}
	public void setPriority(Short priority) {
		this.priority = priority;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	 @Override
	    public String toString() {
	        return "Price{\n" +
	                "id=" + id + "\n" +
	                "brandId=" + (brand != null ? brand.getId(): "null") + "\n" +
	                "productId=" + productId + "\n" +
	                "price=" + price + "\n" +
	                "currency='" + currency + '\'' + "\n" +
	                '}';
	    }
	
}
