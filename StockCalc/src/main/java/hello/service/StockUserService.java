package hello.service;

import hello.Stock;
import hello.User;
import hello.UserStock;
import hello.mail.SendMail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.DateTime;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Entity.Builder;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

/**
 * A stock user service to add user and retrive new user
 * 
 */
@Service
public class StockUserService {
	
	private static final Logger log = LoggerFactory.getLogger(StockUserService.class);

	// [START build_service]
	// Create an authorized Datastore service using Application Default
	// Credentials.
	private final Datastore datastore = DatastoreOptions.getDefaultInstance()
			.getService();

	// Create a Key factory to construct keys associated with this project.
	private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(
			"StockCalc_User");

	private final KeyFactory keyFactory1 = datastore.newKeyFactory().setKind(
			"StockCalc_UserStocks");

	// [END build_service]

	// [START add_entity]
	/**
	 * Adds a task entity to the Datastore.
	 *
	 * @param emaild
	 *            The user name
	 * @param password
	 *            the user password
	 * @return The {@link Key} of the entity
	 * @throws DatastoreException
	 *             if the ID allocation or put fails
	 */
	public Key addUser(String emailId, String password) {
		Key key = datastore.allocateId(keyFactory.newKey());
		Entity user = Entity
				.newBuilder(key)
				.set("emailid", StringValue.newBuilder(emailId).build())
				.set("password",
						StringValue.newBuilder(password)
								.setExcludeFromIndexes(true).build())
				.set("dateCreated", DateTime.now()).build();
		datastore.put(user);
		return key;
	}

	public Key insertOrUpdateUserStock(UserStock userStock) {
		
		System.out.println("inside insertOrUpdateUserStock " +  userStock.toString());

		Query<Entity> query = Query
				.newEntityQueryBuilder()
				.setKind("StockCalc_User")
				.setFilter(PropertyFilter.eq("emailid", userStock.getEmailid()))
				.build();
		QueryResults<Entity> users = datastore.run(query);
		System.out.println(query.toString() + users);
		if (users.hasNext()) {
			
			Entity userE = users.next();		
			Key key = keyFactory.newKey(userE.getKey().getId());
			Entity userEntity = Entity.newBuilder(key)
					.set("invAmt", userStock.getInvAmt())
					.set("investSchdAlertType", userStock.getInvestSchdAlertType())
					.set("emailid",userE.getString("emailid"))
					.set("password",userE.getString("password")).build();			
			datastore.update(userEntity);
			
			System.out.println("updated"  + userEntity);
			// Entity userStockE =new Entity

			Query<Entity> query1 = Query
					.newEntityQueryBuilder()
					.setKind("StockCalc_UserStocks")
					.setFilter(
							PropertyFilter.eq("emailid", userStock.getEmailid()))
					.build();
			QueryResults<Entity> stocksE = datastore.run(query1);
			while (stocksE.hasNext()) {
				
				Entity entity=stocksE.next();
				datastore.delete(entity.getKey());
				log.debug("deleted" + entity.getKey() + " " + entity.getString("emailid"));

			}
			addStocks(userStock.getStocks(), userStock.getEmailid());

		}

		return null;
	}

	public UserStock retriveUserStock(String emailid) {

		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind("StockCalc_User")
				.setFilter(PropertyFilter.eq("emailid", emailid)).build();
		QueryResults<Entity> users = datastore.run(query);
		System.out.println(query.toString() + users);
		if (users.hasNext()) {

			UserStock userStock = new UserStock();
			Entity userE = users.next();

			userStock.setEmailid(emailid);
			userStock.setId(userE.getKey().getId());
			if(userE.contains("invAmt")){
			userStock.setInvAmt(userE.getLong("invAmt"));
			}
			if(userE.contains("investSchdAlertType")){
			userStock.setInvestSchdAlertType(userE.getString("investSchdAlertType"));
		     }

			Query<Entity> query1 = Query
					.newEntityQueryBuilder()
					.setKind("StockCalc_UserStocks")
					.setFilter(
							PropertyFilter.eq("emailid", userStock.getEmailid()))
					.build();
			QueryResults<Entity> stocksE = datastore.run(query1);

			Stock stocks[] = getStocks(stocksE);
			userStock.setStocks(stocks);

			return userStock;
		}

		return null;
	}

