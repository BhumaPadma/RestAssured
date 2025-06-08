package day_11_GraphQL;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * https://datafetcher.com/graphql-json-body-converter
 * converting the graphql queries to json
 */

public class GraphQL_Mutations {
	
	private static final String BASE_URL = "https://hasura.io/learn/graphql";
	private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY4NDU0M2ZiN2JiNGVlMWE3MmQ1YzU0MSJ9LCJuaWNrbmFtZSI6InBhZG1hIiwibmFtZSI6InBhZG1hQGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci9hNTNkMzQ2MDY0NmVhY2FmNTk2ZjU2YjliNDM4MDljND9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRnBhLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDI1LTA2LTA4VDA4OjA0OjEyLjczNVoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsInN1YiI6ImF1dGgwfDY4NDU0M2ZiN2JiNGVlMWE3MmQ1YzU0MSIsImlhdCI6MTc0OTM2OTg1NCwiZXhwIjoxNzQ5NDA1ODU0LCJzaWQiOiJuS2VVLThTT1pQRC1NZ0pTb1ZBOHpjZmRWZkozendmRyIsImF0X2hhc2giOiJ1NVljbUs0WV9qZWQ0dW5fRG9TOWZnIiwibm9uY2UiOiJHM3RkOWpvdm1Rc3ZHMTlxUlZ-MkZSMXl4aUsySHlndyJ9.ABCF5NQlxwQ2QR9IUkv4LjX2YbmCe1xYK9WcactBlFDmhbm4MSKbvVqabU459OqzHtzZZVJPDkPxnUZZiTU1QwmyXZPCDgWC_weGmtxn2AE2fliIHO89tjKQouFUzvsYaGxqsuLoJ-ly3nopTZqwSSvpVe94JXa6S6U7GSbO7Gmo1ziPPkD-Ye8pt-nEDVDTWtft91s0-5-hJapmhY2cdwatZCg3_YCIzBOfieA5ftSQmTQOLDl_pNbkM6ascdTO_4PLFbznR-cgBypAAKe9ZV4ooLTS-Hg04SFMgpoU6TOwF4lNzAmrfZV8itgTCsl0OoI4-jP5qii9DcKJDClgqg";
	
	String id;
	
	@Test(priority = 1)
	public void testSimpleInsert() {
		
		String GraphqlMutation = "{\r\n"
				+ "  \"query\": \"mutation { insert_todos(objects: [{title: \\\"sdet\\\"}]) { affected_rows returning { id created_at title } }}\"\r\n"
				+ "}";
		
	 id = given()
			.contentType(ContentType.JSON)
			.header("Authorization" , AUTH_TOKEN)
			.body(GraphqlMutation)
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(200)
			.body("data.insert_todos.returning[0].title" , equalTo("sdet"))
			.body("data.insert_todos.returning[0].id", notNullValue())
			.log().body()
			.extract().response().jsonPath().getString("data.insert_todos.returning[0].id");
	 
	 System.out.println("ID : " + id);
			
	}
	
	@Test(priority = 2)
	public void testSimpleUpdate() {
		
		String GraphqlMutation = "{\r\n"
				+ "  \"query\": \"mutation { update_todos( where: { id: { _eq:"+id+"} } _set: { title: \\\"GraphQL SDET\\\", is_completed: true } ) { affected_rows returning { id title is_completed } }}\"\r\n"
				+ "}";

		given()
			.contentType(ContentType.JSON)
			.header("Authorization" , AUTH_TOKEN)
			.body(GraphqlMutation)
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(200)
			.body("data.update_todos.returning[0].title" , equalTo("GraphQL SDET"))
			.body("data.update_todos.returning[0].id", notNullValue())
			.log().body() ;
			
	}
	
	@Test(priority = 3)
	public void testSimpleDelete() {
		
		String GraphqlMutation = "{\r\n"
				+ "  \"query\": \"mutation { delete_todos(where: { id: { _eq: "+id+" } }) { affected_rows returning { title } }}\"\r\n"
				+ "}";

		given()
			.contentType(ContentType.JSON)
			.header("Authorization" , AUTH_TOKEN)
			.body(GraphqlMutation)
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(200)
			.body("data.delete_todos.returning[0].title" , equalTo("GraphQL SDET"))
			.log().body() ;
			
	}
		
}

