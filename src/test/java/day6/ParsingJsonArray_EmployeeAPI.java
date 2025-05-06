package day6;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParsingJsonArray_EmployeeAPI {

	@Test
	void testParsingJsonObject() {

		ResponseBody responsebody = given()

				.when().get("http://localhost:3000/employees")

				.then().statusCode(200).extract().response().body();

		JsonPath jsonpath = new JsonPath(responsebody.asString());

		// get the size of jsonarray
		int employeeCount = jsonpath.getInt("size()");
		System.out.println(employeeCount);

		// print all details
		for (int i = 0; i < employeeCount; i++) {
			String firstName = jsonpath.getString("[" + i + "].first_name");
			String lastName = jsonpath.getString("[" + i + "].last_name");
			String email = jsonpath.getString("[" + i + "].email");
			String gender = jsonpath.getString("[" + i + "].gender");

			System.out.println(firstName + " " + lastName + " " + email + " " + gender);
		}

		// search for an employee name "steve" in the list
		boolean status = false;
		for (int i = 0; i < employeeCount; i++) {
			String firstName = jsonpath.getString("[" + i + "].first_name");
			if (firstName.equals("steve")) {
				status = true;
				break;
			}
			assertThat(true, is(true));

		}

	}

}
