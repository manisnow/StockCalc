package hello;

import hello.service.StockUserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StockController {
	
	@Autowired
	StockUserService stockUserService;

	//http://finance.google.com/finance/info?client=ig&q=NSE%3ATCS
	
	@CrossOrigin(origins= "http://localhost:3000/")
	@GetMapping("stock/{stockName}")
    public String stockGet(@RequestParam(value="exchange", required=false, defaultValue="NSE") String exchange,@PathVariable("stockName") String stockName) throws JsonParseException, JsonMappingException, IOException {
		
		//String url1="http://finance.google.com/finance/info?client=ig";
		//https://www.google.com/finance/info?q=NSE:TCS
		String url1="https://www.google.com/finance/info?";
		System.out.println("stockName"+stockName);
		String url2="q="+exchange+":"+stockName;
		
		System.out.println("URL"+url1+url2);
		  RestTemplate restTemplate = new RestTemplate();
	        String quote = restTemplate.getForObject(url1+url2, String.class);
	        	        quote=quote.replaceAll("//", "");
	        System.out.println("quote"+quote);
	       // String price=getStockFromJson(quote);
	        Stock stock=createDummyStock();
	        ArrayList<Stock> list=new ArrayList<Stock>();
	        list.add(stock);
	        String stockJson=getJsonFromStock(getStockFromJson(quote));
	        System.out.println("json"+stockJson);
	        return stockJson;
    }
	
	
	private Stock createDummyStock(){
		Stock stock=new Stock();
		
		stock.setId("784961");
		stock.setT("TCS");
		stock.setE("NSE");
		stock.setL("2,299.80");
		stock.setL_fix("2,299.80");
		stock.setL_cur("&#8377;2,299.80");
		stock.setS("0");
		stock.setLtt("3:48PM GMT+5:30");
		
		return stock;
	}
	
	private Stock[] getStockFromJson(String json) throws JsonParseException, JsonMappingException, IOException{
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		

		//JSON from file to Object
		List objList = mapper.readValue(json, List.class);

		//JSON from URL to Object
		//Staff obj = mapper.readValue(new URL("http://mkyong.com/api/staff.json"), Staff.class);

		//JSON from String to Object
		//Staff obj = mapper.readValue(jsonInString, Staff.class);
		String price=null;
		List<Stock> stockList=new ArrayList<Stock>();
		for(Object object:objList){
			
			 System.out.println(object);
			 Map map=(Map)(object);
			 if(map!=null && map.containsKey("l")){
				 Stock stock=new Stock();
				price= map.get("l").toString();
				stock.setId(map.get("id").toString());
				stock.setT(map.get("t").toString());
				stock.setE(map.get("e").toString());				
				stock.setL(map.get("l").toString());
				stock.setL_fix(map.get("l_fix").toString());
				stock.setL_cur(map.get("l_cur").toString());
				stock.setS(map.get("s").toString());
				stock.setLtt(map.get("ltt").toString());
				stockList.add(stock);
			 }
			 
			 
		}
		
		
		return stockList.toArray(new Stock[0]);
		
	}
	
	
	
	
	
		
	private String getJsonFromStock(Stock stock[]) throws JsonParseException, JsonMappingException, IOException{
			
			ObjectMapper mapper = new ObjectMapper();
			

			//JSON from file to Object
		String json = mapper.writeValueAsString(stock);

			//JSON from URL to Object
			//Staff obj = mapper.readValue(new URL("http://mkyong.com/api/staff.json"), Staff.class);

			//JSON from String to Object
			//Staff obj = mapper.readValue(jsonInString, Staff.class);
			
			
			
			return json;
	}
	
	
	
	
}
