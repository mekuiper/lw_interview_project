package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {
	
	@Value("${spring.solr.server}")
	private String solrServer;
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	@GetMapping("/testQuerySolr")
	public ResponseEntity<String> querySolr(@RequestParam(value = ""
			+ "", defaultValue = "peanutbutter") String searchTerm) {
		return restTemplate.getForEntity(solrServer + "/select?q=id:"+searchTerm,  String.class);
		
	}
	
	@PostMapping(path="/createcustomers", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> createCustomer(@RequestBody String customers) {

		System.out.println("/creatcustomers: " + customers);
		//curl -X POST -H 'Content-Type: application/json' 'http://localhost:8983/solr/my_collection/update' --data-binary '
		//curl -X POST -H 'Content-Type: application/json' 'http://localhost:8087/creatcustomers' --data-binary '
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(customers,headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity(
				solrServer + "/update?commit=true", entity , String.class);
		
		System.out.println("ResponseEntity<String> response " + response);
		
		return response;
//		
	}
	
	@GetMapping("/listcustomers")
	public ResponseEntity<String> listCustomers(@RequestParam(value = "", defaultValue = "*:*") String searchTerm) {
		return restTemplate.getForEntity(solrServer + "/select?q="+searchTerm,  String.class);	
	}
	
	@GetMapping("/getcustomerbyid")
	public ResponseEntity<String> getCustomerById(@RequestParam(value = "", defaultValue = "") String id) {
		return restTemplate.getForEntity(solrServer + "/select?q=id:"+id,  String.class);	
	}
	
//	@PostMapping(path="/deletecustomerbyid", consumes = "application/json", produces = "application/json")
	@GetMapping(path="/deletecustomerbyid")
	public ResponseEntity<String> deleteCustomerById(@RequestParam (value = "", defaultValue = "")String id) {

		System.out.println("/deletecustomerbyid: " + id);
		//curl -X POST -H 'Content-Type: application/json' 'http://localhost:8983/solr/my_collection/update' --data-binary '
		//curl -X POST -H 'Content-Type: application/json' 'http://localhost:8087/creatcustomers' --data-binary '
		
		HttpHeaders headers = new HttpHeaders();
//		curl http://localhost:8983/solr/mykeyspace.mysolr/update --data '<delete><query>color:red</query></delete>' -H 'Content-type:text/xml; charset=utf-8'
		headers.setContentType(MediaType.TEXT_XML);
		//headers.setAcceptCharset(new );

		HttpEntity<String> entity = new HttpEntity<String>("<delete><query>id:"+id+"</query></delete>",headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity(
				solrServer + "/update?commit=true", entity , String.class);
		
		System.out.println("ResponseEntity<String> response " + response);
		
		return response;
//		
	}


}