	private void addStocks(Stock[] stocks, String emailid) {
		
		

		for (Stock stock : stocks) {
			
			System.out.println(""+stock);

			Key key = datastore.allocateId(keyFactory1.newKey());
			Builder stockBuilder=Entity
					.newBuilder(key);
			
			
			
			//Entity user = Entity
					//.newBuilder(key)
					
			stockBuilder=stockBuilder.set("emailid", emailid);
			
			        if(stock.getC()!=null)
					stockBuilder=stockBuilder.set("c",stock.getC());
			        else
			        	stockBuilder=stockBuilder.setNull("c");
			        if(stock.getC_fix()!=null)				
					stockBuilder=stockBuilder.set("c_fix",
							stock.getC_fix());
			        else
			        	stockBuilder=stockBuilder.setNull("c_fix");
			        if(stock.getCcol()!=null)				
					stockBuilder=stockBuilder.set("ccol",
							stock.getCcol());
			        else
			        	stockBuilder=stockBuilder.setNull("ccol");
			        if(stock.getCp()!=null)				
					stockBuilder=stockBuilder.set("cp",
							stock.getCp());
			        else
			        	stockBuilder=stockBuilder.setNull("cp");
			        if(stock.getCp_fix()!=null)				
					stockBuilder=stockBuilder.set("cp_fix",
							stock.getCp_fix());
			        else
			        	stockBuilder=stockBuilder.setNull("cp_fix");
			        if(stock.getE()!=null)				
					stockBuilder=stockBuilder.set("e",
							stock.getE());
			        else
			        	stockBuilder=stockBuilder.setNull("e");
			        if(stock.getL()!=null)				
					stockBuilder=stockBuilder.set("l",
							stock.getL());
			        else
			        	stockBuilder=stockBuilder.setNull("l");
			        if(stock.getL_cur()!=null)				
					stockBuilder=stockBuilder.set("l_cur",
							stock.getL_cur());
			        else
			        	stockBuilder=stockBuilder.setNull("l_cur");
			        if(stock.getL_fix()!=null)				
					stockBuilder=stockBuilder.set("l_fix",
							stock.getL_fix());
			        else
			        	stockBuilder=stockBuilder.setNull("l_fix");
			        if(stock.getLt()!=null)				
					stockBuilder=stockBuilder.set("lt",
							stock.getLt());
			        else
			        	stockBuilder=stockBuilder.setNull("lt");
			        if(stock.getLt_dts()!=null)				
					stockBuilder=stockBuilder.set("lt_dts",
							stock.getLt_dts());
			        else
			        	stockBuilder=stockBuilder.setNull("lt_dts");
			        if(stock.getLtt()!=null)				
					stockBuilder=stockBuilder.set("ltt",
							stock.getLtt());
			        else
			        	stockBuilder=stockBuilder.setNull("ltt");
			        if(stock.getPcls_fix()!=null)				
					stockBuilder=stockBuilder.set("pcls_fix",
							stock.getPcls_fix());
			        else
			        	stockBuilder=stockBuilder.setNull("pcls_fix");
			        if(stock.getS()!=null)				
					stockBuilder=stockBuilder.set("s",
							stock.getS());
			        else
			        	stockBuilder=stockBuilder.setNull("s");
			        if(stock.getT()!=null)				
					stockBuilder=stockBuilder.set("t",
							stock.getT());
			        else
			        	stockBuilder=stockBuilder.setNull("t");
			        
					stockBuilder=stockBuilder.set("percentage",
							(stock.getPercentage())
									);

					Entity user=stockBuilder.set("dateCreated", DateTime.now()).build();
			datastore.put(user);
		}

	}

	private Stock[] getStocks(QueryResults<Entity> stockE) {

		ArrayList<Stock> stockList = new ArrayList<Stock>();

		while (stockE.hasNext()) {

			Stock stock = new Stock();

			Entity entity = stockE.next();

			stock.setC(entity.getString("c"));

			stock.setC_fix(entity.getString("c_fix"));

			stock.setCcol(entity.getString("ccol"));

			stock.setCp(entity.getString("cp"));

			stock.setCp_fix(entity.getString("cp_fix"));

			stock.setE(entity.getString("e"));

			stock.setL(entity.getString("l"));

			stock.setL_cur(entity.getString("l_cur"));

			stock.setL_fix(entity.getString("l_fix"));

			stock.setLt(entity.getString("lt"));

			stock.setLt_dts(entity.getString("lt_dts"));

			stock.setLtt(entity.getString("ltt"));

			stock.setPcls_fix(entity.getString("pcls_fix"));

			stock.setS(entity.getString("s"));
			stock.setT(entity.getString("t"));
			stock.setPercentage(entity.getLong("percentage"));

			stockList.add(stock);

		}

		return stockList.toArray(new Stock[0]);

	}

	/**
	 * 
	 * @param emailId
	 * @param password
	 * @return true if the emailid existing in the database otherwise false;
	 */

	public User userExists(String emailId) {

		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind("StockCalc_User")
				.setFilter(PropertyFilter.eq("emailid", emailId)).build();

		QueryResults<Entity> users = datastore.run(query);

		System.out.println(query.toString() + users);
		if (users.hasNext()) {
			Entity entity = users.next();

			User user = new User();
			user.setEmailid(entity.getString("emailid"));
			user.setPassword(entity.getString("password"));
			System.out.println(user.toString());
			return user;

		}

		return null;

	}

	/**
	 * Returns a list of all task entities in ascending order of creation time.
	 *
	 * @throws DatastoreException
	 *             if the query fails
	 */
	Iterator<Entity> listUSer() {
		Query<Entity> query = Query.newEntityQueryBuilder().setKind("Task")
				.setOrderBy(OrderBy.asc("created")).build();
		return datastore.run(query);
	}

	// [END retrieve_entities]

