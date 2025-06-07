package day_11_GraphQL;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GraphQL_Queries {
	
	private static final String BASE_URL = "https://hasura.io/learn/graphql";
	private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY4NDA1YmQwM2IzOTdiNWE2YTkyMjA5NSJ9LCJuaWNrbmFtZSI6InBhZG1hIiwibmFtZSI6InBhZG1hQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci8zOGNlM2NlZmZjMDc4MDMyYzg0NTZlNzNmNzMxZWJhMD9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRnBhLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDI1LTA2LTA0VDE0OjQ0OjMzLjY1MVoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsInN1YiI6ImF1dGgwfDY4NDA1YmQwM2IzOTdiNWE2YTkyMjA5NSIsImlhdCI6MTc0OTMwNzAwOSwiZXhwIjoxNzQ5MzQzMDA5LCJzaWQiOiIyVmtjMUg2dDdDWlkxZWY5NnItMjZVUjFrV19qS1JPWCIsImF0X2hhc2giOiI5ZEpyb1ZYd0MzMkRkdDRtcUdyUG9nIiwibm9uY2UiOiJWY2RuTm00T2hGUDhNMk1ob1F4SjMzNlBUbk9waHE5eCJ9.BVFmXpOOnpltE3h3FBTSnL2sINiBZQ8jlO_7WdKscx9q3-NrQyqu2Lmh9n5ZY2iRPl4VOnMyuBYxn3bb1pfsFhxglgmqtzlwUhs5Zb1EagJkfR3-Tf-KzZGH6F-jDU7SnBj8E7FeyQEdnAO5RPezbXQiyKOYlKlYh1mvpr_CuL1Yhdk4FBjVCe5pUcR9RlplXSKvljG_wysn3RGPgXl9dDCLP0uyISzw1vwQd9zSIpfJdAl1yT1HTEw0aeeyiSykTXPzZkdXtVofcVYsD4XOaj1Y31xfL0Cda13p5LGtd9sWUfYGDcI6GxxegZvPTxf7tYxifCZsgDgQoYcbTnR_lQ";

	@Test
	public void testFetchUsersAndTheirTodos() {
		
		String graphqlQuery = "{\r\n"
				+ "  \"query\": \" query { users { name todos { title } } }\"\r\n"
				+ "}";
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization" , AUTH_TOKEN)
			.body(graphqlQuery)
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(200)
			.body("data.users", hasSize(greaterThan(0)))
			.body("data.users[0].name", notNullValue())
			.body("data.users[0].todos", hasItem(greaterThanOrEqualTo(0)))
			.log().body();
	}
	
	@Test
	public void testFetchUserWithArgument() {
		String graphqlQuery = "{\r\n"
				+ "  \"query\": \"{ todos(limit: 5) { id title }}\"\r\n"
				+ "}";
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization" , AUTH_TOKEN)
			.body(graphqlQuery)
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(200)
			.body("data.todos", hasSize(lessThanOrEqualTo(5)))
			.body("data.todos[0].id", notNullValue())
			.body("data.todos[0].title", notNullValue())
			.log().body();
	}
}
