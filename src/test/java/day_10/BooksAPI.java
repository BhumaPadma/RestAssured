package day_10;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONObject;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class BooksAPI {
	
	private static final String BEARER_TOKEN = "Bearer 6216d58de2027544f10f0c8739169085882b413f2110c6166cbe66fe0c590aa5";
	private static final String BASE_URL = "https://simple-books-api.glitch.me/orders";
	
	@Test
	void testSubmitAndDeleteOrder() {
		
		JSONObject requestData = new JSONObject();
		requestData.put("bookId", 5);
		requestData.put("customerName","Johnn");
		
		String orderID = given()
			.contentType("application/json")
			.header("Authorization", BEARER_TOKEN)
			.body(requestData.toString())
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(201)
			.log().body()
			.extract().response().jsonPath().getString("orderId");
		
		given()
			.header("Authorization", BEARER_TOKEN)
			.contentType("application/json")
			.pathParam("orderId", orderID)
		.when()
			.delete(BASE_URL +"/{orderId}")
		.then()
			.statusCode(204);
		
		
	}

}
