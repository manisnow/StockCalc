package hello;

import java.io.IOException;

import hello.service.StockUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
public class UserStockController {

	
	@Autowired
	StockUserService stockUserService;
	
	
	
	
	@CrossOrigin(origins= "*")
	@PostMapping("stocks")
	public ResponseEntity  saveStocks(@RequestBody UserStock userStock) throws JsonParseException, JsonMappingException, IOException {	
		System.out.println("userStock"+ userStock.getEmailid() );
		stockUserService.insertOrUpdateUserStock(userStock);
		  
		 return new ResponseEntity(String.valueOf("Stocks saved into database"),  HttpStatus.OK);
	  
    }
	
	
	@CrossOrigin(origins= "*")
	@GetMapping("stocks/{emailid:.+}")
	public ResponseEntity retriveStocks(@PathVariable("emailid") String emailid) throws JsonParseException, JsonMappingException, IOException {	
		 System.out.println("Retrive Stock Started");
		 UserStock userStock=stockUserService.retriveUserStock(emailid);
		 System.out.println("Retrive Stock ended");
		 
		  
		 return new ResponseEntity<UserStock>(userStock,  HttpStatus.OK);
	  
    }
	
	
}
