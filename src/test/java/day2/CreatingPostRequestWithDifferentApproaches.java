package day2;

import static io.restassured.RestAssured.*;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.response.Response;


public class CreatingPostRequestWithDifferentApproaches {
	
	String id ;
	
	//1) creating post request using hashmap set
	
	//@Test(priority = 1)
	void postRequestUsingHashMap() {
		
		HashMap data = new HashMap();
		data.put("name", "Padma");
		data.put("location", "France");
		data.put("phone", "123456");
		
		String CoursesArr[] = {"C","C++"};
		data.put("courses", CoursesArr);
		
		Response response = 
		
		given()
		.contentType("application/json")
		.body(data)
		.when()
		.post("http://localhost:3000/students/") ;
		
		id = response.jsonPath().getString("id");
		
		response.then()
		.statusCode(201)
		.body("name", equalTo("Padma"))
		.body("location", equalTo("France"))
		.body("phone", equalTo("123456"))
		.body("courses[0]",equalTo("C"))
		.body("courses[1]",equalTo("C++"))
		.log().all()
		
		;
		
		
	}
	
	//2) creating post request using json library
	
//	@Test(priority = 1)
	void postRequestUsingJsonLibrary() {
		
		JSONObject data = new JSONObject();
		
		data.put("name", "Padma");
		data.put("location", "France");
		data.put("phone", "123456");
		
		String CoursesArr[] = {"C","C++"};
		data.put("courses", CoursesArr);
		
		Response response = 
		
		given()
		.contentType("application/json")
		.body(data.toString())
		.when()
		.post("http://localhost:3000/students/") ;
		
		id = response.jsonPath().getString("id");
		
		response.then()
		.statusCode(201)
		.body("name", equalTo("Padma"))
		.body("location", equalTo("France"))
		.body("phone", equalTo("123456"))
		.body("courses[0]",equalTo("C"))
		.body("courses[1]",equalTo("C++"))
		.header("Content-Type", "application/json")
		.log().all()
		
		;
		
	}
	
//	3) creating post request using pojo classes
	
	//@Test
	void postRequestUsingPojoClases() {
		
		POJO_class data = new POJO_class();
		
		data.setName("Padma");
		data.setLocation("Paris");
		data.setPhone("1236478");
		
		String CoursesArr[] = {"C","C++"};
		data.setCourses(CoursesArr);
		
		Response response = 
				
				given()
				.contentType("application/json")
				.body(data)
				.when()
				.post("http://localhost:3000/students/") ;
				
				id = response.jsonPath().getString("id");
				
				response.then()
				.statusCode(201)
				.body("name", equalTo("Padma"))
				.body("location", equalTo("Paris"))
				.body("phone", equalTo("1236478"))
				.body("courses[0]",equalTo("C"))
				.body("courses[1]",equalTo("C++"))
				.header("Content-Type", "application/json")
				.log().all()
				
				;
		
	}
	
	
//	4)Creating Post request using external json files
	@Test(priority = 1)
	void postRequestUsingExternalFiles() throws FileNotFoundException {
		
		File f = new File(".\\body.json");
		
		FileReader fr = new FileReader(f);
		
		JSONTokener jt = new JSONTokener(fr);
		
		JSONObject data = new JSONObject(jt);
		
		Response response = 
				
				given()
				.contentType("application/json")
				.body(data.toString())
				.when()
				.post("http://localhost:3000/students/") ;
				
				id = response.jsonPath().getString("id");
				
				response.then()
				.statusCode(201)
				.body("name", equalTo("Padma"))
				.body("location", equalTo("Paris"))
				.body("phone", equalTo("7894578"))
				.body("courses[0]",equalTo("Selenium"))
				.body("courses[1]",equalTo("RestAPI"))
				.header("Content-Type", "application/json")
				.log().all()
				
				;
		
		
	}
	
	@Test(priority = 2)
	void postDelete() {
		
		given()
		
		.when()
		.delete("http://localhost:3000/students/"+id)
		
		.then()
		.statusCode(200)
		;
	}

}
