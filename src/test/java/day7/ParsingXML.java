package day7;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParsingXML {

	/**
     * Test case to validate XML response data
     * Validates:
     * - HTTP status code
     * - Content type
     * - Specific XML element values
     */

	@Test(priority = 1)
	void testXmlResponse1() {

		given()
		
		.when()
			.get("https://mocktarget.apigee.net/xml")
		.then()
			.statusCode(200)
			.body("root.city", equalTo("San Jose"))
			.body("root.firstName", equalTo("John"))
			.body("root.lastName", equalTo("Doe"))
			.body("root.state", equalTo("CA"))
			.contentType("application/xml")
			.log().body();

	}
	
	/**
     * Test case to validate attributes in an XML response
     * Validates:
     * - HTTP status code
     * - Content type
     * - Specific XML attributes using '@' notation
     */

	
	@Test(priority = 2)
	void testXmlResponse2() {

		given()
		
		.when()
			.get("https://eu.httpbin.org/xml")
		.then()
			.statusCode(200)
			.contentType("application/xml")
			.body("slideshow.@title", equalTo("Sample Slide Show"))
			.body("slideshow.@date", equalTo("Date of publication"))
			.body("slideshow.@author", equalTo("Yours Truly"))
			.body("slideshow.slide[0].@type", equalTo("all"))
			.body("slideshow.slide[0].title", equalTo("Wake up to WonderWidgets!"))
			.log().body();

	}
	
	 /**
     * Test case to extract and parse XML response data using XmlPath
     * Validates:
     * - Number of slides in the response
     * - Slide titles
     * - Number of slide items
     * - Specific slide item values
     */

	@Test(priority = 3)
	void testXmlResponse3() {

		Response response = 
				given()
				.when()
				.get("https://eu.httpbin.org/xml")
				.then()
				.statusCode(200)
				.contentType("application/xml")
				.extract().response();
		
		XmlPath xmlpath = new XmlPath(response.asString());
		
		//captuing the title 
		List<String> slideTitles = xmlpath.getList("slideshow.slide.title");
		
		//counting the title and validating
		assertThat(slideTitles.size(), is(2));
		
		//validate the titles
		assertThat(slideTitles.get(0), is("Wake up to WonderWidgets!"));
		assertThat(slideTitles.get(1), is("Overview"));
		assertThat(slideTitles, hasItems("Wake up to WonderWidgets!", "Overview"));
		
		//get the items
		List<String> items = xmlpath.getList("slideshow.slide.item");
		
		//counting the items and validating
		assertThat(items.size(), is(3));
		
		//validate the items
		
		assertThat(items.get(0), is("WonderWidgets"));
		assertThat(items.get(2), is("buys"));
		assertThat(items, hasItems("WonderWidgets", "buys"));
		
		
	}
	

}
