package day8;

import static io.restassured.RestAssured.given;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class DeleteUserTest {
	static final String BEARER_TOKEN = "9eb314859bfe5195d937453dda84cfcc0a92da79d937207a2ee8716f6f0ce61d";
	static final String BASE_URL  = "https://gorest.co.in/public/v2/users";
	
	@Test(dependsOnMethods = "day8.UpdateUserTest.updateUser")
	void deleteUser(ITestContext context) {
		given()
			.header("Authorization", "Bearer " + BEARER_TOKEN)
			.pathParam("id", (Integer)context.getAttribute("userid"))
		.when()
			.delete(BASE_URL + "/{id}")
		.then()
			.statusCode(204);
	}

}
