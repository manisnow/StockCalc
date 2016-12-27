package hello;


import hello.service.StockUserService;

import java.io.IOException;

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
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController {
	
	
	@Autowired
	StockUserService stockUserService;
	
	
	
	

	//http://finance.google.com/finance/info?client=ig&q=NSE%3ATCS
	
	@CrossOrigin(origins= "*")
	@GetMapping("users/{userName}")
    public String getUser(@PathVariable("userName") String userName) throws JsonParseException, JsonMappingException, IOException {
		
		 return getJsonFromUser(stockUserService.userExists(userName));
    }
	
	
	@CrossOrigin(origins= "*")
	@PostMapping("users")
	public ResponseEntity<User>  addUser(@RequestBody User user) throws JsonParseException, JsonMappingException, IOException {		
		stockUserService.addUser(user.getEamilid(), user.getPassword());		
		return new ResponseEntity<User>(user, HttpStatus.OK);	
    }
	

	
	
	private String getJsonFromUser(User user) throws JsonParseException, JsonMappingException, IOException{		
	ObjectMapper mapper = new ObjectMapper();
	//JSON from file to Object
	String json = mapper.writeValueAsString(user);
		
	return json;
   }
		
	
	
	
	
}