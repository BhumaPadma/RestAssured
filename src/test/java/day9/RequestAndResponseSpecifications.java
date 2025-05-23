package day9;

import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.json.JSONObject;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;


public class RequestAndResponseSpecifications {
	
	Faker faker = new Faker();
	String username = faker.name().username();
	
	@Test
	void createUser() {
		JSONObject requestdata = new JSONObject();
		requestdata.put("id", 78);
		requestdata.put("username", username);
		requestdata.put("firstName", faker.name().firstName());
		requestdata.put("lastname", faker.name().lastName());
		requestdata.put("email", faker.internet().safeEmailAddress());
		requestdata.put("password", faker.internet().password());
		requestdata.put("phone", "14597863145");
		requestdata.put("userStatus",0);
		
		given()
			.contentType("application/json")
			.body(requestdata.toString())
		.when()
			.post("https://petstore.swagger.io/v2/user")
		.then()
			.statusCode(200)
			.log().body();
		
		System.out.println(username);
	}
	
	@Test
	void getUser() {
		
		given()
			.contentType("application/json")
			.pathParam("username", username)
		.when()
			.get("https://petstore.swagger.io/v2/user/{username}")
		.then()
			.statusCode(200)
			.log().body();
	}
}
