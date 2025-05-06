package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParsingComplexJSONResponse {

	JSONObject getJsonResponse() {

		File jsonFile = new File(".\\src\\test\\resources\\complex.json");

		FileReader fileReader = null;

		try {
			fileReader = new FileReader(jsonFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		JSONTokener jsontokener = new JSONTokener(fileReader);

		JSONObject jsonResponse = new JSONObject(jsontokener);

		return jsonResponse;

	}

	@Test(priority = 1)
	void testUserDetails() {

		String str = getJsonResponse().toString();

		JsonPath jsonPath = new JsonPath(str);

		// Verify the status field in the JSON response is "success".

		String status = jsonPath.getString("status");
		System.out.println("Value of Status Key : " + status);

		assertThat(status, is("success"));
		System.out.println("1. Status Assertion Passed");

		// Validate the id, name, and email fields of the user.
		int userid = jsonPath.getInt("data.userDetails.id");
		String userName = jsonPath.getString("data.userDetails.name");
		String userEmail = jsonPath.getString("data.userDetails.email");

		assertThat(userid, is(12345));
		assertThat(userName, is("John Doe"));
		assertThat(userEmail, is("john.doe@example.com"));

		System.out.println("2. UserDetails Assertion Passed");

		// Confirm the first phone number is of type home with the value 123-456-7890.

		String userPhoneType = jsonPath.getString("data.userDetails.phoneNumbers[0].type");
		String userPhoneNumber = jsonPath.getString("data.userDetails.phoneNumbers[0].number");

		assertThat(userPhoneNumber, is("123-456-7890"));
		assertThat(userPhoneType, is("home"));

		System.out.println("3. Phnnumber details Assertion Passed");

		// Verify the geo coordinates of the user's address are latitude 39.7817 and
		// longitude -89.6501.

		Double geoLatitude = jsonPath.getDouble("data.userDetails.address.geo.latitude");
		Double geoLongitude = jsonPath.getDouble("data.userDetails.address.geo.longitude");

		assertThat(geoLatitude, is(39.7817));
		assertThat(geoLongitude, is(-89.6501));

		System.out.println("4. geo coordinates details Assertion Passed");

		// Validate that the user has enabled notifications and is using the "dark"
		// theme.

		Boolean notifications = jsonPath.getBoolean("data.userDetails.preferences.notifications");
		String theme = jsonPath.getString("data.userDetails.preferences.theme");

		assertThat(notifications, is(true));
		assertThat(theme, is("dark"));

		System.out.println("5. preferences details validated");

	}

	@Test(priority = 2)
	void testRecentOrderValidation() {

		String str = getJsonResponse().toString();

		JsonPath jsonPath = new JsonPath(str);

		// Verify the total number of orders is 2.

		int totalOrders = jsonPath.getList("data.recentOrders").size();

		assertThat(totalOrders, is(2));

		// Validate the orderId of the first order is 101 and that the total amount is
		// 1226.49.

		int firstOrderId = jsonPath.getInt("data.recentOrders[0].orderId");
		Double firstOrderTotalAmt = jsonPath.getDouble("data.recentOrders[0].totalAmount");

		assertThat(firstOrderId, is(101));
		assertThat(firstOrderTotalAmt, is(1226.49));

		System.out.println("First order details validated");

		// Confirm the name of the second item in the first order is "Mouse" and its
		// price is 25.50.

		String firstOrderItem = jsonPath.getString("data.recentOrders[0].items[1].name");
		Double mousePrice = jsonPath.getDouble("data.recentOrders[0].items[1].price");

		assertThat(firstOrderItem, is("Mouse"));
		assertThat(mousePrice, is(25.50));

		System.out.println("Second item in first order details validated");

		// Check that the second order has only one item, a "Smartphone", with a price
		// of 799.99

		int secondOrder = jsonPath.getList("data.recentOrders[1].items").size();
		String secondOrderItem = jsonPath.getString("data.recentOrders[1].items[0].name");
		Double smartphonePrice = jsonPath.getDouble("data.recentOrders[1].items[0].price");

		assertThat(secondOrderItem, is("Smartphone"));
		assertThat(secondOrder, is(1));
		assertThat(smartphonePrice, is(799.99));

		System.out.println("Second order details validated");

	}
}
