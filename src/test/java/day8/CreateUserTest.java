package day8;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class CreateUserTest {
	
	static final String BEARER_TOKEN = "9eb314859bfe5195d937453dda84cfcc0a92da79d937207a2ee8716f6f0ce61d";
	static final String BASE_URL  = "https://gorest.co.in/public/v2/users";
	int userid;
	
	Faker faker = new Faker();
	
	@Test(priority = 1)
	void createUser(ITestContext context) {
		JSONObject requestdata = new JSONObject();
		requestdata.put("name", faker.name().fullName());
		requestdata.put("email", faker.internet().safeEmailAddress());
		requestdata.put("gender","Male");
		requestdata.put("status","Inactive");
		
		userid = given()
			.header("Authorization", "Bearer " + BEARER_TOKEN)
			.contentType("application/json")
			.body(requestdata.toString())
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(201)
			.log().body()
			.extract().response().jsonPath().getInt("id");
		
		context.setAttribute("userid", userid);
	}

}
