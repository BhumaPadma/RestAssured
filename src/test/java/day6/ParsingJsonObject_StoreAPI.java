package day6;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParsingJsonObject_StoreAPI {

	@Test
	void testParsingJsonObject() {

		ResponseBody responsebody = given()

				.when().get("http://localhost:3000/store")

				.then().statusCode(200).extract().response().body();

		JsonPath jsonpath = new JsonPath(responsebody.asString());

		// get the size of json array
		int bookCount = jsonpath.getInt("book.size()");
		System.out.println(bookCount);

		// validate at least one book is available
		assertThat(bookCount, greaterThan(0));

		// validate and calculate all prices
		double totalPrice = 0;
		for (int i = 0; i < bookCount; i++) {
			double bookPrice = jsonpath.getDouble("book[" + i + "].price");
			totalPrice = totalPrice + bookPrice;
		}
		System.out.println(totalPrice);
		assertThat(totalPrice, is(53.92));

		// search for a book name "Moby Dick" in the list
		boolean status = false;
		for (int i = 0; i < bookCount; i++) {
			String booktitle = jsonpath.getString("book[" + i + "].title");
			if (booktitle.equals("Moby Dick")) {
				status = true;
				break;
			}
		}
		assertThat(status, is(true));

	}

}
