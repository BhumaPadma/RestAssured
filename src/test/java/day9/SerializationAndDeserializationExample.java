package day9;

import static io.restassured.matcher.RestAssuredMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class SerializationAndDeserializationExample {

	
	String stdId;

	// Serialization - converting java object into json object
	@Test
	void testSerialization() {
		
		String[] courses = { "Selenium", "java", "Python" };
		Student stu = new Student("Padma", "India", "12347895601", courses);

		 stdId = given()
			.contentType("application/json")
			.body(stu)
		.when()
			.post("http://localhost:3000/students")
		.then()
			.statusCode(201)
			.log().body()
			.extract().response().jsonPath().getString("id");
	}
	
	
	@Test(dependsOnMethods = "testSerialization")
	void testDeserialization() {

		Response response = given()
				.pathParam("Id", stdId )
		.when()
			.get("http://localhost:3000/students/{Id}")
		.then()
			.statusCode(200)
			.extract().response();
		
		//Extract ID separately from Json response
		
		String id = response.jsonPath().getString("id");
		
		//Use as() to deserialize
		Student stu = response.as(Student.class);
		
		assertThat(stu.getName(), is("Padma"));
		assertThat(stu.getLocation(), is("India"));
		assertThat(stu.getPhone(), is("12347895601"));
		assertThat(stu.getCourses()[0], is("Selenium"));
		
		System.out.println("Extracted details are " + stu + id);
		
	}
	

	@Test(dependsOnMethods = "testDeserialization")
	void testDelete() {
		given()
			.pathParam("Id", stdId )
		.when()
			.delete("http://localhost:3000/students/{Id}")
		.then()
		 	.statusCode(200); 
	}
	
	

}
