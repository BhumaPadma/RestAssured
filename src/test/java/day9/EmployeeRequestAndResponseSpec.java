package day9;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/*
 * http://localhost:3000/employees
 * http://localhost:3000/employees/1
 */

public class EmployeeRequestAndResponseSpec {
	
	RequestSpecification httpRequest ;
	ResponseSpecification httpResponse;
	
	@BeforeClass
	public void setup() {
		//Create Request specification 
		RequestSpecBuilder requestSpec  = new RequestSpecBuilder();
		requestSpec.setBaseUri("http://localhost:3000");
		requestSpec.setBasePath("/employees");
		requestSpec.setContentType("application/json");
		httpRequest = requestSpec.build();
		
		
		// Create Response specification
		ResponseSpecBuilder responseSpec = new ResponseSpecBuilder();
		responseSpec.expectStatusCode(200);
		responseSpec.expectContentType("application/json");
		httpResponse = responseSpec.build();

	}
	
	@Test
	void getAllEmployees() {
		
		given()
			.spec(httpRequest)
		
		.when()
			.get()
		
		.then()
			.spec(httpResponse)
			.log().body()
		;
		
	}
	
	@Test
	void getAllMaleEmployees() {
		System.out.println("All male employee details : ");
		given()
			.queryParam("gender", "Male")
			.spec(httpRequest)
		
		.when()
			.get()
		
		.then()
			.spec(httpResponse)
			.log().body();
	}
	
	@Test
	void getAllFemaleEmployees() {
		System.out.println("All Female employee details : ");
		given()
			.queryParam("gender", "Female")
			.spec(httpRequest)
		
		.when()
			.get()
		
		.then()
			.spec(httpResponse)
			.log().body();
	}
	
}
