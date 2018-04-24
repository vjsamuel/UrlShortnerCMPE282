package com.javasampleapproach.dynamodb.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.dynamodb.model.Customer;
import com.javasampleapproach.dynamodb.repo.CustomerRepository;


@RestController
public class WebController {

	@Autowired
	CustomerRepository repository;

	@RequestMapping("/delete")
	public String delete() {
		repository.deleteAll();
		return "Done";
	}

	@RequestMapping("/save")
	public String save() {
		// save a single Customer
		repository.save(new Customer("JSA-1", "Jack", "Smith", 12));

		// save a list of Customers
		repository.save(Arrays.asList(new Customer("JSA-2", "Adam", "Johnson",22), new Customer("JSA-3", "Kim", "Smith",54),
				new Customer("JSA-4", "David", "Williams", 24), new Customer("JSA-5", "Peter", "Davis",44)));

		return "Done";
	}
	
    @RequestMapping(method = RequestMethod.POST, value = "/saveCustomer") 
    public ResponseEntity<String> save(@RequestBody Customer input){
    	
    	try {
    		repository.save(input);
    		return new ResponseEntity("done", HttpStatus.ACCEPTED);
    	} catch(Exception e) {
    		return new ResponseEntity(null, HttpStatus.EXPECTATION_FAILED);
    	}
    }


	@RequestMapping("/findall")
	public String findAll() {
		String result = "";
		Iterable<Customer> customers = repository.findAll();

		for (Customer cust : customers) {
			result += cust.toString() + "<br>";
		}

		return result;
	}

	@RequestMapping("/findbyid")
	public String findById(@RequestParam("id") String id) {
		String result = "";
		result = repository.findOne(id).toString();
		return result;
	}

	@RequestMapping("/findbylastname")
	public String fetchDataByLastName(@RequestParam("lastname") String lastName) {
		String result = "";

		for (Customer cust : repository.findByLastName(lastName)) {
			result += cust.toString() + "<br>";
		}

		return result;
	}
}
