package day_11_GraphQL;

import org.testng.annotations.Test;


import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * https://datafetcher.com/graphql-json-body-converter
 * converting the graphql queries to json
 */
public class GraphQL_Queries {
	
	private static final String BASE_URL = "https://hasura.io/learn/graphql";
	private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY4NDU0M2ZiN2JiNGVlMWE3MmQ1YzU0MSJ9LCJuaWNrbmFtZSI6InBhZG1hIiwibmFtZSI6InBhZG1hQGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci9hNTNkMzQ2MDY0NmVhY2FmNTk2ZjU2YjliNDM4MDljND9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRnBhLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDI1LTA2LTA4VDA4OjA0OjEyLjczNVoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsInN1YiI6ImF1dGgwfDY4NDU0M2ZiN2JiNGVlMWE3MmQ1YzU0MSIsImlhdCI6MTc0OTM2OTg1NCwiZXhwIjoxNzQ5NDA1ODU0LCJzaWQiOiJuS2VVLThTT1pQRC1NZ0pTb1ZBOHpjZmRWZkozendmRyIsImF0X2hhc2giOiJ1NVljbUs0WV9qZWQ0dW5fRG9TOWZnIiwibm9uY2UiOiJHM3RkOWpvdm1Rc3ZHMTlxUlZ-MkZSMXl4aUsySHlndyJ9.ABCF5NQlxwQ2QR9IUkv4LjX2YbmCe1xYK9WcactBlFDmhbm4MSKbvVqabU459OqzHtzZZVJPDkPxnUZZiTU1QwmyXZPCDgWC_weGmtxn2AE2fliIHO89tjKQouFUzvsYaGxqsuLoJ-ly3nopTZqwSSvpVe94JXa6S6U7GSbO7Gmo1ziPPkD-Ye8pt-nEDVDTWtft91s0-5-hJapmhY2cdwatZCg3_YCIzBOfieA5ftSQmTQOLDl_pNbkM6ascdTO_4PLFbznR-cgBypAAKe9ZV4ooLTS-Hg04SFMgpoU6TOwF4lNzAmrfZV8itgTCsl0OoI4-jP5qii9DcKJDClgqg";
	
	
	//Fetch Users And Their Todos
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
			.log().body();
	}
	
	//Fetch user with Argument
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
	
	//Query with Multiple Arguments
	@Test
	public void testQueryWithMultipleArguments() {
		
		String graphqlQuery = "{\r\n"
				+ "  \"query\": \"query { users(limit: 2) { id name todos(order_by: {created_at: desc}, limit: 5) { id title } } }\"\r\n"
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
			.body("data.users[0].id", notNullValue())
			.body("data.users[0].name", notNullValue())
			.log().body();
	}
	
	//Query with Variables
	@Test
	public void testQueryWithVariables() {
		
		String graphqlQuery = "{\r\n"
				+ "  \"query\": \"query ($limit: Int!) { todos(limit: $limit) { id title }}\",\r\n"
				+ "  \"variables\": {\r\n"
				+ "    \"limit\": 5\r\n"
				+ "  }\r\n"
				+ "}";
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization" , AUTH_TOKEN)
			.body(graphqlQuery)
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(200)
			.body("data.todos", hasSize(greaterThan(0)))
			.body("data.todos[0].id", notNullValue())
			.body("data.todos[0].title", notNullValue())
			.log().body();
	}
	
	//Query with Filter (Where Clause)
	@Test
	public void testQueryWithFilter() {
		
		String graphqlQuery = "{\r\n"
				+ "  \"query\": \"query { todos(where: {is_public: {_eq: true}},limit : 5) { title is_public is_completed }}\"\r\n"
				+ "}";
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization" , AUTH_TOKEN)
			.body(graphqlQuery)
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(200)
			.body("data.todos", hasSize(5))
			.body("data.todos[0].is_public", equalTo(true))
			.body("data.todos[0].title", notNullValue())
			.log().body();
	}
	
}
