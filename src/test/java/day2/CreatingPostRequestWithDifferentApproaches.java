package day2;

import static io.restassured.RestAssured.*;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;

import org.testng.annotations.Test;

public class CreatingPostRequestWithDifferentApproaches {
	
	//1) creating post request using hashmap set
	
	@Test
	void postRequestUsingHashMap() {
		
		HashMap data = new HashMap<>();
		data.put("name", "Padma");
		data.put("location", "France");
		data.put("phone", "123456");
		
		String CoursesArr[] = {"C","C++"};
		data.put("courses", CoursesArr);
		
		given()
		
		.contentType("application/json")
		.body(data)
		
		.when()
		.post("")
		
		
		.then()
		.statusCode(201)
		.body("name", equalTo("Padma"))
		.body("location", equalTo("France"))
		.body("phone", equalTo("123456"))
		.body("courses[0]",equalTo("C"))
		.body("courses[1]",equalTo("C"))
		.log().all()
		
		;
		
		
	}
	
	@Test
	void postRequestUsingJsonLibrary() {
		
	}
	
	@Test
	void postRequestUsingPojoClases() {
		
	}
	
	@Test
	void postRequestUsingExternalFiles() {
		
	}

}
