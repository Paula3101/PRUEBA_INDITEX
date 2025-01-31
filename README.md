# INDITEX
Este proyecto consta de una pequeña aplicación que permite al usuario conocer el precio de un articulo en una determinada fecha. 
## REQUISITOS DEL SISTEMA
Hemos creado un proyecto maven con Spring boot.
- **Java Develoment Kit (JDK)** : version 17
- **Spring boot**: version 3.4.2
- **H2 Database**
- **Mokito y Junit**
## ESTRUCTURA DEL PROYECTO 
Este proyecto sigue el modelo de arquitectura hexagonal (Ports & Adapters) para garantizar una mejor separación de responsabilidades y facilitar el mantenimiento del código.
1. Domain: Contiene las entidades y lógica de negocio.
2. Application: Define los casos de uso y la lógica de aplicación.
3. Infrastructure: Contiene las configuraciones y mecanismos técnicos (persistencia, seguridad, etc.).
   
```PRICES_INDITEX/
│── src/
│   ├── main/
│   │   ├── java/com/example/pricing
│   │   │   ├── Application
│   │   │   │   ├──PriceService.java #logica de negocio
│   │   │   ├── Domain
│   │   │   │   ├──Model #Modelo de datos, entidades
│   │   │   │   │  ├──Brand.java
│   │   │   │   │  ├──Price.java
│   │   │   │   ├──Repository #Adaptador de persistencia
│   │   │   │   │  ├──BrandRepository.java 
│   │   │   │   │  ├──PriceRepository.java
│   │   │   ├── Infraestructure
│   │   │   │   │  ├──PriceController.java #Recoge las peticiones REST
│   │   │   ├── PricingApplication.java  #Aplicacion principal
│   │   ├── resources/
│   │   │   ├── application.properties  # Configuración de Spring Boot
│   │   │   ├── data.sql  # Datos iniciales de la base de datos
│   │   │   ├── schema.sql  # Esquema de la base de datos
│   ├── test/
│   │   ├── java/com/example/demo
│   │   │   ├──PriceControllerTest.java  # Pruebas de la aplicación
│── data/                 # Datos extraídos
│── notebooks/            # Análisis exploratorio y visualizaciones
│── pom.xml               # Dependencias de Maven
│── README.md             # Documentación del proyecto
```
### EXPLICACIÓN MÁS DETALLADA
**1. Domain**
La capa de dominio (Domain Layer) es el núcleo de la aplicación.<br>
Contiene la lógica de negocio y define las reglas y entidades del sistema, sin depender de tecnologías externas como bases de datos o frameworks.<br>
En este caso, contiene dos entidades:<br>
**- Brand:** Clase que hace referencia a la tala Brand, que explicaremos más adelante.<br>
Esta clase recoge el identificador de la cadena de grupo y su correspondiente nombre<br>
**- Price:** Clase que hace referencia a la tala Prices, que explicaremos más adelante.<br>
Esta clase recoge un identificador de la cadena de grupo, identificador de código de producto, un rango de fechas, un identificador de la tarifa, un desambiguador de aplicacion de precios, el precio final y la moneda.<br>
Por otra parte, nos encontramos con los **Repositories**, es decir, los adaptadores de persistencia.<br>
Son interfaces que actúan como intermediarios entre la lógica de negocio y la base de datos.<br>
En el caso de la clase **PriceRepository.java** contiene la llamada a la base de datos que devuelve un precio entre un rango de fechas.<br>
**2. Application**<br>
La capa de aplicación (Application Layer) es la encargada de orquestar los casos de uso y coordinar la ejecución de la lógica de negocio.<br>
En este caso la clase **PriceService.java** se encarga de formatear la fecha correspondiente, obtener si existe el Brand con el id proporcionado y devolver en un objeto tipo Price<br>
si el metodo que hemos realizado en el repository devuelve algún resultado.<br>
**3. Infraestructure**<br>
La capa de infraestructura se encarga de manejar los detalles técnicos y de comunicación con el mundo exterior, es decir, es la que se encarga de comunicar los datos recibidos<br>
por los usuarios a la capa de aplicacion.<br>
En este caso, la clase PriceController.java se encarga de recoger los parametros en un servicio REST para pasarlos a la capa de aplicacion.<br>
**4. Aplicacion principal**<br>
Nuestra aplicación principal PricingApplication.java es la clase principal que inicia la aplicación Spring Boot. La anotación @SpringBootApplication combina configuraciones automáticas y escaneo de componentes,<br> 
y el método main invoca SpringApplication.run() para iniciar la aplicación y todo el ciclo de vida de Spring Boot.<br>

**Resources**<br>
Este apartado contiene 3 tipos de clases:
- **application.properties:** En esta clase realizamos la configuracion del spring boot
- **data.sql:** introducimos los datos necesarios para nuestras tablas en H2
- **schema.sql:** creacion de dichas tablas, con sus correspondientes dependencias
Estos son los datos que hemos introducido para hacer los casos de prueba explicados mas adelante.
Hemos añadido más para poder probar distintos identificadores de cadena, códigos de producto y prioridad.
```
INSERT INTO brand (name) VALUES
('ZARA'),
('MANGO');


INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, currency) VALUES
(1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
(1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
(1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),
(1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR'),
(2, '2020-06-15 00:00:00', '2020-10-03 11:00:00', 3, 35458, 1, 25.50, 'EUR'),
(1, '2020-06-14 09:00:00', '2020-07-15 11:00:00', 3, 35455, 2, 31.95, 'EUR');
```

**Test**<br>
Por último, encontramos la clase **PriceControllerTest.java** <br>
En esta clase desarrollamos los casos de prueba. <br>
Introducimos los datos: brandId, productId y applicationDate. El primero es el identificador de la cadena de grupo, el segundo es el identificador de un producto y la fecha.
**Caso 1**
```  @Test
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
```
En este caso devuelve el siguiente objeto ya que la fecha se encuentra en el rango del primer y ultimo registro, pero muestra el último ya que la prioridad es más alta. 
```
TEST 01 Objeto Price recibido: Price{
id=6
brandId=1
productId=35455
price=31.95
currency='EUR'
}
```

**Caso 2**
``` @Test
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
```
En este caso devuelve el siguiente objeto ya que la fecha se encuentra en el rango del segundo y ultimo registro, pero muestra el último ya que la prioridad es más alta. 
```
TEST 01 Objeto Price recibido: Price{
id=6
brandId=1
productId=35455
price=31.95
currency='EUR'
}
```

**Caso 3**
``` @Test
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
```

**Caso 4**
```  @Test
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
```

**Caso 5**
``` @Test
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
```

**Caso 6**
``` @Test
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
```

**Caso 7**
```  @Test
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
```

**Caso 8**
``` @Test
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
```

**Caso 9**
``` @Test
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
```

**Caso 10**
``` @Test
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
```

**Caso 11**
``` @Test
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
```

**Caso 12**
``` @Test
	    void test12() throws Exception {

	    	  mockMvc.perform(MockMvcRequestBuilders.get("/prices")
	    			 	.param("brandId", "2")
		                .param("productId", "35458")     
		                .param("applicationDate", "2020-06-155 10:00:00"))
		                .andExpect(status().isOk()).andReturn();

	    }
```













  
