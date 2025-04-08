package day3;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

public class AuthenticationTypes {
	
	//1. Basic Authentication
	
	//@Test
	void verifyBasicAuth() {
		given()
			.auth().basic("postman", "password")
		.when()
			.get("https://postman-echo.com/basic-auth")
		.then()
			.statusCode(200)
			.body("authenticated", Matchers.equalTo(true))
			.log().all()
		;
	}
	
	//2. Basic preemptive Authentication
	
	//	@Test
		void verifyPreemptiveAuth() {
			given()
				.auth().preemptive().basic("postman", "password")
			.when()
				.get("https://postman-echo.com/basic-auth")
			.then()
				.statusCode(200)
				.body("authenticated", Matchers.equalTo(true))
				.log().body()
			;
		}
		
		// 3. Digest Authentication

		//@Test
		void verifyDigestAuth() {
			given()
				.auth().digest("postman", "password")
			.when()
				.get("https://postman-echo.com/basic-auth")
					
			.then()
				.statusCode(200)
				.body("authenticated", Matchers.equalTo(true))
				.log().body();
		}
		
		//4. Bearer Authentication
		
	//	@Test
		void verifyBearerAuth() {
			
			String bearerToken = "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";
			given()
				.header("Authorization" , "Bearer " +bearerToken)
			
			.when()
			.get("https://api.github.com/user/repos")
			
			.then()
			.statusCode(200)
			.log().body();
			
			;
			
		}
		
		//5. API Key Authentication
		
		@Test
		void verifyAPIKeyAuth() {
			given()
				.queryParam("q", "Delhi")
				.queryParam("units", "metric")
				.queryParam("cnt", "7")
				.queryParam("appid", "fe9c5cddb7e01d747b4611c3fc9eaf2c")
			.when()
				.get("https://api.openweathermap.org/data/2.5/forecast/daily")
			
			.then()
			.statusCode(200)
			.log().body()
			
			;
			
		}

	
}
