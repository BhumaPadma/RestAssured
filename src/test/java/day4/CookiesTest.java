package day4;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.Cookie;
import io.restassured.response.Response;

public class CookiesTest {
	
	@Test
	void testcookies() {
		
		Response response =  given()
			
		.when()
			.get("https://www.google.com/")
		.then()
			.log().cookies()
			.statusCode(200)
			.cookie("AEC" , notNullValue())
			.cookie("NID" , notNullValue())
			.extract().response();
		
		//Extract specific cookie value
		String cookieValue = response.getCookie("AEC");
		System.out.println("value of 'AEC' cookie : " + cookieValue);
		
		//Extract all cookies
		Map<String,String> allCookies = response.getCookies();
		System.out.println(allCookies);
		
		//printing all cookies using for loop
		for(String key: allCookies.keySet()) {
			System.out.println(key + ":" + allCookies.get(key) );
		}
		
		//get detailed information of specific cookie
		Cookie cookie_info = response.getDetailedCookie("AEC");
		System.out.println(cookie_info.hasExpiryDate());
		System.out.println(cookie_info.getExpiryDate());
		System.out.println(cookie_info.hasValue());
		System.out.println(cookie_info.getValue());
		System.out.println(cookie_info.isSecured());
		System.out.println(cookie_info.isHttpOnly());
		
	}

}
