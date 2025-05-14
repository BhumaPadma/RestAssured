package day8;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class UpdateUserTest {
	
	static final String BEARER_TOKEN = "9eb314859bfe5195d937453dda84cfcc0a92da79d937207a2ee8716f6f0ce61d";
	static final String BASE_URL  = "https://gorest.co.in/public/v2/users";
	
	@Test(dependsOnMethods = "day8.GetUserTest.getUser")
	void updateUser(ITestContext context) {
		JSONObject requestdata = new JSONObject();
		Faker faker = new Faker();
		requestdata.put("name", faker.name().fullName());
		requestdata.put("email", faker.internet().safeEmailAddress());
		requestdata.put("gender","Male");
		requestdata.put("status","active");
		
		given()
			.header("Authorization", "Bearer " + BEARER_TOKEN)
			.contentType("application/json")
			.body(requestdata.toString())
			.pathParam("id", (Integer)context.getAttribute("userid"))

		.when()
			.put(BASE_URL + "/{id}")
		.then()
			.statusCode(200)
			.log().body();
	}

}
