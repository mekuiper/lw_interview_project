package com.example.springboot;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Greetings from Spring Boot!")));
	}
	
//Return a customer by customer id
//	@GetMapping("/getcustomerbyid")
	@Test
	public void getcustomerbyid() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/getcustomerbyid").param("id", "006").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.response.numFound").value("1"))
				.andExpect(jsonPath("$.response.docs[0].id").value("006"));
	}
	
	
	//Delete a customer by customer id
	//@GetMapping(path="/deletecustomerbyid")
	@Test
	public void deletecustomerbyid() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/deletecustomerbyid").param("id", "006").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}	
		
	@Test
	public void verifydeletecustomerbyid() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/getcustomerbyid").param("id", "006").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.response.numFound").value("0"));
	}
	
	//@PostMapping(path="/createcustomers", consumes = "application/json", produces = "application/json")
	//TODO create test
	
    //	@GetMapping("/listcustomers")
	//TODO create test
	
}