	// [START delete_entity]
	/**
	 * Deletes a task entity.
	 *
	 * @param id
	 *            The ID of the task entity as given by {@link Key#id()}
	 * @throws DatastoreException
	 *             if the delete fails
	 */
	void deleteUser(long id) {
		datastore.delete(keyFactory.newKey(id));
	}

	// [END delete_entity]

	// [START format_results]
	/**
	 * Converts a list of task entities to a list of formatted task strings.
	 *
	 * @param tasks
	 *            An iterator over task entities
	 * @return A list of tasks strings, one per entity
	 */
	static List<String> formatTasks(Iterator<Entity> tasks) {
		List<String> strings = new ArrayList<>();
		while (tasks.hasNext()) {
			Entity task = tasks.next();
			if (task.getBoolean("done")) {
				strings.add(String.format("%d : %s (done)", task.getKey()
						.getId(), task.getString("description")));
			} else {
				strings.add(String.format("%d : %s (created %s)", task.getKey()
						.getId(), task.getString("description"), task
						.getDateTime("created")));
			}
		}
		return strings;
	}

	// [END format_results]

	/**
	 * Handles a single command.
	 *
	 * @param commandLine
	 *            A line of input provided by the user
	 */
	void handleCommandLine(String commandLine) {
		String[] args = commandLine.split("\\s+");

		if (args.length < 1) {
			throw new IllegalArgumentException("not enough args");
		}

		String command = args[0];
		switch (command) {
		case "new":
			// Everything after the first whitespace token is interpreted to be
			// the description.
			args = commandLine.split("\\s+", 2);
			if (args.length != 2) {
				throw new IllegalArgumentException("missing description");
			}
			// Set created to now() and done to false.
			addUser(args[1], args[2]);
			System.out.println("task added");
			break;
		case "done":

			break;
		case "list":
			assertArgsLength(args, 1);

			break;
		case "delete":
			assertArgsLength(args, 2);
			// deleteTask(L(args[1]));
			System.out.println("task deleted (if it existed)");
			break;
		default:
			throw new IllegalArgumentException("unrecognized command: "
					+ command);
		}
	}

	private void assertArgsLength(String[] args, int expectedLength) {
		if (args.length != expectedLength) {
			throw new IllegalArgumentException(String.format(
					"expected exactly %d arg(s), found %d", expectedLength,
					args.length));
		}
	}
	
	
	public void sendStockEmailNoticationToUsers(String invSchdType){
		
		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind("StockCalc_User")
				.setFilter(PropertyFilter.eq("investSchdAlertType", invSchdType)).build();
		QueryResults<Entity> users = datastore.run(query);
		System.out.println(query.toString() + users);
		while (users.hasNext()) {
			try{
			Entity user=users.next();
			UserStock userStock=retriveUserStock(user.getString("emailid"));
			String html=buildUserStockHtml(userStock);
			new SendMail().sendMail("manisnow@gmail.com",userStock.getEmailid() ,userStock.getEmailid().substring(userStock.getEmailid().indexOf(".")), "Time To Invest", html);
			
			}catch(Exception e){
				log.error(e.getMessage(),e);
			}
			
			
			
		}
		
	}
	
	private String buildUserStockHtml(UserStock userStock) throws JsonParseException, JsonMappingException, IOException{
		StringBuilder htmlBuilder=new StringBuilder();
		htmlBuilder.append("<html>").append("<body>").append("<label>InvestMent:").append(userStock.getInvAmt()).append("</label>");		
		htmlBuilder.append("<table><tr>").append("<th>Excahnge</th><th>Stock Name</th><th>Stock Price</th><th>Percentage</th><th>quantity</th><th>Total Amt</th></tr>");	
		for(Stock stock:userStock.getStocks()){			
			Stock latStock=getStockByStockName(stock.getE(),stock.getT());
			double qty = Math.floor((userStock.getInvAmt() * (stock.getPercentage() / 100)) / Double.parseDouble(latStock.getL_fix()));
		    double totalAmt = qty * Double.parseDouble(stock.getL_fix());
			 htmlBuilder.append("<tr>")
		    .append("<td>").append(stock.getE()).append("</td>")
		    .append("<td>").append(stock.getT()).append("</td>")				
			.append("<td>").append(latStock.getL()).append("</td>")
		    .append("<td>").append(stock.getPercentage()).append("</td>")
		    .append("<td>").append(qty).append("</td>")
		    .append("<td>").append(totalAmt).append("</td>")
		    .append("</tr>");
		}
		return htmlBuilder.toString();
	}
	
	
	public Stock getStockByStockName(String exchange,String stockName) throws JsonParseException, JsonMappingException, IOException{
		
		
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
			        
			        
			        return getStockFromJson(quote)[0];
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

	private static void printUsage() {
		System.out.println("Usage:");
		System.out.println();
		System.out
				.println("  new <description>  Adds a task with a description <description>");
		System.out.println("  done <task-id>     Marks a task as done");
		System.out
				.println("  list               Lists all tasks by creation time");
		System.out.println("  delete <task-id>   Deletes a task");
		System.out.println();
	}
}