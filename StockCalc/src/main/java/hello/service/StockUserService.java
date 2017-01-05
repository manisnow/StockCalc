package hello.service;

import hello.Stock;
import hello.User;
import hello.UserStock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.DateTime;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.LongValue;
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
		
		System.out.println("inside insertOrUpdateUserStock " +  userStock.getEmailid() );

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

				datastore.delete(stocksE.next().getKey());

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
			userStock.setInvAmt(userE.getLong("invAmt"));

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

			Key key = datastore.allocateId(keyFactory1.newKey());
			Entity user = Entity
					.newBuilder(key)
					.set("emailid", emailid)
					.set("c",
							stock.getC())
									
					.set("c_fix",
							stock.getC_fix())
									
					.set("ccol",
							stock.getCcol())
									
					.set("cp",
							stock.getCp())
									
					.set("cp_fix",
							stock.getCp_fix())
									
					.set("e",
							stock.getE())
									
					.set("l",
							stock.getL())
									
					.set("l_cur",
							stock.getL_cur())
									
					.set("l_fix",
							stock.getL_fix())
									
					.set("lt",
							stock.getLt())
									
					.set("lt_dts",
							stock.getLt_dts())
									
					.set("ltt",
							stock.getLtt())
									
					.set("pcls_fix",
							stock.getPcls_fix())
									
					.set("s",
							stock.getS())
									
					.set("t",
							stock.getT())
									
					.set("percentage",
							(stock.getPercentage())
									)

					.set("dateCreated", DateTime.now()).build();
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