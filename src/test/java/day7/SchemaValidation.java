package day7;


import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;


public class SchemaValidation {
	
	@Test
	void testJsonSchema()
	{
		given()
		
		.when()
			.get("https://mocktarget.apigee.net/json")
		.then()
			.statusCode(200)
			.contentType("application/json; charset=utf-8")
			.assertThat() 
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchema.json"))
			.log().body();
	}
	
	@Test
	void testXmlSchema()
	{
		given()
		
		.when()
			.get("https://mocktarget.apigee.net/xml")
		.then()
			.statusCode(200)
			.contentType("application/xml; charset=utf-8")
			.body(RestAssuredMatchers.matchesXsdInClasspath("xmlSchema.xsd"))
			.log().body();
	}
	
}
