package day4;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class HeadersTest {
	
	@Test
	void testHeadersInResponse() {
	
	Response response =  given()
			
			.when()
				.get("https://www.google.com/")
			.then()
				.log().headers()
				.statusCode(200)
				.header("Content-Type", containsString("text/html"))
				.header("Server", equalTo("gws"))
				.header("Content-Encoding",  equalTo("gzip"))
				.extract().response();
	
		//Extract Specific header
		String headerValue = response.getHeader("Content-Type");
		System.out.println(headerValue);
		
		//Extract all headers and print
		Headers headers = response.getHeaders();
		for(Header h :headers) {
			System.out.println(h.getName() + " : " + h.getValue());
		}
		
	}
	
	
	
	

}
