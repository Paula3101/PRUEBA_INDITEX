package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.pricing.PricingApplication;
import com.example.pricing.domain.model.Price;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = PricingApplication.class)
@AutoConfigureMockMvc
class PriceControllerTest {

	 @Autowired
	    private MockMvc mockMvc;
	    
	    @Test
	    void test1() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "1")
		                .param("productId", "35455")     
		                .param("applicationDate", "2020-06-14 10:00:00"))
		                .andExpect(status().isOk()).andReturn();
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 01 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	    @Test
	    void test2() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "1")
		                .param("productId", "35455")     
		                .param("applicationDate", "2020-06-14 16:00:00"))
		                .andExpect(status().isOk()).andReturn(); 
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 02 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	    @Test
	    void test3() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "1")
		                .param("productId", "35455")     
		                .param("applicationDate", "2020-06-14 21:00:00"))
		                .andExpect(status().isOk()).andReturn();
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 03 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	    @Test
	    void test4() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "1")
		                .param("productId", "35455")     
		                .param("applicationDate", "2020-06-15 10:00:00"))
		                .andExpect(status().isOk()).andReturn(); 
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 04 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	    @Test
	    void test5() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "1")
		                .param("productId", "35455")     
		                .param("applicationDate", "2020-06-16 21:00:00"))
		                .andExpect(status().isOk()).andReturn(); 
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 05 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	    //identificador de cadena no valido
	    @Test
	    void test6() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "5")
		                .param("productId", "35455")     
		                .param("applicationDate", "2020-06-16 21:00:00"))
		                .andExpect(status().isOk()).andReturn(); 
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 06 Objeto Price recibido: " + priceResponse.toString());
	    }

	    //Fecha fuera de rango
	    @Test
	    void test7() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "1")
		                .param("productId", "35455")     
		                .param("applicationDate", "2019-06-16 21:00:00"))
		                .andExpect(status().isOk()).andReturn();
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 07 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	    //El identificador del peoductono existe
	    @Test
	    void test8() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "1")
		                .param("productId", "52348")     
		                .param("applicationDate", "2020-06-16 21:00:00"))
		                .andExpect(status().isOk()).andReturn(); 
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 08 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	    //Añadimos rango de prioridad y coinciden en 3 rangos de fechas
	    @Test
	    void test9() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "1")
		                .param("productId", "35455")     
		                .param("applicationDate", "2020-06-14 10:00:00"))
		                .andExpect(status().isOk()).andReturn();
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 09 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	  //Añadimos identificador de brand pero no esta dentro de rango
	    @Test
	    void test10() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "2")
		                .param("productId", "35458")     
		                .param("applicationDate", "2020-06-14 10:00:00"))
		                .andExpect(status().isOk()).andReturn();
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 10 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	  //Añadimos identificador de brand y esta dentro de rango
	    @Test
	    void test11() throws Exception {

	    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "2")
		                .param("productId", "35458")     
		                .param("applicationDate", "2020-06-15 10:00:00"))
		                .andExpect(status().isOk()).andReturn();
	    	 
	    	 String responseContent = result.getResponse().getContentAsString();
	    	 ObjectMapper objectMapper = new ObjectMapper();
	    	 Price priceResponse = objectMapper.readValue(responseContent, Price.class);
	    	 
	    	 System.out.println("TEST 11 Objeto Price recibido: " + priceResponse.toString());
	    }
	    
	  //Fecha erronea
	    @Test
	    void test12() throws Exception {

	    	  mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "2")
		                .param("productId", "35458")     
		                .param("applicationDate", "2020-06-155 10:00:00"))
		                .andExpect(status().isOk()).andReturn();

	    }
}

