package day_10;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class DataDrivenTesting {

	private static final String BEARER_TOKEN = "Bearer 6216d58de2027544f10f0c8739169085882b413f2110c6166cbe66fe0c590aa5";
	private static final String BASE_URL = "https://simple-books-api.glitch.me/orders";
	
	@Test(dataProvider = "excelDataProvider", dataProviderClass = Dataproviders.class)
	void runWithExcelData(String bookId, String customerName)
	{
		testSubmitAndDeleteOrder(bookId,customerName);
	}
	
	@Test(dataProvider = "jsonDataProvider", dataProviderClass = Dataproviders.class)
	void runWithJsonData(Map<String,String> data)
	{
		testSubmitAndDeleteOrder(data.get("BookID"), data.get("CustomerName"));
	}
	
	 @Test(dataProvider="csvDataProvider", dataProviderClass=Dataproviders.class)
	 public void testWithCSVData(String bookId, String customerName)
	 {
		 testSubmitAndDeleteOrder(bookId,customerName);
	 }

	 void testSubmitAndDeleteOrder(String bookId, String customerName) {
		
		JSONObject requestData = new JSONObject();
		requestData.put("bookId", Integer.parseInt(bookId));
		requestData.put("customerName", customerName);
		
		String orderID = given()
			.contentType("application/json")
			.header("Authorization", BEARER_TOKEN)
			.body(requestData.toString())
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(201)
			.log().body()
			.extract().response().jsonPath().getString("orderId");
		
		given()
			.header("Authorization", BEARER_TOKEN)
			.contentType("application/json")
			.pathParam("orderId", orderID)
		.when()
			.delete(BASE_URL +"/{orderId}")
		.then()
			.statusCode(204);
	}
}
